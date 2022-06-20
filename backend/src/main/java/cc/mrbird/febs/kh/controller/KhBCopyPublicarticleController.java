package cc.mrbird.febs.kh.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.kh.service.IKhBCopyPublicarticleService;
import cc.mrbird.febs.kh.entity.KhBCopyPublicarticle;

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
@RequestMapping("khBCopyPublicarticle")

public class KhBCopyPublicarticleController extends BaseController{

private String message;
@Autowired
public IKhBCopyPublicarticleService iKhBCopyPublicarticleService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/KhBCopyPublicarticle/KhBCopyPublicarticle','dca/KhBCopyPublicarticle/KhBCopyPublicarticle','khBCopyPublicarticle:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','khBCopyPublicarticle:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','khBCopyPublicarticle:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','khBCopyPublicarticle:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param khBCopyPublicarticle 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("khBCopyPublicarticle:view")
public Map<String, Object> List(QueryRequest request, KhBCopyPublicarticle khBCopyPublicarticle){
        return getDataTable(this.iKhBCopyPublicarticleService.findKhBCopyPublicarticles(request, khBCopyPublicarticle));
        }

/**
 * 添加
 * @param  khBCopyPublicarticle
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("khBCopyPublicarticle:add")
public void addKhBCopyPublicarticle(@Valid KhBCopyPublicarticle khBCopyPublicarticle)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        khBCopyPublicarticle.setCreateUserId(currentUser.getUserId());
        this.iKhBCopyPublicarticleService.createKhBCopyPublicarticle(khBCopyPublicarticle);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param khBCopyPublicarticle
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("khBCopyPublicarticle:update")
public void updateKhBCopyPublicarticle(@Valid KhBCopyPublicarticle khBCopyPublicarticle)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      khBCopyPublicarticle.setModifyUserId(currentUser.getUserId());
        this.iKhBCopyPublicarticleService.updateKhBCopyPublicarticle(khBCopyPublicarticle);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("khBCopyPublicarticle:delete")
public void deleteKhBCopyPublicarticles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iKhBCopyPublicarticleService.deleteKhBCopyPublicarticles(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("khBCopyPublicarticle:export")
public void export(QueryRequest request, KhBCopyPublicarticle khBCopyPublicarticle, HttpServletResponse response) throws FebsException {
        try {
        List<KhBCopyPublicarticle> khBCopyPublicarticles = this.iKhBCopyPublicarticleService.findKhBCopyPublicarticles(request, khBCopyPublicarticle).getRecords();
        ExcelKit.$Export(KhBCopyPublicarticle.class, response).downXlsx(khBCopyPublicarticles, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public KhBCopyPublicarticle detail(@NotBlank(message = "{required}") @PathVariable String id) {
    KhBCopyPublicarticle khBCopyPublicarticle=this.iKhBCopyPublicarticleService.getById(id);
        return khBCopyPublicarticle;
        }
        }