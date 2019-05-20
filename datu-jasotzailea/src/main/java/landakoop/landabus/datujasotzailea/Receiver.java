package landakoop.landabus.datujasotzailea;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	 public void receiveMessage(String message) {
		 System.out.println("MSG: " + message);
	 }
}
