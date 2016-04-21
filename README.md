# MangapandaDownloader

MangapandaDownloader is a java program where you can download the images of a manga from http://www.mangapanda.com/

The way to use it is very simple. The program will ask for a link which you'll provide such as http://www.mangapanda.com/berserk/1
MangaPandaDownloader will try to connect, to find the image and download it to the folder you want.

If your are using a proxy or a VPN, you'll have to modify the source code and add this two lines:

//HTTP 
System.setProperty("http.proxyHost", yourProxy);
System.setProperty("http.proxyPort", yourProxyPort); //Port by default is 80

//HTTPS
System.setProperty("https.proxyHost", yourProxy);
System.setProperty("https.proxyPort", yourProxyPort); //Port by default is 443

Or you can also use the constructor of URL class:

URL website = new URL("http", proxy, Integer.parseInt(proxyPort), websiteUrl);

Hope it will help!
