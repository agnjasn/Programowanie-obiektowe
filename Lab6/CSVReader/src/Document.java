
import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Document {
    @XmlElement
    String title;
    @XmlElement
    List<Section> sections = new ArrayList<>();

    Document(){}
    Document setTitle(String title){
        this.title = title;
        return this;
    }


    Section addSection(String sectionTitle){
        Section sect=new Section();
        sect.setTitle(sectionTitle);
        sections.add(sect);
        return sect;
    }
    Document addSection(Section s)
    {
        sections.add(s);
        return this;
    }


    void writeHTML(PrintStream out){
        out.printf("<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" +
                "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<title>%s</title>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml;\n" +
                "charset=UTF-8\" />\n" +
                "</head>\n" +
                "<body>\n"+
                "<h1> %s </h1>", title, title);

        for(int i=0; i<sections.size(); i++)
        {
            sections.get(i).writeHTML(out);
        }
        out.print("</body>\n" +
                "</html>");
        // zapisz niezbędne znaczniki HTML
        // dodaj tytuł i obrazek
        // dla każdej sekcji wywołaj section.writeHTML(out)
    }


    public void write(String fileName){
        try {
            JAXBContext jc = JAXBContext.newInstance(Document.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            FileWriter writer= new FileWriter(fileName);;
            m.marshal(this, writer);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public static Document read(String fileName){
        try {
            JAXBContext jc = JAXBContext.newInstance(Document.class);
            Unmarshaller m = jc.createUnmarshaller();
            FileReader reader = new FileReader(fileName);
            return (Document) m.unmarshal(reader);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
