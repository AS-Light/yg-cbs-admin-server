package io.renren.modules.org_cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.org_cbs.dao.OrgCbsCompanyDao;
import io.renren.modules.org_cbs.entity.OrgCbsCompanyEntity;
import io.renren.modules.org_cbs.service.OrgCbsCompanyService;
import io.renren.modules.org_cbs.service.OrgCbsMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;


@Service("orgCbsCompanyService")
public class OrgCbsCompanyServiceImpl extends ServiceImpl<OrgCbsCompanyDao, OrgCbsCompanyEntity> implements OrgCbsCompanyService {

    @Autowired
    private OrgCbsMenuService orgCbsMenuService;

    @Override
    public PageUtils queryIndex(OrgCbsCompanyEntity entity) {
        QueryWrapper<OrgCbsCompanyEntity> qw = new QueryWrapper<>();
        qw.eq("id", entity.getId());
        qw.like(StringUtils.isNotBlank(entity.getCode()), "code", entity.getCode());
        qw.like(StringUtils.isNotBlank(entity.getCustomsCode()), "customs_code", entity.getCode());
        qw.like(StringUtils.isNotBlank(entity.getName()), "name", entity.getName());
        IPage<OrgCbsCompanyEntity> page = this.page(new QueryPage<OrgCbsCompanyEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    @Override
    public OrgCbsCompanyEntity selectByCode(String code) {
        return baseMapper.selectOne(new QueryWrapper<OrgCbsCompanyEntity>().eq("code", code));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCompany(OrgCbsCompanyEntity orgCbsCompany) {
        //检查权限是否越权
        checkPrems(orgCbsCompany);
        Integer count = baseMapper.selectCount(new QueryWrapper<>()) + 1;
        orgCbsCompany.setCode(getConteactNo("DL", count));
        this.save(orgCbsCompany);
        //保存公司与菜单关系
        orgCbsMenuService.saveOrUpdate(orgCbsCompany.getId(), orgCbsCompany.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCompanyById(OrgCbsCompanyEntity orgCbsCompany) {
        //检查权限是否越权
        checkPrems(orgCbsCompany);
        this.updateById(orgCbsCompany);
        //保存公司与菜单关系
        orgCbsMenuService.saveOrUpdate(orgCbsCompany.getId(), orgCbsCompany.getMenuIdList());
    }

    /**
     * 只更新公司信息，不更新权限
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCompanyInfoOnlyById(OrgCbsCompanyEntity orgCbsCompany) {
        this.updateById(orgCbsCompany);
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(OrgCbsCompanyEntity orgCbsCompany) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (orgCbsCompany.getCreateUserId() == Constant.SUPER_ADMIN) {
            return;
        }
    }

    /**
     * 自动生成编号
     *
     * @param prefix 前缀，往往是一串字符串
     * @param nowNum 当前要生成的数字
     * @return
     */
    public static String getConteactNo(String prefix, int nowNum) {
        StringBuilder builder = new StringBuilder();
        StringBuilder num = new StringBuilder();
        AtomicInteger count = new AtomicInteger(nowNum);
        // 4位数字的采取编号处理。9999的情况下从001开始采取。
        if (count.get() > 9999) {
            count = new AtomicInteger(1);
        }
        // 采用4位数的数字进行序号处理。
        if (count.get() < 10) {
            num.append("00").append(count.getAndIncrement());
        } else if (count.get() >= 100) {
            num.append(count.getAndIncrement());
        } else {
            num.append("0").append(count.getAndIncrement());
        }
        // 组合。
        builder.append(prefix);
        builder.append(num);
        return builder.toString();
    }


}