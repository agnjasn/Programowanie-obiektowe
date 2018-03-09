public class BoundingBox {
    double xmin=Double.NaN;
    double ymin=Double.NaN;
    double xmax=Double.NaN;
    double ymax=Double.NaN;

    /**
     * Powiększa BB tak, aby zawierał punkt (x,y)
     * @param x - współrzędna x
     * @param y - współrzędna y
     */
    void addPoint(double x, double y){
        if(!this.isEmpty()) {
            if (x < xmin) xmin = x;
            if(x>xmax) xmax = x;
            if (y<ymin) ymin=y;
            if(y>ymax) ymax = y;
        }
        else {
            xmin = x;
            ymin = y;
            xmax = x;
            ymax =y;
        }
    }

    /**
     * Sprawdza, czy BB zawiera punkt (x,y)
     * @param x
     * @param y
     * @return
     */
    boolean contains(double x, double y){
        if(x>xmin && x<xmax && y>ymin && y<ymax) return true;
        else return false;
    }

    /**
     * Sprawdza czy dany BB zawiera bb
     * @param bb
     * @return
     */
    boolean contains(BoundingBox bb){
        if(this.contains(bb.xmin, bb.ymin) && this.contains(bb.xmax,bb.ymax)) return true;
        else return false;
    }

    /**
     * Sprawdza, czy dany BB przecina się z bb
     * @param bb
     * @return
     */
    boolean intersects(BoundingBox bb){
        if(this.contains(bb.xmax, bb.ymax) || this.contains(bb.xmin, bb.ymax) || this.contains(bb.xmax, bb.ymin)  || this.contains(bb.xmin, bb.ymin) ) return true;
        else return false;
    }
    /**
     * Powiększa rozmiary tak, aby zawierał bb oraz poprzednią wersję this
     * @param bb
     * @return
     */
    BoundingBox add(BoundingBox bb){
        if(!this.contains(bb))
        {
            this.addPoint(bb.xmin, bb.ymin);
            this.addPoint(bb.xmax, bb.ymax);

        }
        return this;
    }
    /**
     * Sprawdza czy BB jest pusty
     * @return
     */
    boolean isEmpty(){
        if (Double.isNaN(xmin))
        return true;
        else return false;
    }

    /**
     * Oblicza i zwraca współrzędną x środka
     * @return if !isEmpty() współrzędna x środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterX(){
        if(!this.isEmpty()) {
            return (xmin + xmax) / 2;
        }
        else throw new RuntimeException("Not implemented");
    }
    /**
     * Oblicza i zwraca współrzędną y środka
     * @return if !isEmpty() współrzędna y środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterY(){
        if(!this.isEmpty()) {
            return (ymin + ymax) / 2;
        }
        else throw new RuntimeException("Not implemented");
    }

    /**
     * Oblicza odległość pomiędzy środkami this bounding box oraz bbx
     * @param bbx prostokąt, do którego liczona jest odległość
     * @return if !isEmpty odległość, else wyrzuca wyjątek lub zwraca maksymalną możliwą wartość double
     * Ze względu na to, że są to współrzędne geograficzne, zamiast odległosci euklidesowej możesz użyć wzoru haversine
     * (ang. haversine formula)
     */
    double distanceTo(BoundingBox bbx){
        if(!this.isEmpty() && !bbx.isEmpty())
        {
            double thisx=getCenterX();
            double thisy=getCenterY();
            double bx=bbx.getCenterX();
            double by=bbx.getCenterY();

            return Haversine.distance(thisx,thisy,bx,by);
        }
        else return Double.MAX_VALUE;

       // throw new RuntimeException("Not implemented");
    }

    String tostring()
    {
        String str="("+Double.toString(xmin)+" "+Double.toString(ymin)+","+Double.toString(xmax)+" "+Double.toString(ymax)+")";
        return str;
    }



}

