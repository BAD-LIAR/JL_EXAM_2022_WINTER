package ru.itis.examproject.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.examproject.dto.EmailPasswordDto;
import ru.itis.examproject.dto.TokenDto;
import ru.itis.examproject.models.Token;
import ru.itis.examproject.models.User;
import ru.itis.examproject.repository.TokenRepository;
import ru.itis.examproject.repository.UsersRepository;

import java.util.UUID;
import java.util.function.Supplier;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokensRepository;

    @SneakyThrows
    @Override
    public TokenDto login(EmailPasswordDto emailPassword) {
        User user = usersRepository.findByEmail(emailPassword.getEmail())
                .orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(emailPassword.getPassword(), user.getHashPassword())) {
            String tokenValue = UUID.randomUUID().toString();
            Token token = Token.builder()
                    .token(tokenValue)
                    .user(user)
                    .build();

            tokensRepository.save(token);

            return TokenDto.builder()
                    .token(tokenValue)
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Override
    public User createUser(EmailPasswordDto emailPasswordDto) {
        User user = User.builder()
                .email(emailPasswordDto.getEmail())
                .hashPassword(passwordEncoder.encode(emailPasswordDto.getPassword()))
                .role(User.Role.USER)
                .state(User.State.ACTIVE)
                .build();
        return usersRepository.save(user);
    }
}

