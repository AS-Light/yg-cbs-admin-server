package io.renren.common.enumeration;

/**
 * @Description: 进口单业务判断
 * @Param:
 * @return:
 * @Author: chenning
 * @Date: 2020/1/4
 */
public enum InOutEnum {

    BUSINESS_JUDGMENT_1(1, "成功"),
    BUSINESS_JUDGMENT_0(0, "未知异常，请联系管理员"),
    BUSINESS_JUDGMENT_NEGATIVE_1(-1, "状态不合法，待审核状态才能撤销提审"),
    BUSINESS_JUDGMENT_NEGATIVE_2(-2, "状态不合法，编辑中和不通过状态才能提交审核"),
    BUSINESS_JUDGMENT_NEGATIVE_3(-3, "没有找到这个进口单,请联系管理员"),
    BUSINESS_JUDGMENT_NEGATIVE_4(-4, "状态不合法，请先提交审核"),

    // 1：编辑中 2：审核中 3：审核失败 4：审核通过（编辑报关） 11：报关提审 12：报关审核失败 13：报关审核通过 14：已完关（已入库-关联入库单） 99：中止
    STATUS_1(1, "编辑中"),
    STATUS_2(2, "审核中"),
    STATUS_3(3, "审核失败"),
    STATUS_4(4, "审核通过"),
    STATUS_11(11, "报关提审"),
    STATUS_12(12, "报关审核失败"),
    STATUS_13(13, "报关审核通过"),
    STATUS_14(14, "已完关"),
    STATUS_99(99, "中止"),

    ;


    private int code;
    private String msg;

    InOutEnum(int code, String msg) {
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
