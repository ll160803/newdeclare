<template>
  <div>
    <a-spin :spinning="loading">
      <a-card title="教师资格证编号及获得时间">
        <div>
          <a-form layout="horizontal">
            <a-row>
              <div>
                <a-col
                  :md="6"
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
                  :md="6"
                  :sm="24"
                >
                  <a-form-item
                    label="序号"
                    v-bind="formItemLayout"
                  >
                    <a-input-number style="width:40%!important;" v-model="queryParams.auditXuhaoS"></a-input-number>至<a-input-number style="width:40%!important;" v-model="queryParams.auditXuhaoE" ></a-input-number>
                  </a-form-item>
                </a-col>
                 <a-col
                  :md="6"
                  :sm="24"
                >
                  <a-form-item
                    label="初审状态"
                    v-bind="formItemLayout"
                  >
                    <a-select @change="handleChangeState">
                      <a-select-option
                        key="-1"
                        value="-1"
                      >全部</a-select-option>
                      <a-select-option
                        key="0"
                        value="0"
                      >审核一待审核</a-select-option>
                      <a-select-option
                        key="1"
                        value="1"
                      >审核二待审核</a-select-option>
                      <a-select-option
                        key="2"
                        value="2"
                      >审核三待审核</a-select-option>
                      <a-select-option
                        key="3"
                        value="3"
                      >审核四待审核</a-select-option>

                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col
                  :md="6"
                  :sm="24"
                  
                >
                  <a-form-item
                    label="申报年度"
                    v-bind="formItemLayout"
                    v-show="!dcaType==''"
                  >
                    <a-input v-model="queryParams.auditMan"  />
                  </a-form-item>
                </a-col>
              </div>
              <span style="float: right; margin-top: 3px;">
                 <a-button
                  type="primary"
                  @click="exportCustomExcel"
                >导出</a-button>
                <a-button
                  type="primary"
                  @click="search2"
                >查询</a-button>
                <a-button
                  style="margin-left: 8px"
                  @click="reset"
                >重置</a-button>
              </span>
            </a-row>
          </a-form>
        </div>
        <a-tabs
          type="card"
          @change="callback"
        >
          <a-tab-pane
            key="1"
            tab="待审核"
          >
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
                slot="tqCode"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'tqCode')"
                    :value="record.tqCode"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="tqReceiveDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'tqReceiveDate')"
                  />
                </div>
              </template>
              <template
                slot="isUse"
                slot-scope="text, record"
              >
                <a-checkbox
                  @change="e => onIsUseChange(e,record,'isUse')"
                  :checked="text"
                ></a-checkbox>
              </template>
              <template
                slot="auditSuggestion"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'auditSuggestion')"
                    :value="record.auditSuggestion"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="isBest"
                slot-scope="text, record"
              >
                <div key="jzContent">

                  <a-switch
                    checked-children="是"
                    un-checked-children="否"
                    @change="(e1,f) => inputCheckChange(e1,f,record,'isBest')"
                    :checked="record.isBest=='是'"
                  >
                  </a-switch>
                </div>
              </template>
              <template
                slot="userAccount"
                slot-scope="text, record"
              >
                <a
                  href="#"
                  @click="showUserInfo(text)"
                >{{text}}</a>
              </template>
              <template
                slot="action"
                slot-scope="text, record"
              >
               <a-button
                v-hasNoPermission="['dca:audit']"
                  style="width:40%;padding-left:2px;padding-right:2px;"
                  type="dashed"
                  block
                  @click="handleSave(record)"
                >
                  保存
                </a-button>
                <a-button
                v-hasNoPermission="['dca:audit']"
                  style="width:50%;padding-left:2px;padding-right:2px;"
                  type="dashed"
                  block
                  @click="handleAuditNext(record)"
                >
                  下一轮
                </a-button>
                <a-button
                v-hasNoPermission="['dca:audit']"
                  style="width:40%;padding-left:2px;padding-right:2px;"
                  type="dashed"
                  block
                  @click="handleAudit(record)"
                >
                  通过
                </a-button>
                <a-button
                v-hasNoPermission="['dca:audit']"
                 style="width:50%;padding-left:2px;padding-right:2px;"
                  type="danger"
                  block
                  @click="handleAuditNo(record)"
                >
                  审核不通过
                </a-button>
              </template>
            </a-table>
          </a-tab-pane>
          <a-tab-pane
            key="2"
            tab="已审核"
            :forceRender="true"
          >
            <dcaBTeacherqualify-done
              ref="TableInfo2"
              :state="3"
            >
            </dcaBTeacherqualify-done>
          </a-tab-pane>
          <a-tab-pane
            key="3"
            tab="审核未通过"
            :forceRender="true"
          >
            <dcaBTeacherqualify-done
              ref="TableInfo3"
              :state="2"
            >
            </dcaBTeacherqualify-done>
          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-spin>
    <audit-userInfo
      ref="userinfo"
      @close="onCloseUserInfo"
      :visibleUserInfo="visibleUserInfo"
      :userAccount="userAccount"
        :dcaYear="queryParams.auditMan"
        :gwdj="queryParams.auditManName"
    ></audit-userInfo>
  </div>
</template>

