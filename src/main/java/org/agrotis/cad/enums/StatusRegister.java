package org.agrotis.cad.enums;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import java.io.Serializable;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum StatusRegister implements Serializable {

  REGISTER_SUCESS(0, "Cadastro realizado com sucesso!"),
  REGISTER_FAILL_EMPTY_NAME(1, "Nome não informado!"),
  REGISTER_FAILL_EMPTY_DATE_INI(2, "Data inicial não informada!"),
  REGISTER_FAILL_EMPTY_DATE_FIM(3, "Data final não informada!"),
  REGISTER_FAILL_EMPTY_PROPERTY(4, "Propriedade informada inexistente na base!"),
  REGISTER_FAILL_EMPTY_LABORATORY(5, "Laboratório informado inexistente na base!"),
  REGISTER_FAILL_DATE_ORDER(6, "Data início não pode ser maior que data fim."),
  REGISTER_FAILL_DATE_INVALID(7, "Formato de data inválido. Use o formato yyyyMMdd."),
  NA(99, "Erro inesperado");

  private final Integer codigo;
  private final String message;

  StatusRegister(Integer codigo, String message) {
    this.codigo = codigo;
    this.message = message;
  }
}
