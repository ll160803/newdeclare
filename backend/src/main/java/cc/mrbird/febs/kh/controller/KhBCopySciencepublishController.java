package cc.mrbird.febs.kh.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.kh.service.IKhBCopySciencepublishService;
import cc.mrbird.febs.kh.entity.KhBCopySciencepublish;

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
@RequestMapping("khBCopySciencepublish")

public class KhBCopySciencepublishController extends BaseController{

private String message;
@Autowired
public IKhBCopySciencepublishService iKhBCopySciencepublishService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'科研论文','/dca/KhBCopySciencepublish/KhBCopySciencepublish','dca/KhBCopySciencepublish/KhBCopySciencepublish','khBCopySciencepublish:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研论文新增','khBCopySciencepublish:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研论文编辑','khBCopySciencepublish:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'科研论文删除','khBCopySciencepublish:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param khBCopySciencepublish 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("khBCopySciencepublish:view")
public Map<String, Object> List(QueryRequest request, KhBCopySciencepublish khBCopySciencepublish){
        return getDataTable(this.iKhBCopySciencepublishService.findKhBCopySciencepublishs(request, khBCopySciencepublish));
        }

/**
 * 添加
 * @param  khBCopySciencepublish
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("khBCopySciencepublish:add")
public void addKhBCopySciencepublish(@Valid KhBCopySciencepublish khBCopySciencepublish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        khBCopySciencepublish.setCreateUserId(currentUser.getUserId());
        this.iKhBCopySciencepublishService.createKhBCopySciencepublish(khBCopySciencepublish);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param khBCopySciencepublish
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("khBCopySciencepublish:update")
public void updateKhBCopySciencepublish(@Valid KhBCopySciencepublish khBCopySciencepublish)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      khBCopySciencepublish.setModifyUserId(currentUser.getUserId());
        this.iKhBCopySciencepublishService.updateKhBCopySciencepublish(khBCopySciencepublish);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("khBCopySciencepublish:delete")
public void deleteKhBCopySciencepublishs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iKhBCopySciencepublishService.deleteKhBCopySciencepublishs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("khBCopySciencepublish:export")
public void export(QueryRequest request, KhBCopySciencepublish khBCopySciencepublish, HttpServletResponse response) throws FebsException {
        try {
        List<KhBCopySciencepublish> khBCopySciencepublishs = this.iKhBCopySciencepublishService.findKhBCopySciencepublishs(request, khBCopySciencepublish).getRecords();
        ExcelKit.$Export(KhBCopySciencepublish.class, response).downXlsx(khBCopySciencepublishs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public KhBCopySciencepublish detail(@NotBlank(message = "{required}") @PathVariable String id) {
    KhBCopySciencepublish khBCopySciencepublish=this.iKhBCopySciencepublishService.getById(id);
        return khBCopySciencepublish;
        }
        }