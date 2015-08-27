package communication;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class Message {
	private String messageText;
	private Vector<Byte> ToSend;
	
	public Message () {
		this.messageText = "";
		this.ToSend = new  Vector<Byte>();
	}
	
	public Message (Vector<Byte> v) {
		this.messageText = "";
		this.ToSend = v;
	}
	
	public Message (String messageText) {
		this.messageText = messageText;
		this.ToSend = new  Vector<Byte>();
	}
	
	public Message (Message m){
		this.messageText = new String (m.messageText);
		this.ToSend = new Vector<Byte>(m.ToSend);
	}
	
	public void append (String addition) {
		messageText = this.messageText.concat(addition);
	}
	
	public void setTosend(Vector<Byte> v){
	this.ToSend = new Vector<Byte>(v);
	}
	
	public Vector<Byte> getToSend(){
		return new Vector<Byte>(ToSend);
	}
	
	public Document toDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			String noTabsAndSpacesMsg = this.messageText.replaceAll(">\\s+<", "><").trim();
			InputSource is = new InputSource(new StringReader(noTabsAndSpacesMsg));
			return builder.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
	
	public boolean validate() {
		return true;
	}
	
	public String toString() {
		return messageText;
	}
	
	public void SetMessageText(String txt){
		this.messageText = txt;
	}
	
	
	
	public byte[] getBytes() {
		if(! CommunicationManager.getInstance().isSimulator())
			return messageText.getBytes(Charset.forName("UTF-8"));
		
		byte[] res = new byte[this.ToSend.size()];
		for (int i=0;i<res.length; i++)
			res[i]=this.ToSend.elementAt(i).byteValue();
		return res;		
		
	}
	
	public byte[] getBytesReceive() {

		byte[] res = new byte[this.ToSend.size()];
		for (int i=0;i<res.length; i++)
			res[i]=this.ToSend.elementAt(i).byteValue();
		return res;		
		
	}
	
	public byte[] ConvertTobyteArr(){
		byte[] res = new byte[this.ToSend.size()];
		for (int i=0;i<res.length; i++)
			res[i]=this.ToSend.elementAt(i).byteValue();
		return res;
	}

}
