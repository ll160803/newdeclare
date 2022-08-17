package cc.mrbird.febs.zq.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zq.entity.ZqBCopyLastemploy;
import cc.mrbird.febs.zq.dao.ZqBCopyLastemployMapper;
import cc.mrbird.febs.zq.service.IZqBCopyLastemployService;
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
 * 完成上一聘期 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
@Slf4j
@Service("IZqBCopyLastemployService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZqBCopyLastemployServiceImpl extends ServiceImpl<ZqBCopyLastemployMapper, ZqBCopyLastemploy> implements IZqBCopyLastemployService {


@Override
public IPage<ZqBCopyLastemploy> findZqBCopyLastemploys(QueryRequest request, ZqBCopyLastemploy zqBCopyLastemploy){
        try{
        LambdaQueryWrapper<ZqBCopyLastemploy> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ZqBCopyLastemploy::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(zqBCopyLastemploy.getUserAccount())) {
                                queryWrapper.like(ZqBCopyLastemploy::getUserAccount, zqBCopyLastemploy.getUserAccount());
                                }

        Page<ZqBCopyLastemploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ZqBCopyLastemploy> findZqBCopyLastemployList (QueryRequest request, ZqBCopyLastemploy zqBCopyLastemploy){
        try{
        Page<ZqBCopyLastemploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findZqBCopyLastemploy(page,zqBCopyLastemploy);
        }catch(Exception e){
        log.error("获取完成上一聘期失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createZqBCopyLastemploy(ZqBCopyLastemploy zqBCopyLastemploy){
                zqBCopyLastemploy.setId(UUID.randomUUID().toString());
        zqBCopyLastemploy.setCreateTime(new Date());
        zqBCopyLastemploy.setIsDeletemark(1);
        this.save(zqBCopyLastemploy);
        }

@Override
@Transactional
public void updateZqBCopyLastemploy(ZqBCopyLastemploy zqBCopyLastemploy){
        zqBCopyLastemploy.setModifyTime(new Date());
        this.baseMapper.updateZqBCopyLastemploy(zqBCopyLastemploy);
        }

@Override
@Transactional
public void deleteZqBCopyLastemploys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<ZqBCopyLastemploy> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<ZqBCopyLastemploy> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(ZqBCopyLastemploy::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(ZqBCopyLastemploy::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }