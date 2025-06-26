package com.app.urlshortener.services;

import com.app.urlshortener.dto.URLStatsDTO;
import com.app.urlshortener.models.Click;
import com.app.urlshortener.models.URL;
import com.app.urlshortener.repositories.ClickRepository;
import com.app.urlshortener.repositories.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class URLService {
    @Autowired
    private URLRepository repo;


    @Autowired
    private ClickRepository clickRepository;

    private String generateShortUrl() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public URL shortenUrl(String longUrl, LocalDateTime expiresAt) {
        String shortUrl = generateShortUrl();
        URL u = new URL();
        u.setLongUrl(longUrl);
        u.setShortUrl(shortUrl);
        u.setCreatedAt(LocalDateTime.now());
        u.setExpiresAt(expiresAt);
        return repo.save(u);
    }

    public URL expandUrl(String shortUrl, HttpServletRequest request) {
        URL url = repo.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("Invalid URL id"));

        Click click = new Click();
        click.setUrl(url);
            click.setUserAgent(request.getHeader("User-Agent"));
        click.setIpAddress(request.getRemoteAddr());
        click.setTimestamp(LocalDateTime.now());

        clickRepository.save(click);

        repo.save(url);
        return url;
    }

    public URLStatsDTO getUrlStats(String shortUrl) {
        URL url = repo.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        int totalClicks = url.getHits() != null ? url.getHits().size() : 0;

        return new URLStatsDTO(
                url.getShortUrl(),
                url.getLongUrl(),
                totalClicks,
                url.getCreatedAt(),
                url.getExpiresAt()
        );
    }

    public List<URLStatsDTO> getAllURLS() {
        return repo.findAll().stream().map(url ->
                getUrlStats(url.getShortUrl())
        ).collect(Collectors.toList()).reversed();
    }
    }
