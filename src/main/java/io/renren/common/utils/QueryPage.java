/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

/**
 * 查询参数
 *
 * @author Mark sunlightcs@gmail.com
 */
public class QueryPage<T> {

    public IPage<T> getPage(BaseEntity base) {
        return this.getPage(base, null, false);
    }

    public IPage<T> getPage(BaseEntity base, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if (base.getPage() != null) {
            curPage = Long.parseLong(base.getPage());
        }
        if (base.getLimit() != null) {
            limit = Long.parseLong(base.getLimit());
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject(base.getOrderField());
        String order = base.getOrder();

        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                return page.setAsc(orderField);
            } else {
                return page.setDesc(orderField);
            }
        }

        if (defaultOrderField == null) {
            return page;
        }

        //默认排序
        if (isAsc) {
            page.setAsc(defaultOrderField);
        } else {
            page.setDesc(defaultOrderField);
        }

        return page;
    }
}
