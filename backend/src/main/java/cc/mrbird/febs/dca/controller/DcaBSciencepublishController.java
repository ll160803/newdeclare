package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.entity.DcaBSciencepublish_Import;
import cc.mrbird.febs.dca.entity.DcaBUserapply;
import cc.mrbird.febs.dca.entity.DcaDJb;
import cc.mrbird.febs.dca.service.IDcaBSciencepublishService;
import cc.mrbird.febs.dca.entity.DcaBSciencepublish;

import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import cc.mrbird.febs.dca.service.IDcaDJbService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencepublish;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2020-10-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBSciencepublish")

public class DcaBSciencepublishController extends BaseController{

private String message;
@Autowired
public IDcaBSciencepublishService iDcaBSciencepublishService;

@Autowired
private IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private IDcaDJbService iDcaDJbService;

/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBSciencepublish 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBSciencepublish dcaBSciencepublish){
        return getDataTable(this.iDcaBSciencepublishService.findDcaBSciencepublishs(request, dcaBSciencepublish));
        }

        @GetMapping("jbLb")
        public List<DcaDJb> ListJb(){
              return  this.iDcaDJbService.list();
        }

@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBSciencepublish dcaBSciencepublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSciencepublish.setUserAccount(currentUser.getUsername());
    dcaBSciencepublish.setIsDeletemark(1);
        //request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBSciencepublishService.findDcaBSciencepublishs(request, dcaBSciencepublish));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBSciencepublish dcaBSciencepublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSciencepublish.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        if(dcaBSciencepublish.getState()!=null && dcaBSciencepublish.getState().equals(9)){
        return getDataTable(this.iDcaBSciencepublishService.findDcaBCopySciencepublishs(request, dcaBSciencepublish));
    }
    else {
        return getDataTable(this.iDcaBSciencepublishService.findDcaBSciencepublishs(request, dcaBSciencepublish));
    }

        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBSciencepublishCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBSciencepublish> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBSciencepublish>>(){
        });
          /**  LambdaQueryWrapper<DcaBUserapply> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUserapply::getUserAccount,currentUser.getUsername());
            String np="";
           List<DcaBUserapply> listUser= iDcaBUserapplyService.list(queryWrapper);
            if(listUser.size()>0){
            listUser =listUser.stream().sorted(new Comparator<DcaBUserapply>() {
                @Override
                public int compare(DcaBUserapply o1, DcaBUserapply o2) {
                    return  o1.getDcaYear().compareTo(o2.getDcaYear());
                }
            }).collect(Collectors.toList());
             np= listUser.get(0).getNpPositionName();
           }*/
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBSciencepublishService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBSciencepublishService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBSciencepublish dcaBSciencepublish:list
        ){
        if(dcaBSciencepublish.getState()!=null&&dcaBSciencepublish.getState().equals(3)) {
    dcaBSciencepublish.setState(3);
        }
        else{
    dcaBSciencepublish.setState(state);
   // if(教授，副教授，研究员，副研究员)
       /** if(np.contains("教授") || np.contains("副教授")||np.contains("研究员")||np.contains("副研究员")){
            dcaBSciencepublish.setIsJxzcsb("是");
        }
            if(!(np.equals("教授") || np.equals("副教授")||np.equals("研究员")||np.equals("副研究员"))){
                dcaBSciencepublish.setIsLczcsb("是");
                dcaBSciencepublish.setLczcsl("1");
            }*/
        }
    dcaBSciencepublish.setDisplayIndex(display);
        display+=1;

    dcaBSciencepublish.setCreateUserId(currentUser.getUserId());
    dcaBSciencepublish.setUserAccount(currentUser.getUsername());
    dcaBSciencepublish.setUserAccountName(currentUser.getRealname());
        this.iDcaBSciencepublishService.createDcaBSciencepublish(dcaBSciencepublish);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBSciencepublish(@Valid String jsonStr ,int state,int auditState )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBSciencepublish dcaBSciencepublish= JSON.parseObject(jsonStr, new TypeReference<DcaBSciencepublish>() {
        });
    dcaBSciencepublish.setState(state);
            if (auditState >= 0) {
                if(state==2){
                    dcaBSciencepublish.setAuditState(0);
                }
                else {
                    dcaBSciencepublish.setAuditState(auditState+1);
                }

            }
    dcaBSciencepublish.setAuditMan(currentUser.getUsername());
    dcaBSciencepublish.setAuditManName(currentUser.getRealname());
    dcaBSciencepublish.setAuditDate(DateUtil.date());
        this.iDcaBSciencepublishService.updateDcaBSciencepublish(dcaBSciencepublish);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBSciencepublish
 * @return
 */
@Log("新增/按钮")
    @PostMapping
    public void addDcaBSciencepublish(@Valid DcaBSciencepublish dcaBSciencepublish)throws FebsException{
        try{
            User currentUser=FebsUtil.getCurrentUser();
            dcaBSciencepublish.setCreateUserId(currentUser.getUserId());
            dcaBSciencepublish.setUserAccount(currentUser.getUsername());
            this.iDcaBSciencepublishService.deleteByuseraccount(currentUser.getUsername());
            this.iDcaBSciencepublishService.createDcaBSciencepublish(dcaBSciencepublish);
        }catch(Exception e){
            message="新增/按钮失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    private boolean isExistPaperName(String userAccount,String paperName,String id){
       LambdaQueryWrapper<DcaBSciencepublish> queryWrapper= new LambdaQueryWrapper<>();
       queryWrapper.eq(DcaBSciencepublish::getUserAccount,userAccount);
       queryWrapper.eq(DcaBSciencepublish::getIsDeletemark,1);
       queryWrapper.eq(DcaBSciencepublish::getPaperName,paperName.trim());
       if(StringUtils.isNotEmpty(id)){
           queryWrapper.ne(DcaBSciencepublish::getId,id);
       }
      int cou= this.iDcaBSciencepublishService.count(queryWrapper);
       if(cou>0){
           return  true;
       }
       else{
           return  false;
       }
    }
    @Log("新增单独添加")
    @PostMapping("add")
    public FebsResponse addDcaBSciencepublish2(@Valid DcaBSciencepublish dcaBSciencepublish)throws FebsException{
        try{
            User currentUser=FebsUtil.getCurrentUser();
            dcaBSciencepublish.setCreateUserId(currentUser.getUserId());
            dcaBSciencepublish.setUserAccount(currentUser.getUsername());

            dcaBSciencepublish.setUserAccountName(currentUser.getRealname());
            if(isExistPaperName(currentUser.getUsername(),dcaBSciencepublish.getPaperName(),"")){
                throw new FebsException("此论文已经存在，请勿重复添加");
            }


            this.iDcaBSciencepublishService.deleteRealByuseraccount(currentUser.getUsername());
            this.iDcaBSciencepublishService.createDcaBSciencepublish(dcaBSciencepublish);
            return  new FebsResponse().data(dcaBSciencepublish.getId());
        }catch(Exception e){

            throw new FebsException(e.getMessage());
        }
    }
    @Log("新增单独编辑")
    @PostMapping("update")
    public FebsResponse updateDcaBSciencepublish2(@Valid DcaBSciencepublish dcaBSciencepublish)throws FebsException{
        try{
            User currentUser=FebsUtil.getCurrentUser();
            dcaBSciencepublish.setModifyUserId(currentUser.getUserId());
            if(isExistPaperName(currentUser.getUsername(),dcaBSciencepublish.getPaperName(),dcaBSciencepublish.getId())){
                throw new FebsException("此论文已经存在，请勿重复添加");
            }
            this.iDcaBSciencepublishService.updateDcaBSciencepublish(dcaBSciencepublish);
            return  new FebsResponse().data(dcaBSciencepublish.getId());
        }catch(Exception e){
            message="新增/按钮失败";
            log.error(message,e);
            throw new FebsException(e.getMessage());
        }
    }


    @Log("全部提交更新状态")
    @PostMapping("updateState")
    public void updateAllState(){
        User currentUser=FebsUtil.getCurrentUser();
        this.iDcaBSciencepublishService.updateStateByUserAccount(currentUser.getUsername());
    }
/**
 * 修改
 * @param dcaBSciencepublish
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBSciencepublish(@Valid DcaBSciencepublish dcaBSciencepublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSciencepublish.setModifyUserId(currentUser.getUserId());
        this.iDcaBSciencepublishService.updateDcaBSciencepublish(dcaBSciencepublish);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBSciencepublish:delete")
public void deleteDcaBSciencepublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBSciencepublishService.deleteDcaBSciencepublishs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

    @PostMapping("excel")
    public void export(QueryRequest request, DcaBSciencepublish dcaBSciencepublish,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();

            dcaBSciencepublish.setIsDeletemark(1);
            request.setSortField("user_account asc,state asc,display_Index");
            request.setSortOrder("ascend");
            if(dcaBSciencepublish.getState()!=null && dcaBSciencepublish.getState().equals(9)) {
                List<DcaBCopySciencepublish> dcaBSciencepublishList = this.iDcaBSciencepublishService.findDcaBCopySciencepublishs(request, dcaBSciencepublish).getRecords();
                ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList,dataJson,"");
            }
            else {
                List<DcaBSciencepublish> dcaBSciencepublishList = this.iDcaBSciencepublishService.findDcaBSciencepublishs(request, dcaBSciencepublish).getRecords();
                ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList,dataJson,"");
            }
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);

        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@GetMapping("/{id}")
public DcaBSciencepublish detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBSciencepublish dcaBSciencepublish=this.iDcaBSciencepublishService.getById(id);
        return dcaBSciencepublish;
        }
    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
public void downTemplate(HttpServletResponse response) {
    List<DcaBSciencepublish_Import> publishList = new ArrayList<>();
    ExcelKit.$Export(DcaBSciencepublish_Import.class, response).downXlsx(publishList, true);
}
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<DcaBSciencepublish_Import> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();

        int display=this.iDcaBSciencepublishService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        ExcelKit.$Import(DcaBSciencepublish_Import.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<DcaBSciencepublish_Import>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, DcaBSciencepublish_Import entity) {
                        successList.add(entity); // 单行读取成功，加入入库队列。
                    }

                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        java.util.List<ExcelErrorField> errorFields) {
                        // 读取数据失败，记录了当前行所有失败的数据
                        errorList.add(MapUtil.of("sheetIndex", sheetIndex));
                        errorList.add(MapUtil.of("rowIndex", rowIndex));
                        errorList.add(MapUtil.of("errorFields", errorFields));
                    }
                });

        // TODO: 执行successList的入库操作。
        if(CollectionUtil.isEmpty(errorList)){
            for (DcaBSciencepublish_Import dcaBSciencepublishImport:successList
            ) {
                DcaBSciencepublish dcaBSciencepublish =new DcaBSciencepublish();
                dcaBSciencepublish.setAuthorRank(dcaBSciencepublishImport.getAuthorRank());
                dcaBSciencepublish.setDjzz(dcaBSciencepublishImport.getDjzz());
                dcaBSciencepublish.setIsBest(dcaBSciencepublishImport.getIsBest());
                dcaBSciencepublish.setJournalCode(dcaBSciencepublishImport.getJournalCode());
                dcaBSciencepublish.setJournalName(dcaBSciencepublishImport.getJournalName());
                dcaBSciencepublish.setOtherTimes(dcaBSciencepublishImport.getOtherTimes());
                dcaBSciencepublish.setPaperCause(dcaBSciencepublishImport.getPaperCause());
                dcaBSciencepublish.setPaperName(dcaBSciencepublishImport.getPaperName());
                dcaBSciencepublish.setWzlx(dcaBSciencepublishImport.getWzlx());
                dcaBSciencepublish.setQkjb(dcaBSciencepublishImport.getQkjb());
                dcaBSciencepublish.setPaperShoulu(dcaBSciencepublishImport.getPaperShoulu());
                dcaBSciencepublish.setPaperPublishdate(Convert.toDate(dcaBSciencepublishImport.getPaperPublishdate()));

                dcaBSciencepublish.setCreateUserId(currentUser.getUserId());
                dcaBSciencepublish.setUserAccount(currentUser.getUsername());
                dcaBSciencepublish.setIsDeletemark(1);
                dcaBSciencepublish.setState(0);
                dcaBSciencepublish.setDisplayIndex(display);
                this.iDcaBSciencepublishService.createDcaBSciencepublish(dcaBSciencepublish);
                display+=1;
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }
}