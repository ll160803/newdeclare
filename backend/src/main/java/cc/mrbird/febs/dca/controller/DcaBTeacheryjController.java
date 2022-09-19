package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaBTeacheryjService;
import cc.mrbird.febs.dca.entity.DcaBTeacheryj;

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
@RequestMapping("dcaBTeacheryj")

public class DcaBTeacheryjController extends BaseController{

private String message;
@Autowired
public IDcaBTeacheryjService iDcaBTeacheryjService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBTeacheryj 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBTeacheryj dcaBTeacheryj){
        return getDataTable(this.iDcaBTeacheryjService.findDcaBTeacheryjs(request, dcaBTeacheryj));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBTeacheryj dcaBTeacheryj){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTeacheryj.setUserAccount(currentUser.getUsername());
    dcaBTeacheryj.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBTeacheryjService.findDcaBTeacheryjs(request, dcaBTeacheryj));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBTeacheryj dcaBTeacheryj){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTeacheryj.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBTeacheryjService.findDcaBTeacheryjs(request, dcaBTeacheryj));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBTeacheryjCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBTeacheryj> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBTeacheryj>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBTeacheryjService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBTeacheryjService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBTeacheryj dcaBTeacheryj:list
        ){
        if(dcaBTeacheryj.getState()!=null&&dcaBTeacheryj.getState().equals(3)) {
    dcaBTeacheryj.setState(3);
        }
        else{
    dcaBTeacheryj.setState(state);
        }
    dcaBTeacheryj.setDisplayIndex(display);
        display+=1;
    dcaBTeacheryj.setCreateUserId(currentUser.getUserId());
    dcaBTeacheryj.setUserAccount(currentUser.getUsername());
    dcaBTeacheryj.setUserAccountName(currentUser.getRealname());
        this.iDcaBTeacheryjService.createDcaBTeacheryj(dcaBTeacheryj);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBTeacheryj(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBTeacheryj dcaBTeacheryj= JSON.parseObject(jsonStr, new TypeReference<DcaBTeacheryj>() {
        });
    dcaBTeacheryj.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaBTeacheryj.setAuditState(0);
        }
        else {
    dcaBTeacheryj.setAuditState(auditState+1);
        }

        }*/
    dcaBTeacheryj.setAuditMan(currentUser.getUsername());
    dcaBTeacheryj.setAuditManName(currentUser.getRealname());
    dcaBTeacheryj.setAuditDate(DateUtil.date());
        this.iDcaBTeacheryjService.updateDcaBTeacheryj(dcaBTeacheryj);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBTeacheryj
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBTeacheryj(@Valid DcaBTeacheryj dcaBTeacheryj)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTeacheryj.setCreateUserId(currentUser.getUserId());
    dcaBTeacheryj.setUserAccount(currentUser.getUsername());
        this.iDcaBTeacheryjService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBTeacheryjService.createDcaBTeacheryj(dcaBTeacheryj);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBTeacheryj
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBTeacheryj(@Valid DcaBTeacheryj dcaBTeacheryj)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTeacheryj.setModifyUserId(currentUser.getUserId());
        this.iDcaBTeacheryjService.updateDcaBTeacheryj(dcaBTeacheryj);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
public void deleteDcaBTeacheryjs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBTeacheryjService.deleteDcaBTeacheryjs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaBTeacheryj dcaBTeacheryj,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaBTeacheryj.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaBTeacheryj> dcaBTeacheryjList=  this.iDcaBTeacheryjService.findDcaBTeacheryjs(request, dcaBTeacheryj).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaBTeacheryjList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaBTeacheryj detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBTeacheryj dcaBTeacheryj=this.iDcaBTeacheryjService.getById(id);
        return dcaBTeacheryj;
        }
        }