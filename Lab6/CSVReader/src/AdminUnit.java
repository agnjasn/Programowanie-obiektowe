import java.util.ArrayList;
import java.util.List;

public class AdminUnit {
    String name;
    Integer adminLevel;
    Double population;
    Double area;
    Double density;
    AdminUnit parent;
    BoundingBox bbox = new BoundingBox();
    List<AdminUnit> children= new ArrayList<>();

    String tostring()
    {
        String str=name+" "+Integer.toString(adminLevel)+" "+Double.toString(population)+" "+Double.toString(area)+" "+Double.toString(density)+" "+bbox.tostring();
        return str;
    }



}
