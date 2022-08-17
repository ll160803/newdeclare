package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.domain.user_extend;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface UserService extends IService<User> {

    /**
     * 通过用户id更改用户状态
     * @param id
     * @param status
     */
    void updateUserByName( User user) throws Exception;
    /**
     * 通过用户名查找用户
     *
     * @param username username
     * @return user
     */
    User findByName(String username);

    /**
     * 查询用户详情，包括基本信息，用户角色，用户部门
     *
     * @param user user
     * @param queryRequest queryRequest
     * @return IPage
     */
    IPage<User> findUserDetail(User user, QueryRequest queryRequest);

    /**
     * 更新用户登录时间
     *
     * @param username username
     */
    void updateLoginTime(String username) throws Exception;

    /**
     * 新增用户
     *
     * @param user user
     */
    void createUser(User user) throws Exception;

    /**
     * 修改用户
     *
     * @param user user
     */
    void updateUser(User user) throws Exception;

    /**
     * 删除用户
     *
     * @param userIds 用户 id数组
     */
    void deleteUsers(String[] userIds) throws Exception;

    /**
     * 更新个人信息
     *
     * @param user 个人信息
     */
    void updateProfile(User user) throws Exception;

    /**
     * 更新用户头像
     *
     * @param username 用户名
     * @param avatar   用户头像
     */
    void updateAvatar(String username, String avatar) throws Exception;

    /**
     * 更新用户密码
     *
     * @param username 用户名
     * @param password 新密码
     */
    void updatePassword(String username, String password) throws Exception;

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     */
    void regist(String username, String password) throws Exception;

    /**
     * 重置密码
     *
     * @param usernames 用户集合
     */
    void resetPassword(String[] usernames,String pwd) throws Exception;

    /**
     * 设置微信账户ID 不更改缓存
     * @param username
     * @param openid
     */
    void updateOpenid(String username, String openid) throws Exception ;

    /**
     * 再次设置微信账户ID 缓存更改
     * @param username
     * @param openid
     */
    void updateOpenid2(String username, String openid) throws Exception ;

    /**
     * 修改用户的真实姓名
     * @param username
     * @param realname
     * @throws Exception
     */
    void updateRealname(String username, String realname) throws Exception ;

    /**
     * 修改用户信息
     * @param user
     * @param username
     * @throws Exception
     */
    void UpdateUserOnly(User user,String username) throws Exception;


    List<User> findUserWithoutOpenid();

    /**
     * 江哥 用户导入
     * @param userRoleList
     * @param strRoleList
     * @param strDeptList
     * @return
     * @throws Exception
     */
    String importUserRoles(List<user_extend> userRoleList, List<String> strRoleList, List<String> strDeptList) throws Exception ;


}
