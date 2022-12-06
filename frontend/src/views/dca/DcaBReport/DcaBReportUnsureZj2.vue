<template>
  <div>
    <a-spin :spinning="loading">
      <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="(record) => record.id"
        :pagination="pagination"
        @change="handleTableChange"
        :rowSelection="{
          selectedRowKeys: selectedRowKeys,
          onChange: onSelectChange,
        }"
        bordered
        :scroll="scroll"
      >
        <template slot="splitHang" slot-scope="text, record">
          <div style="width: 100%" v-for="item in splitStr(text)">{{ item }}</div>
        </template>
        <template slot="userAccount" slot-scope="text, record">
          <a @click="showUserInfo(text)">{{ text }}</a>
        </template>
        <template slot="action" slot-scope="text, record">
          <div v-if="record.state == 2">
            <a-button
              style="width: 100%; padding-left: 2px; padding-right: 2px"
              type="dashed"
              block
              @click="ExportDeclareReport(record)"
            >
              导出职称申报表
            </a-button>
            <a-button
              style="width: 100%; padding-left: 2px; padding-right: 2px"
              type="dashed"
              block
              @click="ExportAttachReport(record)"
            >
              导出附件材料
            </a-button>
            <a-button
              style="width: 100%; padding-left: 2px; padding-right: 2px"
              type="dashed"
              block
              @click="ExportDocReport(record)"
            >
              确认表
            </a-button>
             <a-button
              v-if="record.showState == 0"
              v-hasNoPermission="['dca:audit']"
              style="width: 100%; padding-left: 2px; padding-right: 2px"
              type="dashed"
              block
              @click="handleBack(record)"
            >
              退回待处理
            </a-button>
          </div>
          <div v-else-if="record.state == 1">
            <a-button
              v-hasNoPermission="['dca:audit']"
              style="width: 100%; padding-left: 2px; padding-right: 2px"
              type="dashed"
              block
              @click="handleSave(record)"
            >
              退回
            </a-button>
          </div>
        </template>
        <template slot="auditMan" slot-scope="text, record">
          <div v-if="text == '正常'">
            <a-tag color="green" @click="showUserInfoRight(record)">正常</a-tag>
          </div>
          <div v-else>
            <a-tag color="red" @click="showUserInfoRight(record)">异常</a-tag>
          </div>
        </template>
      </a-table>
      <audit-userInfo
        ref="userinfo"
        @close="onCloseUserInfo"
        :visibleUserInfo="visibleUserInfo"
        :userAccount="userAccount"
      ></audit-userInfo>
      <audit-resultInfo
        ref="userinfo"
        @close="onCloseUserInfoRight"
        :visibleUserInfo="visibleUserInfo_right"
        :userAccount="userAccount_right"
        :dcaYear="deaYear"
        :gwdj="gwDj"
      ></audit-resultInfo>
    </a-spin>
  </div>
</template>

