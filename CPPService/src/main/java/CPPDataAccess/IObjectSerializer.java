package CPPDataAccess;

public interface IObjectSerializer {
	String ToJson(Object src) throws Exception;
	<T> T FromJson(String json, Class<T> classOfT) throws Exception;
}
