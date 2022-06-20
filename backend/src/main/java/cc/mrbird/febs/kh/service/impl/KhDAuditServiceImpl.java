package cc.mrbird.febs.kh.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.kh.entity.KhDAudit;
import cc.mrbird.febs.kh.dao.KhDAuditMapper;
import cc.mrbird.febs.kh.service.IKhDAuditService;
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
 * 打分人 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
@Slf4j
@Service("IKhDAuditService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KhDAuditServiceImpl extends ServiceImpl<KhDAuditMapper, KhDAudit> implements IKhDAuditService {


@Override
public IPage<KhDAudit> findKhDAudits(QueryRequest request, KhDAudit khDAudit){
        try{
        LambdaQueryWrapper<KhDAudit> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(KhDAudit::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(khDAudit.getAuditUserAccount())) {
                                queryWrapper.like(KhDAudit::getAuditUserAccount, khDAudit.getAuditUserAccount());
                                }
                                if (StringUtils.isNotBlank(khDAudit.getAuditType())) {
                                queryWrapper.like(KhDAudit::getAuditType, khDAudit.getAuditType());
                                }

        Page<KhDAudit> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<KhDAudit> findKhDAuditList (QueryRequest request, KhDAudit khDAudit){
        try{
        Page<KhDAudit> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findKhDAudit(page,khDAudit);
        }catch(Exception e){
        log.error("获取打分人失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createKhDAudit(KhDAudit khDAudit){
                khDAudit.setCreateTime(new Date());
        khDAudit.setIsDeletemark(1);
        this.save(khDAudit);
        }

@Override
@Transactional
public void updateKhDAudit(KhDAudit khDAudit){
        khDAudit.setModifyTime(new Date());
        this.baseMapper.updateKhDAudit(khDAudit);
        }

@Override
@Transactional
public void deleteKhDAudits(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<KhDAudit> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<KhDAudit> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(KhDAudit::getAuditUserAccount, userAccount);
        }

      return  this.baseMapper.selectList(queryWrapper);
        }

        }