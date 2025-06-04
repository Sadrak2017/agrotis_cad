package org.agrotis.cad.services;

import org.agrotis.cad.dto.LaboratoryDto;
import org.agrotis.cad.dto.PropertyDto;
import org.agrotis.cad.dto.RegisterDto;
import org.agrotis.cad.dto.ShippingRegister;
import org.agrotis.cad.dto.input.RegisterInput;
import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.messages.Message;
import org.agrotis.cad.factory.MessageFactory;
import org.agrotis.cad.model.LaboratoryEntity;
import org.agrotis.cad.model.PropertyEntity;
import org.agrotis.cad.model.RegisterEntity;
import org.agrotis.cad.repositories.LaboratoryRepository;
import org.agrotis.cad.repositories.PropertyRepository;
import org.agrotis.cad.repositories.RegisterRepository;
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
public class RegisterService {

  @Autowired
  public RegisterRepository registerRepository;
  @Autowired
  public PropertyRepository propertyRepository;
  @Autowired
  public LaboratoryRepository laboratoryRepository;

  public ResponseEntity<ApiResponse<ShippingRegister>> search() {
    List<RegisterEntity> registerEntities = registerRepository.findAll();
    List<RegisterDto> registerDtos = new ArrayList<>();
    try {
      registerEntities.forEach(entity -> {
        RegisterDto registerDto = new RegisterDto();
        BeanUtils.copyProperties(entity, registerDto);
        PropertyDto propertyDto = new PropertyDto();
        LaboratoryDto laboratoryDto = new LaboratoryDto();
        BeanUtils.copyProperties(entity.getPropertyEntity(), propertyDto);
        BeanUtils.copyProperties(entity.getLaboratoryEntity(), laboratoryDto);
        registerDto.setLaboratory(laboratoryDto);
        registerDto.setInfosProperty(propertyDto);
        registerDtos.add(registerDto);
      });
      ShippingRegister shippingRegister = new ShippingRegister();
      shippingRegister.setCadastro(registerDtos);
      return ResponseEntity.ok(new ApiResponse<>(shippingRegister, HttpStatus.OK)
          .message(
              MessageFactory.forQuery(ShippingRegister.class, registerDtos.isEmpty())
          )
      );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
          ApiResponse.<ShippingRegister>info(HttpStatus.INTERNAL_SERVER_ERROR).message(
              MessageFactory.forError(ShippingRegister.class, e)
          )
      );
    }
  }

  public ResponseEntity<ApiResponse<Message>> save(RegisterInput registerInput) {
    LaboratoryEntity lab = laboratoryRepository.findById(registerInput.getCodLaboratory()).orElse(null);
    PropertyEntity prop = propertyRepository.findById(registerInput.getCodProperty()).orElse(null);

    List<Message> messages = ValidationUtils.validateRegisterInput(registerInput, lab, prop);

    if (!messages.isEmpty()) return ResponseEntity.badRequest().body(new ApiResponse<Message>().messages(messages));

    RegisterEntity entity = new RegisterEntity();
    BeanUtils.copyProperties(registerInput, entity);
    entity.setLaboratoryEntity(lab);
    entity.setPropertyEntity(prop);
    try {
      registerRepository.save(entity);
      messages.add(0, MessageFactory.forSave(RegisterDto.class));
      return ResponseEntity.ok(new ApiResponse<Message>().messages(messages));
    }catch (Exception e){
      return ResponseEntity.internalServerError().body(
          new ApiResponse<Message>().message(MessageFactory.forSaveError(RegisterDto.class, e))
      );
    }
  }

  public ResponseEntity<ApiResponse<Message>> delete(String uuid) {
    if (Objects.isNull(uuid) || uuid.isEmpty())
      return ResponseEntity.badRequest().body(new ApiResponse<Message>()
          .message(MessageFactory.forInvalidId(RegisterDto.class))
      );
    if (!registerRepository.existsById(uuid))
      return ResponseEntity.badRequest().body(new ApiResponse<Message>()
          .message(MessageFactory.forNotFound(RegisterDto.class))
      );
    try{
      registerRepository.deleteById(uuid);
      return ResponseEntity.ok(new ApiResponse<Message>().message(MessageFactory.forDelete(RegisterDto.class)));
    }catch (Exception e){
      return ResponseEntity.internalServerError().body(
          new ApiResponse<Message>().message(MessageFactory.forDeleteError(RegisterDto.class, e))
      );
    }
  }
}
