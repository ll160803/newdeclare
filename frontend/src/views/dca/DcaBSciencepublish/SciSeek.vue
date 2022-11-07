<template>
  <a-card title="搜索数据(如果文章没有搜索到，请检查论文名的全称是否正确)" :headStyle="{
        fontWeight: 'bold',
        fontSize: '20px',
        color: 'red'
      }">
    <a-row>
      <a-col :span="16">
        <a-form-item v-bind="formItemLayout" label="论文名">
          <a-input placeholder="请输入论文名" v-model="sciName" />
        </a-form-item>
      </a-col>
      <a-col :span="3" :offset="1">
        <a-button @click="handleSubmit" type="primary" :loading="loading" 
          >智能搜索</a-button
        >
      </a-col>
    </a-row>
     <!-- <a-card title="返回值" v-if="auditShow">
      <a-row>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="发表年">
            <a-input v-model="artData.pub_year" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="发表月">
            <a-input v-model="artData.pub_month" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="他引次数">
            <a-input v-model="cited_count" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="期刊名">
            <a-input v-model="artData.journal" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="期刊号">
            <a-input
              v-model="jifData.issn == '' ? artData.issn : jifData.issn"
            />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="相似度">
            <a-input v-model="artData.jw_sim" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="是否完成">
            <a-input v-model="loading" />
          </a-form-item>
        </a-col>
      </a-row>

      <a-row>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="影响因子">
            <a-input v-model="jifData.jif" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="rank_q">
            <a-input v-model="jifData.rank_q" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="rank">
            <a-input v-model="jifData.rank" />
          </a-form-item>
        </a-col>
      </a-row>
    </a-card>-->
    <a-card title="文章值" v-if="auditShow">
      <a-row>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="发表年月">
            <a-input v-model="artData.pub_year + month" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="他引次数">
            <a-input v-model="cited_count" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="期刊名">
            <a-input v-model="journalName" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="期刊号">
            <a-input v-model="issn" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="影响因子">
            <a-input v-model="jifData.jif" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="是否SCI">
            <a-input v-model="isSci" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="期刊级别">
            <a-input v-model="qkjb" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="rank">
            <a-input v-model="rankValue" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="收录情况">
            <a-input v-model="slqk" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="是否一流">
            <a-input v-model="isBest" />
          </a-form-item>
        </a-col>
        <a-col :sm="8">
          <a-form-item v-bind="formItemLayout" label="相似度">
            <a-input v-model="artData.jw_sim" />
          </a-form-item>
        </a-col>
      </a-row>
    </a-card>  
  </a-card>
</template>

