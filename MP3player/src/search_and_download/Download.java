package search_and_download;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import song.Song;
import search_and_download.FileManager;
public class Download {
	Song song;
	public Download(Song song) {
		// TODO Auto-generated constructor stub
		this.song = song;
	}
	void runDownload() {
		new Thread(new Runnable() {  
	        @Override  
	        public void run() {  
	        	try {
	                URL makeURL= new URL(song.getAddress());
	                HttpURLConnection httpURLConnection=(HttpURLConnection)makeURL.openConnection();
	                httpURLConnection.setRequestMethod("GET");
	                httpURLConnection.addRequestProperty("User-Agent", "Mozilla");
	                httpURLConnection.setConnectTimeout(200000);
	                httpURLConnection.setReadTimeout(300000);
	                httpURLConnection.setDoOutput(true);
	//                System.out.println(httpURLConnection.getResponseCode());
	      
	                if( httpURLConnection.getResponseCode()==200)
	                {
	//                	System.out.println("Connected, getting data");
	                	BufferedInputStream br=new BufferedInputStream(httpURLConnection.getInputStream());
	                	byte[] buff=new byte[1024];
	                	int len=0;
	                	song.setDir("Music\\"+song.getId()+".mp3");
	                	FileOutputStream fout = new FileOutputStream(new File(song.getDir()));
	                	while( (len= br.read(buff)) != -1) 
	                	{         
	                		fout.write(buff, 0, len);
	                		// len=br.read(buff);   
	                	}
	              
	                	fout.close();
	                	FileManager.addLog(song);
	//                	System.out.println("Downloading done");
	                }
	                else
	                {
	                	System.out.println("Error no connection can be made");
	                }
	        	}catch(Exception e){
	        		System.out.println("Check execption inside run "+e);
	        		}
	        	}
	    }).start();
	    try {  
	        Thread.sleep(500);  
	    } catch (InterruptedException e) {  
	        e.printStackTrace();  
	    }
	}
}
