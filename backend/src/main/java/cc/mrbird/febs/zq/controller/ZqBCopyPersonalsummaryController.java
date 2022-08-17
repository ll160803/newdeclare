package cc.mrbird.febs.zq.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zq.service.IZqBCopyPersonalsummaryService;
import cc.mrbird.febs.zq.entity.ZqBCopyPersonalsummary;

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
@RequestMapping("zqBCopyPersonalsummary")

public class ZqBCopyPersonalsummaryController extends BaseController{

private String message;
@Autowired
public IZqBCopyPersonalsummaryService iZqBCopyPersonalsummaryService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'个人总结','/dca/ZqBCopyPersonalsummary/ZqBCopyPersonalsummary','dca/ZqBCopyPersonalsummary/ZqBCopyPersonalsummary','zqBCopyPersonalsummary:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人总结新增','zqBCopyPersonalsummary:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人总结编辑','zqBCopyPersonalsummary:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人总结删除','zqBCopyPersonalsummary:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param zqBCopyPersonalsummary 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("zqBCopyPersonalsummary:view")
public Map<String, Object> List(QueryRequest request, ZqBCopyPersonalsummary zqBCopyPersonalsummary){
        return getDataTable(this.iZqBCopyPersonalsummaryService.findZqBCopyPersonalsummarys(request, zqBCopyPersonalsummary));
        }

/**
 * 添加
 * @param  zqBCopyPersonalsummary
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("zqBCopyPersonalsummary:add")
public void addZqBCopyPersonalsummary(@Valid ZqBCopyPersonalsummary zqBCopyPersonalsummary)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        zqBCopyPersonalsummary.setCreateUserId(currentUser.getUserId());
        this.iZqBCopyPersonalsummaryService.createZqBCopyPersonalsummary(zqBCopyPersonalsummary);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param zqBCopyPersonalsummary
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("zqBCopyPersonalsummary:update")
public void updateZqBCopyPersonalsummary(@Valid ZqBCopyPersonalsummary zqBCopyPersonalsummary)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      zqBCopyPersonalsummary.setModifyUserId(currentUser.getUserId());
        this.iZqBCopyPersonalsummaryService.updateZqBCopyPersonalsummary(zqBCopyPersonalsummary);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("zqBCopyPersonalsummary:delete")
public void deleteZqBCopyPersonalsummarys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iZqBCopyPersonalsummaryService.deleteZqBCopyPersonalsummarys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("zqBCopyPersonalsummary:export")
public void export(QueryRequest request, ZqBCopyPersonalsummary zqBCopyPersonalsummary, HttpServletResponse response) throws FebsException {
        try {
        List<ZqBCopyPersonalsummary> zqBCopyPersonalsummarys = this.iZqBCopyPersonalsummaryService.findZqBCopyPersonalsummarys(request, zqBCopyPersonalsummary).getRecords();
        ExcelKit.$Export(ZqBCopyPersonalsummary.class, response).downXlsx(zqBCopyPersonalsummarys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ZqBCopyPersonalsummary detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ZqBCopyPersonalsummary zqBCopyPersonalsummary=this.iZqBCopyPersonalsummaryService.getById(id);
        return zqBCopyPersonalsummary;
        }
        }