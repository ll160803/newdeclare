package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacheryj;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyTeacheryjMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyTeacheryjService;
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
 * 主要教学业绩 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-09-30
 */
@Slf4j
@Service("IDcaBCopyTeacheryjService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyTeacheryjServiceImpl extends ServiceImpl<DcaBCopyTeacheryjMapper, DcaBCopyTeacheryj> implements IDcaBCopyTeacheryjService {


@Override
public IPage<DcaBCopyTeacheryj> findDcaBCopyTeacheryjs(QueryRequest request, DcaBCopyTeacheryj dcaBCopyTeacheryj){
        try{
        LambdaQueryWrapper<DcaBCopyTeacheryj> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyTeacheryj::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyTeacheryj.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBCopyTeacheryj.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBCopyTeacheryj::getCreateTime, dcaBCopyTeacheryj.getCreateTimeFrom())
                                .le(DcaBCopyTeacheryj::getCreateTime, dcaBCopyTeacheryj.getCreateTimeTo());
                                }

        Page<DcaBCopyTeacheryj> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyTeacheryj> findDcaBCopyTeacheryjList (QueryRequest request, DcaBCopyTeacheryj dcaBCopyTeacheryj){
        try{
        Page<DcaBCopyTeacheryj> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyTeacheryj(page,dcaBCopyTeacheryj);
        }catch(Exception e){
        log.error("获取主要教学业绩失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyTeacheryj(DcaBCopyTeacheryj dcaBCopyTeacheryj){
                dcaBCopyTeacheryj.setId(UUID.randomUUID().toString());
        dcaBCopyTeacheryj.setCreateTime(new Date());
        dcaBCopyTeacheryj.setIsDeletemark(1);
        this.save(dcaBCopyTeacheryj);
        }

@Override
@Transactional
public void updateDcaBCopyTeacheryj(DcaBCopyTeacheryj dcaBCopyTeacheryj){
        dcaBCopyTeacheryj.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyTeacheryj(dcaBCopyTeacheryj);
        }

@Override
@Transactional
public void deleteDcaBCopyTeacheryjs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyTeacheryj> getAll(String userAccount,String dcaYear,String gwDj){
        LambdaQueryWrapper<DcaBCopyTeacheryj> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyTeacheryj::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyTeacheryj::getDcaYear, dcaYear);
        }
        if (StringUtils.isNotBlank(gwDj)) {
                queryWrapper.eq(DcaBCopyTeacheryj::getGwdj, gwDj);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }