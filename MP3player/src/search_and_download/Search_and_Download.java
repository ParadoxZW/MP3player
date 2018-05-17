import com.show.api.ShowApiRequest;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Search_and_Download {
    Search_Client search_client = new Search_Client();
    public String getRes(String keyword) {
        String res = new ShowApiRequest("http://route.showapi.com/213-1", "65276", "fffbe40c401a4d53bdf73deb44fd8128")
                .addTextPara("keyword", keyword)
                .addTextPara("page", "1")
                .post();
        return res;
    }
    public Search_Client doJson(String res){
        JSONObject jsonObject = JSONObject.fromObject(res);
        JSONObject jsonObject1 = JSONObject.fromObject(jsonObject.getString("showapi_res_body"));
        JSONObject jsonObject2 = JSONObject.fromObject(jsonObject1.getString("pagebean"));
        JSONArray jsonArray = JSONArray.fromObject(jsonObject2.getString("contentlist"));

        for (int i=0;i<jsonArray.size();i++){
            JSONObject jsonTemp = jsonArray.getJSONObject(i);
            Song song = new Song();
            song.setName(jsonTemp.getString("songname"));
            song.setArtist_name(jsonTemp.getString("singername"));
            song.setAddress(jsonTemp.getString("downUrl"));
            search_client.printRes(song);
        }
        return search_client;
    }


}
