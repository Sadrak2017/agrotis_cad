package org.agrotis.cad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.agrotis.cad.dto.input.RegisterInput;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Register")
public class RegisterDto extends RegisterInput implements Serializable {

  @Schema(description = "Código do registro (UUID) - RFC-4122", example = "1d0cd4f1-dffe-447f-ae1e-8336f72e5bb5")
  private String uuid;


}
