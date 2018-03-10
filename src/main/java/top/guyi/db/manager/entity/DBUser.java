package top.guyi.db.manager.entity;

public class DBUser {

    private String name;

    private String host;

    public DBUser(){}
    public DBUser(String name, String host) {
        this.name = name;
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
