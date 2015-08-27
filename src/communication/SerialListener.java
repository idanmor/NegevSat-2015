package communication;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialListener implements SerialPortEventListener {
	
	private DataInputStream in;
	
	public SerialListener (DataInputStream in) {
		this.in = in;
	}

	/**
	 * Handle serial events
	 */
	@Override
	public void serialEvent(SerialPortEvent event) {
		switch(event.getEventType()) {
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            outputBufferEmpty(event);
            break;

        case SerialPortEvent.DATA_AVAILABLE:
            dataAvailable(event);
            break;

        /*
        case SerialPortEvent.BI:
            breakInterrupt(event);
            break;

        case SerialPortEvent.CD:
            carrierDetect(event);
            break;

        case SerialPortEvent.CTS:
            clearToSend(event);
            break;

        case SerialPortEvent.DSR:
            dataSetReady(event);
            break;

        case SerialPortEvent.FE:
            framingError(event);
            break;

        case SerialPortEvent.OE:
            overrunError(event);
            break;

        case SerialPortEvent.PE:
            parityError(event);
            break;
        case SerialPortEvent.RI:
            ringIndicator(event);
            break;
        */
		}
		
	}

	private void dataAvailable(SerialPortEvent event) {
		CommunicationManager.getInstance().getInputLock().lock();
		try {
			CommunicationManager.getInstance().getInputDataAvailable().signal();
		} finally {
			CommunicationManager.getInstance().getInputLock().unlock();
		}
	}

	private void outputBufferEmpty(SerialPortEvent event) {
		// TODO Auto-generated method stub
		
	}

}
