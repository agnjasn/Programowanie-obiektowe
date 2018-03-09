import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Names {
    List<Unit> units=new ArrayList<>();

    public void read(String filename) throws IOException {
        CSVReader reader= new CSVReader(filename, ";", true);
        //int licznik=0;
        while(reader.next()){
            //if(licznik==10) break;
            Unit unit= new Unit();
            unit.rok= reader.getInt(0);
            unit.name = reader.get("Imię");
            unit.liczba= reader.getInt(2);
            unit.plec = reader.get("Płeć");
            units.add(unit);

            //licznik++;
        }

}

    void list(PrintStream out, int offset, int limit ){
        for(int i=offset; i<=(offset+limit); i++)
        {
            out.println(units.get(i).tostring());
        }
    }

    Names sortInplaceByliczba(){
        class CompareByName implements Comparator<Unit>
        {
            @Override
            public int compare(Unit t, Unit t1)
            {
                return t.liczba.compareTo(t1.liczba);
            }
        }
        units.sort(new CompareByName());
        return this;
    }

    Names sortInplaceByname(){
        class CompareByName implements Comparator<Unit>
        {
            @Override
            public int compare(Unit t, Unit t1)
            {
                return t.name.compareTo(t1.name);
            }
        }
        units.sort(new CompareByName());
        return this;
    }

    Names filter(Predicate<Unit> pred)
    {
        Names tmp= new Names();
        for(Unit n:units)
        {
            if(pred.test(n)) tmp.units.add(n);
        }
        return tmp;
    }

    Names filter(Predicate<Unit> pred, int limit){
        Names tmp= new Names();
        for(Unit n:units)
        {
            if(tmp.units.size()==limit) break;
            if(pred.test(n)) tmp.units.add(n);
        }
        return tmp;
    }

    int numberofkids(int rok, String plec)
    {
        Names tmp2= new Names();
        tmp2=this.filter(a->a.rok.equals(rok));
        if(!plec.isEmpty()) tmp2=tmp2.filter(a->a.plec.equals(plec));
        int sum=0;
        for(int i=0; i<tmp2.units.size();i++)
        {
            sum+=tmp2.units.get(i).liczba;
        }
        return sum;
    }

    static Map<String,Integer> numberofnames(Names tmp, String plec)
    {
        Map<String,Integer> map=new HashMap<String,Integer>();
        for(int i=0; i<tmp.units.size(); i++ )
        {
            if(plec.equals(tmp.units.get(i).plec)){
            if(map.containsKey(tmp.units.get(i).name))
            {
                map.replace(tmp.units.get(i).name,tmp.units.get(i).liczba+map.get(tmp.units.get(i).name));
            }
            else map.put(tmp.units.get(i).name, tmp.units.get(i).liczba);
        }}
        return map;
    }

    static Map<String, Double> min(Names tmp, String plec)
    {
        int sum=0;
        double pr=0;
        Map<String,Double> min=new HashMap<>();
        Map<String,Double> max=new HashMap<>();
        for(int i=2000; i<2017; i++)
        {
            sum=tmp.numberofkids(i, plec);
            for(int j=0; j<tmp.units.size(); j++)
            {
                pr=tmp.units.get(j).liczba*100/sum;
                if((!min.containsKey(tmp.units.get(j).name) || min.get(tmp.units.get(j).name)>pr) && tmp.units.get(j).plec.equals(plec))
                {
                    min.put(tmp.units.get(j).name, pr);
                }
            }
        }
        return min;
    }

    static Map<String, Double> max(Names tmp, String plec)
    {
        int sum=0;
        double pr=0;
        Map<String,Double> max=new HashMap<>();
        for(int i=2000; i<2017; i++)
        {
            sum=tmp.numberofkids(i, plec);
            for(int j=0; j<tmp.units.size(); j++)
            {
                pr=tmp.units.get(j).liczba*100/sum;
                if((!max.containsKey(tmp.units.get(j).name) || max.get(tmp.units.get(j).name)<pr) && tmp.units.get(j).plec.equals(plec))
                {
                    max.put(tmp.units.get(j).name, pr);
                }
            }
        }
        return max;
    }


    public static void main(String[] args) throws IOException {
        Names tmp= new Names();
        tmp.read("/home/agnieszka/IdeaProjects/programowanieobiektowe/Kolokwium/imiona-2000-2016.csv");
        //tmp.list(System.out);
        //for(int i=0; i<tmp.units.size(); i++) {
        //System.out.print(tmp.units.get(i).tostring());
       //}


        Map<String,Integer> names=numberofnames(tmp, "K");
        System.out.print("Zeńskie najpopularniejsze imię : " + Collections.max(names.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey() + "\n");

        names=numberofnames(tmp, "M");
        System.out.print("Męskie najpopularniejsze imię : " + Collections.max(names.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey() + "\n");

        int sum=0;
        for(int i=2000; i<2017; i++){
            sum+=tmp.numberofkids(i, "");}
        System.out.print("Liczba dzieci:"+sum+"\n");//}


        Map<String,Double> max=max(tmp, "M");
        Map<String,Double> min=min(tmp, "M");
        Map<String,Double> procent=new HashMap<>();
        max.forEach((k,v)->procent.put(k,v-min.get(k)));
        System.out.print("Największy przyrost: " + Collections.max(procent.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey() + "\n");

        Map<String,Double> max2=max(tmp, "K");
        Map<String,Double> min2=min(tmp, "K");
        Map<String,Double> procent2=new HashMap<>();
        max2.forEach((k,v)->procent2.put(k,v-min2.get(k)));
        System.out.print("Największy przyrost: " + Collections.max(procent2.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey() + "\n");



    }
}
