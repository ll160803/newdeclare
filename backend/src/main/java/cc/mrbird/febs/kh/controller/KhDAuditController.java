package cc.mrbird.febs.kh.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.kh.service.IKhDAuditService;
import cc.mrbird.febs.kh.entity.KhDAudit;

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
 * @since 2022-05-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("khDAudit")

public class KhDAuditController extends BaseController{

private String message;
@Autowired
public IKhDAuditService iKhDAuditService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'打分人','/dca/KhDAudit/KhDAudit','dca/KhDAudit/KhDAudit','khDAudit:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'打分人新增','khDAudit:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'打分人编辑','khDAudit:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'打分人删除','khDAudit:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param khDAudit 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("khDAudit:view")
public Map<String, Object> List(QueryRequest request, KhDAudit khDAudit){
        return getDataTable(this.iKhDAuditService.findKhDAudits(request, khDAudit));
        }

/**
 * 添加
 * @param  khDAudit
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("khDAudit:add")
public void addKhDAudit(@Valid KhDAudit khDAudit)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        khDAudit.setCreateUserId(currentUser.getUserId());
        this.iKhDAuditService.createKhDAudit(khDAudit);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param khDAudit
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("khDAudit:update")
public void updateKhDAudit(@Valid KhDAudit khDAudit)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      khDAudit.setModifyUserId(currentUser.getUserId());
        this.iKhDAuditService.updateKhDAudit(khDAudit);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("khDAudit:delete")
public void deleteKhDAudits(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iKhDAuditService.deleteKhDAudits(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("khDAudit:export")
public void export(QueryRequest request, KhDAudit khDAudit, HttpServletResponse response) throws FebsException {
        try {
        List<KhDAudit> khDAudits = this.iKhDAuditService.findKhDAudits(request, khDAudit).getRecords();
        ExcelKit.$Export(KhDAudit.class, response).downXlsx(khDAudits, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public KhDAudit detail(@NotBlank(message = "{required}") @PathVariable String id) {
    KhDAudit khDAudit=this.iKhDAuditService.getById(id);
        return khDAudit;
        }
        }