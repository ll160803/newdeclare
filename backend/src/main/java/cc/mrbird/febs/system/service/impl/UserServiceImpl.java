package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.CacheService;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.common.utils.MD5Util;
import cc.mrbird.febs.dca.dao.DcaUserAuditMapper;
import cc.mrbird.febs.dca.dao.DcaUserMoudulesMapper;
import cc.mrbird.febs.dca.dao.DcaUserXlMapper;
import cc.mrbird.febs.dca.entity.DcaUserAudit;
import cc.mrbird.febs.dca.entity.DcaUserMoudules;
import cc.mrbird.febs.dca.entity.DcaUserXl;
import cc.mrbird.febs.scm.dao.ScmBUserandareaMapper;
import cc.mrbird.febs.scm.entity.ScmBUserandarea;
import cc.mrbird.febs.system.dao.UserMapper;
import cc.mrbird.febs.system.dao.UserRoleMapper;
import cc.mrbird.febs.system.domain.*;
import cc.mrbird.febs.system.manager.UserManager;
import cc.mrbird.febs.system.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserManager userManager;
    @Autowired
    private DcaUserMoudulesMapper mudulesUserMapper;

    @Autowired
    private DcaUserAuditMapper dcaUserAuditMapper;
    @Autowired
    private DcaUserXlMapper dcaUserXlMapper;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    @Override
    public User findByName(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }


    @Override
    public IPage<User> findUserDetail(User user, QueryRequest request) {
        try {
            Page<User> page = new Page<>();
            SortUtil.handlePageSort(request, page, "userId", FebsConstant.ORDER_ASC, false);
            return this.baseMapper.findUserDetail(page, user);
        } catch (Exception e) {
            log.error("??????????????????", e);
            return null;
        }
    }
    @Override
    public  void updateUserByName( User user) throws Exception
    {
        this.baseMapper.updateUserByName(user);
        // ?????????????????????????????? redis???
        cacheService.saveUser(user.getUsername());
    }
    @Override
    @Transactional
    public void updateLoginTime(String username) throws Exception {
        User user = new User();
        user.setLastLoginTime(new Date());

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));

        // ?????????????????????????????? redis???
        cacheService.saveUser(username);
    }

    @Override
    @Transactional
    public void createUser(User user) throws Exception {
        // ????????????
        user.setCreateTime(new Date());
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setPassword(MD5Util.encrypt(user.getUsername(), User.DEFAULT_PASSWORD));
        save(user);

        // ??????????????????
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

        //???????????????????????????
        if(user.getAreaId()!=null && user.getAreaId()!="") {
            String[] areaIds = user.getAreaId().split(StringPool.COMMA);
            setUserArea(user, areaIds);
        }

        //???????????????????????????
        if(user.getAuditId()!=null && user.getAuditId()!="") {
            String[] areaIds = user.getAuditId().split(StringPool.COMMA);
            setUserAudit(user, areaIds);
        }
        //???????????????????????????
        if(user.getXlId()!=null && user.getXlId()!="") {
            String[] areaIds = user.getXlId().split(StringPool.COMMA);
            setUserXl(user, areaIds);
        }
        // ????????????????????????????????????
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));

        // ?????????????????????????????? Redis???
        userManager.loadUserRedisCache(user);
    }
    private void setUserXl(User user, String[] areaIds) {
        Arrays.stream(areaIds).forEach(menuId -> {
            DcaUserXl rm = new DcaUserXl();
            // rm.setId(UUID.randomUUID().toString());
            rm.setXlId(Long.parseLong(menuId));
            rm.setUserId(user.getUserId());
            this.dcaUserXlMapper.insert(rm);
        });
    }
    private void setUserAudit(User user, String[] areaIds) {
        Arrays.stream(areaIds).forEach(menuId -> {
            DcaUserAudit rm = new DcaUserAudit();
           // rm.setId(UUID.randomUUID().toString());
            rm.setAuditId(Integer.parseInt(menuId));
            rm.setUserId(user.getUserId());
             this.dcaUserAuditMapper.insert(rm);
        });
    }

    private void setUserArea(User user, String[] areaIds) {
        Arrays.stream(areaIds).forEach(menuId -> {
            DcaUserMoudules rm = new DcaUserMoudules();
            // rm.setId(UUID.randomUUID().toString());
            rm.setMuduleId(Integer.parseInt(menuId));
            rm.setUserId(user.getUserId());
            this.mudulesUserMapper.insert(rm);
        });
    }

    @Override
    @Transactional
    public void updateUser(User user) throws Exception {
        // ????????????
        user.setPassword(null);
        user.setModifyTime(new Date());
        updateById(user);

        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));

        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

        this.mudulesUserMapper.delete(new LambdaQueryWrapper<DcaUserMoudules>().eq(DcaUserMoudules::getUserId, user.getUserId()));

        this.dcaUserAuditMapper.delete(new LambdaQueryWrapper<DcaUserAudit>().eq(DcaUserAudit::getUserId, user.getUserId()));

        this.dcaUserXlMapper.delete(new LambdaQueryWrapper<DcaUserXl>().eq(DcaUserXl::getUserId, user.getUserId()));

        //???????????????????????????
        //???????????????????????????
        if(user.getAreaId()!=null && user.getAreaId()!="") {
            String[] areaIds = user.getAreaId().split(StringPool.COMMA);
            setUserArea(user, areaIds);
        }

        //???????????????????????????
        if(user.getAuditId()!=null && user.getAuditId()!="") {
            String[] areaIds = user.getAuditId().split(StringPool.COMMA);
            setUserAudit(user, areaIds);
        }
        //???????????????????????????
        if(user.getXlId()!=null && user.getXlId()!="") {
            String[] areaIds = user.getXlId().split(StringPool.COMMA);
            setUserXl(user, areaIds);
        }

        // ??????????????????????????????????????????????????????????????? ????????? redis???
        cacheService.saveUser(user.getUsername());
        cacheService.saveRoles(user.getUsername());
        cacheService.savePermissions(user.getUsername());
    }

    @Override
    @Transactional
    public void deleteUsers(String[] userIds) throws Exception {
        // ????????????????????????
        this.userManager.deleteUserRedisCache(userIds);

        List<String> list = Arrays.asList(userIds);

        removeByIds(list);

        // ??????????????????
        this.userRoleService.deleteUserRolesByUserId(userIds);
        // ???????????????????????????
        this.userConfigService.deleteByUserId(userIds);
    }

    @Override
    @Transactional
    public void updateProfile(User user) throws Exception {
        updateById(user);
        // ????????????????????????
        cacheService.saveUser(user.getUsername());
    }

    @Override
    @Transactional
    public void updateAvatar(String username, String avatar) throws Exception {
        User user = new User();
        user.setAvatar(avatar);

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // ????????????????????????
        cacheService.saveUser(username);
    }

    @Override
    @Transactional
    public void updatePassword(String username, String password) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // ????????????????????????
        cacheService.saveUser(username);
    }
    @Override
    @Transactional
    public void updateOpenid(String username, String openid) throws Exception {
        User user = new User();
        user.setCode("2020"+openid);//?????????????????????????????????openid

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // ????????????????????????
       // cacheService.saveUser(username);
    }
    @Override
    @Transactional
    public void updateOpenid2(String username, String openid) throws Exception {
        User user = new User();
        user.setCode(openid);

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // ????????????????????????
        cacheService.saveUser(username);
    }

    @Override
    @Transactional
    public void regist(String username, String password) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setUsername(username);
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSsex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setDescription("????????????");
        this.save(user);

        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(2L); // ?????????????????? ID
        this.userRoleMapper.insert(ur);

        // ????????????????????????????????????
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));
        // ?????????????????????????????? Redis???
        userManager.loadUserRedisCache(user);

    }

