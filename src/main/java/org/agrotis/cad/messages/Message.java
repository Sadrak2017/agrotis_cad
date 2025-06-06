package org.agrotis.cad.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable {

  @Serial
  private static final long serialVersionUID = -1L;
  @Schema(description = "Mensagem da operação realizada", example = "Processamento realizado com sucesso.")
  private String message;
  @Schema(description = "Tipo da mensagem", example = "O")
  private MessageTypeEnum type;

  public Message(String message, MessageTypeEnum type) {
    this.message = message;
    this.type = type;
  }

  @Getter
  public enum MessageTypeEnum {
    E("Erro"),
    C("Consistência"),
    A("Atenção"),
    I("Informação"),
    S("Sucesso");

    private final String description;
    MessageTypeEnum(String description) {
      this.description = description;
    }
  }
}
