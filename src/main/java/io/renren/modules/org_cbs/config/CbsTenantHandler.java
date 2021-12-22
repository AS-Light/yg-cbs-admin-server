package io.renren.modules.org_cbs.config;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import io.renren.modules.org_cbs.entity.OrgCbsUserEntity;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.shiro.SecurityUtils;

public class CbsTenantHandler implements TenantHandler {

    @Override
    public Expression getTenantId(boolean where) {
        return tenantIdCondition();
    }

    private Expression tenantIdCondition() {
        Object oUser = SecurityUtils.getSubject().getPrincipal();
        if (oUser instanceof OrgCbsUserEntity) {
            return new LongValue(((OrgCbsUserEntity) oUser).getTenantId());//ID自己想办法获取到
        } else {
            return null;
        }
    }

    @Override
    public String getTenantIdColumn() {
        return "tenant_id";
    }

    @Override
    public boolean doTableFilter(String tableName) {
        if ("org_cbs_log".equalsIgnoreCase(tableName) ||
                "org_cbs_menu".equalsIgnoreCase(tableName) ||
                "org_cbs_role".equalsIgnoreCase(tableName)) {
            // 组织结构表
            return false;
        } else if ("cbs_contract".equalsIgnoreCase(tableName) ||
                "cbs_import".equalsIgnoreCase(tableName) ||
                "cbs_export".equalsIgnoreCase(tableName) ||
                "cbs_purchase".equalsIgnoreCase(tableName) ||
                "cbs_sell".equalsIgnoreCase(tableName) ||
                "cbs_produce".equalsIgnoreCase(tableName) ||
                "cbs_store_goods".equalsIgnoreCase(tableName) ||
                "cbs_store_goods_in".equalsIgnoreCase(tableName) ||
                "cbs_store_goods_out".equalsIgnoreCase(tableName) ||
                "cbs_money_in".equalsIgnoreCase(tableName) ||
                "cbs_money_out".equalsIgnoreCase(tableName) ||
                "cbs_money_in_expected".equalsIgnoreCase(tableName) ||
                "cbs_money_out_expected".equalsIgnoreCase(tableName) ||
                "cbs_money_type_oneself".equalsIgnoreCase(tableName)) {
            // 业务表
            return false;
        } else if (tableName.contains("cbs_directory")) {
            // 所有字典
            return false;
        } else if (tableName.equals("cbs_partner")) {
            // 合作伙伴
            return false;
        } else if (tableName.equals("cbs_document_control")) {
            // 文控中心
            return false;
        } else {
            return true;
        }
    }

}