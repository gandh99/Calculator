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
      combinedTokens.add(inputToken);
    } else if (inputToken instanceof OpenParenthesis) {
      combinedTokens.add(this);
      combinedTokens.add(inputToken);
    }

    return combinedTokens;
  }

  /* Used when the subtraction operator is to be treated as a negation */
  public Token handleInputTokenAsUnary(Token token) {
    return new NumberValue(getValue() + token.getValue());
  }

  @Override
  public boolean hasLowerOrEqualPrecedenceTo(Token token) {
    return true;
  }

  @Override
  public NumberValue calculate(NumberValue operand1, NumberValue operand2) {
    double doubleResult = Double.valueOf(operand1.getValue()) - Double.valueOf(operand2.getValue());
    String stringResult = String.valueOf(doubleResult);

    return new NumberValue(stringResult);
  }
}
