package io.renren.common.enumeration;

/**
 * @Description: BindCbsCustomsBrokerCtbCompanyEntity
 * @Param:
 * @return:
 * @Author: chenning
 * @Date: 2020/1/4
 */
public enum BindEnum {

    STATUS_1(1, "甲方发起绑定"),
    STATUS_2(2, "乙方发起确认"),
    STATUS_3(3, "已绑定"),
    STATUS_4(4, "乙方已确核"),
    STATUS_5(99, "无效"),
    ;


    private int code;
    private String msg;

    BindEnum(int code, String msg) {
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
