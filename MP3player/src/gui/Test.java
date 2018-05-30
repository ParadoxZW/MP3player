package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.Thread.State;

import javax.swing.*;  
import javax.swing.event.*;  
  
public class Test extends JFrame implements ChangeListener{
	private int state;
    private JPanel jp = new JPanel();  
    private int time = 0;
    private long begintime;
    private JProgressBar progressBar = new JProgressBar(0,10000); //指定最小值，最大值  
    private JSlider js = new JSlider(0,10000); //指定最小值，最大值  
    JLabel jl1 = new JLabel("进度条：");  
    JLabel jl2 = new JLabel("拖动滑块可以控制进度条");  
    public Test()  
    {  
        jp.setLayout(null);  
        jl1.setBounds(10, 10, 200, 30);//设置标签的大小位置  
        jl2.setBounds(10, 70, 200, 30);  
        jp.add(jl1);  
        jp.add(jl2);  
        progressBar.setBounds(20, 40, 300, 20);  
        js.setBounds(10, 110, 320, 50);  
        jp.add(progressBar);  
        jp.add(js);  
        progressBar.setValue(50);  
        progressBar.setStringPainted(true);//设置进度条将显示信息字符串  
        js.setPaintTicks(true);//设置滑块绘制刻度标记  
        js.setPaintLabels(true); //设置主刻度标记的状态  
        js.setMajorTickSpacing(10);//设置主刻度标记的间隔  
        js.setMinorTickSpacing(2);//设置副刻度标记的间隔  
        js.addChangeListener(this);
        js.addMouseListener(new myMouseListener());
        js.addMouseMotionListener(new myMouseMotionListener());
        this.add(jp);  
        this.setTitle("滑块控制进度条");  
        this.setResizable(false);  
        this.setBounds(200, 200, 350, 200);  
        this.setVisible(true);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
    public void stateChanged(ChangeEvent c)  
    {  
        progressBar.setValue(js.getValue());  
    }  
    public static void main(String args[])  
    {  
        Test test = new Test();
        test.state = 1;
        test.sliderAction();
    }
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
			state = -1;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			int t = js.getValue();
			long n = System.currentTimeMillis();
			begintime = n - t;
			state = 1;
		}
    	
    }
    private class myMouseMotionListener implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    private void sliderAction() {
    	new Thread(new Runnable() {  
            @Override  
            public void run() {
            	begintime = System.currentTimeMillis();
                while (state != 0) {
                	if (state == 1) { 
	                	long nowtime = System.currentTimeMillis();
	                	time = (int)(nowtime - begintime);
	                	js.setValue(time);
                	}
                	else if(state == -1) continue;
                }
                js.setValue(0);
            }  
    	}).start();
    }
}  
