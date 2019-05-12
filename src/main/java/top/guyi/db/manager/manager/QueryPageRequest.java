package top.guyi.db.manager.manager;

import lombok.Data;

@Data
public abstract class QueryPageRequest {

    private int page = 1;
    private int size = 100;

    public QueryPageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getStart(){
        return (this.page - 1) * this.size;
    }
    public int getEnd(){
        return (this.page) * this.size;
    }

    public abstract String getPageSql();

}
