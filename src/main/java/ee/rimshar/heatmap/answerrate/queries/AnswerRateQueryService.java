package ee.rimshar.heatmap.answerrate.queries;

import ee.rimshar.heatmap.common.queries.QueryService;
import org.springframework.stereotype.Service;

@Service
public class AnswerRateQueryService extends QueryService {

  public String getAnswerRateSearchQuery() {
    return readFile("/queries/answerrate/answer-rate-search.sql");
  }
}
