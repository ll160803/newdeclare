package cc.mrbird.febs.doctor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class YearMonthAudit  implements Serializable {
    @TableField("NAME")
    private String name;
    @TableField("CODE")
    private String code;
    @TableField("LOGIN_NAME")
    private String loginName;
    @TableField("START_DATE")
    private String startDate;
    @TableField("KHYF")
    private String khyf;

    @TableField("KHNY")
    private String khny;
    @TableField("SXZZKHJG")
    private String sxzzkhjg;
    @TableField("DSKHJG")
    private String dskhjg;
    @TableField("SXZZBX")
    private String sxzzbx;
    @TableField("KQZK")
    private String kqzk;
    @TableField("GZTD")
    private String gztd;
    @TableField("GZJZ")
    private String gzjz;
    @TableField("CGGS")
    private String cggs;


}
