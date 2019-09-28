package com.example.calculator.InputType;

import java.util.ArrayList;
import java.util.List;

public class NumberValue extends Token {

  public NumberValue(String value) {
    super(value);
  }

  @Override
  public boolean canAcceptToken(Token inputToken) {
    return (inputToken instanceof NumberValue
      || inputToken instanceof Operator
      || inputToken instanceof CloseParenthesis
      || (inputToken instanceof Decimal && !containsDecimal()));
  }

  @Override
  public List<Token> handleInputToken(Token inputToken) {
    List<Token> combinedTokens = new ArrayList<>();

    if (inputToken instanceof NumberValue
      || inputToken instanceof Decimal) {
      Token newToken = new NumberValue(getValue() + inputToken.getValue());
      combinedTokens.add(newToken);
    } else {
      combinedTokens.add(this);
      combinedTokens.add(inputToken);
    }

    return combinedTokens;
  }

  private boolean containsDecimal() {
    return getValue().contains(".");
  }

}
