package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbMoneyTicketsEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * 出口单产生的收入流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
public interface CtbMoneyTicketsService extends IService<CtbMoneyTicketsEntity> {

    PageUtils queryPage(CtbMoneyTicketsEntity entity);

    void updateMoneyImg(CtbMoneyTicketsEntity entity);

    void confirmAnnex(CtbMoneyTicketsEntity entity);

    void uploadInvoice(CtbMoneyTicketsEntity entity);

    void confirmInvoice(CtbMoneyTicketsEntity entity);

    String uploadInvoiceExcel(HttpServletResponse response, CtbMoneyTicketsEntity entity);
}

