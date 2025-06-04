package org.agrotis.cad;

import org.agrotis.cad.dto.LaboratoryDto;
import org.agrotis.cad.dto.input.LaboratoryInput;
import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.messages.Message;
import org.agrotis.cad.model.LaboratoryEntity;
import org.agrotis.cad.repositories.LaboratoryRepository;
import org.agrotis.cad.services.LaboratoryService;
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
class LaboratoryServiceTest {

  @InjectMocks
  private LaboratoryService laboratoryService;

  @Mock
  private LaboratoryRepository laboratoryRepository;

  private LaboratoryEntity labEntity;
  private LaboratoryInput labInput;

  @BeforeEach
  void setup() {
    labEntity = new LaboratoryEntity();
    labEntity.setId(1);
    labEntity.setDescription("Lab 1");

    labInput = new LaboratoryInput();
    labInput.setDescription("Lab 1");
  }

  @Test
  void testSearch_returnsLaboratories() {
    List<LaboratoryEntity> labs = Collections.singletonList(labEntity);
    when(laboratoryRepository.findAll()).thenReturn(labs);

    ResponseEntity<ApiResponse<List<LaboratoryDto>>> response = laboratoryService.search();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getData()).hasSize(1);
    assertThat(response.getBody().getData().get(0).getDescription()).isEqualTo("Lab 1");
  }


  @Test
  void testSave_withValidData_savesAndReturnsSuccess() {
    List<LaboratoryInput> inputs = List.of(labInput);

    // Mock comportamento estático de ValidationUtils
    try (MockedStatic<ValidationUtils> validationMock = Mockito.mockStatic(ValidationUtils.class)) {
      validationMock.when(() -> ValidationUtils.removeExistingEntriesByDescription(
          anyList(), any(), any(), any(), any(), anyString()
      )).thenReturn(new ArrayList<>());

      ResponseEntity<ApiResponse<Message>> response = laboratoryService.save(inputs);

      verify(laboratoryRepository, times(1)).saveAll(anyList());
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
  }

  @Test
  void testSave_withEmptyList_returnsBadRequest() {
    ResponseEntity<ApiResponse<Message>> response = laboratoryService.save(Collections.emptyList());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testDelete_withValidId_deletesSuccessfully() {
    when(laboratoryRepository.existsById(1)).thenReturn(true);

    ResponseEntity<ApiResponse<Message>> response = laboratoryService.delete(1);

    verify(laboratoryRepository).deleteById(1);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void testDelete_withNullId_returnsBadRequest() {
    ResponseEntity<ApiResponse<Message>> response = laboratoryService.delete(null);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testDelete_withNonexistentId_returnsBadRequest() {
    when(laboratoryRepository.existsById(99)).thenReturn(false);

    ResponseEntity<ApiResponse<Message>> response = laboratoryService.delete(99);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testDelete_withException_returnsInternalServerError() {
    when(laboratoryRepository.existsById(1)).thenReturn(true);
    doThrow(new RuntimeException()).when(laboratoryRepository).deleteById(1);

    ResponseEntity<ApiResponse<Message>> response = laboratoryService.delete(1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
