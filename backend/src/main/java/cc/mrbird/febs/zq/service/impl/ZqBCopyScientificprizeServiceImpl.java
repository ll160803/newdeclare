package cc.mrbird.febs.zq.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.zq.entity.ZqBCopyScientificprize;
import cc.mrbird.febs.zq.dao.ZqBCopyScientificprizeMapper;
import cc.mrbird.febs.zq.service.IZqBCopyScientificprizeService;
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
 * @since 2022-06-24
 */
@Slf4j
@Service("IZqBCopyScientificprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZqBCopyScientificprizeServiceImpl extends ServiceImpl<ZqBCopyScientificprizeMapper, ZqBCopyScientificprize> implements IZqBCopyScientificprizeService {


@Override
public IPage<ZqBCopyScientificprize> findZqBCopyScientificprizes(QueryRequest request, ZqBCopyScientificprize zqBCopyScientificprize){
        try{
        LambdaQueryWrapper<ZqBCopyScientificprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ZqBCopyScientificprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(zqBCopyScientificprize.getUserAccount())) {
                                queryWrapper.like(ZqBCopyScientificprize::getUserAccount, zqBCopyScientificprize.getUserAccount());
                                }

        Page<ZqBCopyScientificprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ZqBCopyScientificprize> findZqBCopyScientificprizeList (QueryRequest request, ZqBCopyScientificprize zqBCopyScientificprize){
        try{
        Page<ZqBCopyScientificprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findZqBCopyScientificprize(page,zqBCopyScientificprize);
        }catch(Exception e){
        log.error("获取自任职以来科研获奖情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createZqBCopyScientificprize(ZqBCopyScientificprize zqBCopyScientificprize){
                zqBCopyScientificprize.setId(UUID.randomUUID().toString());
        zqBCopyScientificprize.setCreateTime(new Date());
        zqBCopyScientificprize.setIsDeletemark(1);
        this.save(zqBCopyScientificprize);
        }

@Override
@Transactional
public void updateZqBCopyScientificprize(ZqBCopyScientificprize zqBCopyScientificprize){
        zqBCopyScientificprize.setModifyTime(new Date());
        this.baseMapper.updateZqBCopyScientificprize(zqBCopyScientificprize);
        }

@Override
@Transactional
public void deleteZqBCopyScientificprizes(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<ZqBCopyScientificprize> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<ZqBCopyScientificprize> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(ZqBCopyScientificprize::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(ZqBCopyScientificprize::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }