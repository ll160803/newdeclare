<template>
  <a-card class="card-area" title="">
    <a-spin :spinning="loading">
      <div>
        <a-form layout="horizontal">
          <a-row>
            <div>
              <a-col :md="8" :sm="24">
                <a-form-item label="发薪号/姓名" v-bind="formItemLayout">
                  <a-input v-model="queryParams.userAccount" />
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="申报年度" v-bind="formItemLayout">
                  <a-input v-model="queryParams.dcaYear" />
                </a-form-item>
              </a-col>
              <!-- <a-col :md="8" :sm="24">
                <a-form-item label="岗位等级" v-bind="formItemLayout">
                  <a-select
                    mode="multiple"
                    style="width: 100%"
                    @change="handleChangeSearch"
                  >
                    <a-select-option value="二级"> 二级 </a-select-option>
                    <a-select-option value="三级"> 三级 </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col> -->
            </div>
            <span style="float: right; margin-top: 3px">
              <a-button
                v-hasNoPermission="['dca:audit']"
                v-if="activeKey == 1"
                type="primary"
                @click="bulkSave"
                >保存</a-button
              >
              <a-button
                v-hasNoPermission="['dca:audit']"
                v-if="activeKey == 1"
                type="primary"
                @click="showModal"
                >推送用户确认</a-button
              >
               <span style="float:left;">
                <import-excel
          templateUrl="dcaBReport/downTemplate23"
          @succ="handleRefesh"
          url="dcaBReport/import23"
          :queryParams="queryParams"
        >
        </import-excel> </span>
              <a-button type="primary" @click="exportExcel">导出</a-button>
              <a-button type="primary" @click="search">查询</a-button>
              <a-button style="margin-left: 8px" @click="reset">重置</a-button>
            </span>
          </a-row>
        </a-form>
      </div>
      <a-tabs ref="tabCard" type="card" @change="callback">
        <a-tab-pane key="1" tab="待处理">
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
            <template slot="splitHang" slot-scope="text, record">
              <div style="width: 100%" v-for="item in splitStr(text)">
                {{ item }}
              </div>
            </template>
            <template slot="confirmIndex" slot-scope="text, record">
              <div v-if="record.state == 3">
                {{ text }}
              </div>
              <div v-else>
                <a-input-number
                  @blur="
                    (e) => inputChange(e.target.value, record, 'confirmIndex')
                  "
                  :value="record.confirmIndex"
                  :precision="0"
                >
                </a-input-number>
              </div>
            </template>
            <template slot="danganIndex" slot-scope="text, record">
              <div v-if="record.state == 3">
                {{ text }}
              </div>
              <div v-else>
                <a-input-number
                  @blur="
                    (e) => inputChange(e.target.value, record, 'danganIndex')
                  "
                  :value="record.danganIndex"
                  :precision="0"
                >
                </a-input-number>
              </div>
            </template>
            <template slot="baomingIndex" slot-scope="text, record">
              <div v-if="record.state == 3">
                {{ text }}
              </div>
              <div v-else>
                <a-input-number
                  @blur="
                    (e) => inputChange(e.target.value, record, 'baomingIndex')
                  "
                  :value="record.baomingIndex"
                  :precision="0"
                >
                </a-input-number>
              </div>
            </template>

            <template slot="pingshenfenzu" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-select
                  :value="
                    record.pingshenfenzu == null ? '' : record.pingshenfenzu
                  "
                  style="width: 100%"
                  @change="
                    (e, f) => handleSelectChange(e, f, record, 'pingshenfenzu')
                  "
                >
                  <a-select-option value="手术组高级">
                    手术组高级
                  </a-select-option>
                  <a-select-option value="非手术组高级">
                    非手术组高级
                  </a-select-option>
                  <a-select-option value="药护技及其他组高级">
                    药护技及其他组高级
                  </a-select-option>
                  <a-select-option value="医师组中初级">
                    医师组中初级
                  </a-select-option>
                  <a-select-option value="护理组中初级">
                    护理组中初级
                  </a-select-option>
                  <a-select-option value="药技及其他组中初级">
                    药技及其他组中初级
                  </a-select-option>
                </a-select>
              </div>
            </template>
            <template slot="ifshuangbao" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="
                    (e) => inputChange(e.target.value, record, 'ifshuangbao')
                  "
                  :value="record.ifshuangbao"
                >
                </a-textarea>
              </div>
            </template>
            <template slot="ifsyyltj" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="(e) => inputChange(e.target.value, record, 'ifsyyltj')"
                  :value="record.ifsyyltj"
                >
                </a-textarea>
              </div>
            </template>
            <template slot="ifbxyltj" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="(e) => inputChange(e.target.value, record, 'ifbxyltj')"
                  :value="record.ifbxyltj"
                >
                </a-textarea>
              </div>
            </template>
            <template slot="mzsstjqk" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="(e) => inputChange(e.target.value, record, 'mzsstjqk')"
                  :value="record.mzsstjqk"
                >
                </a-textarea>
              </div>
            </template>
            <template slot="kslb" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="(e) => inputChange(e.target.value, record, 'kslb')"
                  :value="record.kslb"
                >
                </a-textarea>
              </div>
            </template>
            <template slot="note" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="(e) => inputChange(e.target.value, record, 'note')"
                  :value="record.note"
                >
                </a-textarea>
              </div>
            </template>
            <template slot="ifdaitou" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-switch
                  checked-children="是"
                  un-checked-children="否"
                  @change="
                    (e1, f) => inputCheckChange(e1, f, record, 'ifdaitou')
                  "
                  :checked="record.ifdaitou == '是'"
                >
                </a-switch>
              </div>
            </template>

            <template slot="iffuhekeyan" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-switch
                  checked-children="是"
                  un-checked-children="否"
                  @change="
                    (e1, f) => inputCheckChange(e1, f, record, 'iffuhekeyan')
                  "
                  :checked="record.iffuhekeyan == '是'"
                >
                </a-switch>
              </div>
            </template>
            <template slot="iffuhediyi" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-switch
                  checked-children="是"
                  un-checked-children="否"
                  @change="
                    (e1, f) => inputCheckChange(e1, f, record, 'iffuhediyi')
                  "
                  :checked="record.iffuhediyi == '是'"
                >
                </a-switch>
              </div>
            </template>
            <template slot="iffuhebibei" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="
                    (e) => inputChange(e.target.value, record, 'iffuhebibei')
                  "
                  :value="record.iffuhebibei"
                >
                </a-textarea>
              </div>
            </template>

            <template slot="publishDup" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-input-number
                  @blur="
                    (e) => inputChange(e.target.value, record, 'publishDup')
                  "
                  :value="record.publishDup"
                  :precision="2"
                >
                </a-input-number>
              </div>
            </template>
            <template slot="publishEup" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-input-number
                  @blur="
                    (e) => inputChange(e.target.value, record, 'publishEup')
                  "
                  :value="record.publishEup"
                  :precision="2"
                >
                </a-input-number>
              </div>
            </template>
            <template slot="publishFup" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-input-number
                  @blur="
                    (e) => inputChange(e.target.value, record, 'publishFup')
                  "
                  :value="record.publishFup"
                  :precision="2"
                >
                </a-input-number>
              </div>
            </template>

            <template slot="sblx" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-select
                  :value="record.sblx == null ? '' : record.sblx"
                  style="width: 100%"
                  @change="(e, f) => handleSelectChange(e, f, record, 'sblx')"
                >
                  <a-select-option value="顺升"> 顺升 </a-select-option>
                  <a-select-option value="单靠"> 单靠 </a-select-option>
                  <a-select-option value="援疆"> 援疆 </a-select-option>
                  <a-select-option value="援藏"> 援藏 </a-select-option>
                </a-select>
              </div>
            </template>
            <template slot="choosepos" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="
                    (e) => inputChange(e.target.value, record, 'choosepos')
                  "
                  :value="record.choosepos"
                >
                </a-textarea>
              </div>
            </template>
            <template slot="clshjg" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-select
                  :value="setValueClshjg(record)"
                  style="width: 100%"
                  @change="(e, f) => handleSelectChange(e, f, record, 'clshjg')"
                >
                  <a-select-option value="正常"> 正常 </a-select-option>
                  <a-select-option value="退审"> 退审 </a-select-option>
                  <a-select-option value="审核中"> 审核中 </a-select-option>
                </a-select>
              </div>
            </template>
            <template slot="ntyy" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="(e) => inputChange(e.target.value, record, 'ntyy')"
                  :value="setNtyyValue(record)"
                >
                </a-textarea>
              </div>
            </template>
             <template slot="mzsstjqkAudit" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="(e) => inputChange(e.target.value, record, 'mzsstjqkAudit')"
                  :value="record.mzsstjqkAudit"
                >
                </a-textarea>
              </div>
            </template>
            <template slot="ksrank" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="(e) => inputChange(e.target.value, record, 'ksrank')"
                  :value="record.ksrank"
                >
                </a-textarea>
              </div>
            </template>

            <template slot="npqk" slot-scope="text, record">
              <div v-if="record.state == 3 || record.state == 1">
                {{ text }}
              </div>
              <div v-else>
                <a-textarea
                  @blur="(e) => inputChange(e.target.value, record, 'npqk')"
                  :value="record.npqk"
                >
                </a-textarea>
              </div>
            </template>
            <template slot="action" slot-scope="text, record">
              <a-button
                v-hasNoPermission="['dca:audit']"
                style="width: 100%; padding-left: 2px; padding-right: 2px"
                type="dashed"
                block
                @click="handleSave(record)"
              >
                保存
              </a-button>
              <a-button
                v-hasNoPermission="['dca:audit']"
                style="width: 100%; padding-left: 2px; padding-right: 2px"
                type="dashed"
                block
                @click="handleAudit(record)"
              >
                推送用户确认
              </a-button>
            </template>
            <template slot="auditMan" slot-scope="text, record">
              <div v-if="text == '异常'">
                <a-tag
                  color="red"
                  @click="showUserInfoRight(record.userAccount)"
                  >异常</a-tag
                >
              </div>
              <div v-else>
                <a-tag
                  color="green"
                  @click="showUserInfoRight(record.userAccount)"
                  >正常</a-tag
                >
              </div>
            </template>
            <template slot="userAccount" slot-scope="text, record">
              <a @click="showUserInfo(text)">{{ text }}</a>
            </template>
          </a-table>
        </a-tab-pane>
        <a-tab-pane key="2" tab="待确认" :forceRender="true">
          <dcaBReport-unsure ref="TableInfo2" :state="1" :activeKey="activeKey">
          </dcaBReport-unsure>
        </a-tab-pane>
        <a-tab-pane
          key="3"
          tab="已确认"
          :forceRender="true"
          :activeKey="activeKey"
        >
          <dcaBReport-unsure ref="TableInfo3" :state="2"> </dcaBReport-unsure>
        </a-tab-pane>
      </a-tabs>
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
      ></audit-resultInfo>
      <a-modal
        v-model="modalVisible"
        title="推送用户确认"
        ok-text="确认"
        cancel-text="取消"
        @ok="mutiSend"
      >
        <span>请输入发送消息：</span>
        <a-textarea @blur="(e) => userChange(e.target.value)" :value="sendInfo">
        </a-textarea>
      </a-modal>
    </a-spin>
  </a-card>
