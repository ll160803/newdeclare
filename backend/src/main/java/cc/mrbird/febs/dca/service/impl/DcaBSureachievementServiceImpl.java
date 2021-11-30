package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBScientificprize;
import cc.mrbird.febs.dca.entity.DcaBSureachievement;
import cc.mrbird.febs.dca.dao.DcaBSureachievementMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBSureachievementService;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
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

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 医疗认可 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-09-15
 */
@Slf4j
@Service("IDcaBSureachievementService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBSureachievementServiceImpl extends ServiceImpl<DcaBSureachievementMapper, DcaBSureachievement> implements IDcaBSureachievementService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBSureachievement> findDcaBSureachievements(QueryRequest request, DcaBSureachievement dcaBSureachievement) {
        try {
            LambdaQueryWrapper<DcaBSureachievement> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBSureachievement::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBSureachievement.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBSureachievement::getUserAccount, dcaBSureachievement.getUserAccount()).or()
                        .like(DcaBSureachievement::getUserAccountName, dcaBSureachievement.getUserAccount()));

            }
            if (dcaBSureachievement.getState() != null) {
                queryWrapper.eq(DcaBSureachievement::getState, dcaBSureachievement.getState());
            }
            if (StringUtils.isNotBlank(dcaBSureachievement.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBSureachievement.getAuditMan(), dcaBSureachievement.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBSureachievement::getUserAccount, userAccountsList);
            }
            if (dcaBSureachievement.getAuditXuhaoS() != null && (dcaBSureachievement.getAuditXuhaoS() > 0)) {
                if (dcaBSureachievement.getAuditXuhaoE() == null || dcaBSureachievement.getAuditXuhaoE().equals(0)) {
                    dcaBSureachievement.setAuditXuhaoE(dcaBSureachievement.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_scientificprize.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBSureachievement.getAuditXuhaoS() + " and" +
                        " " + dcaBSureachievement.getAuditXuhaoE() + ")");
            }
            /** if (dcaBSureachievement.getAuditState()!=null && (dcaBSureachievement.getAuditState()>=0)) {
             queryWrapper.eq(DcaBSureachievement::getAuditState, dcaBSureachievement.getAuditState());
             }*/
            if (StringUtils.isNotBlank(dcaBSureachievement.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBSureachievement.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBSureachievement::getCreateTime, dcaBSureachievement.getCreateTimeFrom())
                        .le(DcaBSureachievement::getCreateTime, dcaBSureachievement.getCreateTimeTo());
            }

            Page<DcaBSureachievement> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBSureachievement> result = this.page(page, queryWrapper);
            for (DcaBSureachievement item : result.getRecords()
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
    public IPage<DcaBSureachievement> findDcaBSureachievementList(QueryRequest request, DcaBSureachievement dcaBSureachievement) {
        try {
            Page<DcaBSureachievement> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBSureachievement(page, dcaBSureachievement);
        } catch (Exception e) {
            log.error("获取医疗认可失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBSureachievement(DcaBSureachievement dcaBSureachievement) {
        dcaBSureachievement.setId(UUID.randomUUID().toString());
        dcaBSureachievement.setCreateTime(new Date());
        dcaBSureachievement.setIsDeletemark(1);
        this.save(dcaBSureachievement);
    }

    @Override
    @Transactional
    public void updateDcaBSureachievement(DcaBSureachievement dcaBSureachievement) {
        dcaBSureachievement.setModifyTime(new Date());
        this.baseMapper.updateDcaBSureachievement(dcaBSureachievement);
    }

    @Override
    @Transactional
    public void deleteDcaBSureachievements(String[] Ids) {
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