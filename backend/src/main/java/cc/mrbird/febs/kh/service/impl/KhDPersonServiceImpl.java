package cc.mrbird.febs.kh.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.kh.entity.KhDPerson;
import cc.mrbird.febs.kh.dao.KhDPersonMapper;
import cc.mrbird.febs.kh.service.IKhDPersonService;
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
 * 被打分人 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
@Slf4j
@Service("IKhDPersonService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KhDPersonServiceImpl extends ServiceImpl<KhDPersonMapper, KhDPerson> implements IKhDPersonService {


@Override
public IPage<KhDPerson> findKhDPersons(QueryRequest request, KhDPerson khDPerson){
        try{
        LambdaQueryWrapper<KhDPerson> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(KhDPerson::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(khDPerson.getUserAccount())) {
                                queryWrapper.like(KhDPerson::getUserAccount, khDPerson.getUserAccount());
                                }
                                if (StringUtils.isNotBlank(khDPerson.getDeptName())) {
                                queryWrapper.like(KhDPerson::getDeptName, khDPerson.getDeptName());
                                }

        Page<KhDPerson> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<KhDPerson> findKhDPersonList (QueryRequest request, KhDPerson khDPerson){
        try{
        Page<KhDPerson> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findKhDPerson(page,khDPerson);
        }catch(Exception e){
        log.error("获取被打分人失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createKhDPerson(KhDPerson khDPerson){
                khDPerson.setCreateTime(new Date());
        khDPerson.setIsDeletemark(1);
        this.save(khDPerson);
        }

@Override
@Transactional
public void updateKhDPerson(KhDPerson khDPerson){
        khDPerson.setModifyTime(new Date());
        this.baseMapper.updateKhDPerson(khDPerson);
        }

@Override
@Transactional
public void deleteKhDPersons(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<KhDPerson> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<KhDPerson> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(KhDPerson::getUserAccount, userAccount);
        }

      return  this.baseMapper.selectList(queryWrapper);
        }

        }