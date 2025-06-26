package com.app.urlshortener.controllers;

import com.app.urlshortener.models.URL;
import com.app.urlshortener.services.URLService;
import jakarta.servlet.http.HttpServletRequest;
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
    public URL expandUrl(@PathVariable String shortUrl, HttpServletRequest request) {
        return service.expandUrl(shortUrl, request);
    }


}

