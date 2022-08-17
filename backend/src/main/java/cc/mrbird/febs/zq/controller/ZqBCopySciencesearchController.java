package cc.mrbird.febs.zq.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zq.service.IZqBCopySciencesearchService;
import cc.mrbird.febs.zq.entity.ZqBCopySciencesearch;

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
@RequestMapping("zqBCopySciencesearch")

public class ZqBCopySciencesearchController extends BaseController{

private String message;
@Autowired
public IZqBCopySciencesearchService iZqBCopySciencesearchService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'科研项目','/dca/ZqBCopySciencesearch/ZqBCopySciencesearch','dca/ZqBCopySciencesearch/ZqBCopySciencesearch','zqBCopySciencesearch:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研项目新增','zqBCopySciencesearch:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研项目编辑','zqBCopySciencesearch:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研项目删除','zqBCopySciencesearch:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param zqBCopySciencesearch 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("zqBCopySciencesearch:view")
public Map<String, Object> List(QueryRequest request, ZqBCopySciencesearch zqBCopySciencesearch){
        return getDataTable(this.iZqBCopySciencesearchService.findZqBCopySciencesearchs(request, zqBCopySciencesearch));
        }

/**
 * 添加
 * @param  zqBCopySciencesearch
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("zqBCopySciencesearch:add")
public void addZqBCopySciencesearch(@Valid ZqBCopySciencesearch zqBCopySciencesearch)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        zqBCopySciencesearch.setCreateUserId(currentUser.getUserId());
        this.iZqBCopySciencesearchService.createZqBCopySciencesearch(zqBCopySciencesearch);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param zqBCopySciencesearch
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("zqBCopySciencesearch:update")
public void updateZqBCopySciencesearch(@Valid ZqBCopySciencesearch zqBCopySciencesearch)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      zqBCopySciencesearch.setModifyUserId(currentUser.getUserId());
        this.iZqBCopySciencesearchService.updateZqBCopySciencesearch(zqBCopySciencesearch);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("zqBCopySciencesearch:delete")
public void deleteZqBCopySciencesearchs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iZqBCopySciencesearchService.deleteZqBCopySciencesearchs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("zqBCopySciencesearch:export")
public void export(QueryRequest request, ZqBCopySciencesearch zqBCopySciencesearch, HttpServletResponse response) throws FebsException {
        try {
        List<ZqBCopySciencesearch> zqBCopySciencesearchs = this.iZqBCopySciencesearchService.findZqBCopySciencesearchs(request, zqBCopySciencesearch).getRecords();
        ExcelKit.$Export(ZqBCopySciencesearch.class, response).downXlsx(zqBCopySciencesearchs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ZqBCopySciencesearch detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ZqBCopySciencesearch zqBCopySciencesearch=this.iZqBCopySciencesearchService.getById(id);
        return zqBCopySciencesearch;
        }
        }