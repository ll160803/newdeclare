<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null : 'fold'">
            <a-col :md="8" :sm="24">
              <a-form-item label="论文名" v-bind="formItemLayout">
                <a-input v-model="queryParams.paperName" />
              </a-form-item>
            </a-col>
             <a-col :md="8" :sm="24">
              <a-form-item label="期刊名" v-bind="formItemLayout">
                <a-input v-model="queryParams.journalName" />
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
            <a @click="toggleAdvanced" style="margin-left: 8px">
              {{ advanced ? "收起" : "展开" }}
              <a-icon :type="advanced ? 'up' : 'down'" />
            </a>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
        <a-button
          type="primary"
          ghost
          @click="add"
          >新增</a-button
        >
        <a-button
         type="primary"
          ghost
          @click="handleDelete"
          >删除</a-button
        >
        <a-button
         type="primary"
          ghost
          @click="handleAll"
          >全部一起提交</a-button
        >
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="(record) => record.id"
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        :rowSelection="{
          selectedRowKeys: selectedRowKeys,
          onChange: onSelectChange,
        }"
        @change="handleTableChange"
        :bordered="bordered"
         :scroll="scroll"
      >
        <template slot="remark" slot-scope="text, record">
          <a-popover placement="topLeft">
            <template slot="content">
              <div style="max-width: 200px">{{ text }}</div>
            </template>
            <p style="width: 200px; margin-bottom: 0">{{ text }}</p>
          </a-popover>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            :title="`${record.state==0?'修改':'查看'}`"
          ></a-icon>
        </template>
      </a-table>
    </div>
    <!-- 新增字典 -->
    <dcaBSciencepublish-add
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </dcaBSciencepublish-add>
    <!-- 修改字典 -->
    <dcaBSciencepublish-edit
      ref="dcaBSciencepublishEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </dcaBSciencepublish-edit>
  </a-card>
</template>