<script>
import moment from "moment";
import AuditUserInfo from "../../common/AuditUserInfo";
import AuditResultInfo from "../../common/AuditResultInfo";
export default {
  data() {
    return {
      dateFormat: "YYYY-MM-DD",
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ["10", "20", "30", "40", "100"],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) =>
          `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`,
      },
      queryParams: {
        userAccount: "",
        ks: "二三级"
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 2700,
        y: window.innerHeight - 200 - 100 - 20 - 80,
      },
      visibleUserInfo: false,
      userAccount: "",
      userAccount_right: "",
      visibleUserInfo_right: false,
      deaYear: "",
      gwDj: ''
    };
  },
  components: { AuditUserInfo, AuditResultInfo },
  props: {
    state: {
      default: 1,
    },
    activeKey: {
      default: 1,
    },
  },
  mounted() {
    this.fetch2(this.queryParams);
  },
  methods: {
    moment,
    splitStr(text) {
      if (text == null) return "";
      return text.split("#");
    },
    search() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.fetch2({
        sortField: "clshjg,prize,np_position_name desc,ifbxyltj,paixu2,paixu3,xrgwjbprsj_back,paixu4,edu_date_back,birthdaystr_back  ",
          sortOrder: "ascend",
        ...this.queryParams,
      });
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys;
    },
    fetch2(params = {}) {
      this.loading = true;
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current;
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize;
        params.pageSize = this.paginationInfo.pageSize;
        params.pageNum = this.paginationInfo.current;
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize;
        params.pageNum = this.pagination.defaultCurrent;
      }
      params.sortField= "clshjg,prize,np_position_name desc,ifbxyltj,paixu2,paixu3,xrgwjbprsj_back,paixu4,edu_date_back,birthdaystr_back  ";
          params.sortOrder = "ascend";
      this.$get("dcaBReport", {
        state: this.state,
        ...params,
      }).then((r) => {
        this.loading = false;
        let data = r.data;
        const pagination = { ...this.pagination };
        pagination.total = data.total;
        this.dataSource = data.rows;
        this.pagination = pagination;
      });
    },
    ExportDeclareReport(record) {
      this.$download(
        "dcaBCopyUser/excel",
        {
          userAccount: record.userAccount,
          dcaYear: record.year,
          gwdj: record.gwdj,//岗位等级
          npPositionName: record.npPositionName,
          sexName: record.gwdj, //岗位等级
        },
        record.userAccount + ".pdf"
      );
    },
    ExportDocReport(record) {
      this.$download(
        "dcaBCopyUser/doc",
        {
          userAccount: record.userAccount,
          year: record.year,
          
          npPositionName: record.npPositionName,
          gwdj: record.gwdj, //岗位等级
        },
        record.year + "_" + record.userAccount + "_二三级确认表" + ".docx"
      );
    },
    ExportAttachReport(record) {
      this.$download(
        "dcaBCopyUser/attach",
        {
          userAccount: record.userAccount,
          dcaYear: record.year,
          gwdj: record.gwdj,//岗位等级
          npPositionName: record.npPositionName,
        },
        record.year + record.userAccount + ".pdf"
      );
    },
      handleBack(record) {
      // let jsonStr = JSON.stringify(record)
      let vRecord = {};
      vRecord.id = record.id;
      vRecord.userAccount = record.userAccount;
      vRecord.state = 0;
      var that = this;
      that.$confirm({
        title: "确定退回所选中的记录?",
        content: "当您点击确定按钮后，此记录将退回待处理状态！",
        centered: true,
        onOk() {
          // vRecord.dcaBAuditdynamicList=''
          that.loading = true;
          that
            .$put("dcaBReport", {
              ...vRecord,
            })
            .then(() => {
              // this.reset()
              that.$message.success("退回成功");
              that.search();
              that.loading = false;
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
      });
    },
    handleSave(record) {
      // let jsonStr = JSON.stringify(record)
      let vRecord = {};
      vRecord.id = record.id;
      vRecord.userAccount = record.userAccount;
      vRecord.state = 0;
      // vRecord.dcaBAuditdynamicList=''
      this.loading = true;
      this.$put("dcaBReport", {
        ...vRecord,
      })
        .then(() => {
          // this.reset()
          this.$message.success("保存成功");
          this.search();
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    onCloseUserInfo() {
      this.visibleUserInfo = false;
    },
    showUserInfo(text) {
      //debugger
      this.visibleUserInfo = true;
      this.userAccount = text;
    },
    showUserInfoRight(record) {
      //debugger
      this.visibleUserInfo_right = true;
      this.userAccount_right = record.userAccount;
      this.deaYear = record.state == 2 ? record.year : "";
      this.gwDj= record.gwdj
    },
    onCloseUserInfoRight() {
      this.visibleUserInfo_right = false;
    },
    fetch(userAccount) {
      this.loading = true;
      this.queryParams.userAccount = userAccount;
      let params = {};
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current;
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize;
        params.pageSize = this.paginationInfo.pageSize;
        params.pageNum = this.paginationInfo.current;
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize;
        params.pageNum = this.paginationInfo.defaultCurrent;
      }
      params.sortField = "clshjg,prize,np_position_name desc,ifbxyltj,paixu2,paixu3,xrgwjbprsj_back,paixu4,edu_date_back,birthdaystr_back  ";
          params.sortOrder = "ascend";
      params.userAccount = userAccount;
      this.$get("dcaBReport", {
        state: this.state,
        ...params,
      }).then((r) => {
        this.loading = false;
        let data = r.data;
        const pagination = { ...this.pagination };
        pagination.total = data.total;
        this.dataSource = data.rows;
        this.pagination = pagination;
      });
    },
    handleTableChange(pagination, filters, sorter) {
      this.sortedInfo = sorter;
      this.paginationInfo = pagination;
      this.fetch2({
         sortField: "clshjg,prize,np_position_name desc,ifbxyltj,paixu2,paixu3,xrgwjbprsj_back,paixu4,edu_date_back,birthdaystr_back  ",
          sortOrder: "ascend",
        ...this.queryParams,
      });
    },
    exportCustomExcel() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
         let json = [
        {
          title: "顺序号1",
          dataIndex: "confirmIndex",
          width: 100,
        },
        {
          title: "报名档案顺序号2",
          dataIndex: "baomingIndex",
          width: 100,
        },
        {
          title: "科室分类3",
          dataIndex: "kslb",
          width: 180,
        },
        {
          title: "申报等级4",
          dataIndex: "gwdj",
          width: 60,
          //    fixed: 'left',
        },
        {
          title: "科室5",
          dataIndex: "ks",
          width: 80,
          //    fixed: 'left',
        },
        {
          title: "职员代码6",
          dataIndex: "userAccount",
          width: 80,
        },
        {
          title: "性别7",
          dataIndex: "sexName",
          width: 80,
          //    fixed: 'left',
        },
        {
          title: "姓名8",
          dataIndex: "userAccountName",
          width: 80,
          //    fixed: 'left',
        },
        {
          title: "出生年月9",
          dataIndex: "birthdaystr",
          width: 100,
        },
        {
          title: "年龄10",
          dataIndex: "age",
          width: 60,
        },
        {
          title: "学历(位)11",
          dataIndex: "edu",
          width: 100,
        },
        {
          title: "毕业时间12",
          dataIndex: "eduDate",
          width: 100,
        },

        {
          title: "任职13",
          dataIndex: "positionName",
          width: 100,
        },
        {
          title: "聘任时间14",
          dataIndex: "zygwDate",
          width: 100,
        },

        {
          title: "岗位等级15",
          dataIndex: "xrgwjb",
          width: 100,
        },
        {
          title: "任职时间16",
          dataIndex: "xrgwjbprsj",
          width: 100,
        },
        {
          title: "申报三级时是否使用医疗条件17",
          dataIndex: "ifsyyltj",
          width: 100
        },
        {
          title: "任博导时间18",
          dataIndex: "rbdsj",
          width: 100,
        },
        {
          title: "申报岗位19",
          dataIndex: "npPositionName",
          width: 100,
        },
        {
          title: "申报年度20",
          dataIndex: "year",
          width: 100,
        },
        {
          title: "是否符合必备条件21",
          dataIndex: "iffuhebibei",
          width: 80
        },
        {
          title: "是否必须使用医疗条件22",
          dataIndex: "ifbxyltj",
          width: 80
        },
        {
          title: "满足学术条件情况23",
          dataIndex: "mzsstjqk",
          width: 80
        },
 {
          title: "满足学术条件情况24",
          dataIndex: "mzsstjqkAudit",
          width: 80
        },
      
        {
          title: "分数25",
          dataIndex: "jxpfdj",
          width: 80,
        },
        {
          title: "近三年核心人力资源评分26",
          dataIndex: "j3nhxrlzypf",
          width: 80
        },
        {
          title: "近三年医疗综合评分27",
          dataIndex: "j3ylzhpf",
          width: 100,
        },
        {
          title: "近三年手术台次28",
          dataIndex: "j3nssztc",
          width: 100,
        },

        {
          title: "近三年收治住院病人数29",
          dataIndex: "j3nzyszbrsl",
          width: 100,
        },
        {
          title: "近三年门诊收治病人数30",
          dataIndex: "j3nmzszbrsl",
          width: 100,
        },

        {
          title: "负责开展的新技术新业务31",
          dataIndex: "xjsxyw",
          width: 100,
        },
        {
          title: "负责的新技术新业务获奖情况32",
          dataIndex: "xjsxywprize",
          width: 100,
        },
         {
          title: "单篇SCI高章≥3033",
          dataIndex: "dpsci20",
          width: 60,
        },
        {
          title: "单篇SCI高分文章≥1033",
          dataIndex: "dpsci10",
          width: 60,
        },
        {
          title: "A 类34",
          dataIndex: "publishA",
          width: 60,
        },
        {
          title: "B 类35",
          dataIndex: "publishB",
          width: 60,
        },
        {
          title: "C 类36",
          dataIndex: "publishC",
          width: 60,
        },
        {
          title: "D 类37",
          dataIndex: "publishD",
          width: 60,
        },
        {
          title: "E 类38",
          dataIndex: "publishE",
          width: 60,
        },
        {
          title: "F 类39",
          dataIndex: "publishF",
          width: 60,
        },
        {
          title: "著作40",
          dataIndex: "publicarticle1",
          width: 100,
        },
        {
          title: "数量41",
          dataIndex: "publicarticle2",
          width: 100,
        },

        {
          title: "主持国家级课题42",
          dataIndex: "sciDjlb",
          width: 100,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "资助金额万元43",
          dataIndex: "sciDjfund",
          width: 100,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "国家自然基金资助批准时间44",
          dataIndex: "sciDjranknum",
          width: 100,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "名称45",
          dataIndex: "sciName",
          width: 100,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "等级46",
          dataIndex: "sciDengji",
          width: 60,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "排名47",
          dataIndex: "sciRanknum",
          width: 60,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "学会任职48",
          dataIndex: "xhrzqk",
          width: 100,
        },
        {
          title: "重要抗疫奖励",
          dataIndex: "prize",
          width: 100,
        },
        {
          title: "是否符合基本条件50",
          dataIndex: "clshjg",
          width: 100,
          scopedSlots: { customRender: "clshjg" },
        },
        {
          title: "退审原因51",
          dataIndex: "ntyy",
          width: 100,
          scopedSlots: { customRender: "ntyy" },
        },
        {
          title: "备注52",
          dataIndex: "note",
          width: 100,
          scopedSlots: { customRender: "note" },
        },
         {
          title: "备注2",
          dataIndex: "paixu4",
          width: 100,
        },
        {
          title: "联系方式53",
          dataIndex: "telephone",
          width: 100,
        },
      ];
      let dataJson = JSON.stringify(json);

      this.$export("dcaUserAudit/excelBigTable", {
         sortField: "clshjg,prize,np_position_name desc,ifbxyltj,paixu2,paixu3,xrgwjbprsj_back,paixu4,edu_date_back,birthdaystr_back  ",
          sortOrder: "ascend",
        state: this.state,
        dataJson: dataJson,
        excelIndex: 2,
        ...this.queryParams,
      });
    },
  },
  computed: {
    columns() {
      let clm = [
        {
          title: "顺序号",
          dataIndex: "confirmIndex",
          width: 100,
          scopedSlots: { customRender: "confirmIndex" },
          // fixed: 'left',
        },
        {
          title: "报名档案顺序号",
          dataIndex: "baomingIndex",
          width: 100,
        },
        {
          title: "科室分类",
          dataIndex: "kslb",
          width: 180,
          //    fixed: 'left',
        },
        {
          title: "申报等级",
          dataIndex: "gwdj",
          width: 60,
          //    fixed: 'left',
        },
        {
          title: "科室",
          dataIndex: "ks",
          width: 80,
          //    fixed: 'left',
        },
        {
          title: "职员代码",
          dataIndex: "userAccount",
          width: 80,
          scopedSlots: { customRender: "userAccount" },
          //     fixed: 'left',
        },
        {
          title: "性别",
          dataIndex: "sexName",
          width: 80,
          //    fixed: 'left',
        },
        {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 80,
          //    fixed: 'left',
        },
        {
          title: "出生年月",
          dataIndex: "birthdaystr",
          width: 100,
        },
        {
          title: "年龄",
          dataIndex: "age",
          width: 60,
        },
        {
          title: "学历(位)",
          dataIndex: "edu",
          width: 100,
        },
        {
          title: "毕业时间",
          dataIndex: "eduDate",
          width: 100,
        },
        {
          title: "专业技术职务",
          children: [
            {
              title: "任职",
              dataIndex: "positionName",
              width: 100,
            },
            {
              title: "聘任时间",
              dataIndex: "zygwDate",
              width: 100,
            },
          ],
        },
        {
          title: "现任岗位级别及时间",
          children: [
            {
              title: "岗位等级",
              dataIndex: "xrgwjb",
              width: 100,
            },
            {
              title: "任职时间",
              dataIndex: "xrgwjbprsj",
              width: 100,
            },
          ],
        },
        {
          title: "申报三级时是否使用医疗条件",
          dataIndex: "ifsyyltj",
          width: 100,
          scopedSlots: { customRender: "ifsyyltj" },
        },
        {
          title: "任博导时间",
          dataIndex: "rbdsj",
          width: 100,
        },
        {
          title: "申报岗位",
          dataIndex: "npPositionName",
          width: 100,
        },
        {
          title: "申报年度",
          dataIndex: "year",
          width: 100,
        },
        {
          title: "是否符合必备条件",
          dataIndex: "iffuhebibei",
          width: 80,
          scopedSlots: { customRender: "iffuhebibei" },
        },
        {
          title: "是否必须使用医疗条件",
          dataIndex: "ifbxyltj",
          width: 80,
          scopedSlots: { customRender: "ifbxyltj" },
        },
        {
          title: "满足学术条件情况(申报人填报)",
          dataIndex: "mzsstjqk",
          width: 300,
          scopedSlots: { customRender: "splitHang" },
        },
         {
          title: "满足学术条件情况(人事处审核)",
          dataIndex: "mzsstjqkAudit",
          width: 300,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "教学评分",
          dataIndex: "jxpf2",
              width: 80,
              customRender: (text, row, index) => {
                return row.jxpfdj;
              },
        },
        {
          title: "近三年核心人力资源评分均值",
          dataIndex: "j3nhxrlzypf",
          width: 80,
          scopedSlots: { customRender: "j3nhxrlzypf" },
        },
        {
          title: "近三年医疗综合评分",
          dataIndex: "j3ylzhpf",
          width: 100,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "近三年手术台次",
          dataIndex: "j3nssztc",
          width: 100,
          scopedSlots: { customRender: "splitHang" },
        },

        {
          title: "近三年收治住院病人数",
          dataIndex: "j3nzyszbrsl",
          width: 100,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "近三年门诊收治病人数",
          dataIndex: "j3nmzszbrsl",
          width: 100,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "近三年新技术新业务",
          children: [
            {
              title: "负责开展的新技术新业务",
              dataIndex: "xjsxyw",
              width: 100,
               scopedSlots: { customRender: "splitHang" },
            },
            {
              title: "负责的新技术新业务获奖情况",
              dataIndex: "xjsxywprize",
              width: 100,
               scopedSlots: { customRender: "splitHang" },
            },
          ],
        },
        {
          title: "任现岗位以来",
          children: [
            {
              title: "单篇SCI高分文章≥20（折算后）",
              dataIndex: "dpsci20",
              width: 60,
            },
            {
              title: "单篇SCI高分文章≥10且<20（折算后）",
              dataIndex: "dpsci10",
              width: 60,
            },
            {
              title: "发表主要文章（折算后）",
              children: [
                {
                  title: "A 类",
                  dataIndex: "publishA",
                  width: 60,
                },
                {
                  title: "B 类",
                  dataIndex: "publishB",
                  width: 60,
                },
                {
                  title: "C 类",
                  dataIndex: "publishC",
                  width: 60,
                },
                {
                  title: "D 类",
                  dataIndex: "publishD",
                  width: 60,
                },
                {
                  title: "E 类",
                  dataIndex: "publishE",
                  width: 60,
                },
                {
                  title: "F 类",
                  dataIndex: "publishF",
                  width: 60,
                },
              ],
            },
            {
              title: "著作",
              children: [
                {
                  title: "著作",
                  dataIndex: "publicarticle1",
                  width: 100,
                  scopedSlots: { customRender: "splitHang" },
                },
                {
                  title: "数量",
                  dataIndex: "publicarticle2",
                  width: 100,
                  scopedSlots: { customRender: "splitHang" },
                },
              ],
            },
            {
              title: "主持国家级课题",
              children: [
                {
                  title: "主持国家级课题",
                  dataIndex: "sciDjlb",
                  width: 100,
                  scopedSlots: { customRender: "splitHang" },
                },
                {
                  title: "资助金额万元",
                  dataIndex: "sciDjfund",
                  width: 100,
                  scopedSlots: { customRender: "splitHang" },
                },
                {
                  title: "国家自然基金资助批准时间",
                  dataIndex: "sciDjranknum",
                  width: 100,
                  scopedSlots: { customRender: "splitHang" },
                },
              ],
            },
            {
              title: "科研获奖",
              children: [
                {
                  title: "名称",
                  dataIndex: "sciName",
                  width: 100,
                  scopedSlots: { customRender: "splitHang" },
                },
                {
                  title: "等级",
                  dataIndex: "sciDengji",
                  width: 60,
                  scopedSlots: { customRender: "splitHang" },
                },
                {
                  title: "排名",
                  dataIndex: "sciRanknum",
                  width: 60,
                  scopedSlots: { customRender: "splitHang" },
                },
              ],
            },
          ],
        },
        {
          title: "学会任职",
          dataIndex: "xhrzqk",
          width: 250,
          scopedSlots: { customRender: "splitHang" },
        },
          {
          title: "重要抗疫奖励",
          dataIndex: "prize",
          width: 120,
          scopedSlots: { customRender: "splitHang" },
        },
        {
          title: "材料审核结果",
          dataIndex: "clshjg",
          width: 100,
          scopedSlots: { customRender: "clshjg" },
        },
        {
          title: "退审原因",
          dataIndex: "ntyy",
          width: 100,
          scopedSlots: { customRender: "ntyy" },
        },
        {
          title: "备注",
          dataIndex: "note",
          width: 100,
          scopedSlots: { customRender: "note" },
        },
         {
          title: "备注2",
          dataIndex: "paixu4",
          width: 100,
        },
        {
          title: "联系方式",
          dataIndex: "telephone",
          width: 100,
        },
        {
          title: "操作",
          dataIndex: "action",
          scopedSlots: { customRender: "action" },

          width: 120,
        },
      ];
      // if(this.state==1){
      //   clm.push({
      //     title: '操作',
      //     dataIndex: 'action',
      //     scopedSlots: { customRender: 'action' },

      //     width: 100
      //   })
      // }
      let filtersCls = [
        "confirmIndex",
        "pingshenfenzu",
        "kslb",
        "iffuhebibei",
        "sblx",
        "choosepos",
        "auditMan",
        "clshjg",
        "ntyy",
        "ksrank",
        "note",
      ];
      let permissions = this.$store.state.account.permissions;
      //console.info(permissions)
      if (permissions.includes("dca:audit")) {
        clm = clm.filter((p) => !filtersCls.includes(p.dataIndex));
      }
      return clm;
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>