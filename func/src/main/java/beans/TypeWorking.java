package beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TypeWorking {
    void saveTypeXML(String direct) throws IOException, SQLException;
}
