package org.agrotis.cad.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.agrotis.cad.model.RegisterEntity;
import org.springframework.http.HttpStatus;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Payload de retorno da API")
public class ApiResponseRegister extends ApiResponseMessage implements Serializable {

  @Schema(description = "Registro")
  private RegisterEntity data;

  public ApiResponseRegister(HttpStatus status, List<Message> messages, RegisterEntity data, String globalMessage) {
    this.data = data;
    this.setStatus(status);
    this.setGlobalMessage(globalMessage);
    this.setMessages(messages);
  }

  public ApiResponseRegister(HttpStatus status, List<Message> messages, String globalMessage) {
    this.setStatus(status);
    this.setGlobalMessage(globalMessage);
    this.setMessages(messages);
  }

  public ApiResponseRegister(HttpStatus status, Message messages) {
    this.setStatus(status);
    this.setMessages(List.of(messages));
  }
}
