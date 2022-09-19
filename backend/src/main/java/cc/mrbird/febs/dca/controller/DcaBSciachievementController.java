package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaBSciachievementService;
import cc.mrbird.febs.dca.entity.DcaBSciachievement;

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
@RequestMapping("dcaBSciachievement")

public class DcaBSciachievementController extends BaseController{

private String message;
@Autowired
public IDcaBSciachievementService iDcaBSciachievementService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBSciachievement 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBSciachievement dcaBSciachievement){
        return getDataTable(this.iDcaBSciachievementService.findDcaBSciachievements(request, dcaBSciachievement));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBSciachievement dcaBSciachievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSciachievement.setUserAccount(currentUser.getUsername());
    dcaBSciachievement.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBSciachievementService.findDcaBSciachievements(request, dcaBSciachievement));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBSciachievement dcaBSciachievement){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBSciachievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBSciachievementService.findDcaBSciachievements(request, dcaBSciachievement));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBSciachievementCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBSciachievement> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBSciachievement>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBSciachievementService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBSciachievementService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBSciachievement dcaBSciachievement:list
        ){
        if(dcaBSciachievement.getState()!=null&&dcaBSciachievement.getState().equals(3)) {
    dcaBSciachievement.setState(3);
        }
        else{
    dcaBSciachievement.setState(state);
        }
    dcaBSciachievement.setDisplayIndex(display);
        display+=1;
    dcaBSciachievement.setCreateUserId(currentUser.getUserId());
    dcaBSciachievement.setUserAccount(currentUser.getUsername());
    dcaBSciachievement.setUserAccountName(currentUser.getRealname());
        this.iDcaBSciachievementService.createDcaBSciachievement(dcaBSciachievement);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBSciachievement(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBSciachievement dcaBSciachievement= JSON.parseObject(jsonStr, new TypeReference<DcaBSciachievement>() {
        });
    dcaBSciachievement.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBSciachievement.setAuditState(0);
        }
        else {
    dcaBSciachievement.setAuditState(auditState+1);
        }

        }*/
    dcaBSciachievement.setAuditMan(currentUser.getUsername());
    dcaBSciachievement.setAuditManName(currentUser.getRealname());
    dcaBSciachievement.setAuditDate(DateUtil.date());
        this.iDcaBSciachievementService.updateDcaBSciachievement(dcaBSciachievement);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBSciachievement
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBSciachievement(@Valid DcaBSciachievement dcaBSciachievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSciachievement.setCreateUserId(currentUser.getUserId());
    dcaBSciachievement.setUserAccount(currentUser.getUsername());
        this.iDcaBSciachievementService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBSciachievementService.createDcaBSciachievement(dcaBSciachievement);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBSciachievement
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBSciachievement(@Valid DcaBSciachievement dcaBSciachievement)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBSciachievement.setModifyUserId(currentUser.getUserId());
        this.iDcaBSciachievementService.updateDcaBSciachievement(dcaBSciachievement);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
public void deleteDcaBSciachievements(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBSciachievementService.deleteDcaBSciachievements(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBSciachievement dcaBSciachievement,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBSciachievement.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBSciachievement> dcaBSciachievementList=  this.iDcaBSciachievementService.findDcaBSciachievements(request, dcaBSciachievement).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBSciachievementList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBSciachievement detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBSciachievement dcaBSciachievement=this.iDcaBSciachievementService.getById(id);
        return dcaBSciachievement;
        }
        }