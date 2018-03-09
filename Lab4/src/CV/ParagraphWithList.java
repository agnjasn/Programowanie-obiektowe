package CV;

import javax.xml.bind.annotation.XmlElement;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ParagraphWithList extends Paragraph {
    @XmlElement
    UnorderedList paragrapslist= new UnorderedList();
    ParagraphWithList(){}

    ParagraphWithList addListItem(String item)
    {
        paragrapslist.addItem(item);
        return this;
    }
    ParagraphWithList addListItem(ListItem item)
    {
        paragrapslist.addItem(item);
        return this;
    }

    void writeHTML(PrintStream out){
        out.printf("<p>%s", content);
        paragrapslist.writeHTML(out);
        out.print("</p>");
    }

}
