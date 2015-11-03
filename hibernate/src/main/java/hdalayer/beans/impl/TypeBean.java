package hdalayer.beans.impl;

import beans.TypeWorking;
import func.FileWorking;
import model.HolidayType;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.SQLException;

@Stateless
public class TypeBean implements TypeWorking {

    public void saveTypeXML(String direct) throws IOException, SQLException {
        Element root = new Element("typeSave");
        Document doc = new Document(root);

        for (HolidayType t : HolidayType.values()) {
            Element typeElement = new Element("type");
            Element typeId = new Element("typeId");
            Element typeName = new Element("typeName");
            typeId.setText(HolidayType.valueOf(t.name()).toString());
            typeName.setText(t.name());
            typeElement.addContent(typeId);
            typeElement.addContent(typeName);
            root.addContent(typeElement);
            FileWorking.writeXml(doc, direct);
        }
    }
}
