<template>
  <div>
    <a-spin :spinning="loading">
      <a-card title="主要科研业绩">
        <div>
          <a-form layout="horizontal">
            <a-row>
              <div>
                <a-col :md="8" :sm="24">
                  <a-form-item label="发薪号" v-bind="formItemLayout">
                    <a-input v-model="queryParams.userAccount" />
                  </a-form-item>
                </a-col>
                <a-col :md="8" :sm="24">
                  <a-form-item label="序号" v-bind="formItemLayout">
                    <a-input-number
                      style="width: 40% !important"
                      v-model="queryParams.auditXuhaoS"
                    ></a-input-number
                    >至<a-input-number
                      style="width: 40% !important"
                      v-model="queryParams.auditXuhaoE"
                    ></a-input-number>
                  </a-form-item>
                </a-col>
                <a-col :md="8" :sm="24">
                  <a-form-item
                    label="申报年度"
                    v-bind="formItemLayout"
                    v-show="!dcaType == ''"
                  >
                    <a-input v-model="queryParams.auditMan" />
                  </a-form-item>
                </a-col>
              </div>
              <span style="float: right; margin-top: 3px">
                <a-button type="primary" @click="search2">查询</a-button>
                <a-button style="margin-left: 8px" @click="reset"
                  >重置</a-button
                >
              </span>
            </a-row>
          </a-form>
        </div>
        <a-tabs type="card" @change="callback">
          <a-tab-pane key="1" tab="待审核">
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
              :bordered="true"
              :scroll="scroll"
            >
              <template slot="achievementName" slot-scope="text, record">
                <div v-if="record.state == 3 || record.state == 1">
                  {{ text }}
                </div>
                <div v-else>
                  <a-select
                    :value="record.achievementName"
                    style="width: 430px"
                    @change="
                      (e, f) =>
                        handleSelectChange(e, f, record, 'achievementName')
                    "
                  >
                     <a-select-option value="“973”首席科学家"
                      >“973”首席科学家</a-select-option
                    >
                     <a-select-option value="国家级科技三大奖一等奖的前两名"
                      >国家级科技三大奖一等奖的前两名</a-select-option
                    >
                     <a-select-option value="国家级科技三大奖二等奖的第一名"
                      >国家级科技三大奖二等奖的第一名</a-select-option
                    >
                     <a-select-option value="国家杰出青年科学基金获得者"
                      >国家杰出青年科学基金获得者</a-select-option
                    >
                     <a-select-option
                      value="近五年在《science》国际顶级期刊以华中科技大学为第一署名单位发表学术论文的通讯作者或第一作者"
                      >近五年在《science》国际顶级期刊以华中科技大学为第一署名单位发表学术论文的通讯作者或第一作者</a-select-option
                    >
                     <a-select-option
                      value="近五年在《nature》国际顶级期刊以华中科技大学为第一署名单位发表学术论文的通讯作者或第一作者"
                      >近五年在《nature》国际顶级期刊以华中科技大学为第一署名单位发表学术论文的通讯作者或第一作者</a-select-option
                    >
                     <a-select-option
                      value="近五年在《cell》国际顶级期刊以华中科技大学为第一署名单位发表学术论文的通讯作者或第一作者"
                      >近五年在《cell》国际顶级期刊以华中科技大学为第一署名单位发表学术论文的通讯作者或第一作者</a-select-option
                    >
                     <a-select-option
                      value="国家自然（社会）科学基金重大项目负责人"
                      >国家自然（社会）科学基金重大项目负责人</a-select-option
                    >
                     <a-select-option
                      value="国家自然（社会）科学基金重点项目负责人"
                      >国家自然（社会）科学基金重点项目负责人</a-select-option
                    >
                     <a-select-option value="863目标导向类项目负责人"
                      >863目标导向类项目负责人</a-select-option
                    >
                     <a-select-option value="863重点项目负责人"
                      >863重点项目负责人</a-select-option
                    >
                     <a-select-option value="863重大项目负责人"
                      >863重大项目负责人</a-select-option
                    >
                     <a-select-option value="863科技部支撑项目负责人"
                      >863科技部支撑项目负责人</a-select-option
                    >
                     <a-select-option
                      value="国家重点研发计划课题负责人（一级子项目负责人且批准金额大于200万）"
                      >国家重点研发计划课题负责人（一级子项目负责人且批准金额大于200万）</a-select-option
                    >
                     <a-select-option
                      value="近五年“973”课题组长（原一级子项目负责人，以科技部合同为准)"
                      >近五年“973”课题组长（原一级子项目负责人，以科技部合同为准)</a-select-option
                    >
                     <a-select-option value="国家重点研发计划项目负责人"
                      >国家重点研发计划项目负责人</a-select-option
                    >
                     <a-select-option
                      value="近五年在《science》国际顶级期刊以通讯作者或第一作者发表学术论文者（文科在《中国社会科学》发表）"
                      >近五年在《science》国际顶级期刊以通讯作者或第一作者发表学术论文者（文科在《中国社会科学》发表）</a-select-option
                    >
                     <a-select-option
                      value="近五年在《nature》国际顶级期刊以通讯作者或第一作者发表学术论文者（文科在《中国社会科学》发表）"
                      >近五年在《nature》国际顶级期刊以通讯作者或第一作者发表学术论文者（文科在《中国社会科学》发表）</a-select-option
                    >
                     <a-select-option
                      value="近五年在《cell》国际顶级期刊以通讯作者或第一作者发表学术论文者（文科在《中国社会科学》发表）"
                      >近五年在《cell》国际顶级期刊以通讯作者或第一作者发表学术论文者（文科在《中国社会科学》发表）</a-select-option
                    >
                     <a-select-option value="全国百篇优秀博士论文获得者"
                      >全国百篇优秀博士论文获得者</a-select-option
                    >
                     <a-select-option
                      value="省部级科技三大奖一等奖及以上（含社会科学成果奖）的第一名"
                      >省部级科技三大奖一等奖及以上（含社会科学成果奖）的第一名</a-select-option
                    >
                     <a-select-option value="近五年主持863项目的科研项目者"
                      >近五年主持863项目的科研项目者</a-select-option
                    >
                     <a-select-option
                      value="近五年主持国家自然（社会）科学基金2项以上的科研项目者"
                      >近五年主持国家自然（社会）科学基金2项以上的科研项目者</a-select-option
                    >
                     <a-select-option
                      value="近五年主持单项500万元以上的科研项目者"
                      >近五年主持单项500万元以上的科研项目者</a-select-option
                    >
                     <a-select-option
                      value="近五年在影响因子20以上的杂志以华中科技大学为第一完成单位发表第一作者或通讯作者学术论文1篇，或10以上杂志发表2篇；在A类期刊以第一作者或通讯作者署名发表本学科学术论文5篇"
                      >近五年在影响因子20以上的杂志以华中科技大学为第一完成单位发表第一作者或通讯作者学术论文1篇，或10以上杂志发表2篇；在A类期刊以第一作者或通讯作者署名发表本学科学术论文5篇</a-select-option
                    >
                     <a-select-option
                      value="国家科技三大奖（含社会科学成果奖）的前3名和省部级科技三大奖（含社会科学成果奖）二等以上奖的第一名"
                      >国家科技三大奖（含社会科学成果奖）的前3名和省部级科技三大奖（含社会科学成果奖）二等以上奖的第一名</a-select-option
                    >
                  </a-select>
                </div>
              </template>
              <template slot="rankIndex" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-input-number
                    style="width: 100%"
                    @blur="
                      (e) => inputChange(e.target.value, record, 'rankIndex')
                    "
                    :value="record.rankIndex"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template slot="achievementGrade" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-select
                    :value="record.achievementGrade"
                    style="width: 100%"
                    @change="
                      (e, f) =>
                        handleSelectChange(e, f, record, 'achievementGrade')
                    "
                  >
                    <a-select-option value="一"> 一 </a-select-option>
                    <a-select-option value="二"> 二 </a-select-option>
                    <a-select-option value="三"> 三 </a-select-option>
                  </a-select>
                </div>
              </template>
              <template slot="achievementDate" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text == "" || text == null ? "" : text.substr(0, 10) }}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="
                      text == '' || text == null ? '' : moment(text, dateFormat)
                    "
                    @change="
                      (e, f) => handleChange(e, f, record, 'achievementDate')
                    "
                  />
                </div>
              </template>
              <template slot="achievementDefine" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="
                      (e) =>
                        inputChange(e.target.value, record, 'achievementDefine')
                    "
                    :value="record.achievementDefine"
                  >
                  </a-textarea>
                </div>
              </template>
              <template slot="achievementContent" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="
                      (e) =>
                        inputChange(
                          e.target.value,
                          record,
                          'achievementContent'
                        )
                    "
                    :value="record.achievementContent"
                  >
                  </a-textarea>
                </div>
              </template>
              <template slot="isUse" slot-scope="text, record">
                <a-checkbox
                  @change="(e) => onIsUseChange(e, record, 'isUse')"
                  :checked="text"
                ></a-checkbox>
              </template>
              <template slot="auditSuggestion" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="
                      (e) =>
                        inputChange(e.target.value, record, 'auditSuggestion')
                    "
                    :value="record.auditSuggestion"
                  >
                  </a-textarea>
                </div>
              </template>
              <template slot="userAccount" slot-scope="text, record">
                <a href="#" @click="showUserInfo(text)">{{ text }}</a>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-button type="dashed" block @click="handleAudit(record)">
                  通过
                </a-button>
                <a-button type="danger" block @click="handleAuditNo(record)">
                  审核不通过
                </a-button>
              </template>
            </a-table>
          </a-tab-pane>
          <a-tab-pane key="2" tab="已审核" :forceRender="true">
            <dcaBSciachievement-done ref="TableInfo2" :state="3">
            </dcaBSciachievement-done>
          </a-tab-pane>
          <a-tab-pane key="3" tab="审核未通过" :forceRender="true">
            <dcaBSciachievement-done ref="TableInfo3" :state="2">
            </dcaBSciachievement-done>
          </a-tab-pane>
        </a-tabs>
        <audit-userInfo
          ref="userinfo"
          @close="onCloseUserInfo"
          :visibleUserInfo="visibleUserInfo"
          :userAccount="userAccount"
        :dcaYear="queryParams.auditMan"
        :gwdj="queryParams.auditManName"
        ></audit-userInfo>
      </a-card>
    </a-spin>
  </div>
