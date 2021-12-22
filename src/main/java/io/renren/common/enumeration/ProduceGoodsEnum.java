package io.renren.common.enumeration;

/**
 * @Description: 产品
 * @Param:
 * @return:
 * @Author: chenning
 * @Date: 2020/1/4
 */
public enum ProduceGoodsEnum {

    CONTRACT_TYPE_1(1, "原料"),
    CONTRACT_TYPE_2(2, "产品"),

    CONTRACT_STATUS_1(1, "生产单创建（缺省）"),
    CONTRACT_STATUS_2(2, "生产开始提审（提审计划）"),
    CONTRACT_STATUS_3(3, "生产开始打回（退审计划）"),
    CONTRACT_STATUS_4(4, "生产开始审核通过（生产中）"),
    CONTRACT_STATUS_5(5, "生产结束提审（提审完成）"),
    CONTRACT_STATUS_6(6, "生产结束打回（退审完成）"),
    CONTRACT_STATUS_7(7, "生产结束审核通过（生产完成）"),
    CONTRACT_STATUS_99(99, "生产中止"),

    ;

    private int code;
    private String msg;

    ProduceGoodsEnum(int code, String msg) {
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
