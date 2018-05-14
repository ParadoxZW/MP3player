import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MP3Player implements Runnable{
    Thread t;
    private String dir;
    private Player player;
    public MP3Player(String dir){   //创建MP3Play的对象，并传入歌曲本地路径
        this.dir = dir;
    }
    public void play(){     //调用play()方法就可以播放了
        try{
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(dir));
            player = new Player(buffer);
            player.play();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    public void stop(){     //调用stop()方法可以让歌曲停止播放
        player.close();
    }
    public int getPosition(){
        return player.getPosition();
    }
    @Override
    public void run(){      //用线程启动play()，这样才能正常使用stop()
        play();
    }
    public void start(){
        t = new Thread(this);
        t.start();
    }

    public static void main(String[] args){
        MP3Player mp3 = new MP3Player("C:\\Users\\牟宇\\Music\\赵雷 - 成都.mp3"); //创建MP3Play的对象，并传入歌曲本地路径
        mp3.start(); //调用play()方法就可以播放了
        try {
            Thread.currentThread().sleep(5000);  //让线程睡眠5秒
//            mp3.stop();     //测试是否能让歌曲停止播放
            System.out.println(mp3.getPosition());      //获取当前歌曲从播放开始的毫秒数
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
