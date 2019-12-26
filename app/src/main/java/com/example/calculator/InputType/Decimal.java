package com.example.calculator.InputType;

import java.util.ArrayList;
import java.util.List;

public class Decimal extends Token {

  public Decimal() {
    super(".");
  }

  @Override
  public boolean canAcceptToken(Token inputToken) {
    return (inputToken instanceof NumberValue);
  }

  @Override
  public List<Token> handleInputToken(Token inputToken) {
    List<Token> combinedTokens = new ArrayList<>();

    if (inputToken instanceof NumberValue) {
      Token newToken = new NumberValue(getValue() + inputToken.getValue());
      combinedTokens.add(newToken);
    }

    return combinedTokens;
  }
}
