package com.example.calculator.InputType;

import java.util.ArrayList;
import java.util.List;

public class CloseParenthesis extends Parenthesis {

  public CloseParenthesis() {
    super(")");
  }

  @Override
  public boolean canAcceptToken(Token inputToken) {
    return (inputToken instanceof Operator
      || inputToken instanceof CloseParenthesis);
  }

  @Override
  public List<Token> handleInputToken(Token inputToken) {
    List<Token> combinedTokens = new ArrayList<>();

    combinedTokens.add(this);
    combinedTokens.add(inputToken);

    return combinedTokens;
  }
}
