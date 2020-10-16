package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityConfig {

  private static VelocityConfig velocityConfig;

  private static final String CONFIG_FILE = "velocity.properties";

  public static VelocityConfig getInstance() {

    if (velocityConfig == null) {
      velocityConfig = new VelocityConfig();
    }
    return velocityConfig;
  }

  private VelocityConfig() {
    Properties properties = new Properties();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
      properties.load(is);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Velocity.init(properties);
  }

  public VelocityContext getContext() {
    return new VelocityContext();
  }

}
