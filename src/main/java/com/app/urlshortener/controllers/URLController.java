package com.app.urlshortener.controllers;

import com.app.urlshortener.models.URL;
import com.app.urlshortener.services.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class URLController {
    @Autowired
    private URLService service;

    @PostMapping("/shorten")
    public URL shortenUrl(@RequestBody String longUrl,
                          @RequestParam(required = false) LocalDateTime expiresAt) {
        return service.shortenUrl(longUrl, expiresAt);
    }

    @GetMapping("/expand/{shortUrl}")
    public URL expandUrl(@PathVariable String shortUrl) {
        URL u = service.expandUrl(shortUrl);
        u.setHits(u.getHits() + 1);
        return service.shortenUrl(u.getLongUrl(), u.getExpiresAt());
    }
}

