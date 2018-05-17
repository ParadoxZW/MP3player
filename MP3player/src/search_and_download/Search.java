package search_and_download;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import song.Song;

public class Search {
	public static final ArrayList<Song> search(String name) {
		/**
		 * 输入歌名，返回歌曲数组 */
		ArrayList<Song> list;
		list = new ArrayList<Song>();
		new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    HttpURLConnection connection;  
                    String finalTitle = URLEncoder.encode(name,"utf-8");  
                    URL url = new URL("http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.search.catalogSug&format=json&callback=&query="+name);  
                    connection = (HttpURLConnection) url.openConnection();  
                    connection.setConnectTimeout(60*1000);  
                    connection.setReadTimeout(60*1000);  
                    connection.connect();  
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
                    String s;  
                    if ((s=reader.readLine())!=null)  
//                        doJson(s);  
                    	System.out.println(s);
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }).start();  
		return list;
	}
	
	public static void main(String[] args) {
		search("告白气球");
		System.out.println("1");
	}
}
