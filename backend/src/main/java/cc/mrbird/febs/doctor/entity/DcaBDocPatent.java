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
 * 申请专利情况
 * </p>
 *
 * @author viki
 * @since 2021-01-13
 */

@Excel("dca_b_doc_patent")
@Data
@Accessors(chain = true)
public class DcaBDocPatent implements Serializable{

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
     * 专利号
     */
        
        @ExcelField(value ="专利号")
    private String patentCode;

    /**
     * 专利名称
     */
        
        @ExcelField(value ="专利名称")
    private String patentName;

    /**
     * 专利类别
     */
        
        @ExcelField(value ="专利类别")
    private String patentType;

    /**
     * 分数
     */

    @ExcelField(value ="分数")
    private String fenshu;

    /**
     * 批准年月
     */
        
        @ExcelField(value ="批准年月", writeConverter = DateConverter.class)
    private Date patentDate;
    private transient String patentDateFrom;
    private transient String patentDateTo;

    /**
     * 合作导师排名
     */
        
        @ExcelField(value ="合作导师排名")
    private Integer daoshiRanknum;

    /**
     * 本人排名
     */
        
        @ExcelField(value ="本人排名")
    private Integer patentRanknum;

    /**
     * 是否授权
     */
        
        @ExcelField(value ="是否授权")
    private String isAuthority;

    /**
     * 是否转让
     */
        
        @ExcelField(value ="是否转让")
    private String isZhuanrang;

    /**
     * 转让效益
     */
        
        @ExcelField(value ="转让效益")
    private String patentGood;

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

    public static final String FILE_URL ="file_url" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String PATENT_CODE ="patent_code" ;

    public static final String PATENT_NAME ="patent_name" ;

    public static final String PATENT_TYPE ="patent_type" ;

    public static final String PATENT_DATE ="patent_date" ;

    public static final String DAOSHI_RANKNUM ="daoshi_ranknum" ;

    public static final String PATENT_RANKNUM ="patent_ranknum" ;

    public static final String IS_AUTHORITY ="is_authority" ;

    public static final String IS_ZHUANRANG ="is_zhuanrang" ;

    public static final String PATENT_GOOD ="patent_good" ;

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