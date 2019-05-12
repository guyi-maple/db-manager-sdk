package top.guyi.db.manager;

import org.springframework.jdbc.core.JdbcTemplate;
import top.guyi.db.manager.entity.DbConfig;

/**
 * 线程数据仓库
 */
public class ThreadRepository {

    public static ThreadLocal<JdbcTemplate> jdbcTemplate = new ThreadLocal<>();

    public static ThreadLocal<DbConfig> dbConfig = new ThreadLocal<>();

}
