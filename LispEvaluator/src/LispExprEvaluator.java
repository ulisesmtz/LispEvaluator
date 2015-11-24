import java.util.*;

public class LispExprEvaluator {
	private String inputExpr;
	private Stack<Object> thisExprStack;
	private Stack<Double> thisOpStack;

	public LispExprEvaluator() {
		inputExpr = "";
		thisExprStack = new Stack<Object>();
		thisOpStack = new Stack<Double>();
	}

	
	public LispExprEvaluator(String inputExpression) {
		inputExpr = inputExpression;
		thisExprStack = new Stack<Object>();
		thisOpStack = new Stack<Double>();
	}


	public void reset(String inputExpression) {
		inputExpr = inputExpression;
		thisExprStack = new Stack<Object>();
		thisOpStack = new Stack<Double>();
	}

	// This function evaluates current operator with its operands
	// See complete algorithm in evaluate()
	//
	// Main Steps:
	// Pop operands from thisExprStack and push them onto
	// thisOpStack until you find an operator
	// Apply the operator to the operands on thisOpStack
	// Push the result into thisExprStack
	//
	
	private void evaluateCurrentOperation() {
    	while (thisExprStack.peek().getClass().getName().equals("java.lang.Double")
    			&& !thisExprStack.isEmpty()) {		// keep looping until no more doubles 
    		thisOpStack.push( (Double) thisExprStack.pop());
    	}
		    	
    	Character operator = (Character)thisExprStack.pop();
    	double answer = 0.0;
		int size = thisOpStack.size();
		boolean flag = true;	// flag is used when minus or division operator is used.
								// In these cases, flag is only used to get the first number
								// and save it to answer. After that, flag is set to false and 
								// reseted at the end

    	switch (operator) {
    		case '+':
    			while (!thisOpStack.empty()) {
    				if (answer == 0) {
    					answer = thisOpStack.pop();
    				} else {
        				answer += thisOpStack.pop();
    				}
    			}
    			break;

    		case '-':
    			while (!thisOpStack.empty()) {
    				if (size == 1) {
    					answer = -thisOpStack.pop();
    				} else {
    					if (flag) {
    						answer = thisOpStack.pop();
    						flag = false;
    					} else {
    						answer -= thisOpStack.pop();
    					}
    				}
				}
    			
    			flag = true;	// reset flag value
    			break;

    		case '*':
    			while (!thisOpStack.empty()) {
    				if (answer == 0) {
    					answer = thisOpStack.pop();
    				} else {
    				answer *= thisOpStack.pop();
    				}
    			}
    			break;

    		case '/':
    			while (!thisOpStack.empty()) {
    				if (size == 1) {
    					answer = 1 / thisOpStack.pop();
    				} else {
    					if (flag) {
    						answer = thisOpStack.pop();
    						flag = false;
    					} else {
    						answer /= thisOpStack.pop();
    					}
    				}
				}

    			flag = true;	// reset flag value
    			break;
    		
    		default:
    			throw new LispExprEvaluatorException();
    	}
    	
    	thisExprStack.push((Double)answer);
    }

	/**
	 * This function evaluates current Lisp expression in inputExpr It return
	 * result of the expression
	 * 
	 * The algorithm:
	 * 
	 * Step 1 Scan the tokens in the string. Step 2 If you see an operand, push
	 * operand object onto the thisExprStack Step 3 If you see "(", next token
	 * should be an operator Step 4 If you see an operator, push operator object
	 * onto the thisExprStack Step 5 If you see ")" // steps in
	 * evaluateCurrentOperation() : Step 6 Pop operands and push them onto
	 * thisOpStack until you find an operator Step 7 Apply the operator to the
	 * operands on thisOpStack Step 8 Push the result into thisExprStack Step 9
	 * If you run out of tokens, the value on the top of thisExprStack is is the
	 * result of the expression.
	 */
	public double evaluate() {
		Scanner inputExprScanner = new Scanner(inputExpr);

		// Use zero or more white space as delimiter,
		// which breaks the string into single character tokens
		inputExprScanner = inputExprScanner.useDelimiter("\\s*");

		while (inputExprScanner.hasNext()) {

			if (inputExprScanner.hasNextInt()) {
				String dataString = inputExprScanner.findInLine("\\d+");
				thisExprStack.push(new Double(dataString));
			} else {
				String aToken = inputExprScanner.next();
				char item = aToken.charAt(0);
				String nextToken;
				char nextItem;
				

				switch (item) {
					case '(':
						nextToken = inputExprScanner.next();
						nextItem = nextToken.charAt(0);
						if (nextItem == '+' || nextItem == '-' || nextItem == '*' || nextItem == '/') {
							thisExprStack.push(nextItem);
						}
						break;

					case ')':
						try {
							evaluateCurrentOperation();
						} catch (LispExprEvaluatorException e) {
							System.out.println("Error: " + e);
						} catch (ArithmeticException ae) {
							System.out.println("Error: " + ae);
						}
						break;
					
				
					default: // error
						throw new LispExprEvaluatorException(item 
								+ " is not a legal expression operator");
					
				} // end switch
			} // end else
		} // end while
		
		double result = (Double)thisExprStack.pop();
		
		return result;
	}

}
