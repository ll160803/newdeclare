package cc.mrbird.febs.kh.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.kh.entity.KhBCopyScientificprize;
import cc.mrbird.febs.kh.dao.KhBCopyScientificprizeMapper;
import cc.mrbird.febs.kh.service.IKhBCopyScientificprizeService;
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
 * 自任职以来科研获奖情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-05-13
 */
@Slf4j
@Service("IKhBCopyScientificprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KhBCopyScientificprizeServiceImpl extends ServiceImpl<KhBCopyScientificprizeMapper, KhBCopyScientificprize> implements IKhBCopyScientificprizeService {


@Override
public IPage<KhBCopyScientificprize> findKhBCopyScientificprizes(QueryRequest request, KhBCopyScientificprize khBCopyScientificprize){
        try{
        LambdaQueryWrapper<KhBCopyScientificprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(KhBCopyScientificprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(khBCopyScientificprize.getUserAccount())) {
                                queryWrapper.like(KhBCopyScientificprize::getUserAccount, khBCopyScientificprize.getUserAccount());
                                }

        Page<KhBCopyScientificprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<KhBCopyScientificprize> findKhBCopyScientificprizeList (QueryRequest request, KhBCopyScientificprize khBCopyScientificprize){
        try{
        Page<KhBCopyScientificprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findKhBCopyScientificprize(page,khBCopyScientificprize);
        }catch(Exception e){
        log.error("获取自任职以来科研获奖情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createKhBCopyScientificprize(KhBCopyScientificprize khBCopyScientificprize){
                khBCopyScientificprize.setId(UUID.randomUUID().toString());
        khBCopyScientificprize.setCreateTime(new Date());
        khBCopyScientificprize.setIsDeletemark(1);
        this.save(khBCopyScientificprize);
        }

@Override
@Transactional
public void updateKhBCopyScientificprize(KhBCopyScientificprize khBCopyScientificprize){
        khBCopyScientificprize.setModifyTime(new Date());
        this.baseMapper.updateKhBCopyScientificprize(khBCopyScientificprize);
        }

@Override
@Transactional
public void deleteKhBCopyScientificprizes(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<KhBCopyScientificprize> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<KhBCopyScientificprize> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(KhBCopyScientificprize::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(KhBCopyScientificprize::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }