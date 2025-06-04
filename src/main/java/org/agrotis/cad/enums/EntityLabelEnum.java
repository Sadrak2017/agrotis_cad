package org.agrotis.cad.enums;

import lombok.Getter;

@Getter
public enum EntityLabelEnum {
  PROPERTY("Property", "Propriedade"),
  LABORATORY("Laboratory", "Laboratório"),
  DEFAULT("NA", "Entidade");

  private final String className;
  private final String label;

  EntityLabelEnum(String className, String label) {
    this.className = className;
    this.label = label;
  }

}
