package org.agrotis.cad.messages;

import org.agrotis.cad.enums.MessageLogEnum;

public class MessageFactory {

  public static Message forQuery(Class<?> clazz, boolean isEmpty) {
    MessageLogEnum log = MessageLogEnum.fromClass(clazz);
    String text = isEmpty ? log.getEmptyMessage() : log.getSuccessMessage();
    Message.MessageTypeEnum type = isEmpty ? Message.MessageTypeEnum.I : Message.MessageTypeEnum.S;
    return new Message(text, type);
  }

  public static Message forError(Class<?> clazz, Exception e) {
    MessageLogEnum log = MessageLogEnum.fromClass(clazz);
    String message = log.getErrorMessage() + ": " + (e.getMessage() != null ? e.getMessage() : e.toString());
    return new Message(message, Message.MessageTypeEnum.E);
  }
  public static Message forSave(Class<?> clazz) {
    MessageLogEnum log = MessageLogEnum.fromClass(clazz);
    return new Message(log.getSaveMessage(), Message.MessageTypeEnum.S);
  }

  public static Message forDelete(Class<?> clazz) {
    MessageLogEnum log = MessageLogEnum.fromClass(clazz);
    return new Message(log.getDeleteMessage(), Message.MessageTypeEnum.S);
  }

  public static Message forSaveError(Class<?> clazz, Exception e) {
    MessageLogEnum log = MessageLogEnum.fromClass(clazz);
    String msg = log.getSaveErrorMessage() + ": " + (e.getMessage() != null ? e.getMessage() : e.toString());
    return new Message(msg, Message.MessageTypeEnum.E);
  }

  public static Message forInvalidId(Class<?> clazz) {
    MessageLogEnum log = MessageLogEnum.fromClass(clazz);
    return new Message(log.getInvalidIdMessage(), Message.MessageTypeEnum.A);
  }

  public static Message forNotFound(Class<?> clazz) {
    MessageLogEnum log = MessageLogEnum.fromClass(clazz);
    return new Message(log.getNotFoundMessage(), Message.MessageTypeEnum.A);
  }

  public static Message forDeleteError(Class<?> clazz, Exception e) {
    MessageLogEnum log = MessageLogEnum.fromClass(clazz);
    String msg = log.getDeleteErrorMessage() + ": " + (e.getMessage() != null ? e.getMessage() : e.toString());
    return new Message(msg, Message.MessageTypeEnum.E);
  }
  public static Message forDeleteError(Class<?> clazz, IllegalArgumentException e) {
    MessageLogEnum log = MessageLogEnum.fromClass(clazz);
    String msg = log.getDeleteErrorMessage() + ": " + (e.getMessage() != null ? e.getMessage() : e.toString());
    return new Message(msg, Message.MessageTypeEnum.E);
  }
}
