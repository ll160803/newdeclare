package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocAuditfivemonth;
import cc.mrbird.febs.doctor.dao.DcaBDocAuditfivemonthMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocAuditfivemonthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 * 月度考核 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-11-14
 */
@Slf4j
@Service("IDcaBDocAuditfivemonthService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocAuditfivemonthServiceImpl extends ServiceImpl<DcaBDocAuditfivemonthMapper, DcaBDocAuditfivemonth> implements IDcaBDocAuditfivemonthService {


@Override
@DS("slave")
public IPage<DcaBDocAuditfivemonth> findDcaBDocAuditfivemonths(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth){
        try{
        LambdaQueryWrapper<DcaBDocAuditfivemonth> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocAuditfivemonth::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBDocAuditfivemonth.getUserAccount())) {
                                queryWrapper.like(DcaBDocAuditfivemonth::getUserAccount, dcaBDocAuditfivemonth.getUserAccount());
                                }

        Page<DcaBDocAuditfivemonth> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocAuditfivemonth> findDcaBDocAuditfivemonthList (QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth){
        try{
        Page<DcaBDocAuditfivemonth> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocAuditfivemonth(page,dcaBDocAuditfivemonth);
        }catch(Exception e){
        log.error("获取月度考核失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocAuditfivemonth(DcaBDocAuditfivemonth dcaBDocAuditfivemonth){
                dcaBDocAuditfivemonth.setId(UUID.randomUUID().toString());
        dcaBDocAuditfivemonth.setCreateTime(new Date());
        dcaBDocAuditfivemonth.setIsDeletemark(1);
        this.save(dcaBDocAuditfivemonth);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocAuditfivemonth(DcaBDocAuditfivemonth dcaBDocAuditfivemonth){
        dcaBDocAuditfivemonth.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocAuditfivemonth(dcaBDocAuditfivemonth);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocAuditfivemonths(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
@DS("slave")
public List<DcaBDocAuditfivemonth> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBDocAuditfivemonth> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBDocAuditfivemonth::getUserAccount, userAccount);
        }
queryWrapper.eq(DcaBDocAuditfivemonth::getState,3);
      return  this.baseMapper.selectList(queryWrapper);
        }
        @Override
        @Transactional
        @DS("slave")
        public    void deleteAll(){
                this.baseMapper.deleteAll();
        }
        }