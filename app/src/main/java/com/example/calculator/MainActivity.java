package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculator.InputType.AdditionOperator;
import com.example.calculator.InputType.CloseParenthesis;
import com.example.calculator.InputType.Decimal;
import com.example.calculator.InputType.DivisionOperator;
import com.example.calculator.InputType.MultiplicationOperator;
import com.example.calculator.InputType.NumberValue;
import com.example.calculator.InputType.OpenParenthesis;
import com.example.calculator.InputType.Operator;
import com.example.calculator.InputType.Parenthesis;
import com.example.calculator.InputType.SubtractionOperator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
  TextView textViewFormula, textViewResult;
  Button[] numberButtons = new Button[10];
  Map<Button, Operator> operatorButtons = new HashMap<>();
  Map<Button, Parenthesis> parenthesisButtons = new HashMap<>();
  Button evaluateButton, deleteButton, decimalButton;
  Expression expression;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textViewFormula = findViewById(R.id.textViewFormula);
    textViewResult = findViewById(R.id.textViewResult);
    evaluateButton = findViewById(R.id.buttonEvaluate);
    deleteButton = findViewById(R.id.buttonDelete);
    decimalButton = findViewById(R.id.buttonDecimal);
    expression = new Expression();

    initNumberButtons();
    initOperatorButtons();
    initParenthesisButtons();
    initEvaluateButton();
    initDeleteButton();
    initDecimalButton();
  }

  private void initNumberButtons() {
    for (int i = 0; i < 10; i++) {
      String buttonID ="button" + i;
      int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
      numberButtons[i] = findViewById(resID);
      final NumberValue numberValue = new NumberValue(String.valueOf(i));

      numberButtons[i].setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (expression.isValidInput(numberValue)) {
            expression.updateExpression(numberValue);
            updateInput(numberValue.getValue());
          }
        }
      });
    }
  }

  private void initOperatorButtons() {
    operatorButtons.put((Button) findViewById(R.id.buttonPlus), new AdditionOperator());
    operatorButtons.put((Button) findViewById(R.id.buttonMinus), new SubtractionOperator());
    operatorButtons.put((Button) findViewById(R.id.buttonTimes), new MultiplicationOperator());
    operatorButtons.put((Button) findViewById(R.id.buttonDivide), new DivisionOperator());

    for (Map.Entry<Button, Operator> entry : operatorButtons.entrySet()) {
      final Button operatorButton = entry.getKey();
      final Operator operator = entry.getValue();

      operatorButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (expression.isValidInput(operator)) {
            expression.updateExpression(operator);
            updateInput(operator.getValue());
          }
        }
      });
    }
  }

  private void initParenthesisButtons() {
    parenthesisButtons.put((Button) findViewById(R.id.buttonOpenBracket), new OpenParenthesis());
    parenthesisButtons.put((Button) findViewById(R.id.buttonCloseBracket), new CloseParenthesis());

    for (Map.Entry<Button, Parenthesis> entry : parenthesisButtons.entrySet()) {
      Button parenthesisButton = entry.getKey();
      final Parenthesis parenthesis = entry.getValue();

      parenthesisButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (expression.isValidInput(parenthesis)) {
            expression.updateExpression(parenthesis);
            updateInput(parenthesis.getValue());
          }
        }
      });
    }
  }

  private void initEvaluateButton() {
    evaluateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          String result = expression.evaluate();
          textViewResult.setText(result);
        } catch (IllegalArgumentException | ClassCastException | EmptyStackException e) {
          textViewResult.setText("Invalid syntax");
        }
      }
    });
  }

  private void initDeleteButton() {
    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        expression.backspace();
        String oldFormula = textViewFormula.getText().toString();

        if (oldFormula.length() >= 1) {
          String newFormula = oldFormula.substring(0, oldFormula.length() - 1);
          textViewFormula.setText(newFormula);
        }
      }
    });

    deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        expression.clear();
        textViewFormula.setText("");
        textViewResult.setText("");
        return true;
      }
    });
  }

  private void updateInput(String input) {
    String newInput = textViewFormula.getText().toString() + input;
    textViewFormula.setText(newInput);
  }

  private void initDecimalButton() {
    final Decimal decimal = new Decimal();

    decimalButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (expression.isValidInput(decimal)) {
          expression.updateExpression(decimal);
          updateInput(decimal.getValue());
        }
      }
    });
  }
}
