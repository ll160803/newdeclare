package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciachievement;
import cc.mrbird.febs.dcacopy.dao.DcaBCopySciachievementMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopySciachievementService;
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
 * 主要科研业绩 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-09-30
 */
@Slf4j
@Service("IDcaBCopySciachievementService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopySciachievementServiceImpl extends ServiceImpl<DcaBCopySciachievementMapper, DcaBCopySciachievement> implements IDcaBCopySciachievementService {


@Override
public IPage<DcaBCopySciachievement> findDcaBCopySciachievements(QueryRequest request, DcaBCopySciachievement dcaBCopySciachievement){
        try{
        LambdaQueryWrapper<DcaBCopySciachievement> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopySciachievement::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopySciachievement.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBCopySciachievement.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBCopySciachievement::getCreateTime, dcaBCopySciachievement.getCreateTimeFrom())
                                .le(DcaBCopySciachievement::getCreateTime, dcaBCopySciachievement.getCreateTimeTo());
                                }

        Page<DcaBCopySciachievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopySciachievement> findDcaBCopySciachievementList (QueryRequest request, DcaBCopySciachievement dcaBCopySciachievement){
        try{
        Page<DcaBCopySciachievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopySciachievement(page,dcaBCopySciachievement);
        }catch(Exception e){
        log.error("获取主要科研业绩失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopySciachievement(DcaBCopySciachievement dcaBCopySciachievement){
                dcaBCopySciachievement.setId(UUID.randomUUID().toString());
        dcaBCopySciachievement.setCreateTime(new Date());
        dcaBCopySciachievement.setIsDeletemark(1);
        this.save(dcaBCopySciachievement);
        }

@Override
@Transactional
public void updateDcaBCopySciachievement(DcaBCopySciachievement dcaBCopySciachievement){
        dcaBCopySciachievement.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopySciachievement(dcaBCopySciachievement);
        }

@Override
@Transactional
public void deleteDcaBCopySciachievements(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopySciachievement> getAll(String userAccount,String dcaYear,String gwDj){
        LambdaQueryWrapper<DcaBCopySciachievement> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopySciachievement::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopySciachievement::getDcaYear, dcaYear);
        }
        if (StringUtils.isNotBlank(gwDj)) {
                queryWrapper.eq(DcaBCopySciachievement::getGwdj, gwDj);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }