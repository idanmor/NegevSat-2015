package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerialReader implements Runnable {
	DataInputStream in;
	private boolean isRunning;
	//	DataInputStream DataInput;

	public SerialReader ( DataInputStream in ) {
		this.in = in;
		isRunning = true;
		// this.DataInput = new DataInputStream(in);
	}

	public void run () {     // TO DO : CHANGE SWITCH-CASE LOOP FOR ALL TYPE OF MessageInterface - now working only with XMLMessage
		int len = -1;   
		Message msg = new Message();
		StringBuffer remainder = new StringBuffer();
		while(isRunning) {
			try
			{

				byte[] buffer = new byte[1024];
				byte[] temp = new byte[1];
				int reads=0;
				len=0;

				while(len!=-1){
					len =  this.in.read(temp, 0, 1);
					if(len!=1)
						continue;
					if(temp[0]!=10){
						buffer[reads]= temp[0];
						reads++;
					}
					else{
						len = reads;
						break;
					}

				}

				//byte[] buffer = new byte[1024];
				//len = in.read(buffer, 0, buffer.length);

				//len = this.in.read(buffer, 0, buffer.length);
				if (len > 0 && buffer[0]!=94) {					// Fix BUG  when send packet to sat and got immediate unknown message 
					System.out.println("DEBUG: Read " + len + " bytes");



					/////////////////////////////////////////////////////////////////////////////////////////////////////// 

					// for XML communication

					/*
		        	StringBuffer str = new StringBuffer(new String(buffer, 0, len));
		        	str = remainder.append(str);

		        	Pattern pattern = Pattern.compile(CommunicationManager.msgStartDelimiter 
		        							+ "(.*?)" + CommunicationManager.msgStopDelimiter, Pattern.DOTALL);
		        	Matcher matcher = pattern.matcher(str);
		        	while (matcher.find())
		        	{
		        		String foundMsg = matcher.group(1);
		        		StringBuffer trash = new StringBuffer();
		        		matcher.appendReplacement(trash, foundMsg);
		        	    System.out.println(foundMsg);
		        	    CommunicationManager.getInstance().getMessageAcceptorQueue().put(new Message(foundMsg));
		        	}
		        	remainder = new StringBuffer();
		        	matcher.appendTail(remainder);
					 */

					///////////////////////////////////////////////////////////////////////////////////////////////////////        	




					///////////////////////////////////////////////////////////////////////////////////////////////////////

					// for Binary Communication
					boolean pass = true;
					Vector<Byte> tmp = new Vector<Byte>();
					for (int i=0; i< len; i++){
						if (buffer[i]== 11 && i==len-1){
							pass=false;
							break;
						}
						if  (buffer[i] == 11 && buffer[i+1]==12){
							tmp.addElement((byte)10);
							i++;
						}
						else if (buffer[i]==11 && buffer[i+1]==11){
							tmp.addElement((byte)11);
							i++;
						}
						else
							tmp.addElement(buffer[i]);
					}

					if (pass){

						msg.setTosend(tmp);
						CommunicationManager.getInstance().getMessageAcceptorQueue().put(msg);
					}
					///////////////////////////////////////////////////////////////////////////////////////////////////////

				}

				CommunicationManager.getInstance().getInputLock().lock();
				CommunicationManager.getInstance().getInputDataAvailable().await();
			}
			catch ( IOException e ) {
				e.printStackTrace();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally {
				CommunicationManager.getInstance().getInputLock().unlock();
			}
		}
	}

	public void stopThread() {
		this.isRunning = false;
	}
}
