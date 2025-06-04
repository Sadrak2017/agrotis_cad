package org.agrotis.cad.enums;

import lombok.Getter;

@Getter
public enum MessageLogEnum {
  PROPERTY(
      "Property",
      "Propriedades retornadas com sucesso!",
      "Nenhuma propriedade encontrada.",
      "Falha ao consultar propriedades.",
      "Propriedade salva com sucesso!",
      "Propriedade atualizada com sucesso!",
      "Propriedade removida com sucesso!",
      "Falha ao salvar propriedade.",
      "Falha ao atualizar propriedade.",
      "Falha ao remover propriedade.",
      "Id inválido. Informe um valor numérico inteiro.",
      "Propriedade inexistente para o id informado."
  ),

  LABORATORY(
      "Laboratory",
      "Laboratórios retornados com sucesso!",
      "Nenhum laboratório encontrado.",
      "Falha ao consultar laboratórios.",
      "Laboratório salvo com sucesso!",
      "Laboratório atualizado com sucesso!",
      "Laboratório removido com sucesso!",
      "Falha ao salvar laboratório.",
      "Falha ao atualizar laboratório.",
      "Falha ao remover laboratório.",
      "Id inválido. Informe um valor numérico inteiro.",
      "Laboratório inexistente para o id informado."
  ),

  DEFAULT(
      "NA",
      "Consulta realizada com sucesso!",
      "Nenhum dado encontrado.",
      "Falha ao realizar consulta.",
      "Cadastro realizado com sucesso!",
      "Atualização realizada com sucesso!",
      "Remoção realizada com sucesso!",
      "Falha ao salvar dado.",
      "Falha ao atualizar dado.",
      "Falha ao remover dado.",
      "Id inválido.",
      "Registro inexistente para o id informado."
  ),
  REGISTER(
      "Register",
      "Registros retornados com sucesso!",
      "Nenhum registro encontrado.",
      "Falha ao consultar registros.",
      "Registro salvo com sucesso!",
      "Registro atualizado com sucesso!",
      "Registro removido com sucesso!",
      "Falha ao salvar registro.",
      "Falha ao atualizar registro.",
      "Falha ao remover registro.",
      "Id inválido. Informe um valor numérico inteiro.",
      "Registro inexistente para o UUID informado."
  );

  private final String className;
  private final String successMessage;
  private final String emptyMessage;
  private final String errorMessage;

  private final String saveMessage;
  private final String updateMessage;
  private final String deleteMessage;

  private final String saveErrorMessage;
  private final String updateErrorMessage;
  private final String deleteErrorMessage;

  private final String invalidIdMessage;
  private final String notFoundMessage;

  MessageLogEnum(
      String className,
      String successMessage,
      String emptyMessage,
      String errorMessage,
      String saveMessage,
      String updateMessage,
      String deleteMessage,
      String saveErrorMessage,
      String updateErrorMessage,
      String deleteErrorMessage,
      String invalidIdMessage,
      String notFoundMessage
  ) {
    this.className = className;
    this.successMessage = successMessage;
    this.emptyMessage = emptyMessage;
    this.errorMessage = errorMessage;
    this.saveMessage = saveMessage;
    this.updateMessage = updateMessage;
    this.deleteMessage = deleteMessage;
    this.saveErrorMessage = saveErrorMessage;
    this.updateErrorMessage = updateErrorMessage;
    this.deleteErrorMessage = deleteErrorMessage;
    this.invalidIdMessage = invalidIdMessage;
    this.notFoundMessage = notFoundMessage;
  }

  public static MessageLogEnum fromClass(Class<?> clazz) {
    for (MessageLogEnum value : values()) {
      if (value.className.equalsIgnoreCase(clazz.getSimpleName())) {
        return value;
      }
    }
    return DEFAULT;
  }
}
