package com.example.calculator;

public class NumericalResultFormatter {

  public static String formatResult(String result) {
    if (result.matches("-?[0-9]+.0")) {
      // Matches cases like XXXX.0
      return result.substring(0, result.length() - 2);
    }

    return result;
  }
}
