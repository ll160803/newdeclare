package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaDYearsetting;
import cc.mrbird.febs.dca.dao.DcaDYearsettingMapper;
import cc.mrbird.febs.dca.service.IDcaDYearsettingService;
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
 * 申报年度设置 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-10-20
 */
@Slf4j
@Service("IDcaDYearsettingService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaDYearsettingServiceImpl extends ServiceImpl<DcaDYearsettingMapper, DcaDYearsetting> implements IDcaDYearsettingService {


@Override
public IPage<DcaDYearsetting> findDcaDYearsettings(QueryRequest request, DcaDYearsetting dcaDYearsetting){
        try{
        LambdaQueryWrapper<DcaDYearsetting> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaDYearsetting::getIsDeletemark, 1);//1是未删 0是已删


        Page<DcaDYearsetting> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaDYearsetting> findDcaDYearsettingList (QueryRequest request, DcaDYearsetting dcaDYearsetting){
        try{
        Page<DcaDYearsetting> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaDYearsetting(page,dcaDYearsetting);
        }catch(Exception e){
        log.error("获取申报年度设置失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaDYearsetting(DcaDYearsetting dcaDYearsetting){
                dcaDYearsetting.setCreateTime(new Date());
        dcaDYearsetting.setIsDeletemark(1);
        this.save(dcaDYearsetting);
        }

@Override
@Transactional
public void updateDcaDYearsetting(DcaDYearsetting dcaDYearsetting){
        dcaDYearsetting.setModifyTime(new Date());
        this.baseMapper.updateDcaDYearsetting(dcaDYearsetting);
        }

@Override
@Transactional
public void deleteDcaDYearsettings(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }