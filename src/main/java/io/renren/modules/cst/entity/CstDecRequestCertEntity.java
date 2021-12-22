package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 进出口报关单 申请单证信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Data
@TableName("cst_dec_request_cert")
public class CstDecRequestCertEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 申请单证代码串【报检人申请对本批货物出具的证单种类】
     */
    private String appCertCode;
    /**
     * 申请单证正本数【报检人申请证单的正本数量是申请单证附表内容】
     */
    private String applOri;
    /**
     * 申请单证副本数【报检人申请证单的副本数量是申请单证附表内容】
     */
    private String applCopyQuan;

}
