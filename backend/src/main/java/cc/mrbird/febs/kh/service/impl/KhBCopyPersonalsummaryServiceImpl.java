package cc.mrbird.febs.kh.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.kh.entity.KhBCopyPersonalsummary;
import cc.mrbird.febs.kh.dao.KhBCopyPersonalsummaryMapper;
import cc.mrbird.febs.kh.service.IKhBCopyPersonalsummaryService;
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
 * @since 2022-05-12
 */
@Slf4j
@Service("IKhBCopyPersonalsummaryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KhBCopyPersonalsummaryServiceImpl extends ServiceImpl<KhBCopyPersonalsummaryMapper, KhBCopyPersonalsummary> implements IKhBCopyPersonalsummaryService {


@Override
public IPage<KhBCopyPersonalsummary> findKhBCopyPersonalsummarys(QueryRequest request, KhBCopyPersonalsummary khBCopyPersonalsummary){
        try{
        LambdaQueryWrapper<KhBCopyPersonalsummary> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(KhBCopyPersonalsummary::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(khBCopyPersonalsummary.getUserAccount())) {
                                queryWrapper.like(KhBCopyPersonalsummary::getUserAccount, khBCopyPersonalsummary.getUserAccount());
                                }

        Page<KhBCopyPersonalsummary> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<KhBCopyPersonalsummary> findKhBCopyPersonalsummaryList (QueryRequest request, KhBCopyPersonalsummary khBCopyPersonalsummary){
        try{
        Page<KhBCopyPersonalsummary> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findKhBCopyPersonalsummary(page,khBCopyPersonalsummary);
        }catch(Exception e){
        log.error("获取个人总结失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createKhBCopyPersonalsummary(KhBCopyPersonalsummary khBCopyPersonalsummary){
                khBCopyPersonalsummary.setId(UUID.randomUUID().toString());
        khBCopyPersonalsummary.setCreateTime(new Date());
        khBCopyPersonalsummary.setIsDeletemark(1);
        this.save(khBCopyPersonalsummary);
        }

@Override
@Transactional
public void updateKhBCopyPersonalsummary(KhBCopyPersonalsummary khBCopyPersonalsummary){
        khBCopyPersonalsummary.setModifyTime(new Date());
        this.baseMapper.updateKhBCopyPersonalsummary(khBCopyPersonalsummary);
        }

@Override
@Transactional
public void deleteKhBCopyPersonalsummarys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<KhBCopyPersonalsummary> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<KhBCopyPersonalsummary> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(KhBCopyPersonalsummary::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(KhBCopyPersonalsummary::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }