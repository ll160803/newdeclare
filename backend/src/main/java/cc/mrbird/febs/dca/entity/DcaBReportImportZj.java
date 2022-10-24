package cc.mrbird.febs.dca.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

@Excel("中初级")
@Data
public class DcaBReportImportZj {

    /**
     * 起止年度
     */
    @ExcelField(value ="申请年度" )
    private String year;


    /**
     * 姓名
     */
    @ExcelField(value ="姓名" )
    private String userAccountName;

    /**
     * 人事编号
     */
    @ExcelField(value ="人事编号" )
    private String userAccount;

    /**
     * 申报等级
     */
    @ExcelField(value ="申报等级" )
    private String gwdj;

    /**
     * 申报职称
     */
    @ExcelField(value ="申报职称" )
    private String npPositionName;

    /**
     * 确认顺序号
     */
    @ExcelField(value ="确认顺序号" )
    private Integer confirmIndex;


    /**
     * 评审分组
     */
    @ExcelField(value ="评审分组" )
    private String pingshenfenzu;


    /**
     * 中专毕业时间
     */
    @ExcelField(value ="中专毕业时间" )
    private String zzbysj;

    /**
     * 大专毕业时间
     */
    @ExcelField(value ="大专毕业时间" )
    private String dzbysj;

    /**
     * 本科毕业时间
     */
    @ExcelField(value ="本科毕业时间" )
    private String bkbysj;

    /**
     * 硕士毕业时间
     */
    @ExcelField(value ="硕士毕业时间" )
    private String ssbysj;

    /**
     * 博士毕业时间
     */
    @ExcelField(value ="博士毕业时间" )
    private String bsbysj;

    /**
     * 材料审核结果
     */
    @ExcelField(value ="材料审核结果" )
    private String clshjg;
    /**
     * 退审原因
     */
    @ExcelField(value ="退审原因" )
    private String ntyy;


    /**
     * 申报类型
     */
    @ExcelField(value ="申报类型" )
    private String sblx;

    @ExcelField(value ="科室排序（护理）" )
    private String kspaixu;



    /**
     * 排序5
     */
    @ExcelField(value ="科室排序（其他）" )
    private String paixu5;
    /**
     * 备注
     */
    @ExcelField(value ="备注" )
    private String note;

}
