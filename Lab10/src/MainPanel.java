import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel {
    public static void main(String[] args) {


        JFrame frame = new JFrame("Choinka");
        frame.setContentPane(new DrawPanel());
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

    }
}
