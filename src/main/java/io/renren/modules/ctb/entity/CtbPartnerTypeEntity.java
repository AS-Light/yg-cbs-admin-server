package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.cbs.entity.CbsPartnerTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ctb_partner的type表 ，同一个partner可以有多种类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_partner_type")
public class CtbPartnerTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public CtbPartnerTypeEntity(CbsPartnerTypeEntity cbsPartnerTypeEntity, Long partnerId) {
        fkPartnerId = partnerId;
        type = cbsPartnerTypeEntity.getType();
    }

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联ctb_partner
     */
    private Long fkPartnerId;
    /**
     * 1、采购（料件）来源方 2、销售（成品）对象 3、进出口（经营）单位 4、生产（加工）单位 10、国内供货商 11、国内销售对象
     */
    private Integer type;

}
