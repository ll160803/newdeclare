package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBTeacherprizeService;
import cc.mrbird.febs.dca.entity.DcaBTeacherprize;

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
 * @since 2020-11-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBTeacherprize")

public class DcaBTeacherprizeController extends BaseController{

private String message;
@Autowired
public IDcaBTeacherprizeService iDcaBTeacherprizeService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBTeacherprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBTeacherprize:view")
public Map<String, Object> List(QueryRequest request, DcaBTeacherprize dcaBTeacherprize){
        return getDataTable(this.iDcaBTeacherprizeService.findDcaBTeacherprizes(request, dcaBTeacherprize));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaBTeacherprize dcaBTeacherprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTeacherprize.setUserAccount(currentUser.getUsername());
    dcaBTeacherprize.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBTeacherprizeService.findDcaBTeacherprizes(request, dcaBTeacherprize));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaBTeacherprize dcaBTeacherprize){
        User currentUser= FebsUtil.getCurrentUser();
    dcaBTeacherprize.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBTeacherprizeService.findDcaBTeacherprizes(request, dcaBTeacherprize));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaBTeacherprizeCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaBTeacherprize> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaBTeacherprize>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaBTeacherprizeService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaBTeacherprizeService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;
        for(DcaBTeacherprize dcaBTeacherprize:list
        ){
        if(dcaBTeacherprize.getState()!=null&&dcaBTeacherprize.getState().equals(3)) {
    dcaBTeacherprize.setState(3);
        }
        else{
    dcaBTeacherprize.setState(state);
        }
    dcaBTeacherprize.setDisplayIndex(display);
        display+=1;
    dcaBTeacherprize.setCreateUserId(currentUser.getUserId());
    dcaBTeacherprize.setUserAccount(currentUser.getUsername());
    dcaBTeacherprize.setUserAccountName(currentUser.getRealname());
        this.iDcaBTeacherprizeService.createDcaBTeacherprize(dcaBTeacherprize);
        }
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaBTeacherprize(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaBTeacherprize dcaBTeacherprize= JSON.parseObject(jsonStr, new TypeReference<DcaBTeacherprize>() {
        });
    dcaBTeacherprize.setState(state);
    dcaBTeacherprize.setAuditMan(currentUser.getUsername());
    dcaBTeacherprize.setAuditManName(currentUser.getRealname());
    dcaBTeacherprize.setAuditDate(DateUtil.date());
        this.iDcaBTeacherprizeService.updateDcaBTeacherprize(dcaBTeacherprize);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaBTeacherprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBTeacherprize(@Valid DcaBTeacherprize dcaBTeacherprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTeacherprize.setCreateUserId(currentUser.getUserId());
    dcaBTeacherprize.setUserAccount(currentUser.getUsername());
        this.iDcaBTeacherprizeService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaBTeacherprizeService.createDcaBTeacherprize(dcaBTeacherprize);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBTeacherprize
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBTeacherprize(@Valid DcaBTeacherprize dcaBTeacherprize)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
    dcaBTeacherprize.setModifyUserId(currentUser.getUserId());
        this.iDcaBTeacherprizeService.updateDcaBTeacherprize(dcaBTeacherprize);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBTeacherprize:delete")
public void deleteDcaBTeacherprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBTeacherprizeService.deleteDcaBTeacherprizes(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBTeacherprize:export")
public void export(QueryRequest request, DcaBTeacherprize dcaBTeacherprize,HttpServletResponse response)throws FebsException{
        try{
        List<DcaBTeacherprize> dcaBTeacherprizes=this.iDcaBTeacherprizeService.findDcaBTeacherprizes(request, dcaBTeacherprize).getRecords();
        ExcelKit.$Export(DcaBTeacherprize.class,response).downXlsx(dcaBTeacherprizes,false);
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBTeacherprize detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaBTeacherprize dcaBTeacherprize=this.iDcaBTeacherprizeService.getById(id);
        return dcaBTeacherprize;
        }
        }