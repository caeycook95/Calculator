/**
 * IT 179, Fall 2015
 * @author Chung-Chih Li   10/6/2015
 * 
 *  Students should not modify this file.
 *  
 */
package myUtil; // remove before turning in.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  This program will read infix arithmetic expressions from a file and use Calculator class's static 
 *  method to do the following:
 *  
 *  1. Evaluate the expression directly from its infix notation;
 *  2. Convert to postfix notation;
 *  3. Evaluate the expression from its postfix notation.
 * 
 *  @author Chung-Chih Li 
 */

public class Asg5 {

	/**
	 * @param args[0], if provided, should be the name of file that contains arithmetic expressions in infix notation.
	 * Each line contains one expression.   
	 */
	public static void main(String[] args) {
			
		String  fname;
		Scanner inS = null;
		fname = (args.length != 0 ? args[0] : "exps.txt");		
		try {
			File file = new File(fname);
			inS = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println("Can't find file: "+fname);
			System.exit(-1);
		}
		
		String exp="";
		while (inS.hasNextLine()) {
			try {
				exp = inS.nextLine();
				String postf = Calculator.infixToPostfix(exp);
		
				System.out.printf("\n  Infix: %s",exp);
				System.out.printf("\nPostfix: %s",postf);
				
				double ivalue = Calculator.evaluate (exp);
				double pvalue = Calculator.evaluatePostfix(postf);
				System.out.printf("\n         %8.4f = %8.4f%n",ivalue,pvalue);
				
			} catch (ArithmeticException e) {
				System.out.printf("\nError: %s%n",e.getMessage());	
			} 
		}
		inS.close();
	}
}

