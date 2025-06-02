package org.agrotis.cad.enums;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.agrotis.cad.model.Register;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum StatusRegister implements Serializable {

  REGISTER_SUCESS(0, "Cadastro realizado com sucesso!"),

  REGISTER_FAILL_EMPTY_NAME(1, "Cadastro incompleto! Nome não informado!"),
  REGISTER_FAILL_EMPTY_DATE_INI(2, "Cadastro incompleto! Data inicial não informada!"),
  REGISTER_FAILL_EMPTY_DATE_FIM(3, "Cadastro incompleto! Data final não informada!"),
  REGISTER_FAILL_EMPTY_PROPERTY(4, "Cadastro incompleto! Propriedade não informada!"),
  REGISTER_FAILL_EMPTY_LABORATORY(5, "Cadastro incompleto! Laborátório não informado!"),
  NA(99, "Erro inesperado");

  private final Integer codigo;
  private final String message;

  public static List<StatusRegister> status(Register register) {

    return null;
  }
}
