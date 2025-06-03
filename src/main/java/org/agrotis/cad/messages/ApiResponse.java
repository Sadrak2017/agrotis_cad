package org.agrotis.cad.messages;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
public class ApiResponse<T> extends ApiResponseMessage {

  @Schema(description = "Dados da resposta")
  private transient T data;

  private ApiResponse(HttpStatus status, String globalMessage) {
    super(status, globalMessage);
    status();
  }

  private ApiResponse(T body, HttpStatus status) {
    super(status, null);
    this.data = body;
  }

  private ApiResponse(HttpStatus status) {
    super(status, null);
  }

  private void status() {
//    try {
//      ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
////      HttpServletResponse response = requestAttributes.getResponse();
////      response.setStatus(getStatus() != null ? getStatus().value() : HttpStatus.OK.value());
//    } catch (Exception ignored) {
//      log.warn("::api-response:: a requisição não possui um objeto de response");
//    }
  }

}
