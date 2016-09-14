/*
 * File name: Calculator.java
 *
 * Programmer: Casey Cook
 * ULID: clcoo10
 *
 * Date: Oct 7, 2015
 *
 * Class: IT 179
 * Instructor: Dr. Li
 */
package myUtil;

import java.util.EmptyStackException;
import java.util.StringTokenizer;

/**
 * Calculates the results of String expressions
 *
 * @author Casey Cook
 *
 */
public class Calculator
{
	// String of operators that are used in the expressions
	private static final String operators = "()+-*/^";
	
	/**
	 * Converts a mathematical expression from infix to postfix form.
	 * @param exp String mathematical expression
	 * @return postfix form of the expression
	 */
	public static String infixToPostfix(String exp)
	{
		// StringTokenizer used to break up exp variable into tokens
		StringTokenizer tkn = new StringTokenizer(exp,"()+-*/^", true);
		// Stack that stores the postfix notation
		AStack<String> postfix = new AStack<String>();
		// Stack that stores operators that will be put into the postfix notation stack
		AStack<String> operator = new AStack<String>();
		// Holds the postfix form that will be returned
		String postfixForm = "";
		
		//While the String exp has more tokens
		while(tkn.hasMoreTokens()) 
		{
			// Puts next token into String variable item
			String item = tkn.nextToken(); 
			// If the item is an operator this if statement executes
			if(operators.indexOf(item) != -1) 
			{
				// If the operator stack is empty this if statement executes
				if(operator.empty()) 
				{
					// Puts operator into operator stack
					operator.push(item); 
				}
				// If the item is a closing parenthesis this if statement executes
				else if(item.equals(")")) 
				{
					//While the operator stack is not empty AND the operator on top doesn't equal an open parenthesis
					while((!operator.empty()) && (!operator.peek().equals("(")))
					{
						// Put the operator from the top of the stack into the postfix stack
						postfix.push(operator.pop()); 
						// If the operator stack is not empty AND the top of the operator stack is an open parenthesis
						if((!operator.empty()) && operator.peek().equals("("))
						{break;}
					}

					// Pops the open parenthesis which is on top of the operator stack
					operator.pop(); 
				}
				// Check the precedence of the item and the top of the operator stack
				else if(checkPrecedence(operator.peek(), item))
				{
					//Pushes item into operator stack if item's precedence is higher
					operator.push(item); 
				}
				else // Precedence of item is lower than top of operator stack
				{
					// While the operator stack is not empty AND the precedence of item is lower than the top of the stack
					while((!operator.empty())&& !checkPrecedence(operator.peek(), item))
					{
						// push the top of the operator stack into the postfix stack
						postfix.push(operator.pop()); 
					}
					// Pushes item into operator stack
					operator.push(item); 
				}
			}
			else
			{
				// Pushes a non operator into the postfix stack
				postfix.push(item); 
			}
		}
		//While the operator stack is not empty
		while(!operator.empty()) 
		{
			//Pushes remaining operators into postfix stack
			postfix.push(operator.pop());
		}
		
		//Empties postfix stack and makes it into a string
		while(!postfix.empty())
		{
			postfixForm = postfix.pop() + " " + postfixForm;
		}
		return postfixForm; // Returns postfix form of the expression
		
	}
	/**
	 * Evaluates a mathematical expression that is in infix form
	 * @param exp String mathematical expression
	 * @return the result of the expression
	 */
	public static double evaluate(String exp)
	{
		try // catches a NumberFormatException
		{
		// StringTokenizer used to break up exp variable into tokens
		StringTokenizer tkn = new StringTokenizer(exp,"()+-*/^", true);
		// Stack that stores number values
		AStack<String> value = new AStack<String>();
		// Stack that stores operators
		AStack<String> operator = new AStack<String>();
		//While the String exp has more tokens
		while(tkn.hasMoreTokens())
		{
			// Puts next token into String variable item
			String item = tkn.nextToken();
			// If the item is an operator this if statement executes
			if(operators.indexOf(item) != -1)
			{
				// If the operator stack is empty this if statement executes
				if(operator.empty())
				{
					// Puts operator into operator stack
					operator.push(item);
				}
				// If the item is a closing parenthesis this if statement executes
				else if(item.equals(")"))
				{
					//While the operator stack is not empty AND the operator on top doesn't equal an open parenthesis
					while((!operator.empty()) && (!operator.peek().equals("(")))
					{
						//Pops two numbers from the stack
						double num1 = Double.parseDouble(value.pop());
						double num2 = Double.parseDouble(value.pop());
						//Evaluates the two numbers and pushes result into value stack
						value.push(operation(operator.pop(), num2, num1));
					}
					// Pops the open parenthesis which is on top of the operator stack
					operator.pop();
				}
				// Check the precedence of the item and the top of the operator stack
				else if(checkPrecedence(operator.peek(), item))
				{
					//Pushes item into operator stack if item's precedence is higher
					operator.push(item);
				}
				else // Precedence of item is lower than top of operator stack
				{
					// While the operator stack is not empty AND the precedence of item is lower than the top of the stack
					while((!operator.empty())&& !checkPrecedence(operator.peek(), item))
					{
						//Pops two numbers from the stack
						double num1 = Double.parseDouble(value.pop());
						double num2 = Double.parseDouble(value.pop());
						//Evaluates the two numbers and pushes result into value stack
						value.push(operation(operator.pop(), num2, num1));
					}
					// Pushes item into operator stack
					operator.push(item);
				}
			}
			else
			{
				// Pushes a non operator into the postfix stack
				value.push(item);
			}
			}
		// Evaluates the rest of the numbers in the value stack until the operator stack is empty
		while(!operator.empty())
		{
			//Pops two numbers from the stack
			double num1 = Double.parseDouble(value.pop());
			double num2 = Double.parseDouble(value.pop());
			//Evaluates the two numbers and pushes result into value stack
			value.push(operation(operator.pop(), num2, num1));
		}
		
		// Returns the result of the mathematical expression
		return Double.parseDouble(value.pop());
		}
//		 Catches an exception if the expression is invalid
		catch (NumberFormatException e)
		{
			throw new ArithmeticException("Invalid expression");
		}
		catch (EmptyStackException e)
		{
			throw new ArithmeticException("Invalid expression");
		}
	}
	/**
	 * Evaluate a mathematical expression that is in postfix notation
	 * @param exp String mathematical expression
	 * @return Result of mathematical expression
	 */
	public static double evaluatePostfix(String exp)
	{
		try // catches a NumberFormatException
		{
		// StringTokenizer used to break up exp variable into tokens
		StringTokenizer tkn = new StringTokenizer(exp);
		// Stack that stores the numbers and result of the expression
		AStack<String> result = new AStack<String>();
		//While the String exp has more tokens
		while(tkn.hasMoreTokens())
		{
			// Puts next token into String variable item
			String item = tkn.nextToken();
			// If the item is an operator this if statement executes
			if(operators.indexOf(item) != -1)
			{
				//Pops two numbers from the stack
				double num1 = Double.parseDouble(result.pop());
				double num2 = Double.parseDouble(result.pop());
				//Evaluates the two numbers and pushes result into value stack
				result.push(operation(item, num2, num1));
			}
			else
			{
				// Pushes a non operator into the postfix stack
				result.push(item);
			}
		}
		// Returns the result of the mathematical expression
		return Double.parseDouble(result.pop());
		}
		// Catches an exception if the expression is invalid
		catch (NumberFormatException e)
		{
			throw new ArithmeticException("Invalid expression");
		}
		catch (EmptyStackException e)
		{
			throw new ArithmeticException("Invalid expression");
		}
	}
	
