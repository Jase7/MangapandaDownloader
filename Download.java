package MangaPanda;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

class Download implements ActionListener, Runnable  {

	TextArea urlInput;
	TextArea console;
	static String folder = "D:";
	Thread t1;

	public Download() {}

	public Download(TextArea url, TextArea console) {

		this.urlInput = url;
		this.console = console;

	}

	public void actionPerformed(ActionEvent arg0) {	

			Runnable r = new Download(urlInput, console);
			Thread t = new Thread(r);
			t.start();
		
	}

	@Override
	public void run() {
		
		String input = urlInput.getText();

		int nImagen;
		String[] urls;
		urls = input.split("\n");
		int j = 0;  
		for(String url : urls){
			url = url.replace("\n","").trim();
			j++;
			String[] parts;
			parts = url.split("/");
			String num = parts[parts.length-1];
	
			if ((parts.length == 3 || parts.length == 5) && !url.endsWith("/") ) {
				url += "/1";
			}
	
			else if ((parts.length == 3 || parts.length == 5) && url.endsWith("/")) {
				url += "1";
			}
	
			nImagen = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.length()));
			url = url.substring(0, url.lastIndexOf("/") + 1);		
	
			/*Download part */
	
			for (int i = nImagen; ; i++) {
	
				//Open the connection
				URL nUrl = null;
				HttpURLConnection open = null;
	
				String cUrl = url + String.valueOf(i);
				console.append("La URL es " + cUrl + "\n");
	
				//We want to handle 404 errors and malformed URLs
				try {
					nUrl = new URL(cUrl);
	
				} catch(MalformedURLException e){
					console.append("La URL es inválida\n");
					//System.exit(1);
					break;
				}
	
	
				try {
					open = (HttpURLConnection) nUrl.openConnection();
					open.setRequestMethod("GET");
					open.setReadTimeout(15*1000);
					open.connect();
				} catch (IOException e1) {
	
					e1.printStackTrace();
				}
	
	
				//404 handler
				try {
					open.getInputStream();
				} catch (IOException e) {
					console.append("La página web no existe\n");
					//System.exit(1);
					break;
				}
	
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new InputStreamReader(open.getInputStream()));
				} catch (IOException e1) {
	
					e1.printStackTrace();
				}
				StringBuilder stringBuilder = new StringBuilder();
				String line = "";
	
				try {
					while((line = reader.readLine())!=null) {
						stringBuilder.append(line + "\n");
					}
	
				} catch (IOException e) {				
					e.printStackTrace();
				}
	
				//Catch image's url
	
				int begin = stringBuilder.indexOf("<img id");
				int end = stringBuilder.lastIndexOf("name=\"img\"");
				String imagen = stringBuilder.substring(begin, end);
	
				begin = imagen.indexOf("http");
				end = imagen.lastIndexOf("\" alt");
				imagen = imagen.substring(begin, end);
	
				//Download the image
				//File f = destiny;
	
				String name = cUrl.substring(cUrl.lastIndexOf("/") + 1, cUrl.length());
				name += ".jpg";
	
				//Reopen the connection, now with the image's URL
				console.append("Se descargará " + imagen + " del link " + j + "\n" );
	
				try {
					nUrl = new URL(imagen);
				} catch (MalformedURLException e) {
	
					e.printStackTrace();
				}
				try {
					open = (HttpURLConnection) nUrl.openConnection();
					open.setRequestProperty("User-Agent", 
							"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;" 
									+ "rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
					open.connect();
	
				} catch (IOException e) {				
					e.printStackTrace();
				}
	
	
				InputStream in = null;
				OutputStream out = null;
				try {
					in = open.getInputStream();
					console.append("Se guardará en " + folder + "\n");
					File dir = new File(folder + "/" + num);	
					dir.mkdirs();
					File fE = new File(folder + "/" + num + "/" + name);	
					out = new FileOutputStream(fE);
				} catch (IOException e) {
	
					e.printStackTrace();
				}
	
	
	
				console.append("Descargando página: " + i + "\n");
				int b = 0;
				while (b != -1) {
					try {
						b = in.read();
					} catch (IOException e) {
	
						e.printStackTrace();
					}
					if (b != -1)
						try {
							out.write(b);
						} catch (IOException e) {
	
							e.printStackTrace();
						}
				}
	
				console.append("Descargado\n\n");
	
				try {
					in.close();
					out.close();
	
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		}
		
		
	}	
}
