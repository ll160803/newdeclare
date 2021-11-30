package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBSciachievement;
import cc.mrbird.febs.dca.dao.DcaBSciachievementMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBSciachievementService;
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
 * 主要科研业绩 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-09-14
 */
@Slf4j
@Service("IDcaBSciachievementService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBSciachievementServiceImpl extends ServiceImpl<DcaBSciachievementMapper, DcaBSciachievement> implements IDcaBSciachievementService {

        @Autowired
        IDcaBUserapplyService iDcaBUserapplyService;
        @Autowired
        private DcaBSciencepublishMapper dcaBSciencepublishMapper;
@Override
public IPage<DcaBSciachievement> findDcaBSciachievements(QueryRequest request, DcaBSciachievement dcaBSciachievement){
        try{
        LambdaQueryWrapper<DcaBSciachievement> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBSciachievement::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBSciachievement.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBSciachievement::getUserAccount, dcaBSciachievement.getUserAccount()).or()
        .like(DcaBSciachievement::getUserAccountName, dcaBSciachievement.getUserAccount()));

        }
        if (dcaBSciachievement.getState()!=null) {
        queryWrapper.eq(DcaBSciachievement::getState, dcaBSciachievement.getState());
        }
       /** if (dcaBSciachievement.getAuditState()!=null && (dcaBSciachievement.getAuditState()>=0)) {
        queryWrapper.eq(DcaBSciachievement::getAuditState, dcaBSciachievement.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBSciachievement.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBSciachievement.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBSciachievement::getCreateTime, dcaBSciachievement.getCreateTimeFrom())
                                .le(DcaBSciachievement::getCreateTime, dcaBSciachievement.getCreateTimeTo());
                                }
                if (StringUtils.isNotBlank(dcaBSciachievement.getAuditManName())) {// 年度 和高级、中级、初级
                        List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBSciachievement.getAuditMan(), dcaBSciachievement.getAuditManName());
                        if (userAccountsList.size() == 0) {
                                userAccountsList.add("qiuc09");
                        }
                        queryWrapper.in(DcaBSciachievement::getUserAccount, userAccountsList);
                }
                if (dcaBSciachievement.getAuditXuhaoS() != null && (dcaBSciachievement.getAuditXuhaoS() > 0)) {
                        if (dcaBSciachievement.getAuditXuhaoE() == null || dcaBSciachievement.getAuditXuhaoE().equals(0)) {
                                dcaBSciachievement.setAuditXuhaoE(dcaBSciachievement.getAuditXuhaoS());
                        }
                        queryWrapper.apply(" dca_b_scientificprize.user_account in " +
                                "(select user_account from dca_b_user where patent_ranknum" +
                                " between " + dcaBSciachievement.getAuditXuhaoS() + " and" +
                                " " + dcaBSciachievement.getAuditXuhaoE() + ")");
                }
        Page<DcaBSciachievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
                List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
                IPage<DcaBSciachievement> result = this.page(page, queryWrapper);
                for (DcaBSciachievement item : result.getRecords()
                ) {
                        List<userXuhao> list2 = xuhaoList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount())).collect(Collectors.toList());
                        if (list2.size() > 0) {
                                item.setAuditXuhao(list2.get(0).getPatentRanknum());
                        }
                }
                return result;
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBSciachievement> findDcaBSciachievementList (QueryRequest request, DcaBSciachievement dcaBSciachievement){
        try{
        Page<DcaBSciachievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBSciachievement(page,dcaBSciachievement);
        }catch(Exception e){
        log.error("获取主要科研业绩失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBSciachievement(DcaBSciachievement dcaBSciachievement){
                dcaBSciachievement.setId(UUID.randomUUID().toString());
        dcaBSciachievement.setCreateTime(new Date());
        dcaBSciachievement.setIsDeletemark(1);
        this.save(dcaBSciachievement);
        }

@Override
@Transactional
public void updateDcaBSciachievement(DcaBSciachievement dcaBSciachievement){
        dcaBSciachievement.setModifyTime(new Date());
        this.baseMapper.updateDcaBSciachievement(dcaBSciachievement);
        }

@Override
@Transactional
public void deleteDcaBSciachievements(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }
        }