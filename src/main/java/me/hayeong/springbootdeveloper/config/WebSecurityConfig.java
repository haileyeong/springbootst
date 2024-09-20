package me.hayeong.springbootdeveloper.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import lombok.RequiredArgsConstructor;
import me.hayeong.springbootdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final UserDetailService userService;

  // 스프링 시큐리티 기능 비활성화
  @Bean
  public WebSecurityCustomizer configure() {
    return (web) ->
        web.ignoring()
            .requestMatchers(toH2Console())
            .requestMatchers(new AntPathRequestMatcher("/statice/**"));
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeRequests(
            auth ->
                auth // 인증, 인가 설정
                    .requestMatchers( // 특정 요청과 일치하는 url에 대한 액세스 설정
                        new AntPathRequestMatcher("/login"),
                        new AntPathRequestMatcher("/signup"),
                        new AntPathRequestMatcher("/user"))
                    .permitAll() // 누구나 접근 가능
                    .anyRequest()
                    .authenticated()) // 위의 url 이외의 요청은. 인증이 성공된 상태여야 접근 가능
        .formLogin(
            formLogin ->
                formLogin // 폼 기반 로그인 설정
                    .loginPage("/login") // 로그인 페이지 경로
                    .defaultSuccessUrl("/") // 로그인이 완료되었을 때 이동할 경로
            )
        .logout(
            logout ->
                logout
                    .logoutSuccessUrl("/login") // 로그아웃 설정 -> 로그아웃이 완료되었을때 이동할 경로
                    .invalidateHttpSession(true)) // 로그아웃 이후 세션 전체 삭제 여부
        .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
        .build();
  }

  // 인증 관리자 관련 설정
  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity http,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      UserDetailService userDetailService) // UserDetailsService를 상속받는 클래스여야함
      throws Exception {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService); // 사용자 정보 서비스 설정
    authProvider.setPasswordEncoder(bCryptPasswordEncoder);
    return new ProviderManager(authProvider);
  }

  // 패스워드 인코더로 사용할 빈 등록
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
