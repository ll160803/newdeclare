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
        bordered
        :scroll="scroll"
      >
        <template slot="userAccount" slot-scope="text, record">
          <a href="#" @click="showUserInfo(text)">{{ text }}</a>
        </template>
      </a-table>
      <audit-userInfo
        ref="userinfo"
        @close="onCloseUserInfo"
        :visibleUserInfo="visibleUserInfo"
        :userAccount="userAccount"
        :dcaYear="queryParams.auditMan"
        :gwdj="queryParams.auditManName"
      ></audit-userInfo>
    </a-spin>
  </div>
</template>

<script>
import moment from "moment";
import AuditUserInfo from "../../common/AuditUserInfo";
export default {
  data() {
    return {
      dateFormat: "YYYY-MM-DD",
      dataSource: [],
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
        auditMan: "",
        auditManName: "",
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 20 - 80,
      },
      visibleUserInfo: false,
      userAccount: "",
    };
  },
  components: { AuditUserInfo },
  props: {
    state: {
      default: 3,
    },
  },
  mounted() {
    //this.fetch2()
  },
  methods: {
    moment,
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
      params.sortField = "userAccount";
      params.sortOrder = "descend";
      this.$get("dcaBLetter/audit", {
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
    onCloseUserInfo() {
      this.visibleUserInfo = false;
    },
    showUserInfo(text) {
      //debugger
      this.visibleUserInfo = true;
      this.userAccount = text;
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
        params.pageNum = 1;
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize;
        params.pageNum = 1;
      }
      params.sortField = "userAccount";
      params.sortOrder = "descend";
      params.userAccount = userAccount;
      this.$get("dcaBLetter/audit", {
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
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams,
      });
    },
  },
  computed: {
    columns() {
      return [
        {
          title: "发薪号",
          dataIndex: "userAccount",
          width: 80,
          scopedSlots: { customRender: "userAccount" },
        },
        {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 80,
        },
        {
          title: "通讯评审时间",
          dataIndex: "letterDate",
          width: 130,
        },
        {
          title: "通讯评审结果",
          dataIndex: "letterJl",
          width: 130,
        },
        {
          title: "有效期",
          dataIndex: "validDate",
          width: 130,
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
       
      ];
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>