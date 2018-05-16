import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

public class Client {
    private JTextField searchText;
    private Player player;
    private JList musicList;
    private String musicPath = "C:\\Users\\牟宇\\Music";
    private ArrayList<String> musicLists;

    private void go(){
        JPanel mainPanel = new JPanel();
        //搜索框
        searchText = new JTextField("Search for musics...",20);
        searchText.addMouseListener(new searchTextMouseListener());
        //歌曲列表
        musicList = new JList(getMusicList(musicPath).toArray());
        musicList.setBorder(BorderFactory.createTitledBorder("歌曲列表   共" + musicLists.size() + "首"));
        musicList.setFixedCellWidth(450);
        musicList.setVisibleRowCount(12);
        //歌曲列表滚动条
        JScrollPane qScroller = new JScrollPane(musicList);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //搜索按钮
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new searchButtonActionListener());
        //播放按钮
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new playButtonActionListener());
        //停止按钮
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new stopButtonActionListener());

        mainPanel.add(searchText);
        mainPanel.add(searchButton);
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
    private ArrayList<String> getMusicList(String musicPath){
        musicLists = new ArrayList<>();
        File file = new File(musicPath);
        File[] array = file.listFiles();
        assert array != null;
        for (File anArray : array) {
            if (anArray.isFile() && anArray.getName().endsWith(".mp3")) {
                musicLists.add(anArray.getName());
            }
        }
        return musicLists;
    }
    public static void main(String[] args){
        Client client = new Client();
        client.go();

    }

    private class searchButtonActionListener implements ActionListener {
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
            String musicName = (String)musicList.getSelectedValue();
            player = new Player(musicPath + "\\" + musicName);
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
