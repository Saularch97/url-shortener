package com.br.url_shortener.repositories;

import com.br.url_shortener.model.entities.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlShortenerRepository extends MongoRepository<Url, String> {
    Optional<Url> findByUrlSha(String originalUrl);
}
