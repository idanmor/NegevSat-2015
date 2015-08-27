package communication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SendTest {

	public static void main(String[] args) {
		try {
			CommunicationManager.getInstance().connect("COM2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] filenames = {"message1.xml", "message2.xml", "message3.xml"};
		
		for (String filename : filenames) {
			try {
				byte[] filecontent = Files.readAllBytes(Paths.get("C:\\negevsat\\" + filename));
				String msg = new String(filecontent, "UTF-8");
				CommunicationManager.getInstance().sendMessage(new Message(msg));
			} catch (IOException e) {
				System.out.println("Cannot read from file " + filename);
			}
		}
		while(true) {
			
		}
	}
}
