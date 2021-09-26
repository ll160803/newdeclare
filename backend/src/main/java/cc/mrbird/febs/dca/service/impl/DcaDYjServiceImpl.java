package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.dca.entity.DcaDYj;
import cc.mrbird.febs.dca.dao.DcaDYjMapper;
import cc.mrbird.febs.dca.service.IDcaDYjService;
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

import java.util.*;
import java.time.LocalDate;
/**
 * <p>
 * 学术业绩 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-09-23
 */
@Slf4j
@Service("IDcaDYjService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaDYjServiceImpl extends ServiceImpl<DcaDYjMapper, DcaDYj> implements IDcaDYjService {


@Override
public IPage<DcaDYj> findDcaDYjs(QueryRequest request, DcaDYj dcaDYj){
        try{
        LambdaQueryWrapper<DcaDYj> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaDYj::getIsDeletemark, 1);//1是未删 0是已删

       
        if (dcaDYj.getState()!=null) {
        queryWrapper.eq(DcaDYj::getState, dcaDYj.getState());
        }
       /** if (dcaDYj.getAuditState()!=null && (dcaDYj.getAuditState()>=0)) {
        queryWrapper.eq(DcaDYj::getAuditState, dcaDYj.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaDYj.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaDYj.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaDYj::getCreateTime, dcaDYj.getCreateTimeFrom())
                                .le(DcaDYj::getCreateTime, dcaDYj.getCreateTimeTo());
                                }

        Page<DcaDYj> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaDYj> findDcaDYjList (QueryRequest request, DcaDYj dcaDYj){
        try{
        Page<DcaDYj> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaDYj(page,dcaDYj);
        }catch(Exception e){
        log.error("获取学术业绩失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaDYj(DcaDYj dcaDYj){
                dcaDYj.setCreateTime(new Date());
        dcaDYj.setIsDeletemark(1);
        this.save(dcaDYj);
        }

@Override
@Transactional
public void updateDcaDYj(DcaDYj dcaDYj){
        dcaDYj.setModifyTime(new Date());
        this.baseMapper.updateDcaDYj(dcaDYj);
        }

@Override
@Transactional
public void deleteDcaDYjs(String[]Ids){
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
        public Map<String, Object> findDeptsByUserId(String userId,String year){
                Map<String, Object> result = new HashMap<>();
                try {
                        LambdaQueryWrapper<DcaDYj> queryWrapper=new LambdaQueryWrapper<>();
                        queryWrapper.apply("dca_d_yj.id in (select yj_id from dca_user_yj where userId ="+userId+" and dca_year='"+year+"')");
                        queryWrapper.eq(DcaDYj::getIsDeletemark,1);

                        List<DcaDYj> depts = this.baseMapper.selectList(queryWrapper);
                        ;
                        List<Tree<DcaDYj>> trees = new ArrayList<>();
                        buildTrees(trees, depts);
                        Tree<DcaDYj> deptTree = TreeUtil.build(trees);

                        result.put("rows", deptTree);
                        result.put("total", depts.size());
                } catch (Exception e) {
                        log.error("获取部门列表失败", e);
                        result.put("rows", null);
                        result.put("total", 0);
                }
                return result;
        }

        @Override
        public Map<String, Object> findDepts(String dj) {
                Map<String, Object> result = new HashMap<>();
                try {
                        LambdaQueryWrapper<DcaDYj> queryWrapper=new LambdaQueryWrapper<>();
                        queryWrapper.eq(DcaDYj::getIsDeletemark,1);
                        if(dj.equals("二级")) {
                            queryWrapper.eq(DcaDYj::getJb, dj);
                        }
                        List<DcaDYj> depts = this.baseMapper.selectList(queryWrapper);
                        List<Tree<DcaDYj>> trees = new ArrayList<>();
                        buildTrees(trees, depts);
                        Tree<DcaDYj> deptTree = TreeUtil.build(trees);

                        result.put("rows", deptTree);
                        result.put("total", depts.size());
                } catch (Exception e) {
                        log.error("获取部门列表失败", e);
                        result.put("rows", null);
                        result.put("total", 0);
                }
                return result;
        }

        private void buildTrees(List<Tree<DcaDYj>> trees, List<DcaDYj> depts) {
                depts.forEach(dept -> {
                        Tree<DcaDYj> tree = new Tree<>();
                        tree.setId(dept.getId().toString());
                        tree.setKey(tree.getId());
                        tree.setParentId(dept.getParentId().toString());
                        tree.setText(dept.getMuduleName());
                       // tree.setTitle(dept.getJb());
                        tree.setCreateTime(dept.getCreateTime());
                        tree.setModifyTime(dept.getModifyTime());
                        tree.setOrder(Double.valueOf(dept.getId().toString()));
                        tree.setTitle(dept.getMuduleName());
                        tree.setValue(tree.getId());
                        trees.add(tree);
                });
        }
        }