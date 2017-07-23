/*
 * Author: Sylvain Slaton
 * Date: 20170720
 */
package fracCalc;
import java.util.Scanner;
public class FracCalc {

	public static void main(String[] args) 
	{
		System.out.println("Enter an expression to evaluate and press Return. Enter \"quit\" to exit");
		
		// TODO: Read the input from the user and call produceAnswer with an equation
		Scanner scan = new Scanner(System.in);
		
		String input = scan.nextLine();
		while(input.toLowerCase() != "quit") {
			
			System.out.println(produceAnswer(input));

			input = scan.nextLine();
		}
		
		scan.close();
	}

	// ** IMPORTANT ** DO NOT DELETE THIS FUNCTION.  This function will be used to test your code
	// This function takes a String 'input' and produces the result
	//
	// input is a fraction string that needs to be evaluated.  For your program, this will be the user input.
	//      e.g. input ==> "1/2 + 3/4"
	//        
	// The function should return the result of the fraction after it has been calculated
	//      e.g. return ==> "1_1/4"
	public static String produceAnswer(String input)
	{ 
		String remainder = input;
		String leftOperand = getFirstElement(remainder);	
		remainder = remainder.substring(leftOperand.length() + 1);		
		
		String operator = getFirstElement(remainder);		
		remainder = remainder.substring(operator.length() + 1);
		
		String rightOperand = remainder;

		return Calculate(leftOperand, rightOperand, operator);
	}
	//   1/2 + 3/4 + 1/3 + 8/5
	
	private static String Calculate(String operand1, String operand2, String operator) {
		operand1 = parseOperand(operand1);
		operand2 = parseOperand(operand2);
		
		int w1 = getWhole(operand1);
		int n1 = getNumerator(operand1);
		int d1 = getDenominator(operand1);
		
		int w2 = getWhole(operand2);
		int n2 = getNumerator(operand2);
		int d2 = getDenominator(operand2);
		
		//handle negative
		if(w1 < 0)
			n1 *= -1;
		if(w2 < 0)
			n2 *= -1;

		//simplify
		n1 = w1 * d1 + n1;
		n2 = w2 * d2 + n2;	
		
		if(operator.equals("+"))
			return Add(n1, d1, n2, d2);
		if(operator.equals("-"))
			return Subtract(n1, d1, n2, d2);
		if(operator.equals("*"))
			return Multiply(n1, d1, n2, d2);
		if(operator.equals("/"))
			return Divide(n1, d1, n2, d2);
		
		return "Invalid operation";
	}
	
	//Add 2 fractions and return the result as string
	private static String Add(int n1, int d1, int n2, int d2) {		
		int resultNumerator = n1 * d2 + n2 * d1;
		int resultDenominator = d1 * d2;
		
		return fractionAsStringSimplified(resultNumerator, resultDenominator);
	}

	//Subtracts 2 fractions and return the result as string
	private static String Subtract(int n1, int d1, int n2, int d2) {
		int resultNumerator = n1 * d2 - n2 * d1;
		int resultDenominator = d1 * d2;
		
		return fractionAsStringSimplified(resultNumerator, resultDenominator);
	}
	
	//Multiply 2 fractions and return the result as string
	private static String Multiply(int n1, int d1, int n2, int d2) {		
		int resultNumerator = n1 * n2;
		int resultDenominator = d1 * d2;
		
		return fractionAsStringSimplified(resultNumerator, resultDenominator);
	}	

	//Divide 2 fractions and return the result as string
	private static String Divide(int n1, int d1, int n2, int d2) {		
		int resultNumerator = n1 * d2;
		int resultDenominator = d1 * n2;
		
		return fractionAsStringSimplified(resultNumerator, resultDenominator);
	}
	
	private static int getWhole(String operand) {
		String whole = operand.substring(0, operand.indexOf("n"));
		return Integer.parseInt(whole);
	}

	private static int getNumerator(String operand) {
		String numerator = operand.substring(operand.indexOf("n") + 1, operand.indexOf("d"));
		return Integer.parseInt(numerator);
	}
	
	private static int getDenominator(String operand) {
		String denominator = operand.substring(operand.indexOf("d") + 1);
		return Integer.parseInt(denominator);
	}
	
	private static String getFirstElement(String input) {
		return input.substring(0, input.indexOf(" "));	
	}
	
	private static String parseOperand(String expression) {
		String whole = "0";
		String numerator = "0";
		String denominator = "1";
		
		int slashIndex = expression.indexOf("/");		
		
		if(slashIndex > -1) { //there's a fraction
			int underscoreIndex = expression.indexOf("_");
			int beginFraction = 0;

			if(underscoreIndex > -1) { //there's a whole
				whole = expression.substring(0, underscoreIndex);
				beginFraction = underscoreIndex + 1;
			}
			
			numerator = expression.substring(beginFraction, slashIndex);		
			denominator = expression.substring(slashIndex + 1);
		}
		else {
			whole = expression;
		}		
		
		return whole+"n"+numerator+"d"+denominator;
	}
	
	private static String fractionAsStringSimplified(int numerator, int denominator) {	
		//Cancel double negatives
		if(numerator < 0 && denominator < 0) {
			numerator *= -1;
			denominator *= -1;
		}
		else if(denominator < 0 && numerator > 0) { //make sure negative sign is on numerator, makes it easier
			numerator *= -1;
			denominator *= -1; 
		}
		
		//Simplify
		if(numerator != 0 && denominator != 0) {
			int gcd = Math.abs(GetGCD(numerator, denominator));
			numerator = numerator / gcd;
			denominator = denominator / gcd;
		}
				
		//extract whole
		int whole = 0;
		if(numerator > 0) {
			while(numerator >= denominator) {
				whole += 1;
				numerator = numerator - denominator;
			}
		}
		else if(numerator < 0) {
			while(numerator <= denominator * -1) {
				whole -= 1;
				numerator = numerator + denominator;
			}
		}
								
		return fractionAsString(whole, numerator, denominator);
	}
	
	private static String fractionAsString(int whole, int numerator, int denominator) {	
		if(numerator == 0)
			return "" + whole;

		if(whole == 0)
			return numerator + "/" + denominator;
		
		if(whole < 0 && numerator < 0)
			numerator *= -1;		
					
		return whole + "_" + numerator + "/" + denominator;
	}
	

	///Returns the Greatest Common Divisor of two numbers, using Euclidean algorithm
	public static int GetGCD(int a, int b) {
		if(a < b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		int r;
		while(true) {
			r = a % b;
			if(r == 0)
				return b;
			a = b;
			b = r;
		}			
	}

}
