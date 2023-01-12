package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;
import cc.mrbird.febs.dca.entity.DcaBReport;
import cc.mrbird.febs.dca.dao.DcaBReportMapper;
import cc.mrbird.febs.dca.entity.DcaDYearsetting;
import cc.mrbird.febs.dca.service.IDcaBAuditdynamicService;
import cc.mrbird.febs.dca.service.IDcaBReportService;
import cc.mrbird.febs.dca.service.IDcaDYearsettingService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditdynamic;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyAuditdynamicService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-13
 */
@Slf4j
@Service("IDcaBReportService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBReportServiceImpl extends ServiceImpl<DcaBReportMapper, DcaBReport> implements IDcaBReportService {

    @Autowired
    public IDcaBCopyAuditdynamicService iDcaBCopyAuditdynamicService;

    @Autowired
    public IDcaBAuditdynamicService iDcaBAuditdynamicService;

    @Autowired
    private IDcaDYearsettingService iDcaDYearsettingService;

@Override
public IPage<DcaBReport> findDcaBReports(QueryRequest request, DcaBReport dcaBReport){
        try{
        LambdaQueryWrapper<DcaBReport> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBReport::getIsDeletemark, 1);//1是未删 0是已删


                if (StringUtils.isNotBlank(dcaBReport.getUserAccount())) {
                        queryWrapper.and(wrap->  wrap.eq(DcaBReport::getUserAccount, dcaBReport.getUserAccount()).or()
                                .like(DcaBReport::getUserAccountName, dcaBReport.getUserAccount()));

                }
               if (dcaBReport.getState()!=null) {
                        queryWrapper.eq(DcaBReport::getState, dcaBReport.getState());
                }
               if(StringUtils.isNotBlank(dcaBReport.getIsSingel())){
                       queryWrapper.in(DcaBReport::getState, CollUtil.toList(1,2));
               }
                if (StringUtils.isNotBlank(dcaBReport.getYear())) {
                        queryWrapper.eq(DcaBReport::getYear, dcaBReport.getYear().trim());
                }
            if (StringUtils.isNotBlank(dcaBReport.getKs())) {
                String[] listKs=dcaBReport.getKs().split(",");
                queryWrapper.in(DcaBReport::getGwdj,listKs);
            }
//            if(dcaBReport.getIsUse()!=null&&dcaBReport.getIsUse()){ //排版系统 行风建设获取数据使用
//                queryWrapper.and(lam->lam.eq(DcaBReport::getClshjg,"正常").or().eq(DcaBReport::getClshjg,"合格"));
//            }

                Page<DcaBReport> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
            IPage<DcaBReport> result =this.page(page,queryWrapper);
            List<DcaBReport> list =result.getRecords();
            String[] arr= {"ylpfbfz","ylpfdj","ydyf","ydyffj","zzsc","zzscypfj","jlsc","xsddsc","xsddscypfj","yyxtsc","sfssds","sfbsds","sftgsdsf",
                    "sdsfypfj","sdsfypfj2","ynjbzr","j5njxgz","mzylpf","mzylpfdj","mzylsgypfj","jlscypfj","yyxtypfj","sfdlwcyjspy","pyzlsfyl"
                    ,"sfypfjyl","hlylpf","hlylpfdj","hljxpfbfz","hljxpfdl","hlhlzrypfj","sshbdts","sshkyxts","blxwjf","wfzgszcf"
                    ,"zypfyjxl","zypfdjyjxl","zypfbfz58","zypfdj59","sfyszgzs","sfjyhlzgzs","xingfscsftg","sfczxfypfj61","zypf52","zypfdj52","beizhumenban","beizhuhuli","beizhuyiwuchu"};

          if(dcaBReport.getState()!=null && dcaBReport.getState()==2) {
              LambdaQueryWrapper<DcaBCopyAuditdynamic> ql = new LambdaQueryWrapper<>();
              ql.eq(DcaBCopyAuditdynamic::getIsDeletemark, 1);
              ql.in(DcaBCopyAuditdynamic::getAuditTitletype, arr);
              //动态评分表
              List<DcaBCopyAuditdynamic> auditdynamicAuditList = this.iDcaBCopyAuditdynamicService.list(ql);

              List<DcaBAuditdynamic> auditdynamicAuditList2 = new ArrayList<>();
              for (DcaBCopyAuditdynamic item : auditdynamicAuditList
              ) {
                  DcaBAuditdynamic fy = new DcaBAuditdynamic();
                  fy.setUserAccount(item.getUserAccount());
                  if(item.getAuditTitletype().equals("ylpfbfz") ||item.getAuditTitletype().equals("ylpfdj")){
                      fy.setAuditTitletype(item.getAuditTitletype()+"2022");
                  }
                  else {
                      fy.setAuditTitletype(item.getAuditTitletype());
                  }
                  fy.setDcaYear(item.getDcaYear());
                  fy.setGwdj(item.getGwdj());
                  fy.setAuditTitle(item.getAuditTitle());
                  fy.setAuditResult(item.getAuditResult());
                  fy.setId(item.getId());
                  auditdynamicAuditList2.add(fy);
              }
              List<DcaDYearsetting> dcaDYearsettingList= this.iDcaDYearsettingService.list();

              for (DcaBReport dca : list
              ) {
                  List<DcaBAuditdynamic> dcaBAuditds = auditdynamicAuditList2.stream().filter(p -> p.getUserAccount().equals(dca.getUserAccount())
                          &&p.getGwdj().equals(dca.getGwdj()) && p.getDcaYear().equals(dca.getYear())).collect(Collectors.toList());

                  dca.setDcaBAuditdynamicList(dcaBAuditds);
                  if(dca.getGwdj()!=null&&dca.getGwdj().equals("正高")||dca.getGwdj().equals("副高")) {
                     long cu= dcaDYearsettingList.stream().filter(p -> p.getDcaYear().equals(dca.getYear()) && p.getGwdj().equals("高级")).count();
                     if(cu>0){
                         dca.setShowState(1);
                     }
                     else{
                         dca.setShowState(0);
                     }
                  }
                  if(dca.getGwdj()!=null&&dca.getGwdj().equals("中级")||dca.getGwdj().equals("初级")) {
                      long cu= dcaDYearsettingList.stream().filter(p -> p.getDcaYear().equals(dca.getYear()) && p.getGwdj().equals("中级")).count();
                      if(cu>0){
                          dca.setShowState(1);
                      }
                      else{
                          dca.setShowState(0);
                      }
                  }
                  if(dca.getGwdj()!=null&&dca.getGwdj().equals("二三级")) {
                      long cu= dcaDYearsettingList.stream().filter(p -> p.getDcaYear().equals(dca.getYear()) && p.getGwdj().equals("初级")).count();
                      if(cu>0){
                          dca.setShowState(1);
                      }
                      else{
                          dca.setShowState(0);
                      }
                  }
              }
          }

            if(dcaBReport.getState()!=null && dcaBReport.getState()==1) {
                LambdaQueryWrapper<DcaBAuditdynamic> ql = new LambdaQueryWrapper<>();
                ql.eq(DcaBAuditdynamic::getIsDeletemark, 1);
                ql.in(DcaBAuditdynamic::getAuditTitletype, arr);
                //动态评分表
                List<DcaBAuditdynamic> auditdynamicAuditList = this.iDcaBAuditdynamicService.list(ql);

                for (DcaBReport dca : list
                ) {
                    List<DcaBAuditdynamic> dcaBAuditds = auditdynamicAuditList.stream().filter(p -> p.getUserAccount().equals(dca.getUserAccount())).collect(Collectors.toList());

                    dca.setDcaBAuditdynamicList(dcaBAuditds);
                }
            }

            return result;
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBReport> findDcaBReportList (QueryRequest request, DcaBReport dcaBReport){
        try{
        Page<DcaBReport> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBReport(page,dcaBReport);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBReport(DcaBReport dcaBReport){
                dcaBReport.setId(UUID.randomUUID().toString());
        dcaBReport.setCreateTime(new Date());
        dcaBReport.setIsDeletemark(1);
        this.save(dcaBReport);
        }

@Override
    @Transactional
    public void updateDcaBReport(DcaBReport dcaBReport){
        dcaBReport.setModifyTime(new Date());
        this.baseMapper.updateDcaBReport(dcaBReport);
        if(dcaBReport.getState().equals(2)){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userAccount", dcaBReport.getUserAccount());
            map.put("dcaYear", dcaBReport.getYear());
            map.put("gw_dj2", dcaBReport.getGwdj());
            this.baseMapper.insertCopy(map);
        }
    }
    @Override
    @Transactional
    public void updateShuangBaoDcaBReport(DcaBReport dcaBReport){

        LambdaQueryWrapper<DcaBReport> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBReport::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.eq(DcaBReport::getUserAccount, dcaBReport.getUserAccount());
        queryWrapper.eq(DcaBReport::getYear, dcaBReport.getYear());
        List<DcaBReport> list= this.list(queryWrapper);
        if(list.size()>0) {
            List<DcaBReport> listShuangbao = list.stream().filter(p ->p.getClshjg()!=null&& p.getClshjg().equals("拟退")).collect(Collectors.toList());
            if (listShuangbao.size() > 0) {
                this.baseMapper.UpdateShuangBao(listShuangbao.get(0).getUserAccount(), listShuangbao.get(0).getYear());
            }
        }
}
@Override
@Transactional
public void deleteDcaBReports(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        if(list.size()>0) {
            this.baseMapper.mutiUpdate(list);
        }
        }
    @Override
    @Transactional
    public  List<DcaBReport> getReportTest(){
       return  this.baseMapper.getReportTest();
    }

    @Override
    @Transactional
    public  List<DcaBReport> getReportForResult(){
            return  this.baseMapper.getReportForResult();
    }
    @Override
    @Transactional

    public List<DcaBReport> getAll(String userAccount,String year,String zc){
        return  this.baseMapper.getAll(userAccount,year,zc);
    }
        }