</template>

<script>
import moment from "moment";
import DcaBReportUnsure from "./DcaBReportUnsureZj2";
import AuditUserInfo from "../../common/AuditUserInfo";
import AuditResultInfo from "../../common/AuditResultInfo";
import ImportExcel from "../../common/ImportExcelQuery";

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
        //onChange: (current, pageSize) => this.pageChange(current, pageSize),
        //onShowSizeChange: (current, pageSize) => this.pageSizeChange(current, pageSize),
        showTotal: (total, range) =>
          `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`,
      },
      queryParams: {
        userAccount: "",
        dcaYear: "",
        ks: "二三级",
        activeKey: 1
      },
      sortedInfo: null,
      paginationInfo: null,
      visibleUserInfo: false,
      scroll: {
        x: 4000,
        y: window.innerHeight - 200 - 100 - 20 - 80,
      },
      listAuditInfo: [
        {
          fieldName: "edu",
        },
        {
          fieldName: "eduDate",
        },
        {
          fieldName: "eduDateBack",
        },
        {
          fieldName: "ylpfbfz",
        },
        {
          fieldName: "ylpfdj",
        },
        {
          fieldName: "jxpf",
        },
        // {
        //   fieldName: "jxpfdj",
        // },
        {
          fieldName: "sciName",
        },
        {
          fieldName: "sciDengji",
        },
        {
          fieldName: "sciRanknum",
        },

        {
          fieldName: "publishA",
        },
        {
          fieldName: "publishB",
        },
        {
          fieldName: "publishA",
        },
        {
          fieldName: "publishC",
        },
        {
          fieldName: "publishD",
        },
        {
          fieldName: "publishE",
        },
        {
          fieldName: "publishF",
        },
        {
          fieldName: "publishDup",
        },
        {
          fieldName: "publishEup",
        },
        {
          fieldName: "publishFup",
        },
        {
          fieldName: "publicarticle1",
        },
        {
          fieldName: "publicarticle2",
        },
        {
          fieldName: "schoolprizeName",
        },
        {
          fieldName: "patentNum",
        },
        {
          fieldName: "patentFund",
        },
        {
          fieldName: "sciDjlb",
        },
        {
          fieldName: "sciDjfund",
        },
        {
          fieldName: "sciDjranknum",
        },
        {
          fieldName: "sciJflb",
        },
        {
          fieldName: "sciJffund",
        },
        {
          fieldName: "sciJfranknum",
        },
        {
          fieldName: "pfHeji",
        },
        {
          fieldName: "tutor",
        },
       
         {
          fieldName: 'xhrzqk'
        },
         {
          fieldName: 'prize'
        },
         {
          fieldName: 'mzsstjqk'
        },
        //  {
        //   fieldName: 'xjsxyw'
        // },
        //  {
        //   fieldName: 'xjsxywprize'
        // },
        {
          fieldName: "rbdsj",
        },
        // {
        //   fieldName: "j3nhxrlzypf",
        // },
        // {
        //   fieldName: "j3ylzhpf",
        // },
        // {
        //   fieldName: "j3nssztc",
        // },
        // {
        //   fieldName: "j3nzyszbrsl",
        // },
        // {
        //   fieldName: "j3nmzszbrsl",
        // },
        {
          fieldName: "dpsci10",
        },
         {
          fieldName: "dpsci20",
        },
      ], // 当前用户包含的审核数据
      userAccount: "",
      visibleUserInfo: false,
      userAccount_right: "",
      visibleUserInfo_right: false,
      pSize: 0,
      activeKey: 1,
      sendInfo: "",
      modalVisible: false,
    };
  },
  components: { DcaBReportUnsure, AuditUserInfo, AuditResultInfo, ImportExcel},
  mounted() {
    // this.fetchUseraudit()
    this.search();
  },
  methods: {
    moment,
    callback(activeKey) {
      this.activeKey = activeKey;
    },
    showModal() {
      this.modalVisible = true;
    },
    bulkSave() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要保存的记录");
        return;
      }
      let flag = 1;
      let dataSourceAll = this.dataSource;
      let dataSource = dataSourceAll.filter((p) =>
        this.selectedRowKeys.includes(p.id)
      );
      var arrData = [];
      dataSource.forEach((element) => {
        delete element.dcaBAuditdynamicList; //移出属性
        delete element.createTime; //移出属性
        delete element.modifyTime; //移出属性
        arrData.push(element);
      });
      if (flag == 1) {
        let that = this;
        that.$confirm({
          title: "确定保存通过此记录?",
          content: "当您点击确定按钮后，此记录将保存",
          centered: true,
          onOk() {
            that.loading = true;
            that
              .$post("dcaBReport/saveData", {
                data: JSON.stringify(arrData),
                state: 0,
              })
              .then(() => {
                that.$message.success("提交成功");
                that.loading = false;
                that.selectedRowKeys = [];
                that.search();
              });
          },
          onCancel() {},
        });
      }
    },
    mutiSend() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择需要推送的记录");
        return;
      }
      let flag = 1;
      let dataSourceAll = this.dataSource;
      let dataSource = dataSourceAll.filter((p) =>
        this.selectedRowKeys.includes(p.id)
      );
      var arrData = [];
      dataSource.forEach((element) => {
        delete element.dcaBAuditdynamicList; //移出属性
        delete element.createTime; //移出属性
        delete element.modifyTime; //移出属性
        arrData.push(element);
      });
      if (flag == 1) {
        let that = this;
        that.$confirm({
          title: "确定审核通过此记录?",
          content: "当您点击确定按钮后，此记录将提交申请人确认",
          centered: true,
          onOk() {
            that.loading = true;
            that
              .$post("dcaBReport/saveData", {
                data: JSON.stringify(arrData),
                state: 1,
              })
              .then(() => {
                that.$message.success("提交成功");
                that.modalVisible = false;
                that.loading = false;
                that.selectedRowKeys = [];
                that.search();
                if (that.sendInfo != "") {
                  that.sendInfoMulti(dataSource);
                }
              });
          },
          onCancel() {
            that.modalVisible = false;
          },
        });
      }
    },
    sendInfoMulti(dataSource) {
      dataSource.forEach((element) => {
        this.$post("user/mess?timestamp=" + new Date().getTime(), {
          tel: element.telephone,
          message: this.sendInfo,
        }).then((r) => {
          this.$message.success("用户:" + element.userAccount + "发送成功");
        });
      });
    },
    handleChangeSearch(value) {
      if (value != "") {
        this.queryParams.ks = value;
      } else {
        this.queryParams.ks = "二三级";
      }
    },
    search() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      //console.info(this.$refs.tabCard.activeKey);
      if (this.activeKey == "1") {
        this.fetch({
          sortField: "user_account",
          sortOrder: "ascend",
          ...this.queryParams,
        });
      }
      this.freshTabs();
    },
    userChange(value) {
      this.sendInfo = value;
    },
    splitStr(text) {
      if(text==null) return ''
      return text.split("#");
    },
    showUserInfo(text) {
      //debugger
      this.visibleUserInfo = true;
      this.userAccount = text;
    },
    onCloseUserInfo() {
      this.visibleUserInfo = false;
    },
    showUserInfoRight(text) {
      //debugger
      this.visibleUserInfo_right = true;
      this.userAccount_right = text;
    },
    onCloseUserInfoRight() {
      this.visibleUserInfo_right = false;
    },
    freshTabs() {
      this.$refs.TableInfo2.queryParams.userAccount =
        this.queryParams.userAccount;
      this.$refs.TableInfo2.queryParams.year = this.queryParams.dcaYear;

      this.$refs.TableInfo3.queryParams.userAccount =
        this.queryParams.userAccount;
      this.$refs.TableInfo3.queryParams.year = this.queryParams.dcaYear;

      this.$refs.TableInfo2.queryParams.ks = this.queryParams.ks;
      this.$refs.TableInfo3.queryParams.ks = this.queryParams.ks;

        if (this.$refs.TableInfo2.paginationInfo) {
        this.$refs.TableInfo2.paginationInfo.current = 1;
      }
      if (this.$refs.TableInfo3.paginationInfo) {
        this.$refs.TableInfo3.paginationInfo.current = 1;
      }

      this.$refs.TableInfo2.queryParams.yuangongzu =
        this.queryParams.yuangongzu;
      this.$refs.TableInfo3.queryParams.yuangongzu =
        this.queryParams.yuangongzu;

      if (this.activeKey == "2") {
        this.$refs.TableInfo2.search();
      }
      if (this.activeKey == "3") {
        this.$refs.TableInfo3.search();
      }
      //this.$refs.TableInfo3.fetch(this.queryParams.userAccount)
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
      this.search();
    },
    handleTableChange(pagination, filters, sorter) {
      //debugger
      this.sortedInfo = sorter;
      this.pSize =
        pagination.pageSize == null
          ? pagination.defaultPageSize
          : pagination.pageSize;
      this.paginationInfo = pagination;
      this.fetch({
         sortField: "user_account",
          sortOrder: "ascend",
        ...this.queryParams,
      });
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys;
    },
    handleSelectChange(value, option, record, filedName) {
      console.info(value);
      record[filedName] = value;
    },
    handleChange(date, dateStr, record, filedName) {
      const value = dateStr;
      record[filedName] = value;
    },
    checkedValue(record, fieldName) {
      // console.info(fieldName)
      return record[fieldName] == "是";
    },
    inputCheckChange(blFlag, f, record, filedName) {
      //console.info(blFlag)
      record[filedName] = blFlag ? "是" : "否";
    },
    inputChange(value, record, filedName) {
      console.info(value);
      record[filedName] = value;
    },
    onIsUseChange(e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    handleSave(record) {
      record.state = 0;
      // let jsonStr = JSON.stringify(record)
      let vRecord = record;
      delete vRecord.dcaBAuditdynamicList; //移出属性
      delete vRecord.createTime; //移出属性
      delete vRecord.modifyTime; //移出属性
      // vRecord.dcaBAuditdynamicList=''
      this.loading = true;
      this.$post("dcaBReport", {
        ...vRecord,
      })
        .then((r) => {
          // this.reset()
          console.info(r.data);
          this.$message.success("保存成功");
          record.id = r.data;
          //1·this.search()
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    handleAudit(record) {
      let that = this;
      let vRecord = record;
      delete vRecord.dcaBAuditdynamicList; //移出属性
      delete vRecord.createTime;
      delete vRecord.modifyTime;
      vRecord.state = 1;
      this.$confirm({
        title: "确定审核通过此记录?",
        content: "当您点击确定按钮后，此记录将提交申请人确认",
        centered: true,
        onOk() {
          let jsonStr = JSON.stringify(vRecord);
          that.loading = true;
          that
            .$post("dcaBReport", {
              ...record,
            })
            .then(() => {
              //this.reset()
              that.$message.success("提交成功");
              that.search();
              // that.freshTabs()
              that.loading = false;
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
      });
    },
    setDefaultValue(element2) {
      return "否";
    },
    setValueClshjg(record) {
      let str = "";
      if (record.applyState == 2) {
        str = "退审";
         if(record.clshjg==''){
          record.clshjg= "退审"
          return str;
        }
      } else {
        str = record.clshjg == null ? "" : record.clshjg;
      }
      return str;
    },
    setNtyyValue(record) {
      if (record.applyState == 2) {
        if(record.ntyy==''){
          record.ntyy= "中途退回"
          return "中途退回";
        }
       
      }
      return record.ntyy;
    },
    exportExcel() {
      this.$refs.TableInfo2.queryParams.userAccount =
        this.queryParams.userAccount;
      this.$refs.TableInfo2.queryParams.year = this.queryParams.dcaYear;

      this.$refs.TableInfo3.queryParams.userAccount =
        this.queryParams.userAccount;
      this.$refs.TableInfo3.queryParams.year = this.queryParams.dcaYear;

      this.$refs.TableInfo2.queryParams.ks = this.queryParams.ks;
      this.$refs.TableInfo3.queryParams.ks = this.queryParams.ks;

      this.$refs.TableInfo2.queryParams.yuangongzu =
        this.queryParams.yuangongzu;
      this.$refs.TableInfo3.queryParams.yuangongzu =
        this.queryParams.yuangongzu;

      if (this.activeKey == "1") {
        this.exportCustomExcel();
      }
      if (this.activeKey == "2") {
        this.$refs.TableInfo2.exportCustomExcel();
      }
      if (this.activeKey == "3") {
        this.$refs.TableInfo3.exportCustomExcel();
      }
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
          width: 100,
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
          width: 80,
        },
        {
          title: "是否必须使用医疗条件22",
          dataIndex: "ifbxyltj",
          width: 80,
        },
        {
          title: "满足学术条件情况23",
          dataIndex: "mzsstjqk",
          width: 80,
        },
         {
          title: "满足学术条件情况24",
          dataIndex: "mzsstjqkAudit",
          width: 80,
        },

        {
          title: "等级24",
          dataIndex: "jxpfdj",
          width: 100,
        },
       
        {
          title: "近三年核心人力资源评分26",
          dataIndex: "j3nhxrlzypf",
          width: 80,
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
          title: "单篇SCI高分文章≥2033",
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
      let listj = [
        "edu",
        "eduDate",
        "eduDateBack",
        "ylpfbfz",
        "ylpfdj",
        "jxpf",
      //  "jxpfdj",
        "sciName",
        "sciDengji",
        "sciRanknum",
        "teachName",
        "teachDengji",
        "teachRanknum",
        "publishA",
        "publishB",
        "publishA",
        "publishC",
        "publishD",
        "publishE",
        "publishF",
        "publishDup",
        "publishEup",
        "publishFup",
        "publicarticle1",
        "publicarticle2",
        "schoolprizeName",
       
        "sciDjlb",
        "sciDjfund",
        "sciDjranknum",
        "sciJflb",
        "sciJffund",
        "sciJfranknum",
        "pfHeji",
        "tutor",
        "teacherQualify",

           'xhrzqk',
        'prize',
         'mzsstjqk',
       
        //   'xjsxyw',
      
        //  'xjsxywprize',
      
          "rbdsj",
        
        "rbdsj",
        // "j3nhxrlzypf",
        // "j3ylzhpf",
        // "j3nssztc",
        // "j3nzyszbrsl",
        // "j3nmzszbrsl",
        "dpsci10",
        "dpsci20",
      ];

      json.forEach((element) => {
        if (listj.includes(element.dataIndex)) {
          element["isDynamic"] = 1;
        }
      });
      let dataJson = JSON.stringify(json);

      this.$export("dcaUserAudit/excelBigTable", {
         sortField: "user_account",
          sortOrder: "ascend",
        dataJson: dataJson,
        excelIndex: 2,
        ...this.queryParams,
      });
    },
    fetchUseraudit() {
      this.listAuditInfo.forEach((element) => {
        this.columns.push({
          title: element.fieldTitle,
          dataIndex: element.fieldName,
          width: 130,
          scopedSlots: { customRender: element.fieldName },
        });
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
      params.sortField= "user_account";
          params.sortOrder= "ascend";

      this.loading = true;
      let that = this;
      that
        .$get("dcaUserAudit/userAuditResultUser", {
          ...params,
        })
        .then((r) => {
          let data = r.data;
          that.loading = false;
          const pagination = { ...that.pagination };
          // let psize=pagination.pageSize==null?pagination.defaultPageSize:pagination.pageSize
          pagination.total = data.total;
          // pagination.pageSize =data.rows.length>pagination.pageSize?data.rows.length :pagination.pageSize

          data.rows.forEach((element) => {
            let auditList = element.dcaBAuditdynamicList;
            // console.info(auditList)
            if (auditList.length > 0) {
              // console.info(this.listAuditInfo)
              that.listAuditInfo.forEach((element2) => {
                //  console.info('element2' + element2)
                let lire = auditList.filter(
                  (p) => p.auditTitletype == element2.fieldName
                );
                // console.info(lire);
                if (lire.length > 0) {
                  element[element2.fieldName] = lire[0].auditResult;
                } else {
                  element[element2.fieldName] = "";
                }
              });
            } else {
              that.listAuditInfo.forEach((element2) => {
                element[element2.fieldName] = "";
              });
            }
          });
          that.dataSource = data.rows;
          //console.info(data.rows)

          //debugger
          that.pagination = pagination;
          // that.pagination.current = pagination.current
          //  that.pagination.total =pagination.total
        });
    },
  },
  computed: {
    columns() {
      let cls = [
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
          width: 180
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
          scopedSlots: { customRender: "mzsstjqkAudit" },
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
          scopedSlots: { customRender: "splitHang" },
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

          width: 100,
        },
      ];
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
        cls = cls.filter((p) => !filtersCls.includes(p.dataIndex));
      }
      return cls;
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
<style>
</style>
