package top.guyi.db.manager.manager.entry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Columns extends LinkedList<Column> {

    public static Columns create(ResultSet rs, List<String> names) throws SQLException {
        Columns columns = new Columns();
        for (String name : names) {
            columns.add(new Column(name,rs.getObject(name)));
        }
        return columns;
    }

}
