package com.app.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class URLStatsDTO {
    private String shortUrl;
    private String longUrl;
    private int totalClicks;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
