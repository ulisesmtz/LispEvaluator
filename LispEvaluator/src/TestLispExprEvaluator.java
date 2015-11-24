import java.util.*;

public class TestLispExprEvaluator
{
	
    public static void main (String args[])
    {
    	LispExprEvaluator expr= new LispExprEvaluator();
    	Scanner scanner = new Scanner(System.in);

    	String inputExpr;
    	double result;
        int i=0;
        do                                                                  
        {                                                                   
           try 
           {                                                                
              System.out.print( "\ninput an expression string:" );

              // scan next input line
              inputExpr = scanner.nextLine();                            

              if (inputExpr.equals("exit"))
            	  break; 

              i++;
              System.out.println("Evaluate expression #"+ i+" :" + inputExpr);
              expr.reset(inputExpr);
              result = expr.evaluate();
              System.out.printf("Result : %.2f\n", result);

           } // end try                                                     
           catch ( LispExprEvaluatorException e )                
           {                                                                
              System.err.printf( "\nException: %s\n", e);
           } // end catch exception here, continue to next loop                                            

        } while ( true ); // end do...while                         
    } // end main

}
