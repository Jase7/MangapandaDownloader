package MangaPandaDownloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Manga {
	
	public static void main(String[] args) throws IOException {
		
			//Pedimos la URL mediante un dialog
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
			
			//Abrimos la conexión
			URL nUrl = null;
			HttpURLConnection open;
			
			String cUrl = url + String.valueOf(i);
			System.out.println("La URL es " + cUrl);
						
			//Para detectar errores en la URL y 404's
			try {
				nUrl = new URL(cUrl);
				
			} catch(MalformedURLException e){
				System.out.println("Eres tonto y no sabes copiar una URL");
				System.exit(1);
			}
			
			nUrl = new URL(cUrl);
			
			open = (HttpURLConnection) nUrl.openConnection();
			open.setRequestMethod("GET");
			open.setReadTimeout(15*1000);
			open.connect();
			
			//404 handler
			try {
				open.getInputStream();
			} catch (IOException e) {
				System.out.println("La página web no existe");
				System.exit(1);			
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(open.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = "";
			
			while((line = reader.readLine())!=null) {
				stringBuilder.append(line + "\n");
			}
			
			//Catch image's url
			int begin = stringBuilder.indexOf("<img id");
		    	int end = stringBuilder.lastIndexOf("name=\"img\"");
			String imagen = stringBuilder.substring(begin, end);
			
			begin = imagen.indexOf("http");
			end = imagen.lastIndexOf("\" alt");
			imagen = imagen.substring(begin, end);
			
			//Vamos a guardar la imagen
			String folder = "D:/Berserk";
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
			
			//Volvemos a abrir conexión con la URL de la imagen	
			System.out.println("Se descargará " + imagen );
			
			nUrl = new URL(imagen);
			open = (HttpURLConnection) nUrl.openConnection();
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
			
			in.close();
			out.close();
			
		}
	}
}
