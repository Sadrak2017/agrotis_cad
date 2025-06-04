package org.agrotis.cad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.agrotis.cad.dto.input.PropertyInput;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Property")
public class PropertyDto extends PropertyInput implements Serializable {

  @Schema(description = "Código", example = "1")
  private Integer id;

}
