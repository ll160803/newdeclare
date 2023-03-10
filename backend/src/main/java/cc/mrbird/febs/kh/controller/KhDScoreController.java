package cc.mrbird.febs.kh.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.entity.DcaBPersonalsummary;
import cc.mrbird.febs.dca.entity.DcaBSciencepublish;
import cc.mrbird.febs.dca.entity.DcaBSciencesearch;
import cc.mrbird.febs.kh.entity.*;
import cc.mrbird.febs.dca.service.IDcaBMedicalaccidentService;
import cc.mrbird.febs.dca.service.IDcaBPersonalsummaryService;
import cc.mrbird.febs.dca.service.IDcaBSciencepublishService;
import cc.mrbird.febs.dca.service.IDcaBSciencesearchService;
import cc.mrbird.febs.kh.service.*;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
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
 * @since 2022-05-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("khDScore")

public class KhDScoreController extends BaseController {

    private String message;
    @Autowired
    public IKhDScoreService iKhDScoreService;
    @Autowired
    private IKhBCopySciencepublishService iDcaBSciencepublishService;
    @Autowired
    private IKhBCopySciencesearchService iDcaBSciencesearchService;
    @Autowired
    private IKhBCopyPersonalsummaryService iDcaBPersonalsummaryService;

    @Autowired
    private IKhBCopyPublicarticleService iKhBCopyPublicarticleService;
    @Autowired
    private IKhBCopyScientificprizeService iKhBCopyScientificprizeService;
/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'????????????','/dca/KhDScore/KhDScore','dca/KhDScore/KhDScore','khDScore:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'??????????????????','khDScore:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'??????????????????','khDScore:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'??????????????????','khDScore:delete',1,1,NOW())
 */


    /**
     * ??????????????????
     *
     * @param request  ????????????
     * @param khDScore ????????????
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, KhDScore khDScore) {
        return getDataTable(this.iKhDScoreService.findKhDScores(request, khDScore));
    }

    @GetMapping("user")
    public Map<String, Object> List2(QueryRequest request, KhDScore khDScore) {
        User currentUser = FebsUtil.getCurrentUser();
        khDScore.setAuditUserAccount(currentUser.getUsername());
        return getDataTable(this.iKhDScoreService.findAllUserInfo(request, khDScore));
    }

    @GetMapping("score")
    public Map<String, Object> List3(QueryRequest request, KhDScore khDScore) {
        return getDataTable(this.iKhDScoreService.getUserInfoReport(request, khDScore));
    }

    /**
     * ??????
     *
     * @param khDScore
     * @return
     */
    @Log("??????/??????")
    @PostMapping
    public void addKhDScore(@Valid KhDScore khDScore) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            khDScore.setCreateUserId(currentUser.getUserId());
            this.iKhDScoreService.createKhDScore(khDScore);
        } catch (Exception e) {
            message = "??????/????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * ??????
     *
     * @param
     * @return
     */
    @Log("??????")
    @PutMapping
    public void updateKhDScore(String startDate, String year) throws FebsException {
        try {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("startDate", startDate);
            map.put("dcaYear", year);

            this.iKhDScoreService.insert(map);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("??????")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("khDScore:delete")
    public void deleteKhDScores(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iKhDScoreService.deleteKhDScores(arr_ids);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excelDetail")
    public void export3(QueryRequest request, KhDScore khDScore, String dataJson, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(10000);

            List<KhDScore> dcaBSciencepublishList = this.iKhDScoreService.findKhDScores(request, khDScore).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList, dataJson, "");
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    public void export4(QueryRequest request, KhDScore khDScore, String dataJson, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(10000);

            List<KhUser> dcaBSciencepublishList = this.iKhDScoreService.getUserInfoReport(request, khDScore).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList, dataJson, "");
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public KhDScore detail(@NotBlank(message = "{required}") @PathVariable String id) {
        KhDScore khDScore = this.iKhDScoreService.getById(id);
        return khDScore;
    }

    @GetMapping("getBaseInfo/{userAccount}")
    public UserInfo getBaseInfo(@NotBlank(message = "{required}") @PathVariable String userAccount, String year) {
        UserInfo userInfo = new UserInfo();
        LambdaQueryWrapper<KhBCopySciencepublish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KhBCopySciencepublish::getIsDeletemark, 1);//1????????? 0?????????
        queryWrapper.eq(KhBCopySciencepublish::getUserAccount, userAccount);
        queryWrapper.eq(KhBCopySciencepublish::getDcaYear, year);
        List<KhBCopySciencepublish> academicList = this.iDcaBSciencepublishService.list(queryWrapper);

        LambdaQueryWrapper<KhBCopySciencesearch> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(KhBCopySciencesearch::getIsDeletemark, 1);//1????????? 0?????????
        queryWrapper2.eq(KhBCopySciencesearch::getUserAccount, userAccount);
        queryWrapper2.eq(KhBCopySciencesearch::getDcaYear, year);
        List<KhBCopySciencesearch> eduList = this.iDcaBSciencesearchService.list(queryWrapper2);

        LambdaQueryWrapper<KhBCopyPersonalsummary> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.eq(KhBCopyPersonalsummary::getIsDeletemark, 1);//1????????? 0?????????
        queryWrapper3.eq(KhBCopyPersonalsummary::getUserAccount, userAccount);
        queryWrapper3.eq(KhBCopyPersonalsummary::getDcaYear, year);
        List<KhBCopyPersonalsummary> boradList = this.iDcaBPersonalsummaryService.list(queryWrapper3);

        LambdaQueryWrapper<KhBCopyPublicarticle> queryWrapper4 = new LambdaQueryWrapper<>();
        queryWrapper4.eq(KhBCopyPublicarticle::getIsDeletemark, 1);//1????????? 0?????????
        queryWrapper4.eq(KhBCopyPublicarticle::getUserAccount, userAccount);
        queryWrapper4.eq(KhBCopyPublicarticle::getDcaYear, year);
        List<KhBCopyPublicarticle> atrticle = this.iKhBCopyPublicarticleService.list(queryWrapper4);


        LambdaQueryWrapper<KhBCopyScientificprize> queryWrapper5 = new LambdaQueryWrapper<>();
        queryWrapper5.eq(KhBCopyScientificprize::getIsDeletemark, 1);//1????????? 0?????????
        queryWrapper5.eq(KhBCopyScientificprize::getUserAccount, userAccount);
        queryWrapper5.eq(KhBCopyScientificprize::getDcaYear, year);
        List<KhBCopyScientificprize> prize = this.iKhBCopyScientificprizeService.list(queryWrapper5);


        userInfo.setPublishList(academicList);
        userInfo.setSciencesearchList(eduList);
        userInfo.setPublicarticleList(atrticle);
        userInfo.setScientificprizeList(prize);
        userInfo.setSummary(boradList.size() > 0 ? boradList.get(0).getPsContent() : "");
        return userInfo;
    }

}