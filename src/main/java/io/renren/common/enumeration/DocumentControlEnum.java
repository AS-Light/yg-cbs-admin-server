package io.renren.common.enumeration;

/**
 * @Description: 文控中心
 * @Param:
 * @return:
 * @Author: chenning
 * @Date: 2020/1/4
 */
public enum DocumentControlEnum {

    TYPE_CONTRACT(" cbs_img_contract", "合同附件"),// 1
    TYPE_CONTRACT_PROCESSING(" cbs_img_contract_processing", "来料加工合同图片"),// 0
    TYPE_CONTRACT_PROCESSING_RECORD(" cbs_img_contract_processing_record", "来料加工合同海关备案文件图片"),// 1
    TYPE_CONTRACT_RECORD(" cbs_img_contract_record", "海关备案文件图片"),// 0
    TYPE_CONTRACT_SHIP(" cbs_img_contract_ship", "船务合同图片"),// 0
    TYPE_EXPORT_CONTRACT_SHIP(" cbs_img_export_contract_ship", "出口船务合同图片"),// 1
    TYPE_EXPORT_DELIVERY_ORDER(" cbs_img_export_delivery_order", "出口提货单图片"),// 1
    TYPE_EXPORT_ENTRY_BILL(" cbs_img_export_entry_bill", "出口报关单图片"),// 1
    TYPE_EXPORT_INVOICE(" cbs_img_export_invoice", "出口发票附件图片"),// 1
    TYPE_EXPORT_LADING_BILL(" cbs_img_export_lading_bill", "出口提单图片"),// 1
    TYPE_EXPORT_LICENSE_AGREEMENT(" cbs_img_export_license_agreement", "出口报关授权协议附件"),// 1
    TYPE_EXPORT_OTHERS(" cbs_img_export_others", "出口其他附件图片"),// 1
    TYPE_EXPORT_PACKING_LIST(" cbs_img_export_packing_list", "出口箱单附件图片"),// 1
    TYPE_EXPORT_PAY_IN_ADVANCE(" cbs_img_export_pay_in_advance", "出口首付款附件"),// 1
    TYPE_EXPORT_PAY_TAIL(" cbs_img_export_pay_tail", "出口尾款附件"),// 1
    TYPE_EXPORT_POWER_OF_ATTORNEY(" cbs_img_export_power_of_attorney", "出口授权书附件"),// 1
    TYPE_IMPORT_CONTRACT_SHIP(" cbs_img_import_contract_ship", "进口船务合同图片"),// 1
    TYPE_IMPORT_DELIVERY_ORDER(" cbs_img_import_delivery_order", "进口提货单图片"),// 1
    TYPE_IMPORT_ENTRY_BILL(" cbs_img_import_entry_bill", "进口报关单图片"),// 1
    TYPE_IMPORT_INVOICE(" cbs_img_import_invoice", "进口发票附件图片"),// 1
    TYPE_IMPORT_LADING_BILL(" cbs_img_import_lading_bill", "进口提单图片"),// 1
    TYPE_IMPORT_LICENSE_AGREEMENT(" cbs_img_import_license_agreement", "进口报关授权协议附件"),// 1
    TYPE_IMPORT_OTHERS(" cbs_img_import_others", "进口其他附件图片"),// 1
    TYPE_IMPORT_PACKING_LIST(" cbs_img_import_packing_list", "进口箱单附件图片"),// 1
    TYPE_IMPORT_PAY_IN_ADVANCE(" cbs_img_import_pay_in_advance", "进口首付款附件"),// 1
    TYPE_IMPORT_PAY_TAIL(" cbs_img_import_pay_tail", "进口尾款附件"),// 1
    TYPE_IMPORT_POWER_OF_ATTORNEY(" cbs_img_import_power_of_attorney", "进口授权书附件"),// 1
    TYPE_MONEY_IN(" cbs_img_money_in", "财务收入图片"),// 1
    TYPE_MONEY_OUT(" cbs_img_money_out", "财务支出图片"),// 1
    TYPE_PACKING_LIST(" cbs_img_packing_list", "箱单附件图片"),// 0
    TYPE_PRODUCE_GOODS_STREAM(" cbs_img_produce_goods_stream", "生产验收报告"),// 0
    TYPE_PURCHASE_DELIVERY_ORDER(" cbs_img_purchase_delivery_order", "国内收货提货单图片"),// 1
    TYPE_PURCHASE_INVOICE(" cbs_img_purchase_invoice", "国内采购发票附件图片"),// 1
    TYPE_PURCHASE_RECEIPT(" cbs_img_purchase_receipt", "国内收货签收证明图片"),// 1
    TYPE_SELL_DELIVERY_ORDER(" cbs_img_sell_delivery_order", "国内发货提货单图片"),// 1
    TYPE_SELL_INVOICE(" cbs_img_sell_invoice", "国内销售发票附件图片"),// 1
    TYPE_SELL_RECEIPT(" cbs_img_sell_receipt", "国内发货签收证明图片"),// 1
    TYPE_STORE_GOODS_IN_DELIVERY_ORDER(" cbs_img_store_goods_in_delivery_order", "入库提货单图片"),// 1
    TYPE_STORE_GOODS_IN_RECEIPT(" cbs_img_store_goods_in_receipt", "入库签收证明图片"),// 1
    TYPE_STORE_GOODS_OUT_DELIVERY_ORDER(" cbs_img_store_goods_out_delivery_order", "出库提货单图片"),// 1
    TYPE_STORE_GOODS_OUT_RECEIPT(" cbs_img_store_goods_out_receipt", "出库签收证明图片"),// 1



    TYPE_CTB_SERVICE_CONTRACT(" ctb_img_service_contract", "报关行服务企业的合同附件"),// 1
    ;


    private String code;
    private String msg;

    DocumentControlEnum(String code, String msg) {
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
