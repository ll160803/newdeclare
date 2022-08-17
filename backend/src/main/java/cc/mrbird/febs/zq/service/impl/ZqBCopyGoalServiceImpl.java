package cc.mrbird.febs.zq.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zq.entity.ZqBCopyGoal;
import cc.mrbird.febs.zq.dao.ZqBCopyGoalMapper;
import cc.mrbird.febs.zq.service.IZqBCopyGoalService;
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
 * @since 2022-06-24
 */
@Slf4j
@Service("IZqBCopyGoalService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZqBCopyGoalServiceImpl extends ServiceImpl<ZqBCopyGoalMapper, ZqBCopyGoal> implements IZqBCopyGoalService {


@Override
public IPage<ZqBCopyGoal> findZqBCopyGoals(QueryRequest request, ZqBCopyGoal zqBCopyGoal){
        try{
        LambdaQueryWrapper<ZqBCopyGoal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ZqBCopyGoal::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(zqBCopyGoal.getUserAccount())) {
                                queryWrapper.like(ZqBCopyGoal::getUserAccount, zqBCopyGoal.getUserAccount());
                                }

        Page<ZqBCopyGoal> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ZqBCopyGoal> findZqBCopyGoalList (QueryRequest request, ZqBCopyGoal zqBCopyGoal){
        try{
        Page<ZqBCopyGoal> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findZqBCopyGoal(page,zqBCopyGoal);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createZqBCopyGoal(ZqBCopyGoal zqBCopyGoal){
                zqBCopyGoal.setId(UUID.randomUUID().toString());
        zqBCopyGoal.setCreateTime(new Date());
        zqBCopyGoal.setIsDeletemark(1);
        this.save(zqBCopyGoal);
        }

@Override
@Transactional
public void updateZqBCopyGoal(ZqBCopyGoal zqBCopyGoal){
        zqBCopyGoal.setModifyTime(new Date());
        this.baseMapper.updateZqBCopyGoal(zqBCopyGoal);
        }

@Override
@Transactional
public void deleteZqBCopyGoals(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<ZqBCopyGoal> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<ZqBCopyGoal> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(ZqBCopyGoal::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(ZqBCopyGoal::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }