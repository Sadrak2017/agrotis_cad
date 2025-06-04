package org.agrotis.cad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Remessa de registros")
public class ShippingRegister implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "Lista de registros")
  private List<RegisterDto> registerDtos;

}
