package cc.mrbird.febs.doctor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class YearMiddleAudit implements Serializable {
    @TableField("NAME")
    private String name;
    @TableField("CODE")
    private String code;
    @TableField("LOGIN_NAME")
    private String loginName;
    @TableField("START_DATE")
    private Date startDate;
    @TableField("TXRQ")
    private Date txrq;
    @TableField("ZCYJXM")
    private String zcyjxm;
    @TableField("GZXJ")
    private String gzxj;
    @TableField("CGGS")
    private String cggs;

}
