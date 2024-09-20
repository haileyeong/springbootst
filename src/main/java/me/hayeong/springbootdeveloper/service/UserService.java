package me.hayeong.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hayeong.springbootdeveloper.domain.User;
import me.hayeong.springbootdeveloper.dto.AddUserRequest;
import me.hayeong.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public Long save(AddUserRequest dto) {
    return userRepository.save(User.builder().email(dto.getEmail())
        //패스워드 암호화
        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
        .build()).getId();
  }
}
