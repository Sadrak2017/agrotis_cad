package org.agrotis.cad;

import org.agrotis.cad.dto.ShippingRegister;
import org.agrotis.cad.dto.input.RegisterInput;
import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.messages.Message;
import org.agrotis.cad.model.LaboratoryEntity;
import org.agrotis.cad.model.PropertyEntity;
import org.agrotis.cad.model.RegisterEntity;
import org.agrotis.cad.repositories.LaboratoryRepository;
import org.agrotis.cad.repositories.PropertyRepository;
import org.agrotis.cad.repositories.RegisterRepository;
import org.agrotis.cad.services.RegisterService;
import org.agrotis.cad.utils.ValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RegisterServiceTest {

  @InjectMocks
  private RegisterService registerService;

  @Mock
  private RegisterRepository registerRepository;

  @Mock
  private PropertyRepository propertyRepository;

  @Mock
  private LaboratoryRepository laboratoryRepository;

  private RegisterInput registerInput;
  private LaboratoryEntity labEntity;
  private PropertyEntity propEntity;
  private RegisterEntity regEntity;

  @BeforeEach
  void setup() {
    registerInput = new RegisterInput();
    registerInput.setCodLaboratory(1);
    registerInput.setCodProperty(1);
    registerInput.setName("Registro Teste");

    labEntity = new LaboratoryEntity();
    labEntity.setId(1);
    labEntity.setDescription("Lab");

    propEntity = new PropertyEntity();
    propEntity.setId(1);
    propEntity.setDescription("Propriedade");

    regEntity = new RegisterEntity();
    regEntity.setUuid("abc-123");
    regEntity.setName("Registro Teste");
    regEntity.setLaboratoryEntity(labEntity);
    regEntity.setPropertyEntity(propEntity);
  }

  @Test
  void testSearch_returnsRegisters() {
    List<RegisterEntity> entities = List.of(regEntity);
    when(registerRepository.findAll()).thenReturn(entities);

    ResponseEntity<ApiResponse<ShippingRegister>> response = registerService.search();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getData().getCadastro()).hasSize(1);
    assertThat(response.getBody().getData().getCadastro().get(0).getName()).isEqualTo("Registro Teste");
  }


  @Test
  void testSave_validInput_savesInternalServerError() {
    when(laboratoryRepository.findById(1)).thenReturn(Optional.of(labEntity));
    when(propertyRepository.findById(1)).thenReturn(Optional.of(propEntity));

    try (MockedStatic<ValidationUtils> mocked = mockStatic(ValidationUtils.class)) {
      mocked.when(() -> ValidationUtils.validateRegisterInput(registerInput, labEntity, propEntity))
          .thenReturn(Collections.emptyList());

      ResponseEntity<ApiResponse<Message>> response = registerService.save(registerInput);

      verify(registerRepository).save(any(RegisterEntity.class));
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Test
  void testSave_withValidationErrors_returnsBadRequest() {
    when(laboratoryRepository.findById(1)).thenReturn(Optional.of(labEntity));
    when(propertyRepository.findById(1)).thenReturn(Optional.of(propEntity));

    try (MockedStatic<ValidationUtils> mocked = mockStatic(ValidationUtils.class)) {
      mocked.when(() -> ValidationUtils.validateRegisterInput(registerInput, labEntity, propEntity))
          .thenReturn(List.of(new Message("Erro de validação", Message.MessageTypeEnum.A)));

      ResponseEntity<ApiResponse<Message>> response = registerService.save(registerInput);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
      verify(registerRepository, never()).save(any());
    }
  }

  @Test
  void testSave_withException_returnsInternalServerError() {
    when(laboratoryRepository.findById(1)).thenReturn(Optional.of(labEntity));
    when(propertyRepository.findById(1)).thenReturn(Optional.of(propEntity));

    try (MockedStatic<ValidationUtils> mocked = mockStatic(ValidationUtils.class)) {
      mocked.when(() -> ValidationUtils.validateRegisterInput(registerInput, labEntity, propEntity))
          .thenReturn(Collections.emptyList());

      doThrow(new RuntimeException("Erro ao salvar")).when(registerRepository).save(any());

      ResponseEntity<ApiResponse<Message>> response = registerService.save(registerInput);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Test
  void testDelete_withValidUuid_deletesSuccessfully() {
    when(registerRepository.existsById("abc-123")).thenReturn(true);

    ResponseEntity<ApiResponse<Message>> response = registerService.delete("abc-123");

    verify(registerRepository).deleteById("abc-123");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void testDelete_withNullUuid_returnsBadRequest() {
    ResponseEntity<ApiResponse<Message>> response = registerService.delete(null);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testDelete_withEmptyUuid_returnsBadRequest() {
    ResponseEntity<ApiResponse<Message>> response = registerService.delete("");

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testDelete_withNonexistentUuid_returnsBadRequest() {
    when(registerRepository.existsById("abc-999")).thenReturn(false);

    ResponseEntity<ApiResponse<Message>> response = registerService.delete("abc-999");

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testDelete_withException_returnsInternalServerError() {
    when(registerRepository.existsById("abc-123")).thenReturn(true);
    doThrow(new RuntimeException("Erro interno")).when(registerRepository).deleteById("abc-123");

    ResponseEntity<ApiResponse<Message>> response = registerService.delete("abc-123");

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
