package org.agrotis.cad.utils;

import org.agrotis.cad.messages.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValidationUtils {

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
