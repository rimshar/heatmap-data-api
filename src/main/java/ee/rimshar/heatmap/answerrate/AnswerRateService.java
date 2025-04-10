package ee.rimshar.heatmap.answerrate;

import ee.rimshar.heatmap.answerrate.api.AnswerRate;
import ee.rimshar.heatmap.answerrate.queries.AnswerRateQueryService;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerRateService {

  private final JdbcTemplate jdbcTemplate;
  private final AnswerRateQueryService queryService;

  public List<AnswerRate> getAnswerRateData(
      String dateInput,
      Integer numberOfShades,
      Integer startHour,
      Integer endHour
  ) {
    validateDateRange(startHour, endHour);

    return jdbcTemplate.query(
        queryService.getAnswerRateSearchQuery(),
        (results, rowNum) -> buildAnswerRate(
            results.getInt("callHour"),
            results.getInt("answeredCalls"),
            results.getInt("totalCalls"),
            numberOfShades
        ),
        dateInput, startHour, endHour
    );
  }

  private void validateDateRange(Integer startHour, Integer endHour) {
    if ((startHour != null && endHour != null) && (startHour > endHour)) {
      throw new ConstraintViolationException("endHour: must be less or equal to startHour", null);
    }
  }

  private AnswerRate buildAnswerRate(int hour, int answeredCallCount, int totalCallCount, int numberOfShades) {
    float answeredCallRate = totalCallCount > 0 ? (answeredCallCount * 100f / totalCallCount) : 0;
    String shade = computeShade(answeredCallRate, numberOfShades);
    return new AnswerRate(hour, answeredCallCount, totalCallCount, answeredCallRate, shade);
  }

  private String computeShade(float answeredCallRate, int numberOfShades) {
    int shadeIndex = Math.min((int) (answeredCallRate / (100f / numberOfShades)) + 1, numberOfShades);
    return "Shade" + shadeIndex;
  }
}
