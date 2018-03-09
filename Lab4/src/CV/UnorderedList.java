package CV;

import javax.xml.bind.annotation.XmlElement;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UnorderedList{
    @XmlElement
    List<ListItem> listofItems= new ArrayList<>();
    UnorderedList(){}

    UnorderedList addItem(String item)
    {
        ListItem listItem= new ListItem();
        listItem.content=item;
        listofItems.add(listItem);
        return this;
    }
    UnorderedList addItem(ListItem item)
    {
        ListItem listItem= new ListItem();
        listItem=item;
        listofItems.add(listItem);
        return this;
    }

    void writeHTML(PrintStream out)
    {
        out.print("<ul>");
        for(int i=0; i<listofItems.size(); i++)
        {
            listofItems.get(i).writeHTML(out);
        }
        out.println("</ul>");
    }
}
