package top.guyi.db.manager.entity;

/**
 * SQL语句执行结果返回
 */
public class SQLExecuteResult {

    private String dump;

    private boolean success;

    private String message;

    private Object data;

    public SQLExecuteResult(String dump) {
        this.dump = dump;
    }

    public String getDump() {
        return dump;
    }

    public void setDump(String dump) {
        this.dump = dump;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SQLExecuteResult{" +
                "dump='" + dump + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
