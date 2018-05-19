package search_and_download;
import java.io.BufferedInputStream;
import java.io.PrintWriter;  
import java.util.HashMap;  
import java.util.Map; 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import song.Song;

public class FileManager {
	/**有关文件结构的说明：
	 * --Music/
	 * 	--log.json	存储了本地歌曲信息
	 * 	--id.mp3
	 *	--id.lrc 可选 */
	public static ArrayList<Song> locallist = new ArrayList<Song>();
	static JSONArray json;  
	public static void logRead() {
		new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {    
                	JSONTokener jsonTokener = new JSONTokener(new FileReader(new File("Music\\log.json")));  
                    json = new JSONArray(jsonTokener);
                    int k = json.length();
                    for (int i = 0; i < k; i++) {
                    	JSONObject temp = json.getJSONObject(i);
                    	Song songt = new Song();
                    	songt.setId(temp.getString("id"));
                    	songt.setName(temp.getString("Name"));
                    	songt.setArtist_name(temp.getString("ArtistName"));
                    	songt.setDir(temp.getString("LocalDir"));
                    	locallist.add(songt);
                    }
                }  
                catch (IOException e) {  
                    e.printStackTrace();  
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            }  
        }).start();  
        try {  
            Thread.sleep(500);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }
	}
	public static void logWrite(){
		new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {    
                    File fp=new File("Music\\log.json");
                    PrintWriter pfp= new PrintWriter(fp);
                    pfp.print(json.toString());
                    pfp.close();
                }  
                catch (IOException e) {  
                    e.printStackTrace();  
				}  
            }  
        }).start();  
        try {  
            Thread.sleep(500);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }
		
	}
	public static void addLog(Song ns) {
		new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {
                	locallist.add(ns);
                    JSONObject njson = new JSONObject();
                    njson.put("id", ns.getId());
                    njson.put("Name", ns.getName());
                    njson.put("ArtistName", ns.getArtist_name());
                    njson.put("LocalDir", ns.getDir());
                    json.put(njson);
                    logWrite();
                }  
                catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            }  
        }).start();  
        try {  
            Thread.sleep(500);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }
	}
	public static void main(String[] args){
		logWrite();
	}
}

