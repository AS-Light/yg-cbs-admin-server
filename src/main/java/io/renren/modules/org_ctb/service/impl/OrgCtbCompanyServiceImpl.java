package io.renren.modules.org_ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.org_ctb.dao.OrgCtbCompanyDao;
import io.renren.modules.org_ctb.entity.OrgCtbCompanyEntity;
import io.renren.modules.org_ctb.service.OrgCtbCompanyService;
import io.renren.modules.org_ctb.service.OrgCtbMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service("orgCtbCompanyService")
public class OrgCtbCompanyServiceImpl extends ServiceImpl<OrgCtbCompanyDao, OrgCtbCompanyEntity> implements OrgCtbCompanyService {

    @Autowired
    private OrgCtbMenuService orgCtbMenuService;

    @Override
    public PageUtils queryIndex(OrgCtbCompanyEntity entity) {
        QueryWrapper<OrgCtbCompanyEntity> qw = new QueryWrapper<>();
        qw.eq("id", entity.getId());
        qw.like(StringUtils.isNotBlank(entity.getCode()), "code", entity.getCode());
        qw.like(StringUtils.isNotBlank(entity.getCustomsCode()), "customs_code", entity.getCode());
        qw.like(StringUtils.isNotBlank(entity.getName()), "name", entity.getName());
        IPage<OrgCtbCompanyEntity> page = this.page(new QueryPage<OrgCtbCompanyEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    @Override
    public List<OrgCtbCompanyEntity> listWithCbsBind(Long cbsCompanyId, String ctbName) {
        return baseMapper.listWithCbsBind(cbsCompanyId, ctbName);
    }


    @Override
    public OrgCtbCompanyEntity selectByCode(String code) {
        return baseMapper.selectOne(new QueryWrapper<OrgCtbCompanyEntity>().eq("code", code));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCompany(OrgCtbCompanyEntity orgCtbCompany) {
        //????????????????????????
        checkPrems(orgCtbCompany);
        Integer count = baseMapper.selectCount(new QueryWrapper<>()) + 1;
        orgCtbCompany.setCode(getConteactNo("DL", count));
        this.save(orgCtbCompany);
        //???????????????????????????
        orgCtbMenuService.saveOrUpdate(orgCtbCompany.getId(), orgCtbCompany.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCompanyById(OrgCtbCompanyEntity orgCtbCompany) {
        //????????????????????????
        checkPrems(orgCtbCompany);
        this.updateById(orgCtbCompany);
        //???????????????????????????
        orgCtbMenuService.saveOrUpdate(orgCtbCompany.getId(), orgCtbCompany.getMenuIdList());
    }

    /**
     * ???????????????????????????????????????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCompanyInfoOnlyById(OrgCtbCompanyEntity orgCtbCompany) {
        this.updateById(orgCtbCompany);
    }

    /**
     * ????????????????????????
     */
    private void checkPrems(OrgCtbCompanyEntity orgCtbCompany) {
        //???????????????????????????????????????????????????????????????????????????????????????
        if (orgCtbCompany.getCreateUserId() == Constant.SUPER_ADMIN) {
            return;
        }
    }

    /**
     * ??????????????????
     *
     * @param prefix ?????????????????????????????????
     * @param nowNum ????????????????????????
     * @return
     */
    public static String getConteactNo(String prefix, int nowNum) {
        StringBuilder builder = new StringBuilder();
        StringBuilder num = new StringBuilder();
        AtomicInteger count = new AtomicInteger(nowNum);
        // 4?????????????????????????????????9999???????????????001???????????????
        if (count.get() > 9999) {
            count = new AtomicInteger(1);
        }
        // ??????4????????????????????????????????????
        if (count.get() < 10) {
            num.append("00").append(count.getAndIncrement());
        } else if (count.get() >= 100) {
            num.append(count.getAndIncrement());
        } else {
            num.append("0").append(count.getAndIncrement());
        }
        // ?????????
        builder.append(prefix);
        builder.append(num);
        return builder.toString();
    }


}