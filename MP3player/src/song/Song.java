public class Song {
    private String id; // 歌曲编号 考虑到我们可能会下载两首同名歌曲 所以歌曲统一以 编号.mp3 命名格式保存在本地
    //我们会在本地建一个txt文件记录已经下载的歌曲的id和歌名、歌手、文件路径等用于本地检索的信息
    private String name;
    private String artist_name;
    private String address; //  歌曲网上链接
    private String dir; //  歌曲文件本地路径
    private String lyrics; // 备用功能
    // 预留专辑封面.jpg属性

//    Song(String name,String artist_name,String address,String lyrics){
//        this.name = name;
//        this.artist_name = artist_name;
//        this.address = address;
//        this.lyrics = lyrics;
//    }

    void setId(String id){
        this.id = id;
    }
    void setName(String name){
        this.name = name;
    }
    void setArtist_name(String artist_name){
        this.artist_name = artist_name;
    }
    void setAddress(String address){
        this.address =address;
    }
    void setDir(String dir){
        this.dir = dir;
    }
    void setLyrics(String lyrics){
        this.lyrics = lyrics;
    }
    String getId(){
        return id;
    }
    String getName(){
        return name;
    }
    String getArtist_name(){
        return artist_name;
    }
    String getAddress(){
        return address;
    }
    String getDir(){
        return dir;
    }
    String getLyrics(){
        return lyrics;
    }
}
