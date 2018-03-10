package top.guyi.db.manager.manager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import top.guyi.db.manager.ThreadRepository;
import top.guyi.db.manager.entity.DBConfig;
import top.guyi.db.manager.entity.DBUser;
import top.guyi.db.manager.entity.SQLExecuteResult;

import javax.sql.DataSource;
import java.util.List;

/**
 * 数据库管理器接口
 */
public interface DBManager {

    default void open(DBConfig config){
        ThreadRepository.dbConfig.set(config);
    }

    default JdbcTemplate getTemplate(){
        JdbcTemplate template = ThreadRepository.jdbcTemplate.get();
        if(template == null){
            DBConfig config = ThreadRepository.dbConfig.get();
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

    DriverManagerDataSource buildSource(DBConfig config);

    List<String> dataBaseNames();

    List<String> tables(String databaseName);

    List<String> columnNames(String dataBaseName, String tableName);

    List<DBUser> users();

    SQLExecuteResult createUser(DBUser user,String passWord);

    SQLExecuteResult deleteUser(DBUser user);
}
