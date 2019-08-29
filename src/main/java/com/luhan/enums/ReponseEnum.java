package com.luhan.enums;

/**
 * 〈服务器限流标志Enum〉<br>
 *
 * @author luHan
 * @create 2019-08-29 18:18
 * @since 1.0.0
 */
public enum ReponseEnum {
    OK("访问成功"),
    FAIL("您被限流了")
    ;
    private String desc;

    ReponseEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
