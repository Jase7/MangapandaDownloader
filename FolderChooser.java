package MangaPanda;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

class FolderChooser implements ActionListener{
	
	JFileChooser choosedFolder;
	Frame f;

	@Override
	public void actionPerformed(ActionEvent arg0) {

		choosedFolder = new JFileChooser("Selecciona una carpeta");
		choosedFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		choosedFolder.setAcceptAllFileFilterUsed(false);
		
		if (choosedFolder.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			Download.folder = choosedFolder.getSelectedFile().toString();
			System.out.println(Download.folder);
		}
		else {
			System.out.println("No Selection ");
		}
		
		
		
		
	}
}
