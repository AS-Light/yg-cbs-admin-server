package io.renren.common.enumeration;

public enum PartnerTypeEnum {
    PARTNER_TYPE_1(1, "国际供货来源"),
    PARTNER_TYPE_2(2, "国际销售对象"),
    PARTNER_TYPE_3(3, "经营单位"),
    PARTNER_TYPE_4(4, "生产单位"),
    PARTNER_TYPE_10(10, "国内供货来源"),
    PARTNER_TYPE_11(11, "国内销售对象");

    private int code;
    private String msg;

    PartnerTypeEnum(int code, String msg) {
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
