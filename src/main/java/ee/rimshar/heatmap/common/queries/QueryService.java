package ee.rimshar.heatmap.common.queries;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.springframework.core.io.ClassPathResource;

public abstract class QueryService {

  protected String readFile(String filePath) {
    ClassPathResource resource = new ClassPathResource(filePath);
    try {
      return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
    } catch (IOException ex) {
      throw new SQLScriptException(ex.getMessage());
    }
  }
}
