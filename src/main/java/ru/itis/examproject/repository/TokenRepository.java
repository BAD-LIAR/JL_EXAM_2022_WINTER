package ru.itis.examproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.examproject.models.Token;

import java.util.Optional;


public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}

