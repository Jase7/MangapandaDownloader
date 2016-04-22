package MangaPanda;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.IOException;



public class MangaDownloader{
	
	public static void main(String[] args) throws IOException {
		
		new Interfaz();
	}
}

class Interfaz {
	
	Frame frame; 
	Panel panel;
	TextField urlInput;
	TextArea console;
	Button accept;
	Button folder;
	Label info;	
	
	public Interfaz() {
		
		//Create the elements
		this.frame = new Frame("MangaPandaDownloader");
		this.accept = new Button("Descargar");
		this.folder = new Button("Seleccionar carpeta");
		this.panel = new Panel();
		this.urlInput = new TextField();
		this.console = new TextArea("Consola de descargas:\n");
		this.info = new Label("Introduce la url");
		
		this.frame.setLayout(new GridLayout(2, 1));
		this.panel.setLayout(new GridLayout(2, 2));
		this.frame.setSize(400, 300);
		this.frame.setVisible(true);
		this.frame.addWindowListener(new Closing());
		this.accept.addActionListener(new Download(urlInput, console));
		this.folder.addActionListener(new FolderChooser());
		this.console.setEditable(false);
		
		//Add elements
		this.panel.add(info);
		this.panel.add(urlInput);
		this.panel.add(folder);
		this.panel.add(accept);
		this.frame.add(panel);
		this.frame.add(console);
	}
}
