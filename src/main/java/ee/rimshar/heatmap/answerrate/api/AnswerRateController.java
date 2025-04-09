package ee.rimshar.heatmap.answerrate.api;

import ee.rimshar.heatmap.answerrate.AnswerRateService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/api/heatmap/answer-rate", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnswerRateController {
  
  private final AnswerRateService service;

  @GetMapping
  public List<AnswerRate> getAnswerRateData(
      @RequestParam @NonNull String dateInput,
      @RequestParam @NonNull @Min(3) @Max(10) Integer numberOfShades,
      @RequestParam(required = false, defaultValue = "0") Integer startHour,
      @RequestParam(required = false, defaultValue = "23") Integer endHour
  ) {
    return service.getAnswerRateData(
        dateInput,
        numberOfShades,
        startHour,
        endHour
    );
  }
}
