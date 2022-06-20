package cc.mrbird.febs.kh.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.kh.entity.KhBCopyPublicarticle;
import cc.mrbird.febs.kh.dao.KhBCopyPublicarticleMapper;
import cc.mrbird.febs.kh.service.IKhBCopyPublicarticleService;
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
 * @since 2022-05-13
 */
@Slf4j
@Service("IKhBCopyPublicarticleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KhBCopyPublicarticleServiceImpl extends ServiceImpl<KhBCopyPublicarticleMapper, KhBCopyPublicarticle> implements IKhBCopyPublicarticleService {


@Override
public IPage<KhBCopyPublicarticle> findKhBCopyPublicarticles(QueryRequest request, KhBCopyPublicarticle khBCopyPublicarticle){
        try{
        LambdaQueryWrapper<KhBCopyPublicarticle> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(KhBCopyPublicarticle::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(khBCopyPublicarticle.getUserAccount())) {
                                queryWrapper.like(KhBCopyPublicarticle::getUserAccount, khBCopyPublicarticle.getUserAccount());
                                }

        Page<KhBCopyPublicarticle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<KhBCopyPublicarticle> findKhBCopyPublicarticleList (QueryRequest request, KhBCopyPublicarticle khBCopyPublicarticle){
        try{
        Page<KhBCopyPublicarticle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findKhBCopyPublicarticle(page,khBCopyPublicarticle);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createKhBCopyPublicarticle(KhBCopyPublicarticle khBCopyPublicarticle){
                khBCopyPublicarticle.setId(UUID.randomUUID().toString());
        khBCopyPublicarticle.setCreateTime(new Date());
        khBCopyPublicarticle.setIsDeletemark(1);
        this.save(khBCopyPublicarticle);
        }

@Override
@Transactional
public void updateKhBCopyPublicarticle(KhBCopyPublicarticle khBCopyPublicarticle){
        khBCopyPublicarticle.setModifyTime(new Date());
        this.baseMapper.updateKhBCopyPublicarticle(khBCopyPublicarticle);
        }

@Override
@Transactional
public void deleteKhBCopyPublicarticles(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<KhBCopyPublicarticle> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<KhBCopyPublicarticle> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(KhBCopyPublicarticle::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(KhBCopyPublicarticle::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }