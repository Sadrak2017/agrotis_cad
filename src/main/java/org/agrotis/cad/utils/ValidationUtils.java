package org.agrotis.cad.utils;

import org.agrotis.cad.dto.input.RegisterInput;
import org.agrotis.cad.enums.StatusRegister;
import org.agrotis.cad.factory.MessageFactory;
import org.agrotis.cad.messages.Message;
import org.agrotis.cad.model.LaboratoryEntity;
import org.agrotis.cad.model.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValidationUtils {

  public static List<Message> validateRegisterInput(
      RegisterInput input,
      LaboratoryEntity laboratoryEntity,
      PropertyEntity propertyEntity
  ) {
    List<Message> messages = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE; // yyyyMMdd

    if (input.getName() == null || input.getName().trim().isEmpty()) {
      messages.add(MessageFactory.forStatusRegister(StatusRegister.REGISTER_FAILL_EMPTY_NAME));
    }

    if (input.getData_ini() == null) {
      messages.add(MessageFactory.forStatusRegister(StatusRegister.REGISTER_FAILL_EMPTY_DATE_INI));
    }

    if (input.getDate_fim() == null) {
      messages.add(MessageFactory.forStatusRegister(StatusRegister.REGISTER_FAILL_EMPTY_DATE_FIM));
    }

    if (input.getData_ini() != null && input.getDate_fim() != null) {
      try {
        LocalDate dataIni = LocalDate.parse(input.getData_ini().toString(), formatter);
        LocalDate dataFim = LocalDate.parse(input.getDate_fim().toString(), formatter);
        if (dataIni.isAfter(dataFim)) {
          messages.add(MessageFactory.forStatusRegister(StatusRegister.REGISTER_FAILL_DATE_ORDER));
        }
      } catch (DateTimeParseException e) {
        messages.add(MessageFactory.forStatusRegister(StatusRegister.REGISTER_FAILL_DATE_INVALID));
      }
    }

    if (laboratoryEntity == null) {
      messages.add(MessageFactory.forStatusRegister(StatusRegister.REGISTER_FAILL_EMPTY_LABORATORY));
    }

    if (propertyEntity == null) {
      messages.add(MessageFactory.forStatusRegister(StatusRegister.REGISTER_FAILL_EMPTY_PROPERTY));
    }
    return messages;
  }

  public static <T, E> List<Message> removeExistingEntriesByDescription(
      List<T> inputs,
      Function<T, String> getDescription,
      JpaRepository<E, ?> repository,
      Function<E, String> getEntityDescription,
      Function<E, Timestamp> getEntityCreated,
      String entityLabel
  ) {
    if (inputs.isEmpty()) return Collections.emptyList();

    List<String> descriptions = inputs.stream()
        .map(getDescription)
        .collect(Collectors.toList());

    List<E> existingEntities = findByDescriptionIn(repository, descriptions);

    List<Message> messages = existingEntities.stream()
        .map(entity -> new Message(
            String.format(
                "%s '%s' cadastrado em %s.",
                entityLabel,
                getEntityDescription.apply(entity),
                getEntityCreated.apply(entity).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            ),
            Message.MessageTypeEnum.A
        ))
        .collect(Collectors.toList());

    Set<String> existingDescriptions = existingEntities.stream()
        .map(getEntityDescription)
        .collect(Collectors.toSet());

    inputs.removeIf(input -> existingDescriptions.contains(getDescription.apply(input)));

    return messages;
  }

  @SuppressWarnings("unchecked")
  private static <E> List<E> findByDescriptionIn(JpaRepository<E, ?> repository, List<String> descriptions) {
    try {
      return (List<E>) repository.getClass()
          .getMethod("findByDescriptionIn", List.class)
          .invoke(repository, descriptions);
    } catch (Exception e) {
      throw new RuntimeException("O repositório deve implementar findByDescriptionIn(List<String>)", e);
    }
  }
}
