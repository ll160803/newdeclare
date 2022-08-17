<template>
  <a-card :bordered="false" class="card-area">
    <a-form :form="form">
      <a-row>
            <a-col :md="6" :sm="24">
              <a-input v-model="year" placeholder="请输入年度" />
         </a-col>
        <a-col :md="6" :sm="24">
            <a-date-picker
              @change="dateChange"
            />
        </a-col>
         <a-col :md="6" :sm="24">
              <a-button type="primary" @click="setting">设置</a-button>
         </a-col>
      </a-row>
    </a-form>
  </a-card>
</template>

<script>
import moment from 'moment'
export default {
  name: 'zq',
  data () {
    return {
      form: this.$form.createForm(this),
      loading: false,
      date: '',
      year: ''
    }
  },
  mounted(){
    
  },
  methods: {
    moment,
    reset () {
      this.loading = false
      this.form.resetFields()
    },
   dateChange(date,strDate){
       console.info(strDate+date)
       this.date= strDate
   },
    setting(){
         this.loading =true
        // let scmDControl = this.form.getFieldsValue()
         if(this.date=='' ||this.year==''){
              this.$message.warn("请填写内容");
         }
         else{
          this.$put('zqDScore', {
            startDate: this.date,
            year: this.year
          }).then(() => {
              this.loading =false
            //this.reset()
             this.$message.success('设置成功.')
          }).catch(() => {
            this.loading = false
          })
         }
    }
  }
}
</script>

<style>
</style>