package cc.mrbird.febs.zq.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zq.entity.ZqBCopyPublicarticle;
import cc.mrbird.febs.zq.dao.ZqBCopyPublicarticleMapper;
import cc.mrbird.febs.zq.service.IZqBCopyPublicarticleService;
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
@Service("IZqBCopyPublicarticleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZqBCopyPublicarticleServiceImpl extends ServiceImpl<ZqBCopyPublicarticleMapper, ZqBCopyPublicarticle> implements IZqBCopyPublicarticleService {


@Override
public IPage<ZqBCopyPublicarticle> findZqBCopyPublicarticles(QueryRequest request, ZqBCopyPublicarticle zqBCopyPublicarticle){
        try{
        LambdaQueryWrapper<ZqBCopyPublicarticle> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ZqBCopyPublicarticle::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(zqBCopyPublicarticle.getUserAccount())) {
                                queryWrapper.like(ZqBCopyPublicarticle::getUserAccount, zqBCopyPublicarticle.getUserAccount());
                                }

        Page<ZqBCopyPublicarticle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ZqBCopyPublicarticle> findZqBCopyPublicarticleList (QueryRequest request, ZqBCopyPublicarticle zqBCopyPublicarticle){
        try{
        Page<ZqBCopyPublicarticle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findZqBCopyPublicarticle(page,zqBCopyPublicarticle);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createZqBCopyPublicarticle(ZqBCopyPublicarticle zqBCopyPublicarticle){
                zqBCopyPublicarticle.setId(UUID.randomUUID().toString());
        zqBCopyPublicarticle.setCreateTime(new Date());
        zqBCopyPublicarticle.setIsDeletemark(1);
        this.save(zqBCopyPublicarticle);
        }

@Override
@Transactional
public void updateZqBCopyPublicarticle(ZqBCopyPublicarticle zqBCopyPublicarticle){
        zqBCopyPublicarticle.setModifyTime(new Date());
        this.baseMapper.updateZqBCopyPublicarticle(zqBCopyPublicarticle);
        }

@Override
@Transactional
public void deleteZqBCopyPublicarticles(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<ZqBCopyPublicarticle> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<ZqBCopyPublicarticle> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(ZqBCopyPublicarticle::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(ZqBCopyPublicarticle::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }