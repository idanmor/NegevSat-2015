package communication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

import data.Command;
import data.Mission;

public class LocalIncomingTest {

	public static void main(String[] args) {
		try {
			CommunicationManager.getInstance().connect("LOCAL");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] filenames = {"message1.xml", "message2.xml", "message3.xml"};
		
		/*
		LinkedList<Mission> missions = new LinkedList<Mission>();
		missions.add(new Mission(null, Command.FORMAT_TEMP, 2));
		missions.add(new Mission(new Timestamp((new Date()).getTime()), Command.MOVE_TO_OP, 1));
		
		CommunicationManager.getInstance().sendMissions(missions);
		*/
		
		for (String filename : filenames) {
			try {
				byte[] filecontent = Files.readAllBytes(Paths.get("C:\\negevsat\\" + filename));
				String msg = new String(filecontent, "UTF-8");
				msg = msg.replaceAll("\n", "");
				msg = msg.replaceAll("\t", "");
				msg = msg.replaceAll("\r", "");
				CommunicationManager.getInstance().sendLocalMessage(new Message(msg));
			} catch (IOException e) {
				System.out.println("Cannot read from file " + filename);
			}
		}
	}

}
