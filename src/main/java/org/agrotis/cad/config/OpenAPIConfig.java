package org.agrotis.cad.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.agrotis.cad.CadApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Calendar;
import java.util.List;

@Configuration
public class OpenAPIConfig {

  @Value("${agrotis.environment.server.dev-url}")
  private String devUrl;
  @Value("${agrotis.environment.server.hml-url}")
  private String hmlUrl;
  @Value("${agrotis.environment.server.prd-url}")
  private String prodUrl;

  @Bean
  public OpenAPI openAPI() {

    Contact contact = new Contact();
    contact.setEmail("sadrak_earth@outlook.com");
    contact.setName("Agrotis");
    contact.setUrl("https://agrotis.com.br/");

    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    License license = new License()
      .name("Agrotis | Teste Técnico - Sistema de Cadastro")
      .url("https://agrotis.com.br/termos-e-condicoes-de-uso/");

    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("URL do servidor em ambiente de desenvolvimento");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("URL do servidor em ambiente de produção");

    Server hmlServer = new Server();
    hmlServer.setUrl(hmlUrl);
    hmlServer.setDescription("URL do servidor em ambiente de homologação/poc");

    String version = "1.0.0";
    Info info = new Info()
      .title("Teste Técnico - Sistema de Cadastro - Agrotis")
      .version(version)
      .contact(contact)
      .description("Agrotis| Sistema de Cadastro © " + currentYear +
        " Todos os direitos reservados - Termos e condições de uso.")
      .termsOfService("https://agrotis.com.br/termos-e-condicoes-de-uso/")
      .license(license);

    CadApplication.setVersion(version);

    return new OpenAPI()
      .servers(List.of(devServer, hmlServer, prodServer))
      .info(info);
  }
}
