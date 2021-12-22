package io.renren.common.enumeration;

/**
 * @Description: 仓储状态
 * @Param:
 * @return:
 * @Author: chenning
 * @Date: 2020/1/4
 */
public enum StoreGoodsEnum {

    CONTRACT_TYPE_1(1, "进口"),
    CONTRACT_TYPE_2(2, "生产"),
    CONTRACT_TYPE_3(3, "国内"),

    ;

    private int code;
    private String msg;

    StoreGoodsEnum(int code, String msg) {
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
