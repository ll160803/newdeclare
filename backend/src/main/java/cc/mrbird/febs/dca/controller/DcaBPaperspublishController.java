package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBPaperspublishService;
import cc.mrbird.febs.dca.entity.DcaBPaperspublish;

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
@RequestMapping("dcaBPaperspublish")

public class DcaBPaperspublishController extends BaseController{

private String message;
@Autowired
public IDcaBPaperspublishService iDcaBPaperspublishService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBPaperspublish 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBPaperspublish:view")
public Map<String, Object> List(QueryRequest request, DcaBPaperspublish dcaBPaperspublish){
        return getDataTable(this.iDcaBPaperspublishService.findDcaBPaperspublishs(request, dcaBPaperspublish));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBPaperspublish dcaBPaperspublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBPaperspublish.setUserAccount(currentUser.getUsername());
    dcaBPaperspublish.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBPaperspublishService.findDcaBPaperspublishs(request, dcaBPaperspublish));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBPaperspublish dcaBPaperspublish){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBPaperspublish.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBPaperspublishService.findDcaBPaperspublishs(request, dcaBPaperspublish));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBPaperspublishCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBPaperspublish> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBPaperspublish>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBPaperspublishService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBPaperspublishService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBPaperspublish dcaBPaperspublish:list
        ){
        if(dcaBPaperspublish.getState()!=null&&dcaBPaperspublish.getState().equals(3)) {
    dcaBPaperspublish.setState(3);
        }
        else{
    dcaBPaperspublish.setState(state);
        }
    dcaBPaperspublish.setDisplayIndex(display);
        display+=1;
    dcaBPaperspublish.setCreateUserId(currentUser.getUserId());
    dcaBPaperspublish.setUserAccount(currentUser.getUsername());
    dcaBPaperspublish.setUserAccountName(currentUser.getRealname());
        this.iDcaBPaperspublishService.createDcaBPaperspublish(dcaBPaperspublish);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBPaperspublish(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBPaperspublish dcaBPaperspublish= JSON.parseObject(jsonStr, new TypeReference<DcaBPaperspublish>() {
        });
    dcaBPaperspublish.setState(state);
    dcaBPaperspublish.setAuditMan(currentUser.getUsername());
    dcaBPaperspublish.setAuditManName(currentUser.getRealname());
    dcaBPaperspublish.setAuditDate(DateUtil.date());
        this.iDcaBPaperspublishService.updateDcaBPaperspublish(dcaBPaperspublish);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBPaperspublish
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBPaperspublish(@Valid DcaBPaperspublish dcaBPaperspublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPaperspublish.setCreateUserId(currentUser.getUserId());
    dcaBPaperspublish.setUserAccount(currentUser.getUsername());
        this.iDcaBPaperspublishService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBPaperspublishService.createDcaBPaperspublish(dcaBPaperspublish);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBPaperspublish
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBPaperspublish(@Valid DcaBPaperspublish dcaBPaperspublish)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBPaperspublish.setModifyUserId(currentUser.getUserId());
        this.iDcaBPaperspublishService.updateDcaBPaperspublish(dcaBPaperspublish);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBPaperspublish:delete")
public void deleteDcaBPaperspublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBPaperspublishService.deleteDcaBPaperspublishs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBPaperspublish:export")
public void export(QueryRequest request, DcaBPaperspublish dcaBPaperspublish,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBPaperspublish> dcaBPaperspublishs=this.iDcaBPaperspublishService.findDcaBPaperspublishs(request, dcaBPaperspublish).getRecords();
        ExcelKit.$Export(DcaBPaperspublish.class,response).downXlsx(dcaBPaperspublishs,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBPaperspublish detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBPaperspublish dcaBPaperspublish=this.iDcaBPaperspublishService.getById(id);
        return dcaBPaperspublish;
        }
        }