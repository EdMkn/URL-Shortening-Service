package com.app.urlshortener.repositories;
import com.app.urlshortener.models.URL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<URL, Long> {
    URL findByShortUrl(String shortUrl);
}
