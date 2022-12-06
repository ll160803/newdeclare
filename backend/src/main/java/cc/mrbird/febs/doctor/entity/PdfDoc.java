package cc.mrbird.febs.doctor.entity;

import cc.mrbird.febs.doctor.service.*;
import cc.mrbird.febs.model.PdfStyle;
import cc.mrbird.febs.model.PdfValue;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.List;

@Component
@Log4j2
public class PdfDoc {
    BaseColor black = BaseColor.BLACK;
    BaseColor red = BaseColor.RED;
    BaseColor blue = BaseColor.BLUE;
    private int bold = Font.BOLD; // 粗体
    private int normal = Font.NORMAL; // 正常字体
    private int italic = Font.ITALIC; // 斜体
    private int boldItalic = Font.BOLDITALIC; // 粗斜体
    private float setting = 100; // 首行缩进参数



   
    public void writePdf2021_1(String userAccount, String fileName,DataUser dataUser) throws Exception {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        FileOutputStream out = new FileOutputStream(fileName);
        PdfWriter writer = PdfWriter.getInstance(document, out);

        final String projectPath = System.getProperty("user.dir");
        String fontpath = projectPath + "\\font\\";
        //region 关闭写入
        document.open(); // 文档里写入
        //  BaseFont baseFontChinese = BaseFont.createFont("宋体", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //  BaseFont baseFontChinese = BaseFont.createFont("D:/font/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFontChinese, 11, normal, black);
        Font fontgoal = new Font(baseFontChinese, 9, normal, black);

        int numColumns = 24;
        int totalWidth = 520;
        int[] setWids = new int[numColumns];
        PdfPTable table = null;
        Font font2_bold = new Font(baseFontChinese, 11, bold, black);
        Font fontCover1 = new Font(baseFontChinese, 15, normal, black);
        document.newPage();
        numColumns = 24;
        table = new PdfPTable(numColumns);
        setWids = new int[numColumns];

        //table总Width宽度
        table.setTotalWidth(totalWidth);
        //设置总Width宽度 生效
        table.setLockedWidth(true);

        //列布局
        for (int i = 0; i < numColumns; i++) {
            setWids[i] = 1;
        }
        table.setWidths(setWids);


        DcaBDocUser user= dataUser.getDocUser();
log.info(user.getUserAccountName());
        table.addCell(GeneratePdfCell("发薪号：", 4, font));
        table.addCell(GeneratePdfCell(user.getUserAccount(), 4, font));
        table.addCell(GeneratePdfCell("姓名：", 4, font));
        table.addCell(GeneratePdfCell(user.getUserAccountName(), 4, font));
        table.addCell(GeneratePdfCell("性别：", 4, font));
        table.addCell(GeneratePdfCell(user.getSexName(), 4, font));


        table.addCell(GeneratePdfCell("出生日期：", 4, font));
        table.addCell(GeneratePdfCell(DateStr(user.getBirthday(), "yyyy-MM-dd"), 4, font));
        table.addCell(GeneratePdfCell("政治面貌：", 4, font));
        table.addCell(GeneratePdfCell(user.getPoliticalStatus(), 4, font));
        table.addCell(GeneratePdfCell("民族：", 4, font));
        table.addCell(GeneratePdfCell(user.getNation(), 4, font));

        table.addCell(GeneratePdfCell("籍贯：", 4, font));
        table.addCell(GeneratePdfCell(user.getJiguan(), 4, font));
        table.addCell(GeneratePdfCell("所在学科：", 4, font));
        table.addCell(GeneratePdfCell(user.getDeptDesc(), 4, font));
        table.addCell(GeneratePdfCell("合作导师：", 4, font));
        table.addCell(GeneratePdfCell(user.getHezuodaoshi(), 4, font));

        table.addCell(GeneratePdfCell("博士毕业学校：", 4, font));
        table.addCell(GeneratePdfCell(user.getBiyexuexiao(), 4, font));
        table.addCell(GeneratePdfCell("博士专业：", 4, font));
        table.addCell(GeneratePdfCell(user.getZhuanye(), 4, font));
        table.addCell(GeneratePdfCell("博士导师：", 4, font));
        table.addCell(GeneratePdfCell(user.getBoshidaoshi(), 4, font));

        table.addCell(GeneratePdfCell("是否预留院博后：", 4, font));
        table.addCell(GeneratePdfCell(user.getIsYlybh(), 4, font));
        table.addCell(GeneratePdfCell("招收类型：", 4, font));
        table.addCell(GeneratePdfCell(user.getZslx(), 4, font));
        table.addCell(GeneratePdfCell("流动站名称：", 4, font));
        table.addCell(GeneratePdfCell(user.getLdz(), 4, font));


        table.addCell(GeneratePdfCell("是否在职博后：", 4, font));
        table.addCell(GeneratePdfCell(user.getIsZzbh(), 4, font));
        table.addCell(GeneratePdfCell("来院工作日期：", 4, font));
        table.addCell(GeneratePdfCell(DateStr(user.getSchoolDate(), "yyyy-MM-dd"), 4, font));
        table.addCell(GeneratePdfCell("进站日期：", 4, font));
        table.addCell(GeneratePdfCell(DateStr(user.getInDate(), "yyyy-MM-dd"), 4, font));

        table.addCell(GeneratePdfCell("聘期：", 4, font));
        table.addCell(GeneratePdfCell(user.getPinqi(), 4, font));
        table.addCell(GeneratePdfCell("手机号：", 4, font));
        table.addCell(GeneratePdfCell(user.getTelephone(), 4, font));
        table.addCell(GeneratePdfCell("", 4, font));
        table.addCell(GeneratePdfCell("", 4, font));

        table.addCell(GeneratePdfCell("出站日期：", 4, font));
        table.addCell(GeneratePdfCell(DateStr(user.getCzDate(), "yyyy-MM-dd"), 4, font));
        table.addCell(GeneratePdfCell("身份证号：", 4, font));
        table.addCell(GeneratePdfCell(user.getIdCard(), 12, font));

        table.addCell(GeneratePdfCell("站内工作目标：", 4, font));
        table.addCell(GeneratePdfCell(user.getGoal(), 20, font));


        table.addCell(GeneratePdfCell("截止目前成果情况：", 4, font));

        String jzmqcgqk=String.format("文章%1s篇，最高影响因子%2s，科研项目%3s个，科研获奖%4s个，申请专利%5s个，出版著作%6s个",
               dataUser.getWenzhangshuliang(),dataUser.getZuigaoyinzi(),dataUser.getKeyanxiangmugeshu(),dataUser.getKeyanhuojianggeshu(),
               dataUser.getShenqingzhuanligeshu(),dataUser.getChubanzhuzuogeshu() );
        table.addCell(GeneratePdfCell(jzmqcgqk, 20, font));

        table.addCell(GeneratePdfCell("出站评分：", 4, font));
        table.addCell(GeneratePdfCell(String.valueOf(dataUser.getChuzhanpingfen()), 20, font));

        table.addCell(GeneratePdfCell("目前评分：", 4, font));
        table.addCell(GeneratePdfCell(String.valueOf(dataUser.getMuqianpingfen()), 20, font));

        table.addCell(GeneratePdfCell("年度考核：", 24, font2_bold));


        log.info("333333");
        List<DcaBDocAuditfive> list = dataUser.getDcaBDocAuditfives();

        if (list.size() > 0) {
            for (DcaBDocAuditfive five : list
            ) {
                table.addCell(GeneratePdfCell("考核年度:", 4, font));
                table.addCell(GeneratePdfCell(five.getYear(), 8, font));
                table.addCell(GeneratePdfCell("考核结果:", 4, font));
                table.addCell(GeneratePdfCell(five.getKhjg(), 8, font));

                table.addCell(GeneratePdfCell("政治表现、职业道德、廉洁自律、业务能力工作态度总结", 24, font));
                table.addCell(GeneratePdfCell(five.getZzbx(), 24, font));

                table.addCell(GeneratePdfCell("个人科研进展较为详细的成果总结", 24, font));
                table.addCell(GeneratePdfCell(five.getGrkyjz(), 24, font));

                table.addCell(GeneratePdfCell("下年度科研课题较为详细计划", 24, font));
                table.addCell(GeneratePdfCell(five.getXndkykt(), 24, font));
            }
        } else {
            table.addCell(GeneratePdfCell("考核年度：", 4, font));
            table.addCell(GeneratePdfCell(" ", 8, font));
            table.addCell(GeneratePdfCell("考核结果：", 4, font));
            table.addCell(GeneratePdfCell(" ", 8, font));

            table.addCell(GeneratePdfCell("政治表现、职业道德、廉洁自律、业务能力工作态度总结", 24, font));
            table.addCell(GeneratePdfCell(" ", 24, font));

            table.addCell(GeneratePdfCell("个人科研进展较为详细的成果总结", 24, font));
            table.addCell(GeneratePdfCell(" ", 24, font));

            table.addCell(GeneratePdfCell("下年度科研课题较为详细计划", 24, font));
            table.addCell(GeneratePdfCell(" ", 24, font));
        }


        table.addCell(GeneratePdfCell("月度考核：", 24, font2_bold));

        List<DcaBDocAuditfivemonth> dcaBDocAuditfivemonths = dataUser.getDcaBDocAuditfivemonths();
        if (dcaBDocAuditfivemonths.size() > 0) {
            for (DcaBDocAuditfivemonth month : dcaBDocAuditfivemonths
            ) {
                table.addCell(GeneratePdfCell("考核年月：", 4, font));
                table.addCell(GeneratePdfCell(month.getYear(), 8, font));
                table.addCell(GeneratePdfCell("考核结果：", 4, font));
                table.addCell(GeneratePdfCell(month.getKhjg(), 8, font));
            }
        } else {
            table.addCell(GeneratePdfCell("考核年月：", 4, font));
            table.addCell(GeneratePdfCell(" ", 8, font));
            table.addCell(GeneratePdfCell("考核结果：", 4, font));
            table.addCell(GeneratePdfCell(" ", 8, font));
        }


        List<DcaBDocAuditfiveother> dcaBDocAuditfiveotherList = dataUser.getDcaBDocAuditfiveotherList();

        log.info("5555555555");
        table.addCell(GeneratePdfCell("中期考核", 24, font2_bold));
        if (dcaBDocAuditfiveotherList.size() > 0) {
            table.addCell(GeneratePdfCell("成果概述", 4, font));
            table.addCell(GeneratePdfCell(dcaBDocAuditfiveotherList.get(0).getCggs(), 20, font));
            table.addCell(GeneratePdfCell("主持研究的项目名称及研究计划", 4, font));
            table.addCell(GeneratePdfCell(dcaBDocAuditfiveotherList.get(0).getZcyjxmyj(), 20, font));
            table.addCell(GeneratePdfCell("博士后本人中期工作小结", 4, font));
            table.addCell(GeneratePdfCell(dcaBDocAuditfiveotherList.get(0).getBshbrzqxj(), 20, font));
        } else {
            table.addCell(GeneratePdfCell("成果概述", 4, font));
            table.addCell(GeneratePdfCell("", 20, font));
            table.addCell(GeneratePdfCell("主持研究的项目名称及研究计划", 4, font));
            table.addCell(GeneratePdfCell("", 20, font));
            table.addCell(GeneratePdfCell("博士后本人中期工作小结", 4, font));
            table.addCell(GeneratePdfCell("", 20, font));
        }
               List<DcaBDocAuditfivemiddle> dcaBDocAuditfivemiddles = dataUser.getDcaBDocAuditfivemiddles();

        table.addCell(GeneratePdfCell("出站考核", 24, font2_bold));
        if (dcaBDocAuditfivemiddles.size() > 0) {
            table.addCell(GeneratePdfCell(dcaBDocAuditfivemiddles.get(0).getCzkhzj(), 24, font));
        } else {
            table.addCell(GeneratePdfCell("", 24, font));
        }
        List<DcaBDocEducationexperice> dcaBDocEducationexpericeList = dataUser.getDcaBDocEducationexpericeList();

        table.addCell(GeneratePdfCell("主要学习及工作经历", 24, font2_bold));
        table.addCell(GeneratePdfCell("开始日期", 4, font));
        table.addCell(GeneratePdfCell("结束日期", 4, font));
        table.addCell(GeneratePdfCell("何地", 4, font));
        table.addCell(GeneratePdfCell("学校", 4, font));
        table.addCell(GeneratePdfCell("何单位职位或学位/学历", 4, font));
        table.addCell(GeneratePdfCell("证明人", 4, font));

        for (DcaBDocEducationexperice edu : dcaBDocEducationexpericeList
        ) {

            table.addCell(GeneratePdfCell(DateStr(edu.getExpStartTime(), "yyyy-MM-dd"), 4, font));
            table.addCell(GeneratePdfCell(DateStr(edu.getExpEndTime(), "yyyy-MM-dd"), 4, font));
            table.addCell(GeneratePdfCell(edu.getExpAddress(), 4, font));
            table.addCell(GeneratePdfCell(edu.getExpSchool(), 4, font));
            table.addCell(GeneratePdfCell(edu.getExpPosition(), 4, font));
            table.addCell(GeneratePdfCell(edu.getExpCertifier(), 4, font));
        }

        log.info("666666666666");

        List<DcaBDocParttimejob> jobList = dataUser.getJobList();

        table.addCell(GeneratePdfCell("社会兼职", 24, font2_bold));
        table.addCell(GeneratePdfCell("开始日期", 4, font));
        table.addCell(GeneratePdfCell("结束日期", 4, font));
        table.addCell(GeneratePdfCell("所在学会", 8, font));
        table.addCell(GeneratePdfCell("职务", 8, font));
        if (jobList.size() > 0) {
            for (DcaBDocParttimejob job : jobList
            ) {
                table.addCell(GeneratePdfCell(DateStr(job.getJzStartTime(), "yyyy-MM-dd"), 4, font));
                table.addCell(GeneratePdfCell(DateStr(job.getJzEndTime(), "yyyy-MM-dd"), 4, font));
                table.addCell(GeneratePdfCell(job.getJzContent(), 8, font));
                table.addCell(GeneratePdfCell(job.getJzZw(), 8, font));
            }
        } else {

            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 8, font));
            table.addCell(GeneratePdfCell("", 8, font));
        }


        table.addCell(GeneratePdfCell("奖励与处分", 24, font2_bold));
        table.addCell(GeneratePdfCell("类型", 4, font));
        table.addCell(GeneratePdfCell("时间", 4, font));
        table.addCell(GeneratePdfCell("名称", 8, font));
        table.addCell(GeneratePdfCell("部门", 4, font));
        table.addCell(GeneratePdfCell("类别", 4, font));

        List<DcaBDocPrizeorpunish> dcaBDocPrizeorpunishList = dataUser.getDcaBDocPrizeorpunishList();
        if (dcaBDocPrizeorpunishList.size() > 0) {
            for (DcaBDocPrizeorpunish pp : dcaBDocPrizeorpunishList
            ) {
                table.addCell(GeneratePdfCell(pp.getPpCategory(), 4, font));
                table.addCell(GeneratePdfCell(DateStr(pp.getPpStartTime(), "yyyy-MM-dd"), 4, font));
                table.addCell(GeneratePdfCell(pp.getPpContent(), 8, font));
                table.addCell(GeneratePdfCell(pp.getPpPartment(), 4, font));
                table.addCell(GeneratePdfCell(pp.getPpLb(), 4, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 8, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
        }

        log.info("8888888888888");
        table.addCell(GeneratePdfCell("出国情况", 24, font2_bold));
        table.addCell(GeneratePdfCell("出国日期", 4, font));
        table.addCell(GeneratePdfCell("回国日期", 4, font));
        table.addCell(GeneratePdfCell("留学国家", 4, font));
        table.addCell(GeneratePdfCell("留学单位", 8, font));
        table.addCell(GeneratePdfCell("派出渠道", 4, font));

        List<DcaBDocExportcountry> dcaBDocExportcountryList = dataUser.getDcaBDocExportcountryList();
        if (dcaBDocExportcountryList.size() > 0) {
            for (DcaBDocExportcountry country : dcaBDocExportcountryList
            ) {
                table.addCell(GeneratePdfCell(DateStr(country.getCgsj(), "yyyy-MM-dd"), 4, font));
                table.addCell(GeneratePdfCell(DateStr(country.getHgsj(), "yyyy-MM-dd"), 4, font));
                table.addCell(GeneratePdfCell(country.getLxgj(), 4, font));
                table.addCell(GeneratePdfCell(country.getLxdw(), 8, font));
                table.addCell(GeneratePdfCell(country.getQudao(), 4, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 8, font));
            table.addCell(GeneratePdfCell("", 4, font));
        }



        table.addCell(GeneratePdfCell("新技术新业务获批情况", 24, font2_bold));

        table.addCell(GeneratePdfCell("名称", 4, font));
        table.addCell(GeneratePdfCell("排名", 4, font));
        table.addCell(GeneratePdfCell("等级", 4, font));
        table.addCell(GeneratePdfCell("获得日期", 4, font));
        table.addCell(GeneratePdfCell("期限", 4, font));
        table.addCell(GeneratePdfCell("备注", 4, font));

        List<DcaBDocAchievement> achievementList = dataUser.getAchievementList();
        if (achievementList.size() > 0) {
            for (DcaBDocAchievement achievement : achievementList
            ) {
                table.addCell(GeneratePdfCell(achievement.getAchievementName(), 4, font));
                table.addCell(GeneratePdfCell(String.valueOf(achievement.getRankIndex()), 4, font));
                table.addCell(GeneratePdfCell(achievement.getAchievementGrade(), 4, font));
                table.addCell(GeneratePdfCell(DateStr(achievement.getAchievementDate(), "yyyy-MM-dd"), 4, font));
                table.addCell(GeneratePdfCell(achievement.getAchievementDefine(), 4, font));
                table.addCell(GeneratePdfCell(achievement.getAchievementContent(), 4, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
        }

        log.info("99999999999999999");
        table.addCell(GeneratePdfCell("文章情况", 24, font2_bold));
        table.addCell(GeneratePdfCell("论文名", 6, font));
        table.addCell(GeneratePdfCell("期刊名", 4, font));
        table.addCell(GeneratePdfCell("期刊号", 2, font));
        table.addCell(GeneratePdfCell("发表年月", 2, font));
        table.addCell(GeneratePdfCell("期刊级别", 1, font));
        table.addCell(GeneratePdfCell("影响因子", 1, font));
        table.addCell(GeneratePdfCell("第一或通讯作者", 4, font));
        table.addCell(GeneratePdfCell("共几人", 2, font));
        table.addCell(GeneratePdfCell("排第几", 2, font));

        List<DcaBDocSciencepublish> sciencepublishList = dataUser.getSciencepublishList();

        if (sciencepublishList.size() > 0) {
            for (DcaBDocSciencepublish sci : sciencepublishList
            ) {
                table.addCell(GeneratePdfCell(sci.getPaperName(), 6, font));
                table.addCell(GeneratePdfCell(sci.getJournalName(), 4, font));
                table.addCell(GeneratePdfCell(sci.getJournalCode(), 2, font));
                table.addCell(GeneratePdfCell(DateStr(sci.getPaperPublishdate(), "yyyy-MM-dd"), 2, font));
                table.addCell(GeneratePdfCell(sci.getQkjb(), 1, font));
                table.addCell(GeneratePdfCell(sci.getPaperCause(), 1, font));
                table.addCell(GeneratePdfCell(sci.getAuthorRank(), 4, font));
                table.addCell(GeneratePdfCell(String.valueOf(sci.getAuditTotalnum()), 2, font));
                table.addCell(GeneratePdfCell(sci.getDjzz(), 2, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 6, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 1, font));
            table.addCell(GeneratePdfCell("", 1, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
        }


        table.addCell(GeneratePdfCell("科研项目", 24, font2_bold));
        table.addCell(GeneratePdfCell("项目名称", 6, font));
        table.addCell(GeneratePdfCell("项目性质", 2, font));
        table.addCell(GeneratePdfCell("项目来源", 2, font));
        table.addCell(GeneratePdfCell("合同经费", 2, font));
        table.addCell(GeneratePdfCell("实到经费", 2, font));
        table.addCell(GeneratePdfCell("批准年月", 2, font));
        table.addCell(GeneratePdfCell("起始日期", 2, font));
        table.addCell(GeneratePdfCell("终止日期", 2, font));
        table.addCell(GeneratePdfCell("本人排名", 2, font));
        table.addCell(GeneratePdfCell("合作导师排名", 2, font));

        List<DcaBDocSciencesearch> docSciencesearchList = dataUser.getDocSciencesearchList();
        if (docSciencesearchList.size() > 0) {
            for (DcaBDocSciencesearch search : docSciencesearchList
            ) {
                table.addCell(GeneratePdfCell(search.getProjectName(), 6, font));
                table.addCell(GeneratePdfCell(search.getProjectType(), 2, font));
                table.addCell(GeneratePdfCell(search.getProjectSource(), 2, font));
                table.addCell(GeneratePdfCell(String.valueOf(search.getContractFund()), 2, font));
                table.addCell(GeneratePdfCell(String.valueOf(search.getRealFund()), 2, font));
                table.addCell(GeneratePdfCell(DateStr(search.getAuditDate2(), "yyyy-MM-dd"), 2, font));
                table.addCell(GeneratePdfCell(DateStr(search.getStartDate(), "yyyy-MM-dd"), 2, font));
                table.addCell(GeneratePdfCell(DateStr(search.getEndDate(), "yyyy-MM-dd"), 2, font));
                table.addCell(GeneratePdfCell(String.valueOf(search.getRankNum()), 2, font));
                table.addCell(GeneratePdfCell(String.valueOf(search.getDaoshiRanknum()), 2, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 6, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
        }

        table.addCell(GeneratePdfCell("科研获奖", 24, font2_bold));
        table.addCell(GeneratePdfCell("获奖名称", 6, font));
        table.addCell(GeneratePdfCell("奖项级别", 3, font));
        table.addCell(GeneratePdfCell("奖项等级", 3, font));
        table.addCell(GeneratePdfCell("授奖部门", 3, font));
        table.addCell(GeneratePdfCell("批准年月", 3, font));
        table.addCell(GeneratePdfCell("本人排名", 3, font));
        table.addCell(GeneratePdfCell("合作导师排名", 3, font));

        List<DcaBDocScientificprize> dcaBDocScientificprizes = dataUser.getDcaBDocScientificprizes();
        if (dcaBDocScientificprizes.size() > 0) {
            for (DcaBDocScientificprize prize : dcaBDocScientificprizes
            ) {
                table.addCell(GeneratePdfCell(prize.getSpProjectName(), 6, font));
                table.addCell(GeneratePdfCell(prize.getSrProjectGrade(), 3, font));
                table.addCell(GeneratePdfCell(prize.getSrProjectLevel(), 3, font));
                table.addCell(GeneratePdfCell(prize.getSrPrizeDept(), 3, font));
                table.addCell(GeneratePdfCell(DateStr(prize.getSrPrizeDate(), "yyyy-MM-dd"), 3, font));
                table.addCell(GeneratePdfCell(String.valueOf(prize.getSrPrizeRanknum()), 3, font));
                table.addCell(GeneratePdfCell(String.valueOf(prize.getDaoshiRanknum()), 3, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 6, font));
            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 3, font));
        }


        table.addCell(GeneratePdfCell("申请专利", 24, font2_bold));
        table.addCell(GeneratePdfCell("专利号", 4, font));
        table.addCell(GeneratePdfCell("专利名", 6, font));
        table.addCell(GeneratePdfCell("专利类别", 2, font));
        table.addCell(GeneratePdfCell("批准年月", 2, font));
        table.addCell(GeneratePdfCell("本人排名", 2, font));
        table.addCell(GeneratePdfCell("合作导师排名", 2, font));
        table.addCell(GeneratePdfCell("是否授权", 2, font));
        table.addCell(GeneratePdfCell("是否转让", 2, font));
        table.addCell(GeneratePdfCell("转让效益", 2, font));

        List<DcaBDocPatent> patentList = dataUser.getPatentList();
        if (patentList.size() > 0) {
            for (DcaBDocPatent patent : patentList
            ) {
                table.addCell(GeneratePdfCell(patent.getPatentCode(), 4, font));
                table.addCell(GeneratePdfCell(patent.getPatentName(), 6, font));
                table.addCell(GeneratePdfCell(patent.getPatentType(), 2, font));
                table.addCell(GeneratePdfCell(DateStr(patent.getPatentDate(), "yyyy-MM-dd"), 2, font));
                table.addCell(GeneratePdfCell(String.valueOf(patent.getPatentRanknum()), 2, font));
                table.addCell(GeneratePdfCell(String.valueOf(patent.getDaoshiRanknum()), 2, font));
                table.addCell(GeneratePdfCell(patent.getIsAuthority(), 2, font));
                table.addCell(GeneratePdfCell(patent.getIsZhuanrang(), 2, font));
                table.addCell(GeneratePdfCell(patent.getPatentGood(), 2, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 6, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
        }

        log.info("2142432423423");
        table.addCell(GeneratePdfCell("出版著作", 24, font2_bold));
        table.addCell(GeneratePdfCell("著作类型", 4, font));
        table.addCell(GeneratePdfCell("著作名称", 6, font));
        table.addCell(GeneratePdfCell("著者身份", 2, font));
        table.addCell(GeneratePdfCell("出版日期", 2, font));
        table.addCell(GeneratePdfCell("ISNB号", 2, font));
        table.addCell(GeneratePdfCell("出版社名称", 2, font));
        table.addCell(GeneratePdfCell("作为第一编写人编写章节名称", 3, font));
        table.addCell(GeneratePdfCell("作为第一编写人编写字数合计（单位：万字）", 3, font));

        List<DcaBDocPublicarticle> docPublicarticles = dataUser.getDocPublicarticles();
        if (docPublicarticles.size() > 0) {
            for (DcaBDocPublicarticle article : docPublicarticles
            ) {
                table.addCell(GeneratePdfCell(article.getZzlx(), 4, font));
                table.addCell(GeneratePdfCell(article.getZzmc(), 6, font));
                table.addCell(GeneratePdfCell(article.getZzsf(), 2, font));
                table.addCell(GeneratePdfCell(DateStr(article.getCbDate(), "yyyy-MM-dd"), 2, font));
                table.addCell(GeneratePdfCell(article.getBookNo(), 2, font));
                table.addCell(GeneratePdfCell(article.getCbsmc(), 2, font));
                table.addCell(GeneratePdfCell(article.getBxzjmc(), 3, font));
                table.addCell(GeneratePdfCell(article.getBxwzqzy(), 3, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 6, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 3, font));

        }

        document.add(table);
        out.flush();
        document.close();
        out.close();
    }

    private PdfPCell GeneratePdfCell(String cellValue, int colspan, Font font) {

        PdfPCell cell = new PdfPCell(new Phrase(handleEmptyString(cellValue), font));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //  cell.setFixedHeight(contentHeight30);
        cell.setColspan(colspan);
        cell.setPaddingTop(10f);
        cell.setPaddingBottom(10f);
        return cell;
    }


    private PdfValue generatePdfValue(PdfStyle style, String cellValue, int colspan) {
        PdfValue pdfValue = new PdfValue();
        pdfValue.setCellValue(cellValue);
        pdfValue.setColspan(colspan);
        PdfStyle sty = new PdfStyle();
        sty = ObjectUtil.clone(style);
        pdfValue.setPdfStyle(sty);
        return pdfValue;
    }

    private PdfValue generatePdfValue(PdfStyle style, String cellValue, int colspan, float fixedHeight, int rowspan) {
        PdfValue pdfValue = new PdfValue();
        pdfValue.setCellValue(cellValue);
        pdfValue.setColspan(colspan);
        pdfValue.setRowspan(rowspan);
        PdfStyle sty = new PdfStyle();
        sty = ObjectUtil.clone(style);
        sty.setFixedHeight(fixedHeight);
        pdfValue.setPdfStyle(sty);
        return pdfValue;
    }

    private PdfValue generatePdfValue(PdfStyle style, String cellValue, int colspan, float fixedHeight) {
        PdfValue pdfValue = new PdfValue();
        pdfValue.setCellValue(cellValue);
        pdfValue.setColspan(colspan);
        PdfStyle sty = new PdfStyle();
        sty = ObjectUtil.clone(style);
        sty.setFixedHeight(fixedHeight);
        pdfValue.setPdfStyle(sty);
        return pdfValue;
    }

    private PdfValue generatePdfValue(PdfStyle style, String cellValue, int colspan, float fixedHeight, int horizontalAlignment, int verticalAlignment) {
        PdfValue pdfValue = new PdfValue();
        pdfValue.setCellValue(cellValue);
        pdfValue.setColspan(colspan);
        PdfStyle sty = new PdfStyle();
        sty = ObjectUtil.clone(style);
        sty.setFixedHeight(fixedHeight);
        sty.setHorizontalAlignment(horizontalAlignment);
        if (verticalAlignment > 0) {
            sty.setVerticalAlignment(verticalAlignment);
        }
        pdfValue.setPdfStyle(sty);
        return pdfValue;
    }

    private PdfValue generatePdfValue(PdfStyle style, String cellValue, int colspan, float fixedHeight, Font font) {
        PdfValue pdfValue = new PdfValue();
        pdfValue.setCellValue(cellValue);
        pdfValue.setColspan(colspan);
        PdfStyle sty = new PdfStyle();
        sty = ObjectUtil.clone(style);
        sty.setFixedHeight(fixedHeight);
        sty.setFont(font);
        pdfValue.setPdfStyle(sty);
        return pdfValue;
    }

    private String zuozhe(String zuozhe) {
        String a = zuozhe.replace("[", "").replace("]", "").replace(",", "和").replace("\"", "");
        return a;
    }

    private String DateStr(Date date, String stringFormat) {
        if (date == null) return "";
        return DateUtil.format(date, stringFormat);
    }

    // 查询图片组装image
    private Image loadingPicture(String picUrl) throws BadElementException, IOException {
        File file = new File(picUrl);
        byte[] by = File2byte(file);
        Image image = Image.getInstance(by);
        image.scaleAbsolute(80, 100);// 调整图片大小(宽度 高度)
        return image;
    }

    private byte[] File2byte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    private String handleEmptyString(String value) {
        if (value == null) return "";
        if(value.equals("null")) return  "";
        return value;
    }


}
