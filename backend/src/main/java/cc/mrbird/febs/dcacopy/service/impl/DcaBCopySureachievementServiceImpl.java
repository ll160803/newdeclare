package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySureachievement;
import cc.mrbird.febs.dcacopy.dao.DcaBCopySureachievementMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopySureachievementService;
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
 * 医疗认可 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-09-30
 */
@Slf4j
@Service("IDcaBCopySureachievementService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopySureachievementServiceImpl extends ServiceImpl<DcaBCopySureachievementMapper, DcaBCopySureachievement> implements IDcaBCopySureachievementService {


@Override
public IPage<DcaBCopySureachievement> findDcaBCopySureachievements(QueryRequest request, DcaBCopySureachievement dcaBCopySureachievement){
        try{
        LambdaQueryWrapper<DcaBCopySureachievement> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopySureachievement::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopySureachievement.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBCopySureachievement.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBCopySureachievement::getCreateTime, dcaBCopySureachievement.getCreateTimeFrom())
                                .le(DcaBCopySureachievement::getCreateTime, dcaBCopySureachievement.getCreateTimeTo());
                                }

        Page<DcaBCopySureachievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopySureachievement> findDcaBCopySureachievementList (QueryRequest request, DcaBCopySureachievement dcaBCopySureachievement){
        try{
        Page<DcaBCopySureachievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopySureachievement(page,dcaBCopySureachievement);
        }catch(Exception e){
        log.error("获取医疗认可失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopySureachievement(DcaBCopySureachievement dcaBCopySureachievement){
                dcaBCopySureachievement.setId(UUID.randomUUID().toString());
        dcaBCopySureachievement.setCreateTime(new Date());
        dcaBCopySureachievement.setIsDeletemark(1);
        this.save(dcaBCopySureachievement);
        }

@Override
@Transactional
public void updateDcaBCopySureachievement(DcaBCopySureachievement dcaBCopySureachievement){
        dcaBCopySureachievement.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopySureachievement(dcaBCopySureachievement);
        }

@Override
@Transactional
public void deleteDcaBCopySureachievements(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopySureachievement> getAll(String userAccount,String dcaYear,String gwDj){
        LambdaQueryWrapper<DcaBCopySureachievement> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopySureachievement::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopySureachievement::getDcaYear, dcaYear);
        }
        if (StringUtils.isNotBlank(gwDj)) {
                queryWrapper.eq(DcaBCopySureachievement::getGwdj, gwDj);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }