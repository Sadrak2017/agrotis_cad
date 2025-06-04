package org.agrotis.cad.services;

import org.agrotis.cad.dto.LaboratoryDto;
import org.agrotis.cad.dto.input.LaboratoryInput;
import org.agrotis.cad.enums.EntityLabelEnum;
import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.messages.Message;
import org.agrotis.cad.factory.MessageFactory;
import org.agrotis.cad.model.LaboratoryEntity;
import org.agrotis.cad.repositories.LaboratoryRepository;
import org.agrotis.cad.utils.ValidationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LaboratoryService {
  @Autowired
  public LaboratoryRepository laboratoryRepository;

  public ResponseEntity<ApiResponse<List<LaboratoryDto>>> search() {
    List<LaboratoryEntity> laboratoryEntities = laboratoryRepository.findAll();
    List<LaboratoryDto> laboratoriesDTO = new ArrayList<>();
    try {
      laboratoryEntities.forEach(entity -> {
        LaboratoryDto laboratoryDto = new LaboratoryDto();
        BeanUtils.copyProperties(entity, laboratoryDto);
        laboratoriesDTO.add(laboratoryDto);
      });
      return ResponseEntity.ok(new ApiResponse<>(laboratoriesDTO, HttpStatus.OK)
          .message(
              MessageFactory.forQuery(LaboratoryDto.class, laboratoriesDTO.isEmpty())
          )
      );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
          ApiResponse.<List<LaboratoryDto>>info(HttpStatus.INTERNAL_SERVER_ERROR).message(
              MessageFactory.forError(LaboratoryDto.class, e)
          )
      );
    }
  }

  public ResponseEntity<ApiResponse<Message>> save(List<LaboratoryInput> laboratories) {
    if (laboratories.isEmpty())
      return ResponseEntity.badRequest().body(new ApiResponse<Message>()
          .message(MessageFactory.forQuery(LaboratoryDto.class, Boolean.TRUE))
      );

    List<Message> messages = ValidationUtils.removeExistingEntriesByDescription(
        laboratories,
        LaboratoryInput::getDescription,
        laboratoryRepository,
        LaboratoryEntity::getDescription,
        LaboratoryEntity::getCreated,
        EntityLabelEnum.LABORATORY.getLabel()
    );

    List<LaboratoryEntity> laboratoryEntities = new ArrayList<>();
    laboratories.forEach(input -> {
      LaboratoryEntity entity = new LaboratoryEntity();
      BeanUtils.copyProperties(input, entity);
      laboratoryEntities.add(entity);
    });
    try {
      laboratoryRepository.saveAll(laboratoryEntities);
      messages.add(0, MessageFactory.forSave(LaboratoryDto.class));
      return ResponseEntity.ok(new ApiResponse<Message>().messages(messages));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
          new ApiResponse<Message>().message(MessageFactory.forSaveError(LaboratoryDto.class, e))
      );
    }
  }

  public ResponseEntity<ApiResponse<Message>> delete(Integer id) {
    if (Objects.isNull(id))
      return ResponseEntity.badRequest().body(new ApiResponse<Message>()
          .message(MessageFactory.forInvalidId(LaboratoryDto.class))
      );
    if (!laboratoryRepository.existsById(id))
      return ResponseEntity.badRequest().body(new ApiResponse<Message>()
          .message(MessageFactory.forNotFound(LaboratoryDto.class))
      );
    try {
      laboratoryRepository.deleteById(id);
      return ResponseEntity.ok(new ApiResponse<Message>().message(MessageFactory.forDelete(LaboratoryDto.class)));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
          new ApiResponse<Message>().message(MessageFactory.forDeleteError(LaboratoryDto.class, e))
      );
    }
  }
}
