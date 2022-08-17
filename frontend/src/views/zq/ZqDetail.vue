<template>
  <a-modal
    title="打分详情"
    :visible="infoVisiable"
    :footer="null"
    @cancel="cancelInfo"
    :maskClosable="false"
    width="80%"
  >
   
  
      <a-table
        :columns="eduColumns"
        :pagination="false"
        :data-source="eduList"
        :rowKey="(record) => record.id"
        bordered
      >
      </a-table>
  </a-modal>
</template>
  
  <script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 12 },
  wrapperCol: { span: 12 },
};
const formItemLayout2 = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};
export default {
  name: "userInfo",
  data() {
    return {
      eduList: [],
      formItemLayout,
      formItemLayout2,
      dcaBUser: {},
      score: 0,
      khDScore: {},
      loading: false
    };
  },
  props: {
    infoVisiable: {
      default: false,
    },
    userAccount: {
      default: "",
    },
    year: {
      default: "",
    },
    picUrl: {
      default: "",
    },
     isShow: {
      default: 0,
    },
  },
  watch: {
    infoVisiable() {
      if (this.infoVisiable) {
        this.fetch(this.userAccount, this.year);
        //this.dcaBUser = this.dcaBUser1
       
      }
    },
  },
  computed: {
    eduColumns() {
      return [
         {
          title: "账号",
          dataIndex: "auditUserAccount",
          width: 100,
        },
        {
          title: "姓名",
          dataIndex: "auditUserAccountName",
          width: 100,
        },
        {
          title: "科室名称",
          dataIndex: "auditDeptName",
          width: 100,
        },
        {
          title: "分组",
          dataIndex: "auditFenzu",
          width: 100,
        },
        {
          title: "打分类型",
          dataIndex: "auditType",
          width: 100,
        },
        {
          title: "考核年度",
          dataIndex: "year",
          width: 100,
        },
        {
          title: "分数",
          dataIndex: "score",
          width: 100,
        },
      ];
    },
  },
  methods: {
    moment,
    reset() {
      this.eduList = [];
    },
    fetch(userAccount, year) {
      this.$get("khDScore", {
          userAccount: userAccount,
        year: year,
      }).then((r) => {
        let data = r.data;
        this.eduList = data.rows;
      });
    },
  
   
  
  
    cancelInfo() {
      this.reset();
      this.$emit("close");
    },
  },
};
</script>
  
  <style>
</style>