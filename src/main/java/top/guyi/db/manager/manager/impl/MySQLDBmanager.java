package top.guyi.db.manager.manager.impl;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import top.guyi.db.manager.entity.DBConfig;
import top.guyi.db.manager.entity.DBUser;
import top.guyi.db.manager.entity.SQLExecuteResult;
import top.guyi.db.manager.manager.DBManager;

import java.util.List;

public class MySQLDBmanager implements DBManager{

    @Override
    public DriverManagerDataSource buildSource(DBConfig config) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://"+config.getHost()+":"+config.getPort()+"?characterEncoding="+config.getCharset()+"&useSSL=false");
        dataSource.setUsername(config.getUserName());
        dataSource.setPassword(config.getPassWord());
        return dataSource;
    }

    @Override
    public List<String> dataBaseNames() {
        return this.getTemplate().queryForList("show databases",String.class);
    }

    @Override
    public List<String> tables(String databaseName) {
        return this.getTemplate().queryForList(
                "select table_name from information_schema.tables where table_schema= ? and table_type='base table'",
                String.class,databaseName);
    }

    @Override
    public List<String> columnNames(String dataBaseName, String tableName) {
        return this.getTemplate().queryForList(
                "select column_name from information_schema.columns where table_name = ? and table_schema = ? order by ordinal_position",
                String.class,tableName,dataBaseName
        );
    }

    @Override
    public List<DBUser> users() {
        return this.getTemplate().query("select `user`,`host` from mysql.user", (rs,i)->{
            DBUser user = new DBUser();
            user.setName(rs.getString("user"));
            user.setHost(rs.getString("host"));
            return user;
        });
    }

    @Override
    public SQLExecuteResult createUser(DBUser user, String passWord) {
        return this.execute("CREATE USER '"+user.getName()+"'@'"+user.getHost()+"' IDENTIFIED BY '"+passWord+"'");
    }

    @Override
    public SQLExecuteResult deleteUser(DBUser user) {
        return this.execute("delete from mysql.user where user='"+user.getName()+"' and Host='"+user.getHost()+"'");
    }

}
