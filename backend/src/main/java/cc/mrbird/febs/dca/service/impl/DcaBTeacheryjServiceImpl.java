package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBTeacheryj;
import cc.mrbird.febs.dca.dao.DcaBTeacheryjMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBTeacheryjService;
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
 * 主要教学业绩 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-09-14
 */
@Slf4j
@Service("IDcaBTeacheryjService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBTeacheryjServiceImpl extends ServiceImpl<DcaBTeacheryjMapper, DcaBTeacheryj> implements IDcaBTeacheryjService {

        @Autowired
        IDcaBUserapplyService iDcaBUserapplyService;
        @Autowired
        private DcaBSciencepublishMapper dcaBSciencepublishMapper;
@Override
public IPage<DcaBTeacheryj> findDcaBTeacheryjs(QueryRequest request, DcaBTeacheryj dcaBTeacheryj){
        try{
        LambdaQueryWrapper<DcaBTeacheryj> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBTeacheryj::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBTeacheryj.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBTeacheryj::getUserAccount, dcaBTeacheryj.getUserAccount()).or()
        .like(DcaBTeacheryj::getUserAccountName, dcaBTeacheryj.getUserAccount()));

        }
        if (dcaBTeacheryj.getState()!=null) {
        queryWrapper.eq(DcaBTeacheryj::getState, dcaBTeacheryj.getState());
        }
       /** if (dcaBTeacheryj.getAuditState()!=null && (dcaBTeacheryj.getAuditState()>=0)) {
        queryWrapper.eq(DcaBTeacheryj::getAuditState, dcaBTeacheryj.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBTeacheryj.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBTeacheryj.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBTeacheryj::getCreateTime, dcaBTeacheryj.getCreateTimeFrom())
                                .le(DcaBTeacheryj::getCreateTime, dcaBTeacheryj.getCreateTimeTo());
                                }
                if (StringUtils.isNotBlank(dcaBTeacheryj.getAuditManName())) {// 年度 和高级、中级、初级
                        List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBTeacheryj.getAuditMan(), dcaBTeacheryj.getAuditManName());
                        if (userAccountsList.size() == 0) {
                                userAccountsList.add("qiuc09");
                        }
                        queryWrapper.in(DcaBTeacheryj::getUserAccount, userAccountsList);
                }
                if (dcaBTeacheryj.getAuditXuhaoS() != null && (dcaBTeacheryj.getAuditXuhaoS() > 0)) {
                        if (dcaBTeacheryj.getAuditXuhaoE() == null || dcaBTeacheryj.getAuditXuhaoE().equals(0)) {
                                dcaBTeacheryj.setAuditXuhaoE(dcaBTeacheryj.getAuditXuhaoS());
                        }
                        queryWrapper.apply(" dca_b_scientificprize.user_account in " +
                                "(select user_account from dca_b_user where patent_ranknum" +
                                " between " + dcaBTeacheryj.getAuditXuhaoS() + " and" +
                                " " + dcaBTeacheryj.getAuditXuhaoE() + ")");
                }
        Page<DcaBTeacheryj> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
                List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
                IPage<DcaBTeacheryj> result = this.page(page, queryWrapper);
                for (DcaBTeacheryj item : result.getRecords()
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
public IPage<DcaBTeacheryj> findDcaBTeacheryjList (QueryRequest request, DcaBTeacheryj dcaBTeacheryj){
        try{
        Page<DcaBTeacheryj> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBTeacheryj(page,dcaBTeacheryj);
        }catch(Exception e){
        log.error("获取主要教学业绩失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBTeacheryj(DcaBTeacheryj dcaBTeacheryj){
                dcaBTeacheryj.setId(UUID.randomUUID().toString());
        dcaBTeacheryj.setCreateTime(new Date());
        dcaBTeacheryj.setIsDeletemark(1);
        this.save(dcaBTeacheryj);
        }

@Override
@Transactional
public void updateDcaBTeacheryj(DcaBTeacheryj dcaBTeacheryj){
        dcaBTeacheryj.setModifyTime(new Date());
        this.baseMapper.updateDcaBTeacheryj(dcaBTeacheryj);
        }

@Override
@Transactional
public void deleteDcaBTeacheryjs(String[]Ids){
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