<script>
import DcaBSciencepublishAdd from "./DcaBSciencepublishAdd";
import DcaBSciencepublishEdit from "./DcaBSciencepublishEdit";
import moment from 'moment';

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  name: "DcaBSciencepublish",
  components: { DcaBSciencepublishAdd, DcaBSciencepublishEdit },
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      formItemLayout,
      pagination: {
        pageSizeOptions: ["10", "20", "30", "40", "100"],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) =>
          `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`,
      },
      queryParams: {},
      addVisiable: false,
      editVisiable: false,
      loading: false,
      bordered: true,
      scroll: { 
        x: 3400,
        y: window.innerHeight - 200 - 100  - 80,
       }
    };
  },
  computed: {
    columns() {
      let { sortedInfo } = this;
      sortedInfo = sortedInfo || {};
      return [
       {
        title: '论文名',
        dataIndex: 'paperName',
        width: 400,
        scopedSlots: { customRender: 'paperName' },
        fixed: "left",
      },
      {
        title: '期刊名',
        dataIndex: 'journalName',
        width: 200,
        scopedSlots: { customRender: 'journalName' },
        fixed: "left",
      },
      {
        title: '期刊号',
        dataIndex: 'journalCode',
        width: 120,
        scopedSlots: { customRender: 'journalCode' }
      },
      {
          title: '发表年月',
          dataIndex: 'paperPublishdate',
          width: 130,
          customRender: (text, row, index) => {
            if(text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
      {
        title: '收录情况',
        dataIndex: 'paperShoulu',
        width: 120,
        scopedSlots: { customRender: 'paperShoulu' }
      },
      {
        title: '影响因子',
        dataIndex: 'paperCause',
        width: 120,
        scopedSlots: { customRender: 'paperCause' }
      },
      {
        title: '是否一流期刊',
        dataIndex: 'isBest',
        width: 120,
        scopedSlots: { customRender: 'isBest' }
      },
      {
        title: '他引次数',
        dataIndex: 'otherTimes',
        width: 120,
        scopedSlots: { customRender: 'otherTimes' }
      },
      {
        title: '期刊级别',
        dataIndex: 'qkjb',
        width: 100,
        scopedSlots: { customRender: 'qkjb' }
      },
      {
        title: '第一或通讯作者',
        dataIndex: 'authorRank',
        width: 200,
        scopedSlots: { customRender: 'authorRank' }
      },
      {
        title: '第几作者',
        dataIndex: 'djzz',
        width: 80,
        scopedSlots: { customRender: 'djzz' }
      },
      {
        title: '期刊类型',
        dataIndex: 'wzlx',
        width: 80,
        scopedSlots: { customRender: 'wzlx' }
      },
       {
        title: '是否SCI',
        dataIndex: 'sciValue',
        width: 80,
        scopedSlots: { customRender: 'sciValue' }
      },
       {
        title: 'rank值(百分制)',
        dataIndex: 'rankValue',
        width: 80,
        scopedSlots: { customRender: 'rankValue' }
      }, 
      {
        title: '状态',
        dataIndex: 'state',
        width: 100,
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
        dataIndex: 'auditSuggestion'
      },
     {
          title: '经审核是否构成职称晋升条件',
          dataIndex: 'isUse',
          width: 100,
          customRender: (text, row, index) => {
            if (text) return  <a-tag color="green">是</a-tag>
             return <a-tag color="red">否</a-tag>
          }
        },  {
          title: '是否能用于教学职称申报(审核)',
          dataIndex: 'isJxzcsb',
         
          width: 120,
        }, {
          title: '是否能用于临床职称申报(审核)',
          dataIndex: 'isLczcsb',
          width: 120
        }, {
          title: '期刊级别(审核)',
          dataIndex: 'auditQkjb',
          width: 80,
         
        }, {
          title: '教学职称文章认定数量(审核)',
          dataIndex: 'jxzcsl',
          width: 80,
         
        }, {
          title: '临床职称文章认定数量(审核)',
          dataIndex: 'lczcsl',
          width: 80,
         
        }, {
          title: '第一作者或通讯作者共几人(审核)',
          dataIndex: 'auditTotalnum',
          width: 100,
         
        },
        {
          title: '非第一作者或通讯作者(审核)',
          dataIndex: 'auditIsfirst',
          width: 100,
          
          customRender: (text, row, index) => {
            if (text) return "是"
            return "否"
          }
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
        },
        {
          title: "操作",
          dataIndex: "operation",
          scopedSlots: { customRender: "operation" },
          fixed: "right",
          width: 100,
        },
      ];
    },
  },
  mounted() {
    this.fetch();
  },
  methods: {
    moment,
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    toggleAdvanced() {
      this.advanced = !this.advanced;
      if (!this.advanced) {
        this.queryParams.comments = "";
      }
    },
    handleAddSuccess() {
      this.addVisiable = false;
      this.$message.success("新增成功");
      this.search();
    },
    handleAddClose() {
      this.addVisiable = false;
    },
    add() {
      this.addVisiable = true;
    },
    handleEditSuccess() {
      this.editVisiable = false;
      this.$message.success("修改成功");
      this.search();
    },
    handleEditClose() {
      this.editVisiable = false;
    },
    edit(record) {
      this.$refs.dcaBSciencepublishEdit.setFormValues(record);
      this.editVisiable = true;
    },
     handleDelete () {
      if (!this.selectedRowKeys.length || this.selectedRowKeys.length > 1) {
        this.$message.warning('请选择一条需要删除的记录')
        return
      }
     let rows = this.dataSource.filter(p => this.selectedRowKeys.indexOf(p.id) >= 0)
       if (rows[0].state != 3 && rows[0].state != 1) {
          
        
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let dcaBPatentIds = that.selectedRowKeys.join(',')
          const dataSource = [...that.dataSource];
         // let new_dataSource = dataSource.filter(p => that.selectedRowKeys.indexOf(p.id) < 0)
          that.$put('dcaBSciencepublish', {
            id: that.selectedRowKeys[0],
            isDeletemark: 0
          }).then(() => {

          }).catch(() => {

          })
       //   that.dataSource = new_dataSource
          that.search()
          that.$message.success('删除成功')
          that.selectedRowKeys = []
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
       }
       else{
        this.$message.warning('已提交或已审核数据不能删除')
        return
       }
    },
    handleAll(){
      let that = this
      this.$confirm({
        title: '确定提交全部记录?',
        content: '当您点击确定按钮后，信息将全部提交',
        centered: true,
        onOk () {
            that.loading = true
            that.$post('dcaBSciencepublish/updateState', {
            }).then(() => {
              //this.reset()
              that.$message.success('提交成功')
             
              that.loading = false
              that.search()
            }).catch(() => {
              that.loading = false
            })
          
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })

    },
    exportExcel() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.$export("dcaBSciencepublish/excel", {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
      });
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
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
      });
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
    handleTableChange(pagination, filters, sorter) {
      this.sortedInfo = sorter;
      this.paginationInfo = pagination;
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
      });
    },
    fetch(params = {}) {
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
      this.$get("dcaBSciencepublish/custom", {
        ...params,
      }).then((r) => {
        let data = r.data;
       
        const pagination = { ...this.pagination };
        pagination.total = data.total;
        this.loading = false;
        this.dataSource = data.rows;
        this.pagination = pagination;
      });
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
