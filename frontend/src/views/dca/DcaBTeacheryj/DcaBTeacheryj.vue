<template>
  <a-card title="主要教学业绩">
    <div>
      <a-button @click="handleAdd" type="primary" :loading="loading"
        >添加行</a-button
      >
      <a-button @click="handleDelete" type="primary" :loading="loading"
        >删除行</a-button
      >
    </div>
    <a-table
      :columns="columns"
      :data-source="dataSource"
      :rowKey="(record) => record.id"
      :rowSelection="{
        selectedRowKeys: selectedRowKeys,
        onChange: onSelectChange,
      }"
      bordered
      :scroll="scroll"
    >
      <template slot="teachName" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-select
            :value="record.teachName"
            style="width: 380px"
            @change="(e, f) => handleSelectChange(e, f, record, 'teachName')"
          >
            <a-select-option
              value="国家教学成果一等奖的第一名"
            >国家教学成果一等奖的第一名</a-select-option>
            <a-select-option
              value="全国百篇优秀博士论文指导教师"
            >全国百篇优秀博士论文指导教师</a-select-option>
            <a-select-option value="国家级“教学名师奖”获得者">国家级“教学名师奖”获得者</a-select-option>
            <a-select-option value="国家级精品课负责人">国家级精品课负责人</a-select-option>
            <a-select-option
              value="省部级教学成果一等奖及以上的第一名"
            >省部级教学成果一等奖及以上的第一名</a-select-option>
            <a-select-option
              value="国家教学基地负责人（以有关文件为准）"
            >国家教学基地负责人（以有关文件为准）</a-select-option>
            <a-select-option
              value="国家教材二等奖及以上的第一名"
            >国家教材二等奖及以上的第一名</a-select-option>
            <a-select-option
              value="国家教学成果二等奖及以上的第一名"
            >国家教学成果二等奖及以上的第一名</a-select-option>
            <a-select-option
              value="国家教学成果二等奖及以上的前两名"
            >国家教学成果二等奖及以上的前两名</a-select-option>
          </a-select>
        </div>
      </template>
      <template slot="teachDate" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text == "" || text == null ? "" : text.substr(0, 10) }}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="
              text == '' || text == null ? '' : moment(text, dateFormat)
            "
            @change="(e, f) => handleChange(e, f, record, 'teachDate')"
          />
        </div>
      </template>
      <template slot="rankNum" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-input-number
            style="width: 100%"
            @blur="(e) => inputChange(e.target.value, record, 'rankNum')"
            :value="record.rankNum"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template slot="teachContent" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          {{ text }}
        </div>
        <div v-else>
          <a-textarea
            @blur="(e) => inputChange(e.target.value, record, 'teachContent')"
            :value="record.teachContent"
          >
          </a-textarea>
        </div>
      </template>
      <template slot="fileId" slot-scope="text, record">
        <div v-if="record.state == 3 || record.state == 1">
          <a
            :href="record.fileUrl"
            v-if="text != null && text != ''"
            target="_blank"
            >查看</a
          >
        </div>
        <div v-else>
          <a-button type="dashed" block @click="OpenFile(record)">
            {{
              record.fileId != null && record.fileId != "" ? "已上传" : "上传"
            }}
          </a-button>
        </div>
      </template>
      <template slot="isUse" slot-scope="text, record">
        <a-checkbox
          @change="(e) => onIsUseChange(e, record, 'isUse')"
          :checked="text"
        ></a-checkbox>
      </template>
    </a-table>
    <div>
      <a-button @click="handleSave" type="primary" :loading="loading"
        >保存草稿</a-button
      >
      <a-button @click="handleSubmit" type="primary" :loading="loading"
        >全部提交</a-button
      >
    </div>
    <tableUpload-file
      ref="upFile"
      :fileId="editRecord.fileId"
      :fileVisiable="fileVisiable"
      @setFileId="setFileId"
    >
    </tableUpload-file>
  </a-card>
</template>

