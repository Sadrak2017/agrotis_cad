package org.agrotis.cad.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.agrotis.cad.dto.LaboratoryDto;
import org.agrotis.cad.dto.input.LaboratoryInput;
import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.messages.Message;
import org.agrotis.cad.services.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@Tag(name = "Laboratory - CRUD", description = "Ações operacionais laboratórios")
@CrossOrigin(origins = "*")
@RequestMapping(value = "v1/laboratory", produces = "application/json")
public class LaboratoryController {

  @Autowired
  private LaboratoryService laboratoryService;

  @Operation(
    description = "Solicita lista de laboratórios",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content =
      @Content(schema = @Schema(implementation = ApiResponse.class)))},
    summary = "Retorna lista de laboratórios."
  )
  @GetMapping(value = "/all", produces = "application/json")
  public ResponseEntity<ApiResponse<List<LaboratoryDto>>> search() {
    return laboratoryService.search();
  }

  @Operation(
      description = "Salva laboratório",
      summary = "Cadastra laboratório.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Lista de laboratórios para cadastro",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = LaboratoryInput.class, type = "array"),
              examples = @ExampleObject(value = "[" +
                  "{\"description\":\"LAB - Fertilização Artificial\"}," +
                  "{\"description\":\"LAB - Análises Químicas\"}," +
                  "{\"description\":\"LAB - Biotecnologia\"}," +
                  "{\"description\":\"LAB - Microbiologia\"}," +
                  "{\"description\":\"LAB - Controle de Qualidade\"}" +
                  "]"
              )
          )
      ),
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              description = "Resposta padrão da API",
              content = @Content(schema = @Schema(implementation = ApiResponse.class))
          )
      }
  )
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<ApiResponse<Message>> save(@RequestBody List<LaboratoryInput> body) {
    return laboratoryService.save(body);
  }


  @Operation(
      description = "Remove laboratório",
      responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content =
      @Content(schema = @Schema(implementation = ApiResponse.class)))},
      summary = "Remove laboratório por ID."
  )
  @DeleteMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<ApiResponse<Message>> delete(@PathVariable Integer id) {
    return laboratoryService.delete(id);
  }

}
