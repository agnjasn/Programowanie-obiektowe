package CV;

import sun.swing.SwingUtilities2;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class Section{
    @XmlAttribute
    String title;
    @XmlElements(value= {
            @XmlElement(name = "paragraph", type = Paragraph.class),
            @XmlElement(name = "paragraph-with-list", type = ParagraphWithList.class)
    })
    List<Paragraph> paragraps = new ArrayList<>() ;

    Section(){}
    Section setTitle(String title)
    {
        this.title=title;
        return this;
    }

    Section addParagraph(String paragraphText)
    {
        Paragraph paragraph= new Paragraph();
        paragraph.content=paragraphText;
        paragraps.add(paragraph);
        return this;
    }
    Section addParagraph(Paragraph p)
    {
        paragraps.add(p);
        return this;
    }

    void writeHTML(PrintStream out){
        out.printf("<h2> %s </h2>\n <p>",title);
        for(int i=0; i<paragraps.size(); i++)
        {
            paragraps.get(i).writeHTML(out);
        }
        out.print("</p>");
    }
}
