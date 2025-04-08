package ee.rimshar.heatmap.answerrate.api;

public record AnswerRate(
    Integer hour,
    Integer answeredCalls,
    Integer totalCalls,
    Float rate,
    String shade
) {}
