package com.app.urlshortener.repositories;
import com.app.urlshortener.models.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URLRepository extends JpaRepository<URL, Long> {
    Optional<URL> findByShortUrl(String shortUrl);
}
