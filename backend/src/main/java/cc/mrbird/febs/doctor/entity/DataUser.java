package cc.mrbird.febs.doctor.entity;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@Data
public class DataUser {
    private DcaBDocUser docUser;
    private List<DcaBDocAuditfive> dcaBDocAuditfives;
    private List<DcaBDocAuditfivemonth> dcaBDocAuditfivemonths;
    private List<DcaBDocAuditfiveother> dcaBDocAuditfiveotherList;
    private List<DcaBDocAuditfivemiddle> dcaBDocAuditfivemiddles;
    private List<DcaBDocEducationexperice> dcaBDocEducationexpericeList;
    private List<DcaBDocParttimejob> jobList;
    private List<DcaBDocPrizeorpunish> dcaBDocPrizeorpunishList;
    private List<DcaBDocExportcountry> dcaBDocExportcountryList;
    private List<DcaBDocAchievement> achievementList;
    private List<DcaBDocSciencepublish> sciencepublishList;
    private List<DcaBDocSciencesearch> docSciencesearchList;
    private List<DcaBDocScientificprize> dcaBDocScientificprizes;
    private List<DcaBDocPatent> patentList;
    private List<DcaBDocPublicarticle> docPublicarticles;

    private long wenzhangshuliang;
    private String zuigaoyinzi;
    private long keyanxiangmugeshu;
    private long keyanhuojianggeshu;
    private long shenqingzhuanligeshu;
    private long chubanzhuzuogeshu;
    private double chuzhanpingfen;
    private double muqianpingfen;

}
