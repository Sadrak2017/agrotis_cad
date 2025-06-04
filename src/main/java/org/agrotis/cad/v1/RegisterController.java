package org.agrotis.cad.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrotis.cad.dto.ShippingRegister;
import org.agrotis.cad.dto.input.RegisterInput;
import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.messages.Message;
import org.agrotis.cad.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Tag(name = "Register - CRUD", description = "Ações operacionais cadastro")
@CrossOrigin(origins = "*")
@RequestMapping(value = "v1/register", produces = "application/json")
public class RegisterController {

  @Autowired
  private RegisterService registerService;

  @Operation(
      description = "Solicita lista de cadastro",
      responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content =
      @Content(schema = @Schema(implementation = ApiResponse.class)))},
      summary = "Retorna lista de registro."
  )
  @GetMapping(value = "/all", produces = "application/json")
  public ResponseEntity<ApiResponse<ShippingRegister>> search() {
    return registerService.search();
  }

  @Operation(
      description = "Salva cadastro",
      summary = "Cadastra registro.",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200",
              description = "Cadastro realizado com sucesso",
              content = @Content(schema = @Schema(implementation = ApiResponse.class))
          ),
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "400",
              description = "Requisição inválida"
          )
      }
  )
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<ApiResponse<Message>> save(@RequestBody RegisterInput body) {
    return registerService.save(body);
  }

  @Operation(
      description = "Remove cadastro",
      responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content =
      @Content(schema = @Schema(implementation = ApiResponse.class)))},
      summary = "Remove cadastro por UUID - RFC-4122"
  )
  @DeleteMapping(value = "/{uuid}", produces = "application/json")
  public ResponseEntity<ApiResponse<Message>> delete(
      @Schema(description = "Identificador do cadastro (UUID) -  RFC-4122.", example = "4c6d86df-b297-42a8-adba-4f691facfe98", required = true)
      @RequestParam(value = "uuid",  defaultValue = "4c6d86df-b297-42a8-adba-4f691facfe98") String uuid) {
    return registerService.delete(uuid);
  }

}
