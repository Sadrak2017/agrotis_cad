package org.agrotis.cad.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrotis.cad.dto.PropertyDto;
import org.agrotis.cad.dto.input.PropertyInput;
import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.messages.Message;
import org.agrotis.cad.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Tag(name = "Property - CRUD", description = "Ações operacionais proprieadades")
@CrossOrigin(origins = "*")
@RequestMapping(value = "v1/property", produces = "application/json")
public class PropertyController {

  @Autowired
  private PropertyService propertyService;

  @Operation(
      description = "Solicita lista de propriedades",
      responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content =
      @Content(schema = @Schema(implementation = ApiResponse.class)))},
      summary = "Retorna lista de propriedades."
  )
  @GetMapping(value = "/all", produces = "application/json")
  public ResponseEntity<ApiResponse<List<PropertyDto>>> search() {
    return propertyService.search();
  }

  @Operation(
      description = "Salva propriedade",
      summary = "Cadastra propriedade.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Lista de propriedades para cadastro",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(type = "array", implementation = PropertyInput.class),
              examples = @ExampleObject(value = "[" +
                  "{\"description\":\"Fazenda Boa Vista\"}," +
                  "{\"description\":\"Latifundio Recanto Feliz\"}," +
                  "{\"description\":\"Fazenda Primavera\"}," +
                  "{\"description\":\"Fazenda Santa Clara\"}," +
                  "{\"description\":\"Fazenda Vale Verde\"}," +
                  "{\"description\":\"Fazenda Água Limpa\"}" +
                  "]"
              )
          )
      ),
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
  public ResponseEntity<ApiResponse<Message>> save(@RequestBody List<PropertyInput> body) {
    return propertyService.save(body);
  }

  @Operation(
      description = "Remove propriedade",
      responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content =
      @Content(schema = @Schema(implementation = ApiResponse.class)))},
      summary = "Remove propriedade por ID."
  )
  @DeleteMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<ApiResponse<Message>> delete(@PathVariable Integer id) {
    return propertyService.delete(id);
  }


}
