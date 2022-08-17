<template>
  <div>
    <a-spin :spinning="loading">
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
          slot="operation"
          slot-scope="text, record"
        >
          <a-icon
            type="eye"
            theme="twoTone"
            twoToneColor="#42b983"
            @click="showUserInfo(record)"
            title="查看"
          ></a-icon>
        </template>
      </a-table>
     <user-info
        ref="user"
        :infoVisiable="infoVisiable"
        :userAccount="userAccount"
        :year="year"
        :picUrl="picUrl"
        @close="onCloseUserInfo"
        :isShow="1"
      >
      </user-info>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment';
import UserInfo from './ZqUserInfo'
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
        userAccount: '',
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1500,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      infoVisiable: false,
      userAccount: '',
       picUrl: '',
      year: '',
      user: [],
    }
  },
  components: { UserInfo },
  props: {
    state: {
      default: 3
    }
  },
  mounted () {
    //this.fetch2()
  },
  methods: {
    moment,
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
     // params.sortField = "a.user_account"
     // params.sortOrder = "descend"
      this.$get('zqDScore/user', {
        state: 3,
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
   
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch2({
        ...this.queryParams
      })
    },
  },
  computed: {
    columns () {
      return [
         {
          title: "考核年度",
          dataIndex: "year",
          width: 100,
        },
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
          width: 100,
        },
         {
          title: '评分等级',
          dataIndex: 'fenzu',
          width: 100,
        },
        {
          title: '该职工是否能按期完成考核目标',
          dataIndex: 'ifDone',
          width: 100,
        },
        {
          title: '整改建议',
          dataIndex: 'suggestion',
          width: 150,
        },
        {
        title: '查看',
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