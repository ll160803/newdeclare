package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dcacopy.service.IDcaBCopySureachievementService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySureachievement;

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
 * @since 2021-09-15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBCopySureachievement")

public class DcaBCopySureachievementController extends BaseController{

private String message;
@Autowired
public IDcaBCopySureachievementService iDcaBCopySureachievementService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopySureachievement 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBCopySureachievement dcaBCopySureachievement){
        return getDataTable(this.iDcaBCopySureachievementService.findDcaBCopySureachievements(request, dcaBCopySureachievement));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBCopySureachievement dcaBCopySureachievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBCopySureachievement.setUserAccount(currentUser.getUsername());
    dcaBCopySureachievement.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBCopySureachievementService.findDcaBCopySureachievements(request, dcaBCopySureachievement));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBCopySureachievement dcaBCopySureachievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBCopySureachievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBCopySureachievementService.findDcaBCopySureachievements(request, dcaBCopySureachievement));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBCopySureachievementCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBCopySureachievement> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBCopySureachievement>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBCopySureachievementService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBCopySureachievementService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBCopySureachievement dcaBCopySureachievement:list
        ){
        if(dcaBCopySureachievement.getState()!=null&&dcaBCopySureachievement.getState().equals(3)) {
    dcaBCopySureachievement.setState(3);
        }
        else{
    dcaBCopySureachievement.setState(state);
        }
    dcaBCopySureachievement.setDisplayIndex(display);
        display+=1;
    dcaBCopySureachievement.setCreateUserId(currentUser.getUserId());
    dcaBCopySureachievement.setUserAccount(currentUser.getUsername());
    dcaBCopySureachievement.setUserAccountName(currentUser.getRealname());
        this.iDcaBCopySureachievementService.createDcaBCopySureachievement(dcaBCopySureachievement);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBCopySureachievement(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBCopySureachievement dcaBCopySureachievement= JSON.parseObject(jsonStr, new TypeReference<DcaBCopySureachievement>() {
        });
    dcaBCopySureachievement.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBCopySureachievement.setAuditState(0);
        }
        else {
    dcaBCopySureachievement.setAuditState(auditState+1);
        }

        }*/
    dcaBCopySureachievement.setAuditMan(currentUser.getUsername());
    dcaBCopySureachievement.setAuditManName(currentUser.getRealname());
    dcaBCopySureachievement.setAuditDate(DateUtil.date());
        this.iDcaBCopySureachievementService.updateDcaBCopySureachievement(dcaBCopySureachievement);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBCopySureachievement
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBCopySureachievement(@Valid DcaBCopySureachievement dcaBCopySureachievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBCopySureachievement.setCreateUserId(currentUser.getUserId());
    dcaBCopySureachievement.setUserAccount(currentUser.getUsername());
        this.iDcaBCopySureachievementService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBCopySureachievementService.createDcaBCopySureachievement(dcaBCopySureachievement);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopySureachievement
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopySureachievement:update")
public void updateDcaBCopySureachievement(@Valid DcaBCopySureachievement dcaBCopySureachievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBCopySureachievement.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopySureachievementService.updateDcaBCopySureachievement(dcaBCopySureachievement);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopySureachievement:delete")
public void deleteDcaBCopySureachievements(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopySureachievementService.deleteDcaBCopySureachievements(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBCopySureachievement dcaBCopySureachievement,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBCopySureachievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBCopySureachievement> dcaBCopySureachievementList=  this.iDcaBCopySureachievementService.findDcaBCopySureachievements(request, dcaBCopySureachievement).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBCopySureachievementList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBCopySureachievement detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBCopySureachievement dcaBCopySureachievement=this.iDcaBCopySureachievementService.getById(id);
        return dcaBCopySureachievement;
        }
        }