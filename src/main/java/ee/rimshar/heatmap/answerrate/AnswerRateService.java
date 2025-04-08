package ee.rimshar.heatmap.answerrate;

import ee.rimshar.heatmap.answerrate.api.AnswerRate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerRateService {
  
  public List<AnswerRate> getAnswerRateData(
      String dateInput,
      Integer numberOfShades,
      Integer startHour,
      Integer endHour
  ) {
    return List.of(new AnswerRate(1, 2, 1, 1.434234F, "TEST"));
  }
}
