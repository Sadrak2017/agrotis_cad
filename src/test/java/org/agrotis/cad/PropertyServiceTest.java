package org.agrotis.cad;

import org.agrotis.cad.dto.PropertyDto;
import org.agrotis.cad.dto.input.PropertyInput;
import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.messages.Message;
import org.agrotis.cad.model.PropertyEntity;
import org.agrotis.cad.repositories.PropertyRepository;
import org.agrotis.cad.services.PropertyService;
import org.agrotis.cad.utils.ValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class PropertyServiceTest {

  @InjectMocks
  private PropertyService propertyService;

  @Mock
  private PropertyRepository propertyRepository;

  private PropertyEntity propEntity;
  private PropertyInput propInput;

  @BeforeEach
  void setup() {
    propEntity = new PropertyEntity();
    propEntity.setId(1);
    propEntity.setDescription("Property 1");

    propInput = new PropertyInput();
    propInput.setDescription("Property 1");
  }

  @Test
  void testSearch_returnsProperties() {
    List<PropertyEntity> entities = List.of(propEntity);
    when(propertyRepository.findAll()).thenReturn(entities);

    ResponseEntity<ApiResponse<List<PropertyDto>>> response = propertyService.search();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getData()).hasSize(1);
    assertThat(response.getBody().getData().get(0).getDescription()).isEqualTo("Property 1");
  }


  @Test
  void testSave_withEmptyList_returnsBadRequest() {
    ResponseEntity<ApiResponse<Message>> response = propertyService.save(Collections.emptyList());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testSave_withValidData_savesAndReturnsSuccess() {
    List<PropertyInput> inputs = List.of(propInput);

    try (MockedStatic<ValidationUtils> validationMock = Mockito.mockStatic(ValidationUtils.class)) {
      validationMock.when(() -> ValidationUtils.removeExistingEntriesByDescription(
          anyList(), any(), any(), any(), any(), anyString()
      )).thenReturn(new ArrayList<>());

      ResponseEntity<ApiResponse<Message>> response = propertyService.save(inputs);

      verify(propertyRepository, times(1)).saveAll(anyList());
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
  }

  @Test
  void testSave_withException_returnsInternalServerError() {
    List<PropertyInput> inputs = List.of(propInput);

    try (MockedStatic<ValidationUtils> validationMock = Mockito.mockStatic(ValidationUtils.class)) {
      validationMock.when(() -> ValidationUtils.removeExistingEntriesByDescription(
          anyList(), any(), any(), any(), any(), anyString()
      )).thenReturn(new ArrayList<>());

      doThrow(new RuntimeException("Erro ao salvar")).when(propertyRepository).saveAll(anyList());

      ResponseEntity<ApiResponse<Message>> response = propertyService.save(inputs);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Test
  void testDelete_withValidId_deletesSuccessfully() {
    when(propertyRepository.existsById(1)).thenReturn(true);

    ResponseEntity<ApiResponse<Message>> response = propertyService.delete(1);

    verify(propertyRepository).deleteById(1);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void testDelete_withNullId_returnsBadRequest() {
    ResponseEntity<ApiResponse<Message>> response = propertyService.delete(null);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testDelete_withNonexistentId_returnsBadRequest() {
    when(propertyRepository.existsById(99)).thenReturn(false);

    ResponseEntity<ApiResponse<Message>> response = propertyService.delete(99);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testDelete_withException_returnsInternalServerError() {
    when(propertyRepository.existsById(1)).thenReturn(true);
    doThrow(new RuntimeException("Erro ao deletar")).when(propertyRepository).deleteById(1);

    ResponseEntity<ApiResponse<Message>> response = propertyService.delete(1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
