package ru.itis.examproject.service;

import ru.itis.examproject.dto.EmailPasswordDto;
import ru.itis.examproject.dto.TokenDto;
import ru.itis.examproject.models.User;

public interface LoginService {
    TokenDto login(EmailPasswordDto emailPassword);

    User createUser(EmailPasswordDto emailPasswordDto);
}

