package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBLetter;
import cc.mrbird.febs.dca.dao.DcaBLetterMapper;
import cc.mrbird.febs.dca.service.IDcaBLetterService;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 通讯评审 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-07-26
 */
@Slf4j
@Service("IDcaBLetterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBLetterServiceImpl extends ServiceImpl<DcaBLetterMapper, DcaBLetter> implements IDcaBLetterService {

        @Autowired
        IDcaBUserapplyService iDcaBUserapplyService;
@Override
public IPage<DcaBLetter> findDcaBLetters(QueryRequest request, DcaBLetter dcaBLetter){
        try{
        LambdaQueryWrapper<DcaBLetter> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBLetter::getIsDeletemark, 1);//1是未删 0是已删
                if (StringUtils.isNotBlank(dcaBLetter.getUserAccount())) {
                        queryWrapper.and(wrap -> wrap.eq(DcaBLetter::getUserAccount, dcaBLetter.getUserAccount()).or()
                                .like(DcaBLetter::getUserAccountName, dcaBLetter.getUserAccount()));

                }
                if (StringUtils.isNotBlank(dcaBLetter.getAuditManName())) {// 年度 和高级、中级、初级
                        List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBLetter.getAuditMan(), dcaBLetter.getAuditManName());
                        if (userAccountsList.size() == 0) {
                                userAccountsList.add("qiuc09");
                        }
                        queryWrapper.in(DcaBLetter::getUserAccount, userAccountsList);
                }
                if (dcaBLetter.getState() != null) {
                        queryWrapper.eq(DcaBLetter::getState, dcaBLetter.getState());
                }

        Page<DcaBLetter> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBLetter> findDcaBLetterList (QueryRequest request, DcaBLetter dcaBLetter){
        try{
        Page<DcaBLetter> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBLetter(page,dcaBLetter);
        }catch(Exception e){
        log.error("获取通讯评审失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBLetter(DcaBLetter dcaBLetter){
                dcaBLetter.setId(UUID.randomUUID().toString());
        dcaBLetter.setCreateTime(new Date());
        dcaBLetter.setIsDeletemark(1);
        this.save(dcaBLetter);
        }

@Override
@Transactional
public void updateDcaBLetter(DcaBLetter dcaBLetter){
        dcaBLetter.setModifyTime(new Date());
        this.baseMapper.updateDcaBLetter(dcaBLetter);
        }

@Override
@Transactional
public void deleteDcaBLetters(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBLetter> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBLetter> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBLetter::getUserAccount, userAccount);
        }

      return  this.baseMapper.selectList(queryWrapper);
        }

        }