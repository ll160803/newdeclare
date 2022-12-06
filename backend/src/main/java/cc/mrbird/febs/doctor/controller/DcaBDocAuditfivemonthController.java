package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocAuditfivemonthService;
import cc.mrbird.febs.doctor.entity.DcaBDocAuditfivemonth;

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
@RequestMapping("dcaBDocAuditfivemonth")

public class DcaBDocAuditfivemonthController extends BaseController{

private String message;
@Autowired
public IDcaBDocAuditfivemonthService iDcaBDocAuditfivemonthService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'月度考核','/dca/DcaBDocAuditfivemonth/DcaBDocAuditfivemonth','dca/DcaBDocAuditfivemonth/DcaBDocAuditfivemonth','dcaBDocAuditfivemonth:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'月度考核新增','dcaBDocAuditfivemonth:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'月度考核编辑','dcaBDocAuditfivemonth:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'月度考核删除','dcaBDocAuditfivemonth:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocAuditfivemonth 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth){
        return getDataTable(this.iDcaBDocAuditfivemonthService.findDcaBDocAuditfivemonths(request, dcaBDocAuditfivemonth));
        }
    @GetMapping("custom")
    public Map<String, Object> ListCustom(QueryRequest request, DcaBDocAuditfivemonth dcaBDocEmploy){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBDocEmploy.setUserAccount(currentUser.getUsername());
        dcaBDocEmploy.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAuditfivemonthService.findDcaBDocAuditfivemonths(request, dcaBDocEmploy));
    }
    @GetMapping("audit")
    public Map<String, Object> List2(QueryRequest request, DcaBDocAuditfivemonth dcaBDocEmploy){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBDocEmploy.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAuditfivemonthService.findDcaBDocAuditfivemonths(request, dcaBDocEmploy));
    }
/**
 * 添加
 * @param  dcaBDocAuditfivemonth
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocAuditfivemonth(@Valid DcaBDocAuditfivemonth dcaBDocAuditfivemonth)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBDocAuditfivemonth.setCreateUserId(currentUser.getUserId());
        this.iDcaBDocAuditfivemonthService.createDcaBDocAuditfivemonth(dcaBDocAuditfivemonth);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocAuditfivemonth
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBDocAuditfivemonth(@Valid DcaBDocAuditfivemonth dcaBDocAuditfivemonth)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBDocAuditfivemonth.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocAuditfivemonthService.updateDcaBDocAuditfivemonth(dcaBDocAuditfivemonth);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
public void deleteDcaBDocAuditfivemonths(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocAuditfivemonthService.deleteDcaBDocAuditfivemonths(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, DcaBDocAuditfivemonth dcaBDocAuditfivemonth, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBDocAuditfivemonth> dcaBDocAuditfivemonths = this.iDcaBDocAuditfivemonthService.findDcaBDocAuditfivemonths(request, dcaBDocAuditfivemonth).getRecords();
        ExcelKit.$Export(DcaBDocAuditfivemonth.class, response).downXlsx(dcaBDocAuditfivemonths, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBDocAuditfivemonth detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBDocAuditfivemonth dcaBDocAuditfivemonth=this.iDcaBDocAuditfivemonthService.getById(id);
        return dcaBDocAuditfivemonth;
        }
        }