package com.example.calculator.InputType;

import java.util.ArrayList;
import java.util.List;

public class AdditionOperator extends Operator {

  public AdditionOperator() {
    super("+");
  }

  @Override
  public boolean canAcceptToken(Token inputToken) {
    return (inputToken instanceof NumberValue
      || inputToken instanceof SubtractionOperator
      || inputToken instanceof OpenParenthesis);
  }

  @Override
  public List<Token> handleInputToken(Token inputToken) {
    List<Token> combinedTokens = new ArrayList<>();

    combinedTokens.add(this);
    combinedTokens.add(inputToken);

    return combinedTokens;
  }

  @Override
  public boolean hasLowerOrEqualPrecedenceTo(Token token) {
    return true;
  }

  @Override
  public NumberValue calculate(NumberValue operand1, NumberValue operand2) {
    double doubleResult = Double.valueOf(operand1.getValue()) + Double.valueOf(operand2.getValue());
    String stringResult = String.valueOf(doubleResult);

    return new NumberValue(stringResult);
  }
}
