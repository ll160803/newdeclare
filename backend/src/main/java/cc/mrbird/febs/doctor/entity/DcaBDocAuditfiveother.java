package cc.mrbird.febs.doctor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * 中期考核
 * </p>
 *
 * @author viki
 * @since 2022-11-14
 */

@Excel("dca_b_doc_auditfiveother")
@Data
@Accessors(chain = true)
public class DcaBDocAuditfiveother implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

    /**
     * 附件
     */
        
        @ExcelField(value ="附件")
    private String fileId;

    /**
     * 考核结果
     */
        
        @ExcelField(value ="考核结果")
    private String khjg;

    /**
     * 考核类型
     */
        
        @ExcelField(value ="考核类型")
    private String khtype;

    /**
     * 考核时间
     */
        
        @ExcelField(value ="考核时间", writeConverter = DateConverter.class)
    private Date khdate;
    private transient String khdateFrom;
    private transient String khdateTo;

    /**
     * 附件地址
     */
        
        @ExcelField(value ="附件地址")
    private String fileUrl;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 人事编号
     */
        
        @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 填写日期
     */
        
        @ExcelField(value ="填写日期", writeConverter = DateConverter.class)
    private Date txDate;
    private transient String txDateFrom;
    private transient String txDateTo;

    /**
     * 成果概述
     */
        
        @ExcelField(value ="成果概述")
    private String cggs;

    /**
     * 主持研究的项目名称及研究计划
     */
        
        @ExcelField(value ="主持研究的项目名称及研究计划")
    private String zcyjxmyj;

    /**
     * 博士后本人中期工作小结
     */
        
        @ExcelField(value ="博士后本人中期工作小结")
    private String bshbrzqxj;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String adContent;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
        
        @ExcelField(value ="是否删除")
    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 排序
     */
        
        @ExcelField(value ="排序")
    private Integer displayIndex;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        
        @ExcelField(value ="修改时间", writeConverter = DateConverter.class)
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
        
        @ExcelField(value ="创建人")
    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
        
        @ExcelField(value ="修改人")
    private Long modifyUserId;

    /**
     * 审核人
     */
    @TableField("auditMan")
        
        @ExcelField(value ="审核人")
    private String auditMan;

    /**
     * 审核人姓名
     */
    @TableField("auditManName")
        
        @ExcelField(value ="审核人姓名")
    private String auditManName;

    /**
     * 审核时间
     */
    @TableField("auditDate")
        
        @ExcelField(value ="审核时间", writeConverter = DateConverter.class)
    private Date auditDate;
    private transient String auditDateFrom;
    private transient String auditDateTo;

    /**
     * 审核意见
     */
    @TableField("auditSuggestion")
        
        @ExcelField(value ="审核意见")
    private String auditSuggestion;

    /**
     * 是否用于本次评审
     */
    @TableField("IsUse")
        
        @ExcelField(value ="是否用于本次评审")
    private Boolean IsUse;



    public static final String ID ="id" ;

    public static final String FILE_ID ="file_id" ;

    public static final String KHJG ="khjg" ;

    public static final String KHTYPE ="khtype" ;

    public static final String KHDATE ="khdate" ;

    public static final String FILE_URL ="file_url" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String TX_DATE ="tx_date" ;

    public static final String CGGS ="cggs" ;

    public static final String ZCYJXMYJ ="zcyjxmyj" ;

    public static final String BSHBRZQXJ ="bshbrzqxj" ;

    public static final String AD_CONTENT ="ad_content" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String AUDITMAN ="auditMan" ;

    public static final String AUDITMANNAME ="auditManName" ;

    public static final String AUDITDATE ="auditDate" ;

    public static final String AUDITSUGGESTION ="auditSuggestion" ;

    public static final String ISUSE ="IsUse" ;

        }