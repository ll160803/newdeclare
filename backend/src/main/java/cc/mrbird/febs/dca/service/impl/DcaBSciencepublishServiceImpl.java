package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.*;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.service.IDcaBSciencepublishService;
import cc.mrbird.febs.dca.service.IDcaDJbService;
import cc.mrbird.febs.dca.service.IDcaDYearsettingService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencepublish;
import cc.mrbird.febs.dcacopy.service.IDcaBCopySciencepublishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 科研论文 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBSciencepublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBSciencepublishServiceImpl extends ServiceImpl<DcaBSciencepublishMapper, DcaBSciencepublish> implements IDcaBSciencepublishService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    IDcaDJbService iDcaDJbService;

    @Autowired
    IDcaDYearsettingService iDcaDYearsettingService;

    @Autowired
    IDcaBCopySciencepublishService iDcaBCopySciencepublishService;


    private boolean IsExistInYearSetting(String dcaYear, String gwdj) {
        if(StringUtils.isEmpty(dcaYear)){
            return true;
        }
        List<DcaDYearsetting> yearsettings = this.iDcaDYearsettingService.list();
        if (yearsettings.stream().filter(p -> p.getDcaYear().equals(dcaYear) && p.getGwdj().equals(gwdj)).count() > 0) {
            return false;
        } else {
            return true;
        }
    }
    @Override
    public IPage<DcaBCopySciencepublish> findDcaBCopySciencepublishs(QueryRequest request, DcaBSciencepublish dcaBSciencepublish){

        if(IsExistInYearSetting(dcaBSciencepublish.getAuditMan(), dcaBSciencepublish.getAuditManName())){
            IPage<DcaBCopySciencepublish> result2 = new Page<>();
            return result2;
        }
        LambdaQueryWrapper<DcaBCopySciencepublish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopySciencepublish::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBSciencepublish.getUserAccount())) {
            queryWrapper.and(wrap -> wrap.eq(DcaBCopySciencepublish::getUserAccount, dcaBSciencepublish.getUserAccount()).or()
                    .like(DcaBCopySciencepublish::getUserAccountName, dcaBSciencepublish.getUserAccount()));

        }
        if (StringUtils.isNotBlank(dcaBSciencepublish.getAuditManName())) {// 年度 和高级、中级、初级
            if (dcaBSciencepublish.getAuditManName().equals("高级")) {

                queryWrapper.in(DcaBCopySciencepublish::getGwdj,new String[]{"正高","副高"} );
            }
            if (dcaBSciencepublish.getAuditManName().equals("中级")) {
                queryWrapper.in(DcaBCopySciencepublish::getGwdj,new String[]{"中级","初级"} );
            }
            if (dcaBSciencepublish.getAuditManName().equals("初级")) {
                queryWrapper.in(DcaBCopySciencepublish::getGwdj,new String[]{"二三级"} );
            }
            if (StringUtils.isNotBlank(dcaBSciencepublish.getAuditMan())) {// 年度 和高级、中级、初级
                queryWrapper.eq(DcaBCopySciencepublish::getDcaYear, dcaBSciencepublish.getAuditMan());
            }
        }



        if (dcaBSciencepublish.getAuditState() != null && (dcaBSciencepublish.getAuditState() >= 0)) {
            queryWrapper.eq(DcaBCopySciencepublish::getAuditState, dcaBSciencepublish.getAuditState());
        }
        if (dcaBSciencepublish.getAuditXuhaoS() != null && (dcaBSciencepublish.getAuditXuhaoS() > 0)) {
            if (dcaBSciencepublish.getAuditXuhaoE() == null || dcaBSciencepublish.getAuditXuhaoE().equals(0)) {
                dcaBSciencepublish.setAuditXuhaoE(dcaBSciencepublish.getAuditXuhaoS());
            }
            queryWrapper.apply(" dca_b_copy_sciencepublish.user_account in (select user_account from dca_b_user where patent_ranknum between " + dcaBSciencepublish.getAuditXuhaoS() + " and " + dcaBSciencepublish.getAuditXuhaoE() + ")");
        }
        if (StringUtils.isNotBlank(dcaBSciencepublish.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBSciencepublish.getCreateTimeTo())) {
            queryWrapper
                    .ge(DcaBCopySciencepublish::getCreateTime, dcaBSciencepublish.getCreateTimeFrom())
                    .le(DcaBCopySciencepublish::getCreateTime, dcaBSciencepublish.getCreateTimeTo());
        }
        if (StringUtils.isNotBlank(dcaBSciencepublish.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBSciencepublish.getModifyTimeTo())) {
            queryWrapper
                    .ge(DcaBCopySciencepublish::getModifyTime, dcaBSciencepublish.getModifyTimeFrom())
                    .le(DcaBCopySciencepublish::getModifyTime, dcaBSciencepublish.getModifyTimeTo());
        }

        Page<DcaBCopySciencepublish> page = new Page<>();
        SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
        IPage<DcaBCopySciencepublish> result = this.iDcaBCopySciencepublishService.page(page, queryWrapper);


        return result;
    }


    @Override
    public IPage<DcaBSciencepublish> findDcaBSciencepublishs(QueryRequest request, DcaBSciencepublish dcaBSciencepublish) {
        try {

            if(!IsExistInYearSetting(dcaBSciencepublish.getAuditMan(), dcaBSciencepublish.getAuditManName())){
                    IPage<DcaBSciencepublish> result2 = new Page<>();
                    return result2;
            }
            LambdaQueryWrapper<DcaBSciencepublish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBSciencepublish::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBSciencepublish.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBSciencepublish::getUserAccount, dcaBSciencepublish.getUserAccount()).or()
                        .like(DcaBSciencepublish::getUserAccountName, dcaBSciencepublish.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBSciencepublish.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBSciencepublish.getAuditMan(), dcaBSciencepublish.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBSciencepublish::getUserAccount, userAccountsList);
            }
            if (StringUtils.isNotBlank(dcaBSciencepublish.getPaperName())) {
                queryWrapper.like(DcaBSciencepublish::getPaperName, dcaBSciencepublish.getPaperName());
            }
            if (StringUtils.isNotBlank(dcaBSciencepublish.getJournalName())) {
                queryWrapper.like(DcaBSciencepublish::getJournalName, dcaBSciencepublish.getJournalName());
            }
            if (dcaBSciencepublish.getState() != null) {
                queryWrapper.eq(DcaBSciencepublish::getState, dcaBSciencepublish.getState());
            }
            if (dcaBSciencepublish.getAuditState() != null && (dcaBSciencepublish.getAuditState() >= 0)) {
                queryWrapper.eq(DcaBSciencepublish::getAuditState, dcaBSciencepublish.getAuditState());
            }
            if (dcaBSciencepublish.getAuditXuhaoS() != null && (dcaBSciencepublish.getAuditXuhaoS() > 0)) {
                if (dcaBSciencepublish.getAuditXuhaoE() == null || dcaBSciencepublish.getAuditXuhaoE().equals(0)) {
                    dcaBSciencepublish.setAuditXuhaoE(dcaBSciencepublish.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_sciencepublish.user_account in (select user_account from dca_b_user where patent_ranknum between " + dcaBSciencepublish.getAuditXuhaoS() + " and " + dcaBSciencepublish.getAuditXuhaoE() + ")");
            }

            if (StringUtils.isNotBlank(dcaBSciencepublish.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBSciencepublish.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBSciencepublish::getCreateTime, dcaBSciencepublish.getCreateTimeFrom())
                        .le(DcaBSciencepublish::getCreateTime, dcaBSciencepublish.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBSciencepublish.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBSciencepublish.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBSciencepublish::getModifyTime, dcaBSciencepublish.getModifyTimeFrom())
                        .le(DcaBSciencepublish::getModifyTime, dcaBSciencepublish.getModifyTimeTo());
            }

            Page<DcaBSciencepublish> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.baseMapper.getXuhao();
            List<DcaDJb> jbList = this.iDcaDJbService.list();
            IPage<DcaBSciencepublish> result = this.page(page, queryWrapper);

            result.getRecords().parallelStream().forEach(item ->
            {
                List<userXuhao> list2 = xuhaoList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount())).collect(Collectors.toList());
                if (list2.size() > 0) {
                    item.setAuditXuhao(list2.get(0).getPatentRanknum());
                }
                if (item.getState() != null && item.getState().equals(1)) {
                    List<DcaDJb> jb = jbList.stream().filter(p -> p.getJournalCode().equals(item.getJournalCode())).collect(Collectors.toList());
                    if (jb.size() > 0) {
                        item.setCodejb(jb.get(0).getJb());
                        if (item.getSciValue() != null && item.getSciValue().equals("是") && !StringUtils.isNotBlank(item.getAuditQkjb())) {
                            item.setAuditQkjb(jb.get(0).getJb());
                        }
                    }
                    List<DcaDJb> jb2 = jbList.stream().filter(p -> p.getJournalName().equals(item.getJournalName())).collect(Collectors.toList());
                    if (jb2.size() > 0) {
                        item.setNamejb(jb2.get(0).getJb());
                        if (item.getSciValue() != null && item.getSciValue().equals("否") && !StringUtils.isNotBlank(item.getAuditQkjb())) {
                            item.setAuditQkjb(jb2.get(0).getJb());
                        }
                    }
                }
            });
            return result;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaBSciencepublish> findDcaBSciencepublishList(QueryRequest request, DcaBSciencepublish dcaBSciencepublish) {
        try {
            Page<DcaBSciencepublish> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBSciencepublish(page, dcaBSciencepublish);
        } catch (Exception e) {
            log.error("获取科研论文失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBSciencepublish(DcaBSciencepublish dcaBSciencepublish) {
        dcaBSciencepublish.setId(UUID.randomUUID().toString());
        dcaBSciencepublish.setCreateTime(new Date());
        dcaBSciencepublish.setIsDeletemark(1);
        this.save(dcaBSciencepublish);
    }

    @Override
    @Transactional
    public void updateDcaBSciencepublish(DcaBSciencepublish dcaBSciencepublish) {
        dcaBSciencepublish.setModifyTime(new Date());
        this.baseMapper.updateDcaBSciencepublish(dcaBSciencepublish);
    }

    @Override
    @Transactional
    public void updateStateByUserAccount(String userAccount) {
        this.baseMapper.updateStateByAccount(userAccount);
    }

    @Override
    @Transactional
    public void deleteDcaBSciencepublishs(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public void deleteByuseraccount(String userAccount) {
        this.baseMapper.deleteByAccount(userAccount);
    }


    @Override
    @Transactional
    public int getMaxDisplayIndexByuseraccount(String userAccount) {
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
    }

    @Override
    @Transactional
    public void deleteRealByuseraccount(String userAccount) {
        LambdaQueryWrapper<DcaBSciencepublish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DcaBSciencepublish::getUserAccount, userAccount);
        lambdaQueryWrapper.eq(DcaBSciencepublish::getIsDeletemark, 0);
        this.baseMapper.delete(lambdaQueryWrapper);
    }
}