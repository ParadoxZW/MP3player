package search_and_download;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import song.Song;

public class Search {
	static ArrayList<Song> list = new ArrayList<Song>();//每次搜索都会更新
	static boolean status;
	public static final void search(String name_) {
		/**
		 * 输入歌名，返回歌曲数组 */
		new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    HttpURLConnection connection;  
                    String name = URLEncoder.encode(name_,"utf-8");  
                    URL url = new URL("http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.search.catalogSug&format=json&callback=&query="+name);  
                    connection = (HttpURLConnection) url.openConnection();  
                    connection.setConnectTimeout(60*1000);  
                    connection.setReadTimeout(60*1000);  
                    connection.connect();  
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
                    String s;  
                    if ((s=reader.readLine())!=null)  
                        doJson(s);  
//                    	System.out.println(s);
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }).start();  
	}
	private static void doJson(String json) {
		JSONObject jsonObject = null;  
        list.clear();//清空原有的歌曲数组  
        try {  
            //去掉括号  
            json = json.replace("(","");  
            json = json.replace(")","");  
            jsonObject = new JSONObject(json);  
            JSONArray array = new JSONArray(jsonObject.getString("song"));
            int i = 0;
            while (i<array.length()){  
                JSONObject object = array.getJSONObject(i);
                
                String songname = object.getString("songname");  
                String artistname = object.getString("artistname");  
                String songid = object.getString("songid");
                
                Song song1 = new Song();
                
                song1.setId(songid);
                song1.setName(songname);
                song1.setArtist_name(artistname);
                getAdress(song1);
                
                
//                song1.setLrcPath(getLrcAdress(songid));
                if(status) {
//                	System.out.println(i);
                	list.add(i,song1);
                	i++;
                }
            }
//            System.out.println(list.size());
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
	}
	private static void getAdress(Song song){  
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    HttpURLConnection connection;  
                    //URL url = new URL("http://api.5288z.com/weixin/musicapi.php?q="+finalTitle);
//                    System.out.println(song.getId());
                    URL url = new URL("http://ting.baidu.com/data/music/links?songIds="+song.getId());  
                    connection = (HttpURLConnection) url.openConnection();  
                    connection.setConnectTimeout(60*1000);  
                    connection.setReadTimeout(60*1000);  
                    connection.connect();  
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
                    String s;  
                    if ((s=reader.readLine())!=null){
//                    	System.out.println(s);
                    	File fp=new File(song.getId()+".txt");
                        PrintWriter pfp= new PrintWriter(fp);
                        pfp.print(s);
                        pfp.close();
                        s = s.replace("\\","");//去掉\\
//                        String s1 = new String(s);
                        try {  
                            JSONObject object = new JSONObject(s);  
                            JSONObject object1 = object.getJSONObject("data");  
                            JSONArray array = object1.getJSONArray("songList");  
                            JSONObject object2 = array.getJSONObject(0);
                            song.setAddress(object2.getString("songLink"));
                            status = true;
                        } catch (JSONException e) {  
//                            e.printStackTrace();
                        	status = false;
                        }  
                    }  
                } catch (IOException e) {  
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
  
    //获取歌词地址  
//    public String getLrcAdress(final String songid){  
//        new Thread(new Runnable() {  
//            @Override  
//            public void run() {  
//                try {  
//                    HttpURLConnection connection;  
//                    //URL url = new URL("http://api.5288z.com/weixin/musicapi.php?q="+finalTitle);  
//                    URL url = new URL("http://ting.baidu.com/data/music/links?songIds="+songid);  
//                    connection = (HttpURLConnection) url.openConnection();  
//                    connection.setConnectTimeout(60*1000);  
//                    connection.setReadTimeout(60*1000);  
//                    connection.connect();  
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
//                    String s;  
//                    if ((s=reader.readLine())!=null){  
//                        s = s.replace("\\","");//去掉\\  
//                        Log.v("tag","------"+s);  
//                        try {  
//                            JSONObject object = new JSONObject(s);  
//                            JSONObject object1 = object.getJSONObject("data");  
//                            JSONArray array = object1.getJSONArray("songList");  
//                            JSONObject object2 = array.getJSONObject(0);  
//                            LrcAdress = object2.getString("lrcLink");  
//                            Log.v("tag","888888lrc"+ LrcAdress);  
//  
//                        } catch (JSONException e) {  
//                            e.printStackTrace();  
//                        }  
//                    }  
//                } catch (IOException e) {  
//                    e.printStackTrace();  
//                }  
//            }  
//        }).start();  
//        try {  
//            Thread.sleep(500);  
//        } catch (InterruptedException e) {  
//            e.printStackTrace();  
//        }  
//        return LrcAdress;  
//    }  
	public static void main(String[] args) {
		search("告白气球");
	}
}
