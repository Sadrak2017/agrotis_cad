package org.agrotis.cad.messages;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Setter
@Getter
@Slf4j
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Resposta padrão da API")
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> extends ApiResponseMessage {

  @Schema(description = "Dados da resposta")
  private transient T data;

  public ApiResponse(T body, HttpStatus status) {
    super(status);
    this.data = body;
    status();
  }
  private ApiResponse(HttpStatus status) {
    super(status);
    status();
  }

  public ApiResponse<T> messages(List<Message> messages) {
    this.setMessages(messages);
    return this;
  }

  public ApiResponse<T> message(Message message) {
    this.setMessages(List.of(message));
    return this;
  }

  public static <T> ApiResponse<T> info(HttpStatus status) {
    return new ApiResponse<>(status);
  }

  public ApiResponse<T> info(String globalMessage) {
    this.setGlobalMessage(globalMessage);
    return this;
  }

  public ApiResponse<T> messages(Message message) {
    this.messages(message);
    return this;
  }

  private void status() {
    try {
      ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      HttpServletResponse response = requestAttributes.getResponse();
      response.setStatus(getStatus() != null ? getStatus().value() : HttpStatus.OK.value());
    } catch (Exception ignored) {
      log.warn("::api-response:: a requisição não possui um objeto de response");
    }
  }

}
