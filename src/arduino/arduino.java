import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;
//import gnu.io.SerialPort;
//import RXTXcomm.j;

public class arduino {
	String s = null;
	public String connect(String portname) {
		// now we need to serialport

		SerialPort port = new SerialPort(portname);

		try {
			port.openPort();

			port.setParams(

					SerialPort.BAUDRATE_9600,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE
					);
			port.addEventListener((SerialPortEvent event)->{

				if(event.isRXCHAR()) {

					try {
						s = port.readString();
						System.out.print(s);

					} catch (SerialPortException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			});
			
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(s == null){
			return "";
		}
			
		return s;
	}

	public static void main(String[] args) {
		// we need to array of string that hold all the ports
		String portlist[] = SerialPortList.getPortNames();

		//for(int i =0; i<portlist.length;i++) {
		//	System.out.println(portlist[i]);
		//}
		arduino obj = new arduino();
		System.out.println(obj.connect(portlist[0]));



	}

}