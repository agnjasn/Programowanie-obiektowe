import java.awt.*;

public class Light implements XmasShape {
    int x;
    int y;
    double scale;
    Color lineColor;
    Color fillColor;

    public Light(int x, int y, double scale, Color lineColor, Color fillColor) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.lineColor=lineColor;
        this.fillColor=fillColor;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(lineColor);
        g2d.fillOval(0,0,5,5);
        g2d.setColor(fillColor);
        g2d.drawOval(0,0,5,5);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }
}
