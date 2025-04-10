SELECT
    EXTRACT(HOUR FROM STARTED_AT) AS callHour,
    COUNT(*) AS totalCalls,
    SUM(CASE WHEN STATUS = 'ANSWER' THEN 1 ELSE 0 END) AS answeredCalls
FROM HEATMAP.CALL_LOG
WHERE DATE(STARTED_AT) = ?
  AND EXTRACT(HOUR FROM STARTED_AT) BETWEEN ? AND ?
GROUP BY EXTRACT(HOUR FROM STARTED_AT)
ORDER BY callHour