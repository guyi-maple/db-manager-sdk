package top.guyi.db.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SQL语句执行结果返回
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SQLExecuteResult {

    private String dump;

    private boolean success;

    private String message;

    private Object data;

    public SQLExecuteResult(String dump) {
        this.dump = dump;
    }
}
