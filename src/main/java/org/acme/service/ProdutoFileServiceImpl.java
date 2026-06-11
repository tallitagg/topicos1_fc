package org.acme.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.acme.model.Arquivo;
import org.acme.model.Produto;
import org.acme.repository.ArquivoRepository;
import org.acme.repository.ProdutoRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class ProdutoFileServiceImpl implements FileService {

    private static final Set<String> EXTENSOES_PERMITIDAS = Set.of(
            "jpg",
            "jpeg",
            "png",
            "gif",
            "webp"
    );

    private static final long TAMANHO_MAXIMO = 5L * 1024 * 1024;
    private static final long TAMANHO_MINIMO = 1L;

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    ArquivoRepository arquivoRepository;

    @Inject
    ObjectMapper objectMapper;

    @ConfigProperty(name = "seaweedfs.master.url", defaultValue = "http://localhost:9333")
    String masterUrl;

    @ConfigProperty(name = "seaweedfs.volume.url", defaultValue = "__none__")
    String volumeUrlOverride;

    @ConfigProperty(name = "seaweedfs.request-timeout-ms", defaultValue = "10000")
    int timeoutMs;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    @Transactional
    public void salvar(Long id, FileUpload file) throws IOException {
        Produto produto = produtoRepository.findById(id);

        if (produto == null) {
            throw new NotFoundException("Produto não encontrado.");
        }

        validarArquivo(file);

        String nomeOriginal = Path.of(file.fileName()).getFileName().toString();
        String mimeType = resolverMimeType(nomeOriginal, file.contentType());
        byte[] arquivoBytes = Files.readAllBytes(file.uploadedFile());

        String fid = enviarParaSeaweed(nomeOriginal, mimeType, arquivoBytes);

        Arquivo arquivo = new Arquivo();
        arquivo.setFid(fid);
        arquivo.setNomeOriginal(nomeOriginal);
        arquivo.setMimeType(mimeType);
        arquivo.setTamanhoBytes(file.size());
        arquivo.setSha256(gerarSha256(file.uploadedFile()));

        arquivoRepository.persist(arquivo);

        produto.addArquivo(arquivo);
    }

    @Override
    public ArquivoDownload download(String fid) {
        if (fid == null || fid.isBlank()) {
            throw new WebApplicationException(
                    "Identificador da imagem inválido.",
                    Response.Status.BAD_REQUEST
            );
        }

        Arquivo arquivo = arquivoRepository.findByFid(fid)
                .orElseThrow(() -> new WebApplicationException(
                        "Imagem não encontrada no banco de dados.",
                        Response.Status.NOT_FOUND
                ));

        String volumeId = extrairVolumeId(fid);

        JsonNode lookup = requisitarJson(
                "GET",
                limparUrl(masterUrl) + "/dir/lookup?volumeId=" + volumeId,
                null,
                null
        );

        JsonNode locations = lookup.get("locations");

        if (locations == null || !locations.isArray() || locations.isEmpty()) {
            throw new WebApplicationException(
                    "Imagem não encontrada no SeaweedFS.",
                    Response.Status.NOT_FOUND
            );
        }

        String volumeUrl = obterTexto(locations.get(0), "publicUrl");

        if (volumeUrl == null) {
            volumeUrl = obterTexto(locations.get(0), "url");
        }

        String urlDownload = resolverVolumeUrl(volumeUrl) + "/" + fid;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlDownload))
                .timeout(java.time.Duration.ofMillis(timeoutMs))
                .GET()
                .build();

        try {
            HttpResponse<byte[]> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofByteArray()
            );

            if (response.statusCode() == 404) {
                throw new WebApplicationException(
                        "Imagem não encontrada.",
                        Response.Status.NOT_FOUND
                );
            }

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new WebApplicationException(
                        "Falha ao baixar imagem do SeaweedFS.",
                        Response.Status.BAD_GATEWAY
                );
            }

            return new ArquivoDownload(
                    response.body(),
                    arquivo.getMimeType(),
                    arquivo.getNomeOriginal()
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new WebApplicationException(
                    "Download interrompido.",
                    Response.Status.INTERNAL_SERVER_ERROR
            );

        } catch (IOException e) {
            throw new WebApplicationException(
                    "Erro ao baixar imagem.",
                    e,
                    Response.Status.BAD_GATEWAY
            );
        }
    }

    @Override
    @Transactional
    public void remover(String fid) {
        if (fid == null || fid.isBlank()) {
            throw new WebApplicationException(
                    "Identificador da imagem inválido.",
                    Response.Status.BAD_REQUEST
            );
        }

        Arquivo arquivo = arquivoRepository.findByFid(fid)
                .orElseThrow(() -> new WebApplicationException(
                        "Imagem não encontrada no banco de dados.",
                        Response.Status.NOT_FOUND
                ));

        removerNoSeaweed(fid);

        produtoRepository.findByArquivoId(arquivo.getId())
                .ifPresent(produto -> produto.removeArquivo(arquivo));

        arquivoRepository.delete(arquivo);
    }

    private void validarArquivo(FileUpload file) {
        if (file == null) {
            throw new WebApplicationException(
                    "Arquivo de imagem é obrigatório.",
                    Response.Status.BAD_REQUEST
            );
        }

        if (file.fileName() == null || file.fileName().isBlank()) {
            throw new WebApplicationException(
                    "Nome do arquivo inválido.",
                    Response.Status.BAD_REQUEST
            );
        }

        if (file.size() < TAMANHO_MINIMO) {
            throw new WebApplicationException(
                    "O arquivo está vazio.",
                    Response.Status.BAD_REQUEST
            );
        }

        if (file.size() > TAMANHO_MAXIMO) {
            throw new WebApplicationException(
                    "A imagem deve ter no máximo 5MB.",
                    Response.Status.BAD_REQUEST
            );
        }

        String extensao = obterExtensao(file.fileName());

        if (extensao == null || !EXTENSOES_PERMITIDAS.contains(extensao)) {
            throw new WebApplicationException(
                    "Formato de imagem não permitido. Use jpg, jpeg, png, gif ou webp.",
                    Response.Status.BAD_REQUEST
            );
        }
    }

    private String enviarParaSeaweed(String nomeOriginal, String mimeType, byte[] arquivoBytes) {
        JsonNode assign = requisitarJson(
                "GET",
                limparUrl(masterUrl) + "/dir/assign",
                null,
                null
        );

        String fid = obterTexto(assign, "fid");
        String volumeUrl = obterTexto(assign, "publicUrl");

        if (volumeUrl == null) {
            volumeUrl = obterTexto(assign, "url");
        }

        if (fid == null || volumeUrl == null) {
            throw new WebApplicationException(
                    "Resposta inválida do SeaweedFS ao reservar arquivo.",
                    Response.Status.BAD_GATEWAY
            );
        }

        String extensao = obterExtensao(nomeOriginal);
        String nomeNormalizado = UUID.randomUUID().toString();

        if (extensao != null) {
            nomeNormalizado = nomeNormalizado + "." + extensao;
        }

        String boundary = "----QuarkusSeaweedBoundary" + UUID.randomUUID();
        byte[] corpo = montarMultipart(boundary, nomeNormalizado, mimeType, arquivoBytes);

        String urlUpload = resolverVolumeUrl(volumeUrl) + "/" + fid;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlUpload))
                .timeout(java.time.Duration.ofMillis(timeoutMs))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(corpo))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new WebApplicationException(
                        "Falha ao enviar imagem para o SeaweedFS.",
                        Response.Status.BAD_GATEWAY
                );
            }

            return fid;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new WebApplicationException(
                    "Upload interrompido.",
                    Response.Status.INTERNAL_SERVER_ERROR
            );

        } catch (IOException e) {
            throw new WebApplicationException(
                    "Erro ao enviar imagem para o SeaweedFS.",
                    e,
                    Response.Status.BAD_GATEWAY
            );
        }
    }

    private byte[] montarMultipart(
            String boundary,
            String fileName,
            String contentType,
            byte[] arquivoBytes
    ) {
        String quebraLinha = "\r\n";

        String inicio = "--" + boundary + quebraLinha
                + "Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"" + quebraLinha
                + "Content-Type: " + contentType + quebraLinha
                + quebraLinha;

        String fim = quebraLinha + "--" + boundary + "--" + quebraLinha;

        byte[] inicioBytes = inicio.getBytes(StandardCharsets.UTF_8);
        byte[] fimBytes = fim.getBytes(StandardCharsets.UTF_8);

        byte[] corpo = new byte[inicioBytes.length + arquivoBytes.length + fimBytes.length];

        System.arraycopy(inicioBytes, 0, corpo, 0, inicioBytes.length);
        System.arraycopy(arquivoBytes, 0, corpo, inicioBytes.length, arquivoBytes.length);
        System.arraycopy(fimBytes, 0, corpo, inicioBytes.length + arquivoBytes.length, fimBytes.length);

        return corpo;
    }

    private void removerNoSeaweed(String fid) {
        try {
            String volumeId = extrairVolumeId(fid);

            JsonNode lookup = requisitarJson(
                    "GET",
                    limparUrl(masterUrl) + "/dir/lookup?volumeId=" + volumeId,
                    null,
                    null
            );

            JsonNode locations = lookup.get("locations");

            if (locations == null || !locations.isArray() || locations.isEmpty()) {
                return;
            }

            String volumeUrl = obterTexto(locations.get(0), "publicUrl");

            if (volumeUrl == null) {
                volumeUrl = obterTexto(locations.get(0), "url");
            }

            if (volumeUrl == null) {
                return;
            }

            String urlDelete = resolverVolumeUrl(volumeUrl) + "/" + fid;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlDelete))
                    .timeout(java.time.Duration.ofMillis(timeoutMs))
                    .DELETE()
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.discarding());

        } catch (Exception ignored) {
        }
    }

    private JsonNode requisitarJson(
            String metodo,
            String url,
            String contentType,
            byte[] corpo
    ) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(java.time.Duration.ofMillis(timeoutMs));

        if ("GET".equalsIgnoreCase(metodo)) {
            builder.GET();
        } else if ("POST".equalsIgnoreCase(metodo)) {
            if (contentType != null) {
                builder.header("Content-Type", contentType);
            }

            builder.POST(HttpRequest.BodyPublishers.ofByteArray(corpo == null ? new byte[0] : corpo));
        } else {
            throw new IllegalArgumentException("Método HTTP não suportado: " + metodo);
        }

        try {
            HttpResponse<String> response = httpClient.send(
                    builder.build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new WebApplicationException(
                        "Falha de comunicação com o SeaweedFS.",
                        Response.Status.BAD_GATEWAY
                );
            }

            return objectMapper.readTree(response.body());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new WebApplicationException(
                    "Comunicação com SeaweedFS interrompida.",
                    Response.Status.INTERNAL_SERVER_ERROR
            );

        } catch (IOException e) {
            throw new WebApplicationException(
                    "Erro ao comunicar com SeaweedFS.",
                    e,
                    Response.Status.BAD_GATEWAY
            );
        }
    }

    private String resolverVolumeUrl(String volumeUrlSeaweed) {
    String override = System.getProperty("seaweedfs.volume.url");

    if (override != null
            && !override.isBlank()
            && !"__none__".equals(override)) {
        return limparUrl(override);
    }

    return limparUrl(normalizarUrl(volumeUrlSeaweed));
}

    private String normalizarUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new WebApplicationException(
                    "URL do volume SeaweedFS inválida.",
                    Response.Status.BAD_GATEWAY
            );
        }

        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }

        return "http://" + url;
    }

    private String limparUrl(String url) {
        if (url == null) {
            return "";
        }

        while (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }

    private String extrairVolumeId(String fid) {
        int indiceVirgula = fid.indexOf(',');

        if (indiceVirgula <= 0) {
            throw new WebApplicationException(
                    "FID inválido.",
                    Response.Status.BAD_REQUEST
            );
        }

        return fid.substring(0, indiceVirgula);
    }

    private String obterTexto(JsonNode node, String campo) {
        JsonNode valor = node.get(campo);

        if (valor == null || valor.isNull()) {
            return null;
        }

        return valor.asText();
    }

    private String obterExtensao(String nomeArquivo) {
        if (nomeArquivo == null) {
            return null;
        }

        int indicePonto = nomeArquivo.lastIndexOf('.');

        if (indicePonto < 0 || indicePonto == nomeArquivo.length() - 1) {
            return null;
        }

        return nomeArquivo.substring(indicePonto + 1).toLowerCase(Locale.ROOT);
    }

    private String resolverMimeType(String nomeOriginal, String contentType) {
        if (contentType != null
                && !contentType.isBlank()
                && !"application/octet-stream".equalsIgnoreCase(contentType)) {
            return contentType;
        }

        String extensao = obterExtensao(nomeOriginal);

        if ("jpg".equals(extensao) || "jpeg".equals(extensao)) {
            return "image/jpeg";
        }

        if ("png".equals(extensao)) {
            return "image/png";
        }

        if ("gif".equals(extensao)) {
            return "image/gif";
        }

        if ("webp".equals(extensao)) {
            return "image/webp";
        }

        return "application/octet-stream";
    }

    private String gerarSha256(Path arquivo) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = Files.readAllBytes(arquivo);
            byte[] hash = digest.digest(bytes);

            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new IOException("Erro ao gerar hash SHA-256 do arquivo.", e);
        }
    }
}