package Util;

public interface ILazy<T> {
	T Value() throws Exception;
}
