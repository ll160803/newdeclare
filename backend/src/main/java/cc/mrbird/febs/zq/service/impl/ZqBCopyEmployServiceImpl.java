package cc.mrbird.febs.zq.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zq.entity.ZqBCopyEmploy;
import cc.mrbird.febs.zq.dao.ZqBCopyEmployMapper;
import cc.mrbird.febs.zq.service.IZqBCopyEmployService;
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
 * 任职培养 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
@Slf4j
@Service("IZqBCopyEmployService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZqBCopyEmployServiceImpl extends ServiceImpl<ZqBCopyEmployMapper, ZqBCopyEmploy> implements IZqBCopyEmployService {


@Override
public IPage<ZqBCopyEmploy> findZqBCopyEmploys(QueryRequest request, ZqBCopyEmploy zqBCopyEmploy){
        try{
        LambdaQueryWrapper<ZqBCopyEmploy> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ZqBCopyEmploy::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(zqBCopyEmploy.getUserAccount())) {
                                queryWrapper.like(ZqBCopyEmploy::getUserAccount, zqBCopyEmploy.getUserAccount());
                                }

        Page<ZqBCopyEmploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ZqBCopyEmploy> findZqBCopyEmployList (QueryRequest request, ZqBCopyEmploy zqBCopyEmploy){
        try{
        Page<ZqBCopyEmploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findZqBCopyEmploy(page,zqBCopyEmploy);
        }catch(Exception e){
        log.error("获取任职培养失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createZqBCopyEmploy(ZqBCopyEmploy zqBCopyEmploy){
                zqBCopyEmploy.setId(UUID.randomUUID().toString());
        zqBCopyEmploy.setCreateTime(new Date());
        zqBCopyEmploy.setIsDeletemark(1);
        this.save(zqBCopyEmploy);
        }

@Override
@Transactional
public void updateZqBCopyEmploy(ZqBCopyEmploy zqBCopyEmploy){
        zqBCopyEmploy.setModifyTime(new Date());
        this.baseMapper.updateZqBCopyEmploy(zqBCopyEmploy);
        }

@Override
@Transactional
public void deleteZqBCopyEmploys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<ZqBCopyEmploy> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<ZqBCopyEmploy> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(ZqBCopyEmploy::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(ZqBCopyEmploy::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }