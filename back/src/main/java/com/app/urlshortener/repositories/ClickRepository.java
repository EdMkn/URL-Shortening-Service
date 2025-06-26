package com.app.urlshortener.repositories;

import com.app.urlshortener.models.Click;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClickRepository extends JpaRepository<Click, Long> {
}
