package com.app.urlshortener.services;

import com.app.urlshortener.models.URL;
import com.app.urlshortener.repositories.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class URLService {
    @Autowired
    private URLRepository repo;

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
        u.setHits(0);
        return repo.save(u);
    }

    public URL expandUrl(String shortUrl) {
        return repo.findByShortUrl(shortUrl);
    }
}

