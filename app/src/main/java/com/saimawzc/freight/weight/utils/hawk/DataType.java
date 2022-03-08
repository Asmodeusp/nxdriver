package com.saimawzc.freight.weight.utils.hawk;

enum DataType {
  OBJECT('0'),
  LIST('1'),
  MAP('2'),
  SET('3');

  private final char type;

  DataType(char type) {
    this.type = type;
  }

  char getType() {
    return type;
  }
}