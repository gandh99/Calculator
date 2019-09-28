package com.example.calculator.InputType;

import java.util.ArrayList;
import java.util.List;

public class SubtractionOperator extends Operator {

  public SubtractionOperator() {
    super("-");
  }

  @Override
  public boolean canAcceptToken(Token inputToken) {
    return (inputToken instanceof NumberValue
      || inputToken instanceof OpenParenthesis);
  }

  @Override
  public List<Token> handleInputToken(Token inputToken) {
    List<Token> combinedTokens = new ArrayList<>();

    if (inputToken instanceof NumberValue) {
      combinedTokens.add(this);
      combinedTokens.add(new NumberValue("1"));
      combinedTokens.add(new MultiplicationOperator());
      combinedTokens.add(inputToken);
//      Token newToken = new NumberValue(getValue() + inputToken.getValue());
//      combinedTokens.add(newToken);
    } else if (inputToken instanceof OpenParenthesis) {
      combinedTokens.add(this);
      combinedTokens.add(inputToken);
    }

    return combinedTokens;
  }

  @Override
  public boolean hasLowerPrecedenceThan(Token token) {
    return (token instanceof MultiplicationOperator
      || token instanceof DivisionOperator);
  }

  @Override
  public NumberValue calculate(NumberValue operand1, NumberValue operand2) {
    double doubleResult = Double.valueOf(operand1.getValue()) - Double.valueOf(operand2.getValue());
    String stringResult = String.valueOf(doubleResult);

    return new NumberValue(stringResult);
  }
}
