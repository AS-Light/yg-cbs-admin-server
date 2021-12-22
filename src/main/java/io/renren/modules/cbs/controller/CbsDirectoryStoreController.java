package io.renren.modules.cbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsDirectoryStoreEntity;
import io.renren.modules.cbs.service.CbsDirectoryStoreService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 仓库名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@RestController
@RequestMapping("cbs/directory/store")
public class CbsDirectoryStoreController {

    private CbsDirectoryStoreService cbsDirectoryStoreService;

    @Autowired
    public void setCbsDirectoryStoreService(CbsDirectoryStoreService cbsDirectoryStoreService) {
        this.cbsDirectoryStoreService = cbsDirectoryStoreService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:directory:store:list")
    public R list(@RequestBody CbsDirectoryStoreEntity entity) {
        PageUtils page = cbsDirectoryStoreService.queryPage(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:directory:store:info")
    public R info(@PathVariable("id") Long id) {
        CbsDirectoryStoreEntity cbsDirectoryStore = cbsDirectoryStoreService.getById(id);
        return R.ok().put("cbsDirectoryStore", cbsDirectoryStore);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:directory:store:save")
    public R save(@RequestBody CbsDirectoryStoreEntity cbsDirectoryStore) {
        cbsDirectoryStoreService.save(cbsDirectoryStore);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:directory:store:update")
    public R update(@RequestBody CbsDirectoryStoreEntity cbsDirectoryStore) {
        cbsDirectoryStoreService.updateById(cbsDirectoryStore);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:directory:store:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsDirectoryStoreService.update(CbsDirectoryStoreEntity.builder().available(0).build(), new QueryWrapper<CbsDirectoryStoreEntity>().in("id", ids));
        return R.ok();
    }

}
