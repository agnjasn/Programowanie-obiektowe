package CV;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import java.io.PrintStream;

public class ListItem{
    @XmlValue
    String content;
    ListItem(){}
    void writeHTML(PrintStream out){
        out.printf("<li>%s</li>\n", content);    }
}