//    @Override
//    @Transactional
//    public void resetPassword(String[] usernames) throws Exception {
//        for (String username : usernames) {
//
//            User user = new User();
//            user.setPassword(MD5Util.encrypt(username, User.DEFAULT_PASSWORD));
//
//            this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
//            // ?????????????????????????????? redis???
//            cacheService.saveUser(username);
//        }
//
//    }
    @Override
    @Transactional
    public void resetPassword(String[] usernames,String pwd) throws Exception {
        for (String username : usernames) {

            User user = new User();
            if(pwd == null || pwd.equals("")) {
                user.setPassword(MD5Util.encrypt(username, User.DEFAULT_PASSWORD));
            } else {
                user.setPassword(MD5Util.encrypt(username, pwd));
            }
            this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
            // ?????????????????????????????? redis???
            cacheService.saveUser(username);
        }

    }

    private void setUserRoles(User user, String[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(Long.valueOf(roleId));
            this.userRoleMapper.insert(ur);
        });
    }
    public void updateRealname(String username, String realname) throws Exception {
        User user = new User();
        user.setRealname(realname);

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // ????????????????????????
        cacheService.saveUser(username);
    }

    public  void  UpdateUserOnly(User user,String username) throws Exception
    {
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // ????????????????????????
        cacheService.saveUser(username);
    }
    public List<User> findUserWithoutOpenid(){
       return this.baseMapper.findUserWithoutOpenid();
    }

    @Override
    @Transactional
    public String importUserRoles(List<user_extend> userRoleList, List<String> strRoleList, List<String> strDeptList) throws Exception {
        String msg = "";
        List<Dept> deptList = new ArrayList<>();
        List<Role> roleList = new ArrayList<>();
        LambdaQueryWrapper<Dept> queryDeptWrapper = new LambdaQueryWrapper<>();
        String sql = "DEPT_NAME IN (";
        String strIn = "";
        for (String deptName : strDeptList) {
            if (strIn.equals("")) {
                strIn = "'" + deptName + "'";
            } else {
                strIn += ",'" + deptName + "'";
            }
        }

        sql += strIn + ")";
        queryDeptWrapper.apply(sql);

        deptList = deptService.list(queryDeptWrapper);
        if (deptList.size() == strDeptList.size()) {
            LambdaQueryWrapper<Role> queryRoleWrapper = new LambdaQueryWrapper<>();
            sql = "ROLE_NAME IN (";
            strIn = "";
            for (String roleName : strRoleList) {
                if (strIn.equals("")) {
                    strIn = "'" + roleName + "'";
                } else {
                    strIn += ",'" + roleName + "'";
                }
            }

            sql += strIn + ")";
            queryRoleWrapper.apply(sql);
            roleList = roleService.list(queryRoleWrapper);
        }else {
            return "deptError";
        }
        if (roleList.size() == strRoleList.size()) {
            List<Dept> searchDeptList = new ArrayList<>();
            List<Role> searchRoleList = new ArrayList<>();
            List<User> searchUserList = new ArrayList<>();
            List<User> userList = this.list();
            for (user_extend userRole : userRoleList) {
                searchUserList = userList.stream().filter(
                        s -> s.getUsername().equals(userRole.getUserName())
                ).collect(Collectors.toList());
                if(searchUserList.size() == 0) {
                    User user = new User();
                    user.setCreateTime(new Date());
                    user.setAvatar(User.DEFAULT_AVATAR);
                    user.setPassword(MD5Util.encrypt(userRole.getUserName(), userRole.getPassword()));

                    searchDeptList = deptList.stream().filter(
                            s -> s.getDeptName().equals(userRole.getDeptName())
                    ).collect(Collectors.toList());

                    searchRoleList = roleList.stream().filter(
                            s -> s.getRoleName().equals(userRole.getRoleName())
                    ).collect(Collectors.toList());

                    user.setRoleId(searchRoleList.get(0).getRoleId().toString());
                    user.setDeptId(searchDeptList.get(0).getDeptId());
                    user.setUsername(userRole.getUserName());
                    user.setRealname(userRole.getXmname());
                    user.setCreateTime(new Date());
                    user.setStatus(User.STATUS_VALID);
                    user.setSsex(User.SEX_UNKNOW);
                    user.setAvatar(User.DEFAULT_AVATAR);
                    user.setDescription("????????????");

                    save(user);

                    // ??????????????????
                    String[] roles = user.getRoleId().split(StringPool.COMMA);
                    setUserRoles(user, roles);

                    // ????????????????????????????????????
                    userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));
                    // ?????????????????????????????? Redis???
                    userManager.loadUserRedisCache(user);
                }
            }
        }else {
            return "roleError";
        }
        return msg;
    }
}
