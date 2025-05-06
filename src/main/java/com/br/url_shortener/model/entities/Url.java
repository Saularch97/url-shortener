package com.br.url_shortener.model.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "urls")
public class Url {

    @Id
    private String originalUrl;

    private String urlSha;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getUrlSha() {
        return urlSha;
    }

    public void setUrlSha(String urlSha) {
        this.urlSha = urlSha;
    }

}
