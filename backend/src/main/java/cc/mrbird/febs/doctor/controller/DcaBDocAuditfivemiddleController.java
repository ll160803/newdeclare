package cc.mrbird.febs.doctor.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.doctor.service.IDcaBDocAuditfivemiddleService;
import cc.mrbird.febs.doctor.entity.DcaBDocAuditfivemiddle;

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
@RequestMapping("dcaBDocAuditfivemiddle")

public class DcaBDocAuditfivemiddleController extends BaseController{

private String message;
@Autowired
public IDcaBDocAuditfivemiddleService iDcaBDocAuditfivemiddleService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'中期考核','/dca/DcaBDocAuditfivemiddle/DcaBDocAuditfivemiddle','dca/DcaBDocAuditfivemiddle/DcaBDocAuditfivemiddle','dcaBDocAuditfivemiddle:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'中期考核新增','dcaBDocAuditfivemiddle:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'中期考核编辑','dcaBDocAuditfivemiddle:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'中期考核删除','dcaBDocAuditfivemiddle:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBDocAuditfivemiddle 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle){
        return getDataTable(this.iDcaBDocAuditfivemiddleService.findDcaBDocAuditfivemiddles(request, dcaBDocAuditfivemiddle));
        }
    @GetMapping("custom")
    public Map<String, Object> ListCustom(QueryRequest request, DcaBDocAuditfivemiddle dcaBDocEmploy){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBDocEmploy.setUserAccount(currentUser.getUsername());
        dcaBDocEmploy.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAuditfivemiddleService.findDcaBDocAuditfivemiddles(request, dcaBDocEmploy));
    }
    @GetMapping("audit")
    public Map<String, Object> List2(QueryRequest request, DcaBDocAuditfivemiddle dcaBDocEmploy){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBDocEmploy.setIsDeletemark(1);
        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBDocAuditfivemiddleService.findDcaBDocAuditfivemiddles(request, dcaBDocEmploy));
    }
/**
 * 添加
 * @param  dcaBDocAuditfivemiddle
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaBDocAuditfivemiddle(@Valid DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBDocAuditfivemiddle.setCreateUserId(currentUser.getUserId());
        this.iDcaBDocAuditfivemiddleService.createDcaBDocAuditfivemiddle(dcaBDocAuditfivemiddle);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBDocAuditfivemiddle
 * @return
 */
@Log("修改")
@PutMapping
public void updateDcaBDocAuditfivemiddle(@Valid DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBDocAuditfivemiddle.setModifyUserId(currentUser.getUserId());
        this.iDcaBDocAuditfivemiddleService.updateDcaBDocAuditfivemiddle(dcaBDocAuditfivemiddle);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
public void deleteDcaBDocAuditfivemiddles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBDocAuditfivemiddleService.deleteDcaBDocAuditfivemiddles(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBDocAuditfivemiddle> dcaBDocAuditfivemiddles = this.iDcaBDocAuditfivemiddleService.findDcaBDocAuditfivemiddles(request, dcaBDocAuditfivemiddle).getRecords();
        ExcelKit.$Export(DcaBDocAuditfivemiddle.class, response).downXlsx(dcaBDocAuditfivemiddles, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBDocAuditfivemiddle detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBDocAuditfivemiddle dcaBDocAuditfivemiddle=this.iDcaBDocAuditfivemiddleService.getById(id);
        return dcaBDocAuditfivemiddle;
        }
        }