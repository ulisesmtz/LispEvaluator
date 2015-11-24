public class LispExprEvaluatorException extends RuntimeException
{
    public LispExprEvaluatorException()
    {
	this("");
    }

    public LispExprEvaluatorException(String errorMsg) 
    {
	super(errorMsg);
    }

}
