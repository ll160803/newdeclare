package cc.mrbird.febs.kh.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.kh.entity.KhBCopySciencepublish;
import cc.mrbird.febs.kh.dao.KhBCopySciencepublishMapper;
import cc.mrbird.febs.kh.service.IKhBCopySciencepublishService;
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
 * @since 2022-05-12
 */
@Slf4j
@Service("IKhBCopySciencepublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KhBCopySciencepublishServiceImpl extends ServiceImpl<KhBCopySciencepublishMapper, KhBCopySciencepublish> implements IKhBCopySciencepublishService {


@Override
public IPage<KhBCopySciencepublish> findKhBCopySciencepublishs(QueryRequest request, KhBCopySciencepublish khBCopySciencepublish){
        try{
        LambdaQueryWrapper<KhBCopySciencepublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(KhBCopySciencepublish::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(khBCopySciencepublish.getUserAccount())) {
                                queryWrapper.like(KhBCopySciencepublish::getUserAccount, khBCopySciencepublish.getUserAccount());
                                }

        Page<KhBCopySciencepublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<KhBCopySciencepublish> findKhBCopySciencepublishList (QueryRequest request, KhBCopySciencepublish khBCopySciencepublish){
        try{
        Page<KhBCopySciencepublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findKhBCopySciencepublish(page,khBCopySciencepublish);
        }catch(Exception e){
        log.error("获取科研论文失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createKhBCopySciencepublish(KhBCopySciencepublish khBCopySciencepublish){
                khBCopySciencepublish.setId(UUID.randomUUID().toString());
        khBCopySciencepublish.setCreateTime(new Date());
        khBCopySciencepublish.setIsDeletemark(1);
        this.save(khBCopySciencepublish);
        }

@Override
@Transactional
public void updateKhBCopySciencepublish(KhBCopySciencepublish khBCopySciencepublish){
        khBCopySciencepublish.setModifyTime(new Date());
        this.baseMapper.updateKhBCopySciencepublish(khBCopySciencepublish);
        }

@Override
@Transactional
public void deleteKhBCopySciencepublishs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<KhBCopySciencepublish> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<KhBCopySciencepublish> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(KhBCopySciencepublish::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(KhBCopySciencepublish::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }