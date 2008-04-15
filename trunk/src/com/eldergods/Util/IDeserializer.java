package com.eldergods.Util;

public interface IDeserializer {

	Object deserialize(String serialized);
	Object deserialize(byte[] buffer);
	
	Object deserializeUrl(String url);
}
