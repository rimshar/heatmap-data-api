package ee.rimshar.heatmap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ee.rimshar.heatmap.answerrate.api.AnswerRate;
import ee.rimshar.heatmap.common.JsonService;
import ee.rimshar.heatmap.common.TestException;
import java.util.List;
import java.util.stream.Stream;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AnswerRateControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private JsonService jsonService;

  @Test
  @WithMockUser
  void happyPath_correctDataReturnedForDay() throws Exception {
    String response = performRequest(5, 0, 23)
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
    
    List<AnswerRate> results = jsonService.getJsonList(response, AnswerRate.class);
    
    assertEquals(9, results.size());
    AnswerRate testRate = results.stream()
        .filter(ar -> ar.hour() == 17)
        .findAny().orElseThrow();
    
    assertEquals(2, testRate.answeredCalls());
    assertEquals(50f, testRate.rate());
    assertEquals(4, testRate.totalCalls());
    assertEquals("Shade3", testRate.shade());
  }

  @Test
  @WithMockUser
  void happyPath_shadeChangesWithNumberOfShades() throws Exception {
    String response = performRequest(10, 0, 23)
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    AnswerRate testRate = jsonService.getJsonList(response, AnswerRate.class).stream()
        .filter(ar -> ar.hour() == 17)
        .findAny().orElseThrow();

    assertEquals("Shade6", testRate.shade());
  }

  @Test
  @WithMockUser
  void happyPath_hourDefaultValuesUsed() throws Exception {
    String response = performRequest(3, null, null)
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    List<AnswerRate> results = jsonService.getJsonList(response, AnswerRate.class);
    assertEquals(9, results.size());
  }

  @WithMockUser
  @ParameterizedTest
  @MethodSource("getValidationTestParams")
  void error_validationsWorkCorrectly(Integer numberOfShades, Integer startHour, Integer endHour, String errorMessage) throws Exception {
    performRequest(numberOfShades, startHour, endHour)
        .andExpect(status().isBadRequest())
        .andExpect(content().string(Matchers.equalTo(errorMessage)));
  }

  private ResultActions performRequest(Integer numberOfShades, Integer startHour, Integer endHour) {
    try {
      return mockMvc.perform(get("/api/heatmap/answer-rate")
          .param("dateInput", "2025-04-07")
          .param("numberOfShades", numberOfShades != null ? String.valueOf(numberOfShades) : null)
          .param("startHour", startHour != null ? String.valueOf(startHour) : null)
          .param("endHour", endHour != null ? String.valueOf(endHour) : null));
    } catch (Exception ex) {
      throw new TestException();
    }
  }
  
  public static Stream<Arguments> getValidationTestParams() {
    return Stream.of(
        Arguments.of(1, null, null, "Validation error for parameter numberOfShades: must be greater than or equal to 3"),
        Arguments.of(11, null, null, "Validation error for parameter numberOfShades: must be less than or equal to 10"),
        Arguments.of(5, -40, null, "Validation error for parameter startHour: must be greater than or equal to 0"),
        Arguments.of(5, 40, null, "Validation error for parameter startHour: must be less than or equal to 23"),
        Arguments.of(5, null, -40, "Validation error for parameter endHour: must be greater than or equal to 0"),
        Arguments.of(5, null, 40, "Validation error for parameter endHour: must be less than or equal to 23"),
        Arguments.of(5, 23, 11, "Validation error for parameter endHour: must be less or equal to startHour")
    );
  }
}
