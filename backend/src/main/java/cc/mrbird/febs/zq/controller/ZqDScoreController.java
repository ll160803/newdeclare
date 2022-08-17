package cc.mrbird.febs.zq.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;


import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.zq.entity.*;
import cc.mrbird.febs.zq.service.*;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2022-06-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zqDScore")

public class ZqDScoreController extends BaseController {

    private String message;
    @Autowired
    public IZqDScoreService iZqDScoreService;

    @Autowired
    private IZqBCopySciencepublishService iDcaBSciencepublishService;
    @Autowired
    private IZqBCopySciencesearchService iDcaBSciencesearchService;
    @Autowired
    private IZqBCopyPersonalsummaryService iDcaBPersonalsummaryService;

    @Autowired
    private IZqBCopyPublicarticleService iKhBCopyPublicarticleService;
    @Autowired
    private IZqBCopyScientificprizeService iKhBCopyScientificprizeService;

    @Autowired
    private  IZqBCopyLastemployService iZqBCopyLastemployService;
    @Autowired
    private IZqBCopyGoalService iZqBCopyGoalService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'打分记录','/dca/ZqDScore/ZqDScore','dca/ZqDScore/ZqDScore','zqDScore:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'打分记录新增','zqDScore:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'打分记录编辑','zqDScore:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'打分记录删除','zqDScore:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request  分页信息
     * @param zqDScore 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("zqDScore:view")
    public Map<String, Object> List(QueryRequest request, ZqDScore zqDScore) {
        return getDataTable(this.iZqDScoreService.findZqDScores(request, zqDScore));
    }

    @GetMapping("user")
    public Map<String, Object> List2(QueryRequest request, ZqDScore zqDScore) {
        User currentUser = FebsUtil.getCurrentUser();
        zqDScore.setAuditUserAccount(currentUser.getUsername());
        return getDataTable(this.iZqDScoreService.findAllUserInfo(request, zqDScore));
    }

    @GetMapping("score")
    public Map<String, Object> List3(QueryRequest request, ZqDScore zqDScore) {

        return getDataTable(this.iZqDScoreService.findAllUserInfo(request, zqDScore));
    }

    /**
     * 添加
     *
     * @param zqDScore
     * @return
     */
    @Log("新增/按钮")
    @PutMapping("update")
    public void addZqDScore(@Valid ZqDScore zqDScore) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            zqDScore.setCreateUserId(currentUser.getUserId());
            this.iZqDScoreService.updateZqDScore(zqDScore);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @return
     */
    @Log("修改")
    @PutMapping
    public void updateZqDScore(String startDate, String year) throws FebsException {
        try {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("startDate", startDate);
            map.put("dcaYear", year);

            this.iZqDScoreService.insert(map);
        } catch (Exception e) {
            message = "设置失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("zqDScore:delete")
    public void deleteZqDScores(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iZqDScoreService.deleteZqDScores(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    public void export(QueryRequest request, ZqDScore zqDScore, String dataJson, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(10000);

            List<ZqUser> dcaBSciencepublishList = this.iZqDScoreService.findAllUserInfo(request, zqDScore).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList, dataJson, "");
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ZqDScore detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ZqDScore zqDScore = this.iZqDScoreService.getById(id);
        return zqDScore;
    }
    @GetMapping("getBaseInfo/{userAccount}")
    public UserInfo getBaseInfo(@NotBlank(message = "{required}") @PathVariable String userAccount, String year) {
        UserInfo userInfo = new UserInfo();
        LambdaQueryWrapper<ZqBCopySciencepublish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ZqBCopySciencepublish::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.eq(ZqBCopySciencepublish::getUserAccount, userAccount);
        queryWrapper.eq(ZqBCopySciencepublish::getDcaYear, year);
        List<ZqBCopySciencepublish> academicList = this.iDcaBSciencepublishService.list(queryWrapper);

        LambdaQueryWrapper<ZqBCopySciencesearch> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(ZqBCopySciencesearch::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper2.eq(ZqBCopySciencesearch::getUserAccount, userAccount);
        queryWrapper2.eq(ZqBCopySciencesearch::getDcaYear, year);
        List<ZqBCopySciencesearch> eduList = this.iDcaBSciencesearchService.list(queryWrapper2);

        LambdaQueryWrapper<ZqBCopyPersonalsummary> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.eq(ZqBCopyPersonalsummary::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper3.eq(ZqBCopyPersonalsummary::getUserAccount, userAccount);
        queryWrapper3.eq(ZqBCopyPersonalsummary::getDcaYear, year);
        List<ZqBCopyPersonalsummary> boradList = this.iDcaBPersonalsummaryService.list(queryWrapper3);

        LambdaQueryWrapper<ZqBCopyPublicarticle> queryWrapper4 = new LambdaQueryWrapper<>();
        queryWrapper4.eq(ZqBCopyPublicarticle::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper4.eq(ZqBCopyPublicarticle::getUserAccount, userAccount);
        queryWrapper4.eq(ZqBCopyPublicarticle::getDcaYear, year);
        List<ZqBCopyPublicarticle> atrticle = this.iKhBCopyPublicarticleService.list(queryWrapper4);


        LambdaQueryWrapper<ZqBCopyScientificprize> queryWrapper5 = new LambdaQueryWrapper<>();
        queryWrapper5.eq(ZqBCopyScientificprize::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper5.eq(ZqBCopyScientificprize::getUserAccount, userAccount);
        queryWrapper5.eq(ZqBCopyScientificprize::getDcaYear, year);
        List<ZqBCopyScientificprize> prize = this.iKhBCopyScientificprizeService.list(queryWrapper5);


        LambdaQueryWrapper<ZqBCopyLastemploy> queryWrapper6 = new LambdaQueryWrapper<>();
        queryWrapper6.eq(ZqBCopyLastemploy::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper6.eq(ZqBCopyLastemploy::getUserAccount, userAccount);
        queryWrapper6.eq(ZqBCopyLastemploy::getDcaYear, year);
        List<ZqBCopyLastemploy> employList = this.iZqBCopyLastemployService.list(queryWrapper6);

        LambdaQueryWrapper<ZqBCopyGoal> queryWrapper7 = new LambdaQueryWrapper<>();
        queryWrapper7.eq(ZqBCopyGoal::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper7.eq(ZqBCopyGoal::getUserAccount, userAccount);
        queryWrapper7.eq(ZqBCopyGoal::getDcaYear, year);
        List<ZqBCopyGoal> goalList = this.iZqBCopyGoalService.list(queryWrapper7);


        userInfo.setPublishList(academicList);
        userInfo.setSciencesearchList(eduList);
        userInfo.setPublicarticleList(atrticle);
        userInfo.setScientificprizeList(prize);
        userInfo.setSummary(boradList.size() > 0 ? boradList.get(0).getPsContent() : "");
        userInfo.setEmploy(employList.size() > 0 ? employList.get(0).getLastContent() : "");
        userInfo.setGoal(goalList.size() > 0 ? goalList.get(0).getPreGoal() : "");
        return userInfo;
    }

}