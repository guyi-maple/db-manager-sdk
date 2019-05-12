package top.guyi.db.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库配置信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbConfig {

    private String host;
    private int port = 3306;
    private String userName;
    private String passWord;
    private String charset = "UTF-8";

    public DbConfig(String host, String userName, String passWord) {
        this.host = host;
        this.userName = userName;
        this.passWord = passWord;
    }

}
