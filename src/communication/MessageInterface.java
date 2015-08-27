package communication;

import org.w3c.dom.Document;

public interface MessageInterface {

	public byte[] getBytes();
	public Document toDocument();
	
}
