package io.renren.common.enumeration;

/**
 * @Description: 合同类型
 * @Param:
 * @return:
 * @Author: chenning
 * @Date: 2020/1/4
 */
public enum ContractEnum {

    CONTRACT_TYPE_1(1, "进料加工合同"),
    CONTRACT_TYPE_2(2, "来料加工合同"),
    CONTRACT_TYPE_3(3, "进口合同"),
    CONTRACT_TYPE_4(4, "出口合同"),
    CONTRACT_TYPE_5(5, "国内采购合同"),
    CONTRACT_TYPE_6(6, "国内销售合同"),
    CONTRACT_TYPE_11(11, "进料加工合同进口子合同"),
    CONTRACT_TYPE_12(12, "进料加工合同出口子合同"),
    CONTRACT_TYPE_13(13, "加工合同国内采购子合同"),
    CONTRACT_TYPE_20(20, "委托加工合同（国内）"),


    CONTRACT_STATUS_1(1, "编辑中"),
    CONTRACT_STATUS_2(2, "审核中"),
    CONTRACT_STATUS_3(3, "审核失败"),
    CONTRACT_STATUS_4(4, "已开始"),
    CONTRACT_STATUS_11(11, "结案待审"),
    CONTRACT_STATUS_12(12, "结案退审"),
    CONTRACT_STATUS_13(13, "已结案"),
    CONTRACT_STATUS_99(99, "中止（垃圾箱）"),
    CONTRACT_STATUS_LOSE_1(-1, "子合同随根合同状态，不可单独改变"),

    ;

    private int code;
    private String msg;

    ContractEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ContractEnum findByCode(int code) {
        for (ContractEnum e : ContractEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
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
