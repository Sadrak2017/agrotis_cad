package org.agrotis.cad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Register")
public class RegisterDto implements Serializable {

  @Schema(description = "Código do registro (UUID) - RFC-4122", example = "1d0cd4f1-dffe-447f-ae1e-8336f72e5bb5")
  private String uuid;

  @Schema(description = "Nome do representante", example = "João Marcos Oliveira")
  private String name;

  @Schema(description = "Data início", example = "20250131")
  private Integer data_ini;

  @Schema(description = "Data fim", example = "20250519")
  private Integer date_fim;

  @Schema(description = "Informações do laboratório", example = "3")
  private LaboratoryDto laboratory;

  @Schema(description = "Informações da Propriedade", example = "6")
  private PropertyDto infosProperty;

  @Schema(description = "Observações", example = "6")
  private String observations;

}
