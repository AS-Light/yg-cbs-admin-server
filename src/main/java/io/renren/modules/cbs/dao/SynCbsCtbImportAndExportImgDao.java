package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.vo.SynImportAndExportImgVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-27 10:02:18
 */
@Mapper
@Repository
public interface SynCbsCtbImportAndExportImgDao {
    List<String> selectByField(@Param(Constants.WRAPPER) SynImportAndExportImgVo vo);

    Integer deleteByField(@Param(Constants.WRAPPER) SynImportAndExportImgVo vo);

    Integer insertImg(@Param(Constants.WRAPPER) SynImportAndExportImgVo vo);
}
