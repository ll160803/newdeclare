package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.entity.DcaBDocAuditfiveother;
import cc.mrbird.febs.doctor.service.IDcaBDocAuditfiveotherService;


import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
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
 * @since 2022-11-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBDocAuditfiveother")

public class DcaBDocAuditfiveotherController extends BaseController{

private String message;
@Autowired
public IDcaBDocAuditfiveotherService iDcaBDocAuditfiveotherService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'中期考核','/dca/DcaBDocAuditfiveother/DcaBDocAuditfiveother','dca/DcaBDocAuditfiveother/DcaBDocAuditfiveother','dcaBDocAuditfiveother:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'中期考核新增','dcaBDocAuditfiveother:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'中期考核编辑','dcaBDocAuditfiveother:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'中期考核删除','dcaBDocAuditfiveother:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocAuditfiveother 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocAuditfiveother dcaBDocAuditfiveother){
        return getDataTable(this.iDcaBDocAuditfiveotherService.findDcaBDocAuditfiveothers(request, dcaBDocAuditfiveother));
        }
    @GetMapping("custom")
    public Map<String, Object> ListCustom(QueryRequest request, DcaBDocAuditfiveother dcaBDocEmploy){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBDocEmploy.setUserAccount(currentUser.getUsername());
        dcaBDocEmploy.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAuditfiveotherService.findDcaBDocAuditfiveothers(request, dcaBDocEmploy));
    }
    @GetMapping("audit")
    public Map<String, Object> List2(QueryRequest request, DcaBDocAuditfiveother dcaBDocEmploy){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBDocEmploy.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAuditfiveotherService.findDcaBDocAuditfiveothers(request, dcaBDocEmploy));
    }
/**
 * 添加
 * @param  dcaBDocAuditfiveother
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocAuditfiveother(@Valid DcaBDocAuditfiveother dcaBDocAuditfiveother)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBDocAuditfiveother.setCreateUserId(currentUser.getUserId());
        this.iDcaBDocAuditfiveotherService.createDcaBDocAuditfiveother(dcaBDocAuditfiveother);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocAuditfiveother
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBDocAuditfiveother(@Valid DcaBDocAuditfiveother dcaBDocAuditfiveother)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBDocAuditfiveother.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocAuditfiveotherService.updateDcaBDocAuditfiveother(dcaBDocAuditfiveother);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
public void deleteDcaBDocAuditfiveothers(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocAuditfiveotherService.deleteDcaBDocAuditfiveothers(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, DcaBDocAuditfiveother dcaBDocAuditfiveother, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBDocAuditfiveother> dcaBDocAuditfiveothers = this.iDcaBDocAuditfiveotherService.findDcaBDocAuditfiveothers(request, dcaBDocAuditfiveother).getRecords();
        ExcelKit.$Export(DcaBDocAuditfiveother.class, response).downXlsx(dcaBDocAuditfiveothers, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBDocAuditfiveother detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBDocAuditfiveother dcaBDocAuditfiveother=this.iDcaBDocAuditfiveotherService.getById(id);
        return dcaBDocAuditfiveother;
        }
        }