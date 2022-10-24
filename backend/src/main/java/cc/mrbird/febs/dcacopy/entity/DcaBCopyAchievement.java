package cc.mrbird.febs.dcacopy.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 主要医疗业绩
 * </p>
 *
 * @author viki
 * @since 2020-12-28
 */

@Excel("dca_b_copy_achievement")
@Data
@Accessors(chain = true)
public class DcaBCopyAchievement implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                                @ExcelField(value ="主键")
    private String id;

    /**
     * 申报年度
     */
            @ExcelField(value ="申报年度")
    private String dcaYear;
    /**
     * 岗位等级
     */
    @ExcelField(value ="岗位等级")
    private String gwdj;
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
     * 名称
     */
            @ExcelField(value ="名称")
    private String achievementName;

    /**
     * 排名
     */
            @ExcelField(value ="排名")
    private Integer rankIndex;

    /**
     * 等级
     */
            @ExcelField(value ="等级")
    private String achievementGrade;

    /**
     * 获得时间
     */
            @ExcelField(value ="获得时间")
    private Date achievementDate;
    private transient String achievementDateFrom;
    private transient String achievementDateTo;

    /**
     * 期限
     */
            @ExcelField(value ="期限")
    private String achievementDefine;

    /**
     * 备注
     */
            @ExcelField(value ="备注")
    private String achievementContent;

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
            @ExcelField(value ="创建时间")
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
            @ExcelField(value ="修改时间")
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
            @ExcelField(value ="审核时间")
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

    /**
     * 排序
     */
            @ExcelField(value ="排序")
    private Integer displayIndex;
    @ExcelField(value ="立项/获奖")
            private  String lxhj;



    public static final String ID ="id" ;

    public static final String DCA_YEAR ="dca_year" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String ACHIEVEMENT_NAME ="achievement_name" ;

    public static final String RANK_INDEX ="rank_index" ;

    public static final String ACHIEVEMENT_GRADE ="achievement_grade" ;

    public static final String ACHIEVEMENT_DATE ="achievement_date" ;

    public static final String ACHIEVEMENT_DEFINE ="achievement_define" ;

    public static final String ACHIEVEMENT_CONTENT ="achievement_content" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String AUDITMAN ="auditMan" ;

    public static final String AUDITMANNAME ="auditManName" ;

    public static final String AUDITDATE ="auditDate" ;

    public static final String AUDITSUGGESTION ="auditSuggestion" ;

    public static final String ISUSE ="IsUse" ;

    public static final String DISPLAY_INDEX ="display_index" ;

        }