package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopySciachievementService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciachievement;

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
@RequestMapping("dcaBCopySciachievement")

public class DcaBCopySciachievementController extends BaseController{

private String message;
@Autowired
public IDcaBCopySciachievementService iDcaBCopySciachievementService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'主要科研业绩','/dca/DcaBCopySciachievement/DcaBCopySciachievement','dca/DcaBCopySciachievement/DcaBCopySciachievement','dcaBCopySciachievement:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'主要科研业绩新增','dcaBCopySciachievement:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'主要科研业绩编辑','dcaBCopySciachievement:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'主要科研业绩删除','dcaBCopySciachievement:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopySciachievement 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopySciachievement:view")
public Map<String, Object> List(QueryRequest request, DcaBCopySciachievement dcaBCopySciachievement){
        return getDataTable(this.iDcaBCopySciachievementService.findDcaBCopySciachievements(request, dcaBCopySciachievement));
        }

/**
 * 添加
 * @param  dcaBCopySciachievement
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopySciachievement:add")
public void addDcaBCopySciachievement(@Valid DcaBCopySciachievement dcaBCopySciachievement)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopySciachievement.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopySciachievementService.createDcaBCopySciachievement(dcaBCopySciachievement);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopySciachievement
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopySciachievement:update")
public void updateDcaBCopySciachievement(@Valid DcaBCopySciachievement dcaBCopySciachievement)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopySciachievement.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopySciachievementService.updateDcaBCopySciachievement(dcaBCopySciachievement);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopySciachievement:delete")
public void deleteDcaBCopySciachievements(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopySciachievementService.deleteDcaBCopySciachievements(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopySciachievement:export")
public void export(QueryRequest request, DcaBCopySciachievement dcaBCopySciachievement, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopySciachievement> dcaBCopySciachievements = this.iDcaBCopySciachievementService.findDcaBCopySciachievements(request, dcaBCopySciachievement).getRecords();
        ExcelKit.$Export(DcaBCopySciachievement.class, response).downXlsx(dcaBCopySciachievements, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopySciachievement detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopySciachievement dcaBCopySciachievement=this.iDcaBCopySciachievementService.getById(id);
        return dcaBCopySciachievement;
        }
        }