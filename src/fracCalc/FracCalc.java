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
		// TODO: Implement this function to produce the solution to the input

		String[] elements = input.split(" ");
		
		return parseElement(elements[2]);
	}

	// TODO: Fill in the space below with any helper methods that you think you will need
	private static String parseElement(String expression) {
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
		
		return "whole:"+whole+" numerator:"+numerator+" denominator:"+denominator;
	}
}
