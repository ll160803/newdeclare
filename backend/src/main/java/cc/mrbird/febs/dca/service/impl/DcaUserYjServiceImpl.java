package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaUserYj;
import cc.mrbird.febs.dca.dao.DcaUserYjMapper;
import cc.mrbird.febs.dca.service.IDcaUserYjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 * 学术业绩用户表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-09-23
 */
@Slf4j
@Service("IDcaUserYjService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaUserYjServiceImpl extends ServiceImpl<DcaUserYjMapper, DcaUserYj> implements IDcaUserYjService {


@Override
public IPage<DcaUserYj> findDcaUserYjs(QueryRequest request, DcaUserYj dcaUserYj){
        try{
        LambdaQueryWrapper<DcaUserYj> queryWrapper=new LambdaQueryWrapper<>();

       /** if (dcaUserYj.getAuditState()!=null && (dcaUserYj.getAuditState()>=0)) {
        queryWrapper.eq(DcaUserYj::getAuditState, dcaUserYj.getAuditState());
        }*/

        Page<DcaUserYj> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaUserYj> findDcaUserYjList (QueryRequest request, DcaUserYj dcaUserYj){
        try{
        Page<DcaUserYj> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaUserYj(page,dcaUserYj);
        }catch(Exception e){
        log.error("获取学术业绩用户表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaUserYj(DcaUserYj dcaUserYj){

        this.save(dcaUserYj);
        }

@Override
@Transactional
public void updateDcaUserYj(DcaUserYj dcaUserYj){


        this.baseMapper.updateDcaUserYj(dcaUserYj);
        }

@Override
@Transactional
public void deleteDcaUserYjs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }

        @Override
        @Transactional
        public List<DcaUserYj> getMudulesByUserId(String userId,String year,String dj){
                LambdaQueryWrapper<DcaUserYj> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(DcaUserYj::getUserId,userId);
                queryWrapper.eq(DcaUserYj::getDcaYear,year);
                if(dj.equals("二级")) {
                   queryWrapper.apply("yj_id in (select id from dca_d_yj where jb='二级')");
                }
                return this.baseMapper.selectList(queryWrapper);
        }
        @Override
        @Transactional
        public void deleteByuserid(String userId,String year){
             this.baseMapper.deleteByuserid(userId,year);
        }
        }