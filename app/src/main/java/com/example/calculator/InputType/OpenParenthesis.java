package com.example.calculator.InputType;

import java.util.ArrayList;
import java.util.List;

public class OpenParenthesis extends Parenthesis {

  public OpenParenthesis() {
    super("(");
  }

  @Override
  public boolean canAcceptToken(Token inputToken) {
    return (inputToken instanceof NumberValue
      || inputToken instanceof SubtractionOperator
      || inputToken instanceof Parenthesis);
  }

  @Override
  public List<Token> handleInputToken(Token inputToken) {
    List<Token> combinedTokens = new ArrayList<>();

    combinedTokens.add(this);
    combinedTokens.add(inputToken);

    return combinedTokens;
  }
}
