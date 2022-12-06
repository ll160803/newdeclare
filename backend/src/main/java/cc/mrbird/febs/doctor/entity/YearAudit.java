package cc.mrbird.febs.doctor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class YearAudit implements Serializable {
    @TableField("NAME")
    private String NAME;
    @TableField("CODE")
    private String CODE;
    @TableField("KHND")
    private String KHND;
    @TableField("DSKHJG")
    private String DSKHJG;
    @TableField("KHXZKHJG")
    private String KHXZKHJG;
    @TableField("QMZJ")
    private String QMZJ;
    @TableField("CGZJ")
    private String CGZJ;
    @TableField("KTJH")
    private String KTJH;
}
