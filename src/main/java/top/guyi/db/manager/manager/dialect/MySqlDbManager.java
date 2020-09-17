package top.guyi.db.manager.manager.dialect;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import top.guyi.db.manager.entity.DbConfig;
import top.guyi.db.manager.entity.DbUser;
import top.guyi.db.manager.entity.SQLExecuteResult;
import top.guyi.db.manager.manager.*;
import top.guyi.db.manager.manager.entry.Columns;
import top.guyi.db.manager.manager.entry.DbPage;
import top.guyi.db.manager.manager.entry.QueryPageRequest;
import top.guyi.db.manager.manager.enums.DataBasePermission;

import java.sql.ResultSetMetaData;
import java.util.*;

public class MySqlDbManager implements DbManager {

    /**
     * 刷新权限
     */
    private void flush(){
        this.execute("flush  privileges");
    }

    @Override
    public String dialect() {
        return "MySQL";
    }

    @Override
    public DriverManagerDataSource buildSource(DbConfig config) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://"+config.getHost()+":"+config.getPort()+"?characterEncoding="+config.getCharset()+"&useSSL=false");
        dataSource.setUsername(config.getUserName());
        dataSource.setPassword(config.getPassWord());
        return dataSource;
    }

    @Override
    public Collection<String> dataBaseNames() {
        return this.getTemplate().queryForList("show databases",String.class);
    }

    @Override
    public SQLExecuteResult createDataBase(String name, String charset) {
        return this.execute(String.format(
                "create database `%s` charset `%s`",
                name,charset
        ));
    }

    @Override
    public Collection<String> tables(String databaseName) {
        return this.getTemplate().queryForList(
                "select table_name from information_schema.tables where table_schema= ? and table_type='base table'",
                String.class,databaseName);
    }

    @Override
    public Collection<String> columnNames(String dataBaseName, String tableName) {
        return this.getTemplate().queryForList(
                "select column_name from information_schema.columns where table_name = ? and table_schema = ? order by ordinal_position",
                String.class,tableName,dataBaseName
        );
    }

    @Override
    public Collection<DbUser> users() {
        return this.getTemplate().query("select `user`,`host` from mysql.user", (rs,i) -> DbUser.builder()
                .name(rs.getString("user"))
                .host(rs.getString("host"))
                .build());

    }

    @Override
    public SQLExecuteResult createUser(DbUser user, String passWord) {
        return this.execute(
                String.format("create user '%s'@'%s' identified by '%s'",
                        user.getName(),
                        user.getHost(),
                        passWord)
        );
    }

    @Override
    public SQLExecuteResult deleteUser(DbUser user) {
        return this.execute(
                String.format("delete from mysql.user where user='%s' and Host='%s'",
                        user.getName(),
                        user.getHost()
                )
        );
    }

    @Override
    public SQLExecuteResult authorization(DbUser user, String dataBaseName, DataBasePermission permission) {
        SQLExecuteResult result = this.execute(
                String.format("grant all privileges on `%s`.%s to '%s'@'%s'",
                        dataBaseName,
                        permission.getValue(),
                        user.getName(),
                        user.getHost()
                )
        );
        if (result.isSuccess()){
            this.flush();
        }
        return result;
    }

    @Override
    public Collection<Columns> query(String sql) {
        List<String> names = new LinkedList<>();
        return this.getTemplate().query(sql,(r,index) -> {
            if (names.size() == 0){
                ResultSetMetaData metaData = r.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++){
                    names.add(metaData.getColumnName(i));
                }
            }
            return Columns.create(r,names);
        });
    }

    @Override
    public DbPage<Columns> tableQuery(String dataBaseName, String tableName, QueryPageRequest page) {
        String sql = String.format("select * from %s.%s %s",dataBaseName,tableName,page.getPageSql());
        String countSql = String.format("select count(1) from %s.%s",dataBaseName,tableName);

        DbPage<Columns> result = new DbPage<>();
        result.setPage(page.getPage());
        result.setSize(page.getSize());

        result.setTotal(this.getTemplate()
                .query(countSql,(r,i) -> r.getLong(1))
                .get(0));

        result.setContent(this.query(sql));

        return result;
    }

}
