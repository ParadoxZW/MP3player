package gui;
import player.PlayTest;
import player.Player;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicBorders.ToggleButtonBorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import search_and_download.Download;
import search_and_download.FileManager;
import search_and_download.Search;
import song.Song;

public class Client {
    private JTextField searchText;
    private PlayTest player;
    private JList musicList;
    private JSlider js;
    private int time;
    private int state;
    private long begintime;
    JTextArea jTextArea1;
    JTextArea jTextArea2;
//    private String musicPath = "Music";
    private ArrayList<Song> songlist;//musicList对应的Song列表
    private ArrayList<String> stringlist;//songlist对应的字符串列表

    @SuppressWarnings("unchecked")
	private void go(){
        JPanel mainPanel = new myPanel();
        //搜索框
        searchText = new JTextField("Search for musics...",20);
        searchText.addMouseListener(new searchTextMouseListener());
        //歌曲列表
        musicList = new JList(getMusicList().toArray());
        musicList.setBackground(new Color(205, 205, 205, 0));
        musicList.setOpaque(false);
        musicList.setBorder(BorderFactory.createTitledBorder("本地歌曲列表   共" + stringlist.size() + "首"));
        musicList.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount() ==2) {
					if (state == 0) playmusic(0, true);
					else if (state == 1) down();
				}
			}
		});

        musicList.setFixedCellWidth(450);
        musicList.setVisibleRowCount(12);
        //歌曲列表滚动条
        JScrollPane qScroller = new JScrollPane(musicList);
        qScroller.setOpaque(false);  
        qScroller.getViewport().setOpaque(false); 
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //搜索按钮
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new searchButtonActionListener());
        //搜索按钮
        JButton downButton = new JButton("down");
        downButton.addActionListener(new downButtonActionListener());
        //本地搜索按钮
        JButton localButton = new JButton("local");
        localButton.addActionListener(new localButtonActionListener());
        //播放按钮
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new playButtonActionListener());
        //停止按钮
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new stopButtonActionListener());
        //进度条
        js = new JSlider(); 
        js.addMouseListener(new myMouseListener());
        js.setValue(0);
        //进度显示
        jTextArea1 = new JTextArea("00:00");
        jTextArea2 = new JTextArea("00:00");

        mainPanel.add(searchText);
        mainPanel.add(searchButton);
        mainPanel.add(localButton);
        mainPanel.add(downButton);
        mainPanel.add(qScroller);
        mainPanel.add(jTextArea1);
        mainPanel.add(js);
        mainPanel.add(jTextArea2);
        mainPanel.add(playButton);
        mainPanel.add(stopButton);


        //整体框架设置
        JFrame frame = new JFrame("网易云音乐");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("pic\\icon.jpg"));
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(500,380);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private ArrayList<String> getMusicList(){
        stringlist = new ArrayList<String>();
//        File file = new File(musicPath);
//        File[] array = file.listFiles();
//        assert array != null;
//        for (File anArray : array) {
//            if (anArray.isFile() && anArray.getName().endsWith(".mp3")) {
//                songlist.add(anArray.getName());
//            }
//        }
        FileManager.logRead();
        songlist = FileManager.locallist;
        for (int i = 0; i < FileManager.locallist.size(); i++) {
        	Song so = songlist.get(i);
        	stringlist.add(so.getName()+"	   "+so.getArtist_name());
        }
        return stringlist;
    }
    public static void main(String[] args){
        Client client = new Client();
        client.go();
//        client.test();
    }
//    private class sliderListener implements ChangeListener{
//
//		@Override
//		public void stateChanged(ChangeEvent e) {
//			// TODO Auto-generated method stub
//			if (flag == false) {
//			int t = js.getValue();
//			if (player != null) player.stop();
//            Song so = songlist.get(musicList.getSelectedIndex());
//            player = new PlayTest(so.getDir());
//			player.start = t;
//			player.start();
//			begintime += (time - t);
//			}
//		}
//    	
//    }
    private class myMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			player.state = -1;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			int t = js.getValue();
			long n = System.currentTimeMillis();
			begintime = n - t;
			playmusic(t / 25, false);
			player.state = 1;
		}
    	
    }
    private class searchButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	state = 1;
        	Search.search(searchText.getText());
        	try {  
                Thread.sleep(3000);  
            } catch (InterruptedException e1) {  
                e1.printStackTrace();  
            }
        	songlist = Search.list;
        	stringlist = new ArrayList<String>();
        	for (int i = 0; i < songlist.size(); i++) {
            	Song so = songlist.get(i);
				stringlist.add(so.getName()+"	   "+so.getArtist_name());
            }
        	musicList.setBorder(BorderFactory.createTitledBorder("搜索到   共" + stringlist.size() + "首"));
        	musicList.setListData(stringlist.toArray());
        }
    }
    
    private class localButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	state = 0;
        	stringlist = getMusicList();
        	musicList.setListData(stringlist.toArray());
            musicList.setBorder(BorderFactory.createTitledBorder("本地歌曲列表   共" + stringlist.size() + "首"));
           
        }
    }
    private class searchTextMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            searchText.setText("");
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
	private class downButtonActionListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	down();
	        }
	    }
	private void down() {
		Download d = new Download(songlist.get(musicList.getSelectedIndex()));
    	d.runDownload();
    	playmusic(0, true);
	}
    private class playButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playmusic(0, true);
        }
    }
    private void playmusic(int t, boolean bool) {
    	if (player != null) {
//    		int tt = js.getValue();
    		if (bool) player.state = 0;
    		player.stop();
    	}
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Song so = songlist.get(musicList.getSelectedIndex());
        player = new PlayTest(so.getDir());
        if (bool){
        	js.setMaximum(player.Length()*1000);
        	jTextArea2.setText(String.format("%02d:%02d", player.Length() / 60, player.Length() % 60));
        	sliderAction();
        }
        player.start(t);
	}
    private void sliderAction() {
    	player.state = 1;
    	new Thread(new Runnable() {  
            @Override  
            public void run() {
            	begintime = System.currentTimeMillis();
                while (player.state != 0) {
                	int s = js.getValue() / 1000;
                	jTextArea1.setText(String.format("%02d:%02d", s / 60, s % 60));
                	if (player.state == 1) {
//                		System.out.println("A");
	                	long nowtime = System.currentTimeMillis();
//	                	System.out.println("B");
	                	time = (int)(nowtime - begintime);
//	                	System.out.println("C");
	                	js.setValue(time);
//	                	System.out.println("D");
                	}
                	else {
//                		System.out.println("E");
                		try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		continue;
					}
                }
                js.setValue(0);
                jTextArea1.setText("00:00");
                jTextArea2.setText("00:00");
            }  
    	}).start();
    }
    private class stopButtonActionListener implements ActionListener {
		@Override
        public void actionPerformed(ActionEvent e) {
        	player.state = 0;
            player.stop();
        }
    }
   private class myPanel extends JPanel {  
    ImageIcon icon;  
    Image img;  
    public myPanel() {  
        //  /img/HomeImg.jpg 是存放在你正在编写的项目的bin文件夹下的img文件夹下的一个图片  
        icon=new ImageIcon("pic\\bg.jpg");  
        img=icon.getImage();  
    }  
    public void paintComponent(Graphics g) {  
        super.paintComponent(g);  
        //下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小  
        g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);  
    }  
  
}  
}
