
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
            @XmlElement(name = "link", type = Link.class)
    })
    List<Paragraph> paragraps = new ArrayList<>();
    List<Link> links = new ArrayList<>();

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

    Section addLink(String paragraphText, String name)
    {
        Link link= new Link();
        link.link=paragraphText;
        link.name=name;
        links.add(link);
        return this;
    }

    Section addLink(Link p)
    {
        links.add(p);
        return this;
    }

    void writeHTML(PrintStream out){
        out.printf("<h2> %s </h2>\n <p>",title);
        for(int i=0; i<paragraps.size(); i++)
        {
            paragraps.get(i).writeHTML(out);
        }
        for(int i=0; i<links.size(); i++)
        {
            links.get(i).writeHTML(out);
        }
        out.print("</p>");


    }
}
