package cc.mrbird.febs.zq.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zq.service.IZqBCopyScientificprizeService;
import cc.mrbird.febs.zq.entity.ZqBCopyScientificprize;

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
@RequestMapping("zqBCopyScientificprize")

public class ZqBCopyScientificprizeController extends BaseController{

private String message;
@Autowired
public IZqBCopyScientificprizeService iZqBCopyScientificprizeService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'自任职以来科研获奖情况','/dca/ZqBCopyScientificprize/ZqBCopyScientificprize','dca/ZqBCopyScientificprize/ZqBCopyScientificprize','zqBCopyScientificprize:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'自任职以来科研获奖情况新增','zqBCopyScientificprize:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'自任职以来科研获奖情况编辑','zqBCopyScientificprize:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'自任职以来科研获奖情况删除','zqBCopyScientificprize:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param zqBCopyScientificprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("zqBCopyScientificprize:view")
public Map<String, Object> List(QueryRequest request, ZqBCopyScientificprize zqBCopyScientificprize){
        return getDataTable(this.iZqBCopyScientificprizeService.findZqBCopyScientificprizes(request, zqBCopyScientificprize));
        }

/**
 * 添加
 * @param  zqBCopyScientificprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("zqBCopyScientificprize:add")
public void addZqBCopyScientificprize(@Valid ZqBCopyScientificprize zqBCopyScientificprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        zqBCopyScientificprize.setCreateUserId(currentUser.getUserId());
        this.iZqBCopyScientificprizeService.createZqBCopyScientificprize(zqBCopyScientificprize);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param zqBCopyScientificprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("zqBCopyScientificprize:update")
public void updateZqBCopyScientificprize(@Valid ZqBCopyScientificprize zqBCopyScientificprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      zqBCopyScientificprize.setModifyUserId(currentUser.getUserId());
        this.iZqBCopyScientificprizeService.updateZqBCopyScientificprize(zqBCopyScientificprize);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("zqBCopyScientificprize:delete")
public void deleteZqBCopyScientificprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iZqBCopyScientificprizeService.deleteZqBCopyScientificprizes(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("zqBCopyScientificprize:export")
public void export(QueryRequest request, ZqBCopyScientificprize zqBCopyScientificprize, HttpServletResponse response) throws FebsException {
        try {
        List<ZqBCopyScientificprize> zqBCopyScientificprizes = this.iZqBCopyScientificprizeService.findZqBCopyScientificprizes(request, zqBCopyScientificprize).getRecords();
        ExcelKit.$Export(ZqBCopyScientificprize.class, response).downXlsx(zqBCopyScientificprizes, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ZqBCopyScientificprize detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ZqBCopyScientificprize zqBCopyScientificprize=this.iZqBCopyScientificprizeService.getById(id);
        return zqBCopyScientificprize;
        }
        }