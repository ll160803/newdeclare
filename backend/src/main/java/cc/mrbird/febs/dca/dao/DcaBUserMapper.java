package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2020-10-27
 */
public interface DcaBUserMapper extends BaseMapper<DcaBUser> {
    void updateDcaBUser(DcaBUser dcaBUser);

    IPage<DcaBUser> findDcaBUser(Page page, @Param("dcaBUser") DcaBUser dcaBUser);

   List<String> findAccounts(@Param("userId") Long userId);

    @Update(" update dca_b_user set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
    void deleteByAccount(@Param("useraccount") String useraccount);

    @Select(" select IFNULL(max(display_index),0) As maxIndex from  dca_b_user  where user_account=#{useraccount} ")
    int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "LEFT JOIN  dca_b_parttimejob  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getParttimeUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_prizeorpunish  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getPrizeorpunishUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_educationexperice  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getEducationexpericeUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_sciencepublish  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getSciencepublishUndo();


    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_sciencesearch  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getSciencesearchUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_patent  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getPatentUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_teacherqualify  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_teacherqualifyUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_attachfile  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_attachfileUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_exportcountry  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_exportcountryUndo();

    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_publicarticle  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_publicarticleUndo();

    /**
     * 科研获奖
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_scientificprize  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_scientificprizeUndo();

    /**
     * 担任辅导员教师班主任及考核情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_turtor  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_turtorUndo();

    /**
     * 任现职以来完成教学、人才培养情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_employ  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_employUndo();

    /**
     * 近五年本科教学情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_undergraduate  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_undergraduateUndo();

    /**
     * 任现职以来承担的本科教学改革及建设项目
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_innovatebuild  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_innovatebuildUndo();

    /**
     * 任现职以来本科教学工作获奖情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_undergraduateprize  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_undergraduateprizeUndo();

    /**
     * 省部级及以上教学获奖
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_teacherprize  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_teacherprizeUndo();


    /**
     * 校教学质量奖、校教学成果奖
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_schoolprize  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_schoolprizeUndo();

    /**
     * 精品课程情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_courseclass  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_courseclassUndo();

    /**
     * 教师教学竞赛获奖
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_youngprize  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_youngprizeUndo();

    /**
     * 任现职以来完成研究生教学人才培养情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_talent  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_talentUndo();

    /**
     * 任现职以来独立指导研究生情况
     * @return
     */
    @Select("SELECT  a.user_account from dca_b_user a\n" +
            "INNER JOIN  dca_b_graduate  b\n" +
            "on a.user_account=b.user_account \n" +
            "where b.IS_DELETEMARK=1 and b.state=1 and LENGTH(a.np_position_name)>0")
    List<String> getDca_b_graduateUndo();

    /**
     *  d党部审核人员
     * @return
     */
    @Select("select c.USERNAME,d.user_account from \n" +
            "dca_d_xl a\n" +
            "INNER JOIN dca_user_xl b\n" +
            "on a.id=b.xl_id\n" +
            "INNER JOIN t_user c on b.user_id=c.USER_ID \n" +
            "INNER JOIN dca_b_user d on a.mudule_name=d.np_position_name\n" +
            "where LENGTH(d.np_position_name)>0")
    List<userAuditAccount> getUserAndAccount();

    /**
     * 学历(位)
     * @return
     */
    @Select("select user_account,exp_position as audit_result ,'edu' audit_titletype " +
            "from  dca_b_educationexperice " +
            "where is_hightest='是' AND state = 3 and IS_DELETEMARK=1")
    List<DcaBAuditdynamic> getExpericeStudy();

    /**
     * 毕业时间
     * @return
     */
    @Select("select user_account,DATE_FORMAT(exp_end_TIME, '%Y%m%d') as audit_result ,'edu_date' audit_titletyp" +
            " from  dca_b_educationexperice " +
            "where is_hightest='是' AND state = 3 and IS_DELETEMARK=1")
    List<DcaBAuditdynamic> getExpericeBysj();

    /**
     *  任现职以来承担的主要科研项目
     */
    @Select("SELECT\n" +
            "\taudit_typetp,audit_typetpjx,audit_lb,audit_fund,audit_rank,audit_Date,user_account\n" +
            "FROM\n" +
            "\tdca_b_sciencesearch\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1 \n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBSciencesearch> getScientSearchAudit();


 /**
  * 承担的本科教学改革及建设项目 用于高级 大表  第7大列
  * @return
  */
 @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\tproject_type AS audit_lb,\n" +
            "\trank_num AS audit_rank,\n" +
            "\tcontract_fund AS audit_fund\n" +
            "FROM\n" +
            "\tdca_b_innovatebuild\n" +
            "WHERE\n" +
            "\tstate = 3\n" +
            "AND IsUse = 1\n" +
            "AND IS_DELETEMARK = 1")
 List<DcaBSciencesearch> getInnovatebuild();

    /**
     * 国家、省部级科研奖
     * @return
     */
    @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\taudit_name,\n" +
            "\taudit_grade,\n" +
            "\taudit_rank\n" +
            "FROM\n" +
            "\tdca_b_scientificprize\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1 AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBScientificprize> getScientPrize();

    /**
     *  国家、省部级教学获奖
     * @return
     */
    @Select("SELECT\n" +
            "user_account,prize_name,prize_grade,ranknum\n" +
            "FROM\n" +
            "\tdca_b_teacherprize\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBTeacherprize> getTeachPrize();

    /**
     * 论文
     * @return
     */
    @Select("SELECT\n" +
            "\taudit_qkjb,\n" +
            "\tjxzcsl,\n" +
            "\tlczcsl,\n" +
            "\tuser_account,\n" +
            "\tis_jxzcsb,\n" +
            "\tpaper_cause,\n" +
            "\tis_lczcsb\n" +
            "FROM\n" +
            "\tdca_b_sciencepublish\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBSciencepublish> getSciPublish();

    /**
     * z著作
     * @return
     */
    @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\tzzsf,\n" +
            "\t IFNULL(cdzs,0) cdzs\n" +
            "FROM\n" +
            "\tdca_b_publicarticle\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBPublicarticle> getPublicArticle();

    /**
     * 法定资质
     * @return
     */
    @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\tqualification_name,\n" +
            "\tqualification_date,\n" +
            "\taudit_grade,\n" +
            "\taudit_qu_date\n" +
            "FROM\n" +
            "\tdca_b_qualification\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1\n" +
            "ORDER BY\n" +
            "\tqualification_date DESC")
    List<DcaBQualification> getQualification();
    /**
     * 教学质量奖与成果奖
     * @return
     */
    @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\tprize_name,\n" +
            "\tprize_date,\n" +
            "\tprize_grade,\n" +
            "\tranknum\n" +
            "FROM\n" +
            "\tdca_b_schoolprize\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBSchoolprize> getSchoolPrize();

    /**
     * 教学竞赛获奖
     * @return
     */
    @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\tgrade,\n" +
            "\tranknum,\n" +
            "\tcoruse_date\n" +
            "FROM\n" +
            "\tdca_b_courseclass\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBCourseclass> getCoursecalss();

    @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\tprize_date,\n" +
            "\tprize_grade,\n" +
            "\tprize_jb,\n" +
            "\tranknum\n" +
            "FROM\n" +
            "\tdca_b_youngprize\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBYoungprize> getYoungprize();



    /**
     * 新技术新业务
     * @return
     */
    @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\trank_index,\n" +
            "\tachievement_grade \n" +
            "FROM\n" +
            "\tdca_b_achievement\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBAchievement> getAchievement();

    /**
     * 社会兼职
     * @return
     */
    @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\tisUse, \n" +
            "\tjz_zw, \n" +
            "\tjz_content \n" +
            "FROM\n" +
            "\tdca_b_parttimejob\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "\tAND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBParttimejob> getPartTimejob();

    /**
     * 社会兼职
     * @return
     */
    @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\tacademic_name,\n" +
            "\tacademic_date,\n" +
            "\tacademic_content,\n" +
            "\tIsUse,\n" +
            "\tis_part_time_job\n" +
            "FROM\n" +
            "\tdca_b_academic\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "\tAND state = 3\n" +
            "\tAND IsUse = 1")
    List<DcaBAcademic> getAcademic();

    /**
     * 博导硕导
     */
    @Select("SELECT\n" +
            "\tturtor_date,\n" +
            "\tuser_account\n" +
            "FROM\n" +
            "\tdca_b_doctorturtor\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1\n" +
            "AND turtor_type = '博导'")
    List<DcaBDoctorturtor> getDoctorTutor();
    /**
     * 二三级申报的学术条件情况
     */
    @Select("SELECT\n" +
            "\tdca_d_yj.mudule_name,\n" +
            "\tdca_user_yj.user_id jb\n" +
            "FROM\n" +
            "\tdca_d_yj\n" +
            "INNER JOIN dca_user_yj ON dca_d_yj.id = dca_user_yj.yj_id\n" +
            "WHERE\n" +
            "\tdca_user_yj.dca_year = #{year}")
    List<DcaDYj> getMoudulesYj(@Param("year") String year);

    /**
     * 是否担任一年辅导员或班主任
     * @return
     */
    @Select("SELECT\n" +
            "\tuser_account\n" +
            "FROM\n" +
            "\tdca_b_turtor\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1 \n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<String> getTutor();

    @Select("SELECT\n" +
            "\tpp_content,\n" +
            "\tuser_account\n" +
            "FROM\n" +
            "\tdca_b_prizeorpunish\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBPrizeorpunish> getPrizeOrPunish();

    /**
     * 教师资格证
     * @return
     */
    @Select("SELECT\n" +
            "\tuser_account\n" +
            "FROM\n" +
            "\tdca_b_teacherqualify\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1 \n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<String> getTeacherQualify();

    /**
     * 出国情况
     * @return
     */
    @Select("SELECT\n" +
            "\tCONCAT(\n" +
            "\t\tdate_format(cgsj, '%Y%m'),\n" +
            "\t\t'-',\n" +
            "\t\tdate_format(hgsj, '%Y%m'),\n" +
            "\t\tlxgj\n" +
            "\t) lxgj,\n" +
            "\tuser_account,\n" +
            "\tcgsj,\n" +
            "\thgsj\n" +
            "FROM\n" +
            "\tdca_b_exportcountry\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3\n" +
            "AND IsUse = 1")
    List<DcaBExportcountry> getExportCountry();

    @Select("SELECT\n" +
            "\tuser_account,\n" +
            "\texp_end_TIME,\n" +
            "\texp_position\n" +
            "FROM\n" +
            "\tdca_b_educationexperice\n" +
            "WHERE\n" +
            "\tIS_DELETEMARK = 1\n" +
            "AND state = 3")
    List<DcaBEducationexperice> getEduexperiennce();

    @Select("SELECT\n" +
            "\tCONCAT( date_format(kssj, '%Y%m' ), '-', IFNULL(date_format( jssj, '%Y%m' ),'') ,'_', rwlx ,pzdd ) pzdd,\n" +
            "\tuser_account \n" +
            "FROM\n" +
            "\tdca_b_assitant ")
    List<DcaBAssitant> getAssitant();

    @Select("SELECT a.user_account  from dca_b_auditdynamic a\n" +
            "\n" +
            "INNER JOIN dca_d_auditinfo b ON a.audit_titletype = b.field_name\n" +
            "WHERE\n" +
            "\t(\n" +
            "\t\t(\n" +
            "\t\t\ta.audit_result = '是'\n" +
            "\t\t\tAND b.state = 2\n" +
            "\t\t)\n" +
            "\t\tOR (\n" +
            "\t\t\ta.audit_result = '否'\n" +
            "\t\t\tAND b.state = 1\n" +
            "\t\t)\n" +
            "\t)\n" +
            "AND b.show_type = 1\n" +
            "AND b.field_name NOT IN ('sfssds', 'sfbsds', 'ynjbzr','sfyszgzs','sfjyhlzgzs','sfdlwcyjspy','pyzlsfyl')")
    List<String> getDynamicIsOk();

    @Select("SELECT\n" +
            "\ta.*\n" +
            "FROM\n" +
            "\tdca_b_report a\n" +
            "INNER JOIN dca_b_userapply b ON a.user_account = b.user_account\n" +
            "AND a.`year` = b.dca_year AND a.state=0")
    List<DcaBReport> getAllReportWithUser();

    @Select("SELECT\n" +
        "\tuser_account,\n" +
        "\tpatent_good\n" +
        "FROM\n" +
        "\tdca_b_patent\n" +
        "WHERE\n" +
        "\tIsUse = 1\n" +
        "AND state = 3\n" +
        "AND IS_DELETEMARK = 1")
    List<DcaBPatent> getPatentInfo();

    /**
     * 获取所有数据 不在大报表记录里面的
     * @return
     */
    IPage<DcaBUser> getAllShowUserInfo(Page page, @Param("dcaBUser") DcaBUser dcaBUser);
}