</template>

<script>
import moment from "moment";
import DcaBSciachievementDone from "./DcaBSciachievementDone";
import AuditUserInfo from "../../common/AuditUserInfo";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  data() {
    return {
      dateFormat: "YYYY-MM-DD",
      advanced: false,
      dataSource: [],
      formItemLayout,
      selectedRowKeys: [],
      loading: false,
      dcaBParttimeVisiable: false,
      idNums: 10000,
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
        auditMan: this.dcaYear,
        auditManName: this.dcaType,
        auditXuhaoE: null,
        auditXuhaoS: null,
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1800,
        y: window.innerHeight - 200 - 100 - 20 - 80,
      },
      visibleUserInfo: false,
      userAccount: "",
      activeKey: 1,
    };
  },
  components: { DcaBSciachievementDone, AuditUserInfo },
  mounted() {
    this.search();
  },
  props: {
    dcaYear: {
      default: "", //年度
    },
    dcaType: {
      default: "", //中高级
    },
  },
  methods: {
    moment,
    callback(activeKey) {
      this.activeKey = activeKey;
    },
    search2() {
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent;
      }
      this.search();
    },
    search() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.fetch({
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams,
      });
      this.freshTabs();
    },
    freshTabs() {
      this.$refs.TableInfo2.queryParams.userAccount =
        this.queryParams.userAccount;
      this.$refs.TableInfo2.queryParams.auditMan = this.queryParams.auditMan;
      this.$refs.TableInfo2.queryParams.auditManName =
        this.queryParams.auditManName;
      this.$refs.TableInfo3.queryParams.userAccount =
        this.queryParams.userAccount;
      this.$refs.TableInfo3.queryParams.auditMan = this.queryParams.auditMan;
      this.$refs.TableInfo3.queryParams.auditManName =
        this.queryParams.auditManName;

      if (this.queryParams.auditXuhaoS !== undefined) {
        this.$refs.TableInfo2.queryParams.auditXuhaoS =
          this.queryParams.auditXuhaoS;
        this.$refs.TableInfo3.queryParams.auditXuhaoS =
          this.queryParams.auditXuhaoS;
      }
      if (this.queryParams.auditXuhaoE !== undefined) {
        this.$refs.TableInfo2.queryParams.auditXuhaoE =
          this.queryParams.auditXuhaoE;
        this.$refs.TableInfo3.queryParams.auditXuhaoE =
          this.queryParams.auditXuhaoE;
      }

      this.$refs.TableInfo2.fetch2(this.$refs.TableInfo2.queryParams);
      this.$refs.TableInfo3.fetch2(this.$refs.TableInfo3.queryParams);
    },
    reset() {
      // 取消选中
      this.selectedRowKeys = [];
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent;
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent;
        this.paginationInfo.pageSize = this.pagination.defaultPageSize;
      }
      // 重置列排序规则
      this.sortedInfo = null;
      this.paginationInfo = null;
      // 重置查询参数
      this.queryParams = {};
      this.fetch();
    },
    exportCustomExcel() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
       let json = [...this.columns]
      json.splice(this.columns.length - 1, 1); //移出第一个
      console.info(json);
      let dataJson = JSON.stringify(json);

      let queryParams = this.queryParams;

      let state = 1;
      if (this.activeKey == 1) {
        state = 1;
      }
      if (this.activeKey == 2) {
        state = 3;
        delete queryParams.auditState;
      }
      if (this.activeKey == 3) {
        state = 2;
        delete queryParams.auditState;
      }
      this.$export("dcaBSciachievement/excel", {
        sortField: "user_account",
        sortOrder: "ascend",
        state: state,
        dataJson: dataJson,
        ...queryParams,
      });
    },
    handleTableChange(pagination, filters, sorter) {
      this.sortedInfo = sorter;
      this.paginationInfo = pagination;
      this.fetch({
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams,
      });
    },
    showUserInfo(text) {
      //debugger
      this.visibleUserInfo = true;
      this.userAccount = text;
    },
    onCloseUserInfo() {
      this.visibleUserInfo = false;
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      // console.log(selectedRows)
      if (selectedRows[0].state != 3) {
        this.selectedRowKeys = selectedRowKeys;
      }
    },
    handleChange(date, dateStr, record, filedName) {
      const value = dateStr;
      record[filedName] = value;
    },
    handleSelectChange(value, option, record, filedName) {
      console.info(value);
      record[filedName] = value;
    },
    inputCheckChange(blFlag, f, record, filedName) {
      record[filedName] = blFlag ? "是" : "否";
    },
    handleSelectChange(value, option, record, filedName) {
      console.info(value);
      record[filedName] = value;
    },
    inputChange(value, record, filedName) {
      console.info(value);
      record[filedName] = value;
    },
    onIsUseChange(e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    handleAuditNext(record) {
      let that = this;
      this.$confirm({
        title: "确定审核通过此记录?",
        content: "当您点击确定按钮后，此记录将进入下一个审核人",
        centered: true,
        onOk() {
          let jsonStr = JSON.stringify(record);
          that.loading = true;
          that
            .$post("dcaBSciachievement/updateNew", {
              jsonStr: jsonStr,
              state: 1,
            })
            .then(() => {
              //this.reset()
              that.$message.success("审核成功");
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
    handleAudit(record) {
      let that = this;
      this.$confirm({
        title: "确定审核通过此记录?",
        content: "当您点击确定按钮后，此记录将审核通过",
        centered: true,
        onOk() {
          let jsonStr = JSON.stringify(record);
          that.loading = true;
          that
            .$post("dcaBSciachievement/updateNew", {
              jsonStr: jsonStr,
              state: 3,
            })
            .then(() => {
              //this.reset()
              that.$message.success("审核成功");
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
    handleAuditNo(record) {
      let that = this;
      this.$confirm({
        title: "确定审核不通过此记录?",
        content: "当您点击确定按钮后，此记录将审核不通过",
        centered: true,
        onOk() {
          let jsonStr = JSON.stringify(record);
          that.loading = true;
          that
            .$post("dcaBSciachievement/updateNew", {
              jsonStr: jsonStr,
              state: 2,
            })
            .then(() => {
              //this.reset()
              that.$message.success("操作成功");
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
    fetch(params = {}) {
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
      params.sortField = "userAccount";
      params.sortOrder = "descend";
      this.loading = true;
      this.$get("dcaBSciachievement/audit", {
        ...params,
        state: 1,
      }).then((r) => {
        let data = r.data;
        this.loading = false;
        const pagination = { ...this.pagination };
        pagination.total = data.total;
        this.dataSource = data.rows;
        this.pagination = pagination;
      });
    },
  },
  computed: {
    columns() {
      return [
        {
          title: "序号",
          dataIndex: "auditXuhao",
          width: 60,
          fixed: "left",
        },
        {
          title: "发薪号",
          dataIndex: "userAccount",
          width: 80,
          scopedSlots: { customRender: "userAccount" },
          fixed: "left",
        },
        {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 80,
          fixed: "left",
        },
        {
          title: "名称",
          dataIndex: "achievementName",
          width: 450,
          scopedSlots: { customRender: "achievementName" },
          fixed: "left",
        },
        {
          title: "排名",
          dataIndex: "rankIndex",
          width: 80,
          scopedSlots: { customRender: "rankIndex" },
          fixed: "left",
        },
        {
          title: "获得时间",
          dataIndex: "achievementDate",
          width: 120,
          scopedSlots: { customRender: "achievementDate" },
          fixed: "left",
        },
        {
          title: "期限",
          dataIndex: "achievementDefine",
          width: 80,
          scopedSlots: { customRender: "achievementDefine" },
        },
        {
          title: "备注",
          dataIndex: "achievementContent",
          width: 130,
          scopedSlots: { customRender: "achievementContent" },
        },
        {
          title: "状态",
          dataIndex: "state",
          width: 80,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">未提交</a-tag>;
              case 1:
                return <a-tag color="green">已提交</a-tag>;
              case 2:
                return <a-tag color="red">审核未通过</a-tag>;
              case 3:
                return <a-tag color="#f50">已审核</a-tag>;
              default:
                return text;
            }
          },
        },
        {
          title: "附件",
          dataIndex: "fileId",
          customRender: (text, row, index) => {
            if (text != null && text != "") {
              return (
                <a href={row.fileUrl} target="_blank">
                  查看
                </a>
              );
            }
            return "";
          },
          width: 80,
        },
        {
          title: "审核意见",
          dataIndex: "auditSuggestion",
          scopedSlots: { customRender: "auditSuggestion" },
        },
        {
          title: "经审核是否构成职称晋升条件",
          dataIndex: "isUse",
          scopedSlots: { customRender: "isUse" },
          width: 80,
        },
        {
          title: "审核",
          key: "action",
          scopedSlots: { customRender: "action" },
          width: 100,
        },
      ];
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
