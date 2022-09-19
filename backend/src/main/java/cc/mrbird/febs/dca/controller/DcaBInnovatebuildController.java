package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaBInnovatebuildService;
import cc.mrbird.febs.dca.entity.DcaBInnovatebuild;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.date.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viki
 * @since 2020-11-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBInnovatebuild")

public class DcaBInnovatebuildController extends BaseController{

private String message;
@Autowired
public IDcaBInnovatebuildService iDcaBInnovatebuildService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBInnovatebuild 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBInnovatebuild:view")
public Map<String, Object> List(QueryRequest request, DcaBInnovatebuild dcaBInnovatebuild){
        return getDataTable(this.iDcaBInnovatebuildService.findDcaBInnovatebuilds(request, dcaBInnovatebuild));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBInnovatebuild dcaBInnovatebuild){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBInnovatebuild.setUserAccount(currentUser.getUsername());
    dcaBInnovatebuild.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBInnovatebuildService.findDcaBInnovatebuilds(request, dcaBInnovatebuild));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBInnovatebuild dcaBInnovatebuild){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBInnovatebuild.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBInnovatebuildService.findDcaBInnovatebuilds(request, dcaBInnovatebuild));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBInnovatebuildCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBInnovatebuild> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBInnovatebuild>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBInnovatebuildService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBInnovatebuildService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBInnovatebuild dcaBInnovatebuild:list
        ){
        if(dcaBInnovatebuild.getState()!=null&&dcaBInnovatebuild.getState().equals(3)) {
    dcaBInnovatebuild.setState(3);
        }
        else{
    dcaBInnovatebuild.setState(state);
        }
    dcaBInnovatebuild.setDisplayIndex(display);
        display+=1;
    dcaBInnovatebuild.setCreateUserId(currentUser.getUserId());
    dcaBInnovatebuild.setUserAccount(currentUser.getUsername());
    dcaBInnovatebuild.setUserAccountName(currentUser.getRealname());
        this.iDcaBInnovatebuildService.createDcaBInnovatebuild(dcaBInnovatebuild);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBInnovatebuild(@Valid String jsonStr ,int state,int auditState  )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBInnovatebuild dcaBInnovatebuild= JSON.parseObject(jsonStr, new TypeReference<DcaBInnovatebuild>() {
        });
    dcaBInnovatebuild.setState(state);
        if (auditState >= 0) {
        if(state==2){
    dcaBInnovatebuild.setAuditState(0);
        }
        else {
    dcaBInnovatebuild.setAuditState(auditState+1);
        }

        }
    dcaBInnovatebuild.setAuditMan(currentUser.getUsername());
    dcaBInnovatebuild.setAuditManName(currentUser.getRealname());
    dcaBInnovatebuild.setAuditDate(DateUtil.date());
        this.iDcaBInnovatebuildService.updateDcaBInnovatebuild(dcaBInnovatebuild);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBInnovatebuild
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBInnovatebuild(@Valid DcaBInnovatebuild dcaBInnovatebuild)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBInnovatebuild.setCreateUserId(currentUser.getUserId());
    dcaBInnovatebuild.setUserAccount(currentUser.getUsername());
        this.iDcaBInnovatebuildService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBInnovatebuildService.createDcaBInnovatebuild(dcaBInnovatebuild);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBInnovatebuild
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBInnovatebuild(@Valid DcaBInnovatebuild dcaBInnovatebuild)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBInnovatebuild.setModifyUserId(currentUser.getUserId());
        this.iDcaBInnovatebuildService.updateDcaBInnovatebuild(dcaBInnovatebuild);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBInnovatebuild:delete")
public void deleteDcaBInnovatebuilds(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBInnovatebuildService.deleteDcaBInnovatebuilds(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
//@PostMapping("excel")
//@RequiresPermissions("dcaBInnovatebuild:export")
//public void export(QueryRequest request, DcaBInnovatebuild dcaBInnovatebuild,HttpServletResponse response)throws FebsException{
//        try{
//        List<DcaBInnovatebuild> dcaBInnovatebuilds=this.iDcaBInnovatebuildService.findDcaBInnovatebuilds(request, dcaBInnovatebuild).getRecords();
//        ExcelKit.$Export(DcaBInnovatebuild.class,response).downXlsx(dcaBInnovatebuilds,false);
//        }catch(Exception e){
//        message="导出Excel失败";
//        log.error(message,e);
//        throw new FebsException(message);
//        }
//        }
    @PostMapping("excel")
    public void export(QueryRequest request, DcaBInnovatebuild dcaBSciencepublish,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();

            dcaBSciencepublish.setIsDeletemark(1);
            request.setSortField("user_account asc,state");
            request.setSortOrder("ascend");
            List<DcaBInnovatebuild> dcaBSciencepublishList=  this.iDcaBInnovatebuildService.findDcaBInnovatebuilds(request, dcaBSciencepublish).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@GetMapping("/{id}")
public DcaBInnovatebuild detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBInnovatebuild dcaBInnovatebuild=this.iDcaBInnovatebuildService.getById(id);
        return dcaBInnovatebuild;
        }
        }