package CV;


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
    Photo photo;
    @XmlElement
    List<Section> sections = new ArrayList<>();

   // Document(String tit)
    //{
   //     this.title=tit;
    //}
    Document(){}
    Document setTitle(String title){
        this.title = title;
        return this;
    }

    Document setPhoto(String photoUrl){
        this.photo=new Photo(photoUrl);
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

        photo.writeHTML(out);
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


    public static void main(String args[]) throws FileNotFoundException {
        Document cv = new Document();
        cv.setTitle("Jan Kowalski");
        cv.setPhoto("https://lh4.googleusercontent.com/-OowXWkgMSHI/AAAAAAAAAAI/AAAAAAAAANE/rOf2DCA2AXo/photo.jpg");
        cv.addSection("Wykształcenie")
                .addParagraph("2000-2005 Przedszkole im. Królewny Snieżki w ...")
                .addParagraph("2006-2012 SP7 im Ronalda Regana w ...")
                .addParagraph("...");
        cv.addSection("Umiejętności")
                .addParagraph(
                        new ParagraphWithList().setContent("Języki")
                                .addListItem("C")
                                .addListItem("C++")
                                .addListItem("Java")
                );

        PrintStream out=new PrintStream("cv.html");
        PrintStream out2=new PrintStream("cv2.html");
        cv.writeHTML(out);
        cv.write("cv.xml");
        Document cv2 = Document.read("cv.xml");
        cv2.writeHTML(out2);
    }

}
