package top.guyi.db.manager.manager.entry;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Column {

    private String name;
    private Object value;

}
