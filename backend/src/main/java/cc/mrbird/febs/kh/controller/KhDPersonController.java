package cc.mrbird.febs.kh.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.kh.service.IKhDPersonService;
import cc.mrbird.febs.kh.entity.KhDPerson;

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
@RequestMapping("khDPerson")

public class KhDPersonController extends BaseController{

private String message;
@Autowired
public IKhDPersonService iKhDPersonService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'被打分人','/dca/KhDPerson/KhDPerson','dca/KhDPerson/KhDPerson','khDPerson:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'被打分人新增','khDPerson:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'被打分人编辑','khDPerson:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'被打分人删除','khDPerson:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param khDPerson 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("khDPerson:view")
public Map<String, Object> List(QueryRequest request, KhDPerson khDPerson){
        return getDataTable(this.iKhDPersonService.findKhDPersons(request, khDPerson));
        }

/**
 * 添加
 * @param  khDPerson
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("khDPerson:add")
public void addKhDPerson(@Valid KhDPerson khDPerson)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        khDPerson.setCreateUserId(currentUser.getUserId());
        this.iKhDPersonService.createKhDPerson(khDPerson);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param khDPerson
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("khDPerson:update")
public void updateKhDPerson(@Valid KhDPerson khDPerson)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      khDPerson.setModifyUserId(currentUser.getUserId());
        this.iKhDPersonService.updateKhDPerson(khDPerson);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("khDPerson:delete")
public void deleteKhDPersons(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iKhDPersonService.deleteKhDPersons(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("khDPerson:export")
public void export(QueryRequest request, KhDPerson khDPerson, HttpServletResponse response) throws FebsException {
        try {
        List<KhDPerson> khDPersons = this.iKhDPersonService.findKhDPersons(request, khDPerson).getRecords();
        ExcelKit.$Export(KhDPerson.class, response).downXlsx(khDPersons, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public KhDPerson detail(@NotBlank(message = "{required}") @PathVariable String id) {
    KhDPerson khDPerson=this.iKhDPersonService.getById(id);
        return khDPerson;
        }
        }