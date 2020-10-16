package config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyConfig {

  private static Configuration config;

  private static final String CONFIG_FILE = "config.properties";

  public static Configuration getInstance() throws ConfigurationException {

    if (config == null) {
      Configurations configurations = new Configurations();
      config = configurations.properties(CONFIG_FILE);
    }
    return config;
  }

}
