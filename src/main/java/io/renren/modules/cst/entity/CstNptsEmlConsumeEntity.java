package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 加工贸易手册单耗表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-14 10:46:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cst_npts_eml_consume")
public class CstNptsEmlConsumeEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联加工贸易手册表头
     */
    private Long fkEmlHeaderId;
    /**
     * 预录入统一编号 当企业执行手册首次暂存操作时，无需填写，电子口岸统一返回。
     * 电子口岸返回预录入统一编号后，之后暂存或申报数据时必填。
     */
    private String seqNo;
    /**
     * 成品序号
     */
    private String endprdSeqno;
    /**
     * 成品料号
     */
    private String endprdGdsMtno;
    /**
     * 料件序号
     */
    private String mtpckSeqno;
    /**
     * 料件料号
     */
    private String mtpckGdsMtno;
    /**
     * 单耗版本号 默认为0，不区分版本
     */
    private String ucnsVerno;
    /**
     * 单耗数量(未启用)
     */
    private BigDecimal ucnsQty;
    /**
     * 净耗数量
     */
    private BigDecimal netUseupQty;
    /**
     * 有形损耗率
     */
    private BigDecimal tgblLossRate;
    /**
     * 无形损耗率
     */
    private BigDecimal intgbLossRate;
    /**
     * 单耗申报状态代码 1-未申报 2-已申报 3-已确定，默认为2-已申报
     */
    private String ucnsDclStucd;
    /**
     * 修改标记代码 0-未修改 1-修改 2-删除 3-增加
     */
    private String modfMarkcd;
    /**
     * 保税料件比例
     */
    private BigDecimal bondMtpckPrpr;
    /**
     * 1-运行 2-停用，默认为1，企业申报
     */
    private String etpsExeMarkcd;
    /**
     * 备注
     */
    private String rmk;
    /**
     * 序号
     */
    private Long gseqNo;
    /**
     * 成品商品编码
     */
    private String endprdGdecd;
    /**
     * 成品商品名称
     */
    private String endprdGdsNm;
    /**
     * 料件商品编码
     */
    private String mtpckGdecd;
    /**
     * 料件商品名称
     */
    private String mtpckGdsNm;

}
