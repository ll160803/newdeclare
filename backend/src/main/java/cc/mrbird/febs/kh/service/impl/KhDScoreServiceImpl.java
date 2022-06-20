package cc.mrbird.febs.kh.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.kh.entity.KhDScore;
import cc.mrbird.febs.kh.dao.KhDScoreMapper;
import cc.mrbird.febs.kh.entity.KhScore;
import cc.mrbird.febs.kh.entity.KhUser;
import cc.mrbird.febs.kh.service.IKhDScoreService;
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

import java.util.*;
import java.time.LocalDate;
/**
 * <p>
 * 打分记录 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
@Slf4j
@Service("IKhDScoreService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KhDScoreServiceImpl extends ServiceImpl<KhDScoreMapper, KhDScore> implements IKhDScoreService {


@Override
public IPage<KhDScore> findKhDScores(QueryRequest request, KhDScore khDScore){
        try{
        LambdaQueryWrapper<KhDScore> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(KhDScore::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(khDScore.getUserAccount())) {
                                queryWrapper.like(KhDScore::getUserAccount, khDScore.getUserAccount());
                                }
                                if (StringUtils.isNotBlank(khDScore.getYear())) {
                                queryWrapper.like(KhDScore::getYear, khDScore.getYear());
                                }
                                if (StringUtils.isNotBlank(khDScore.getAuditUserAccount())) {
                                queryWrapper.like(KhDScore::getAuditUserAccount, khDScore.getAuditUserAccount());
                                }
                                if (StringUtils.isNotBlank(khDScore.getAuditType())) {
                                queryWrapper.like(KhDScore::getAuditType, khDScore.getAuditType());
                                }

        Page<KhDScore> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<KhDScore> findKhDScoreList (QueryRequest request, KhDScore khDScore){
        try{
        Page<KhDScore> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findKhDScore(page,khDScore);
        }catch(Exception e){
        log.error("获取打分记录失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createKhDScore(KhDScore khDScore){
                khDScore.setCreateTime(new Date());
        khDScore.setIsDeletemark(1);
        this.save(khDScore);
        }

@Override
@Transactional
public void updateKhDScore(KhDScore khDScore){
        khDScore.setModifyTime(new Date());
        this.baseMapper.updateKhDScore(khDScore);
        }

@Override
@Transactional
public void deleteKhDScores(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<KhDScore> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<KhDScore> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(KhDScore::getUserAccount, userAccount);
        }

      return  this.baseMapper.selectList(queryWrapper);
        }
    @Override
    @Transactional
    public  void insert(Map<String, Object> map){
      this.baseMapper.insertKhCopy(map);
    }

    @Override
    @Transactional
    public IPage<KhUser> findAllUserInfo(QueryRequest request, KhDScore khDScore) {
        try {
            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.getAllUserInfo(page, khDScore);
        } catch (Exception e) {
            log.error("获取重要岗位任职及学术影响失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public IPage<KhUser> getUserInfoReport(QueryRequest request, KhDScore khDScore){
        try {
            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<KhUser> list= this.baseMapper.getUserInfoReport(page, khDScore);
            List<KhScore> scoreList = this.baseMapper.getUserInfoReportScore(khDScore);
            List<KhUser> records= list.getRecords();

            records.parallelStream().forEach(khUser -> {
                double score= scoreList.stream().filter(p->p.getUserAccount().equals(khUser.getUserAccount())).mapToDouble(p->p.getScore()).sum();
                khUser.setScore(score);
            });

            return  list;
        } catch (Exception e) {
            log.error("获取数据失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public  List<KhScore> getUserInfoReportScore(KhDScore khDScore){
       return  this.baseMapper.getUserInfoReportScore(khDScore);
    }
}