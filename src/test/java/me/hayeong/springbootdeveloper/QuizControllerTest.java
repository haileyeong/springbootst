package me.hayeong.springbootdeveloper;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;
import java.awt.PageAttributes.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

  @Autowired protected MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @Autowired private ObjectMapper objcetmapper;

  @BeforeEach
  public void mockMvcSetUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @DisplayName("quiz(): GET /quiz?code=1 이면 응답 코드는 201, 응답 본문은 Created!를 리턴한다.")
  @Test
  public void getQuiz1() throws Exception {
    // given. 응답 코드
    final String url = "/quiz";

    // when
    final ResultActions result = mockMvc.perform(get(url).param("code", "1"));

    // then
    result.andExpect(status().isCreated()).andExpect(content().string("Created!"));
  }

  @DisplayName("quiz(): GET /quiz?code=2 이면 응답 코드는 200, 응답 본문은 Bad Request!를 리턴한다.")
  @Test
  public void getQuiz2() throws Exception {
    // given
    final String url = "/quiz";

    // when
    final ResultActions result = mockMvc.perform(get(url).param("code", "2"));

    // then
    result.andExpect(status().isBadRequest()).andExpect(content().string("Bad Request!"));
  }


}
