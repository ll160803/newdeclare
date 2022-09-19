package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaDYjService;
import cc.mrbird.febs.dca.entity.DcaDYj;

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
 * @since 2021-09-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaDYj")

public class DcaDYjController extends BaseController{

private String message;
@Autowired
public IDcaDYjService iDcaDYjService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaDYj 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaDYj dcaDYj){
        return getDataTable(this.iDcaDYjService.findDcaDYjs(request, dcaDYj));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaDYj dcaDYj){
        User currentUser= FebsUtil.getCurrentUser();
    dcaDYj.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaDYjService.findDcaDYjs(request, dcaDYj));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaDYj dcaDYj){
        User currentUser= FebsUtil.getCurrentUser();
    dcaDYj.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaDYjService.findDcaDYjs(request, dcaDYj));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaDYjCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaDYj> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaDYj>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaDYjService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaDYjService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaDYj dcaDYj:list
        ){
        if(dcaDYj.getState()!=null&&dcaDYj.getState().equals(3)) {
    dcaDYj.setState(3);
        }
        else{
    dcaDYj.setState(state);
        }

        display+=1;
    dcaDYj.setCreateUserId(currentUser.getUserId());

        this.iDcaDYjService.createDcaDYj(dcaDYj);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaDYj(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaDYj dcaDYj= JSON.parseObject(jsonStr, new TypeReference<DcaDYj>() {
        });
    dcaDYj.setState(state);
    /**
        if (auditState >= 0) {
        if(state==2){
    dcaDYj.setAuditState(0);
        }
        else {
    dcaDYj.setAuditState(auditState+1);
        }

        }*/

        this.iDcaDYjService.updateDcaDYj(dcaDYj);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaDYj
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaDYj(@Valid DcaDYj dcaDYj)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaDYj.setCreateUserId(currentUser.getUserId());

        this.iDcaDYjService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaDYjService.createDcaDYj(dcaDYj);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaDYj
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaDYj(@Valid DcaDYj dcaDYj)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaDYj.setModifyUserId(currentUser.getUserId());
        this.iDcaDYjService.updateDcaDYj(dcaDYj);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
public void deleteDcaDYjs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaDYjService.deleteDcaDYjs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaDYj dcaDYj,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();

    dcaDYj.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaDYj> dcaDYjList=  this.iDcaDYjService.findDcaDYjs(request, dcaDYj).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaDYjList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaDYj detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaDYj dcaDYj=this.iDcaDYjService.getById(id);
        return dcaDYj;
        }

    @GetMapping("tree")
    public Map<String, Object> treeList(String dj) {
        return this.iDcaDYjService.findDepts(dj);
    }

    @GetMapping("treeByUserId/{year}")
    public Map<String, Object> treeList2(@PathVariable String year) {
        User currentUser= FebsUtil.getCurrentUser();
        return this.iDcaDYjService.findDeptsByUserId(currentUser.getUsername(),year);
    }
        }