import java.io.*;
import java.net.*;

import com.sun.xml.internal.ws.api.message.stream.InputStreamMessage;

public class Main {

	
	public static void main ( String[] args ){
		
        int argSize = args.length;
        String comport = "COM1";
        String inIp = "127.0.0.1";
        String inPort = "1111";
        String outIp = "127.0.0.1";
        String outPort = "2222";
        for(int i=0;i<args.length;i++){
        	if(args[i].equalsIgnoreCase("-com")&&i<args.length-1){
        		i++;
                if (args[i].contains("COM")){
                	comport = args[i];
                }
        	}
            else if(args[i].equalsIgnoreCase("-inip")&&i<args.length-1){
            	i++;
            	inIp = args[i];
            }
            else if(args[i].equalsIgnoreCase("-inport")&&i<args.length-1){
            	i++;
            	inPort = args[i];
            }
            else if(args[i].equalsIgnoreCase("-outip")&&i<args.length-1){
            	i++;
            	outIp = args[i];
            }
            else if(args[i].equalsIgnoreCase("-outport")&&i<args.length-1){
            	i++;
            	outPort = args[i];
            }
        	
        }
		
		try{
			BufferedReader reader = new BufferedReader(new  InputStreamReader(System.in));
			ComConnection com = new ComConnection(comport);
		  Socket outclientSocket = new Socket(outIp, Integer.parseInt(outPort));
		  System.out.println("Connected to ip "+outIp + "to port "+ outPort + "\n" );
		  //System.out.println("press any key yo continue\n" );
		 //reader.readLine();
		  
		  Socket inclientSocket = new Socket(inIp, Integer.parseInt(inPort));
		  System.out.println("Connected to ip "+inIp + "to port "+ inPort + "\n" );
		  //System.out.println("press any key yo continue\n" );
		  //reader.readLine();
		  com.connect();
		  ComToTcpDirector ComToTcp = new ComToTcpDirector(com, outclientSocket, inclientSocket);
		  TcpToComDirector TcpToCom = new TcpToComDirector(com, outclientSocket, inclientSocket);
		  (new Thread(ComToTcp)).start();
		  (new Thread(TcpToCom)).start();
		}
		catch(Exception exp){
			System.out.println(exp.toString());
		}
		/*
		try{
		String sentence;
		String modifiedSentence;
		  BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		  Socket clientSocket = new Socket("127.0.0.1", 1111);
		  Socket clientSocketTwo = new Socket("127.0.0.1", 2222);
		  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
		  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocketTwo.getInputStream()));
		  sentence = inFromUser.readLine();
		  outToServer.writeBytes(sentence + '\n');
		  modifiedSentence = inFromServer.readLine();
		  System.out.println("FROM SERVER: " + modifiedSentence);
		  clientSocket.close();
		}
		catch(Exception exp){
			System.out.println(exp.toString());
		}
		*/
		
	}
}
