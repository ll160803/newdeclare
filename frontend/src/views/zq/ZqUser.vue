<template>

  <a-card
    class="card-area"
    title="基本资料"
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
                  label="发薪号/姓名"
                  v-bind="formItemLayout"
                >
                  <a-input v-model="queryParams.userAccount" />
                </a-form-item>
              </a-col>
            <a-col
                :md="8"
                :sm="24"
              >
                <a-form-item
                  label="考核年度"
                  v-bind="formItemLayout"
                >
                  <a-input v-model="queryParams.year" />
                </a-form-item>
              </a-col>
              
            </div>
            <span style="float: right; margin-top: 3px;">
              <!-- <a-button
                type="primary"
                @click="exportCustomExcel"
              >导出</a-button> -->
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
<a-tabs ref="tabCard" type="card" @change="callback">
        <a-tab-pane key="1" tab="未打分">
      <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="record => record.id"
        :pagination="pagination"
        @change="handleTableChange"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        :bordered="true"
        :scroll="scroll"
      >
       
<template
          slot="operation"
          slot-scope="text, record"
        >
          <a-icon
            type="setting"
            theme="twoTone"
            v-if="record.score==null||record.score==0"
            twoToneColor="#42b983"
            @click="showUserInfo(record)"
            title="打分"
          ></a-icon>
        </template>
      </a-table>
       </a-tab-pane>
        <a-tab-pane key="2" tab="已打分" :forceRender="true">
          <user-done ref="TableInfo2">
          </user-done>
        </a-tab-pane>
</a-tabs>
      <user-info
        ref="user"
        :infoVisiable="infoVisiable"
        :userAccount="userAccount"
        :year="year"
        :picUrl="picUrl"
        @close="onCloseUserInfo"
         @success="onSuccessUserInfo"
      >
      </user-info>
    </a-spin>
  </a-card>

</template>

<script>
import moment from 'moment';
import UserInfo from './ZqUserInfo'
import UserDone from './UserDone.vue'


const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      advanced: false,
      dataSource: [],
      formItemLayout,
      selectedRowKeys: [],
      loading: false,
      dcaBParttimeVisiable: false,
      idNums: 10000,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      queryParams: {
        userAccount: '',
        auditMan: this.dcaYear,
        auditManName: this.dcaType
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      userAccount: '',
      infoVisiable: false,
      picUrl: '',
      year: '',
      user: [],
      activeKey: 1
    }
  },
  components: { UserInfo,UserDone },
  mounted () {
    this.search()
  },
  props: {
    dcaYear: {
      default: '' //年度
    },
    dcaType: {
      default: '' //中高级
    }
  },
  methods: {
    moment,
        callback(activeKey) {
      this.activeKey = activeKey;
    },
    showUserInfo (record) {
      //debugger
      this.infoVisiable = true
      this.userAccount = record.userAccount
      console.info(record)
      this.picUrl= record.pictureUrl
      this.year= record.year
     // this.user=[]
     // this.user.push(record)
     let that= this
     setTimeout(()=>{
        that.$refs.user.setKh(record)
     },200)

    },
    onCloseUserInfo () {
      this.infoVisiable = false
    },
     onSuccessUserInfo () {
      this.infoVisiable = false
      this.search()
    },
    freshTabs () {
      this.$refs.TableInfo2.fetch2(this.queryParams)
    },
    search () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        ...this.queryParams
      })
       this.freshTabs()
    },

    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列排序规则
      this.sortedInfo = null
      this.paginationInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.search()
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
       
        ...this.queryParams
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {

    },
    handleSelectChange (value, option, record, filedName) {
      record[filedName] = value
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    checkedValue (record, fieldName) {
      // console.info(fieldName)
      return record[fieldName] == '是'
    },
    inputCheckChange (blFlag, f, record, filedName) {
      //console.info(blFlag)
      record[filedName] = blFlag ? '是' : '否'
    },
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      let json = this.columns
      // json.splice(this.columns.length-1,1) //移出第一个
      // console.info(json)
      let dataJson = JSON.stringify(json)

      let queryParams = this.queryParams


      this.$export('dcaBUser/excel2', {
       
        dataJson: dataJson,
        ...queryParams
      })
    },
    fetch (params = {}) {
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

      this.loading = true
      this.$get('zqDScore/user', {
          state: 0,
        ...params
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total

        this.dataSource = data.rows
        console.info(data.rows)
        this.pagination = pagination
      }
      )
    }
  },
  computed: {
    columns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' }
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '性别',
          dataIndex: 'sexName',
          width: 60
        },
      
         {
          title: '所在科室',
          dataIndex: 'deptName',
          width: 100
        },
       
        {
          title: '政治面貌',
          dataIndex: 'politicalStatus',
          width: 80
        },
        {
          title: '出生日期',
          dataIndex: 'birthday',
          width: 100,
          customRender: (text, row, index) => {
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '专业技术职务',
          dataIndex: 'positionName',
          width: 130
        },
        {
          title: '专业技术职务聘任时间',
          dataIndex: 'zygwDate',
          width: 130,
        }, {
        title: '打分',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' },
        width: 100
      }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