<script>
import requestSci from "../../../utils/requestSci";
const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};
export default {
  data() {
    return {
      headStyle: {
        fontWeight: 'bold',
        fontSize: '20px'
      },
      loading: false,
      formItemLayout,
      artData: {
        pub_year: "",
        pub_month: "",
        issn: "",
        cited_count: "",
        journal: "",
        journalCode: "",
        jw_sim: "",
        sd_chinese_journal_meta: {
          CSTPCD: "",
        },
        title: ""
      },
      jifData: {
        jif: "",
        rank_q: "",
        rank: "",
        isSci: "",
        issn: "",
      },
      cscd: {
        is_cscd: "",
        journal_name: "",
        issn: "",
        centered: "",
      },
      month: "",
      cited_count: "",
      paperShoulu: "", //收录情况
      isBest: "否", //是否一流
      qkjb: "",
      issn: "",
      rankValue: "",
      journalName: "", //期刊名称
      isSci: "",
      slqk: "", //收录情况
      monthData: [
        { key: "January", value: "01" },
        { key: "Jan", value: "01" },
        { key: "01", value: "01" },
        { key: "1", value: "01" },
        { key: "February", value: "02" },
        { key: "Feb", value: "02" },
        { key: "02", value: "02" },
        { key: "2", value: "02" },
        { key: "March", value: "03" },
        { key: "Mar", value: "03" },
        { key: "03", value: "03" },
        { key: "3", value: "03" },
        { key: "April", value: "04" },
        { key: "Apr", value: "04" },
        { key: "04", value: "04" },
        { key: "4", value: "04" },
        { key: "May", value: "05" },
        { key: "05", value: "05" },
        { key: "5", value: "05" },
        { key: "June", value: "06" },
        { key: "Jun", value: "06" },
        { key: "06", value: "06" },
        { key: "6", value: "06" },
        { key: "July", value: "07" },
        { key: "Jul", value: "07" },
        { key: "07", value: "07" },
        { key: "7", value: "07" },
        { key: "August", value: "08" },
        { key: "Aug", value: "08" },
        { key: "08", value: "08" },
        { key: "8", value: "08" },
        { key: "September", value: "09" },
        { key: "Sep", value: "09" },
        { key: "09", value: "09" },
        { key: "9", value: "09" },
        { key: "October", value: "10" },
        { key: "Oct", value: "10" },
        { key: "10", value: "10" },
        { key: "November", value: "11" },
        { key: "Nov", value: "11" },
        { key: "11", value: "11" },
        { key: "December", value: "12" },
        { key: "Dec", value: "12" },
        { key: "12", value: "12" },
      ],
      searchIndex: -1,
      article: [],
      flag: 0,
      token: "",
      sciName: '',
      isShow2: false, 
    };
  },
  props: {
    jbLbList2: {
      type: Array,
      default: () => [],
    },
    isAudit: {
      default: false
    }
  },
  computed: {
    jbLbList() {
      return this.jbLbList2;
    },
    sciAuthor() {
      return this.$store.state.account.user.realname;
    },
    auditShow() {
      return this.isAudit;
    }
  },
  watch: {
    searchIndex() {
      //往主页面传值
      if (this.searchIndex >= 0) {
        let dcaBSciencepublish = {
          paperName: this.artData.title,
          journalName: this.journalName,
          journalCode: this.issn,
          paperPublishdate: this.handleYearMonth(this.artData.pub_year, this.month),
          qkjb: this.qkjb,
          paperShoulu: this.slqk,
          paperCause: this.jifData.jif,
          isBest: this.isBest,
          otherTimes: this.cited_count,
          sciValue: this.isSci,
          rankValue: this.rankValue,
          xsd: this.artData.jw_sim,
          rankSear: this.jifData.rank
        };
        this.$emit("setValue", dcaBSciencepublish);
      }
    },
  },
  methods: {
    reset() {
      console.info("seek reset");
      
      this.artData = {
        pub_year: "",
        pub_month: "",
        cited_count: "",
        journal: "",
        journalCode: "",
        jw_sim: "",
        sd_chinese_journal_meta: {
          CSTPCD: "",
        },
        title: ""
      };
      this.jifData = {
        jif: "",
        rank_q: "",
        rank: "",
        isSci: "",
        issn: "",
      };
      this.cscd = {
        is_cscd: "",
        journal_name: "",
        issn: "",
        centered: "",
      };
      this.month = "";

      this.cited_count = "";

      this.isBest = "否";
      this.qkjb = "";
      this.issn = "";

      this.rankValue = "";
      this.journalName = "";
      this.isSci = "否";
      this.slqk = ""; //收录情况
      this.isShow2 = false;
      this.sciName = '';
      this.loading = false;
      this.searchIndex= -1;
    },
    reset2() {
 console.info("seek reset");
      
      this.artData = {
        pub_year: "",
        pub_month: "",
        cited_count: "",
        journal: "",
        journalCode: "",
        jw_sim: "",
        sd_chinese_journal_meta: {
          CSTPCD: "",
        },
        title: ""
      };
      this.jifData = {
        jif: "",
        rank_q: "",
        rank: "",
        isSci: "",
        issn: "",
      };
      this.cscd = {
        is_cscd: "",
        journal_name: "",
        issn: "",
        centered: "",
      };
      this.month = "";

      this.cited_count = "";

      this.isBest = "否";
      this.qkjb = "";
      this.issn = "";

      this.rankValue = "";
      this.journalName = "";
      this.isSci = "否";
      this.slqk = ""; //收录情况
    },
    handleYearMonth(year, month) {
      if (year != "") {
        if (month != "") {
          return year + "-" + month + "-01";
        } else {
          return year + "-01" + "-01";
        }
      }
      return "";
    },
    getSB_Token() {
     
      requestSci
        .get("login", {
          username: this.$store.state.account.user.username,
          password: 2,
        })
        .then((r) => {
          console.info(r);
          this.token = r.data.token;
          console.info(this.token);
        }).catch(()=>{
         
        });
    },
    editSearch(sciName){
       this.sciName =sciName;
    },
    handleSubmit() {
      if(this.sciName.trim()==''){
        this.$message.warn('请输入论文名')
      }
      else{
      this.loading = true;
       console.info(this.$store.state.account.user)
      requestSci
        .get("login", {
          username: this.$store.state.account.user.username,
          password: 2,
        })
        .then((r) => {
          console.info(r);
          this.token = r.data.token;
          this.handlePaperSeach(r.data.token);
        }).catch((error)=>{
           this.loading = false;
           this.searchIndex += 1;
        
          
        });
      }
    },
    handlePaperSeach(token) {
      this.reset2();
      if (this.sciName != "" && !this.isChina(this.sciName)) {
        requestSci
          .get(
            "title",
            {
              title: this.sciName,
              author: this.sciAuthor,
            },
            token
          )
          .then((r) => {
            console.info("first");
            console.info(r);
            this.loading = false;
            if (
              r.data != null &&
              r.data.data != null &&
              r.data.data.length > 0
            ) {
              this.artData = r.data.data[0];
              this.journalName = this.getJournalName(this.artData.journal);
              var arMonth = this.monthData.filter(
                (p) => p.key == this.artData.pub_month
              );
              if (arMonth.length > 0) {
                this.month = arMonth[0].value;
              }
              this.handleSearch(token);
            } else {
              setTimeout(() => {
                this.searchIndex += 1;
              }, 200);
            }
          });

        requestSci
          .get(
            "title",
            {
              //获取他因次数
              title: this.sciName,
              author: this.sciAuthor,
              source: "wos",
            },
            this.token
          )
          .then((r) => {
            console.info(r);
            // this.loading = false;
            if (
              r.data.status == "success" &&
              r.data.data != null &&
              r.data.data.length > 0
            ) {
              this.cited_count = r.data.data[0].cited_count;
            }
            // this.artData = r.data;
          });
      } else if (this.sciName != "" && this.isChina(this.sciName)) {
        //中文搜索
        requestSci
          .get(
            "title",
            {
              title: this.sciName,
              author: this.sciAuthor,
              source: "chinese",
            },
            this.token
          )
          .then((r) => {
            console.info(r);
            this.loading = false;
            if (
              r.data != null &&
              r.data.data != null &&
              r.data.data.length > 0
            ) {
              this.artData = r.data.data[0];
              console.info(this.artData);
              this.journalName = this.getJournalName(this.artData.journal);
              this.qkjb = this.getJbByCSTPCD(this.journalName);
              var arMonth = this.monthData.filter(
                (p) => p.key == this.artData.pub_month
              );
              if (arMonth.length > 0) {
                this.month = arMonth[0].value;
              }
              this.handleSearch_zw(token);
            } else {
              setTimeout(() => {
                this.searchIndex += 1;
              }, 200);
            }
          });
      }
    },
    handleSearch(token) {
      this.loading = true;
      console.info("hgh:" + this.journalName.replace(/\&/g, "%26"));
      requestSci
        .get(
          "jif",
          {
            journal_title: this.journalName.replace(/\&/g, "%26"),
            year: this.artData.pub_year < 2022 ? this.artData.pub_year : "2021",
            issn: this.artData.issn,
          },
          token
        )
        .then((r) => {
          this.loading = false;
          console.info(r);
          if (
            r.data != null &&
            r.data.status != "error" &&
            r.data.data != null
          ) {
            this.jifData = r.data.data;
            this.jifData.isSci = "是";

            this.issn = this.jifData.issn;
            this.rankValue = this.handleRank(this.jifData.rank);
            if (this.rankValue != "") {
              this.isSci = "是";
              this.slqk = "SCI";
              this.qkjb = this.getFinalJb(
                this.journalName,
                this.rankValue,
                this.issn
              );
              if (this.qkjb == "A" || this.qkjb == "B") {
                this.isBest = "是";
              }
            }
          } else {
            this.qkjb = "F";
          }
          setTimeout(() => {
            this.searchIndex += 1;
          }, 200);
        });
    },
    handleSearch_zw(token) {
      this.loading = true;
      this.cscd = {
        is_cscd: "",
        journal_name: "",
        issn: "",
        centered: "",
      };
      requestSci
        .get(
          "cscd",
          {
            journal_title: this.journalName.replace(/&/, "%26"),
          },
          this.token
        )
        .then((r) => {
          this.loading = false;
          console.info(r);
          if (
            r.data != null &&
            r.data.status != "error" &&
            r.data.data != null &&
            r.data.data.is_cscd == true
          ) {
            this.cscd = r.data.data;
            this.issn = r.data.data.issn;

            this.qkjb = this.getFinalJb_zw(this.issn);
            if (this.qkjb == "A" || this.qkjb == "B") {
              this.isBest = "是";
            }
            this.slqk = "中国科学引文数据库";
          } else {
            //this.qkjb = 'F'
          }
          setTimeout(() => {
            this.searchIndex += 1;
          }, 200);
        });
    },
    getJournalName(name) {
      //获取等号前面的
      if (name.indexOf("=") != -1) {
        return name.split("=")[0];
      }
      return name;
    },
    handleRank(rankValue) {
      //取最小值，去掉“无”   例如 无|2/3|无|34/56 ,
      if (rankValue == "" || rankValue == "无" || rankValue == "N/A") return "";
      if (rankValue.indexOf("|") != -1) {
        var arr = rankValue.split("|");
        var r_v = 0;
        for (var i = 0; i < arr.length; i++) {
          if (arr[i] != "无" && arr[i] != 'N/A') {
            var v = eval(arr[i]);
            console.info("rankValue:" + v);
            var v2 = Math.round(v * 10000) / 100;
            if (v2 < r_v || r_v == 0) {
              r_v = v2.toFixed(2);
            }
          }
        }
      } else {
        if (rankValue != "无" && rankValue !='N/A') {
          var v = eval(rankValue);
          var v2 = Math.round(v * 10000) / 100;
          v2 = v2.toFixed(2);
          return v2;
        }
      }
      if (r_v == 0) return "";
      return r_v;
    },
    getQKJBbyRank(v) {
      console.info("v:" + v);
      if (v == "") {
        return "F";
      }
      var v3 = parseFloat(v);
      // console.info("v的值："+v)
      if (v3 <= 20) {
        return "A";
      }
      if (20 < v3 && v3 <= 50) {
        return "B";
      }
      if (v3 > 50) {
        return "C";
      }
    },
    getJbByIssN(ISSN) {
      console.info("ISSN:" + ISSN);
      if (ISSN == "" || ISSN == "null" || ISSN == null) {
        return "";
      }
      var arrIs = ISSN.split("|");
      var jb = "";
      for (var i = 0; i < arrIs.length; i++) {
        var a = this.jbLbList.filter((p) => p.journalCode == arrIs[i]);
        if (a != null && a.length > 0) {
          if (jb == "") {
            jb = a[0].jb;
          } else {
            //return a[0].jb
            if (a[0].jb < jb && a[0].jb != "") {
              jb = a[0].jb;
            }
          }
        }
      }
      return jb;
    },
    getJbByJournalName(name) {
      if (name == "") return "";
      //根据期刊名 获取级别
      if (name.indexOf("华中科技大学学报") == 0) {
        return "D";
      }
      var a = this.jbLbList.filter(
        (p) => p.journalName.toUpperCase() == name.toUpperCase()
      );
      if (a != null && a.length > 0) {
        return a[0].jb;
      }

      return "";
    },
    getJbByCSTPCD(JournalName) {
      var jb = this.getJbByJournalName(JournalName);
      if (this.artData.sd_chinese_journal_meta.CSTPCD != null) {
        console.info(this.artData.sd_chinese_journal_meta.CSTPCD);
        if (
          this.artData.sd_chinese_journal_meta.CSTPCD ==
          "中国科技论文与引文数据库"
        ) {
          this.slqk = "中国科学引文数据库";
          // console.info("jb:"+jb+" "+ (jb>'E'))
          if (jb == "" || jb > "E") {
            jb = "E";
          }
        }
      }
      if (jb == "") {
        jb = "F";
      }

      // console.info("jb:"+jb+" "+ (jb>'E'))
      return jb;
    },
    getFinalJb_zw(ISSN) {
      var jb = this.qkjb;
      console.info("issnJb:" + jb);
      // var v1 = this.getJbByJournalName(JournalName);
      var v2 = this.getJbByIssN(ISSN);

      if (jb == "") {
        jb = v2;
      }

      if (v2 < jb && v2 != "") {
        jb = v2;
      }
      if (jb == "") {
        return "E";
      }
      return jb;
    },
    getFinalJb(JournalName, rankValue, ISSN) {
      var jb = "";
      var v1 = this.getQKJBbyRank(rankValue);
      var v2 = this.getJbByIssN(ISSN);
      var v3 = this.getJbByJournalName(JournalName);
      console.info("rv:" + v1);
      console.info("issn:" + v2);
      console.info("name:" + v3);

      jb = v1;
      if (jb == "") {
        jb = v2;
      }

      if (v2 < jb && v2 != "") {
        jb = v2;
      }
      if (jb == "") {
        jb = v3;
      }

      if (v3 < jb && v3 != "") {
        jb = v3;
      }

      if (jb == "") {
        return "F";
      }
      return jb;
    },
    isChina(s) {
      var patrn = /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;

      if (!patrn.exec(s)) {
        return false;
      } else {
        return true;
      }
    },
    setIsShow(){
      this.isShow2 = true;
    }
  },
};
</script>

<style>
</style>