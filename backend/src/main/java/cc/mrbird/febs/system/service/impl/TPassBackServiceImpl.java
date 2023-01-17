package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.TPassBack;
import cc.mrbird.febs.system.dao.TPassBackMapper;
import cc.mrbird.febs.system.service.ITPassBackService;
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
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2023-01-12
 */
@Slf4j
@Service("ITPassBackService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TPassBackServiceImpl extends ServiceImpl<TPassBackMapper, TPassBack> implements ITPassBackService {


@Override
public IPage<TPassBack> findTPassBacks(QueryRequest request, TPassBack tPassBack){
        try{
        LambdaQueryWrapper<TPassBack> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(TPassBack::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(tPassBack.getUserAccount())) {
                                queryWrapper.like(TPassBack::getUserAccount, tPassBack.getUserAccount());
                                }

        Page<TPassBack> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<TPassBack> findTPassBackList (QueryRequest request, TPassBack tPassBack){
        try{
        Page<TPassBack> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findTPassBack(page,tPassBack);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createTPassBack(TPassBack tPassBack){
                tPassBack.setCreateTime(new Date());
        tPassBack.setIsDeletemark(1);
        this.save(tPassBack);
        }

@Override
@Transactional
public void updateTPassBack(TPassBack tPassBack){
        tPassBack.setModifyTime(new Date());
        this.baseMapper.updateTPassBack(tPassBack);
        }

@Override
@Transactional
public void deleteTPassBacks(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<TPassBack> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<TPassBack> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(TPassBack::getUserAccount, userAccount);
        }

      return  this.baseMapper.selectList(queryWrapper);
        }

        }