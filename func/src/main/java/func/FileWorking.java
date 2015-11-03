package func;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by iocz on 10/10/15.
 */
public class FileWorking {

    public static void writeXml(Document document, String direct) throws IOException {
        XMLOutputter outPutter = new XMLOutputter();
        FileWriter writer = new FileWriter(direct);
        outPutter.output(document, writer);
        writer.close();
    }
}
