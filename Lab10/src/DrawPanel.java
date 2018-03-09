import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel{
    List<XmasShape> shapes = new ArrayList<XmasShape>();
    public DrawPanel() {
        setBackground(Color.red);
    int x=260;
    int y=20;
    double scale= 0.25;
    Color c1= new Color(139,69,19);

        Trunk t1=new Trunk(300, 230, 1, c1);
        shapes.add(t1);

        for(int i=1; i<5; i++)
        {
        Branch br1=new Branch(x,y, scale,  Color.green);
        shapes.add(br1);
        x-=72;
        y+=(20*i);
        scale+=0.25;
        }

        x=200;
        y=80;
        int x2=440;
        int y2=95;
        for(int i=1; i<4; i++)
        {
            Bubble b1=new Bubble(x,y,0.20, Color.blue, Color.cyan);
            shapes.add(b1);
            Bubble b12=new Bubble(320,y2,0.20, Color.blue, Color.cyan);
            shapes.add(b12);
            Bubble b2=new Bubble(x2,y,0.20, Color.blue, Color.cyan);
            shapes.add(b2);

            x-=60;
            x2+=60;
            if(i==2) y+=80;
            else y+=60;
            if(i==2) y2+=85;
            else y2+=75;
        }

       Star s1=new Star(259,3,0.25,Color.yellow);
       shapes.add(s1);

       for(int i=1; i<16; i++)
       {
           Light l1 = new Light(285+(i*6), 25+i, 1, Color.yellow, Color.orange);
           shapes.add(l1);
       }

        for(int i=1; i<31; i++)
        {
            Light l1 = new Light(245+(i*6), 50+i, 1, Color.yellow, Color.orange);
            shapes.add(l1);
        }

        for(int i=1; i<45; i++)
        {
            Light l1 = new Light(200+(i*6), 100+i, 1, Color.yellow, Color.orange);
            shapes.add(l1);
        }

        for(int i=1; i<60; i++)
        {
            Light l1 = new Light(150+(i*6), 170+i, 1, Color.yellow, Color.orange);
            shapes.add(l1);
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(XmasShape s:shapes){
            s.draw((Graphics2D)g);
        }


    }

}
