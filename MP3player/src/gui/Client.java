package gui;
import player.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import search_and_download.Download;
import search_and_download.FileManager;
import search_and_download.Search;
import song.Song;

public class Client {
    private JTextField searchText;
    private Player player;
    private JList musicList;
    private String musicPath = "Music";
    private ArrayList<Song> songlist;//musicList对应的Song列表
    private ArrayList<String> stringlist;//songlist对应的字符串列表

    @SuppressWarnings("unchecked")
	private void go(){
        JPanel mainPanel = new JPanel();
        //搜索框
        searchText = new JTextField("Search for musics...",20);
        searchText.addMouseListener(new searchTextMouseListener());
        //歌曲列表
        musicList = new JList(getMusicList().toArray());
        musicList.setBorder(BorderFactory.createTitledBorder("歌曲列表   共" + stringlist.size() + "首"));
        musicList.setFixedCellWidth(450);
        musicList.setVisibleRowCount(12);
        //歌曲列表滚动条
        JScrollPane qScroller = new JScrollPane(musicList);
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

        mainPanel.add(searchText);
        mainPanel.add(searchButton);
        mainPanel.add(localButton);
        mainPanel.add(downButton);
        mainPanel.add(qScroller);
        mainPanel.add(playButton);
        mainPanel.add(stopButton);


        //整体框架设置
        JFrame frame = new JFrame("网易云音乐");
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

    }

    private class searchButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
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
        	
        	musicList.setListData(stringlist.toArray());
        }
    }
    private class downButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	Download d = new Download(songlist.get(musicList.getSelectedIndex()));
        	d.runDownload();
        	if (player != null) player.stop();
        	player = new Player(songlist.get(musicList.getSelectedIndex()).getDir());
            player.start();
        }
    }
    private class localButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

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

    private class playButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (player != null) player.stop();
            Song so = songlist.get(musicList.getSelectedIndex());
            player = new Player(so.getDir());
            player.start();
        }
    }

    private class stopButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.stop();
        }
    }
}