	/**
	 * Checks the precedence of two operators, one at the top of the operator stack and
	 * the other is the current token
	 * @param stack String operator that is in the stack
	 * @param expression String operator that is the current token
	 * @return True is the expression precedence is higher than the stack precedence
	 */
	private static boolean checkPrecedence(String stack, String expression)
	{
		// Precedence rank
		int positionStack; 
		int positionExpression;
		
		//Precedence of stack operator
		if(stack.equals("^") && expression.equals("^"))
		{
			return true;
		}
		if(stack.equals("("))
		{positionStack = -1;}
		else if(stack.equals("+") || stack.equals("-"))
		{
			positionStack = 1;
		}
		else if(stack.equals("*")|| stack.equals("/"))
		{
			positionStack = 2;
		}
		else
		{
			positionStack = 3;
		}
		
		//Precedence of expression operator
		if(expression.equals("+")|| expression.equals("-"))
		{
			positionExpression = 1;
		}
		else if(expression.equals("*") || expression.equals("/"))
		{
			positionExpression = 2;
		}
		else if(expression.equals("^"))
		{
			positionExpression = 3;
		}
		else
		{
			positionExpression = 4;
		}
		
		// Checks precedence ranks
		return positionStack < positionExpression;	
	}
	
	/**
	 * Evaluate two numbers and the operation depends on the passed operator variable
	 * @param operator String operator
	 * @param num1 double number
	 * @param num2 double number
	 * @return result of the operation
	 */
	private static String operation(String operator, double num1, double num2)
	{
		// result of the two numbers
		double result = 0;
		
		// Does appropriate operation depending on operator passed
		switch(operator)
		{
		case "+":
			result = num1 + num2;
			break;
		case "-":
			result = num1 - num2;
			break;
		case "*":
			result = num1 * num2;
			break;
		case "/":
				if(num2 == 0)
				{
					throw new ArithmeticException("Cannot divide by 0");
				}
			result = num1 / num2;
			break;
		case "^":
			result = Math.pow(num1, num2);
			break;
		default:
			System.out.print("Error operand not found.");
			break;
		}
		// Converts result into a String
		String stringResult = String.valueOf(result);
		
		return stringResult;
	}
}
