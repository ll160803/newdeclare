package cc.mrbird.febs.zq.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zq.entity.ZqBCopySciencesearch;
import cc.mrbird.febs.zq.dao.ZqBCopySciencesearchMapper;
import cc.mrbird.febs.zq.service.IZqBCopySciencesearchService;
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
 * @since 2022-06-24
 */
@Slf4j
@Service("IZqBCopySciencesearchService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZqBCopySciencesearchServiceImpl extends ServiceImpl<ZqBCopySciencesearchMapper, ZqBCopySciencesearch> implements IZqBCopySciencesearchService {


@Override
public IPage<ZqBCopySciencesearch> findZqBCopySciencesearchs(QueryRequest request, ZqBCopySciencesearch zqBCopySciencesearch){
        try{
        LambdaQueryWrapper<ZqBCopySciencesearch> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ZqBCopySciencesearch::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(zqBCopySciencesearch.getUserAccount())) {
                                queryWrapper.like(ZqBCopySciencesearch::getUserAccount, zqBCopySciencesearch.getUserAccount());
                                }

        Page<ZqBCopySciencesearch> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ZqBCopySciencesearch> findZqBCopySciencesearchList (QueryRequest request, ZqBCopySciencesearch zqBCopySciencesearch){
        try{
        Page<ZqBCopySciencesearch> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findZqBCopySciencesearch(page,zqBCopySciencesearch);
        }catch(Exception e){
        log.error("获取科研项目失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createZqBCopySciencesearch(ZqBCopySciencesearch zqBCopySciencesearch){
                zqBCopySciencesearch.setId(UUID.randomUUID().toString());
        zqBCopySciencesearch.setCreateTime(new Date());
        zqBCopySciencesearch.setIsDeletemark(1);
        this.save(zqBCopySciencesearch);
        }

@Override
@Transactional
public void updateZqBCopySciencesearch(ZqBCopySciencesearch zqBCopySciencesearch){
        zqBCopySciencesearch.setModifyTime(new Date());
        this.baseMapper.updateZqBCopySciencesearch(zqBCopySciencesearch);
        }

@Override
@Transactional
public void deleteZqBCopySciencesearchs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<ZqBCopySciencesearch> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<ZqBCopySciencesearch> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(ZqBCopySciencesearch::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(ZqBCopySciencesearch::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }