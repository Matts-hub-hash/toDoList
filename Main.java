import javax.swing.JFrame;

public class Main{
    public static void main(String[] args){
        Frame frame = new Frame();

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("To do list");
        frame.setLocationRelativeTo(null);
    }
}