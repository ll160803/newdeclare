package cc.mrbird.febs.doctor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class YearChuZhan implements Serializable {
    @TableField("NAME")
    private String name;
    @TableField("CODE")
    private String code;
    @TableField("LOGIN_NAME")
    private String loginName;
    @TableField("START_DATE")
    private Date startDate;
    @TableField("KHZJ")
    private String khzj;


}
