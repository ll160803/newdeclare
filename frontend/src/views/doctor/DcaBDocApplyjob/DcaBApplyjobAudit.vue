<template>
  <div>
    <a-spin :spinning="loading">
      <a-card title="拟聘岗位">
        <div>
          <a-form layout="horizontal">
            <a-row>
              <div>
                <a-col
                  :md="8"
                  :sm="24"
                >
                  <a-form-item
                    label="发薪号"
                    v-bind="formItemLayout"
                  >
                    <a-input v-model="queryParams.userAccount" />
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
                slot="action"
                slot-scope="text, record"
              >
                <a-button
                  type="dashed"
                  block
                  @click="OpenAudit(record)"
                >
                  审核
                </a-button>
              </template>
            </a-table>
          </a-tab-pane>
          <a-tab-pane
            key="2"
            tab="已审核"
          >
            <dcaBApplyjob-done
              ref="TableInfo2"
              :state="3"
            >
            </dcaBApplyjob-done>
          </a-tab-pane>
          <a-tab-pane
            key="3"
            tab="审核未通过"
          >
            <dcaBApplyjob-done
              ref="TableInfo3"
              :state="2"
            >
            </dcaBApplyjob-done>
          </a-tab-pane>
        </a-tabs>
        <a-modal
          title="审核"
          :visible="modelVisiable"
          @cancel="cancelAudit"
        >
          <a-textarea
            :value="dcaBApplyjob.applyContent"
            @blur="e => inputChange(e.target.value,'applyContent')"
            :rows="16"
            placeholder="拟聘岗位"
          ></a-textarea>
          <a-textarea
            :value="dcaBApplyjob.auditSuggestion"
            @blur="e => inputChange(e.target.value,'auditSuggestion')"
            :rows="4"
            placeholder="审核意见"
          ></a-textarea>
          <a-checkbox
            @change="e => onIsUseChange(e,'isUse')"
            :checked="dcaBApplyjob.isUse"
          >经审核是否构成职称晋升条件</a-checkbox>
          <template slot="footer">
            <a-row>
              <a-col
                :span="8"
                offset="2"
              >
                <a-button
                  type="dashed"
                  block
                  @click="handleAudit()"
                >
                  审核通过
                </a-button>
              </a-col>
              <a-col
                :span="8"
                offset="2"
              >
                <a-button
                  type="danger"
                  block
                  @click="handleAuditNo()"
                >
                  审核不通过
                </a-button>
              </a-col>
            </a-row>
          </template>
        </a-modal>
      </a-card>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment';
import DcaBApplyjobDone from './DcaBApplyjobDone'

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
        userAccount: ''
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 800,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      modelVisiable: false,
      dcaBApplyjob: {
        isUse: false
      }
    }
  },
  components: { DcaBApplyjobDone },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    callback () {

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
    freshTabs () {
      this.$refs.TableInfo2.fetch(this.queryParams.userAccount)
      this.$refs.TableInfo3.fetch(this.queryParams.userAccount)
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
      this.selectedRowKeys = selectedRowKeys
    },
    inputChange (value, filedName) {
      this.dcaBApplyjob[filedName] = value
    },
    onIsUseChange (e, filedName) {
      this.dcaBApplyjob[filedName] = e.target.checked;
    },
    OpenAudit (record) {
      this.dcaBApplyjob = record
      this.modelVisiable = true
    },
    cancelAudit () {
      this.modelVisiable = false
    },
    handleAudit () {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将审核通过',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(that.dcaBApplyjob)
          that.loading = true
          that.$post('dcaBDocApplyjob/updateNew', {
            jsonStr: jsonStr,
            state: 3
          }).then(() => {
            //this.reset()
            that.modelVisiable = false
            that.$message.success('审核成功')
            that.fetch()
            that.freshTabs()
            
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    handleAuditNo () {
      let that = this
      this.$confirm({
        title: '确定审核不通过此记录?',
        content: '当您点击确定按钮后，此记录将审核不通过',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(that.dcaBApplyjob)
          that.loading = true
          that.$post('dcaBDocApplyjob/updateNew', {
            jsonStr: jsonStr,
            state: 2
          }).then(() => {
            //this.reset()
            that.modelVisiable = false
            that.$message.success('操作成功')
            that.fetch()
            that.freshTabs()
           // that.modelVisiable = false
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
      this.$get('dcaBDocApplyjob/audit', {
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
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
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
          title: '审核',
          key: 'action',
          scopedSlots: { customRender: 'action' },
          width: 100
        }]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
