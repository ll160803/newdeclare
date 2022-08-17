package cc.mrbird.febs.zq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 打分记录
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */

@Excel("zq_d_score")
@Data
@Accessors(chain = true)
public class ZqDScore implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 账号
     */
        
        @ExcelField(value ="账号")
    private String userAccount;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 医师统计科室
     */
        
        @ExcelField(value ="医师统计科室")
    private String deptName;

    /**
     * 分组
     */
        
        @ExcelField(value ="分组")
    private String fenzu;

    /**
     * 账号
     */
        
        @ExcelField(value ="账号")
    private String auditUserAccount;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String auditUserAccountName;

    /**
     * 该职工是否能按期完成考核目标
     */
        
        @ExcelField(value ="该职工是否能按期完成考核目标")
    private String ifDone;

    /**
     * 整改建议
     */
        
        @ExcelField(value ="整改建议")
    private String suggestion;

    /**
     * 入职年度
     */
        
        @ExcelField(value ="考核年度")
    private String year;

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



    public static final String ID ="id" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String FENZU ="fenzu" ;

    public static final String AUDIT_USER_ACCOUNT ="audit_user_account" ;

    public static final String AUDIT_USER_ACCOUNT_NAME ="audit_user_account_name" ;

    public static final String IF_DONE ="if_done" ;

    public static final String SUGGESTION ="suggestion" ;

    public static final String YEAR ="year" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }