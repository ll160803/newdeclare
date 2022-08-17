package cc.mrbird.febs.zq.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zq.service.IZqBCopyEmployService;
import cc.mrbird.febs.zq.entity.ZqBCopyEmploy;

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
@RequestMapping("zqBCopyEmploy")

public class ZqBCopyEmployController extends BaseController{

private String message;
@Autowired
public IZqBCopyEmployService iZqBCopyEmployService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'任职培养','/dca/ZqBCopyEmploy/ZqBCopyEmploy','dca/ZqBCopyEmploy/ZqBCopyEmploy','zqBCopyEmploy:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任职培养新增','zqBCopyEmploy:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任职培养编辑','zqBCopyEmploy:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'任职培养删除','zqBCopyEmploy:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param zqBCopyEmploy 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("zqBCopyEmploy:view")
public Map<String, Object> List(QueryRequest request, ZqBCopyEmploy zqBCopyEmploy){
        return getDataTable(this.iZqBCopyEmployService.findZqBCopyEmploys(request, zqBCopyEmploy));
        }

/**
 * 添加
 * @param  zqBCopyEmploy
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("zqBCopyEmploy:add")
public void addZqBCopyEmploy(@Valid ZqBCopyEmploy zqBCopyEmploy)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        zqBCopyEmploy.setCreateUserId(currentUser.getUserId());
        this.iZqBCopyEmployService.createZqBCopyEmploy(zqBCopyEmploy);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param zqBCopyEmploy
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("zqBCopyEmploy:update")
public void updateZqBCopyEmploy(@Valid ZqBCopyEmploy zqBCopyEmploy)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      zqBCopyEmploy.setModifyUserId(currentUser.getUserId());
        this.iZqBCopyEmployService.updateZqBCopyEmploy(zqBCopyEmploy);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("zqBCopyEmploy:delete")
public void deleteZqBCopyEmploys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iZqBCopyEmployService.deleteZqBCopyEmploys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("zqBCopyEmploy:export")
public void export(QueryRequest request, ZqBCopyEmploy zqBCopyEmploy, HttpServletResponse response) throws FebsException {
        try {
        List<ZqBCopyEmploy> zqBCopyEmploys = this.iZqBCopyEmployService.findZqBCopyEmploys(request, zqBCopyEmploy).getRecords();
        ExcelKit.$Export(ZqBCopyEmploy.class, response).downXlsx(zqBCopyEmploys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ZqBCopyEmploy detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ZqBCopyEmploy zqBCopyEmploy=this.iZqBCopyEmployService.getById(id);
        return zqBCopyEmploy;
        }
        }