package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.CustomApplyFirst;
import cc.mrbird.febs.dca.entity.DcaBReport;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.dca.entity.DcaBYoungprize;
import cc.mrbird.febs.dca.service.IDcaBAcademicService;
import cc.mrbird.febs.dca.service.IDcaBAchievementService;
import cc.mrbird.febs.dca.service.IDcaBMedicalaccidentService;
import cc.mrbird.febs.dca.service.IDcaBReportService;
import cc.mrbird.febs.dcacopy.entity.*;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyUserMapper;
import cc.mrbird.febs.dcacopy.service.*;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyUserServiceImpl extends ServiceImpl<DcaBCopyUserMapper, DcaBCopyUser> implements IDcaBCopyUserService {

    @Autowired
    private IDcaBCopyYoungprizeService iDcaBCopyYoungprize;
    @Autowired
    private IDcaBCopyApplyjobService iDcaBCopyApplyjobService;
    @Autowired
    private IDcaBCopyAttachfileService iDcaBCopyAttachfileService;
    @Autowired
    private IDcaBCopyAuditfiveService iDcaBCopyAuditfiveService;
    @Autowired
    private IDcaBCopyCourseclassService iDcaBCopyCourseclassService;
    @Autowired
    private IDcaBCopyEducationexpericeService iDcaBCopyEducationexpericeService;
    @Autowired
    private IDcaBCopyEmployService iDcaBCopyEmployService;
    @Autowired
    private IDcaBCopyExportcountryService iDcaBCopyExportcountryService;
    @Autowired
    private IDcaBCopyFivecommentService iDcaBCopyFivecommentService;
    @Autowired
    private IDcaBCopyGoalService iDcaBCopyGoalService;
    @Autowired
    private IDcaBCopyGraduateService iDcaBCopyGraduateService;
    @Autowired
    private IDcaBCopyInnovatebuildService iDcaBCopyInnovatebuildService;
    @Autowired
    private IDcaBCopyLastemployService iDcaBCopyLastemployService;
    @Autowired
    private IDcaBCopyOtherworkService iDcaBCopyOtherworkService;
    @Autowired
    private IDcaBCopyUndergraduateService iDcaBCopyUndergraduateService;
    @Autowired
    private IDcaBCopyUndergraduateprizeService iDcaBCopyUndergraduateprizeService;
    @Autowired
    private IDcaBCopyTurtorService iDcaBCopyTurtorService;
    @Autowired
    private IDcaBCopyTeachtalentService iDcaBCopyTeachtalentService;
    @Autowired
    private IDcaBCopyTeacherqualifyService iDcaBCopyTeacherqualifyService;
    @Autowired
    private IDcaBCopyTeacherprizeService iDcaBCopyTeacherprizeService;
    @Autowired
    private IDcaBCopyTalentService iDcaBCopyTalentService;
    @Autowired
    private IDcaBCopyScientificprizeService iDcaBCopyScientificprizeService;
    @Autowired
    private IDcaBCopySciencesearchService iDcaBCopySciencesearchService;
    @Autowired
    private IDcaBCopySciencepublishService iDcaBCopySciencepublishService;
    @Autowired
    private IDcaBCopySchoolprizeService iDcaBCopySchoolprizeService;
    @Autowired
    private IDcaBCopyPublicarticleService iDcaBCopyPublicarticleService;
    @Autowired
    private IDcaBCopyPrizeorpunishService iDcaBCopyPrizeorpunishService;
    @Autowired
    private IDcaBCopyPolitalshowService iDcaBCopyPolitalshowService;
    @Autowired
    private IDcaBCopyPersonalsummaryService iDcaBCopyPersonalsummaryService;
    @Autowired
    private IDcaBCopyPatentService iDcaBCopyPatentService;

    @Autowired
    private IDcaBCopyParttimejobService iDcaBCopyParttimejobService;

    @Autowired
    private IDcaBCopyUserService iDcaBCopyUserService;

    @Autowired
    private IDcaBCopyYoungprizeService iDcaBCopyYoungprizeService;


    @Autowired
    private IDcaBCopyAuditdynamicService iDcaBCopyAuditdynamicService;

    @Autowired
    private IDcaBCopyDoctorturtorService iDcaBCopyDoctorturtorService;

    @Autowired
    private IDcaBCopyMedicalaccidentService iDcaBCopyMedicalaccidentService;

    @Autowired
    private IDcaBCopyAcademicService iDcaBCopyAcademicService;

    @Autowired
    private IDcaBCopyAchievementService iDcaBCopyAchievementService;

    @Autowired
    private IDcaBReportService iDcaBReportService;

    @Autowired
    private IDcaBCopyQualificationService iDcaBCopyQualificationService;

    @Autowired
    private IDcaBCopyTeacheryjService iDcaBCopyTeacheryjService;

    @Autowired
    private IDcaBCopySureachievementService iDcaBCopySureachievementService;

    @Autowired
    private IDcaBCopySciachievementService iDcaBCopySciachievementService;



    @Override
    public IPage<DcaBCopyUser> findDcaBCopyUsers(QueryRequest request, DcaBCopyUser dcaBCopyUser) {
        try {
            LambdaQueryWrapper<DcaBCopyUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBCopyUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBCopyUser.getUserAccountName())) {
                queryWrapper.like(DcaBCopyUser::getUserAccountName, dcaBCopyUser.getUserAccountName());
            }
            if (StringUtils.isNotBlank(dcaBCopyUser.getUserAccount())) {
                queryWrapper.like(DcaBCopyUser::getUserAccount, dcaBCopyUser.getUserAccount());
            }
            if (StringUtils.isNotBlank(dcaBCopyUser.getDcaYear())) {
                queryWrapper.like(DcaBCopyUser::getDcaYear, dcaBCopyUser.getDcaYear());
            }

            Page<DcaBCopyUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaBCopyUser> findDcaBCopyUserList(QueryRequest request, DcaBCopyUser dcaBCopyUser) {
        try {
            Page<DcaBCopyUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBCopyUser(page, dcaBCopyUser);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBCopyUser(DcaBCopyUser dcaBCopyUser) {
        dcaBCopyUser.setId(UUID.randomUUID().toString());
        dcaBCopyUser.setCreateTime(new Date());
        dcaBCopyUser.setIsDeletemark(1);
        this.save(dcaBCopyUser);
    }

    @Override
    @Transactional
    public void updateDcaBCopyUser(DcaBCopyUser dcaBCopyUser) {
        dcaBCopyUser.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyUser(dcaBCopyUser);
    }

    @Override
    @Transactional
    public void deleteDcaBCopyUsers(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public List<DcaBCopyUser> getAll(String userAccount, String dcaYear,String gwDj) {
        LambdaQueryWrapper<DcaBCopyUser> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.eq(DcaBCopyUser::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
            queryWrapper.eq(DcaBCopyUser::getDcaYear, dcaYear);
        }
        if (StringUtils.isNotBlank(gwDj)) {
            queryWrapper.eq(DcaBCopyUser::getGwdj, gwDj);
        }
        return this.baseMapper.selectList(queryWrapper);
    }

    private String DateStr(Date date, String stringFormat) {
        if (date == null) return "";
        return DateUtil.format(date, stringFormat);
    }

    @Override
    @Transactional
    public CustomApplyFirst getPrintPdf(String userAccount, String dcaYear, String zc,String gwDj) {

        CustomApplyFirst customApplyFirst = new CustomApplyFirst();
        // List<DcaBCopyYoungprize> listDcaBCopyYoungprize =this.iDcaBCopyYoungprize.getAll(userAccount,dcaYear);

        List<DcaBReport> dcaBReportList =this.iDcaBReportService.getAll(userAccount,dcaYear,zc);
        List<DcaBCopyPatent> listDcaBCopyPatent = this.iDcaBCopyPatentService.getAll(userAccount, dcaYear,gwDj);
        listDcaBCopyPatent=listDcaBCopyPatent.stream().sorted(new Comparator<DcaBCopyPatent>() {
            @Override
            public int compare(DcaBCopyPatent o1, DcaBCopyPatent o2) {
                if(o1.getPatentDate()==null ){
                    return 1;
                }
                if(o2.getPatentDate()==null ){
                    return -1;
                }
                return  o1.getPatentDate().compareTo(o2.getPatentDate());
            }
        }).collect(Collectors.toList());
        customApplyFirst.setDcaBPatentList(listDcaBCopyPatent);
        List<DcaBCopyQualification> dcaBCopyQualificationList = this.iDcaBCopyQualificationService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyApplyjob> listDcaBApplyjob = this.iDcaBCopyApplyjobService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyAttachfile> listDcaBCopyAttachfile = this.iDcaBCopyAttachfileService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyAuditfive> listDcaBCopyAuditfive = this.iDcaBCopyAuditfiveService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyCourseclass> listDcaBCopyCourseclass = this.iDcaBCopyCourseclassService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyYoungprize> dcaBCopyYoungprizeList = this.iDcaBCopyYoungprizeService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyGoal> listDcaBCopyGoal = this.iDcaBCopyGoalService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyTeacherprize> dcaBCopyTeacherprizeList =this.iDcaBCopyTeacherprizeService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyEducationexperice> listDcaBCopyEducationexperice = this.iDcaBCopyEducationexpericeService.getAll(userAccount, dcaYear,gwDj);
        listDcaBCopyEducationexperice=listDcaBCopyEducationexperice.stream().sorted(new Comparator<DcaBCopyEducationexperice>() {
            @Override
            public int compare(DcaBCopyEducationexperice o1, DcaBCopyEducationexperice o2) {
                if(o1.getExpStartTime()==null ){
                    return 1;
                }
                if(o2.getExpStartTime()==null ){
                    return -1;
                }
                return  o1.getExpStartTime().compareTo(o2.getExpStartTime());
            }
        }).collect(Collectors.toList());
        List<DcaBCopyEmploy> listDcaBCopyEmploy = this.iDcaBCopyEmployService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyExportcountry> listDcaBCopyExportcountry = this.iDcaBCopyExportcountryService.getAll(userAccount, dcaYear,gwDj);
        String fivecomment = this.iDcaBCopyAuditdynamicService.GetZtkhqk(userAccount, dcaYear,gwDj);
        List<DcaBCopyGraduate> listDcaBCopyGraduate = this.iDcaBCopyGraduateService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyOtherwork> listDcaBCopyOtherwork = this.iDcaBCopyOtherworkService.getAll(userAccount, dcaYear,gwDj);
        List<DcaBCopyParttimejob> listDcaBCopyParttimejob = this.iDcaBCopyParttimejobService.getAll(userAccount, dcaYear,gwDj);
        listDcaBCopyParttimejob=listDcaBCopyParttimejob.stream().sorted(new Comparator<DcaBCopyParttimejob>() {
            @Override
            public int compare(DcaBCopyParttimejob o1, DcaBCopyParttimejob o2) {
                if(o1.getJzStartTime()==null ){
                    return 1;
                }
                if(o2.getJzStartTime()==null ){
                    return -1;
                }
                return  o1.getJzStartTime().compareTo(o2.getJzStartTime());
            }
        }).collect(Collectors.toList());
        List<DcaBCopyInnovatebuild> listDcaBCopyInnovatebuild = this.iDcaBCopyInnovatebuildService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyLastemploy> listDcaBCopyLastemploy = this.iDcaBCopyLastemployService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyPersonalsummary> listDcaBCopyPersonalsummary = this.iDcaBCopyPersonalsummaryService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyPolitalshow> listDcaBCopyPolitalshow = this.iDcaBCopyPolitalshowService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyPrizeorpunish> listDcaBCopyPrizeorpunish = this.iDcaBCopyPrizeorpunishService.getAll(userAccount, dcaYear, gwDj);
        listDcaBCopyPrizeorpunish=listDcaBCopyPrizeorpunish.stream().sorted(new Comparator<DcaBCopyPrizeorpunish>() {
            @Override
            public int compare(DcaBCopyPrizeorpunish o1, DcaBCopyPrizeorpunish o2) {
                if(o1.getPpStartTime()==null ||o2.getPpStartTime()==null){
                    return  1;
                }
                if(o2.getPpStartTime()==null){
                    return  -1;
                }
                return  o1.getPpStartTime().compareTo(o2.getPpStartTime());
            }
        }).collect(Collectors.toList());
        List<DcaBCopyPublicarticle> listDcaBCopyPublicarticle = this.iDcaBCopyPublicarticleService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopySchoolprize> listDcaBCopySchoolprize = this.iDcaBCopySchoolprizeService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopySciencepublish> listDcaBCopySciencepublish = this.iDcaBCopySciencepublishService.getAll(userAccount, dcaYear, gwDj);
        listDcaBCopySciencepublish=listDcaBCopySciencepublish.stream().sorted(new Comparator<DcaBCopySciencepublish>() {
            @Override
            public int compare(DcaBCopySciencepublish o1, DcaBCopySciencepublish o2) {
                if(o1.getPaperPublishdate()==null ||o2.getPaperPublishdate()==null){
                    return  1;
                }
                if(o2.getPaperPublishdate()==null){
                    return  -1;
                }
                return  o1.getPaperPublishdate().compareTo(o2.getPaperPublishdate());
            }
        }).collect(Collectors.toList());


        List<DcaBCopySciencesearch> listDcaBCopySciencesearch = this.iDcaBCopySciencesearchService.getAll(userAccount, dcaYear, gwDj);

        listDcaBCopySciencesearch=listDcaBCopySciencesearch.stream().sorted(new Comparator<DcaBCopySciencesearch>() {
            @Override
            public int compare(DcaBCopySciencesearch o1, DcaBCopySciencesearch o2) {
                if(o1.getDisplayIndex()==null ||o2.getDisplayIndex()==null){
                    return  1;
                }
                if(o2.getDisplayIndex()==null){
                    return  -1;
                }
                return  o1.getDisplayIndex().compareTo(o2.getDisplayIndex());
            }
        }).collect(Collectors.toList());

        List<DcaBCopyScientificprize> listDcaBCopyScientificprize = this.iDcaBCopyScientificprizeService.getAll(userAccount, dcaYear, gwDj);
        listDcaBCopyScientificprize=listDcaBCopyScientificprize.stream().sorted(new Comparator<DcaBCopyScientificprize>() {
            @Override
            public int compare(DcaBCopyScientificprize o1, DcaBCopyScientificprize o2) {
                if(o1.getSrPrizeDate()==null ||o2.getSrPrizeDate()==null){
                    return  1;
                }
                if(o2.getSrPrizeDate()==null){
                    return  -1;
                }
                return  o1.getSrPrizeDate().compareTo(o2.getSrPrizeDate());
            }
        }).collect(Collectors.toList());
        List<DcaBCopyTalent> listDcaBCopyTalent = this.iDcaBCopyTalentService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyTeachtalent> listDcaBCopyTeachtalent = this.iDcaBCopyTeachtalentService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyTurtor> listDcaBCopyTurtor = this.iDcaBCopyTurtorService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyUndergraduate> listDcaBCopyUndergraduate = this.iDcaBCopyUndergraduateService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyUndergraduateprize> listDcaBCopyUndergraduateprize = this.iDcaBCopyUndergraduateprizeService.getAll(userAccount, dcaYear, gwDj);

        listDcaBCopyUndergraduate=listDcaBCopyUndergraduate.stream().sorted(new Comparator<DcaBCopyUndergraduate>() {
            @Override
            public int compare(DcaBCopyUndergraduate o1, DcaBCopyUndergraduate o2) {
                if(o1.getUgStartDate()==null ||o2.getUgStartDate()==null){
                    return  1;
                }
                if(o2.getUgStartDate()==null){
                    return  -1;
                }
                return  o1.getUgStartDate().compareTo(o2.getUgStartDate());
            }
        }).collect(Collectors.toList());

        /**
         * 二三级新增
         */
        List<DcaBCopyTeacheryj> dcaBCopyTeacheryjs = this.iDcaBCopyTeacheryjService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopySciachievement> dcaBCopySciachievements= this.iDcaBCopySciachievementService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopySureachievement> dcaBCopySureachievements= this.iDcaBCopySureachievementService.getAll(userAccount, dcaYear, gwDj);

        List<DcaBCopyUser> listDcaBCopyUser = this.iDcaBCopyUserService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyTeacherqualify> listDcaBCopyTeacherqualify = this.iDcaBCopyTeacherqualifyService.getAll(userAccount, dcaYear, gwDj);

        List<DcaBCopyAcademic> dcaBCopyAcademicList = this.iDcaBCopyAcademicService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyAchievement> dcaBCopyAchievementList = this.iDcaBCopyAchievementService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyMedicalaccident> dcaBCopyMedicalaccidentList = this.iDcaBCopyMedicalaccidentService.getAll(userAccount, dcaYear, gwDj);
        List<DcaBCopyDoctorturtor> dcaBCopyDoctorturtorList = this.iDcaBCopyDoctorturtorService.getAll(userAccount, dcaYear, gwDj);

        DcaBCopyUser user = listDcaBCopyUser.get(0);
        customApplyFirst.setName(user.getUserAccountName());
        customApplyFirst.setBirthday(DateStr(user.getBirthday(), "yyyyMM"));
        customApplyFirst.setDcaBEducationexpericeList(listDcaBCopyEducationexperice);
        customApplyFirst.setXzyjsgw(user.getPositionName());//专业技术岗位
        customApplyFirst.setXgwzw(user.getPositionName());//
        customApplyFirst.setNpgw(user.getNpPositionName());//
        customApplyFirst.setXcszyjzc(user.getXcszyjzc());
        customApplyFirst.setWcsypqgzrwqk(listDcaBCopyLastemploy.size() > 0 ? listDcaBCopyLastemploy.get(0).getLastContent() : "");
        customApplyFirst.setSzyx(user.getDeptName());


        //照片
        if(StringUtils.isNotEmpty(user.getPictureUrl())) {
            customApplyFirst.setPicUrl("D:/scm/uploadfile/" + FileUtil.getName(user.getPictureUrl()));
        }
        else {
            customApplyFirst.setPicUrl("");
        }
        customApplyFirst.setXrgwjb(user.getXrgwjb());//现岗位级别
        customApplyFirst.setXrgwjbprsj(DateUtil.format(user.getXrgwjbprsj(),"yyyyMM"));
        customApplyFirst.setDjrdzzw(user.getDjrdzzw());

        customApplyFirst.setDcaBParttimejobList(listDcaBCopyParttimejob);
        customApplyFirst.setDcaBPrizeorpunishList(listDcaBCopyPrizeorpunish);
        customApplyFirst.setDcaBAttachfileList(listDcaBCopyAttachfile);
        customApplyFirst.setDcaBExportcountryList(listDcaBCopyExportcountry);
        customApplyFirst.setDcaBCourseclassList(listDcaBCopyCourseclass);
        customApplyFirst.setDcaBSchoolprizeList(listDcaBCopySchoolprize);
        customApplyFirst.setDcaBYoungprizeList(dcaBCopyYoungprizeList);

        customApplyFirst.setDcaBCopyAcademicList(dcaBCopyAcademicList);
        customApplyFirst.setDcaBCopyAchievementList(dcaBCopyAchievementList);
        customApplyFirst.setDcaBCopyMedicalaccidentList(dcaBCopyMedicalaccidentList);
        customApplyFirst.setDcaBCopyDoctorturtorList(dcaBCopyDoctorturtorList);
        customApplyFirst.setDcaBTeacherprizeList(dcaBCopyTeacherprizeList);

        customApplyFirst.setDcaBCopyQualificationList(dcaBCopyQualificationList);

        /**
         * 新增二三级
         */
        customApplyFirst.setDcaBCopySciachievementList(dcaBCopySciachievements);
        customApplyFirst.setDcaBSCopySureachievementList(dcaBCopySureachievements);
        customApplyFirst.setDcaBCopyTeacheryjsList(dcaBCopyTeacheryjs);

        customApplyFirst.setKs(user.getKs());
        customApplyFirst.setTel(user.getTelephone());
        customApplyFirst.setDcaBCopyTeacherqualifyList(listDcaBCopyTeacherqualify);
        customApplyFirst.setDcaBTurtorList(listDcaBCopyTurtor);
        customApplyFirst.setDcaBCopyGraduateList(listDcaBCopyGraduate);
        String shjz = listDcaBCopyParttimejob.stream().map(p -> DateStr(p.getJzStartTime(), "yyyyMM") + (p.getJzEndTime()==null?"":"至") + DateStr(p.getJzEndTime(), "yyyyMM") + " " + p.getJzContent()).collect(Collectors.joining("\n", "", ""));
        customApplyFirst.setShjz(shjz);//社会兼职
        customApplyFirst.setSex(user.getSexName());
        customApplyFirst.setSbnpgwly(listDcaBApplyjob.size() > 0 ? listDcaBApplyjob.get(0).getApplyContent() : "");
        customApplyFirst.setRsbh(user.getAuditSuggestion()); //华科人事编号
        customApplyFirst.setQtgzjcg(listDcaBCopyOtherwork.size() > 0 ? listDcaBCopyOtherwork.get(0).getOtherWork() : "");
        customApplyFirst.setPrsj(user.getZygwDate());
        customApplyFirst.setNpgwzw(zc);//申请职称
        customApplyFirst.setNpgwgzsljyqmb(listDcaBCopyGoal.size() > 0 ? listDcaBCopyGoal.get(0).getPreGoal() : "");
        customApplyFirst.setLxgzsj(DateStr(user.getSchoolDate(), "yyyyMM"));

        String teacherQualify = listDcaBCopyTeacherqualify.stream().map(p -> DateStr(p.getTqReceiveDate(), "yyyyMM") + " " + p.getTqCode()).collect(Collectors.joining("\n", "", ""));
        customApplyFirst.setJszgzbhjhdsj(teacherQualify);

        customApplyFirst.setKhpecentage(fivecomment);

        int currentYear = Integer.parseInt(dcaYear);
        List<String> yearList = new ArrayList<String>() {{
            this.add(String.valueOf(currentYear - 1));
            this.add(String.valueOf(currentYear - 2));
            this.add(String.valueOf(currentYear - 3));
            this.add(String.valueOf(currentYear - 4));
            this.add(String.valueOf(currentYear - 5));
        }};
        /**近5年考核结果*/
        String j5 = listDcaBCopyAuditfive.stream().filter(p -> yearList.contains(p.getYear())).sorted(new Comparator<DcaBCopyAuditfive>() {
            @Override
            public int compare(DcaBCopyAuditfive o1, DcaBCopyAuditfive o2) {

                return  o1.getYear().compareTo(o2.getYear());
            }
        }).map(p -> p.getYear() + " " + p.getKhjg()).collect(Collectors.joining("\n", "", ""));
        customApplyFirst.setJ5nkhqk(j5);

        List<String> yearList3 = new ArrayList<String>() {{
            this.add(String.valueOf(currentYear - 1));
            this.add(String.valueOf(currentYear - 2));
            this.add(String.valueOf(currentYear - 3));
        }};
        /**近3年考核结果*/
        String j3 = listDcaBCopyAuditfive.stream().filter(p -> yearList3.contains(p.getYear())).sorted(new Comparator<DcaBCopyAuditfive>() {
            @Override
            public int compare(DcaBCopyAuditfive o1, DcaBCopyAuditfive o2) {

                return  o1.getYear().compareTo(o2.getYear());
            }
        }).map(p -> p.getYear() + " " + p.getKhjg()).collect(Collectors.joining("\n", "", ""));
        customApplyFirst.setJ3nkhqk(j3);

        String prizeOrPunish = listDcaBCopyPrizeorpunish.stream().map(p -> DateStr(p.getPpStartTime(), "yyyyMM") + " " + p.getPpContent()).collect(Collectors.joining("\n", "", ""));
        customApplyFirst.setHshdshjljcf(prizeOrPunish);

        customApplyFirst.setGwlb(user.getGwdj());
        customApplyFirst.setGrzj(listDcaBCopyPersonalsummary.size() > 0 ? listDcaBCopyPersonalsummary.get(0).getPsContent() : "");
        customApplyFirst.setGrsxzzjsdsf(listDcaBCopyPolitalshow.size() > 0 ? listDcaBCopyPolitalshow.get(0).getPsContent() : "");
        String drfdyjsbzrjkhqk = listDcaBCopyTurtor.stream().map(p -> p.getTurtorMain() + " " + p.getTutorContent()).collect(Collectors.joining("\n", "", ""));
        customApplyFirst.setDrfdyjsbzrjkhqk(drfdyjsbzrjkhqk);


        int comIndex =dcaBReportList.size()>0?dcaBReportList.get(0).getConfirmIndex():0;
        customApplyFirst.setGwdj(dcaBReportList.size()>0?dcaBReportList.get(0).getGwdj():"");
        customApplyFirst.setConfirmIndex(String.valueOf(comIndex==0?"":comIndex));

        customApplyFirst.setDbxcgbs(dcaBReportList.size()>0?dcaBReportList.get(0).getDbxcgbs():"");

        customApplyFirst.setDcaBUndergraduateList(listDcaBCopyUndergraduate);
        customApplyFirst.setDcaBUndergraduateprizeList(listDcaBCopyUndergraduateprize);
        customApplyFirst.setDcaBCopyEmployList(listDcaBCopyEmploy);
        customApplyFirst.setDcaBTalentList(listDcaBCopyTalent);
        customApplyFirst.setDcaBScientificprizeList(listDcaBCopyScientificprize);
        customApplyFirst.setDcaBSciencesearchList(listDcaBCopySciencesearch);
        customApplyFirst.setDcaBSciencepublishList(listDcaBCopySciencepublish);
        customApplyFirst.setDcaBPublicarticleList(listDcaBCopyPublicarticle);
        customApplyFirst.setDcaBPatentList(listDcaBCopyPatent);
        customApplyFirst.setDcaBInnovatebuildList(listDcaBCopyInnovatebuild);
        customApplyFirst.setDcaBGraduate(listDcaBCopyGraduate.size() > 0 ? listDcaBCopyGraduate.get(0) : null);


        return customApplyFirst;
    }

}