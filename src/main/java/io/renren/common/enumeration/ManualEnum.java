package io.renren.common.enumeration;

/**
 * @Description: 手册类型
 * @Author: chenning
 * @Date: 2020/1/4
 */
public enum ManualEnum {
    // I-料件 E-成品
    Manual_TYPE_1(1, "I"),
    Manual_TYPE_2(2, "E"),
    ;

    private int code;
    private String msg;

    ManualEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
