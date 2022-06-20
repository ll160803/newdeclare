package cc.mrbird.febs.kh.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.kh.service.IKhBCopyScientificprizeService;
import cc.mrbird.febs.kh.entity.KhBCopyScientificprize;

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
 * @since 2022-05-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("khBCopyScientificprize")

public class KhBCopyScientificprizeController extends BaseController{

private String message;
@Autowired
public IKhBCopyScientificprizeService iKhBCopyScientificprizeService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'自任职以来科研获奖情况','/dca/KhBCopyScientificprize/KhBCopyScientificprize','dca/KhBCopyScientificprize/KhBCopyScientificprize','khBCopyScientificprize:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'自任职以来科研获奖情况新增','khBCopyScientificprize:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'自任职以来科研获奖情况编辑','khBCopyScientificprize:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'自任职以来科研获奖情况删除','khBCopyScientificprize:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param khBCopyScientificprize 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("khBCopyScientificprize:view")
public Map<String, Object> List(QueryRequest request, KhBCopyScientificprize khBCopyScientificprize){
        return getDataTable(this.iKhBCopyScientificprizeService.findKhBCopyScientificprizes(request, khBCopyScientificprize));
        }

/**
 * 添加
 * @param  khBCopyScientificprize
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("khBCopyScientificprize:add")
public void addKhBCopyScientificprize(@Valid KhBCopyScientificprize khBCopyScientificprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        khBCopyScientificprize.setCreateUserId(currentUser.getUserId());
        this.iKhBCopyScientificprizeService.createKhBCopyScientificprize(khBCopyScientificprize);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param khBCopyScientificprize
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("khBCopyScientificprize:update")
public void updateKhBCopyScientificprize(@Valid KhBCopyScientificprize khBCopyScientificprize)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      khBCopyScientificprize.setModifyUserId(currentUser.getUserId());
        this.iKhBCopyScientificprizeService.updateKhBCopyScientificprize(khBCopyScientificprize);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("khBCopyScientificprize:delete")
public void deleteKhBCopyScientificprizes(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iKhBCopyScientificprizeService.deleteKhBCopyScientificprizes(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("khBCopyScientificprize:export")
public void export(QueryRequest request, KhBCopyScientificprize khBCopyScientificprize, HttpServletResponse response) throws FebsException {
        try {
        List<KhBCopyScientificprize> khBCopyScientificprizes = this.iKhBCopyScientificprizeService.findKhBCopyScientificprizes(request, khBCopyScientificprize).getRecords();
        ExcelKit.$Export(KhBCopyScientificprize.class, response).downXlsx(khBCopyScientificprizes, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public KhBCopyScientificprize detail(@NotBlank(message = "{required}") @PathVariable String id) {
    KhBCopyScientificprize khBCopyScientificprize=this.iKhBCopyScientificprizeService.getById(id);
        return khBCopyScientificprize;
        }
        }