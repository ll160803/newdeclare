package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocAuditfivemiddle;
import cc.mrbird.febs.doctor.dao.DcaBDocAuditfivemiddleMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocAuditfivemiddleService;
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
 * 中期考核 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-11-14
 */
@Slf4j
@Service("IDcaBDocAuditfivemiddleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocAuditfivemiddleServiceImpl extends ServiceImpl<DcaBDocAuditfivemiddleMapper, DcaBDocAuditfivemiddle> implements IDcaBDocAuditfivemiddleService {


@Override
@DS("slave")
public IPage<DcaBDocAuditfivemiddle> findDcaBDocAuditfivemiddles(QueryRequest request, DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle){
        try{
        LambdaQueryWrapper<DcaBDocAuditfivemiddle> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocAuditfivemiddle::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBDocAuditfivemiddle.getUserAccount())) {
                                queryWrapper.like(DcaBDocAuditfivemiddle::getUserAccount, dcaBDocAuditfivemiddle.getUserAccount());
                                }

        Page<DcaBDocAuditfivemiddle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocAuditfivemiddle> findDcaBDocAuditfivemiddleList (QueryRequest request, DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle){
        try{
        Page<DcaBDocAuditfivemiddle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocAuditfivemiddle(page,dcaBDocAuditfivemiddle);
        }catch(Exception e){
        log.error("获取中期考核失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocAuditfivemiddle(DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle){
                dcaBDocAuditfivemiddle.setId(UUID.randomUUID().toString());
        dcaBDocAuditfivemiddle.setCreateTime(new Date());
        dcaBDocAuditfivemiddle.setIsDeletemark(1);
        this.save(dcaBDocAuditfivemiddle);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocAuditfivemiddle(DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle){
        dcaBDocAuditfivemiddle.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocAuditfivemiddle(dcaBDocAuditfivemiddle);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocAuditfivemiddles(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
@DS("slave")
public List<DcaBDocAuditfivemiddle> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBDocAuditfivemiddle> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBDocAuditfivemiddle::getUserAccount, userAccount);
        }

      return  this.baseMapper.selectList(queryWrapper);
        }
        @Override
        @Transactional
        @DS("slave")
        public    void deleteAll(){
                this.baseMapper.deleteAll();
        }
        }