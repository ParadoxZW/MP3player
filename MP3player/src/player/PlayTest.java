package player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.spec.ECPrivateKeySpec;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import javazoom.jl.player.PlayerApplet;
import javazoom.jl.player.advanced.*;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.*;
import javazoom.jl.player.*;

public class PlayTest implements Runnable{
    Thread t;
    public int state; //0不处于播放状态；1正在播放；-1 记时器暂停 不影响播放
    private int length;
    public int start;
    private String dir;
    BufferedInputStream buffer;
    private AdvancedPlayer player;
    public PlayTest(String dir){   //创建MP3Play的对象，并传入歌曲本地路径
        this.dir = dir;
        getLength();
        
		try {
			buffer = new BufferedInputStream(new FileInputStream(dir));
			player = new AdvancedPlayer(buffer);
		} catch (FileNotFoundException | JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void setDir(String dir){
        this.dir = dir;        
    }
    public int Length() {
		return length;
	}
    private void play(){     //调用play()方法就可以播放了
        try{
        	player = new AdvancedPlayer(buffer);
//            state = true;
        	pbListener();
            player.play(start, length*1000);
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    public void stop(){     //调用stop()方法可以让歌曲停止播放
    	System.out.println("1");
        player.close();
    }
    public void pbListener() {
    	PlaybackListener pbl = new TestPlaybackListener();
    	player.setPlayBackListener(pbl);
	}
//    public int getLarge(){
//    	
//        return player.();
//    }
    private class TestPlaybackListener extends PlaybackListener {
		@Override
		public void playbackFinished(PlaybackEvent e) {
			// TODO Auto-generated method stub
			super.playbackFinished(e);
			state = 0;
		}
		@Override
		public void playbackStarted(PlaybackEvent e) {
			// TODO Auto-generated method stub
			super.playbackStarted(e);
		}
	}
    @Override
    public void run(){      //用线程启动play()，这样才能正常使用stop()
        play();
    }
    public void start(int st){
    	start = st;
    	if (state == -1) stop();
        t = new Thread(this);
        t.start();
    }
    private void getLength() {
    	File file = new File(dir);  

        try {  

            MP3File f = (MP3File)AudioFileIO.read(file);  

            MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();  

            length = audioHeader.getTrackLength();     

        } catch(Exception e) {  

            e.printStackTrace();  

        }  
	}

//    public static void main(String[] args){
//        Player mp3 = new Player("C:\\Users\\牟宇\\Music\\赵雷 - 成都.mp3"); //创建MP3Play的对象，并传入歌曲本地路径
//        mp3.start(); //调用play()方法就可以播放了
//        try {
//            Thread.currentThread().sleep(5000);  //让线程睡眠5秒
////            mp3.stop();     //测试是否能让歌曲停止播放
//            System.out.println(mp3.getPosition());      //获取当前歌曲从播放开始的毫秒数
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
