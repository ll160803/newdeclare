package cc.mrbird.febs.zq.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zq.service.IZqBCopyGoalService;
import cc.mrbird.febs.zq.entity.ZqBCopyGoal;

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
 * @since 2022-06-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zqBCopyGoal")

public class ZqBCopyGoalController extends BaseController{

private String message;
@Autowired
public IZqBCopyGoalService iZqBCopyGoalService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/ZqBCopyGoal/ZqBCopyGoal','dca/ZqBCopyGoal/ZqBCopyGoal','zqBCopyGoal:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','zqBCopyGoal:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','zqBCopyGoal:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','zqBCopyGoal:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param zqBCopyGoal 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("zqBCopyGoal:view")
public Map<String, Object> List(QueryRequest request, ZqBCopyGoal zqBCopyGoal){
        return getDataTable(this.iZqBCopyGoalService.findZqBCopyGoals(request, zqBCopyGoal));
        }

/**
 * 添加
 * @param  zqBCopyGoal
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("zqBCopyGoal:add")
public void addZqBCopyGoal(@Valid ZqBCopyGoal zqBCopyGoal)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        zqBCopyGoal.setCreateUserId(currentUser.getUserId());
        this.iZqBCopyGoalService.createZqBCopyGoal(zqBCopyGoal);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param zqBCopyGoal
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("zqBCopyGoal:update")
public void updateZqBCopyGoal(@Valid ZqBCopyGoal zqBCopyGoal)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      zqBCopyGoal.setModifyUserId(currentUser.getUserId());
        this.iZqBCopyGoalService.updateZqBCopyGoal(zqBCopyGoal);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("zqBCopyGoal:delete")
public void deleteZqBCopyGoals(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iZqBCopyGoalService.deleteZqBCopyGoals(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("zqBCopyGoal:export")
public void export(QueryRequest request, ZqBCopyGoal zqBCopyGoal, HttpServletResponse response) throws FebsException {
        try {
        List<ZqBCopyGoal> zqBCopyGoals = this.iZqBCopyGoalService.findZqBCopyGoals(request, zqBCopyGoal).getRecords();
        ExcelKit.$Export(ZqBCopyGoal.class, response).downXlsx(zqBCopyGoals, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ZqBCopyGoal detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ZqBCopyGoal zqBCopyGoal=this.iZqBCopyGoalService.getById(id);
        return zqBCopyGoal;
        }
        }