package MangaPanda;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Closing extends WindowAdapter {

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);		
	}
}
