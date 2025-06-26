package com.app.urlshortener.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String longUrl;
    private String shortUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Click> hits;
    // getters et setters...
}
