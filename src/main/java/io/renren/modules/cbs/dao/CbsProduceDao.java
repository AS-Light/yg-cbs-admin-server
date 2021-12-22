package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsProduceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 生产单表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Mapper
@Repository
public interface CbsProduceDao extends BaseMapper<CbsProduceEntity> {

    IPage<CbsProduceEntity> queryIndex(IPage<CbsProduceEntity> page, @Param(Constants.WRAPPER) CbsProduceEntity entity);

    List<CbsProduceEntity> queryByContractIdWithGoods(Long contractId);

    CbsProduceEntity selectById(Long id);

    CbsProduceEntity simpleDetail(Long id);

    CbsProduceEntity detail(Long id);

    CbsProduceEntity simpleDetailWithGoodsItems(Long id);

    List<CbsProduceEntity> selectByContractId(Long id);

    List<CbsProduceEntity> listForRawMaterialBack();
}
