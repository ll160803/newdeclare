package cc.mrbird.febs.zq.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zq.entity.ZqBCopySciencepublish;
import cc.mrbird.febs.zq.dao.ZqBCopySciencepublishMapper;
import cc.mrbird.febs.zq.service.IZqBCopySciencepublishService;
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
 * 科研论文 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
@Slf4j
@Service("IZqBCopySciencepublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZqBCopySciencepublishServiceImpl extends ServiceImpl<ZqBCopySciencepublishMapper, ZqBCopySciencepublish> implements IZqBCopySciencepublishService {


@Override
public IPage<ZqBCopySciencepublish> findZqBCopySciencepublishs(QueryRequest request, ZqBCopySciencepublish zqBCopySciencepublish){
        try{
        LambdaQueryWrapper<ZqBCopySciencepublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ZqBCopySciencepublish::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(zqBCopySciencepublish.getUserAccount())) {
                                queryWrapper.like(ZqBCopySciencepublish::getUserAccount, zqBCopySciencepublish.getUserAccount());
                                }

        Page<ZqBCopySciencepublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ZqBCopySciencepublish> findZqBCopySciencepublishList (QueryRequest request, ZqBCopySciencepublish zqBCopySciencepublish){
        try{
        Page<ZqBCopySciencepublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findZqBCopySciencepublish(page,zqBCopySciencepublish);
        }catch(Exception e){
        log.error("获取科研论文失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createZqBCopySciencepublish(ZqBCopySciencepublish zqBCopySciencepublish){
                zqBCopySciencepublish.setId(UUID.randomUUID().toString());
        zqBCopySciencepublish.setCreateTime(new Date());
        zqBCopySciencepublish.setIsDeletemark(1);
        this.save(zqBCopySciencepublish);
        }

@Override
@Transactional
public void updateZqBCopySciencepublish(ZqBCopySciencepublish zqBCopySciencepublish){
        zqBCopySciencepublish.setModifyTime(new Date());
        this.baseMapper.updateZqBCopySciencepublish(zqBCopySciencepublish);
        }

@Override
@Transactional
public void deleteZqBCopySciencepublishs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<ZqBCopySciencepublish> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<ZqBCopySciencepublish> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(ZqBCopySciencepublish::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(ZqBCopySciencepublish::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }