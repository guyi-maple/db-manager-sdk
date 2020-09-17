package top.guyi.db.manager.manager.dialect;

import top.guyi.db.manager.manager.entry.QueryPageRequest;

public class MySqlQueryPageRequest extends QueryPageRequest {

    public MySqlQueryPageRequest(int page, int size) {
        super(page, size);
    }

    @Override
    public String getPageSql() {
        return String.format(" limit %s,%s",this.getStart(),this.getEnd());
    }

}
