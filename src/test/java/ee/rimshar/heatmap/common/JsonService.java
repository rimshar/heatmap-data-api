package ee.rimshar.heatmap.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonService {
  
  private final ObjectMapper objectMapper;

  public <T> List<T> getJsonList(String json, Class<T> convertType) {
    try {
      return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, convertType));
    } catch (Exception ex) {
      throw new TestException();
    }
  }
}
