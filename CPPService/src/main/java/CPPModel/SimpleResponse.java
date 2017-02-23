package CPPModel;

import java.io.*;

import com.google.gson.Gson;

public abstract class SimpleResponse {

	public final String ErrorMessage;
	public final String Trace;
	public final boolean Success;
	
	protected SimpleResponse(boolean success, String errorMessage, String trace) {
		ErrorMessage = errorMessage;
		Trace = trace;
		Success = success;
	}
	
	protected SimpleResponse(Exception e) {
		this(false, e.getMessage(), GetExceptionTrace(e));
	}
	
	protected SimpleResponse() {
		Success = false;
		ErrorMessage = "";
		Trace = "";
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
}
