package cc.mrbird.febs.zq.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zq.entity.ZqBCopyPersonalsummary;
import cc.mrbird.febs.zq.dao.ZqBCopyPersonalsummaryMapper;
import cc.mrbird.febs.zq.service.IZqBCopyPersonalsummaryService;
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
 * 个人总结 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
@Slf4j
@Service("IZqBCopyPersonalsummaryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZqBCopyPersonalsummaryServiceImpl extends ServiceImpl<ZqBCopyPersonalsummaryMapper, ZqBCopyPersonalsummary> implements IZqBCopyPersonalsummaryService {


@Override
public IPage<ZqBCopyPersonalsummary> findZqBCopyPersonalsummarys(QueryRequest request, ZqBCopyPersonalsummary zqBCopyPersonalsummary){
        try{
        LambdaQueryWrapper<ZqBCopyPersonalsummary> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ZqBCopyPersonalsummary::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(zqBCopyPersonalsummary.getUserAccount())) {
                                queryWrapper.like(ZqBCopyPersonalsummary::getUserAccount, zqBCopyPersonalsummary.getUserAccount());
                                }

        Page<ZqBCopyPersonalsummary> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ZqBCopyPersonalsummary> findZqBCopyPersonalsummaryList (QueryRequest request, ZqBCopyPersonalsummary zqBCopyPersonalsummary){
        try{
        Page<ZqBCopyPersonalsummary> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findZqBCopyPersonalsummary(page,zqBCopyPersonalsummary);
        }catch(Exception e){
        log.error("获取个人总结失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createZqBCopyPersonalsummary(ZqBCopyPersonalsummary zqBCopyPersonalsummary){
                zqBCopyPersonalsummary.setId(UUID.randomUUID().toString());
        zqBCopyPersonalsummary.setCreateTime(new Date());
        zqBCopyPersonalsummary.setIsDeletemark(1);
        this.save(zqBCopyPersonalsummary);
        }

@Override
@Transactional
public void updateZqBCopyPersonalsummary(ZqBCopyPersonalsummary zqBCopyPersonalsummary){
        zqBCopyPersonalsummary.setModifyTime(new Date());
        this.baseMapper.updateZqBCopyPersonalsummary(zqBCopyPersonalsummary);
        }

@Override
@Transactional
public void deleteZqBCopyPersonalsummarys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<ZqBCopyPersonalsummary> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<ZqBCopyPersonalsummary> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(ZqBCopyPersonalsummary::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(ZqBCopyPersonalsummary::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }