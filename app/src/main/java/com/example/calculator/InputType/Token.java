package com.example.calculator.InputType;

import java.util.List;

public abstract class Token {
  protected String value;

  public Token(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  };

  public abstract boolean canAcceptToken(Token inputToken);

  public abstract List<Token> handleInputToken(Token inputToken);
}
