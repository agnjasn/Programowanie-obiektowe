import javax.xml.bind.annotation.XmlValue;
import java.io.PrintStream;

public class Link {
    @XmlValue
    String link;
    String name;
    Link(){}
    Link setContent(String link, String name)
    {
        this.link=link.replaceAll("\\s+","");
        this.name=name;
        return this;
    }

    void writeHTML(PrintStream out){
        out.printf("<a href=%s>%s</a>\n",link,name);
    }
}
