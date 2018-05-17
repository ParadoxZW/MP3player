import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Search_Client {
    private JList searchList;
    private JTextField searchText;
    private ArrayList<String> searchLists = new ArrayList<>();

    public void go(String text){
        JPanel mainPanel = new JPanel();
        //搜索框
        searchText = new JTextField(text,20);
        //搜索列表
        searchList = new JList(searchLists.toArray());
        searchList.setBorder(BorderFactory.createTitledBorder("歌曲列表   共" + searchLists.size() + "首"));
        searchList.setFixedCellWidth(450);
        searchList.setVisibleRowCount(15);
        //歌曲列表滚动条
        JScrollPane qScroller = new JScrollPane(searchList);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //搜索按钮
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new searchButtonActionListener());

        //添加控件到Panel上
        mainPanel.add(searchText);
        mainPanel.add(searchButton);
        mainPanel.add(qScroller);

        //整体框架设置
        JFrame frame = new JFrame("搜索结果");
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(500,380);
        frame.setVisible(true);

    }
    public void printRes(Song song){
        String music = song.getArtist_name() + " - " + song.getName();
        searchLists.add(music);
    }

    private class searchButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
