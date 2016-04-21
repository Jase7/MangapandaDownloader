package MangaPandaDownloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Manga {
	
	public static void main(String[] args) throws IOException {
		
			//Using a dialog asking for the URL 
			JFrame frame = new JFrame("MangaPandaDownloader");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			String url;		
			url = JOptionPane.showInputDialog(frame, "Introduce la URL");
			int nImagen;
			String[] parts;
			parts = url.split("/");
			
			System.out.println(parts.length);
			
			if ((parts.length == 3 || parts.length == 5) && !url.endsWith("/") ) {
				url += "/1";
			}
			
			else if ((parts.length == 3 || parts.length == 5) && url.endsWith("/")) {
				url += "1";
			}
			
			nImagen = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.length()));
			url = url.substring(0, url.lastIndexOf("/") + 1);		
			
			for (int i = nImagen; ; i++) {
			
			//Open the connection
			URL nUrl = null;
			URLConnection open;
			
			String cUrl = url + String.valueOf(i);
			System.out.println("La URL es " + cUrl);
						
			//We want to handle 404 errors and malformed URLs
			try {
				nUrl = new URL(cUrl);
				
			} catch(MalformedURLException e){
				System.out.println("La URL es inválida");
				System.exit(1);
			}
			
			nUrl = new URL(cUrl);
			
			open = nUrl.openConnection();
			open.connect();
			
			//404 handler
			try {
				InputStream s = open.getInputStream();
			} catch (IOException e) {
				System.out.println("La página web no existe");
				System.exit(1);			
			}
			
			InputStream s = open.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s));
			String line = "";
			
			while((bufferedReader.readLine())!=null) {
				line += bufferedReader.readLine();
			}
			
			//Catch image's url
			int begin = line.indexOf("<img");
			int end = line.lastIndexOf("name=\"img\" />");
			String imagen = line.substring(begin, end);
			
			begin = imagen.indexOf("http");
			end = imagen.lastIndexOf("\" alt");
			imagen = imagen.substring(begin, end);
			
			//Download the image
			String folder = "D:/";
			String name = cUrl.substring(cUrl.lastIndexOf("/") + 1, cUrl.length());
			name += ".jpg";
			
			File f = new File(folder);
			System.out.println(folder);
			System.out.println(name);
			
			if (!f.exists()) {
				if (!f.mkdir()) {
					System.out.println("No se pudo crear la carpeta");
				}
			}
			System.out.println("llego aquí");
			
			//Reopen the connection, now with the image's URL
			System.out.println("Se descargará " + imagen );
			
			nUrl = new URL(imagen);
			open = nUrl.openConnection();
			open.setRequestProperty("User-Agent", 
							"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;" 
							+ "rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
			open.connect();
					
			InputStream in = open.getInputStream();
			File fE = new File(folder + "/" + name);				
			OutputStream out = new FileOutputStream(fE);
			
			
			System.out.println("Descargando página: " + i);
			int b = 0;
			while (b != -1) {
			  b = in.read();
			  if (b != -1)
			    out.write(b);
			}
			
			System.out.println("Descargado");
			
			s.close();
			in.close();
			out.close();
			
		}
	}
}
