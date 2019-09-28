package com.example.calculator;

import com.example.calculator.InputType.NumberValue;
import com.example.calculator.InputType.OpenParenthesis;
import com.example.calculator.InputType.Operator;
import com.example.calculator.InputType.SubtractionOperator;
import com.example.calculator.InputType.Token;

import java.util.ArrayList;
import java.util.List;

public class Expression {
  private List<Token> inputExpression;

  public Expression() {
    inputExpression = new ArrayList<>();
  }

  public boolean isValidInput(Token newInputToken) {
    Token lastInputToken = getLastInputToken();

    // Lists the valid starting tokens
    if (lastInputToken == null) {
      return (newInputToken instanceof NumberValue
        || newInputToken instanceof SubtractionOperator
        || newInputToken instanceof OpenParenthesis);
    } else {
      return lastInputToken.canAcceptToken(newInputToken);
    }
  }

  private Token getLastInputToken() {
    return (inputExpression.isEmpty()) ? null : inputExpression.get(inputExpression.size() - 1);
  }

  // Assumes input token already validated
  public void updateExpression(Token token) {
    Token lastInputToken = getLastInputToken();

    if (lastInputToken == null) {
      inputExpression.add(token);
    } else if (lastInputToken instanceof SubtractionOperator
      && inputExpression.size() >= 2
      && inputExpression.get(inputExpression.size() - 2) instanceof Operator) {
      // This means the negative operator is to be treated as a unary operator
      inputExpression.remove(lastInputToken);
      inputExpression.add(((SubtractionOperator) lastInputToken).handleInputTokenAsUnary(token));
    } else {
      inputExpression.remove(lastInputToken);
      inputExpression.addAll(lastInputToken.handleInputToken(token));
    }
  }

  public String evaluate() throws IllegalArgumentException {
    CalculatorEngine engine = new CalculatorEngine();
    String result = engine.calculateExpression(inputExpression);

    return result;
  }

  public void clear() {
    inputExpression.removeAll(inputExpression);
  }
}