<script>
import moment from "moment";
import TableUploadFile from "../../common/TableUploadFile";
export default {
  data() {
    return {
      dateFormat: "YYYY-MM-DD",
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      CustomVisiable: false,
      idNums: 10000,
      fileVisiable: false,
      editRecord: {
        fileId: "",
      },
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 20 - 80,
      },
    };
  },
  components: { TableUploadFile },
  mounted() {
    this.fetch();
  },
  methods: {
    moment,
    showFile(record) {
      window.location.href = record.fileUrl;
    },
    OpenFile(record) {
      this.editRecord = record;
      this.fileVisiable = true;
      if (record.fileId != undefined && record.fileId != "") {
        this.$refs.upFile.fetch(record.fileId);
      }
    },
    setFileId(fileId, fileUrl) {
      this.fileVisiable = false;
      console.log(fileUrl);
      /**
       const dataSource = [...this.dataSource]
       console.log(this.editRecord.id)
       let record=dataSource.filter(p=>p.id===this.editRecord.id)
       console.log(record)*/
      this.editRecord["fileId"] = fileId;
      this.editRecord["fileUrl"] = fileUrl;
      //this.dataSource =[...dataSource]
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      // console.log(selectedRows)
      if (selectedRows.length > 0) {
        if (selectedRows[0].state != 3 && selectedRows[0].state != 1) {
          this.selectedRowKeys = selectedRowKeys;
        }
      } else {
        this.selectedRowKeys = selectedRowKeys;
      }
    },
    handleChange(date, dateStr, record, filedName) {
      const value = dateStr;
      record[filedName] = value;
    },
    inputChange(value, record, filedName) {
      console.info(value);
      record[filedName] = value;
    },
    handleSelectChange(value, option, record, filedName) {
      console.info(value);
      record[filedName] = value;
    },
    onIsUseChange(e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    handleAdd() {
      for (let i = 0; i < 4; i++) {
        this.dataSource.push({
          id: (this.idNums + i + 1).toString(),
          state: 0,
          teachName: "",
          teachDate: "",
          rankNum: "",
          teachContent: "",
          isUse: false,
        });
      }
      this.idNums = this.idNums + 4;
    },
    handleSave() {
      const dataSourceAll = [...this.dataSource];
      const dataSource = dataSourceAll.filter(
        (p) => p.state == 0 || p.state == 2
      );
      let dataAdd = [];
      dataSource.forEach((element) => {
        if (
          element.teachName != "" ||
          element.teachDate != "" ||
          element.teachContent != ""
        ) {
          dataAdd.push(element);
        }
      });
      if (dataAdd.length === 0) {
        this.$message.warning("请填写数据！！！");
      } else {
        let jsonStr = JSON.stringify(dataAdd);
        this.loading = true;
        this.$post("dcaBTeacheryj/addNew", {
          jsonStr: jsonStr,
          state: 0,
        })
          .then(() => {
            // this.reset()
            this.$message.success("保存成功");
            this.fetch();
            this.loading = false;
          })
          .catch(() => {
            this.loading = false;
          });
      }
    },
    handleSubmit() {
      let that = this;
      this.$confirm({
        title: "确定提交全部记录?",
        content: "当您点击确定按钮后，信息将不能修改",
        centered: true,
        onOk() {
          const dataSourceAll = [...that.dataSource];
          const dataSource = dataSourceAll.filter(
            (p) => p.state == 0 || p.state == 2
          );
          let dataAdd = [];
          dataSource.forEach((element) => {
            if (
              element.teachName != "" ||
              element.teachDate != "" ||
              element.teachContent != ""
            ) {
              dataAdd.push(element);
            }
          });
          if (dataAdd.length === 0) {
              setTimeout(() => { //hsc 2021 09 26 提交后跳转下一个
              that.$EventBus.$emit('selectMoudles',70)
            }, 300);
          } else {
            let jsonStr = JSON.stringify(dataAdd);
            that.loading = true;
            that
              .$post("dcaBTeacheryj/addNew", {
                jsonStr: jsonStr,
                state: 1,
              })
              .then(() => {
                //this.reset()
                that.$message.success("提交成功");
               // that.fetch();
                that.CustomVisiable = false; //提交之后 不能再修改
                that.loading = false;
                 setTimeout(() => { //hsc 2021 09 26 提交后跳转下一个
              that.$EventBus.$emit('selectMoudles',70)
            }, 300);
              })
              .catch(() => {
                that.loading = false;
              });
          }
        },
        onCancel() {
          that.selectedRowKeys = [];
        },
      });
    },
    handleDelete() {
      if (!this.selectedRowKeys.length || this.selectedRowKeys.length > 1) {
        this.$message.warning("请选择一条需要删除的记录");
        return;
      }
      let that = this;
      this.$confirm({
        title: "确定删除所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被彻底删除",
        centered: true,
        onOk() {
          let dcaBPatentIds = that.selectedRowKeys.join(",");
          const dataSource = [...that.dataSource];
          let new_dataSource = dataSource.filter(
            (p) => that.selectedRowKeys.indexOf(p.id) < 0
          );
          that.dataSource = new_dataSource;
          that
            .$put("dcaBTeacheryj", {
              id: that.selectedRowKeys[0],
              isDeletemark: 0,
            })
            .then(() => {})
            .catch(() => {});
          that.$message.success("删除成功");
          that.selectedRowKeys = [];
        },
        onCancel() {
          that.selectedRowKeys = [];
        },
      });
    },
    fetch() {
      this.$get("dcaBTeacheryj/custom", {}).then((r) => {
        let data = r.data;
        this.dataSource = data.rows;

        for (let i = 0; i < 4; i++) {
          this.dataSource.push({
            id: (this.idNums + i + 1).toString(),
            state: 0,
            teachName: "",
            teachDate: "",
            rankNum: "",
            teachContent: "",
            isUse: false,
          });
          this.idNums = this.idNums + 4;
        }
      });
    },
  },
  computed: {
    columns() {
      return [
        {
          title: "名称",
          dataIndex: "teachName",
          width: 400,
          scopedSlots: { customRender: "teachName" },
        },
        {
          title: "排名",
          dataIndex: "rankNum",
          width: 130,
          scopedSlots: { customRender: "rankNum" },
        },
        {
          title: "任职（获得）时间",
          dataIndex: "teachDate",
          width: 130,
          scopedSlots: { customRender: "teachDate" },
        },
        {
          title: "备注",
          dataIndex: "teachContent",
          width: 130,
          scopedSlots: { customRender: "teachContent" },
        },
        {
          title: "状态",
          dataIndex: "state",
          width: 100,
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
        {
          title: "审核意见",
          dataIndex: "auditSuggestion",
        },
        {
          title: "经审核是否构成职称晋升条件",
          dataIndex: "isUse",
          scopedSlots: { customRender: "isUse" },
          width: 100,
        },
        {
          title: "附件",
          dataIndex: "fileId",
          scopedSlots: { customRender: "fileId" },
          width: 80,
        },
      ];
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
