import java.awt.*;

public class Star implements XmasShape {
    int x;
    int y;
    double scale;
    Color fillColor;

    public Star(int x, int y, double scale, Color fillColor) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.fillColor=fillColor;
    }

    @Override
    public void render(Graphics2D g2d) {
        GradientPaint grad = new GradientPaint(x,y,fillColor,0,100, fillColor);
        g2d.setPaint(grad);
        int x[]={290, 310, 340, 315, 330, 290,   250, 265, 240, 270 };
        int y[]={0, 30, 30, 55, 90, 65,   90, 55, 30, 30};
        g2d.fillPolygon(x,y,x.length);

    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

}
