package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dcacopy.service.IDcaBCopySciachievementService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciachievement;

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
 * @since 2021-09-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBCopySciachievement")

public class DcaBCopySciachievementController extends BaseController{

private String message;
@Autowired
public IDcaBCopySciachievementService iDcaBCopySciachievementService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopySciachievement 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBCopySciachievement dcaBCopySciachievement){
        return getDataTable(this.iDcaBCopySciachievementService.findDcaBCopySciachievements(request, dcaBCopySciachievement));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBCopySciachievement dcaBCopySciachievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBCopySciachievement.setUserAccount(currentUser.getUsername());
    dcaBCopySciachievement.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBCopySciachievementService.findDcaBCopySciachievements(request, dcaBCopySciachievement));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBCopySciachievement dcaBCopySciachievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBCopySciachievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBCopySciachievementService.findDcaBCopySciachievements(request, dcaBCopySciachievement));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBCopySciachievementCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBCopySciachievement> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBCopySciachievement>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBCopySciachievementService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBCopySciachievementService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBCopySciachievement dcaBCopySciachievement:list
        ){
        if(dcaBCopySciachievement.getState()!=null&&dcaBCopySciachievement.getState().equals(3)) {
    dcaBCopySciachievement.setState(3);
        }
        else{
    dcaBCopySciachievement.setState(state);
        }
    dcaBCopySciachievement.setDisplayIndex(display);
        display+=1;
    dcaBCopySciachievement.setCreateUserId(currentUser.getUserId());
    dcaBCopySciachievement.setUserAccount(currentUser.getUsername());
    dcaBCopySciachievement.setUserAccountName(currentUser.getRealname());
        this.iDcaBCopySciachievementService.createDcaBCopySciachievement(dcaBCopySciachievement);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBCopySciachievement(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBCopySciachievement dcaBCopySciachievement= JSON.parseObject(jsonStr, new TypeReference<DcaBCopySciachievement>() {
        });
    dcaBCopySciachievement.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBCopySciachievement.setAuditState(0);
        }
        else {
    dcaBCopySciachievement.setAuditState(auditState+1);
        }

        }*/
    dcaBCopySciachievement.setAuditMan(currentUser.getUsername());
    dcaBCopySciachievement.setAuditManName(currentUser.getRealname());
    dcaBCopySciachievement.setAuditDate(DateUtil.date());
        this.iDcaBCopySciachievementService.updateDcaBCopySciachievement(dcaBCopySciachievement);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBCopySciachievement
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBCopySciachievement(@Valid DcaBCopySciachievement dcaBCopySciachievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBCopySciachievement.setCreateUserId(currentUser.getUserId());
    dcaBCopySciachievement.setUserAccount(currentUser.getUsername());
        this.iDcaBCopySciachievementService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBCopySciachievementService.createDcaBCopySciachievement(dcaBCopySciachievement);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopySciachievement
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopySciachievement:update")
public void updateDcaBCopySciachievement(@Valid DcaBCopySciachievement dcaBCopySciachievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBCopySciachievement.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopySciachievementService.updateDcaBCopySciachievement(dcaBCopySciachievement);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopySciachievement:delete")
public void deleteDcaBCopySciachievements(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopySciachievementService.deleteDcaBCopySciachievements(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBCopySciachievement dcaBCopySciachievement,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBCopySciachievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBCopySciachievement> dcaBCopySciachievementList=  this.iDcaBCopySciachievementService.findDcaBCopySciachievements(request, dcaBCopySciachievement).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBCopySciachievementList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBCopySciachievement detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBCopySciachievement dcaBCopySciachievement=this.iDcaBCopySciachievementService.getById(id);
        return dcaBCopySciachievement;
        }
        }