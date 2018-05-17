package search_and_download;

import song.Song;
public class Search {
	String[] list;
	public static final void search(Song song) {
		GetRun getRun = new GetRun("http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.search.catalogSug&format=json&callback=&query="+song.getName());
		getRun.start();
	}
}
