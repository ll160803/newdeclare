package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBGraduateService;
import cc.mrbird.febs.dca.entity.DcaBGraduate;

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
 * @since 2020-10-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBGraduate")

public class DcaBGraduateController extends BaseController{

private String message;
@Autowired
public IDcaBGraduateService iDcaBGraduateService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBGraduate 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBGraduate:view")
public Map<String, Object> List(QueryRequest request, DcaBGraduate dcaBGraduate){
        return getDataTable(this.iDcaBGraduateService.findDcaBGraduates(request, dcaBGraduate));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBGraduate dcaBGraduate){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBGraduate.setUserAccount(currentUser.getUsername());
    dcaBGraduate.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBGraduateService.findDcaBGraduates(request, dcaBGraduate));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBGraduate dcaBGraduate){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBGraduate.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBGraduateService.findDcaBGraduates(request, dcaBGraduate));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBGraduateCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBGraduate> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBGraduate>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBGraduateService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBGraduateService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBGraduate dcaBGraduate:list
        ){
        if(dcaBGraduate.getState()!=null&&dcaBGraduate.getState().equals(3)) {
    dcaBGraduate.setState(3);
        }
        else{
    dcaBGraduate.setState(state);
        }
    dcaBGraduate.setDisplayIndex(display);
        display+=1;
    dcaBGraduate.setCreateUserId(currentUser.getUserId());
    dcaBGraduate.setUserAccount(currentUser.getUsername());
    dcaBGraduate.setUserAccountName(currentUser.getRealname());
        this.iDcaBGraduateService.createDcaBGraduate(dcaBGraduate);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBGraduate(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBGraduate dcaBGraduate= JSON.parseObject(jsonStr, new TypeReference<DcaBGraduate>() {
        });
    dcaBGraduate.setState(state);
    dcaBGraduate.setAuditMan(currentUser.getUsername());
    dcaBGraduate.setAuditManName(currentUser.getRealname());
    dcaBGraduate.setAuditDate(DateUtil.date());
        this.iDcaBGraduateService.updateDcaBGraduate(dcaBGraduate);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBGraduate
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBGraduate(@Valid DcaBGraduate dcaBGraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBGraduate.setCreateUserId(currentUser.getUserId());
    dcaBGraduate.setUserAccount(currentUser.getUsername());
        this.iDcaBGraduateService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBGraduateService.createDcaBGraduate(dcaBGraduate);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBGraduate
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBGraduate(@Valid DcaBGraduate dcaBGraduate)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBGraduate.setModifyUserId(currentUser.getUserId());
        this.iDcaBGraduateService.updateDcaBGraduate(dcaBGraduate);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBGraduate:delete")
public void deleteDcaBGraduates(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBGraduateService.deleteDcaBGraduates(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBGraduate:export")
public void export(QueryRequest request, DcaBGraduate dcaBGraduate,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBGraduate> dcaBGraduates=this.iDcaBGraduateService.findDcaBGraduates(request, dcaBGraduate).getRecords();
        ExcelKit.$Export(DcaBGraduate.class,response).downXlsx(dcaBGraduates,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBGraduate detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBGraduate dcaBGraduate=this.iDcaBGraduateService.getById(id);
        return dcaBGraduate;
        }
        }