<script>
import moment from 'moment';
import DcaBTeacherqualifyDone from './DcaBTeacherqualifyDone'
import AuditUserInfo from '../../common/AuditUserInfo'

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
        auditManName: this.dcaType,
        auditXuhaoE: null,
        auditXuhaoS: null,
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1300,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: '',
       activeKey: 1,
    }
  },
  components: { DcaBTeacherqualifyDone, AuditUserInfo },
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
   callback (activeKey) {
      this.activeKey = activeKey
    },
    search2 () {
     if (this.paginationInfo) {
       this.paginationInfo.current = this.pagination.defaultCurrent
     }
     this.search()
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
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams
      })
      this.freshTabs()
    },
     exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
       let json = [...this.columns]
      json.splice(this.columns.length-1,1) //移出第一个
      console.info(json)
      let dataJson = JSON.stringify(json)

      let queryParams= this.queryParams
      
      let state = 1
      if(this.activeKey==1){
         state = 1
      }
       if(this.activeKey==2){
         state = 3
         delete queryParams.auditState
      }
       if(this.activeKey==3){
         state = 2
         delete queryParams.auditState
      }
      this.$export('dcaBTeacherqualify/excel', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        state: state,
        dataJson: dataJson,
        ...queryParams
      })
    },
    freshTabs () {
       let queryParams= {...this.queryParams}
       delete queryParams.auditState
      this.$refs.TableInfo2.queryParams = queryParams
      
      this.$refs.TableInfo3.queryParams = queryParams

       if (this.$refs.TableInfo2.paginationInfo) {
       this.$refs.TableInfo2.paginationInfo.current = 1
     }
      if (this.$refs.TableInfo3.paginationInfo) {
       this.$refs.TableInfo3.paginationInfo.current = 1
     }
     
      this.$refs.TableInfo2.fetch2(queryParams)
      this.$refs.TableInfo3.fetch2(queryParams)
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
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      // console.log(selectedRows)
      if (selectedRows[0].state != 3) {
        this.selectedRowKeys = selectedRowKeys
      }
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    inputCheckChange (blFlag, f, record, filedName) {
      record[filedName] = blFlag ? '是' : '否'
    },
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
     handleChangeState (state) {
      this.queryParams.auditState = state
    },
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },


    onCloseUserInfo () {
      this.visibleUserInfo = false
    },
     handleSave (record) {
      let that = this
      this.$confirm({
        title: '确定保存此记录?',
        content: '当您点击确定按钮后，此记录将保存',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBTeacherqualify/updateNew', {
            jsonStr: jsonStr,
            state: 1,
            auditState: -1
          }).then(() => {
            //this.reset()
            that.$message.success('保存成功')
            //that.search()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    handleAuditNext (record) {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将进入下一个审核人',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBTeacherqualify/updateNew', {
            jsonStr: jsonStr,
            state: 1,
            auditState: record.auditState
          }).then(() => {
            //this.reset()
            that.$message.success('审核成功')
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
    handleAudit (record) {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将审核通过',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBTeacherqualify/updateNew', {
            jsonStr: jsonStr,
            state: 3,
            auditState: record.auditState
          }).then(() => {
            //this.reset()
            that.$message.success('审核成功')
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
    handleAuditNo (record) {
      let that = this
      this.$confirm({
        title: '确定审核不通过此记录?',
        content: '当您点击确定按钮后，此记录将审核不通过',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBTeacherqualify/updateNew', {
            jsonStr: jsonStr,
            state: 2,
            auditState: 0
          }).then(() => {
            //this.reset()
            that.$message.success('操作成功')
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
      params.sortField = "userAccount"
      params.sortOrder = "descend"
      this.loading = true
      this.$get('dcaBTeacherqualify/audit', {
        ...params,
        state: 1
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    }
  },
  computed: {
    columns () {
      return [
          {
          title: '序号',
          dataIndex: 'auditXuhao',
          width: 60,
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
          title: '资格编号',
          dataIndex: 'tqCode',
          width: 130,
          scopedSlots: { customRender: 'tqCode' }
        },
        {
          title: '获得时间',
          dataIndex: 'tqReceiveDate',
          width: 130,
          scopedSlots: { customRender: 'tqReceiveDate' }
        },
        {
          title: '状态',
          dataIndex: 'state',
          width: 80,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">未提交</a-tag>
              case 1:
                return <a-tag color="green">已提交</a-tag>
              case 2:
                return <a-tag color="red">审核未通过</a-tag>
              case 3:
                return <a-tag color="#f50">已审核</a-tag>
              default:
                return text
            }
          }
        },
        {
          title: '初审状态',
          dataIndex: 'auditState',
          width: 120,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">审核一待审核</a-tag>
              case 1:
                return <a-tag color="green">审核二待审核</a-tag>
              case 2:
                return <a-tag color="red">审核三待审核</a-tag>
              case 3:
                return <a-tag color="#f50">审核四待审核</a-tag>
              case 4:
                return <a-tag color="#f50">审核五待审核</a-tag>
              case 5:
                return <a-tag color="#f50">审核六待审核</a-tag>
              default:
                return text
            }
          }
        },
        {
          title: '审核意见',
          dataIndex: 'auditSuggestion',
          scopedSlots: { customRender: 'auditSuggestion' },
          width: 120
        },
        {
          title: '经审核是否构成职称晋升条件',
          dataIndex: 'isUse',
          scopedSlots: { customRender: 'isUse' },
          width: 80
        }, {
          title: '附件',
          dataIndex: 'fileId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 80
        }, {
          title: '审核',
          key: 'action',
          scopedSlots: { customRender: 'action' },
          width: 180
        }]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
