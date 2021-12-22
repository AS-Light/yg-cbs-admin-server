package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbMoneyOutEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 进口单产生的支出流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbMoneyOutService extends IService<CtbMoneyOutEntity> {

    PageUtils queryPage(CtbMoneyOutEntity entity);

    void reimbursement(List<Long> asList, Long operator);

    void updateMoneyImg(CtbMoneyOutEntity entity);

    void confirmAnnex(CtbMoneyOutEntity entity);

    String reimbursementExcel(HttpServletResponse response, List<Long> asList);
}

