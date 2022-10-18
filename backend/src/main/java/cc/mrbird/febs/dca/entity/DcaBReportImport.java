package cc.mrbird.febs.dca.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

@Excel("高级")
@Data
public class DcaBReportImport {

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
     * 完成后是否双报
     */
    @ExcelField(value ="审核后是否双报" )
    private String wchshuangbao;

    /**
     * 科室分类
     */
    @ExcelField(value ="科室分类" )
    private String kslb;

    /**
     * 是否起带头或骨干作用
     */
    @ExcelField(value ="是否起带头或骨干作用" )
    private String ifdaitou;


    /**
     * 教学科研项目或获奖情况是否符合
     */
    @ExcelField(value ="教学科研项目或获奖情况是否符合" )
    private String iffuhekeyan;

    /**
     * 第一作者论文情况是否符合
     */
    @ExcelField(value ="第一作者论文情况是否符合" )
    private String iffuhediyi;


    /**
     * 是否符合必备条件
     */
    @ExcelField(value ="是否符合必备条件" )
    private String iffuhebibei;

    /**
     * 抗疫一线人员情况
     */
    @ExcelField(value ="抗疫一线人员情况" )
    private String yxryqk;

    /**
     * 是否构成选择条件
     */
    @ExcelField(value ="是否构成选择条件" )
    private String ifgoucheng;

    /**
     * 教学质量奖与成果奖名称
     */
    @ExcelField(value ="教学质量奖与成果奖名称" )
    private String schoolprizeName;

    /**
     * 等级
     */
    @ExcelField(value ="教学质量奖与成果奖等级" )
    private String schoolprizeDengji;

    /**
     * 排名
     */
    @ExcelField(value ="教学质量奖与成果奖排名" )
    private String schoolprizeRanknum;

    /**
     * 时间
     */
    @ExcelField(value ="教学质量奖与成果奖时间" )
    private String schoolprizeDate;

    /**
     * 教学竞赛获奖奖项级别
     */
    @ExcelField(value ="教学竞赛获奖奖项级别" )
    private String youngName;

    /**
     * 等级
     */
    @ExcelField(value ="教学竞赛获奖等级" )
    private String youngDengji;

    /**
     * 排名
     */
    @ExcelField(value ="教学竞赛获奖排名" )
    private String youngRanknum;

    /**
     * 时间
     */
    @ExcelField(value ="教学竞赛获奖时间" )
    private String youngDate;

    /**
     * 是否有校级奖励
     */
    @ExcelField(value ="是否有校级及以上教学竞赛奖励" )
    private String ifxjjl;

    /**
     * 申报类型
     */
    @ExcelField(value ="申报类型" )
    private String sblx;

    /**
     * 达到选择条件一之第几条
     */
    @ExcelField(value ="达到选择条件一之第几条" )
    private String choosepos;

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
     * 科室排名
     */
    @ExcelField(value ="科室排名" )
    private String ksrank;

    @ExcelField(value ="任现职以来出国时长（月）" )
    private String chuguonianyue;

    /**
     * 援助情况
     */
    @ExcelField(value ="援疆援藏援非援滇" )
    private String help;

    /**
     * 其他指令性支援情况
     */
    @ExcelField(value ="其他指令性支援情况" )
    private String qtzlxzy;


    @ExcelField(value ="支援类型" )
    private String zhiyuanchuguo;

    /**
     * 援助时长
     */
    @ExcelField(value ="援助时长（月）" )
    private String helpmonth;

    /**
     * 备注
     */
    @ExcelField(value ="备注" )
    private String note;

}
