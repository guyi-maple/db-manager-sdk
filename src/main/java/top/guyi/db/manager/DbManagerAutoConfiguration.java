package top.guyi.db.manager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.guyi.db.manager.manager.DbManagerSelector;
import top.guyi.db.manager.manager.dialect.MySqlDbManager;

@Configuration
public class DbManagerAutoConfiguration {

    @Bean
    public DbManagerSelector dbManagerSelector(){
        return new DbManagerSelector();
    }

    @Bean
    public MySqlDbManager mySqlDbManager(){
        return new MySqlDbManager();
    }

}
