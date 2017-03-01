package Encryption;

import java.io.IOException;
import java.io.OutputStream;

public class StringOutputStream extends OutputStream {

	private final StringBuilder _buff;
	
	public StringOutputStream() {
		_buff = new StringBuilder();
	}

	@Override
	public void write(int b) throws IOException {
		_buff.append((char)b);
	}
	
	@Override
	public void write(byte[] buff, int offset, int len) throws IOException {
		for(int i = offset; i < len; i++) {
			_buff.append((char)buff[i]);
		}
	}
	
	@Override
	public String toString() {
		return _buff.toString();
	}
}
