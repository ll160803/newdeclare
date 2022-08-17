package cc.mrbird.febs.zq.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zq.service.IZqBCopySciencepublishService;
import cc.mrbird.febs.zq.entity.ZqBCopySciencepublish;

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
@RequestMapping("zqBCopySciencepublish")

public class ZqBCopySciencepublishController extends BaseController{

private String message;
@Autowired
public IZqBCopySciencepublishService iZqBCopySciencepublishService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'科研论文','/dca/ZqBCopySciencepublish/ZqBCopySciencepublish','dca/ZqBCopySciencepublish/ZqBCopySciencepublish','zqBCopySciencepublish:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研论文新增','zqBCopySciencepublish:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研论文编辑','zqBCopySciencepublish:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研论文删除','zqBCopySciencepublish:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param zqBCopySciencepublish 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("zqBCopySciencepublish:view")
public Map<String, Object> List(QueryRequest request, ZqBCopySciencepublish zqBCopySciencepublish){
        return getDataTable(this.iZqBCopySciencepublishService.findZqBCopySciencepublishs(request, zqBCopySciencepublish));
        }

/**
 * 添加
 * @param  zqBCopySciencepublish
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("zqBCopySciencepublish:add")
public void addZqBCopySciencepublish(@Valid ZqBCopySciencepublish zqBCopySciencepublish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        zqBCopySciencepublish.setCreateUserId(currentUser.getUserId());
        this.iZqBCopySciencepublishService.createZqBCopySciencepublish(zqBCopySciencepublish);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param zqBCopySciencepublish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("zqBCopySciencepublish:update")
public void updateZqBCopySciencepublish(@Valid ZqBCopySciencepublish zqBCopySciencepublish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      zqBCopySciencepublish.setModifyUserId(currentUser.getUserId());
        this.iZqBCopySciencepublishService.updateZqBCopySciencepublish(zqBCopySciencepublish);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("zqBCopySciencepublish:delete")
public void deleteZqBCopySciencepublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iZqBCopySciencepublishService.deleteZqBCopySciencepublishs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("zqBCopySciencepublish:export")
public void export(QueryRequest request, ZqBCopySciencepublish zqBCopySciencepublish, HttpServletResponse response) throws FebsException {
        try {
        List<ZqBCopySciencepublish> zqBCopySciencepublishs = this.iZqBCopySciencepublishService.findZqBCopySciencepublishs(request, zqBCopySciencepublish).getRecords();
        ExcelKit.$Export(ZqBCopySciencepublish.class, response).downXlsx(zqBCopySciencepublishs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ZqBCopySciencepublish detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ZqBCopySciencepublish zqBCopySciencepublish=this.iZqBCopySciencepublishService.getById(id);
        return zqBCopySciencepublish;
        }
        }