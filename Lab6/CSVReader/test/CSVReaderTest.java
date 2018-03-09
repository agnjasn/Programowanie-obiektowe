import org.junit.Test;

import java.io.StringReader;
import java.util.Locale;

import static org.junit.Assert.*;

public class CSVReaderTest {

    @Test
    public void buildtest() throws Exception {
        CSVReader reader2 = new CSVReader("/home/agnieszka/IdeaProjects/programowanieobiektowe/Lab6/CSVReader/no-header.csv",";",false);
        while(reader2.next()){
            String id = reader2.get(0);
            String name = reader2.get(1);
            String surename = reader2.get(2);
            String street = reader2.get(3);
            String nrdomu = reader2.get(4);
            String nrmieszkania = reader2.get(5);

            System.out.printf(Locale.US,"%s %s %s %s %s %s \n",id, name, surename, street, nrdomu, nrmieszkania);
        }

    }

    @Test(expected = NullPointerException.class)
    public void nonexistentcolumnbyname() throws Exception {
        CSVReader reader2 = new CSVReader("/home/agnieszka/IdeaProjects/programowanieobiektowe/Lab6/CSVReader/with-header.csv",";",true);
        while(reader2.next()) {
            String name2 = reader2.get("period");
        }
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void nonexistentcolumnbyindex() throws Exception {
        CSVReader reader2 = new CSVReader("/home/agnieszka/IdeaProjects/programowanieobiektowe/Lab6/CSVReader/with-header.csv",";",true);
        while(reader2.next()) {
            String name2 = reader2.get(44);
        }
    }

    @Test
    public void othersourcereader() throws Exception {
        String text = "a,b,c\n123.4,567.8,91011.12";
        CSVReader reader = new CSVReader(new StringReader(text),",",false);
        while(reader.next()) {
            String var1 = reader.get(0);
            String var2 = reader.get(1);
            String var3 = reader.get(2);
            System.out.printf(Locale.US,"%s %s %s\n",var1, var2, var3);
        }

    }

    @Test
    public void get() throws Exception {
        CSVReader reader2 = new CSVReader("/home/agnieszka/IdeaProjects/programowanieobiektowe/Lab6/CSVReader/with-header.csv",";",true);
        while(reader2.next()){
            String name = reader2.get(1);
            String name2 = reader2.get("imię");

            assertEquals(name,name2);
            //System.out.printf(Locale.US,"%s %s\n",name, surename);
        }
    }

    @Test
    public void getInt() throws Exception {
        CSVReader reader2 = new CSVReader("/home/agnieszka/IdeaProjects/programowanieobiektowe/Lab6/CSVReader/with-header.csv",";",true);
        while(reader2.next()) {
            int id = reader2.getInt(0);
            int id2 = reader2.getInt("id");

            assertEquals(id, id2);
        }
    }

    @Test
    public void getLong() throws Exception {
            CSVReader reader2 = new CSVReader("/home/agnieszka/IdeaProjects/programowanieobiektowe/Lab6/CSVReader/elec.csv",",",true);
            while(reader2.next()){
                long day = reader2.getLong(1);
                long day2 = reader2.getLong("day");

                assertEquals(day,day2);
        }
    }


    @Test
    public void getDouble() throws Exception {
        CSVReader reader2 = new CSVReader("/home/agnieszka/IdeaProjects/programowanieobiektowe/Lab6/CSVReader/elec.csv",",",true);
        while(reader2.next()){
            double period = reader2.getDouble(2);
            double period2 = reader2.getDouble("period");

            assertEquals(period,period2,1e-5);
        }
    }


}