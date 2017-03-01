package Encryption;

import java.io.IOException;
import java.io.InputStream;

public class StringInputStream extends InputStream {
	private final String _data;
	private int _position;
	
	public StringInputStream(String data) {
		_data = data;
		_position = 0;
	}
	
	@Override
	public int read() throws IOException {
		if(_position >= _data.length()) {
			return (-1);
		}
		char value = _data.charAt(_position);
		++_position;
		return (int)value;
	}
	
	@Override
	public int read(byte b[], int off, int len) throws IOException {
		int readCount = 0;
		for(int i = off; i < len && _position < _data.length(); i++) {
			char value = _data.charAt(_position);
			++_position;
			++readCount;
			b[i] = (byte)value;
		}
		return readCount;
	}
}