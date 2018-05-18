package search_and_download;

public class Test {
	static int i = 0;
	public static void main(String[] args) {
		
		new Thread(new Runnable() {  
            @Override  
            public void run() {System.out.println("A: "+" "+i);  
                for (int k = 0;k<100000;k++) {i++;
                }
            }  
        }).start(); 
		new Thread(new Runnable() {  
            @Override  
            public void run() {System.out.println("B: "+" "+i);  
            	for (int k = 0;k<100000;k++) {i--;
            	}
            }  
        }).start();System.out.println("C: "+" "+i); for (int k = 0;k<100000;k++) {i++;
        }
	}
}
