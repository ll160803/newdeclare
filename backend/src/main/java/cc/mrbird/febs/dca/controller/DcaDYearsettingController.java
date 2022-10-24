package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaDYearsettingService;
import cc.mrbird.febs.dca.entity.DcaDYearsetting;

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
 * @since 2022-10-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaDYearsetting")

public class DcaDYearsettingController extends BaseController{

private String message;
@Autowired
public IDcaDYearsettingService iDcaDYearsettingService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'申报年度设置','/dca/DcaDYearsetting/DcaDYearsetting','dca/DcaDYearsetting/DcaDYearsetting','dcaDYearsetting:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'申报年度设置新增','dcaDYearsetting:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'申报年度设置编辑','dcaDYearsetting:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'申报年度设置删除','dcaDYearsetting:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaDYearsetting 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaDYearsetting:view")
public Map<String, Object> List(QueryRequest request, DcaDYearsetting dcaDYearsetting){
        return getDataTable(this.iDcaDYearsettingService.findDcaDYearsettings(request, dcaDYearsetting));
        }

/**
 * 添加
 * @param  dcaDYearsetting
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaDYearsetting:add")
public void addDcaDYearsetting(@Valid DcaDYearsetting dcaDYearsetting)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaDYearsetting.setCreateUserId(currentUser.getUserId());
        this.iDcaDYearsettingService.createDcaDYearsetting(dcaDYearsetting);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaDYearsetting
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaDYearsetting:update")
public void updateDcaDYearsetting(@Valid DcaDYearsetting dcaDYearsetting)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaDYearsetting.setModifyUserId(currentUser.getUserId());
        this.iDcaDYearsettingService.updateDcaDYearsetting(dcaDYearsetting);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaDYearsetting:delete")
public void deleteDcaDYearsettings(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaDYearsettingService.deleteDcaDYearsettings(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaDYearsetting:export")
public void export(QueryRequest request, DcaDYearsetting dcaDYearsetting, HttpServletResponse response) throws FebsException {
        try {
        List<DcaDYearsetting> dcaDYearsettings = this.iDcaDYearsettingService.findDcaDYearsettings(request, dcaDYearsetting).getRecords();
        ExcelKit.$Export(DcaDYearsetting.class, response).downXlsx(dcaDYearsettings, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaDYearsetting detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaDYearsetting dcaDYearsetting=this.iDcaDYearsettingService.getById(id);
        return dcaDYearsetting;
        }
        }