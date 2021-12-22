package io.renren.common.enumeration;

/**
 * @Description: 国内
 * @Param:
 * @return:
 * @Author: chenning
 * @Date: 2020/1/4
 */
public enum PurchaseEnum {


    // 1、编辑中 2、审核中 3、审核失败 4、审核通过 5、已入库 99、已删除
    STATUS_1(1, "编辑中"),
    STATUS_2(2, "审核中"),
    STATUS_3(3, "审核失败"),
    STATUS_4(4, "审核通过"),
    STATUS_5(5, "已入库"),
    STATUS_99(99, "已删除"),


    ;


    private int code;
    private String msg;

    PurchaseEnum(int code, String msg) {
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
