package org.acme.service;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;

public interface FileService {

    void salvar(Long id, FileUpload file) throws IOException;

    ArquivoDownload download(String fid);

    void remover(String fid);
}