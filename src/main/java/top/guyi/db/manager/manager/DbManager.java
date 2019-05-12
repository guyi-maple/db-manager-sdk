package top.guyi.db.manager.manager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import top.guyi.db.manager.ThreadRepository;
import top.guyi.db.manager.entity.DbConfig;
import top.guyi.db.manager.entity.DbUser;
import top.guyi.db.manager.entity.SQLExecuteResult;
import top.guyi.db.manager.manager.enums.DataBasePermission;

import java.util.Collection;

/**
 * 数据库管理器接口
 */
public interface DbManager {

    default void open(DbConfig config){
        ThreadRepository.dbConfig.set(config);
    }

    default JdbcTemplate getTemplate(){
        JdbcTemplate template = ThreadRepository.jdbcTemplate.get();
        if(template == null){
            DbConfig config = ThreadRepository.dbConfig.get();
            if(config == null){
                throw new RuntimeException("please run the method: open");
            }
            DriverManagerDataSource dataSource = this.buildSource(config);
            template = new JdbcTemplate(dataSource);
            ThreadRepository.jdbcTemplate.set(template);
        }
        return template;
    }

    default SQLExecuteResult execute(String dump){
        JdbcTemplate template = this.getTemplate();
        SQLExecuteResult result = new SQLExecuteResult(dump);
        try{
            template.execute(dump);
            result.setSuccess(true);
            result.setMessage("SUCCESS");
        }catch (Exception e){
            result.setMessage(e.getMessage());
        }
        return result;
    }

    DriverManagerDataSource buildSource(DbConfig config);

    Collection<String> dataBaseNames();

    Collection<String> tables(String databaseName);

    Collection<String> columnNames(String dataBaseName, String tableName);

    Collection<DbUser> users();

    SQLExecuteResult createUser(DbUser user, String passWord);

    SQLExecuteResult deleteUser(DbUser user);

    SQLExecuteResult authorization(DbUser user, String dataBaseName, DataBasePermission permission);

    Collection<Columns> query(String sql);

    DbPage<Columns> tableQuery(String dataBaseName,String tableName, QueryPageRequest page);

}
