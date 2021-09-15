package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyTeacheryjService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacheryj;

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
@RequestMapping("dcaBCopyTeacheryj")

public class DcaBCopyTeacheryjController extends BaseController{

private String message;
@Autowired
public IDcaBCopyTeacheryjService iDcaBCopyTeacheryjService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyTeacheryj 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBCopyTeacheryj dcaBCopyTeacheryj){
        return getDataTable(this.iDcaBCopyTeacheryjService.findDcaBCopyTeacheryjs(request, dcaBCopyTeacheryj));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBCopyTeacheryj dcaBCopyTeacheryj){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBCopyTeacheryj.setUserAccount(currentUser.getUsername());
    dcaBCopyTeacheryj.setIsDeletemark(1);
        request.setPageSize(100);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBCopyTeacheryjService.findDcaBCopyTeacheryjs(request, dcaBCopyTeacheryj));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBCopyTeacheryj dcaBCopyTeacheryj){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBCopyTeacheryj.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBCopyTeacheryjService.findDcaBCopyTeacheryjs(request, dcaBCopyTeacheryj));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBCopyTeacheryjCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBCopyTeacheryj> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBCopyTeacheryj>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBCopyTeacheryjService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBCopyTeacheryjService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBCopyTeacheryj dcaBCopyTeacheryj:list
        ){
        if(dcaBCopyTeacheryj.getState()!=null&&dcaBCopyTeacheryj.getState().equals(3)) {
    dcaBCopyTeacheryj.setState(3);
        }
        else{
    dcaBCopyTeacheryj.setState(state);
        }
    dcaBCopyTeacheryj.setDisplayIndex(display);
        display+=1;
    dcaBCopyTeacheryj.setCreateUserId(currentUser.getUserId());
    dcaBCopyTeacheryj.setUserAccount(currentUser.getUsername());
    dcaBCopyTeacheryj.setUserAccountName(currentUser.getRealname());
        this.iDcaBCopyTeacheryjService.createDcaBCopyTeacheryj(dcaBCopyTeacheryj);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBCopyTeacheryj(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBCopyTeacheryj dcaBCopyTeacheryj= JSON.parseObject(jsonStr, new TypeReference<DcaBCopyTeacheryj>() {
        });
    dcaBCopyTeacheryj.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBCopyTeacheryj.setAuditState(0);
        }
        else {
    dcaBCopyTeacheryj.setAuditState(auditState+1);
        }

        }*/
    dcaBCopyTeacheryj.setAuditMan(currentUser.getUsername());
    dcaBCopyTeacheryj.setAuditManName(currentUser.getRealname());
    dcaBCopyTeacheryj.setAuditDate(DateUtil.date());
        this.iDcaBCopyTeacheryjService.updateDcaBCopyTeacheryj(dcaBCopyTeacheryj);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBCopyTeacheryj
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBCopyTeacheryj(@Valid DcaBCopyTeacheryj dcaBCopyTeacheryj)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBCopyTeacheryj.setCreateUserId(currentUser.getUserId());
    dcaBCopyTeacheryj.setUserAccount(currentUser.getUsername());
        this.iDcaBCopyTeacheryjService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBCopyTeacheryjService.createDcaBCopyTeacheryj(dcaBCopyTeacheryj);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyTeacheryj
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyTeacheryj:update")
public void updateDcaBCopyTeacheryj(@Valid DcaBCopyTeacheryj dcaBCopyTeacheryj)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBCopyTeacheryj.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyTeacheryjService.updateDcaBCopyTeacheryj(dcaBCopyTeacheryj);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyTeacheryj:delete")
public void deleteDcaBCopyTeacheryjs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyTeacheryjService.deleteDcaBCopyTeacheryjs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBCopyTeacheryj dcaBCopyTeacheryj,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBCopyTeacheryj.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBCopyTeacheryj> dcaBCopyTeacheryjList=  this.iDcaBCopyTeacheryjService.findDcaBCopyTeacheryjs(request, dcaBCopyTeacheryj).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBCopyTeacheryjList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBCopyTeacheryj detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBCopyTeacheryj dcaBCopyTeacheryj=this.iDcaBCopyTeacheryjService.getById(id);
        return dcaBCopyTeacheryj;
        }
        }