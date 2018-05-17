package search_and_download;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
public class GetRun extends Thread{
	String url;
	StringBuilder response;
	public GetRun(StringBuilder response, String url) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.response = response;
	}
	@Override
    public void run() {
        try {
            //doctype=xml/json/jsonp
//            URL url = new URL("http://ting.baidu.com/data/music/links?songIds=266322598");
        	URL url = new URL(this.url);
            URLConnection connection = url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(in,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
//            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null)
            {
                response.append(line);
            }
            br.close();
            isr.close();
            in.close();
            System.out.println(response.hashCode());
//            System.out.println(response.toString()+"\n");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
