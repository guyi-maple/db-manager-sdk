package top.guyi.db.manager.manager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DbManagerSelector implements InitializingBean {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<String,DbManager> managers = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        this.applicationContext.getBeansOfType(DbManager.class)
                .values()
                .forEach(manager -> managers.put(manager.dialect().toUpperCase(),manager));
    }

    public Optional<DbManager> get(String dialect){
        return Optional.ofNullable(this.managers.get(dialect.toUpperCase()));
    }

}
