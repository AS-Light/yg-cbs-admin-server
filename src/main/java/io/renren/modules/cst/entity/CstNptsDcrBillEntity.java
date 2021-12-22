package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 手册报核清单表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@Data
@TableName("cst_npts_dcr_bill")
public class CstNptsDcrBillEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 预录入统一编号
     */
    private String seqNo;
    /**
     * 序号 按照自然数排序，不可断号
     */
    private Long gNo;
    /**
     * 清单编号
     */
    private String bondInvtNo;
    /**
     * 进出口标志 I：进口；E：出口
     */
    private String ieFlag;

}
