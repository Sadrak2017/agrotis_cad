package org.agrotis.cad.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.agrotis.cad.model.Register;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Payload de retorno da API")
public class ApiResponseRegister extends ApiResponseMessage implements Serializable {

  @Schema(description = "Registro")
  private Register data;

  public ApiResponseRegister(HttpStatus status, String className, List<Message> messages, Register data, String globalMessage) {
    this.data = data;
    this.setStatus(status);
    this.setGlobalMessage(globalMessage);
    this.setClassName(className);
    this.setMessages(messages);
  }

  public ApiResponseRegister(HttpStatus status, String className, List<Message> messages, String globalMessage) {
    this.setStatus(status);
    this.setGlobalMessage(globalMessage);
    this.setClassName(className);
    this.setMessages(messages);
  }

  public ApiResponseRegister(HttpStatus status, String className, Message messages) {
    this.setStatus(status);
    this.setClassName(className);
    this.setMessages(List.of(messages));
  }
}
