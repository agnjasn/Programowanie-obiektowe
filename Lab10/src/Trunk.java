import java.awt.*;

public class Trunk implements XmasShape{
    int x;
    int y;
    double scale;
    Color fillColor;

    public Trunk(int x, int y, double scale, Color fillColor) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.fillColor=fillColor;
    }

    @Override
    public void render(Graphics2D g2d) {
        GradientPaint grad = new GradientPaint(x,y,fillColor,0,100, fillColor);
        g2d.setPaint(grad);
        int x[]={0, 60, 60, 0};
        int y[]={0, 0, 90, 90};

        g2d.fillPolygon(x,y,x.length);


    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }
}
