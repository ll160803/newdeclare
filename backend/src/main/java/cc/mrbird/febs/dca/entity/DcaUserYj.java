package cc.mrbird.febs.dca.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 学术业绩用户表
 * </p>
 *
 * @author viki
 * @since 2021-09-23
 */

@Excel("dca_user_yj")
@Data
@Accessors(chain = true)
public class DcaUserYj implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                    private Long id;

    /**
     * 学术业绩id
     */
            @ExcelField(value ="学术业绩id")
    private Long yjId;

    /**
     * 用户id
     */
            @ExcelField(value ="用户id")
    private String userId;

    /**
     * 申报年度
     */
    @ExcelField(value ="申报年度")
    private String dcaYear;



    public static final String ID ="id" ;

    public static final String YJ_ID ="yj_id" ;

    public static final String USER_ID ="user_id" ;

        }