package step.impl;

import config.PropertyConfig;
import config.VelocityConfig;
import data.velocity.EntityVelocity;
import step.Writer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class JpaEntityWriter implements Writer<EntityVelocity> {

  @Override
  public void write(List<EntityVelocity> dataList) throws Exception {
    String templateFile = PropertyConfig.getInstance().getString("entity.codegen.template.file");
    String outputDir = PropertyConfig.getInstance().getString("entity.codegen.output.dir");
    String packageName = PropertyConfig.getInstance().getString("entity.codegen.package.name");

    dataList.forEach(data -> {
      VelocityContext context = VelocityConfig.getInstance().getContext();

      context.put("package", packageName);
      context.put("class", data.getClassName());
      context.put("table", data.getTableName());
      context.put("fieldList", data.getEntityFieldVelocities());

      try (FileWriter writer = new FileWriter(
          new File(FilenameUtils.concat(outputDir, String.format("%s.java", data.getClassName()))),
          StandardCharsets.UTF_8)) {
        Velocity.getTemplate(templateFile).merge(context, writer);
        writer.flush();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    });

  }

}
