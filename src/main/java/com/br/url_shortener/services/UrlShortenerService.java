package com.br.url_shortener.services;

import com.br.url_shortener.model.entities.Url;
import com.br.url_shortener.model.request.UrlShortenerRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.br.url_shortener.repositories.UrlShortenerRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UrlShortenerService {

    private final UrlShortenerRepository repository;
    
    @Value("${app.base-url}")
    private String baseUrl;

    public UrlShortenerService(UrlShortenerRepository repository) {
        this.repository = repository;
    }

    public String shortenUrl(UrlShortenerRequest request) {
        String urlSha = generateSha();

        // search for sha, while sha is equal generate another
        while (repository.findByUrlSha(urlSha).isPresent()) {
            urlSha = generateSha();
        }

        Url url = new Url();
        url.setOriginalUrl(request.url());
        url.setUrlSha(urlSha);

        repository.save(url);

        return baseUrl + "/" + urlSha;
    }

    private String generateSha() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] randomBytes = new byte[32]; // 256 bits = 32 bytes
            secureRandom.nextBytes(randomBytes);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(randomBytes);

            return Base64.getEncoder().encodeToString(hashBytes).substring(0, 5);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Erro ao gerar o hash: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getUrlFromSha(String sha) {

        return repository.findByUrlSha(sha).orElseThrow().getOriginalUrl();
    }
}
