package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.*;
import cc.mrbird.febs.doctor.dao.DcaBDocUserMapper;
import cc.mrbird.febs.doctor.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.dynamic.datasource.annotation.DS;
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
 * @since 2021-01-12
 */
@Slf4j
@Service("IDcaBDocUserService")
@DS("slave")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocUserServiceImpl extends ServiceImpl<DcaBDocUserMapper, DcaBDocUser> implements IDcaBDocUserService {


    @Autowired
    private IDcaBDocAuditfiveService iDcaBDocAuditfiveService;
    @Autowired
    private IDcaBDocAuditfivemonthService iDcaBDocAuditfivemonthService;
    @Autowired
    private IDcaBDocAuditfiveotherService iDcaBDocAuditfiveotherService;
    @Autowired
    private IDcaBDocAuditfivemiddleService iDcaBDocAuditfivemiddleService;
    @Autowired
    private IDcaBDocEducationexpericeService iDcaBDocEducationexpericeService;
    @Autowired
    private IDcaBDocParttimejobService iDcaBDocParttimejobService;
    @Autowired
    private IDcaBDocPrizeorpunishService iDcaBDocPrizeorpunishService;

    @Autowired
    private IDcaBDocExportcountryService iDcaBDocExportcountryService;

    @Autowired
    private IDcaBDocAchievementService iDcaBDocAchievementService;
    @Autowired
    private IDcaBDocSciencepublishService iDcaBDocSciencepublishService;
    @Autowired
    private IDcaBDocSciencesearchService iDcaBDocSciencesearchService;

    @Autowired
    private IDcaBDocScientificprizeService iDcaBDocScientificprizeService;

    @Autowired
    private IDcaBDocPatentService iDcaBDocPatentService;

    @Autowired
    private IDcaBDocPublicarticleService iDcaBDocPublicarticleService;

    @Override
    @DS("slave")
    public IPage<DcaBDocUser> findDcaBDocUsers(QueryRequest request, DcaBDocUser dcaBDocUser) {
        try {
            LambdaQueryWrapper<DcaBDocUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBDocUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBDocUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBDocUser::getUserAccount, dcaBDocUser.getUserAccount()).or()
                        .like(DcaBDocUser::getUserAccountName, dcaBDocUser.getUserAccount()));

            }
            if (dcaBDocUser.getState() != null) {
                queryWrapper.eq(DcaBDocUser::getState, dcaBDocUser.getState());
            }
            /** if (dcaBDocUser.getAuditState()!=null && (dcaBDocUser.getAuditState()>=0)) {
             queryWrapper.eq(DcaBDocUser::getAuditState, dcaBDocUser.getAuditState());
             }*/
            if (StringUtils.isNotBlank(dcaBDocUser.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocUser.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBDocUser::getCreateTime, dcaBDocUser.getCreateTimeFrom())
                        .le(DcaBDocUser::getCreateTime, dcaBDocUser.getCreateTimeTo());
            }

            Page<DcaBDocUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @DS("slave")
    public IPage<DcaBDocUser> findDcaBDocUserList(QueryRequest request, DcaBDocUser dcaBDocUser) {
        try {
            Page<DcaBDocUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBDocUser(page, dcaBDocUser);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    @DS("slave")
    public void createDcaBDocUser(DcaBDocUser dcaBDocUser) {
        dcaBDocUser.setId(UUID.randomUUID().toString());
        dcaBDocUser.setCreateTime(new Date());
        dcaBDocUser.setIsDeletemark(1);
        this.save(dcaBDocUser);
    }

    @Override
    @Transactional
    @DS("slave")
    public void updateDcaBDocUser(DcaBDocUser dcaBDocUser) {
        dcaBDocUser.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocUser(dcaBDocUser);
    }

    @Override
    @Transactional
    @DS("slave")
    public void deleteDcaBDocUsers(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    @DS("slave")
    public void deleteByuseraccount(String userAccount) {
        this.baseMapper.deleteByAccount(userAccount);
    }

    @Override
    @Transactional
    @DS("slave")
    public int getMaxDisplayIndexByuseraccount(String userAccount) {
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
    }

    @Override
    @Transactional
    @DS("slave")
    public List<DcaBDocUser> findPerson(String userAccount) {
        LambdaQueryWrapper<DcaBDocUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocUser::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.eq(DcaBDocUser::getUserAccount, userAccount);

        }
        queryWrapper.eq(DcaBDocUser::getIsDeletemark, 1);
        return this.baseMapper.selectList(queryWrapper);

    }


    @Override
    @Transactional
    @DS("slave")
    public DataUser generateDataUser(String userAccount) {
        DataUser dataUser = new DataUser();
        LambdaQueryWrapper<DcaBDocUser> queryUser = new LambdaQueryWrapper<>();
        queryUser.eq(DcaBDocUser::getUserAccount, userAccount);
        queryUser.eq(DcaBDocUser::getIsDeletemark, 1);
        DcaBDocUser user = this.getOne(queryUser);
        dataUser.setDocUser(user);

        List<DcaBDocAuditfive> list = iDcaBDocAuditfiveService.getAll(userAccount, "");
        list=list.stream().sorted(Comparator.comparing(
                DcaBDocAuditfive::getYear
        )).collect(Collectors.toList());
        dataUser.setDcaBDocAuditfives(list);
        List<DcaBDocAuditfivemonth> dcaBDocAuditfivemonths = iDcaBDocAuditfivemonthService.getAll(userAccount, "");
        dcaBDocAuditfivemonths=dcaBDocAuditfivemonths.stream().sorted( Comparator.nullsLast(Comparator.comparing(
                DcaBDocAuditfivemonth::getYear,Comparator.nullsLast(String::compareTo))
        )).collect(Collectors.toList());
        dataUser.setDcaBDocAuditfivemonths(dcaBDocAuditfivemonths);
        List<DcaBDocAuditfiveother> dcaBDocAuditfiveotherList = iDcaBDocAuditfiveotherService.getAll(userAccount, "");
        dcaBDocAuditfiveotherList=dcaBDocAuditfiveotherList.stream().sorted(
                Comparator.nullsLast(Comparator.comparing(DcaBDocAuditfiveother::getKhdate,Comparator.nullsLast(Date::compareTo)))
                ).collect(Collectors.toList());
        dataUser.setDcaBDocAuditfiveotherList(dcaBDocAuditfiveotherList);
        List<DcaBDocAuditfivemiddle> dcaBDocAuditfivemiddles = iDcaBDocAuditfivemiddleService.getAll(userAccount, "");
        dcaBDocAuditfivemiddles=dcaBDocAuditfivemiddles.stream().sorted(
                Comparator.nullsLast(Comparator.comparing(DcaBDocAuditfivemiddle::getKhDate,Comparator.nullsLast(Date::compareTo)))
        ).collect(Collectors.toList());

        dataUser.setDcaBDocAuditfivemiddles(dcaBDocAuditfivemiddles);
        List<DcaBDocEducationexperice> dcaBDocEducationexpericeList = iDcaBDocEducationexpericeService.getAll(userAccount, "");
        dcaBDocEducationexpericeList=dcaBDocEducationexpericeList.stream().sorted( Comparator.nullsLast(Comparator.comparing(DcaBDocEducationexperice::getExpStartTime,Comparator.nullsLast(Date::compareTo)))).collect(Collectors.toList());
        dataUser.setDcaBDocEducationexpericeList(dcaBDocEducationexpericeList);

        List<DcaBDocParttimejob> jobList = iDcaBDocParttimejobService.getAll(userAccount, "");
        jobList=jobList.stream().sorted(Comparator.nullsLast(Comparator.comparing(DcaBDocParttimejob::getJzStartTime,Comparator.nullsLast(Date::compareTo)))).collect(Collectors.toList());
        dataUser.setJobList(jobList);

        List<DcaBDocPrizeorpunish> dcaBDocPrizeorpunishList = iDcaBDocPrizeorpunishService.getAll(userAccount, "");
        dcaBDocPrizeorpunishList=dcaBDocPrizeorpunishList.stream().sorted( Comparator.nullsLast(Comparator.comparing(DcaBDocPrizeorpunish::getPpStartTime,Comparator.nullsLast(Date::compareTo)))
        ).collect(Collectors.toList());
        dataUser.setDcaBDocPrizeorpunishList(dcaBDocPrizeorpunishList);

        List<DcaBDocExportcountry> dcaBDocExportcountryList = iDcaBDocExportcountryService.getAll(userAccount, "");
        dcaBDocExportcountryList=dcaBDocExportcountryList.stream().sorted(
                Comparator.nullsLast(Comparator.comparing(DcaBDocExportcountry::getCgsj,Comparator.nullsLast(Date::compareTo)))
        ).collect(Collectors.toList());

        dataUser.setDcaBDocExportcountryList(dcaBDocExportcountryList);
        List<DcaBDocAchievement> achievementList = iDcaBDocAchievementService.getAll(userAccount, "");
        achievementList=achievementList.stream().sorted(
                Comparator.nullsLast(Comparator.comparing(DcaBDocAchievement::getAchievementDate,Comparator.nullsLast(Date::compareTo)))
        ).collect(Collectors.toList());
        dataUser.setAchievementList(achievementList);
        List<DcaBDocSciencepublish> sciencepublishList = iDcaBDocSciencepublishService.getAll(userAccount, "");
        sciencepublishList=sciencepublishList.stream().sorted(
                Comparator.nullsLast(Comparator.comparing(DcaBDocSciencepublish::getPaperPublishdate,Comparator.nullsLast(Date::compareTo)))

        ).collect(Collectors.toList());
        dataUser.setSciencepublishList(sciencepublishList);

        dataUser.setWenzhangshuliang(sciencepublishList.size());
        Optional<DcaBDocSciencepublish> op= sciencepublishList.stream().filter(p->p.getPaperCause()!=""&&p.getPaperCause()!=null).
                max(Comparator.comparing(p->Double.parseDouble(p.getPaperCause())));
        if(op.isPresent()) {
            dataUser.setZuigaoyinzi(op.get().getPaperCause());
        }



        List<DcaBDocSciencesearch> docSciencesearchList = iDcaBDocSciencesearchService.getAll(userAccount, "");
        docSciencesearchList=docSciencesearchList.stream().sorted(
                Comparator.nullsLast(Comparator.comparing(DcaBDocSciencesearch::getAuditDate2,Comparator.nullsLast(Date::compareTo)))

        ).collect(Collectors.toList());
        dataUser.setDocSciencesearchList(docSciencesearchList);

        dataUser.setKeyanxiangmugeshu(docSciencesearchList.size());

        List<DcaBDocScientificprize> dcaBDocScientificprizes = iDcaBDocScientificprizeService.getAll(userAccount, "");
        dcaBDocScientificprizes=dcaBDocScientificprizes.stream().sorted(
                Comparator.nullsLast(Comparator.comparing(DcaBDocScientificprize::getSrPrizeDate,Comparator.nullsLast(Date::compareTo)))
        ).collect(Collectors.toList());
        dataUser.setDcaBDocScientificprizes(dcaBDocScientificprizes);
        dataUser.setKeyanhuojianggeshu(dcaBDocScientificprizes.size());

        List<DcaBDocPatent> patentList = iDcaBDocPatentService.getAll(userAccount, "");
        patentList=patentList.stream().sorted(
                Comparator.nullsLast(Comparator.comparing(DcaBDocPatent::getPatentDate,Comparator.nullsLast(Date::compareTo)))

        ).collect(Collectors.toList());
        dataUser.setPatentList(patentList);
        dataUser.setShenqingzhuanligeshu(patentList.size());
        List<DcaBDocPublicarticle> docPublicarticles = iDcaBDocPublicarticleService.getAll(userAccount, "");
        docPublicarticles=docPublicarticles.stream().sorted(
                Comparator.nullsLast(Comparator.comparing(DcaBDocPublicarticle::getCbDate,Comparator.nullsLast(Date::compareTo)))

        ).collect(Collectors.toList());
        dataUser.setPatentList(patentList);
        dataUser.setDocPublicarticles(docPublicarticles);
        dataUser.setChubanzhuzuogeshu(docPublicarticles.size());

        Date schoolDate= user.getSchoolDate();
        Date outDate= user.getCzDate();
        if(outDate!=null){
           double publishCount= sciencepublishList.stream().filter(p->p.getCdzs()!=null &&p.getPaperPublishdate()!=null &&p.getPaperPublishdate().before(outDate) && p.getPaperPublishdate().after(schoolDate)).mapToDouble(p->p.getCdzs().doubleValue()).sum();
           double sciSearchCount= docSciencesearchList.stream().filter(p->p.getAuditDate2()!=null&&p.getAuditDate2().before(outDate)&& p.getAuditDate2().after(schoolDate)&& p.getAuditFund()!=null).mapToDouble(p->p.getAuditFund().doubleValue()).sum();
           double patentCount= patentList.stream().filter(p->p.getPatentDate()!=null &&p.getPatentDate().before(outDate)&& p.getPatentDate().after(schoolDate)&&p.getFenshu()!=null).mapToDouble(p->Double.parseDouble(p.getFenshu())).sum();
            double prizeCount= dcaBDocScientificprizes.stream().filter(p->p.getSrPrizeDate()!=null &&p.getSrPrizeDate().before(outDate)&& p.getSrPrizeDate().after(schoolDate)&&p.getAuditName()!=null).mapToDouble(p->Double.parseDouble(p.getAuditName())).sum();

           dataUser.setChuzhanpingfen(publishCount+sciSearchCount+prizeCount+patentCount+20d);
        }
        else{
            double publishCount= sciencepublishList.stream().filter(p->p.getCdzs()!=null &&p.getPaperPublishdate()!=null && p.getPaperPublishdate().after(schoolDate)).mapToDouble(p->p.getCdzs().doubleValue()).sum();
            double sciSearchCount= docSciencesearchList.stream().filter(p->p.getAuditDate2()!=null&& p.getAuditDate2().after(schoolDate) && p.getAuditFund()!=null).mapToDouble(p->p.getAuditFund().doubleValue()).sum();
            double patentCount= patentList.stream().filter(p->p.getPatentDate()!=null && p.getPatentDate().after(schoolDate)&&p.getFenshu()!=null).mapToDouble(p->Double.parseDouble(p.getFenshu())).sum();
            double prizeCount= dcaBDocScientificprizes.stream().filter(p->p.getSrPrizeDate()!=null && p.getSrPrizeDate().after(schoolDate)&&p.getAuditName()!=null).mapToDouble(p->Double.parseDouble(p.getAuditName())).sum();
            dataUser.setMuqianpingfen(publishCount+sciSearchCount+prizeCount+patentCount+20d);
        }

        return dataUser;
    }
}