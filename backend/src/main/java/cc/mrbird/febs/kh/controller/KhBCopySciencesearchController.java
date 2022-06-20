package cc.mrbird.febs.kh.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.kh.service.IKhBCopySciencesearchService;
import cc.mrbird.febs.kh.entity.KhBCopySciencesearch;

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
 * @since 2022-05-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("khBCopySciencesearch")

public class KhBCopySciencesearchController extends BaseController{

private String message;
@Autowired
public IKhBCopySciencesearchService iKhBCopySciencesearchService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'科研项目','/dca/KhBCopySciencesearch/KhBCopySciencesearch','dca/KhBCopySciencesearch/KhBCopySciencesearch','khBCopySciencesearch:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研项目新增','khBCopySciencesearch:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研项目编辑','khBCopySciencesearch:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研项目删除','khBCopySciencesearch:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param khBCopySciencesearch 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("khBCopySciencesearch:view")
public Map<String, Object> List(QueryRequest request, KhBCopySciencesearch khBCopySciencesearch){
        return getDataTable(this.iKhBCopySciencesearchService.findKhBCopySciencesearchs(request, khBCopySciencesearch));
        }

/**
 * 添加
 * @param  khBCopySciencesearch
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("khBCopySciencesearch:add")
public void addKhBCopySciencesearch(@Valid KhBCopySciencesearch khBCopySciencesearch)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        khBCopySciencesearch.setCreateUserId(currentUser.getUserId());
        this.iKhBCopySciencesearchService.createKhBCopySciencesearch(khBCopySciencesearch);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param khBCopySciencesearch
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("khBCopySciencesearch:update")
public void updateKhBCopySciencesearch(@Valid KhBCopySciencesearch khBCopySciencesearch)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      khBCopySciencesearch.setModifyUserId(currentUser.getUserId());
        this.iKhBCopySciencesearchService.updateKhBCopySciencesearch(khBCopySciencesearch);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("khBCopySciencesearch:delete")
public void deleteKhBCopySciencesearchs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iKhBCopySciencesearchService.deleteKhBCopySciencesearchs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("khBCopySciencesearch:export")
public void export(QueryRequest request, KhBCopySciencesearch khBCopySciencesearch, HttpServletResponse response) throws FebsException {
        try {
        List<KhBCopySciencesearch> khBCopySciencesearchs = this.iKhBCopySciencesearchService.findKhBCopySciencesearchs(request, khBCopySciencesearch).getRecords();
        ExcelKit.$Export(KhBCopySciencesearch.class, response).downXlsx(khBCopySciencesearchs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public KhBCopySciencesearch detail(@NotBlank(message = "{required}") @PathVariable String id) {
    KhBCopySciencesearch khBCopySciencesearch=this.iKhBCopySciencesearchService.getById(id);
        return khBCopySciencesearch;
        }
        }