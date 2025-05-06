package com.br.url_shortener.controllers;

import com.br.url_shortener.model.request.UrlShortenerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.br.url_shortener.services.UrlShortenerService;

import java.net.URI;

@RestController
@RequestMapping("/xxx.com")
public class UrlShortenerController {

    private final UrlShortenerService service;

    UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @GetMapping("/teste")
    ResponseEntity<String> teste() {
        return ResponseEntity.ok().build();
    }

    // TODO nome mais descritivo
    @PostMapping("/short")
    ResponseEntity<String> shortUrl(@RequestBody UrlShortenerRequest request) {

        String res  = service.shortenUrl(request);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/a/{sha}")
    ResponseEntity<String> redirectToFullUrl(@PathVariable String sha) {

        String fullUrl = service.getUrlFromSha(sha);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(fullUrl))
                .build();

    }
}
