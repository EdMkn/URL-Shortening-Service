package com.app.urlshortener.controllers;

import com.app.urlshortener.dto.URLStatsDTO;
import com.app.urlshortener.models.URL;
import com.app.urlshortener.services.URLService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/showall")
    public List<URLStatsDTO> showAllUrls() {
        List<URLStatsDTO> urls = service.getAllURLS();
        return urls;
    }

    @GetMapping("/expand/{shortUrl}")
    public URL expandUrl(@PathVariable String shortUrl, HttpServletRequest request) {
        return service.expandUrl(shortUrl, request);
    }

    @GetMapping("/stats/{shortUrl}")
    public URLStatsDTO getUrlStats(@PathVariable String shortUrl) {
        return service.getUrlStats(shortUrl);
    }

}

