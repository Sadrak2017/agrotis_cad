package org.agrotis.cad.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Tag(name = "Operations", description = "Ações operacionais de cadastro")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/operation", produces = "application/json")
public class CadController {

  @Autowired
  private RegisterService registerService;


  @Operation(
    description = "Solicita lista de propriedades",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content =
      @Content(schema = @Schema(implementation = ApiResponse.class)))},
    summary = "Retorna status da operação.",
    security = {@SecurityRequirement(name = "Authorization Bearer")}
  )
  @PostMapping(value = "/properties", consumes = "application/json", produces = "application/json")
  public ResponseEntity<ApiResponse> list_properties() {
    return registerService.get_properties();
  }

  @Operation(
    description = "Solicita lista de laboratórios",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content =
      @Content(schema = @Schema(implementation = ApiResponse.class)))},
    summary = "Retorna status da operação.",
    security = {@SecurityRequirement(name = "Authorization Bearer")}
  )
  @PostMapping(value = "/laboratories", consumes = "application/json", produces = "application/json")
  public ResponseEntity<ApiResponse> list_laboratories() {
    return registerService.get_laboratories();
  }

}
