package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.MD5Util;
import cc.mrbird.febs.common.utils.WxMessage;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.dca.service.IDcaBUserService;
import cc.mrbird.febs.scm.entity.ScmBUserandarea;
import cc.mrbird.febs.scm.entity.ScmDArea;
import cc.mrbird.febs.scm.service.IScmBUserandareaService;
import cc.mrbird.febs.scm.service.impl.ScmBUserandareaServiceImpl;
import cc.mrbird.febs.system.domain.Role;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.domain.UserConfig;
import cc.mrbird.febs.system.domain.user_extend;
import cc.mrbird.febs.system.entity.TPassBack;
import cc.mrbird.febs.system.service.ITPassBackService;
import cc.mrbird.febs.system.service.RoleService;
import cc.mrbird.febs.system.service.UserConfigService;
import cc.mrbird.febs.system.service.UserService;
import cc.mrbird.febs.webService.Mess.SmsService;
import cc.mrbird.febs.webService.Mess.SmsServicePortType;
import cn.hutool.Hutool;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    private String message;

    @Autowired
    private UserService userService;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IScmBUserandareaService areaService;

    @Autowired
    private IDcaBUserService iDcaBUserService;
    @Autowired
    private ITPassBackService itPassBackService;

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findByName(username) == null;
    }

    @GetMapping("/{username}")
    public User detail(@NotBlank(message = "{required}") @PathVariable String username) {
        User user = this.userService.findByName(username);
        //??????????????????????????????????????????????????????roleId????????????
        List<Role> roles = roleService.findUserRole(username);
        List<Long> roleIds = roles.stream().map(role -> role.getRoleId()).collect(Collectors.toList());
        String roleIdStr = StringUtils.join(roleIds.toArray(new Long[roleIds.size()]), ",");
        user.setRoleId(roleIdStr);
        return user;
    }

    @GetMapping
    @RequiresPermissions("user:view")
    public Map<String, Object> userList(QueryRequest queryRequest, User user) {
        return getDataTable(userService.findUserDetail(user, queryRequest));
    }

    @GetMapping("area/{userId}")
    public List<String> getRoleMenus(@NotBlank(message = "{required}") @PathVariable String userId) {
        List<ScmBUserandarea> list = this.areaService.getAreaByUserId(userId);
        return list.stream().map(area -> String.valueOf(area.getAreaID())).collect(Collectors.toList());
    }

    @Log("????????????")
    @PostMapping
    @RequiresPermissions("user:add")
    public void addUser(@Valid User user) throws FebsException {
        try {
            this.userService.createUser(user);
        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("????????????")
    @PutMapping
    @RequiresPermissions("user:update")
    public void updateUser(@Valid User user) throws FebsException {
        try {
            this.userService.updateUser(user);
        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("????????????")
    @DeleteMapping("/{userIds}")
    @RequiresPermissions("user:delete")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws FebsException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("profile")
    public void updateProfile(@Valid User user) throws FebsException {
        try {
            this.userService.updateProfile(user);
        } catch (Exception e) {
            message = "????????????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("avatar")
    public void updateAvatar(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String avatar) throws FebsException {
        try {
            this.userService.updateAvatar(username, avatar);
        } catch (Exception e) {
            message = "??????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("userconfig")
    public void updateUserConfig(@Valid UserConfig userConfig) throws FebsException {
        try {
            this.userConfigService.update(userConfig);
        } catch (Exception e) {
            message = "???????????????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("password/check")
    public boolean checkPassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) {
        String encryptPassword = MD5Util.encrypt(username, password);
        User user = userService.findByName(username);
        if (user != null)
            return StringUtils.equals(user.getPassword(), encryptPassword);
        else
            return false;
    }

    @PutMapping("password")
    public void updatePassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws FebsException {
        try {
            message = "";
            if (!HasDigit(password) || !judgeContainsStr(password) || !judgeContainsSpecialStr(password) || password.trim().length() < 8) {
                throw new FebsException("??????8???????????????????????????????????????(@$%!+-)");
            }
            userService.updatePassword(username, password);
        } catch (Exception e) {
            message = "??????????????????,????????????8???????????????????????????????????????(@$%!+-)";
            log.error(message, e);
            throw new FebsException(e.getMessage());
        }
    }

    private boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    public boolean judgeContainsStr(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    public boolean judgeContainsSpecialStr(String cardNum) {
        String regex = ".*[@$%#_+*/!]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

//    @PutMapping("password/reset")
//    @RequiresPermissions("user:reset")
//    public void resetPassword(@NotBlank(message = "{required}") String usernames) throws FebsException {
//        try {
//            String[] usernameArr = usernames.split(StringPool.COMMA);
//            this.userService.resetPassword(usernameArr);
//        } catch (Exception e) {
//            message = "????????????????????????";
//            log.error(message, e);
//            throw new FebsException(message);
//        }
//    }
    @PutMapping("password/reset")
    @RequiresPermissions("user:reset")
    public void resetPassword(@NotBlank(message = "{required}") String usernames,String pwd) throws FebsException {
        try {
            String[] usernameArr = usernames.split(StringPool.COMMA);
            this.userService.resetPassword(usernameArr,pwd);
        } catch (Exception e) {
            message = "????????????????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("user:export")
    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) throws FebsException {
        try {
            List<User> users = this.userService.findUserDetail(user, queryRequest).getRecords();
            ExcelKit.$Export(User.class, response).downXlsx(users, false);
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("bindweixin")
    public void updateOpenid(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String openid) throws FebsException {
        try {
            log.info("openid:" + openid);
            // WxMessage wm=new WxMessage();

            // log.info("openid:"+openidS);
            userService.updateOpenid(username, openid);
        } catch (Exception e) {
            message = "??????????????????ID??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("importUser")
    public FebsResponse importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<user_extend> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();

        ExcelKit.$Import(user_extend.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<user_extend>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, user_extend entity) {
                        successList.add(entity); // ??????????????????????????????????????????
                    }

                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        List<ExcelErrorField> errorFields) {
                        // ????????????????????????????????????????????????????????????
                        errorList.add(MapUtil.of(//
                                "sheetIndex", sheetIndex
                        ));
                        errorList.add(MapUtil.of(//
                                "rowIndex", rowIndex));
                        errorList.add(MapUtil.of(//
                                "errorFields", errorFields));
                    }
                });

        // TODO: ??????successList??????????????????
        Map<String, Object> result = new HashMap<>();
        result.put("data", successList);
        result.put("haveError", !CollectionUtil.isEmpty(errorList));
        result.put("error", errorList);
        result.put("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L);


        List<String> strRoleList = new ArrayList<>();
        List<String> strDeptList = new ArrayList<>();
        String strError = "";
        if (CollectionUtil.isEmpty(errorList)) {
            for (user_extend d : successList
            ) {
                String roleName = d.getRoleName();
                String deptName = d.getDeptName();

                if (StringUtils.isNotBlank(roleName)) {
                    if (!strRoleList.stream().anyMatch(task -> task.equals(roleName))) {
                        strRoleList.add(roleName);
                    }
                } else {
                    strError = "??????????????????";
                    break;
                }
                if (StringUtils.isNotBlank(deptName)) {
                    if (!strDeptList.stream().anyMatch(task -> task.equals(deptName))) {
                        strDeptList.add(deptName);
                    }
                } else {
                    strError = "??????????????????";
                    break;
                }

                try {
                    if (strError.equals("")) {
                        String msg = this.userService.importUserRoles(successList, strRoleList, strDeptList);
                        if (msg.equals("roleError")) {
                            message = "???????????????";
                        } else if (msg.equals("deptError")) {
                            message = "???????????????";
                        } else {
                            message = "??????????????????";
                            // success = 1;
                        }
                    } else {
                        message = strError;
                    }
                } catch (Exception ex) {

                }
            }
        }
        return new FebsResponse().data(errorList);
    }

    @PostMapping("mess")
    public FebsResponse SendMessByTel(String tel, String message) {
        try {
           String sms= getSendMess(tel,message);
           return  new FebsResponse().message(sms);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return  new FebsResponse().message("");
    }

    private  String  getSendMess(String tel, String message){
        SmsService smsService = new SmsService();
        SmsServicePortType ssp = smsService.getSmsServiceHttpPort();
        String in0 = "hrp_hr";
        String in1 = "hrp_hr";
        String in2 = "MAC";
        String in3 = "FC";
        String in4 = tel;
        String in5 = message;
        String sms = ssp.service(in0, in1, in2, in3, in4, in5);
        return  sms;
    }

    @PostMapping("passMess")
    public FebsResponse SendMessByTel2(String tel) {
        try {
            LambdaQueryWrapper<DcaBUser> lam= new LambdaQueryWrapper<>();
            lam.eq(DcaBUser::getTelephone,tel.trim());

            List<DcaBUser> users= this.iDcaBUserService.list(lam);
            log.info("rrrrrrr",555555);
            if(users.size()>0){
                String userAcccount= users.get(0).getUserAccount();
                String telephone= users.get(0).getTelephone();
                String code= RandomUtil.randomNumbers(4);
                TPassBack pb= new TPassBack();
                pb.setCode(code);
                String idCard= users.get(0).getIdCard();
                pb.setSixCode(idCard.substring(idCard.length()-6));
                pb.setIsDeletemark(1);
                pb.setUserAccount(userAcccount);
                pb.setTel(telephone);
                pb.setState(0);
                itPassBackService.createTPassBack(pb);
                String sms= getSendMess(telephone,code);
                log.info(sms,555555);
                return  new FebsResponse().message("????????????????????????");

            }
            else {
                return  new FebsResponse().message("????????????????????????");
            }



        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return  new FebsResponse().message("");
    }

    @PostMapping("resetPass")
    public FebsResponse resetPass(String tel,String code){
        String mess= "";
        LambdaQueryWrapper<TPassBack> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(TPassBack::getCode,code.trim());
        queryWrapper.eq(TPassBack::getTel,tel.trim());
        queryWrapper.eq(TPassBack::getState,0);
        List<TPassBack> backList= this.itPassBackService.list(queryWrapper);

        if(backList.size()>0) {
            backList.stream().sorted(Comparator.comparing(TPassBack::getCreateTime).reversed()).collect(Collectors.toList());
            TPassBack pb = backList.get(0);
            Date a= pb.getCreateTime();
            Date pass= DateUtil.offsetMinute(a,2);
            Date now= new Date();
            if(DateUtil.compare(pass,now)<0){
                return new FebsResponse().message("??????????????????");
            }
            else {
                try {
                    this.userService.updatePassword(pb.getUserAccount(), "XH" + pb.getSixCode());
                    TPassBack pb2= new TPassBack();
                    pb2.setState(1);
                    this.itPassBackService.update(pb2,queryWrapper);
                    return new FebsResponse().message("XH+????????????6???");
                }
                catch (Exception ex){
                    return new FebsResponse().message("??????????????????");
                }
            }
        }
        return new FebsResponse().message("????????????????????????????????????");


    }
}
