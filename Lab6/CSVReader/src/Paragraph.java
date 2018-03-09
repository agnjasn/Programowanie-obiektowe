
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlValue;
import java.io.PrintStream;

//@XmlSeeAlso({ParagraphWithList.class})
public class Paragraph{
    @XmlValue
    String content;
    Paragraph(){}
    Paragraph setContent(String cont)
    {
        this.content=cont;
        return this;
    }

    void writeHTML(PrintStream out){
        out.printf("<p>%s</p>\n",content);
    }


}
