package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopySureachievementService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySureachievement;

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
 * @since 2021-09-30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBCopySureachievement")

public class DcaBCopySureachievementController extends BaseController{

private String message;
@Autowired
public IDcaBCopySureachievementService iDcaBCopySureachievementService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'医疗认可','/dca/DcaBCopySureachievement/DcaBCopySureachievement','dca/DcaBCopySureachievement/DcaBCopySureachievement','dcaBCopySureachievement:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'医疗认可新增','dcaBCopySureachievement:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'医疗认可编辑','dcaBCopySureachievement:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'医疗认可删除','dcaBCopySureachievement:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopySureachievement 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopySureachievement:view")
public Map<String, Object> List(QueryRequest request, DcaBCopySureachievement dcaBCopySureachievement){
        return getDataTable(this.iDcaBCopySureachievementService.findDcaBCopySureachievements(request, dcaBCopySureachievement));
        }

/**
 * 添加
 * @param  dcaBCopySureachievement
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopySureachievement:add")
public void addDcaBCopySureachievement(@Valid DcaBCopySureachievement dcaBCopySureachievement)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopySureachievement.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopySureachievementService.createDcaBCopySureachievement(dcaBCopySureachievement);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopySureachievement
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopySureachievement:update")
public void updateDcaBCopySureachievement(@Valid DcaBCopySureachievement dcaBCopySureachievement)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopySureachievement.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopySureachievementService.updateDcaBCopySureachievement(dcaBCopySureachievement);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopySureachievement:delete")
public void deleteDcaBCopySureachievements(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopySureachievementService.deleteDcaBCopySureachievements(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopySureachievement:export")
public void export(QueryRequest request, DcaBCopySureachievement dcaBCopySureachievement, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopySureachievement> dcaBCopySureachievements = this.iDcaBCopySureachievementService.findDcaBCopySureachievements(request, dcaBCopySureachievement).getRecords();
        ExcelKit.$Export(DcaBCopySureachievement.class, response).downXlsx(dcaBCopySureachievements, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopySureachievement detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopySureachievement dcaBCopySureachievement=this.iDcaBCopySureachievementService.getById(id);
        return dcaBCopySureachievement;
        }
        }