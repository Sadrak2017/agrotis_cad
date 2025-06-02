package org.agrotis.cad.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Api Response Message")
public class ApiResponseMessage implements Serializable {

  @JsonIgnore
  @Serial
  private static final long serialVersionUID = 1L;
  @Schema(description = "Mensaagem global da API", example = "Agrotis - API Rest - Sistema de Cadastro 2025")
  private String globalMessage = "Agrotis - API Rest - Sistema de Cadastro 2025";
  @Schema(description = "Status da requisição" , example = "OK")
  private HttpStatus status;
  @Schema(description = "Versão da API", example = "25.2.0.RC-SNAPSHOT")
  private String version = "24.2.0.RC-SNAPSHOT";
  @Schema(description = "Classe reposável pela reposta requisição", example = "RegisterService")
  private String className;
  @Schema(description = "Data e horário da requisição", example = "2024-05-05 15:30:00.000")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private LocalDateTime datetime;
  @Schema(description = "Mensagens de retorno da operação realizada")
  private List<Message> messages;

  public ApiResponseMessage() {
    messages = new ArrayList<>();
    datetime = LocalDateTime.now();
    globalMessage = Objects.isNull(globalMessage) ? globalMessage : globalMessage.concat(" - ").concat(version);
  }

  public ApiResponseMessage(HttpStatus status, String globalMessage) {
    this();
    this.status = status;
    this.globalMessage = globalMessage;
  }

  public void addMessage(Message message) {
    if (messages == null) {
      messages = new ArrayList<>();
    }
    if (message != null) {
      messages.add(message);
    }
  }

}
