package com.example.calculator.InputType;

public abstract class Operator extends Token {

  public Operator(String value) {
    super(value);
  }

  public abstract boolean hasLowerOrEqualPrecedenceTo(Token token);

  public abstract NumberValue calculate(NumberValue operand1, NumberValue operand2);
}
