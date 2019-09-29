package com.example.calculator;

import com.example.calculator.InputType.NumberValue;
import com.example.calculator.InputType.OpenParenthesis;
import com.example.calculator.InputType.Operator;
import com.example.calculator.InputType.Parenthesis;
import com.example.calculator.InputType.Token;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class CalculatorEngine {

  public String calculateExpression(List<Token> inputInfixExpression) throws IllegalArgumentException {
    Queue<Token> rpnQueue;

    rpnQueue = infixToRPN(inputInfixExpression);

    return evaluateRPN(rpnQueue);
  }

  private String evaluateRPN(Queue<Token> rpnQueue) throws IllegalArgumentException {
    Stack<Token> operatorStack = new Stack<>();

    if (rpnQueue.isEmpty()) {
      throw new IllegalArgumentException("Invalid syntax");
    }

    while (!rpnQueue.isEmpty()) {
      Token token = rpnQueue.remove();

      if (isOperator(token)) {
        NumberValue operand1, operand2, result;
        try {
          operand2 = (NumberValue) operatorStack.pop();
          operand1 = (NumberValue) operatorStack.pop();

          result = ((Operator) token).calculate(operand1, operand2);
        } catch (ClassCastException  e) {
          throw new ClassCastException("Invalid syntax");
        } catch (NumberFormatException e) {
          throw new NumberFormatException("Invalid syntax");
        }

        operatorStack.push(result);
      } else if (isNumber(token)) {
        operatorStack.push(token);
      } else {
        throw new IllegalArgumentException("Invalid syntax");
      }
    }

    return operatorStack.pop().getValue();
  }

  /* Convert infix notation to reverse Polish notation via Shunting-yard algorithm */
  private Queue<Token> infixToRPN(List<Token> inputExpression) throws IllegalArgumentException {
    Stack<Token> operatorStack = new Stack<>();
    Queue<Token> outputQueue = new LinkedList<>();

    for (Token token : inputExpression) {

      if (isNumber(token)) {
        outputQueue.add(token);
      } else if (isOperator(token)) {
        while (!operatorStack.empty()
          && ((Operator) token).hasLowerOrEqualPrecedenceTo(operatorStack.peek())) {
          Token operator = operatorStack.pop();
          outputQueue.add(operator);
        }

        operatorStack.push(token);
      } else if (isParenthesis(token)) {
        if (isOpenParenthesis(token)) {
          operatorStack.push(token);
        } else {
          while (!operatorStack.empty() && !isOpenParenthesis(operatorStack.peek())) {
            Token operator = operatorStack.pop();
            outputQueue.add(operator);
          }

          // There should now be a left parenthesis at the top of the stack. Pop and discard it
          if (!operatorStack.empty()) {
            operatorStack.pop();
          } else {
            // Happens when there are more closing brackets than open brackets
            throw new IllegalArgumentException("Invalid syntax");
          }
        }
      }
    }

    while (!operatorStack.empty()) {
      Token operator = operatorStack.pop();
      outputQueue.add(operator);
    }

    return outputQueue;
  }

  private boolean isOpenParenthesis(Token token) {
    return token instanceof OpenParenthesis;
  }

  private boolean isNumber(Token token) {
    return token instanceof NumberValue;
  }

  private boolean isOperator(Token token) {
    return token instanceof Operator;
  }

  private boolean isParenthesis(Token token) {
    return token instanceof Parenthesis;
  }
}
