package cc.mrbird.febs.kh.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@Excel("kh_user")
public class KhUser {

        private static final long serialVersionUID = 1L;

        /**
         * 主键
         */
        private String id;


        /**
         * 人事编号
         */
        @ExcelField(value = "人事编号")
        private String userAccount;
        /**
         * 姓名
         */
        @ExcelField(value = "姓名")
        private String userAccountName;


        /**
         * 科室
         */
        @ExcelField(value = "科室")
        private String deptName;




        /**
         * 现岗位职务
         */
        @ExcelField(value = "现岗位职务")
        private String positionName;

        public String getPositionName() {
            return (zyjsgw == null ? "" : zyjsgw) +(zyjsgw == null||zyjsgw.equals("")||zyjsgwLc==null||zyjsgwLc.equals("")?"":"/")+ (zyjsgwLc == null ? "" : zyjsgwLc);
        }

        /**
         * 专业技术岗位时间  appointedDate+ appointedDateLc
         */
        @ExcelField(value = "现职务聘任时间")
        private transient String zygwDate;

        public String getZygwDate() {
            String na = "";
            try {
                if (appointedDate != null) {
                    na = DateUtil.format(appointedDate, "yyyyMM");
                }
                if (appointedDateLc != null) {
                    na += (na == "" ? "" : "/") + DateUtil.format(appointedDateLc, "yyyyMM");
                }
            } catch (Exception ex) {

            }
            return na;
        }

        /**
         * 申报年度
         */
        @ExcelField(value = "申报年度")
        private String dcaYear;


        /**
         * 所在院系
         */
        private String auditDeptName;


        /**
         * 拟聘岗位职务
         */
        @ExcelField(value = "拟聘岗位职务")
        private String npPositionName;


        /**
         * 手机号
         */
        @ExcelField(value = "手机号")
        private String telephone;
        /**
         * 性别
         */
        @ExcelField(value = "性别")
        private String sexName;


        /**
         * 出生年月
         */
        @ExcelField(value ="出生年月", writeConverter = DateConverter.class)
        private Date birthday;
        private transient String birthdayFrom;
        private transient String birthdayTo;

        @ExcelField(value = "出生日期")
        private transient String birthdaystr;

        public String getBirthdaystr() {
            String na = "";
            try {
                if (birthday != null) {
                    na = DateUtil.format(birthday, "yyyyMMdd");
                }
            } catch (Exception ex) {

            }
            return na;
        }

        private transient String age;

        public String getAge() {
            int age2 = DateUtil.age(birthday, DateUtil.parse((DateUtil.year(DateUtil.date())-1) + "-10-31"));
            return String.valueOf(age2);
        }

        /**
         * 来校工作时间
         */

        private Date schoolDate;
        private transient String schoolDateFrom;
        private transient String schoolDateTo;

        /**
         * 现专业技术岗位
         */

        private String zyjsgw;

        /**
         * 现专业技术岗位（临床）
         */

        private String zyjsgwLc;


        /**
         * 现从事专业及专长
         */

        private String xcszyjzc;

        /**
         * 聘任时间
         */

        private Date appointedDate;
        private transient String appointedDateFrom;
        private transient String appointedDateTo;




        /**
         * 聘任时间（临床）
         */

        private Date appointedDateLc;
        private transient String appointedDateLcFrom;
        private transient String appointedDateLcTo;



        /**
         * 现任岗位级别
         */
        private String xrgwjb;

        /**
         * 现任岗位级别聘任时间
         */
        private Date xrgwjbprsj;



        /**
         * 政治面貌
         */

        @ExcelField(value ="政治面貌")
        private String politicalStatus;

        /**
         * 身份证号
         */

        @ExcelField(value ="身份证号")
        private String idCard;

    @ExcelField(value ="照片地址")
    private String pictureUrl;

        private  String auditType;


        private   String year;

        private  double score;
    /**
     * 账号
     */

    @ExcelField(value ="账号")
    private String auditUserAccount;

    /**
     * 姓名
     */

    @ExcelField(value ="姓名")
    private String auditUserAccountName;

    @ExcelField(value ="分组")
    private String fenzu;

    @ExcelField(value ="分组")
    private String auditFenzu;



}
