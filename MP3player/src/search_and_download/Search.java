package search_and_download;
import java.util.ArrayList;
import song.Song;

public class Search {
	public static final ArrayList<Song> search(String name) {
		/**
		 * 输入歌名，返回歌曲数组 */
		ArrayList<Song> list;
		list = new ArrayList<Song>();
		StringBuilder response = new StringBuilder();
		get(name, response);
		System.out.println(response.hashCode());
		System.out.println(response.toString());
		return list;
	}
	private static void get(String name, StringBuilder response) {
		GetRun getRun = new GetRun(response, "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.search.catalogSug&format=json&callback=&query="+name);
		getRun.start();	
	}
	public static void main(String[] args) {
		search("告白气球");
		System.out.println("1");
	}
}
