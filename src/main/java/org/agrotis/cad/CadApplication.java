package org.agrotis.cad;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CadApplication {
  @Getter
  @Setter
  private static String version;

  public static void main(String[] args) {
    SpringApplication.run(CadApplication.class, args);
  }

}
