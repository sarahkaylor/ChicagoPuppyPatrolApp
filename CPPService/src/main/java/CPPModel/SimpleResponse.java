package CPPModel;

import java.io.*;

import com.google.gson.Gson;

public abstract class SimpleResponse {

	public final String ExceptionType;
	public final String ErrorMessage;
	public final String Trace;
	public final boolean Success;
	
	protected SimpleResponse(boolean success, String errorMessage, String trace, String exceptionType) {
		ErrorMessage = errorMessage;
		Trace = trace;
		Success = success;
		ExceptionType = exceptionType;
	}
	
	protected SimpleResponse(Exception e) {
		this(false, e.getMessage(), GetExceptionTrace(e), e.getClass().getSimpleName());
	}
	
	protected SimpleResponse() {
		Success = false;
		ErrorMessage = "";
		Trace = "";
		ExceptionType = "";
	}
	
	private static String GetExceptionTrace(Exception e) {
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();
        String stackTrace = sw.toString();
        return stackTrace;
	}
	
	public final String ToJson() {
		return new Gson().toJson(this);
	}
	
	@Override
	public String toString() {
		final String nl = "\r\n";
		if(Success) {
			return "Success";
		} else if(ExceptionType.length() > 0) {
			return "Exception: " + ExceptionType + nl + ErrorMessage + nl + Trace;
		} else {
			return ErrorMessage;
		}
	}
}
