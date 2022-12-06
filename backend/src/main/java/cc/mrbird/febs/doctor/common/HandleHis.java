package cc.mrbird.febs.doctor.common;


import cc.mrbird.febs.common.utils.OracleDB;
import cc.mrbird.febs.doctor.entity.*;
import cc.mrbird.febs.doctor.service.IDcaBDocAuditfiveService;
import cc.mrbird.febs.doctor.service.impl.DcaBDocAuditfiveServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class HandleHis {

    public static List<DcaBDocAuditfive> InsertYearAudit() {
        List<DcaBDocAuditfive> dcaBDocAuditfiveList = new ArrayList<>();
        List<YearAudit> auditList = new ArrayList<>();
        OracleDB<YearAudit> oracleDB = new OracleDB<>();
        YearAudit yearAudit = new YearAudit();
        try {
            auditList = oracleDB.excuteSqlRS(yearAudit, "select *  from whuh.ndkh");
            log.info(auditList.toString());
            for (YearAudit audit : auditList
            ) {
                DcaBDocAuditfive auditfive = new DcaBDocAuditfive();
                auditfive.setKhjg(audit.getDSKHJG());
                auditfive.setUserAccount(audit.getCODE());
                auditfive.setUserAccountName(audit.getNAME());
                auditfive.setYear(audit.getKHND());
                auditfive.setKhjg(audit.getDSKHJG());
                auditfive.setZzbx(audit.getQMZJ());
                auditfive.setGrkyjz(audit.getCGZJ());
                auditfive.setXndkykt(audit.getKTJH());
                auditfive.setState(3);
                dcaBDocAuditfiveList.add(auditfive);
            }
        } catch (Exception ex) {
            log.error("年度考核:" + ex.getMessage());
        }
        return dcaBDocAuditfiveList;
    }

    public static List<DcaBDocAuditfivemiddle> InsertChuZhanAudit() {
        List<DcaBDocAuditfivemiddle> dcaBDocAuditfiveList = new ArrayList<>();
        List<YearChuZhan> auditList = new ArrayList<>();
        OracleDB<YearChuZhan> oracleDB = new OracleDB<>();
        YearChuZhan yearAudit = new YearChuZhan();
        try {
            auditList = oracleDB.excuteSqlRS(yearAudit, "select *  from whuh.czkh");
            log.info(auditList.toString());
            for (YearChuZhan audit : auditList
            ) {
                DcaBDocAuditfivemiddle auditfive = new DcaBDocAuditfivemiddle();

                auditfive.setUserAccount(audit.getCode());
                auditfive.setUserAccountName(audit.getName());
                auditfive.setKhDate(audit.getStartDate());

                auditfive.setCzkhzj(audit.getKhzj());
                auditfive.setState(3);
                dcaBDocAuditfiveList.add(auditfive);
            }
        } catch (Exception ex) {
            log.error("出战考核:" + ex.getMessage());
        }
        return dcaBDocAuditfiveList;
    }

    public static List<DcaBDocAuditfivemonth> InsertMonthAudit() {
        List<DcaBDocAuditfivemonth> dcaBDocAuditfiveList = new ArrayList<>();
        List<YearMonthAudit> auditList = new ArrayList<>();
        OracleDB<YearMonthAudit> oracleDB = new OracleDB<>();
        YearMonthAudit yearAudit = new YearMonthAudit();
        try {
            auditList = oracleDB.excuteSqlRS(yearAudit, "select *  from whuh.ydkh");
            log.info(auditList.toString());
            for (YearMonthAudit audit : auditList
            ) {
                DcaBDocAuditfivemonth auditfive = new DcaBDocAuditfivemonth();

                auditfive.setUserAccount(audit.getCode());
                auditfive.setUserAccountName(audit.getName());


                auditfive.setCggs(audit.getCggs());

                auditfive.setSxzzbx(audit.getSxzzbx());
                auditfive.setKqzk(audit.getKqzk());
                auditfive.setGztd(audit.getGztd());
                auditfive.setGzjz(audit.getGzjz());
                auditfive.setYear(audit.getKhny());
                auditfive.setKhjg(audit.getDskhjg());

                auditfive.setState(3);
                dcaBDocAuditfiveList.add(auditfive);
            }
        } catch (Exception ex) {
            log.error("月度考核:" + ex.getMessage());
        }
        return dcaBDocAuditfiveList;
    }

    public static List<DcaBDocAuditfiveother> InsertMiddleAudit() {
        List<DcaBDocAuditfiveother> dcaBDocAuditfiveList = new ArrayList<>();
        List<YearMiddleAudit> auditList = new ArrayList<>();
        OracleDB<YearMiddleAudit> oracleDB = new OracleDB<>();
        YearMiddleAudit yearAudit = new YearMiddleAudit();
        try {
            auditList = oracleDB.excuteSqlRS(yearAudit, "select *  from whuh.zqkh");
            log.info(auditList.toString());
            for (YearMiddleAudit audit : auditList
            ) {
                DcaBDocAuditfiveother auditfive = new DcaBDocAuditfiveother();

                auditfive.setUserAccount(audit.getCode());
                auditfive.setUserAccountName(audit.getName());


                auditfive.setCggs(audit.getCggs());

                auditfive.setZcyjxmyj(audit.getZcyjxm());
                auditfive.setBshbrzqxj(audit.getGzxj());
                auditfive.setTxDate(audit.getTxrq());
                auditfive.setState(3);
                dcaBDocAuditfiveList.add(auditfive);
            }
        } catch (Exception ex) {
            log.error("中期考核:" + ex.getMessage());
        }
        return dcaBDocAuditfiveList;
    }
}
