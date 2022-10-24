package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBSciencepublish;
import cc.mrbird.febs.dca.entity.DcaBSciencesearch;
import cc.mrbird.febs.dca.dao.DcaBSciencesearchMapper;
import cc.mrbird.febs.dca.entity.DcaDYearsetting;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBSciencesearchService;
import cc.mrbird.febs.dca.service.IDcaDYearsettingService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencesearch;
import cc.mrbird.febs.dcacopy.service.IDcaBCopySciencesearchService;
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
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 任现职以来承担的科研项目 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-06
 */
@Slf4j
@Service("IDcaBSciencesearchService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBSciencesearchServiceImpl extends ServiceImpl<DcaBSciencesearchMapper, DcaBSciencesearch> implements IDcaBSciencesearchService {

    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;
    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;

    @Autowired
    IDcaDYearsettingService iDcaDYearsettingService;

    @Autowired
    IDcaBCopySciencesearchService iDcaBCopySciencesearchService;


    private boolean IsExistInYearSetting(String dcaYear, String gwdj) {
        if (StringUtils.isEmpty(dcaYear)) {
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
    public IPage<DcaBCopySciencesearch> findDcaBCopySciencesearchs(QueryRequest request, DcaBSciencesearch dcaBSciencesearch) {

        if (IsExistInYearSetting(dcaBSciencesearch.getAuditMan(), dcaBSciencesearch.getAuditManName())) {
            IPage<DcaBCopySciencesearch> result2 = new Page<>();
            return result2;
        }
        LambdaQueryWrapper<DcaBCopySciencesearch> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopySciencesearch::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBSciencesearch.getUserAccount())) {
            queryWrapper.and(wrap -> wrap.eq(DcaBCopySciencesearch::getUserAccount, dcaBSciencesearch.getUserAccount()).or()
                    .like(DcaBCopySciencesearch::getUserAccountName, dcaBSciencesearch.getUserAccount()));

        }
        if (StringUtils.isNotBlank(dcaBSciencesearch.getAuditManName())) {// 年度 和高级、中级、初级
            if (dcaBSciencesearch.getAuditManName().equals("高级")) {

                queryWrapper.in(DcaBCopySciencesearch::getGwdj, new String[]{"正高", "副高"});
            }
            if (dcaBSciencesearch.getAuditManName().equals("中级")) {
                queryWrapper.in(DcaBCopySciencesearch::getGwdj, new String[]{"中级", "初级"});
            }
            if (dcaBSciencesearch.getAuditManName().equals("初级")) {
                queryWrapper.in(DcaBCopySciencesearch::getGwdj, new String[]{"二三级"});
            }
            if (StringUtils.isNotBlank(dcaBSciencesearch.getAuditMan())) {// 年度 和高级、中级、初级
                queryWrapper.eq(DcaBCopySciencesearch::getDcaYear, dcaBSciencesearch.getAuditMan());
            }
        }

        if (dcaBSciencesearch.getAuditState() != null && (dcaBSciencesearch.getAuditState() >= 0)) {
            queryWrapper.eq(DcaBCopySciencesearch::getAuditState, dcaBSciencesearch.getAuditState());
        }
        if (dcaBSciencesearch.getAuditXuhaoS() != null && (dcaBSciencesearch.getAuditXuhaoS() > 0)) {
            if (dcaBSciencesearch.getAuditXuhaoE() == null || dcaBSciencesearch.getAuditXuhaoE().equals(0)) {
                dcaBSciencesearch.setAuditXuhaoE(dcaBSciencesearch.getAuditXuhaoS());
            }
            queryWrapper.apply(" dca_b_sciencesearch.user_account in (select user_account from dca_b_user where patent_ranknum between " + dcaBSciencesearch.getAuditXuhaoS() + " and " + dcaBSciencesearch.getAuditXuhaoE() + ")");
        }

        if (StringUtils.isNotBlank(dcaBSciencesearch.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBSciencesearch.getCreateTimeTo())) {
            queryWrapper
                    .ge(DcaBCopySciencesearch::getCreateTime, dcaBSciencesearch.getCreateTimeFrom())
                    .le(DcaBCopySciencesearch::getCreateTime, dcaBSciencesearch.getCreateTimeTo());
        }
        Page<DcaBCopySciencesearch> page = new Page<>();
        SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
        IPage<DcaBCopySciencesearch> result = this.iDcaBCopySciencesearchService.page(page, queryWrapper);
        return result;
    }

    @Override
    public IPage<DcaBSciencesearch> findDcaBSciencesearchs(QueryRequest request, DcaBSciencesearch dcaBSciencesearch) {
        try {
            if (!IsExistInYearSetting(dcaBSciencesearch.getAuditMan(), dcaBSciencesearch.getAuditManName())) {
                IPage<DcaBSciencesearch> result2 = new Page<>();
                return result2;
            }
            LambdaQueryWrapper<DcaBSciencesearch> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBSciencesearch::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBSciencesearch.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBSciencesearch::getUserAccount, dcaBSciencesearch.getUserAccount()).or()
                        .like(DcaBSciencesearch::getUserAccountName, dcaBSciencesearch.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBSciencesearch.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBSciencesearch.getAuditMan(), dcaBSciencesearch.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBSciencesearch::getUserAccount, userAccountsList);
            }
            if (dcaBSciencesearch.getState() != null) {
                queryWrapper.eq(DcaBSciencesearch::getState, dcaBSciencesearch.getState());
            }
            if (dcaBSciencesearch.getAuditState() != null && (dcaBSciencesearch.getAuditState() >= 0)) {
                queryWrapper.eq(DcaBSciencesearch::getAuditState, dcaBSciencesearch.getAuditState());
            }
            if (dcaBSciencesearch.getAuditXuhaoS() != null && (dcaBSciencesearch.getAuditXuhaoS() > 0)) {
                if (dcaBSciencesearch.getAuditXuhaoE() == null || dcaBSciencesearch.getAuditXuhaoE().equals(0)) {
                    dcaBSciencesearch.setAuditXuhaoE(dcaBSciencesearch.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_sciencesearch.user_account in (select user_account from dca_b_user where patent_ranknum between " + dcaBSciencesearch.getAuditXuhaoS() + " and " + dcaBSciencesearch.getAuditXuhaoE() + ")");
            }

            if (StringUtils.isNotBlank(dcaBSciencesearch.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBSciencesearch.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBSciencesearch::getCreateTime, dcaBSciencesearch.getCreateTimeFrom())
                        .le(DcaBSciencesearch::getCreateTime, dcaBSciencesearch.getCreateTimeTo());
            }

            Page<DcaBSciencesearch> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBSciencesearch> result = this.page(page, queryWrapper);
            for (DcaBSciencesearch item : result.getRecords()
            ) {
                List<userXuhao> list2 = xuhaoList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount())).collect(Collectors.toList());
                if (list2.size() > 0) {
                    item.setAuditXuhao(list2.get(0).getPatentRanknum());
                }
            }
            return result;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaBSciencesearch> findDcaBSciencesearchList(QueryRequest request, DcaBSciencesearch dcaBSciencesearch) {
        try {
            Page<DcaBSciencesearch> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBSciencesearch(page, dcaBSciencesearch);
        } catch (Exception e) {
            log.error("获取任现职以来承担的科研项目失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBSciencesearch(DcaBSciencesearch dcaBSciencesearch) {
        dcaBSciencesearch.setId(UUID.randomUUID().toString());
        dcaBSciencesearch.setCreateTime(new Date());
        dcaBSciencesearch.setIsDeletemark(1);
        this.save(dcaBSciencesearch);
    }

    @Override
    @Transactional
    public void updateDcaBSciencesearch(DcaBSciencesearch dcaBSciencesearch) {
        dcaBSciencesearch.setModifyTime(new Date());
        this.baseMapper.updateDcaBSciencesearch(dcaBSciencesearch);
    }

    @Override
    @Transactional
    public void deleteDcaBSciencesearchs(String[] Ids) {
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
}