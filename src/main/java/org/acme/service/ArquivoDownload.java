package org.acme.service;

public record ArquivoDownload(
        byte[] content,
        String contentType,
        String fileName
) {
}