<template>
  <a-card
    :bordered="bordered"
    class="card-area"
    title="完成上一聘期工作任务情况"
  >
  <p style="color:red">（限3000字）</p>
    <a-textarea
      :value="lastContent"
      @blur="e => inputChange(e.target.value)"
      :rows="12"
    ></a-textarea>
    <div>
      <a-button
        @click="handleSave"
        type="primary"
        :loading="loading"
      >保存草稿</a-button>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading"
      >全部提交</a-button>
    </div>
  </a-card>
</template>

<script>

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'DcaBLastemploy',
  data () {
    return {
      dataSource: [],
      lastContent: '',
      loading: false,
      bordered: true,
      CustomVisiable: false
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    inputChange (value) {
      this.lastContent = value
    },
    handleSave () {
      if (this.lastContent.trim() === '') {
        this.$message.warning('请填写数据！！！')
      }
      else {
        this.loading = true
        this.$post('dcaBLastemploy', {
          lastContent: this.lastContent,
          state: 0
        }).then(() => {
          // this.reset()
          this.$message.success('保存成功')
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
          if (that.lastContent.trim() === '') {
            that.$message.warning('请填写数据！！！')
          }
          else {
            that.loading = true

            that.$post('dcaBLastemploy', {
              lastContent: that.lastContent,
              state: 1
            }).then(() => {
              // this.reset()
              that.$message.success('保存成功')
              that.CustomVisiable = false
              that.loading = false
               setTimeout(() => { //hsc 2021 09 26 提交后跳转下一个
              that.$EventBus.$emit('selectMoudles',160)
            }, 300);
            }).catch(() => {
              that.loading = false
            })
          }
        },
        onCancel () {

        }
      })


    },
    fetch () {
      this.$get('dcaBLastemploy/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows
        if (data.rows.length > 0
        ) {
          if (data.rows[0].state === 0) {
            this.CustomVisiable = true
          }
          this.lastContent = data.rows[0].lastContent
        }
        else {
          this.CustomVisiable = true
        }
      }
      )
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
