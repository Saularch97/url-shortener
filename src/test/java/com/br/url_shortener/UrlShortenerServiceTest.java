package com.br.url_shortener;

import com.br.url_shortener.model.entities.Url;
import com.br.url_shortener.model.request.UrlShortenerRequest;
import com.br.url_shortener.repositories.UrlShortenerRepository;
import com.br.url_shortener.services.UrlShortenerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    private UrlShortenerRepository repository;

    @InjectMocks
    private UrlShortenerService service;
    
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "baseUrl", "https://url-shortener-oun3.onrender.com");
    }

    @Test
    void shortenUrl_ShouldGenerateUniqueShaAndSaveUrl() {
        // Arrange
        String originalUrl = "https://example.com";
        UrlShortenerRequest request = new UrlShortenerRequest(originalUrl);

        when(repository.findByUrlSha(any())).thenReturn(Optional.empty());

        // Act
        String shortenedUrl = service.shortenUrl(request);

        // Assert
        assertNotNull(shortenedUrl);
        assertTrue(shortenedUrl.startsWith("https://url-shortener-oun3.onrender.com"));
        verify(repository, times(1)).save(any(Url.class)); // Verify if save was called
    }

    @Test
    void shortenUrl_ShouldRegenerateShaIfCollisionOccurs() {
        // Arrange
        String originalUrl = "https://example.com";
        UrlShortenerRequest request = new UrlShortenerRequest(originalUrl);

        when(repository.findByUrlSha(any()))
                .thenReturn(Optional.of(new Url())) // SHA already exists (colision)
                .thenReturn(Optional.empty());   // new attempt search(no one returns)

        // Act
        String shortenedUrl = service.shortenUrl(request);

        // Assert
        assertNotNull(shortenedUrl);
        verify(repository, atLeast(2)).findByUrlSha(any()); // Verify if generate the Sha
    }

    @Test
    void getUrlFromSha_ShouldReturnOriginalUrlWhenShaExists() {
        // Arrange
        String sha = "abc123";
        String expectedUrl = "https://example.com";
        Url url = new Url();
        url.setOriginalUrl(expectedUrl);

        when(repository.findByUrlSha(sha)).thenReturn(Optional.of(url));

        // Act
        String result = service.getUrlFromSha(sha);

        // Assert
        assertEquals(expectedUrl, result);
    }

    @Test
    void getUrlFromSha_ShouldThrowExceptionWhenShaNotFound() {
        // Arrange
        String sha = "invalidSha";
        when(repository.findByUrlSha(sha)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.getUrlFromSha(sha));
    }
}
