package cc.mrbird.febs.zq.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zq.service.IZqBCopyLastemployService;
import cc.mrbird.febs.zq.entity.ZqBCopyLastemploy;

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
@RequestMapping("zqBCopyLastemploy")

public class ZqBCopyLastemployController extends BaseController{

private String message;
@Autowired
public IZqBCopyLastemployService iZqBCopyLastemployService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'完成上一聘期','/dca/ZqBCopyLastemploy/ZqBCopyLastemploy','dca/ZqBCopyLastemploy/ZqBCopyLastemploy','zqBCopyLastemploy:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'完成上一聘期新增','zqBCopyLastemploy:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'完成上一聘期编辑','zqBCopyLastemploy:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'完成上一聘期删除','zqBCopyLastemploy:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param zqBCopyLastemploy 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("zqBCopyLastemploy:view")
public Map<String, Object> List(QueryRequest request, ZqBCopyLastemploy zqBCopyLastemploy){
        return getDataTable(this.iZqBCopyLastemployService.findZqBCopyLastemploys(request, zqBCopyLastemploy));
        }

/**
 * 添加
 * @param  zqBCopyLastemploy
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("zqBCopyLastemploy:add")
public void addZqBCopyLastemploy(@Valid ZqBCopyLastemploy zqBCopyLastemploy)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        zqBCopyLastemploy.setCreateUserId(currentUser.getUserId());
        this.iZqBCopyLastemployService.createZqBCopyLastemploy(zqBCopyLastemploy);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param zqBCopyLastemploy
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("zqBCopyLastemploy:update")
public void updateZqBCopyLastemploy(@Valid ZqBCopyLastemploy zqBCopyLastemploy)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      zqBCopyLastemploy.setModifyUserId(currentUser.getUserId());
        this.iZqBCopyLastemployService.updateZqBCopyLastemploy(zqBCopyLastemploy);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("zqBCopyLastemploy:delete")
public void deleteZqBCopyLastemploys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iZqBCopyLastemployService.deleteZqBCopyLastemploys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("zqBCopyLastemploy:export")
public void export(QueryRequest request, ZqBCopyLastemploy zqBCopyLastemploy, HttpServletResponse response) throws FebsException {
        try {
        List<ZqBCopyLastemploy> zqBCopyLastemploys = this.iZqBCopyLastemployService.findZqBCopyLastemploys(request, zqBCopyLastemploy).getRecords();
        ExcelKit.$Export(ZqBCopyLastemploy.class, response).downXlsx(zqBCopyLastemploys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ZqBCopyLastemploy detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ZqBCopyLastemploy zqBCopyLastemploy=this.iZqBCopyLastemployService.getById(id);
        return zqBCopyLastemploy;
        }
        }