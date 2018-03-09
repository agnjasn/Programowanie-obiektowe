import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;

public class AdminUnitList
{
    List<AdminUnit> units = new ArrayList<>();



    public void read(String filename) throws IOException {
        CSVReader reader= new CSVReader(filename, ",", true);
        Map<Long, AdminUnit> id_unit= new HashMap<>();
        Map<AdminUnit, Long> unit_parentid= new HashMap<>();
        Map<Long,List<AdminUnit>> parentid2child = new HashMap<>();
        //int licznik=0;
        while(reader.next()){
           // if(licznik==500) break;
            AdminUnit unit= new AdminUnit();

            long parentid= reader.getLong("parent");
            long id = reader.getLong("id");

            id_unit.put(id, unit);
            unit_parentid.put(unit, parentid);
            parentid2child.put(id, unit.children);

            unit.name = reader.get("name");
            unit.adminLevel= reader.getInt("admin_level");
            unit.population= reader.getDouble("population");
            unit.area = reader.getDouble("area");
            unit.density = reader.getDouble("density");
            double  pointx = reader.getDouble("x1");
            double  pointy = reader.getDouble("y1");
            unit.bbox.addPoint(pointx, pointy);
            pointx = reader.getDouble("x2");
            pointy = reader.getDouble("y2");
            unit.bbox.addPoint(pointx, pointy);
            pointx = reader.getDouble("x3");
            pointy = reader.getDouble("y3");
            unit.bbox.addPoint(pointx, pointy);
            pointx = reader.getDouble("x4");
            pointy = reader.getDouble("y4");
            unit.bbox.addPoint(pointx, pointy);
            pointx = reader.getDouble("x11");
            pointy = reader.getDouble("y11");
            unit.bbox.addPoint(pointx, pointy);

            units.add(unit);

           // licznik++;
        }
        for(int i=0; i<units.size(); i++)
        {
            long id=unit_parentid.get(units.get(i));
            if(id_unit.get(id)!=units.get(i))
            {
            units.get(i).parent=id_unit.get(id);
            if(parentid2child.containsKey(id)) {
                parentid2child.get(id).add(units.get(i));
            }
            }
        }

    }

    void list(PrintStream out){
        for(int i=0; i<units.size(); i++)
        {
            out.println(units.get(i).tostring());
        }
    }

    void list(PrintStream out,int offset, int limit ){
        for(int i=offset; i<=(offset+limit); i++)
        {
            out.println(units.get(i).tostring());
        }
    }

    AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList ret = new AdminUnitList();
        for(int i=0; i<units.size(); i++)
        {
            if(regex) {
                if (units.get(i).name.matches(pattern)) ret.units.add(units.get(i));
            }
            else {
                if (units.get(i).name.contains(pattern)) ret.units.add(units.get(i));
            }
        }
        return ret;
    }

    void fixMissingValues(AdminUnit au)
    {
        if(au.parent!=null){
            if(au.parent.density==0 || au.parent.population==0) fixMissingValues(au.parent);
            if(au.density==0)
            {
                au.density=au.parent.density;
                au.population=au.area*au.density;
            }
        }
    }

    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * Czyli sąsiadami wojweództw są województwa, powiatów - powiaty, gmin - gminy, miejscowości - inne miejscowości
     * @param unit - jednostka, której sąsiedzi mają być wyznaczeni
     * @param maxdistance - parametr stosowany wyłącznie dla miejscowości, maksymalny promień odległości od środka unit,
     *                    w którym mają sie znaleźć punkty środkowe BoundingBox sąsiadów
     * @return lista wypełniona sąsiadami
     */
    AdminUnitList getNeighbors(AdminUnit unit, double maxdistance){
        AdminUnitList neig= new AdminUnitList();
        for(int i=0; i<this.units.size(); i++)
        {
            if(units.get(i).adminLevel==unit.adminLevel && unit.bbox.intersects(units.get(i).bbox))
            {
                neig.units.add(units.get(i));

            }
            if(unit.adminLevel==8 && unit.bbox.distanceTo(units.get(i).bbox)<=maxdistance)
            {
                neig.units.add(units.get(i));
            }

        }
        return neig;
        //throw new RuntimeException("Not implemented");
    }

    AdminUnitList sortInplaceByName(){
        class CompareByName implements Comparator<AdminUnit>
        {
            @Override
            public int compare(AdminUnit t, AdminUnit t1)
            {
                return t.name.compareTo(t1.name);
            }
        }
        units.sort(new CompareByName());
        return this;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */
    AdminUnitList sortInplaceByArea(){
        Comparator<AdminUnit> comparebyarea= new Comparator<AdminUnit>()
        {
            @Override
            public int compare(AdminUnit t, AdminUnit t1)
            {
                return t.area.compareTo(t1.area);
            }
        };
        units.sort(comparebyarea);
        return this;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */
    AdminUnitList sortInplaceByPopulation(){
    Comparator<AdminUnit> comparebypopulation=(AdminUnit t, AdminUnit t1)->(t.population.compareTo(t1.population));
    units.sort(comparebypopulation);
    return this;
    }

    AdminUnitList sortInplace(Comparator<AdminUnit> cmp){
        units.sort(cmp);
        return this;
    }

    AdminUnitList sort(Comparator<AdminUnit> cmp){
        AdminUnitList tmp = new AdminUnitList();
        for(AdminUnit n: this.units)
        {
            AdminUnit ad=new AdminUnit();
            ad=n;
            tmp.units.add(ad);
        }
        tmp.sortInplace(cmp);
        return tmp;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred)
    {
        AdminUnitList tmp= new AdminUnitList();
        for(AdminUnit n:units)
        {
            if(pred.test(n)) tmp.units.add(n);
        }
        return tmp;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred, int limit){
        AdminUnitList tmp= new AdminUnitList();
        for(AdminUnit n:units)
        {
            if(tmp.units.size()==limit) break;
            if(pred.test(n)) tmp.units.add(n);
        }
        return tmp;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred, int offset, int limit){
        AdminUnitList tmp= new AdminUnitList();
        for(int i=offset; i<this.units.size(); i++)
        {
            if(tmp.units.size()==limit) break;
            if(pred.test(units.get(i))) tmp.units.add(units.get(i));
        }
        return tmp;
    }

        public static void main(String args[]) throws IOException {
        AdminUnitList tmp= new AdminUnitList();
        tmp.read("/home/agnieszka/IdeaProjects/programowanieobiektowe/Lab6/admin-units.csv");
        //tmp.list(System.out);
       for(int i=0; i<tmp.units.size(); i++) {
           tmp.fixMissingValues(tmp.units.get(i));
       }

       //tmp.filter(a->a.name.startsWith("Ż")).sortInplaceByArea().list(System.out);
      //tmp.filter(a->a.name.startsWith("K"),4,12).sortInplaceByName().list(System.out);
      //tmp.filter(a->a.adminLevel.equals(6) && a.parent.name.equals("województwo małopolskie"),3).list(System.out);

           /* AdminUnitQuery query = new AdminUnitQuery()
                    .selectFrom(tmp)
                    .where(a->a.area>1000)
                    .or(a->a.name.startsWith("Sz"))
                    .sort((a,b)->Double.compare(a.area,b.area))
                    .limit(100);
            query.execute().list(System.out);*/
            for(int i=0; i<tmp.units.size(); i++) {
                Document au = new Document();
                au.setTitle(tmp.units.get(i).name);
                au.addSection("Obszar")
                        .addParagraph(tmp.units.get(i).area.toString());
                au.addSection("Mieszkańcy")
                        .addParagraph(tmp.units.get(i).population.toString());
                au.addSection("gęstość zaludnienia")
                        .addParagraph(tmp.units.get(i).density.toString());
                au.addSection("jednostka nadrzędna")
                        .addLink(tmp.units.get(i).parent.name.replaceAll("\\s+","")+".html", tmp.units.get(i).parent.name);
                Section sec=new Section();
                    sec.setTitle("jednostka podrzędna");
                    for(int j=0; j<tmp.units.get(i).children.size(); j++)
                    {
                        sec.addLink(tmp.units.get(i).children.get(j).name.replaceAll("\\s+","")+".html", tmp.units.get(i).children.get(j).name);
                    }
                au.addSection(sec);

                Section sec2=new Section();
                sec2.setTitle("jednostka sąsiadujące");

                AdminUnitList tmp2= new AdminUnitList();
                tmp2=tmp.getNeighbors(tmp.units.get(i), 15);
                for(int j=0; j<tmp2.units.size(); j++)
                {
                    sec2.addLink(tmp2.units.get(j).name.replaceAll("\\s+","")+".html", tmp2.units.get(j).name);
                }
                au.addSection(sec2);

                PrintStream out = new PrintStream(tmp.units.get(i).name.replaceAll("\\s+","")+".html");
                au.writeHTML(out);
                au.write(tmp.units.get(i).name+".xml");
            }

            Document index=new Document();
            index.setTitle("Wszystkie jednostki:");
            Section sec2=new Section();
            sec2.setTitle("");
            tmp.sortInplaceByName();
            for(int i=0; i<tmp.units.size(); i++)
            {
                sec2.addLink(tmp.units.get(i).name.replaceAll("\\s+","")+".html", tmp.units.get(i).name);
            }
            index.addSection(sec2);
            PrintStream out = new PrintStream("index.html");
            index.writeHTML(out);
            index.write("index.xml");

    }
}
