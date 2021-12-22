package io.renren.modules.org_ctb.config;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.shiro.SecurityUtils;

public class CtbTenantHandler implements TenantHandler {

    @Override
    public Expression getTenantId(boolean where) {
        return tenantIdCondition();
    }

    private Expression tenantIdCondition() {
        Object oUser = SecurityUtils.getSubject().getPrincipal();
        if (oUser instanceof OrgCtbUserEntity) {
            return new LongValue(((OrgCtbUserEntity) oUser).getCtbTenantId());//ID自己想办法获取到
        } else {
            return null;
        }
    }

    @Override
    public String getTenantIdColumn() {
        return "ctb_tenant_id";
    }

    @Override
    public boolean doTableFilter(String tableName) {
        if ("org_ctb_log".equalsIgnoreCase(tableName) ||
                "org_ctb_menu".equalsIgnoreCase(tableName) ||
                "org_ctb_role".equalsIgnoreCase(tableName)) {
            // 组织结构表
            return false;
        } else if ("ctb_order_processing_trade".equalsIgnoreCase(tableName) ||
                "ctb_import".equalsIgnoreCase(tableName) ||
                "ctb_export".equalsIgnoreCase(tableName) ||
                "ctb_money_in".equalsIgnoreCase(tableName) ||
                "ctb_money_out".equalsIgnoreCase(tableName) ||
                "ctb_money_tickets".equalsIgnoreCase(tableName) ||
                "ctb_money_type".equalsIgnoreCase(tableName) ||
                "ctb_store_goods".equalsIgnoreCase(tableName) ||
                "ctb_store_goods_in".equalsIgnoreCase(tableName) ||
                "ctb_store_goods_out".equalsIgnoreCase(tableName)) {
            // 业务表
            return false;
        } else if (tableName.contains("ctb_directory")) {
            // 所有字典
            return false;
        } else if (tableName.equals("ctb_partner")) {
            // 合作伙伴
            return false;
        } else if (tableName.equals("ctb_document_control")) {
            // 文控中心
            return false;
        } else {
            return true;
        }
    }

}