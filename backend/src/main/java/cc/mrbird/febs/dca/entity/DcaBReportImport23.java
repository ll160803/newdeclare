package cc.mrbird.febs.dca.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

@Excel("二三级")
@Data
public class DcaBReportImport23 {

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
     * 科室分类
     */
    @ExcelField(value ="科室分类" )
    private String kslb;

    /**
     * 科室
     */
    @ExcelField(value ="科室" )
    private String ks;


    /**
     * 申报三级时是否使用医疗条件
     */
    @ExcelField(value ="申报三级时是否使用医疗条件" )
    private String ifsyyltj;

    /**
     * 是否符合必备条件
     */
    @ExcelField(value ="是否符合必备条件" )
    private String iffuhebibei;


    /**
     * 是否必须使用医疗条件
     */
    @ExcelField(value ="是否必须使用医疗条件" )
    private String ifbxyltj;

    /**
     * 满足学术条件情况
     */
    @ExcelField(value ="满足学术条件情况(人事处审核)" )
    private String mzsstjqkAudit;

    @ExcelField(value ="教学评分" )
    private String jxpfdj;

    /**
     * 近三年核心人力资源评分
     */
    @ExcelField(value ="近三年核心人力资源评分均值" )
    private String j3nhxrlzypf;

    /**
     * 近三年医疗综合评分
     */
    @ExcelField(value ="近三年医疗综合评分" )
    private String j3ylzhpf;

    /**
     * 近三年手术总台次
     */
    @ExcelField(value ="近三年手术总台次" )
    private String j3nssztc;

    /**
     * 近三年收治住院病人总数
     */
    @ExcelField(value ="近三年收治住院病人总数" )
    private String j3nzyszbrsl;

    /**
     * 近三年门诊收治病人总数
     */
    @ExcelField(value ="近三年门诊收治病人总数" )
    private String j3nmzszbrsl;


    /**
     * 负责开展的新技术新业务
     */
    @ExcelField(value ="负责开展的新技术新业务" )
    private String xjsxyw;

    /**
     * 负责的新技术新业务获奖情况
     */
    @ExcelField(value ="负责的新技术新业务获奖情况" )
    private String xjsxywprize;

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
     * 备注
     */
    @ExcelField(value ="备注" )
    private String note;

    /**
     * 排序4
     */
    @ExcelField(value ="备注2" )
    private String paixu4;
}
