package io.renren.common.enumeration;

/**
 * @Description: 文控中心
 * @Param:
 * @return:
 * @Author: chenning
 * @Date: 2020/1/4
 */
public enum CtbDocumentControlEnum {

    TYPE_ORDER_PROCESSING_TRADE("ctb_img_order_processing_trade", "合同附件"),// 1
    TYPE_ORDER_PROCESSING_TRADE_PROCESSING_RECORD(" ctb_img_order_processing_trade_processing_record", "来料加工合同海关备案文件图片"),// 1
    TYPE_ORDER_PROCESSING_TRADE_RECORD(" ctb_img_order_processing_trade_record", "海关备案文件图片"),// 0
    TYPE_CONTRACT_SHIP(" ctb_img_contract_ship", "船务合同图片"),// 0
    TYPE_EXPORT_CONTRACT(" ctb_img_export_contract", "出口合同图片"),// 1
    TYPE_EXPORT_CONTRACT_SHIP(" ctb_img_export_contract_ship", "出口船务合同图片"),// 1
    TYPE_EXPORT_DELIVERY_ORDER(" ctb_img_export_delivery_order", "出口提货单图片"),// 1
    TYPE_EXPORT_ENTRY_BILL(" ctb_img_export_entry_bill", "出口报关单图片"),// 1
    TYPE_EXPORT_INVOICE(" ctb_img_export_invoice", "出口发票附件图片"),// 1
    TYPE_EXPORT_LADING_BILL(" ctb_img_export_lading_bill", "出口提单图片"),// 1
    TYPE_EXPORT_LICENSE_AGREEMENT(" ctb_img_export_license_agreement", "出口报关授权协议附件"),// 1
    TYPE_EXPORT_OTHERS(" ctb_img_export_others", "出口其他附件图片"),// 1
    TYPE_EXPORT_PACKING_LIST(" ctb_img_export_packing_list", "出口箱单附件图片"),// 1
    TYPE_EXPORT_PAY_IN_ADVANCE(" ctb_img_export_pay_in_advance", "出口首付款附件"),// 1
    TYPE_EXPORT_PAY_TAIL(" ctb_img_export_pay_tail", "出口尾款附件"),// 1
    TYPE_EXPORT_POWER_OF_ATTORNEY(" ctb_img_export_power_of_attorney", "出口授权书附件"),// 1
    TYPE_IMPORT_CONTRACT(" ctb_img_import_contract", "进口合同图片"),// 1
    TYPE_IMPORT_CONTRACT_SHIP(" ctb_img_import_contract_ship", "进口船务合同图片"),// 1
    TYPE_IMPORT_DELIVERY_ORDER(" ctb_img_import_delivery_order", "进口提货单图片"),// 1
    TYPE_IMPORT_ENTRY_BILL(" ctb_img_import_entry_bill", "进口报关单图片"),// 1
    TYPE_IMPORT_INVOICE(" ctb_img_import_invoice", "进口发票附件图片"),// 1
    TYPE_IMPORT_LADING_BILL(" ctb_img_import_lading_bill", "进口提单图片"),// 1
    TYPE_IMPORT_LICENSE_AGREEMENT(" ctb_img_import_license_agreement", "进口报关授权协议附件"),// 1
    TYPE_IMPORT_OTHERS(" ctb_img_import_others", "进口其他附件图片"),// 1
    TYPE_IMPORT_PACKING_LIST(" ctb_img_import_packing_list", "进口箱单附件图片"),// 1
    TYPE_IMPORT_PAY_IN_ADVANCE(" ctb_img_import_pay_in_advance", "进口首付款附件"),// 1
    TYPE_IMPORT_PAY_TAIL(" ctb_img_import_pay_tail", "进口尾款附件"),// 1
    TYPE_IMPORT_POWER_OF_ATTORNEY(" ctb_img_import_power_of_attorney", "进口授权书附件"),// 1
    TYPE_MONEY_IN(" ctb_img_money_in", "财务收入图片"),// 1
    TYPE_MONEY_OUT(" ctb_img_money_out", "财务支出图片"),// 1
    TYPE_STORE_GOODS_IN_DELIVERY_ORDER(" ctb_img_store_goods_in_delivery_order", "入库提货单图片"),// 1
    TYPE_STORE_GOODS_IN_RECEIPT(" ctb_img_store_goods_in_receipt", "入库签收证明图片"),// 1
    TYPE_STORE_GOODS_OUT_DELIVERY_ORDER(" ctb_img_store_goods_out_delivery_order", "出库提货单图片"),// 1
    TYPE_STORE_GOODS_OUT_RECEIPT(" ctb_img_store_goods_out_receipt", "出库签收证明图片"),// 1
    ;


    private String code;
    private String msg;

    CtbDocumentControlEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
