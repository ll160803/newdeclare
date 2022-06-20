package cc.mrbird.febs.kh.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.kh.service.IKhBCopyPersonalsummaryService;
import cc.mrbird.febs.kh.entity.KhBCopyPersonalsummary;

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
@RequestMapping("khBCopyPersonalsummary")

public class KhBCopyPersonalsummaryController extends BaseController{

private String message;
@Autowired
public IKhBCopyPersonalsummaryService iKhBCopyPersonalsummaryService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'个人总结','/dca/KhBCopyPersonalsummary/KhBCopyPersonalsummary','dca/KhBCopyPersonalsummary/KhBCopyPersonalsummary','khBCopyPersonalsummary:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人总结新增','khBCopyPersonalsummary:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人总结编辑','khBCopyPersonalsummary:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'个人总结删除','khBCopyPersonalsummary:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param khBCopyPersonalsummary 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("khBCopyPersonalsummary:view")
public Map<String, Object> List(QueryRequest request, KhBCopyPersonalsummary khBCopyPersonalsummary){
        return getDataTable(this.iKhBCopyPersonalsummaryService.findKhBCopyPersonalsummarys(request, khBCopyPersonalsummary));
        }

/**
 * 添加
 * @param  khBCopyPersonalsummary
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("khBCopyPersonalsummary:add")
public void addKhBCopyPersonalsummary(@Valid KhBCopyPersonalsummary khBCopyPersonalsummary)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        khBCopyPersonalsummary.setCreateUserId(currentUser.getUserId());
        this.iKhBCopyPersonalsummaryService.createKhBCopyPersonalsummary(khBCopyPersonalsummary);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param khBCopyPersonalsummary
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("khBCopyPersonalsummary:update")
public void updateKhBCopyPersonalsummary(@Valid KhBCopyPersonalsummary khBCopyPersonalsummary)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      khBCopyPersonalsummary.setModifyUserId(currentUser.getUserId());
        this.iKhBCopyPersonalsummaryService.updateKhBCopyPersonalsummary(khBCopyPersonalsummary);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("khBCopyPersonalsummary:delete")
public void deleteKhBCopyPersonalsummarys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iKhBCopyPersonalsummaryService.deleteKhBCopyPersonalsummarys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("khBCopyPersonalsummary:export")
public void export(QueryRequest request, KhBCopyPersonalsummary khBCopyPersonalsummary, HttpServletResponse response) throws FebsException {
        try {
        List<KhBCopyPersonalsummary> khBCopyPersonalsummarys = this.iKhBCopyPersonalsummaryService.findKhBCopyPersonalsummarys(request, khBCopyPersonalsummary).getRecords();
        ExcelKit.$Export(KhBCopyPersonalsummary.class, response).downXlsx(khBCopyPersonalsummarys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public KhBCopyPersonalsummary detail(@NotBlank(message = "{required}") @PathVariable String id) {
    KhBCopyPersonalsummary khBCopyPersonalsummary=this.iKhBCopyPersonalsummaryService.getById(id);
        return khBCopyPersonalsummary;
        }
        }