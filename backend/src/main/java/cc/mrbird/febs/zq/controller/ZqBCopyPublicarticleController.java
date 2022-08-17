package cc.mrbird.febs.zq.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.zq.service.IZqBCopyPublicarticleService;
import cc.mrbird.febs.zq.entity.ZqBCopyPublicarticle;

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
@RequestMapping("zqBCopyPublicarticle")

public class ZqBCopyPublicarticleController extends BaseController{

private String message;
@Autowired
public IZqBCopyPublicarticleService iZqBCopyPublicarticleService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/ZqBCopyPublicarticle/ZqBCopyPublicarticle','dca/ZqBCopyPublicarticle/ZqBCopyPublicarticle','zqBCopyPublicarticle:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','zqBCopyPublicarticle:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','zqBCopyPublicarticle:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','zqBCopyPublicarticle:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param zqBCopyPublicarticle 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("zqBCopyPublicarticle:view")
public Map<String, Object> List(QueryRequest request, ZqBCopyPublicarticle zqBCopyPublicarticle){
        return getDataTable(this.iZqBCopyPublicarticleService.findZqBCopyPublicarticles(request, zqBCopyPublicarticle));
        }

/**
 * 添加
 * @param  zqBCopyPublicarticle
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("zqBCopyPublicarticle:add")
public void addZqBCopyPublicarticle(@Valid ZqBCopyPublicarticle zqBCopyPublicarticle)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        zqBCopyPublicarticle.setCreateUserId(currentUser.getUserId());
        this.iZqBCopyPublicarticleService.createZqBCopyPublicarticle(zqBCopyPublicarticle);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param zqBCopyPublicarticle
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("zqBCopyPublicarticle:update")
public void updateZqBCopyPublicarticle(@Valid ZqBCopyPublicarticle zqBCopyPublicarticle)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      zqBCopyPublicarticle.setModifyUserId(currentUser.getUserId());
        this.iZqBCopyPublicarticleService.updateZqBCopyPublicarticle(zqBCopyPublicarticle);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("zqBCopyPublicarticle:delete")
public void deleteZqBCopyPublicarticles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iZqBCopyPublicarticleService.deleteZqBCopyPublicarticles(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("zqBCopyPublicarticle:export")
public void export(QueryRequest request, ZqBCopyPublicarticle zqBCopyPublicarticle, HttpServletResponse response) throws FebsException {
        try {
        List<ZqBCopyPublicarticle> zqBCopyPublicarticles = this.iZqBCopyPublicarticleService.findZqBCopyPublicarticles(request, zqBCopyPublicarticle).getRecords();
        ExcelKit.$Export(ZqBCopyPublicarticle.class, response).downXlsx(zqBCopyPublicarticles, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public ZqBCopyPublicarticle detail(@NotBlank(message = "{required}") @PathVariable String id) {
    ZqBCopyPublicarticle zqBCopyPublicarticle=this.iZqBCopyPublicarticleService.getById(id);
        return zqBCopyPublicarticle;
        }
        }