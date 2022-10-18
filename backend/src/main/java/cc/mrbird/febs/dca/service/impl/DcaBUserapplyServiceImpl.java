package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;
import cc.mrbird.febs.dca.entity.DcaBReport;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.dca.entity.DcaBUserapply;
import cc.mrbird.febs.dca.dao.DcaBUserapplyMapper;
import cc.mrbird.febs.dca.service.IDcaBAuditdynamicService;
import cc.mrbird.febs.dca.service.IDcaBReportService;
import cc.mrbird.febs.dca.service.IDcaBUserService;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditdynamic;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-05
 */
@Slf4j
@Service("IDcaBUserapplyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBUserapplyServiceImpl extends ServiceImpl<DcaBUserapplyMapper, DcaBUserapply> implements IDcaBUserapplyService {

    @Autowired
    private IDcaBUserService dcaBUserService;

    @Autowired
    private IDcaBAuditdynamicService dcaBAuditdynamicService;

    @Autowired
    private IDcaBReportService dcaBReportService;

    @Override
    public IPage<DcaBUserapply> findDcaBUserapplys(QueryRequest request, DcaBUserapply dcaBUserapply) {
        try {
            LambdaQueryWrapper<DcaBUserapply> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUserapply::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUserapply.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUserapply::getUserAccount, dcaBUserapply.getUserAccount()).or()
                        .like(DcaBUserapply::getUserAccountName, dcaBUserapply.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBUserapply.getDcaYear())) {
                queryWrapper.eq(DcaBUserapply::getDcaYear, dcaBUserapply.getDcaYear());
            }
            if (dcaBUserapply.getState() != null && dcaBUserapply.getState() > 0) {
                queryWrapper.eq(DcaBUserapply::getState, dcaBUserapply.getState());
            }
            if (StringUtils.isNotBlank(dcaBUserapply.getGwdj())) {
                //queryWrapper.like(DcaBUserapply::getNpPositionName, dcaBUserapply.getNpPositionName());
                String[] gwdjList = dcaBUserapply.getGwdj().split(",");
                queryWrapper.in(DcaBUserapply::getGwdj, gwdjList);
            }

            Page<DcaBUserapply> page = new Page<>();

            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<DcaBUserapply> result = this.page(page, queryWrapper);

            List<DcaBReport> dcaBReportList =dcaBReportService.getReportForResult();
            for (DcaBUserapply item : result.getRecords()
            ) {
                List<DcaBReport> reports =dcaBReportList.stream().filter(p->p.getYear().equals(item.getDcaYear())&& p.getUserAccount().equals(item.getUserAccount())).collect(Collectors.toList());
                if(reports.size()>0){
                    String info ="";
                    String ntyy="";
                    for (DcaBReport r: reports
                         ) {
                        if(StringUtils.isNotBlank(r.getClshjg())){
                            info+=r.getNpPositionName()+":"+r.getClshjg()+"。";
                        }
                        if(StringUtils.isNotBlank(r.getNtyy())){
                            ntyy+=r.getNpPositionName()+":"+r.getNtyy()+"。";
                        }
                       // info+= r.getNpPositionName()+":"+(r.getClshjg()==null?"":r.getClshjg())+","+(r.getNtyy()==null?"":r.getNtyy())+";";
                    }
                   item.setInf(info);
                   item.setNtyy(ntyy);
                }
            }
            return  result;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaBUserapply> findDcaBUserapplyList(QueryRequest request, DcaBUserapply dcaBUserapply) {
        try {
            Page<DcaBUserapply> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBUserapply(page, dcaBUserapply);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBUserapply(DcaBUserapply dcaBUserapply) {
        dcaBUserapply.setId(UUID.randomUUID().toString());
        dcaBUserapply.setCreateTime(new Date());
        dcaBUserapply.setIsDeletemark(1);
        LambdaQueryWrapper<DcaBUser> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(DcaBUser::getUserAccount, dcaBUserapply.getUserAccount());
        DcaBUser user = this.dcaBUserService.getOne(queryWrapper2);
        user.setNpPositionName(dcaBUserapply.getNpPositionName());
        user.setDcaYear(dcaBUserapply.getDcaYear());
        dcaBUserapply.setUserAccountName(user.getUserAccountName());
        dcaBUserapply.setAppointedDate(user.getZygwDate());
        dcaBUserapply.setPositionName(user.getPositionName() == null ? "" : user.getPositionName());
        dcaBUserapply.setBirthday(user.getBirthday());
        dcaBUserapply.setKs(user.getKs());
        dcaBUserapply.setSchoolDate(user.getSchoolDate());
        dcaBUserapply.setSexName(user.getSexName());
        dcaBUserapply.setTelephone(user.getTelephone());
        dcaBUserapply.setXl(user.getXl());
        dcaBUserapply.setXcszyjzc(user.getXcszyjzc());
        dcaBUserapply.setZyjsgw(user.getPositionName());
        dcaBUserapply.setState(1);
        this.save(dcaBUserapply);
        this.dcaBUserService.updateDcaBUser(user);// 更改 申报职位
    }

    @Override
    @Transactional
    public void updateDcaBUserapply(DcaBUserapply dcaBUserapply) {
        dcaBUserapply.setModifyTime(new Date());
        dcaBUserapply.setState(1);
        this.baseMapper.updateDcaBUserapply(dcaBUserapply);

        LambdaQueryWrapper<DcaBUser> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(DcaBUser::getUserAccount, dcaBUserapply.getUserAccount());
        DcaBUser user = this.dcaBUserService.getOne(queryWrapper2);
        user.setNpPositionName(dcaBUserapply.getNpPositionName());
        user.setDcaYear(dcaBUserapply.getDcaYear());
        this.dcaBUserService.updateDcaBUser(user);// 更改 申报职位
    }

    @Override
    @Transactional
    public void updateDcaBUserapplyState(DcaBUserapply dcaBUserapply) {

        LambdaQueryWrapper<DcaBUser> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(DcaBUser::getUserAccount, dcaBUserapply.getUserAccount());
        DcaBUser user = this.dcaBUserService.getOne(queryWrapper2);
        user.setNpPositionName("");

        this.baseMapper.updateDcaBUserapply(dcaBUserapply);
        ;// 更改 申报职位
        this.dcaBUserService.updateDcaBUser(user);// 更改 申报职位
    }

    @Override
    @Transactional
    public void deleteDcaBUserapplys(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public boolean IsExistApply(DcaBUserapply dcaBUserapply) {
        LambdaQueryWrapper<DcaBUserapply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBUserapply::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.eq(DcaBUserapply::getDcaYear, dcaBUserapply.getDcaYear());
      //  queryWrapper.eq(DcaBUserapply::getGwdj, dcaBUserapply.getGwdj());
        queryWrapper.eq(DcaBUserapply::getUserAccount, dcaBUserapply.getUserAccount());
        if (dcaBUserapply.getId() != null) {
            queryWrapper.ne(DcaBUserapply::getId, dcaBUserapply.getId());
        }
        int count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public List<String> getApplyAccount(String dcaYear, String type) {
        if (type.equals("高级")) {
            return this.baseMapper.GetGj(dcaYear);
        }
        if (type.equals("中级")) {
            return this.baseMapper.GetZj(dcaYear);
        }
        if (type.equals("初级")) {
            return this.baseMapper.GetDj(dcaYear);
        }
        return new ArrayList<String>();
    }

    @Override
    @Transactional
    public IPage<DcaBUserapply> findDcaBUserapplyAudit(QueryRequest request, DcaBUserapply dcaBUserapply) {
        try {
            LambdaQueryWrapper<DcaBUserapply> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUserapply::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUserapply.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUserapply::getUserAccount, dcaBUserapply.getUserAccount()).or()
                        .like(DcaBUserapply::getUserAccountName, dcaBUserapply.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBUserapply.getDcaYear())) {
                queryWrapper.eq(DcaBUserapply::getDcaYear, dcaBUserapply.getDcaYear());
            }
            if (StringUtils.isNotBlank(dcaBUserapply.getGwdj())) {
                if(dcaBUserapply.getGwdj().equals("高级")){
                    queryWrapper.in(DcaBUserapply::getGwdj,"正高,副高".split(","));
                }
                else if(dcaBUserapply.getGwdj().equals("中级")){
                    queryWrapper.in(DcaBUserapply::getGwdj,"中级,初级".split(","));
                }
                else if(dcaBUserapply.getGwdj().equals("初级")){
                    queryWrapper.in(DcaBUserapply::getGwdj,"二三级".split(","));
                }else {
                    queryWrapper.eq(DcaBUserapply::getGwdj, dcaBUserapply.getGwdj());
                }
            }
            queryWrapper.eq(DcaBUserapply::getState, 1);
            Page<DcaBUserapply> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<DcaBUserapply> pageResults = this.page(page, queryWrapper);
            if (pageResults.getRecords().size() > 0) {

                DcaBCopyAuditdynamic dcaBCopyAuditdynamic = new DcaBCopyAuditdynamic();
                dcaBCopyAuditdynamic.setDcaYear(dcaBUserapply.getDcaYear());
                dcaBCopyAuditdynamic.setUserAccount(dcaBUserapply.getUserAccount());
                List<String> listDynamic = pageResults.getRecords().stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
                dcaBCopyAuditdynamic.setUserAccountList(listDynamic);
                List<DcaBCopyAuditdynamic> auditdynamicList = dcaBAuditdynamicService.findDcaBCopyAuditdynamicList(dcaBCopyAuditdynamic);

                pageResults.getRecords().stream().parallel().forEach(dcaBUserapply1->{
                    List<DcaBCopyAuditdynamic> listDy = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(dcaBUserapply1.getUserAccount())&& p.getDcaYear().equals(dcaBUserapply1.getDcaYear())).collect(Collectors.toList());
                    dcaBUserapply1.setDcaBAuditdynamicList(listDy);
                });
//                for (DcaBUserapply dcaBUserapply1: pageResults.getRecords()
//                     ) {
//                    List<DcaBCopyAuditdynamic> listDy = auditdynamicList.stream().filter(p -> p.getUserAccount().equals(dcaBUserapply1.getUserAccount())&& p.getDcaYear().equals(dcaBUserapply1.getDcaYear())).collect(Collectors.toList());
//                    dcaBUserapply1.setDcaBAuditdynamicList(listDy);
//                }

            }
            return pageResults;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public IPage<DcaBUserapply> findDcaBUsersAuditResult(QueryRequest request, DcaBUserapply dcaBUser) {
        try {
            LambdaQueryWrapper<DcaBUserapply> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUserapply::getIsDeletemark, 1);//1是未删 0是已删
            queryWrapper.eq(DcaBUserapply::getState, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUserapply::getUserAccount, dcaBUser.getUserAccount()).or()
                        .like(DcaBUserapply::getUserAccountName, dcaBUser.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBUser.getDcaYear())) {
                queryWrapper.eq(DcaBUserapply::getDcaYear,dcaBUser.getDcaYear());
            }
            if (StringUtils.isNotBlank(dcaBUser.getGwdj())) {
                //queryWrapper.like(DcaBUserapply::getNpPositionName, dcaBUserapply.getNpPositionName());
                String[] gwdjList = dcaBUser.getGwdj().split(",");
                queryWrapper.in(DcaBUserapply::getGwdj, gwdjList);
            }
            Page<DcaBUserapply> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            IPage<DcaBUserapply> listResult = this.page(page, queryWrapper);


            if (listResult.getRecords().size() > 0) {
                List<String> listDynamic = listResult.getRecords().stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
                if (listDynamic.size() > 0) {
                    List<DcaBAuditdynamic> auditdynamicList =this.dcaBUserService.getAllInfo(listDynamic);

                    if (auditdynamicList.size() > 0) {
                        List<DcaBUserapply> userList=listResult.getRecords();
                        userList.parallelStream().forEach(user->{
                            List<DcaBAuditdynamic> listDy = auditdynamicList.stream().filter(Objects::nonNull).filter(p -> p.getUserAccount()!=null && p.getUserAccount().equals(user.getUserAccount())).collect(Collectors.toList());
                            user.setDcaBAuditdynamics(listDy);
                        });
                    }
                }
            }

            return listResult;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }


    }
    @Override
    @Transactional
    public   int countAccount(String userAccount,String gwdj,String dcaYear){
              return  this.baseMapper.countAccount(userAccount,gwdj,dcaYear);
    }
}