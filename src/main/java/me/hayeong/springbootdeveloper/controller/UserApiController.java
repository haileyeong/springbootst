package me.hayeong.springbootdeveloper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.hayeong.springbootdeveloper.dto.AddUserRequest;
import me.hayeong.springbootdeveloper.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

  private final UserService userService;

  @PostMapping("/user")
  public String signup(AddUserRequest request) {
    userService.save(request); // 회원 가입 메서드 호출
    return "redirect:/login"; // 회원 가입 완료 후 로그인 페이지로 이동
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    new SecurityContextLogoutHandler()
        .logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    return "redirect:/login";
  }
}
