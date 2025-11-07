package com.example.exercise9;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

public class CalculatorApp extends Application {

    private String currentExpression = "";
    private String lastResult = "";
    private boolean justCalculated = false;

    @Override
    public void start(Stage stage) {
        Label expressionLabel = new Label();
        expressionLabel.setFont(Font.font("Consolas", 18));
        expressionLabel.setAlignment(Pos.CENTER_RIGHT);
        expressionLabel.setMaxWidth(Double.MAX_VALUE);

        Label resultLabel = new Label("0");
        resultLabel.setFont(Font.font("Consolas", 26));
        resultLabel.setAlignment(Pos.CENTER_RIGHT);
        resultLabel.setMaxWidth(Double.MAX_VALUE);

        String[][] buttons = {
                {"7", "8", "9", "/", "←"},
                {"4", "5", "6", "*", "C"},
                {"1", "2", "3", "-", "("},
                {"0", ".", "=", "+", ")"}
        };

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                String label = buttons[row][col];
                Button btn = new Button(label);
                btn.setMinSize(60, 50);
                btn.setStyle("-fx-font-size: 16px;");
                grid.add(btn, col, row);

                // Button event logic
                btn.setOnAction(e-> {
                    switch (label) {
                        case "=":
                            try {
                                if (currentExpression.isEmpty()) return;
                                double res = evaluate(currentExpression);
                                lastResult = String.format("%.10f", res).replaceAll("\\.?0+$", "");
                                resultLabel.setText("= " + lastResult);
                                justCalculated = true;
                            } catch (Exception ex) {
                                resultLabel.setText("Error");
                                justCalculated = false;
                            }
                            break;

                        case "C":
                            currentExpression = "";
                            expressionLabel.setText("");
                            resultLabel.setText("0");
                            justCalculated = false;
                            break;

                        case "←":
                            if (!currentExpression.isEmpty()) {
                                currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
                                expressionLabel.setText(currentExpression);
                            }
                            break;

                        default:
                            // finish
                            if (justCalculated) {
                                if (isOperator(label)) {
                                    currentExpression = lastResult + label;
                                    expressionLabel.setText(currentExpression);
                                } else {
                                    currentExpression = label;
                                    expressionLabel.setText(currentExpression);
                                    resultLabel.setText("0");
                                }
                                justCalculated = false;
                            } else {
                                // "("
                                if (label.equals("(")) {
                                    if (currentExpression.matches("\\d+(\\.\\d+)?")) {
                                        // only number → clear
                                        currentExpression = "(";
                                    } else if (!currentExpression.isEmpty()) {
                                        // find the nearest operator
                                        int insertPos = findLastOperatorIndex(currentExpression);
                                        if (insertPos == -1) {
                                            // directly add
                                            currentExpression += "(";
                                        } else {
                                            currentExpression = currentExpression.substring(0, insertPos + 1)
                                                    + "("
                                                    + currentExpression.substring(insertPos + 1);
                                        }
                                    } else {
                                        // empty
                                        currentExpression = "(";
                                    }
                                } else {
                                    // normal
                                    currentExpression += label;
                                }
                                expressionLabel.setText(currentExpression);
                            }
                    }
                });
            }
        }

        VBox displayBox = new VBox(5, expressionLabel, resultLabel);
        displayBox.setAlignment(Pos.CENTER_RIGHT);
        displayBox.setPadding(new Insets(10, 20, 0, 20));

        VBox root = new VBox(15, displayBox, grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f2f2f2;");

        Scene scene = new Scene(root, 370, 400);
        stage.setScene(scene);
        stage.setTitle("Calculator App");
        stage.show();
    }

    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("(") || s.equals(")");
    }

    private int findLastOperatorIndex(String expr) {
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(') {
                return i;
            }
        }
        return -1;
    }


    private double evaluate(String expression) {
        expression = expression.replaceAll("\\s+", "");
        if (expression.isEmpty()) throw new IllegalArgumentException("Empty");

        // Unary minus
        StringBuilder fixed = new StringBuilder();
        char prev = '(';
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '-' && (i == 0 || prev == '(' || prev == '+' || prev == '-' || prev == '*' || prev == '/')) {
                fixed.append('0');
            }
            fixed.append(c);
            prev = c;
        }

        List<String> rpn = toRPN(fixed.toString());
        return evalRPN(rpn);
    }

    private List<String> toRPN(String s) {
        List<String> output = new ArrayList<>();
        Deque<Character> ops = new ArrayDeque<>();

        int n = s.length();
        for (int i = 0; i < n; ) {
            char c = s.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                int j = i + 1;
                while (j < n && (Character.isDigit(s.charAt(j)) || s.charAt(j) == '.')) j++;
                output.add(s.substring(i, j));
                i = j;
                continue;
            }

            if (c == '(') {
                ops.push(c);
                i++;
                continue;
            }

            if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(')
                    output.add(String.valueOf(ops.pop()));
                if (ops.isEmpty() || ops.peek() != '(')
                    throw new IllegalArgumentException("Mismatched parentheses");
                ops.pop();
                i++;
                continue;
            }

            if (c == '+' || c == '-' || c == '*' || c == '/') {
                int prec = (c == '+' || c == '-') ? 1 : 2;
                while (!ops.isEmpty()) {
                    char top = ops.peek();
                    int topPrec = (top == '+' || top == '-') ? 1 : (top == '*' || top == '/') ? 2 : 0;
                    if (top != '(' && topPrec >= prec)
                        output.add(String.valueOf(ops.pop()));
                    else break;
                }
                ops.push(c);
                i++;
                continue;
            }

            throw new IllegalArgumentException("Invalid char: " + c);
        }

        while (!ops.isEmpty()) {
            char op = ops.pop();
            if (op == '(' || op == ')')
                throw new IllegalArgumentException("Mismatched parentheses");
            output.add(String.valueOf(op));
        }
        return output;
    }

    private double evalRPN(List<String> rpn) {
        Deque<Double> st = new ArrayDeque<>();
        for (String t : rpn) {
            switch (t) {
                case "+":
                    st.push(st.pop() + st.pop());
                    break;
                case "-": {
                    double b = st.pop(), a = st.pop();
                    st.push(a - b);
                    break;
                }
                case "*":
                    st.push(st.pop() * st.pop());
                    break;
                case "/": {
                    double b = st.pop(), a = st.pop();
                    if (b == 0.0) throw new ArithmeticException("Divide by zero");
                    st.push(a / b);
                    break;
                }
                default:
                    st.push(Double.parseDouble(t));
            }
        }
        if (st.size() != 1) throw new IllegalArgumentException("Bad expression");
        return st.pop();
    }

    public static void main(String[] args) {
        launch();
    }
}
