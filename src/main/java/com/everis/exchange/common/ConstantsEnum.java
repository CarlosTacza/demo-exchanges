package com.everis.exchange.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConstantsEnum {
  HIGH("HIGH", "El valor mas alto"),
  MEDIUM("MEDIUM", "El promedio"),
  LOW("LOW", "El valor mas bajo");

  private String code;
  private String description;
}
