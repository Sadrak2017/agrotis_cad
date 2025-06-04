package org.agrotis.cad.dto.input;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Register")
public class RegisterInput implements Serializable {

  @Schema(description = "Nome do representante", example = "João Marcos Oliveira")
  private String name;

  @Schema(description = "Data início", example = "20250131")
  private Integer data_ini;

  @Schema(description = "Data fim", example = "20250519")
  private Integer date_fim;

  @Schema(description = "Código do laboratório", example = "3")
  private Integer codLaboratory;

  @Schema(description = "Código da Propriedade", example = "6")
  private Integer codProperty;

}
