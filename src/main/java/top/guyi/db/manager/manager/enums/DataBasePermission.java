package top.guyi.db.manager.manager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataBasePermission {

    ALL("*","所有权限"),
    SELECT("SELECT","查询权限"),
    DELETE("DELETE","删除权限"),
    UPDATE("UPDATE","更新权限"),
    CREATE("CREATE","创建权限"),
    DROP("DROP","删除数据库、数据表权限"),
    ALTER("ALTER","数据表更改权限");

    private String value;
    private String text;

}
