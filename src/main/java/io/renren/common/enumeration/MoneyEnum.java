package io.renren.common.enumeration;

/**
 * @Description: 收支类型
 * @Param:
 * @return:
 * @Author: chenning
 * @Date: 2020/1/4
 */
public enum MoneyEnum {

    MONEY_TYPE_1(1, "进口货值支出"),
    MONEY_TYPE_2(2, "出口货值收入"),
    MONEY_TYPE_3(3, "报关费支出"),
    MONEY_TYPE_11(11, "国际运费支出"),
    MONEY_TYPE_12(12, "国际保费支出"),
    MONEY_TYPE_13(13, "国际杂费支出"),
    MONEY_TYPE_21(21, "国内运费支出"),
    MONEY_TYPE_22(22, "国内杂费支出"),
    MONEY_TYPE_4(4, "加工费支出"),
    MONEY_TYPE_5(5, "加工费收入"),
    MONEY_TYPE_101(101, "进口货值预支出"),
    MONEY_TYPE_102(102, "进口货值预收入"),
    MONEY_TYPE_1001(1001, "国内收货支出"),
    MONEY_TYPE_1002(1002, "国内发货收入"),

    CONFIRM_STATUS_1(1, "待确核"),
    CONFIRM_STATUS_2(2, "已确核"),
    CONFIRM_STATUS_3(3, "附件未审核"),
    CONFIRM_STATUS_4(4, "附件已审核"),
    ;


    private int code;
    private String msg;

    MoneyEnum(int code, String msg) {
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
