<template>
  <a-card title="医疗工作量">
    <a-table
      :columns="columns"
      :data-source="dataSource"
      :rowKey="record => record.id"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      bordered
      :scroll="scroll"
    >
      <template
        slot="year"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'year')"
            :value="record.year"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="mzbrl"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'mzbrl')"
            :value="record.mzbrl"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="glzybrl"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'glzybrl')"
            :value="record.glzybrl"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="remarknote"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'remarknote')"
            :value="record.remarknote"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="ssbrl"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'ssbrl')"
            :value="record.ssbrl"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="ssbrl1"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'ssbrl1')"
            :value="record.ssbrl1"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="ssbrl2"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'ssbrl2')"
            :value="record.ssbrl2"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="ssbrl3"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'ssbrl3')"
            :value="record.ssbrl3"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="ssbrl4"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'ssbrl4')"
            :value="record.ssbrl4"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="fileId"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          <a
            :href="record.fileUrl"
            v-if="text!=null && text !=''"
            target="_blank"
          >查看</a>
        </div>
        <div v-else>
          <a-button
            type="dashed"
            block
            @click="OpenFile(record)"
          >
            {{record.fileId!=null &&record.fileId !=''?'已上传':'上传' }}
          </a-button>
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
    </a-table>
    
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
import moment from 'moment';
import TableUploadFile from '../../common/TableUploadFile'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      CustomVisiable: false,
      idNums: 10000,
      fileVisiable: false,
      editRecord: {
        fileId: ''
      },
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
    }
  },
  components: { TableUploadFile },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    showFile (record) {
      window.location.href = record.fileUrl
    },
    OpenFile (record) {
      this.editRecord = record
      this.fileVisiable = true
      if (record.fileId != undefined && record.fileId != '') {
        this.$refs.upFile.fetch(record.fileId)
      }
    },
    setFileId (fileId, fileUrl) {
      this.fileVisiable = false
      console.log(fileUrl)
      /**
       const dataSource = [...this.dataSource]
       console.log(this.editRecord.id)
       let record=dataSource.filter(p=>p.id===this.editRecord.id)
       console.log(record)*/
      this.editRecord["fileId"] = fileId
      this.editRecord["fileUrl"] = fileUrl
      //this.dataSource =[...dataSource]
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      // console.log(selectedRows)
      if (selectedRows.length > 0) {
        if (selectedRows[0].state != 3 && selectedRows[0].state != 1) {
          this.selectedRowKeys = selectedRowKeys
        }
      }
      else {
        this.selectedRowKeys = selectedRowKeys
      }
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    handleSelectChange (value, option, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    handleAdd () {
      for (let i = 0; i < 4; i++) {
        this.dataSource.push({
          id: (this.idNums + i + 1).toString(),
          state: 0,
          year: '',
          mzbrl: '',
          glzybrl: '',
          remarknote: '',
          ssbrl: '',
          ssbrl1: '',
          ssbrl2: '',
          ssbrl3: '',
          ssbrl4: '',
          isUse: false
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSourceAll = [...this.dataSource]
      const dataSource = dataSourceAll.filter(p => p.state == 0 || p.state == 2)
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.year != '' || element.mzbrl != '' || element.glzybrl != '' || element.remarknote != '' || element.ssbrl != '' || element.ssbrl1 != '' || element.ssbrl2 != '' || element.ssbrl3 != '' || element.ssbrl4 != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length === 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBDocWorknum/addNew', {
          jsonStr: jsonStr,
          state: 0
        }).then(() => {
          // this.reset()
          this.$message.success('保存成功')
          this.fetch()
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      }
    },
    handleSubmit () {
      let that = this
      this.$confirm({
        title: '确定提交全部记录?',
        content: '当您点击确定按钮后，信息将不能修改',
        centered: true,
        onOk () {
          const dataSourceAll = [...that.dataSource]
          const dataSource = dataSourceAll.filter(p => p.state == 0 || p.state == 2)
          let dataAdd = []
          dataSource.forEach(element => {
            if (element.year != '' || element.mzbrl != '' || element.glzybrl != '' || element.remarknote != '' || element.ssbrl != '' || element.ssbrl1 != '' || element.ssbrl2 != '' || element.ssbrl3 != '' || element.ssbrl4 != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length === 0) {
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBDocWorknum/addNew', {
              jsonStr: jsonStr,
              state: 1
            }).then(() => {
              //this.reset()
              that.$message.success('提交成功')
              that.fetch()
              that.CustomVisiable = false //提交之后 不能再修改
              that.loading = false
            }).catch(() => {
              that.loading = false
            })
          }
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })


    },
    handleDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let dcaBPatentIds = that.selectedRowKeys.join(',')
          const dataSource = [...that.dataSource];
          let new_dataSource = dataSource.filter(p => that.selectedRowKeys.indexOf(p.id) < 0)
          that.dataSource = new_dataSource
          that.$message.success('删除成功')
          that.selectedRowKeys = []
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    fetch () {
      this.$get('dcaBDocWorknum/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows

       /* for (let i = 0; i < 4; i++) {
          this.dataSource.push({
            id: (this.idNums + i + 1).toString(),
            state: 0,
            year: '',
            mzbrl: '',
            glzybrl: '',
            remarknote: '',
            ssbrl: '',
            ssbrl1: '',
            ssbrl2: '',
            ssbrl3: '',
            ssbrl4: '',
            isUse: false
          })
          this.idNums = this.idNums + 4
        }*/
      })
    }
  },
  computed: {
    columns () {
      return [{
        title: '年度',
        dataIndex: 'year',
        width: 100,
       // scopedSlots: { customRender: 'year' }
      },
      {
        title: '门诊病人量',
        dataIndex: 'mzbrl',
        width: 100,
       // scopedSlots: { customRender: 'mzbrl' }
      },
      {
        title: '管理住院病人量',
        dataIndex: 'glzybrl',
        width: 100,
       // scopedSlots: { customRender: 'glzybrl' }
      },
     
      {
        title: '手术病人量(总）',
        dataIndex: 'ssbrl',
        width: 100,
       // scopedSlots: { customRender: 'ssbrl' }
      },
      {
        title: '手术病人量（1）',
        dataIndex: 'ssbrl1',
        width: 110,
      // scopedSlots: { customRender: 'ssbrl1' }
      },
      {
        title: '手术病人量（2）',
        dataIndex: 'ssbrl2',
        width: 110,
      //  scopedSlots: { customRender: 'ssbrl2' }
      },
      {
        title: '手术病人量（3）',
        dataIndex: 'ssbrl3',
        width: 110,
      //  scopedSlots: { customRender: 'ssbrl3' }
      },
      {
        title: '手术病人量（4）',
        dataIndex: 'ssbrl4',
        width: 110,
      //  scopedSlots: { customRender: 'ssbrl4' }
      },
       {
        title: '备注',
        dataIndex: 'remarknote',
        width: 100,
       // scopedSlots: { customRender: 'remarknote' }
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
     ]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
