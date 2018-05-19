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
import org.json.Test;

import song.Song;

public class Search {
	static ArrayList<Song> list = new ArrayList<Song>();//每次搜索都会更新
	static int state;//-2：没有搜索结果；-3：网络或服务器存在异常
	public static void search(String name_) {
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
//                    	System.out.println(s);
                        doJson(s);
//                    System.out.println("w");
//                    	System.out.println(s);
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }).start();  
	}
	private static void doJson(String json) {
//		System.out.println("entry");
		JSONObject jsonObject = null;  
        list.clear();//清空原有的歌曲数组  
        try {  
            //去掉括号  
            json = json.replace("(","");  
            json = json.replace(")","");  
            jsonObject = new JSONObject(json);  
            JSONArray array = new JSONArray(jsonObject.getString("song"));
            int i = 0, j = 0;
//            System.out.println(array.length());
            while (j<array.length()){  
                JSONObject object = array.getJSONObject(j);
                j++;
                String songname = object.getString("songname");  
                String artistname = object.getString("artistname");  
                String songid = object.getString("songid");
                
                Song song1 = new Song();
                
                song1.setId(songid);
                song1.setName(songname);
                song1.setArtist_name(artistname);
                getAdress(song1);
//                song1.setLrcPath(getLrcAdress(songid));
                if (state == 0 || state == -1) {
                	System.out.println(state+" failed!");
                	continue;
                }
                if (state == 1) {
                	System.out.println(i+" "+song1.getId()+" "+song1.getArtist_name());
                	list.add(i,song1);
                	i++;
                }
            }
            if (list.size() == 0) {
            	if (state == 0) state = -2;//没有搜索结果
            	if (state == -1) state = -3;//网络或服务器存在异常
            }
            System.out.println(list.size());
        } catch (JSONException e) {  
            e.printStackTrace();  
        }
//        System.out.println("exit");
	}
	private static void getAdress(Song song){    
                try {  
                    HttpURLConnection connection;
//                    System.out.println(song.getId());
                    URL url = new URL("http://ting.baidu.com/data/music/links?songIds="+song.getId());  
                    connection = (HttpURLConnection) url.openConnection();  
                    connection.setConnectTimeout(60*1000);  
                    connection.setReadTimeout(60*1000);  
                    connection.connect();  
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
                    String s;  
                    if ((s=reader.readLine())!=null){
                    	System.out.println(s);
//                    	File fp=new File(song.getId()+".txt");
//                        PrintWriter pfp= new PrintWriter(fp);
//                        pfp.print(s);
//                        pfp.close();
                        s = s.replace("\\","");//去掉\\
//                        String s1 = new String(s);
                        try {  
                            JSONObject object = new JSONObject(s);  
                            JSONObject object1 = object.getJSONObject("data");  
                            JSONArray array = object1.getJSONArray("songList");  
                            JSONObject object2 = array.getJSONObject(0);
                            song.setAddress(object2.getString("songLink"));
                            state = 1;
                            if (song.getAddress().equals(""))state = 0;
                        } catch (JSONException e) {  
//                            e.printStackTrace();
                        	state = 0;
                        }  
                    }  
                } catch (IOException e) {
                	state = -1;
//                    e.printStackTrace();  
                }  
//        try {  
//            Thread.sleep(500);  
//        } catch (InterruptedException e) {  
//            e.printStackTrace();  
//        }
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
		search("exit music");
		try {
			Thread.sleep(6000);
		} catch (Exception e) {
			// TODO: handle exception
		}
        Download d = new Download(list.get(6));
        d.runDownload();
	}
}
