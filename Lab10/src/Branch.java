import java.awt.*;
import java.awt.geom.AffineTransform;

public class Branch implements XmasShape {
    int x;
    int y;
    double scale;
    Color fillColor;

    public Branch(int x, int y, double scale, Color fillColor) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.fillColor=fillColor;
    }

    @Override
    public void render(Graphics2D g2d) {
        GradientPaint grad = new GradientPaint(x,y,fillColor,0,100, fillColor);
        g2d.setPaint(grad);
        //int x[]={286,253,223,200,148,119,69,45,0};
        //int y[]={0,101,89,108,79,95,66,80,56};
        int x[]={286,572,527,503,453,424,372,349, 285,223,200,148,119,69,45,0};
        int y[]={0,56,80,66,95,79,108,89, 117,89,108,79,95,66,80,56};

        g2d.fillPolygon(x,y,x.length);


    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

}
