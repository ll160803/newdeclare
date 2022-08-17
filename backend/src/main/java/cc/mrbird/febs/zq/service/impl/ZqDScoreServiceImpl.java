package cc.mrbird.febs.zq.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.zq.entity.ZqDScore;
import cc.mrbird.febs.zq.dao.ZqDScoreMapper;
import cc.mrbird.febs.zq.entity.ZqUser;
import cc.mrbird.febs.zq.service.IZqDScoreService;
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
 * @since 2022-06-24
 */
@Slf4j
@Service("IZqDScoreService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ZqDScoreServiceImpl extends ServiceImpl<ZqDScoreMapper, ZqDScore> implements IZqDScoreService {


    @Override
    public IPage<ZqDScore> findZqDScores(QueryRequest request, ZqDScore zqDScore) {
        try {
            LambdaQueryWrapper<ZqDScore> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ZqDScore::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(zqDScore.getUserAccount())) {
                queryWrapper.like(ZqDScore::getUserAccount, zqDScore.getUserAccount());
            }
            if (StringUtils.isNotBlank(zqDScore.getDeptName())) {
                queryWrapper.like(ZqDScore::getDeptName, zqDScore.getDeptName());
            }
            if (StringUtils.isNotBlank(zqDScore.getAuditUserAccount())) {
                queryWrapper.like(ZqDScore::getAuditUserAccount, zqDScore.getAuditUserAccount());
            }

            Page<ZqDScore> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ZqDScore> findZqDScoreList(QueryRequest request, ZqDScore zqDScore) {
        try {
            Page<ZqDScore> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findZqDScore(page, zqDScore);
        } catch (Exception e) {
            log.error("获取打分记录失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createZqDScore(ZqDScore zqDScore) {
        zqDScore.setCreateTime(new Date());
        zqDScore.setIsDeletemark(1);
        this.save(zqDScore);
    }

    @Override
    @Transactional
    public void updateZqDScore(ZqDScore zqDScore) {
        zqDScore.setModifyTime(new Date());
        this.baseMapper.updateZqDScore(zqDScore);
    }

    @Override
    @Transactional
    public void insert(Map<String, Object> map) {
        this.baseMapper.insertZqCopy(map);
    }

    @Override
    @Transactional
    public void deleteZqDScores(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public List<ZqDScore> getAll(String userAccount, String dcaYear) {
        LambdaQueryWrapper<ZqDScore> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.eq(ZqDScore::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
            queryWrapper.eq(ZqDScore::getYear, dcaYear);
        }
        return this.baseMapper.selectList(queryWrapper);
    }
    @Override
    @Transactional
    public IPage<ZqUser> findAllUserInfo(QueryRequest request, ZqDScore khDScore) {
        try {
            Page<DcaBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.getAllUserInfo(page, khDScore);
        } catch (Exception e) {
            log.error("获取重要岗位任职及学术影响失败", e);
            return null;
        }
    }
}