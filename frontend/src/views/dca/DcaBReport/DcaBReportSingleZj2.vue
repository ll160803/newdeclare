<template>
  <a-card
    class="card-area"
    title=""
  >
    <a-spin :spinning="loading">
      <div>
        <a-form layout="horizontal">
          <a-row>
            <div>
              <a-col
                :md="8"
                :sm="24"
              >
                <a-form-item
                  label="申报年度"
                  v-bind="formItemLayout"
                >
                  <a-input v-model="queryParams.year" />
                </a-form-item>
              </a-col>
            </div>
            <span style="float: right; margin-top: 3px;">
              <a-button
                type="primary"
                @click="search"
              >查询</a-button>
              <a-button
                style="margin-left: 8px"
                @click="reset"
              >重置</a-button>
            </span>
          </a-row>
        </a-form>
      </div>
      <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="record => record.id"
        :pagination="pagination"
        @change="handleTableChange"
        bordered
        :scroll="scroll"
      >
        <template
          slot="splitHang"
          slot-scope="text, record"
        >
          <div
            style="width:100%;"
            v-for="item in splitStr(text)"
          >{{item}}</div>
        </template>
        <template
          slot="action"
          slot-scope="text, record"
        >
          <div v-if="record.state==2">
            
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="ExportDeclareReport(record)"
            >
              导出职称申报表
            </a-button>
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="ExportAttachReport(record)"
            >
              导出附件材料
            </a-button>
             <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="ExportDocReport(record)"
            >
              导出职称确认表
            </a-button>
          </div>
          <div v-else>
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="handleSave(record)"
            >
              确认
            </a-button>
          </div>
        </template>
        <template
          slot="auditMan"
          slot-scope="text, record"
        >
          <div v-if="text=='正常'">
            <a-tag
              color="green"
              @click="showUserInfoRight(record)"
            >正常</a-tag>
          </div>
          <div v-else>
            <a-tag
              color="red"
              @click="showUserInfoRight(record)"
            >异常</a-tag>
          </div>
        </template>
      </a-table>
    </a-spin>
    <audit-resultInfo
      ref="userinfo"
      @close="onCloseUserInfoRight"
      :visibleUserInfo="visibleUserInfo_right"
      :userAccount="userAccount_right"
      :dcaYear="dcaYear"
      :gwdj="gwDj"
    ></audit-resultInfo>
  </a-card>
</template>

<script>
import AuditResultInfo from '../../common/AuditResultInfo'
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
import moment from 'moment';
import { mapState, mapMutations } from 'vuex'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      queryParams: {
        //userAccount: ''
        ks: '二三级'
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 4600,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: '',
      formItemLayout,
      state2: 1,
      userAccount_right: '',
      visibleUserInfo_right: false,
      dcaYear: '',
      gwDj: ''
    }
  },
  components: { AuditResultInfo },
  props: {

  },
  mounted () {
    this.fetch2()
  },
  methods: {
    moment,
    splitStr (text) {
      if(text==null) return ''
      return text.split('#')
    },
     ExportDocReport (record) {
      this.$download('dcaBCopyUser/doc', {
        userAccount: record.userAccount,
        year: record.year,
        npPositionName: record.npPositionName,
        gwdj: record.gwdj//岗位等级
      }, record.year + '_' + record.userAccount + '_职称确认表' + ".docx")
    },
    search () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch2({
        sortField: "user_account",
        sortOrder: "ascend",
        ...this.queryParams
      })
      //this.freshTabs()
    },
    showUserInfoRight (record) {
      //debugger
      this.visibleUserInfo_right = true
      this.userAccount_right = record.userAccount
      console.info(record.year)
      this.dcaYear = record.state==2?record.year :''
      this.gwDj = record.gwdj
    },
    onCloseUserInfoRight () {
      this.visibleUserInfo_right = false
    },
    fetch2 (params = {}) {
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.pagination.defaultCurrent
      }
      params.sortField = "user_account,year"
      params.sortOrder = "ascend"
      let username = this.currentUser.username
      console.info(username)
      this.$get('dcaBReport', {
        userAccount: username,
        isSingel: '1',
        ks: '二三级',
        ...params
      }).then((r) => {
        this.loading = false
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    },
    reset () {

    },
    handleSave (record) {
      // record.state= 2
      // let jsonStr = JSON.stringify(record)
      let vRecord = {}
      vRecord.id = record.id
      vRecord.state = 2
      vRecord.userAccount = record.userAccount
      vRecord.year= record.year
      vRecord.gwdj= record.gwdj

      let that = this
      that.$confirm({
        title: '是否确认此记录?',
        content: '当您点击确定按钮后，所有数据无法进行更改，请仔细核对进行确认',
        centered: true,
        onOk () {
          that.loading = true
          that.$put('dcaBReport', {
            ...vRecord
          }).then(() => {
            // this.reset()
            that.$message.success('保存成功')
            that.search()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    onCloseUserInfo () {
      this.visibleUserInfo = false
    },
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },
    ExportAttachReport (record) {
      this.$download('dcaBCopyUser/attach', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        gwdj: record.gwdj,//岗位等级
        npPositionName: record.npPositionName,
      },record.year+record.userAccount+".pdf")
    },
    ExportDeclareReport (record) {
      this.$download('dcaBCopyUser/excel', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        gwdj: record.gwdj,//岗位等级
        npPositionName: record.npPositionName,
        sexName: record.gwdj //岗位等级
      },record.userAccount+".pdf")
    },
    fetch () {
      this.loading = true
      //this.queryParams.userAccount = userAccount
      let params = {}
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = 1
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = 1
      }
      params.sortField = "user_account,year"
      params.sortOrder = "ascend"
      // params.userAccount = userAccount
      let username = this.currentUser.username

      this.$get('dcaBReport', {
        userAccount: username,
        state: 1,
        ks: '二三级',
        ...params
      }).then((r) => {
        this.loading = false
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch2({
        sortField: "user_account",
        sortOrder: "ascend",
        ...this.queryParams
      })
    },
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      let clm = [
        {
          title: '申报年度',
          dataIndex: 'year',
          width: 100
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
        },
        {
          title: "近三年手术台次",
          dataIndex: "j3nssztc",
          width: 100,
        },

        {
          title: "近三年收治住院病人数",
          dataIndex: "j3nzyszbrsl",
          width: 100,
        },
        {
          title: "近三年门诊收治病人数",
          dataIndex: "j3nmzszbrsl",
          width: 100,
        },
        {
          title: "近三年新技术新业务",
          children: [
            {
              title: "负责开展的新技术新业务",
              dataIndex: "xjsxyw",
              width: 100,
            },
            {
              title: "负责的新技术新业务获奖情况",
              dataIndex: "xjsxywprize",
              width: 100,
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
          width: 200,
          scopedSlots: { customRender: "splitHang" },
        },
         {
          title: "重要抗疫奖励",
          dataIndex: "prize",
          width: 100,
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
          scopedSlots: { customRender: "note" },
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

          width: 150,
        },
      ];
      return clm
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>