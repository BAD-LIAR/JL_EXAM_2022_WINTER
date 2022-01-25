package ru.itis.examproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.examproject.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

