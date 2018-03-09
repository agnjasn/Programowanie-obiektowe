import java.io.*;
import java.util.*;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;
    String[] current;
    String filename;
    List<String> columnLabels = new ArrayList<>();
    // odwzorowanie: nazwa kolumny -> numer kolumny
    Map<String, Integer> columnLabelsToInt = new HashMap<>();
    // nazwy kolumn w takiej kolejności, jak w pliku
    CSVReader(String filename)
    {
        this.filename = filename;
    }

    CSVReader(String filename,String delimiter)
    {
        this(filename);
        this.delimiter=delimiter;
    }

    public CSVReader(Reader reader, String delimiter, boolean hasHeader)
    {
        this.hasHeader=hasHeader;
        this.delimiter=delimiter;
        this.reader=new BufferedReader(reader);
    }

    public CSVReader(String filename, String delimiter, boolean hasHeader) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if (hasHeader) parseHeader();
    }

    void parseHeader() throws IOException {
        // wczytaj wiersz
        String line = reader.readLine();
        if (line == null) {
            return;
        }
        // podziel na pola
        String[] header = line.split(delimiter);
        // przetwarzaj dane w wierszu
        for (int i = 0; i < header.length; i++) {
            columnLabels.add(header[i]);
            columnLabelsToInt.put(header[i], i);
            // dodaj nazwy kolumn do columnLabels i numery do columnLabelsToInt
        }
    }


    boolean next() throws IOException {
        String line= reader.readLine();
        if (line==null) {
        return false;
        }
        current= line.split(delimiter);
        return true;
    }

    List<String> getColumnLabels()
    {
        List<String> wynik = new ArrayList<>();
        for (int i=0; i<columnLabels.size(); i++)
        {
            wynik.add(columnLabels.get(i));
        }
        return wynik;
        //return this.columnLabels;
    }
    int getRecordLength()
    {
        return this.current.length;
    }
    boolean isMissing(int columnIndex)
    {
        if(columnIndex>=current.length) return true;
        if(this.current[columnIndex].isEmpty()) return true;
        else return false;
    }
    boolean isMissing(String columnLabel)
    {
        int columnIndex=this.columnLabelsToInt.get(columnLabel);
        if(columnIndex>=current.length) return true;
        if(this.current[columnIndex].isEmpty()) return true;
        else return false;
    }
    String get(int columnIndex)
    {
        if (isMissing(columnIndex)==false){return this.current[columnIndex].toString();}
        else return "";
    }
    String get(String columnLabel)
    {
        if (isMissing(columnLabel)==false)
        {
            int columnIndex=this.columnLabelsToInt.get(columnLabel);
            return this.current[columnIndex];
        }
        else return "";
    }
    int getInt(int columnIndex)throws NumberFormatException
    {
        if (isMissing(columnIndex)==false) {
            return Integer.parseInt(current[columnIndex]);
        }
        else return 0;
    }
    int getInt(String columnLabel)throws NumberFormatException
    {
        if (isMissing(columnLabel)==false) {
            int columnIndex = this.columnLabelsToInt.get(columnLabel);
            return Integer.parseInt(current[columnIndex]);
        }
        else return 0;

    }
    long getLong(int columnIndex)throws NumberFormatException{
        if (isMissing(columnIndex)==false) {
            return Long.parseLong(current[columnIndex]);
        }
        else return 0;
    }
    long getLong(String columnLabel)throws NumberFormatException
    {
        if (isMissing(columnLabel)==false) {
            int columnIndex = this.columnLabelsToInt.get(columnLabel);
            return Long.parseLong(current[columnIndex]);
        }
        else return 0;
    }
    double getDouble(int columnIndex)throws NumberFormatException
    {
        if (isMissing(columnIndex)==false) {
            return Double.parseDouble(current[columnIndex]);
        }
        else return  0;
    }
    double getDouble(String columnLabel) throws NumberFormatException
    {
        if (isMissing(columnLabel)==false) {
            int columnIndex = this.columnLabelsToInt.get(columnLabel);
            return Double.parseDouble(current[columnIndex]);
        }
        else return  0;
    }

    /*public static void main(String args[]) throws IOException {
        CSVReader reader = new CSVReader("/home/agnieszka/IdeaProjects/programowanieobiektowe/Lab6/admin-units.csv",",",true);
        int licznik =0;
        while(reader.next() && licznik<50){
            int id = reader.getInt("id");
            int parent= reader.getInt("parent");
            String name = reader.get("name");
            int admin_level= reader.getInt("admin_level");
            int population= reader.getInt("population");
            double area = reader.getDouble("area");
            double density = reader.getDouble("density");
            double x1 = reader.getDouble("x1");
            double y1 = reader.getDouble("y1");
            double x2 = reader.getDouble("x2");
            double y2 = reader.getDouble("y2");
            licznik++;

            System.out.printf(Locale.US,"%d %d %s %d %d %f %f %f %f %f %f",id, parent, name, admin_level,population, area,density,x1,y1,x2,y2);
        }


    }*/


}

