package org.agrotis.cad.services;

import org.agrotis.cad.dto.PropertyDto;
import org.agrotis.cad.dto.input.PropertyInput;
import org.agrotis.cad.enums.EntityLabelEnum;
import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.messages.Message;
import org.agrotis.cad.messages.MessageFactory;
import org.agrotis.cad.model.PropertyEntity;
import org.agrotis.cad.repositories.PropertyRepository;
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
public class PropertyService {

  @Autowired
  public PropertyRepository propertyRepository;

  public ResponseEntity<ApiResponse<List<PropertyDto>>> search() {
    List<PropertyEntity> propertiesEntities = propertyRepository.findAll();
    List<PropertyDto> propertiesDTO = new ArrayList<>();
    try {
      propertiesEntities.forEach(entity -> {
        PropertyDto propertyDto = new PropertyDto();
        BeanUtils.copyProperties(entity, propertyDto);
        propertiesDTO.add(propertyDto);
      });
      return ResponseEntity.ok(new ApiResponse<>(propertiesDTO, HttpStatus.OK)
          .message(
              MessageFactory.forQuery(PropertyDto.class, propertiesDTO.isEmpty())
          )
      );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
          ApiResponse.<List<PropertyDto>>info(HttpStatus.INTERNAL_SERVER_ERROR).message(
              MessageFactory.forError(PropertyDto.class, e)
          )
      );
    }
  }

  public ResponseEntity<ApiResponse<Message>> save(List<PropertyInput> properties) {
    if (properties.isEmpty())
      return ResponseEntity.badRequest().body(new ApiResponse<Message>()
          .message(MessageFactory.forQuery(PropertyDto.class, Boolean.TRUE))
      );


    List<Message> messages = ValidationUtils.removeExistingEntriesByDescription(
        properties,
        PropertyInput::getDescription,
        propertyRepository,
        PropertyEntity::getDescription,
        PropertyEntity::getCreated,
        EntityLabelEnum.PROPERTY.getLabel()
    );

    if (properties.isEmpty()) return ResponseEntity.badRequest().body(new ApiResponse<Message>().messages(messages));

    List<PropertyEntity> propertyEntities = new ArrayList<>();
    properties.forEach(input->{
      PropertyEntity entity = new PropertyEntity();
      BeanUtils.copyProperties(input, entity);
      propertyEntities.add(entity);
    });
    try {
      propertyRepository.saveAll(propertyEntities);
      messages.add(0, MessageFactory.forSave(PropertyDto.class));
      return ResponseEntity.ok(new ApiResponse<Message>().messages(messages));
    }catch (Exception e){
      return ResponseEntity.internalServerError().body(
          new ApiResponse<Message>().message(MessageFactory.forSaveError(PropertyDto.class, e))
      );
    }
  }

  public ResponseEntity<ApiResponse<Message>> delete(Integer id) {
    if (Objects.isNull(id))
      return ResponseEntity.badRequest().body(new ApiResponse<Message>()
          .message(MessageFactory.forInvalidId(PropertyDto.class))
      );
    if (!propertyRepository.existsById(id))
      return ResponseEntity.badRequest().body(new ApiResponse<Message>()
          .message(MessageFactory.forNotFound(PropertyDto.class))
      );
    try{
      propertyRepository.deleteById(id);
      return ResponseEntity.ok(new ApiResponse<Message>().message(MessageFactory.forDelete(PropertyDto.class)));
    }catch (Exception e){
      return ResponseEntity.internalServerError().body(
          new ApiResponse<Message>().message(MessageFactory.forDeleteError(PropertyDto.class, e))
      );
    }
  }
}
