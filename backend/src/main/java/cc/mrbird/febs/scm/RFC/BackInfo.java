package cc.mrbird.febs.scm.RFC;

/**
 * IDEA2019
 *
 * @Auther: hsc
 * @Date: 2021/06/11/10:11
 * @Description:
 */
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

@Data
@Excel("数据表")
public class BackInfo {
    @ExcelField(value ="序号")
    public String SERNO;
    @ExcelField(value ="科室")
    public String KS;
    @ExcelField(value ="姓名")
    public String NACHN;
    @ExcelField(value ="性别")
    public String GESCH;
    @ExcelField(value ="人员性质")
    public String RYXZ;
}
