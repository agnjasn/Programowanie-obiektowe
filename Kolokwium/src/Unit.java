import java.util.ArrayList;
import java.util.List;

public class Unit {
    String name;
    Integer liczba;
    Integer rok;
    String plec;

    String tostring()
    {
        String str=name+" "+Integer.toString(liczba)+" "+Integer.toString(rok)+" "+plec+"\n";
        return str;
    }}
