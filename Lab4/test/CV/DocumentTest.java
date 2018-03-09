package CV;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class DocumentTest {

    @Test
    public void setTitle() throws Exception {
        Document doc= new Document();
        doc.setTitle("Jan");
        assertEquals(doc.title, "Jan");
    }

    @Test
    public void setPhoto() throws Exception {
        Document doc= new Document();
        doc.setPhoto("https://m.gadzetomania.pl/linda-2ea8e4a1ad854c2e572670a29e,750,470,0,0.jpg");
        assertEquals(doc.photo.url, "https://m.gadzetomania.pl/linda-2ea8e4a1ad854c2e572670a29e,750,470,0,0.jpg");
    }

    @Test
    public void addSection() throws Exception {
        Document doc= new Document();
        doc.addSection("Jan");
        assertEquals(doc.sections.get(0).title, "Jan");
    }

    @Test
    public void addSection1() throws Exception {
        Document doc= new Document();
        Section sec= new Section();
        sec.title = "Jan";
        doc.addSection(sec);
        assertEquals(doc.sections.get(0).title, "Jan");
    }

    @Test
    public void writeHTML() throws Exception {

        Document cv = new Document();
        cv.setTitle("Agnieszka Nowak");
        cv.setPhoto("https://lh4.googleusercontent.com/-OowXWkgMSHI/AAAAAAAAAAI/AAAAAAAAANE/rOf2DCA2AXo/photo.jpg");
        cv.addSection("Wykształcenie")
                .addParagraph("2000-2005 Przedszkole")
                .addParagraph("2006-2012 SP7")
                .addParagraph("");
        cv.addSection("Coś")
                .addParagraph(
                        new ParagraphWithList().setContent("Języki")
                                .addListItem("C")
                );

        PrintStream out=new PrintStream("cv.html");
        cv.writeHTML(out);

        BufferedReader in = new BufferedReader(new FileReader("cv_test.html"));
        BufferedReader in2 = new BufferedReader(new FileReader("cv.html"));

        String str1;
        String str2;
        while (((str1 = in.readLine()) != null) && ((str2 = in2.readLine()) != null))
        {
            assertEquals(str1, str2);

        }
    }


}