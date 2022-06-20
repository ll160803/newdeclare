package cc.mrbird.febs.kh.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.kh.entity.KhBCopySciencesearch;
import cc.mrbird.febs.kh.dao.KhBCopySciencesearchMapper;
import cc.mrbird.febs.kh.service.IKhBCopySciencesearchService;
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
 * 科研项目 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
@Slf4j
@Service("IKhBCopySciencesearchService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KhBCopySciencesearchServiceImpl extends ServiceImpl<KhBCopySciencesearchMapper, KhBCopySciencesearch> implements IKhBCopySciencesearchService {


@Override
public IPage<KhBCopySciencesearch> findKhBCopySciencesearchs(QueryRequest request, KhBCopySciencesearch khBCopySciencesearch){
        try{
        LambdaQueryWrapper<KhBCopySciencesearch> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(KhBCopySciencesearch::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(khBCopySciencesearch.getUserAccount())) {
                                queryWrapper.like(KhBCopySciencesearch::getUserAccount, khBCopySciencesearch.getUserAccount());
                                }

        Page<KhBCopySciencesearch> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<KhBCopySciencesearch> findKhBCopySciencesearchList (QueryRequest request, KhBCopySciencesearch khBCopySciencesearch){
        try{
        Page<KhBCopySciencesearch> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findKhBCopySciencesearch(page,khBCopySciencesearch);
        }catch(Exception e){
        log.error("获取科研项目失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createKhBCopySciencesearch(KhBCopySciencesearch khBCopySciencesearch){
                khBCopySciencesearch.setId(UUID.randomUUID().toString());
        khBCopySciencesearch.setCreateTime(new Date());
        khBCopySciencesearch.setIsDeletemark(1);
        this.save(khBCopySciencesearch);
        }

@Override
@Transactional
public void updateKhBCopySciencesearch(KhBCopySciencesearch khBCopySciencesearch){
        khBCopySciencesearch.setModifyTime(new Date());
        this.baseMapper.updateKhBCopySciencesearch(khBCopySciencesearch);
        }

@Override
@Transactional
public void deleteKhBCopySciencesearchs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<KhBCopySciencesearch> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<KhBCopySciencesearch> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(KhBCopySciencesearch::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(KhBCopySciencesearch::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }