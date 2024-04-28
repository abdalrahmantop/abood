package applicationstack;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DriverLab5 extends Application {
	private GridPane gridPane = new GridPane();
	private BorderPane borderPane = new BorderPane();
	Label massg ;

	@Override
	public void start(Stage primaryStage) {
		gridPane.setHgap(10);
		gridPane.setVgap(5);
		gridPane.setPadding(new Insets(50, 50, 50, 50));

		Button inFix = new Button("Evaluate Infix");

		Button inFixtopreFix = new Button("Convert Infix to Prefix");
		Button inFixtopostFix = new Button("Convert Infix to Postfix");
		Button butCleaning = new Button("Clean");

		double buttonWidth = 200;
		inFix.setMinWidth(buttonWidth);

		butCleaning.setMinWidth(buttonWidth);
		inFixtopreFix.setMinWidth(buttonWidth);

		inFixtopostFix.setMinWidth(buttonWidth);
		double buttonHeight = 30;
		inFix.setMinHeight(buttonHeight);

		butCleaning.setMinHeight(buttonHeight);
		inFixtopreFix.setMinHeight(buttonHeight);
		inFixtopostFix.setMinHeight(buttonHeight);

		TextField textinFix = new TextField();
		textinFix.setPromptText( null );

		TextField texttopostFixtopreFix = new TextField();
		texttopostFixtopreFix.setPromptText("");
		textinFix.setMinWidth(buttonWidth);
		texttopostFixtopreFix.setPromptText( null );
		texttopostFixtopreFix.setMinWidth(buttonWidth);
		textinFix.setMinHeight(buttonHeight);
		massg = new Label ();
		texttopostFixtopreFix.setMinHeight(buttonHeight);
		texttopostFixtopreFix.setPromptText( null );
		gridPane.add(inFix, 0, 0);
		gridPane.add(textinFix, 1, 0);

		gridPane.add(inFixtopostFix, 0, 1);
		gridPane.add(inFixtopreFix, 1, 1);
		gridPane.add(texttopostFixtopreFix, 2, 1);
		gridPane.add(butCleaning, 0, 3);
		gridPane.add( massg , 0, 5);
	 
		 
		inFix.setOnAction(e -> {

			massg.setText("" + infix(textinFix.getText().trim()));
		});
		inFixtopostFix.setOnAction(e -> {
			massg.setText("" + infixToPostfix(texttopostFixtopreFix.getText().trim()));
		});
		inFixtopreFix.setOnAction(e -> {
			massg.setText("" + infixToPrefix(texttopostFixtopreFix.getText().trim()));
		});
		Font l = new Font(30);
		Label f1 = new Label("  Welcome   ");
		f1.setFont(l);
		Label f2 = new Label(" Hey, my friend ! This is a program that performs     ");
		Label f3 = new Label(" calculations and presents how the  To run the program ");
		Label f4 = new Label(" click the  button ");


		VBox vbox1 = new VBox(10);
		vbox1.getChildren().addAll(f1, f2, f3, f4);
		vbox1.setAlignment(Pos.CENTER);
		vbox1.setPadding(new Insets(60));
		borderPane.setTop(vbox1);
		borderPane.setCenter(gridPane);

		butCleaning.setOnAction(e -> {
			textinFix.clear();
			massg.setText(" ");

			texttopostFixtopreFix.clear();
		});

		gridPane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(borderPane, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle( " welcome ");// 
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

	private static Stack<Character> bracketStack = new Stack<>();

	public static StringBuilder checkBrackets() {
		StringBuilder Brackets = new StringBuilder();
		File file = new File("java.java");

		try (Scanner scan = new Scanner(file)) {
			while (scan.hasNext()) {
				String line = scan.nextLine();
				for (char ch : line.toCharArray()) {
					if (ch == '[' || ch == ']' || ch == '{' || ch == '}' || ch == ')' || ch == '(') {
						Brackets.append(ch);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Brackets;
	}

	public static boolean isBalanced(String str) {
		for (char ch : str.toCharArray()) {
			if (ch == '[' || ch == '(' || ch == '{') {
				bracketStack.push(ch);
			} else if (ch == ']' || ch == ')' || ch == '}') {
				if (bracketStack.isEmpty())
					return false;
				char lastBracket = bracketStack.pop();
				if (!isMatching(lastBracket, ch))
					return false;
			}
		}
		return bracketStack.isEmpty();
	}

	public GridPane getGridPane() {
		return gridPane;
	}

	public void setGridPane(GridPane gridPane) {
		this.gridPane = gridPane;
	}

	private int infix(String str) {
		Stack<Integer> value = new Stack<>();
		Stack<Character> operators = new Stack<>();

		char[] ch = str.toCharArray();

		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];

			if (Character.isDigit(c)) {
				int num = 0;
				while (i < ch.length && Character.isDigit(ch[i])) {
					num = num * 10 + (ch[i] - '0');
					i++;
				}
				i--;
				value.push(num);
			} else if (c == '(') {
				operators.push(c);
			} else if (c == ')') {
				while (!operators.isEmpty() && operators.peek() != '(') {
					Integer result = calculate(value, operators);
					if (result != null) {
						value.push(result);
					}
				}
				operators.pop();
			} else if (isOperator(c)) {
				while (!operators.isEmpty() && priority(c) <= priority(operators.peek())) {
					Integer result = calculate(value, operators);
					if (result != null) {
						value.push(result);
					}
				}
				operators.push(c);
			}
		}

		while (!operators.isEmpty()) {
			Integer result = calculate(value, operators);
			if (result != null) {
				value.push(result);
			}
		}

		Integer finalResult = value.pop();
		return finalResult != null ? finalResult : 0;
	}

	public static String infixToPostfix(String str) {
		Stack<Character> stack = new Stack<>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == ' ')
				continue;
			if (Character.isDigit(ch)) {
				sb.append(ch);
			} else if (ch == '(') {
				stack.push(ch);
			} else if (ch == ')') {
				while (!stack.isEmpty() && stack.peek() != '(') {
					sb.append(stack.pop());
				}
				stack.pop();
			} else {
				while (!stack.isEmpty() && priority(ch) <= priority(stack.peek())) {
					sb.append(stack.pop());
				}
				stack.push(ch);
			}
		}
		while (!stack.isEmpty()) {
			sb.append(stack.pop());
		}
		return sb.toString();
	}

	public static String infixToPrefix(String str) {
	    
	    StringBuilder sb = new StringBuilder();
	    for (int i = str.length() - 1; i >= 0; i--) {
	        char ch = str.charAt(i);
	        if (ch == '(') {
	            sb.append(')');
	        } else if (ch == ')') {
	            sb.append('(');
	        } else {
	            sb.append(ch);
	        }
	    }
	    String reversedStr = sb.toString();

	     
	    String postfix = infixToPostfix(reversedStr);

	    
	    sb.setLength(0);
	    for (int i = postfix.length() - 1; i >= 0; i--) {
	        sb.append(postfix.charAt(i));
	    }

	    return sb.toString();
	}


	private static boolean isMatching(char opening, char closing) {
		return (opening == '(' && closing == ')') || (opening == '[' && closing == ']')
				|| (opening == '{' && closing == '}');
	}

	private int calculate(Stack<Integer> value, Stack<Character> operators) {
		int b = value.pop();
		int a = value.pop();
		char operator = operators.pop();

		switch (operator) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '^':
			return (int) Math.pow(a, b);
		case '/':
			if (b == 0) {
				throw new RuntimeException("Cannot divide by zero");
			}
			return a / b;
		}
		return 0;
	}

	private boolean isOperator(char c) {
		return (c == '+' || c == '-' || c == '/' || c == '*' || c == '^');
	}

	private static int priority(char c) {
		switch (c) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		case '^':
			return 3;
		}
		return -1;
	}
}
