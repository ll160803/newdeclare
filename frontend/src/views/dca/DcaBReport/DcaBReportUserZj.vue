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
              <a-col :md="8" :sm="24">
                <a-form-item label="岗位等级" v-bind="formItemLayout">
                  <a-select
                    mode="multiple"
                    style="width: 100%"
                    @change="handleChangeSearch"
                  >
                    <a-select-option value="中级"> 中级 </a-select-option>
                    <a-select-option value="初级"> 初级 </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
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
          templateUrl="dcaBReport/downTemplateZj"
          @succ="handleRefesh"
          url="dcaBReport/importZj"
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
              <p style="width: 100%" v-for="item in splitStr(text)">
                {{ item }}
              </p>
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
   <template
              slot="note"
              slot-scope="text, record"
            >
              <div v-if="record.state==3 || record.state==1">
                {{text}}
              </div>
              <div v-else>
                <a-textarea
                  @blur="e => inputChange(e.target.value,record,'note')"
                  :value="record.note"
                >
                </a-textarea>
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
                 <a-select-option value="评聘"> 评聘 </a-select-option>
                  <a-select-option value="确定"> 确定 </a-select-option>
                   <a-select-option value="退审"> 退审 </a-select-option>
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
import DcaBReportUnsure from "./DcaBReportUnsureZj";
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
        ks: "中级,初级",
        yuangongzu: "在编,选留,合同制",
        activeKey: 1
      },
      sortedInfo: null,
      paginationInfo: null,
      visibleUserInfo: false,
      scroll: {
        x: 4200,
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
        { fieldName: "ylpfbfz2022" },
        {
          fieldName: "ylpfdj",
        },
        {
          fieldName: "ylpfdj2022",
        },
        {
          fieldName: "jxpf",
        },
        {
          fieldName: "jxpfdj",
        },
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
          fieldName: "teachName",
        },
        {
          fieldName: "teachDengji",
        },
        {
          fieldName: "teachRanknum",
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
          fieldName: "schoolprizeDengji",
        },
        {
          fieldName: "schoolprizeRanknum",
        },
        {
          fieldName: "schoolprizeDate",
        },
        {
          fieldName: "courseDengji",
        },
        {
          fieldName: "courseRanknum",
        },
        {
          fieldName: "courseDate",
        },
        {
          fieldName: "youngName",
        },
        {
          fieldName: "youngDengji",
        },
        {
          fieldName: "youngDate",
        },
        {
          fieldName: "youngRanknum",
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
          fieldName: "teacherQualify",
        },
        {
          fieldName: "borad",
        },
        {
          fieldName: "help",
        },
        {
          fieldName: "auditMan",
        },
        //  {
        //   fieldName: 'zzbysj',
        // },
        //  {
        //   fieldName: 'dzbysj',
        // },
        //  {
        //   fieldName: 'bkbysj',
        // },
        //  {
        //   fieldName: 'ssbysj',
        // },
        // {
        //   fieldName: 'bsbysj',
        // }
        // , {
        //   fieldName: 'rzqedu',
        // },
        {
          fieldName: "fdzz",
        },
        {
          fieldName: "zyjszwzg",
        },
        {
          fieldName: "zyjszwzgsj",
        },
        { fieldName: "ydyf" },
        { fieldName: "ydyffj" },
        { fieldName: "zzsc" },
        { fieldName: "zzscypfj" },
        { fieldName: "jlsc" },
        { fieldName: "xsddsc" },
        { fieldName: "xsddscypfj" },
        { fieldName: "yyxtsc" },
        { fieldName: "sfssds" },
        { fieldName: "sfbsds" },
        { fieldName: "sftgsdsf" },
        { fieldName: "sdsfypfj" },
        { fieldName: "sdsfypfj2" },
        { fieldName: "ynjbzr" },
        { fieldName: "j5njxgz" },
        { fieldName: "mzylpf" },
        { fieldName: "mzylpfdj" },
        { fieldName: "mzylsgypfj" },
        { fieldName: "sfypfjyl" },
        { fieldName: "hlylpf" },
        { fieldName: "hlylpfdj" },
        { fieldName: "hljxpfbfz" },
        { fieldName: "hljxpfdl" },
        { fieldName: "hlhlzrypfj" },
        { fieldName: "sshbdts" },
        { fieldName: "sshkyxts" },
        { fieldName: "blxwjf" },
        {
          fieldName: "wfzgszcf",
        },
        { fieldName: "zypfdjyjxl" },

        { fieldName: "zypfyjxl" },
        { fieldName: "zypfbfz58" },
        { fieldName: "zypfdj59" },
        { fieldName: "sfyszgzs" },
        { fieldName: "sfjyhlzgzs" },
        { fieldName: "xingfscsftg" },
        { fieldName: "sfczxfypfj61" },
        { fieldName: "zypf52" },
        { fieldName: "zypfdj52" },
        //beizhu
        { fieldName: "beizhumenban" },
        { fieldName: "beizhuyiwuchu" },
        { fieldName: "beizhuhuli" },
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
  components: { DcaBReportUnsure, AuditUserInfo, AuditResultInfo, ImportExcel },
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
    handleRefesh(){
      this.search();
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
        this.queryParams.ks = "中级,初级,合同制";
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
          title: "序号",
          dataIndex: "indexHao",
        },
        {
          title: "确认顺序号",
          dataIndex: "confirmIndex",
        },

        {
          title: "报名档案顺序号",
          dataIndex: "baomingIndex",
        },
        {
          title: "职员代码",
          dataIndex: "userAccount",
        },
        {
          title: "人员类别",
          dataIndex: "yuangongzu",
        },
        {
          title: "系列",
          dataIndex: "xl",
        },
        {
          title: "评审分组",
          dataIndex: "pingshenfenzu",
        },
        {
          title: "申报等级",
          dataIndex: "gwdj",
        },
        {
          title: "科室",
          dataIndex: "ks",
        },

        {
          title: "姓名",
          dataIndex: "userAccountName",
        },
        {
          title: "出生年月",
          dataIndex: "birthdaystr",
        },
       {
          title: '性别',
          dataIndex: 'sexName'
        },
        {
          title: "现职务名称",
          dataIndex: "positionName",
          width: 100,
        },
        {
          title: "聘任时间",
          dataIndex: "zygwDate",
          width: 100,
        },
        {
          title: "申报职称",
          dataIndex: "npPositionName",
          width: 100,
        },
        {
          title: "最高学历",
          dataIndex: "edu",
          width: 100,
        },
        {
          title: "最高学历毕业时间",
          dataIndex: "eduDate",
          width: 100,
        },

        {
          title: "来院时间",
          dataIndex: "schoolDate",
          width: 100,
        },
        {
          title: "中专毕业时间",
          dataIndex: "zzbysj",
          width: 100,
        },
        {
          title: "大专毕业时间",
          dataIndex: "dzbysj",
          width: 100,
        },
        {
          title: "本科毕业时间",
          dataIndex: "bkbysj",
          width: 100,
        },
        {
          title: "硕士毕业时间",
          dataIndex: "ssbysj",
          width: 100,
        },
        {
          title: "博士毕业时间",
          dataIndex: "bsbysj",
          width: 100,
        },
         {
          title: "等级7",
          dataIndex: "ylpfdj",
          width: 100,
        },
        {
          title: "分数8",
          dataIndex: "ylpfbfz",
        },
        {
          title: "门办等级90",
          dataIndex: "mzylpfdj",
        },
        {
          title: "门办分数91",
          dataIndex: "mzylpf",
        },

        {
          title: "等级9",
          dataIndex: "jxpfdj",
        },
        {
          title: "分数10",
          dataIndex: "jxpf",
        },
 {
          title: 'A 类',
          dataIndex: 'publishA',
          width: 100
        },
        {
          title: 'B 类',
          dataIndex: 'publishB',
          width: 100
        },
        {
          title: 'C 类',
          dataIndex: 'publishC',
          width: 100
        },
        {
          title: 'D 类',
          dataIndex: 'publishD',
          width: 100
        },
        {
          title: 'E 类',
          dataIndex: 'publishE',
          width: 100
        },
        {
          title: 'F 类',
          dataIndex: 'publishF',
          width: 100
        },

        {
          title: "著作或教材",
          dataIndex: "publicarticle1",
        },
        {
          title: "承担字数(万)",
          dataIndex: "publicarticle2",
        },

        {
          title: "名称1",
          dataIndex: "sciName",
        },
        {
          title: "等级2",
          dataIndex: "sciDengji",
        },
        {
          title: "排名3",
          dataIndex: "sciRanknum",
        },

        {
          title: "级别4",
          dataIndex: "sciDjlb",
        },
        {
          title: "金额万元5",
          dataIndex: "sciDjfund",
        },
        {
          title: "排名6",
          dataIndex: "sciDjranknum",
        },

       
        {
          title: "法定资质10",
          dataIndex: "fdzz",
        },
        {
          title: "业技术职务资格名称11",
          dataIndex: "zyjszwzg",
        },
        {
          title: "业技术职务资格名称时间12",
          dataIndex: "zyjszwzgsj",
        },
        {
          title: "岗前培训情况",
          dataIndex: "gqpxqk",
          width: 100,
        },
        {
          title: "规范化医师培训情况",
          dataIndex: "gfhyspxqk",
          width: 100,
        },
        {
          title: "中级水平能力测试情况",
          dataIndex: "zjspnlceqk",
          width: 100,
        },

        {
          title: "部门审核结果",
          dataIndex: "auditMan",
        },
        {
          title: "是否通过医德医风审核58",
          dataIndex: "ydyf",
          width: 100,
        },
        {
          title: "是否存在医德医风一票否决的情况59",
          dataIndex: "ydyffj",
          width: 120,
        },
        {
          title: "是否通过政治审查60",
          dataIndex: "zzsc",
          width: 100,
        },
        {
          title: "是否存在政治审查一票否决的情况61",
          dataIndex: "zzscypfj",
          width: 120,
        },

        {
          title: "是否通过纪律审查62",
          dataIndex: "jlsc",
          width: 100,
        },
        {
          title: "是否存在纪律审查一票否决的情况63",
          dataIndex: "jlscypfj",
          width: 120,
        },
        {
          title: "是否通过学术道德审查64",
          dataIndex: "xsddsc",
          width: 100,
        },
        {
          title: "是否存在学术道德一票否决的情况65",
          dataIndex: "xsddscypfj",
          width: 120,
        },
        {
          title: "专业评分（仅研究系列需要填写）(百分制)66",
          dataIndex: "zypfyjxl",
          width: 150,
        },
        {
          title: "专业评分等级（仅研究系列需要填写）67",
          dataIndex: "zypfdjyjxl",
          width: 150,
        },
        {
          title: "是否通过意识形态审查68",
          dataIndex: "yyxtsc",
          width: 100,
        },
        {
          title: "是否存在意识形态一票否决的情况69",
          dataIndex: "yyxtypfj",
          width: 120,
        },
        {
          title: "专业评分（仅宣传部职工需要填写）(百分制）70",
          dataIndex: "zypfbfz58",
          width: 150,
        },
        {
          title: "专业评分等级（仅宣传部职工需要填写）71",
          dataIndex: "zypfdj59",
          width: 150,
        },
        {
          title: "是否硕士导师72",
          dataIndex: "sfssds",
          width: 100,
        },
        {
          title: "是否博士导师73",
          dataIndex: "sfbsds",
          width: 120,
        },
        {
          title: "是否通过师德师风审查74",
          dataIndex: "sftgsdsf",
          width: 120,
        },
        {
          title: "是否存在师德师风一票否决的情况75",
          dataIndex: "sdsfypfj",
          width: 120,
        },
        {
          title: "教学评分(百分制)76",
          dataIndex: "jxpf",
          width: 100,
        },
        {
          title: "教学评分等级77",
          dataIndex: "jxpfdj",
          width: 120,
        },
        {
          title: "是否存在师德师风一票否决的情况78",
          dataIndex: "sdsfypfj2",
          width: 120,
        },
        {
          title: "是否担任一年辅导员或班主任并考核合格79",
          dataIndex: "ynjbzr",
          width: 120,
        },
        {
          title: "近五年教学工作在本单位总体评价情况80",
          dataIndex: "j5njxgz",
          width: 150,
        },

        {
          title: "门诊医疗评分81",
          dataIndex: "mzylpf",
          width: 100,
        },
        {
          title: "门诊医疗评分等级82",
          dataIndex: "mzylpfdj",
          width: 120,
        },
        {
          title: "是否门诊医疗事故一票否决的情况83",
          dataIndex: "mzylsgypfj",
          width: 150,
        },
        {
          title: "beizhu100",
          dataIndex: "beizhumenban",
          width: 150,
        },

        {
          title: "医疗评分84",
          dataIndex: "ylpfbfz2022",
          width: 100,
        },
        {
          title: "医疗评分等级85",
          dataIndex: "ylpfdj2022",
          width: 120,
        },
        {
          title: "是否有一票否决的情况86",
          dataIndex: "sfypfjyl",
          width: 120,
        },
        {
          title: "是否具有医师资格证书87",
          dataIndex: "sfyszgzs",
          width: 120,
        },
        {
          title: "备注200",
          dataIndex: "beizhuyiwuchu",
          width: 120,
        },
        {
          title: "医疗评分88",
          dataIndex: "hlylpf",
          width: 100,
        },
        {
          title: "医疗评分等级89",
          dataIndex: "hlylpfdj",
          width: 120,
        },
        {
          title: "教学评分90",
          dataIndex: "hljxpfbfz",
          width: 120,
        },
        {
          title: "教学评分等级91",
          dataIndex: "hljxpfdl",
          width: 120,
        },
        {
          title: "是否有护理责任事故一票否决的情况92",
          dataIndex: "hlhlzrypfj",
          width: 120,
        },
        {
          title: "是否具有护理资格证书93",
          dataIndex: "sfjyhlzgzs",
          width: 120,
        },
        {
          title: "hulinote",
          dataIndex: "beizhuhuli",
          width: 120,
        },
        {
          title: "是否存在收受红包的有效投诉94",
          dataIndex: "sshbdts",
          width: 100,
        },
        {
          title: "是否存在收受回扣的有效投诉95",
          dataIndex: "sshkyxts",
          width: 120,
        },
        {
          title: "是否违反湖北96",
          dataIndex: "blxwjf",
          width: 200,
        },
        {
          title: "是否违反97",
          dataIndex: "wfzgszcf",
          width: 150,
        },
        {
          title: "行风审查是否通过98",
          dataIndex: "xingfscsftg",
          width: 120,
        },
        {
          title: "是否存在行风建设一99",
          dataIndex: "sfczxfypfj61",
          width: 150,
        },

        {
          title: "专业评分100",
          dataIndex: "zypf52",
          width: 80,
        },
        {
          title: "专业评分等级101",
          dataIndex: "zypfdj52",
          width: 100,
        },
        {
          title: "联系方式",
          dataIndex: "telephone",
        },
        {
          title: "材料审核结果",
          dataIndex: "clshjg",
        },
        {
          title: "退审原因",
          dataIndex: "ntyy",
        },
        {
          title: "申报类型",
          dataIndex: "sblx",
        },
         {
          title: '备注',
          dataIndex: 'note',
          width: 100
        },
          {
          title: "科室排序（护理）",
          dataIndex: "kspaixu",
        },
         {
          title: "科室排序（其他）",
          dataIndex: "paixu5",
        },
      ];
      let listj = [
        "edu",
        "eduDate",
        "eduDateBack",
        "ylpfbfz",
        
        "ylpfdj",
        "jxpf",
        "jxpfdj",
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
        "patentNum",
        "patentFund",
        "schoolprizeDengji",
        "schoolprizeRanknum",
        "schoolprizeDate",
        "courseDengji",
        "courseRanknum",
        "courseDate",
        "youngName",
        "youngDengji",
        "youngDate",
        "youngRanknum",
        "sciDjlb",
        "sciDjfund",
        "sciDjranknum",
        "sciJflb",
        "sciJffund",
        "sciJfranknum",
        "pfHeji",
        "tutor",
        "teacherQualify",
        "borad",
        "help",
        "auditMan",
        // "zzbysj",
        // "dzbysj",
        // "bkbysj",
        // "ssbysj",
        // "bsbysj",
        "ydyf",
        "ydyffj",
        "zzsc",
        "zzscypfj",
        "jlsc",
        "jlscypfj",
        "xsddsc",
        "xsddscypfj",
        "zypfyjxl",
        "zypfdjyjxl",
        "yyxtsc",
        "yyxtypfj",
        "zypfbfz58",
        "zypfdj59",
        "sfssds",
        "sfbsds",
        "sftgsdsf",
        "sdsfypfj",
        "jxpf",
        "jxpfdj",
        "sdsfypfj2",
        "ynjbzr",
        "j5njxgz",
        "mzylpf",
        "mzylpfdj",
        "mzylsgypfj",
        "ylpfbfz2022",
        "ylpfdj",
        "ylpfdj2022",
        "sfypfjyl",
        "sfyszgzs",
        "hlylpf",
        "hlylpfdj",
        "hljxpfbfz",
        "hljxpfdl",
        "hlhlzrypfj",
        "sfjyhlzgzs",
        "sshbdts",
        "sshkyxts",
        "blxwjf",
        "wfzgszcf",
        "xingfscsftg",
        "sfczxfypfj61",
        "zypf52",
        "zypfdj52",
        "beizhuyiwuchu",
        "beizhumenban",
        "beizhuhuli",
        "fdzz",
        "zyjszwzg",
        "zyjszwzgsj"
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
        excelIndex: 1,
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
          title: "确认顺序号",
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
          title: "职员代码",
          dataIndex: "userAccount",
          width: 80,
          scopedSlots: { customRender: "userAccount" },
          //     fixed: 'left',
        },
        {
          title: "人员类别",
          dataIndex: "yuangongzu",
          width: 100,
        },
        {
          title: "系列",
          dataIndex: "xl",
          width: 80,
          //     fixed: 'left'
        },
        {
          title: "评审分组",
          dataIndex: "pingshenfenzu",
          width: 180,
          scopedSlots: { customRender: "pingshenfenzu" },
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
          title: '性别',
          dataIndex: 'sexName',
          width: 60
        },
        {
          title: "现职务",
          children: [
            {
              title: "现职务名称",
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
          title: "最高学历",
          dataIndex: "edu",
          width: 100,
        },
        {
          title: "最高学历毕业时间",
          dataIndex: "eduDate",
          width: 100,
        },
        {
          title: "申报职称",
          dataIndex: "npPositionName",
          width: 100,
        },
        {
          title: "来院时间",
          dataIndex: "schoolDate",
          width: 100,
        },
        {
          title: "中专毕业时间",
          dataIndex: "zzbysj",
          width: 100,
        },
        {
          title: "大专毕业时间",
          dataIndex: "dzbysj",
          width: 100,
        },
        {
          title: "本科毕业时间",
          dataIndex: "bkbysj",
          width: 100,
        },
        {
          title: "硕士毕业时间",
          dataIndex: "ssbysj",
          width: 100,
        },
        {
          title: "博士毕业时间",
          dataIndex: "bsbysj",
          width: 100,
        },
 {
          title: "医疗评价",
          children: [
            {
              title: "医务等级",
              dataIndex: "ylpfdj",
              width: 100,
            },
            {
              title: "医务分数",
              dataIndex: "ylpfbfz",
              width: 80,
              // customRender: (text, row, index) => {
              //   return row.ylpfbfz;
              // },
            },
            {
              title: "门办等级",
              dataIndex: "mzylpfdj",
              width: 100,
            },
            {
              title: "门办分数",
              dataIndex: "mzylpf2",
              width: 80,
              customRender: (text, row, index) => {
                return row.mzylpf;
              },
            },
          ],
        },
        {
          title: "教学评价",
          children: [
            {
              title: "等级",
              dataIndex: "jxpfdj",
              width: 100,
            },
            {
              title: "分数",
              dataIndex: "jxpf2",
              width: 80,
              customRender: (text, row, index) => {
                return row.jxpf;
              },
            },
          ],
        },
        {
          title: "论文",
          children: [
        {
          title: 'A 类',
          dataIndex: 'publishA',
          width: 100
        },
        {
          title: 'B 类',
          dataIndex: 'publishB',
          width: 100
        },
        {
          title: 'C 类',
          dataIndex: 'publishC',
          width: 100
        },
        {
          title: 'D 类',
          dataIndex: 'publishD',
          width: 100
        },
        {
          title: 'E 类',
          dataIndex: 'publishE',
          width: 100
        },
        {
          title: 'F 类',
          dataIndex: 'publishF',
          width: 100
        },
          ],
        },

        {
          title: "著作或教材",
          children: [
            {
              title: "著作或教材",
              dataIndex: "publicarticle1",
              width: 100,
            },
            {
              title: "承担字数(万)",
              dataIndex: "publicarticle2",
              width: 100,
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

        {
          title: "科研课题",
          children: [
            {
              title: "级别",
              dataIndex: "sciDjlb",
              width: 100,
              scopedSlots: { customRender: "splitHang" },
            },
            {
              title: "金额万元",
              dataIndex: "sciDjfund",
              width: 100,
              scopedSlots: { customRender: "splitHang" },
            },
            {
              title: "排名",
              dataIndex: "sciDjranknum",
              width: 100,
              scopedSlots: { customRender: "splitHang" },
            },
          ],
        },

       
        {
          title: "法定资质",
          dataIndex: "fdzz",
          width: 100,
        },
         {
          title: "取得湖北省相应专业技术职务资格及时间",
          children: [
            {
              title: "专业技术职务资格名称",
              dataIndex: "zyjszwzg",
              width: 100,
            },
            {
              title: "时间",
              dataIndex: "zyjszwzgsj",
              width: 80,
            },
          ],
        },
        {
          title: "岗前培训情况",
          dataIndex: "gqpxqk",
          width: 100,
        },
        {
          title: "规范化医师培训情况",
          dataIndex: "gfhyspxqk",
          width: 100,
        },
        {
          title: "中级水平能力测试情况",
          dataIndex: "zjspnlceqk",
          width: 100,
        },

        {
          title: "部门审核结果",
          dataIndex: "auditMan",
          width: 100,
          scopedSlots: { customRender: "auditMan" },
        },
        {
          title: "党办",
          children: [
            {
              title: "是否通过医德医风审核",
              dataIndex: "ydyf",
              width: 100,
              customRender: (text, row, index) => {
                switch (text) {
                  case "是":
                    return "通过";
                  case "否":
                    return "不通过";
                  default:
                    return "";
                }
              },
            },
            {
              title: "是否存在医德医风一票否决的情况",
              dataIndex: "ydyffj",
              width: 120,
            },
          ],
        },
        {
          title: "党委组织部",
          children: [
            {
              title: "是否通过政治综合评价",
              dataIndex: "zzsc",
              width: 100,
              customRender: (text, row, index) => {
                switch (text) {
                  case "是":
                    return "通过";
                  case "否":
                    return "不通过";
                  default:
                    return "";
                }
              },
            },
            {
              title: "是否存在政治综合评价一票否决的情况",
              dataIndex: "zzscypfj",
              width: 120,
            },
          ],
        },
        {
          title: "纪委办公室",
          children: [
            {
              title: "是否通过纪律审查",
              dataIndex: "jlsc",
              width: 100,
              customRender: (text, row, index) => {
                switch (text) {
                  case "是":
                    return "通过";
                  case "否":
                    return "不通过";
                  default:
                    return "";
                }
              },
            },
            {
              title: "是否存在纪律审查一票否决的情况",
              dataIndex: "jlscypfj",
              width: 120,
            },
          ],
        },
        {
          title: "科研处",
          children: [
            {
              title: "是否通过学术道德审查",
              dataIndex: "xsddsc",
              width: 100,
              customRender: (text, row, index) => {
                switch (text) {
                  case "是":
                    return "通过";
                  case "否":
                    return "不通过";
                  default:
                    return "";
                }
              },
            },
            {
              title: "是否存在学术道德一票否决的情况",
              dataIndex: "xsddscypfj",
              width: 120,
            },
            {
              title: "专业评分（仅研究系列需要填写）(百分制)",
              dataIndex: "zypfyjxl",
              width: 150,
            },
            {
              title: "专业评分等级（仅研究系列需要填写）",
              dataIndex: "zypfdjyjxl",
              width: 150,
            },
          ],
        },
        {
          title: "宣传部",
          children: [
            {
              title: "是否通过意识形态审查",
              dataIndex: "yyxtsc",
              width: 100,
              customRender: (text, row, index) => {
                switch (text) {
                  case "是":
                    return "通过";
                  case "否":
                    return "不通过";
                  default:
                    return "";
                }
              },
            },
            {
              title: "是否存在意识形态一票否决的情况",
              dataIndex: "yyxtypfj",
              width: 120,
            },
            {
              title: "专业评分（仅宣传部职工需要填写）(百分制）",
              dataIndex: "zypfbfz58",
              width: 150,
            },
            {
              title: "专业评分等级（仅宣传部职工需要填写）",
              dataIndex: "zypfdj59",
              width: 150,
            },
          ],
        },
        {
          title: "研究生管理办",
          children: [
            {
              title: "是否硕士导师",
              dataIndex: "sfssds",
              width: 100,
            },
            {
              title: "是否博士导师",
              dataIndex: "sfbsds",
              width: 120,
            },
            {
              title: "是否通过师德师风审查",
              dataIndex: "sftgsdsf",
              width: 120,
              customRender: (text, row, index) => {
                switch (text) {
                  case "是":
                    return "通过";
                  case "否":
                    return "不通过";
                  default:
                    return "";
                }
              },
            },
            {
              title: "是否存在师德师风一票否决的情况",
              dataIndex: "sdsfypfj",
              width: 120,
            },
          ],
        },
        {
          title: "教学办公室",
          children: [
            {
              title: "教学评分(百分制)",
              dataIndex: "jxpf",
              width: 100,
            },
            {
              title: "教学评分等级",
              dataIndex: "jxpfdj",
              width: 120,
            },
            {
              title: "是否存在师德师风一票否决的情况",
              dataIndex: "sdsfypfj2",
              width: 120,
            },
            {
              title: "是否担任一年辅导员或班主任并考核合格",
              dataIndex: "ynjbzr",
              width: 120,
            },
            {
              title: "近五年教学工作在本单位总体评价情况（前%）",
              dataIndex: "j5njxgz",
              width: 150,
            },
          ],
        },
        {
          title: "门诊办公室",
          children: [
            {
              title: "门诊医疗评分(百分制)",
              dataIndex: "mzylpf",
              width: 100,
            },
            {
              title: "门诊医疗评分等级",
              dataIndex: "mzylpfdj",
              width: 120,
            },
            {
              title: "是否门诊医疗事故一票否决的情况",
              dataIndex: "mzylsgypfj",
              width: 150,
            },
            {
              title: "备注",
              dataIndex: "beizhumenban",
              width: 150,
            },
          ],
        },
        {
          title: "医务处",
          children: [
            {
              title: "医疗评分(百分制)",
              dataIndex: "ylpfbfz2022",
              width: 100,
            },
            {
              title: "医疗评分等级",
              dataIndex: "ylpfdj2022",
              width: 120,
            },
            {
              title: "是否有一票否决的情况",
              dataIndex: "sfypfjyl",
              width: 120,
            },
            {
              title: "是否具有医师资格证书",
              dataIndex: "sfyszgzs",
              width: 120,
            },
            {
              title: "备注",
              dataIndex: "beizhuyiwuchu",
              width: 150,
            },
          ],
        },
        {
          title: "护理部",
          children: [
            {
              title: "医疗评分(百分制)",
              dataIndex: "hlylpf",
              width: 100,
            },
            {
              title: "医疗评分等级",
              dataIndex: "hlylpfdj",
              width: 120,
            },
            {
              title: "教学评分(百分制)",
              dataIndex: "hljxpfbfz",
              width: 120,
            },
            {
              title: "教学评分等级",
              dataIndex: "hljxpfdl",
              width: 120,
            },
            {
              title: "是否有护理责任事故一票否决的情况",
              dataIndex: "hlhlzrypfj",
              width: 120,
            },
            {
              title: "是否具有护理资格证书",
              dataIndex: "sfjyhlzgzs",
              width: 120,
            },
            {
              title: "备注",
              dataIndex: "beizhuhuli",
              width: 150,
            },
          ],
        },
        {
          title: "行风建设办公室",
          children: [
            {
              title: "是否存在收受红包的有效投诉",
              dataIndex: "sshbdts",
              width: 100,
            },
            {
              title: "是否存在收受回扣的有效投诉",
              dataIndex: "sshkyxts",
              width: 120,
            },
            {
              title:
                "是否违反湖北省医务人员不良执业行为记分管理办法，并被卫生健康部门一次记12分、6分",
              dataIndex: "blxwjf",
              width: 200,
            },
            {
              title: "是否违反《职工守则》有关行风建设规定并被处罚",
              dataIndex: "wfzgszcf",
              width: 150,
            },
            {
              title: "行风审查是否通过",
              dataIndex: "xingfscsftg",
              width: 120,
              customRender: (text, row, index) => {
                switch (text) {
                  case "是":
                    return "通过";
                  case "否":
                    return "不通过";
                  default:
                    return "";
                }
              },
            },
            {
              title: "是否存在行风建设一票否决的情况",
              dataIndex: "sfczxfypfj61",
              width: 150,
            },
          ],
        },
        {
          title: "科室负责人",
          children: [
            {
              title: "专业评分",
              dataIndex: "zypf52",
              width: 80,
            },
            {
              title: "专业评分等级",
              dataIndex: "zypfdj52",
              width: 100,
            },
          ],
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
          title: "联系方式",
          dataIndex: "telephone",
          width: 100,
        },
        {
          title: "申报类型",
          dataIndex: "sblx",
          width: 100,
          scopedSlots: { customRender: "sblx" },
        },
         {
          title: '备注',
          dataIndex: 'note',
          width: 100,
          scopedSlots: { customRender: "note" },
        },
         {
          title: "科室排序（护理）",
          dataIndex: "kspaixu",
          width: 100,
        },
        {
          title: "科室排序（其他）",
          dataIndex: "paixu5",
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
