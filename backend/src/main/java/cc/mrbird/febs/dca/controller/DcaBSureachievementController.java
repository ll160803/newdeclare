package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaBSureachievementService;
import cc.mrbird.febs.dca.entity.DcaBSureachievement;

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
@RequestMapping("dcaBSureachievement")

public class DcaBSureachievementController extends BaseController{

private String message;
@Autowired
public IDcaBSureachievementService iDcaBSureachievementService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBSureachievement 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBSureachievement dcaBSureachievement){
        return getDataTable(this.iDcaBSureachievementService.findDcaBSureachievements(request, dcaBSureachievement));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBSureachievement dcaBSureachievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSureachievement.setUserAccount(currentUser.getUsername());
    dcaBSureachievement.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBSureachievementService.findDcaBSureachievements(request, dcaBSureachievement));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBSureachievement dcaBSureachievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSureachievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBSureachievementService.findDcaBSureachievements(request, dcaBSureachievement));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBSureachievementCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBSureachievement> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBSureachievement>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBSureachievementService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBSureachievementService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBSureachievement dcaBSureachievement:list
        ){
        if(dcaBSureachievement.getState()!=null&&dcaBSureachievement.getState().equals(3)) {
    dcaBSureachievement.setState(3);
        }
        else{
    dcaBSureachievement.setState(state);
        }
    dcaBSureachievement.setDisplayIndex(display);
        display+=1;
    dcaBSureachievement.setCreateUserId(currentUser.getUserId());
    dcaBSureachievement.setUserAccount(currentUser.getUsername());
    dcaBSureachievement.setUserAccountName(currentUser.getRealname());
        this.iDcaBSureachievementService.createDcaBSureachievement(dcaBSureachievement);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBSureachievement(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBSureachievement dcaBSureachievement= JSON.parseObject(jsonStr, new TypeReference<DcaBSureachievement>() {
        });
    dcaBSureachievement.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBSureachievement.setAuditState(0);
        }
        else {
    dcaBSureachievement.setAuditState(auditState+1);
        }

        }*/
    dcaBSureachievement.setAuditMan(currentUser.getUsername());
    dcaBSureachievement.setAuditManName(currentUser.getRealname());
    dcaBSureachievement.setAuditDate(DateUtil.date());
        this.iDcaBSureachievementService.updateDcaBSureachievement(dcaBSureachievement);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBSureachievement
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBSureachievement(@Valid DcaBSureachievement dcaBSureachievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSureachievement.setCreateUserId(currentUser.getUserId());
    dcaBSureachievement.setUserAccount(currentUser.getUsername());
        this.iDcaBSureachievementService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBSureachievementService.createDcaBSureachievement(dcaBSureachievement);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBSureachievement
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBSureachievement(@Valid DcaBSureachievement dcaBSureachievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSureachievement.setModifyUserId(currentUser.getUserId());
        this.iDcaBSureachievementService.updateDcaBSureachievement(dcaBSureachievement);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBSureachievement:delete")
public void deleteDcaBSureachievements(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBSureachievementService.deleteDcaBSureachievements(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBSureachievement dcaBSureachievement,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBSureachievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBSureachievement> dcaBSureachievementList=  this.iDcaBSureachievementService.findDcaBSureachievements(request, dcaBSureachievement).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBSureachievementList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBSureachievement detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBSureachievement dcaBSureachievement=this.iDcaBSureachievementService.getById(id);
        return dcaBSureachievement;
        }
        }