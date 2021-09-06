<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <div>
      <div style="text-align:center;font-weight:bold; font-size:28px;">
        信息确认表
      </div>
      <div style="text-align:left; font-weight:bold; font-size:24px;">一 、学历学位信息</div>
      <table class="tb">
        <tr>
          <td colspan="2" style="text-align:center; font-weight:bold; font-size:24px;">博士信息</td>
        </tr>
        <tr>
          <td tyle="width:50%;">毕业学校</td>
          <td tyle="width:50%;">毕业时间</td>
        </tr>
        <tr>
          <td>{{worker.doctorSchool}}</td>
          <td>{{worker.doctorDate}}</td>
        </tr>
        <tr>
          <td colspan="2" style="text-align:center; font-weight:bold; font-size:24px;">硕士信息</td>
        </tr>
        <tr>
          <td>毕业学校</td>
          <td>毕业时间</td>
        </tr>
        <tr>
          <td>{{worker.graduateSchool}}</td>
          <td>{{worker.graduateDate}}</td>
        </tr>
        <tr>
          <td colspan="2" style="text-align:center; font-weight:bold; font-size:24px;">本科信息</td>
        </tr>
        <tr>
          <td>毕业学校</td>
          <td>毕业时间</td>
        </tr>
        <tr>
          <td>{{worker.bkSchool}}</td>
          <td>{{worker.bkDate}}</td>
        </tr>
        <tr>
          <td colspan="2" style="text-align:center; font-weight:bold; font-size:24px;">大专信息</td>
        </tr>
        <tr>
          <td>毕业学校</td>
          <td>毕业时间</td>
        </tr>
        <tr>
          <td>{{worker.dzSchool}}</td>
          <td>{{worker.dzDate}}</td>
        </tr>
        <tr>
          <td colspan="2" style="text-align:center; font-weight:bold; font-size:24px;">中专信息</td>
        </tr>
        <tr>
          <td>毕业学校</td>
          <td>毕业时间</td>
        </tr>
        <tr>
          <td>{{worker.zzSchool}}</td>
          <td>{{worker.zzDate}}</td>
        </tr>
      </table>
      <div style="text-align:left; font-weight:bold; font-size:24px;">二、专业技术职称信息</div>
      <table class="tb">
        <tr>
          <td style="width:50%;">现任专业技术职务</td>
          <td style="width:50%;">{{worker.xrzyzw}}</td>
        </tr>
        <tr>
          <td>聘任时间</td>
          <td>{{worker.prDate}}</td>
        </tr>
      </table>
       <div style="text-align:left; font-weight:bold; font-size:24px;">三、管理职员信息</div>
      <table class="tb">
        <tr>
          <td style="width:50%;">职员类别</td>
          <td style="width:50%;">{{worker.zylb}}</td>
        </tr>
        <tr>
          <td>现任职员职级</td>
          <td>{{worker.zrzyjb}}</td>
        </tr>
        <tr>
          <td>聘任时间</td>
          <td>{{worker.zrprDate}}</td>
        </tr>
      </table>
       <div style="text-align:left; font-weight:bold; font-size:24px;">四、签订劳动合同时间</div>
      <table class="tb">
        <tr><td>
        {{worker.comployDate}}</td>
        </tr>
      </table>
      <a-form-item v-bind="formItemLayout"
                    label="如需退回，请输入退回原因:">
                        <a-input placeholder="请输入退回的原因" v-model="zzZy" />
                </a-form-item>
    <div style="text-align:center">  <a-button @click="handleAudit" v-show="worker.id!=''&&worker.id!=null && worker.state==0" type="primary" :loading="loading">确认</a-button>
      <a-button style="margin-left:30px;"  @click="handleBack" v-show="worker.id!=''&&worker.id!=null && worker.state==0" type="primary" :loading="loading">退回</a-button>
      </div>
    </div>
  </a-card>
</template>

<script>
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'DcaBWorker',
  components: {},
  data () {
    return {
      loading: false,
      worker:{
        id:''

      },
      zzZy: ''
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    handleAudit () {
      let that = this
      this.$confirm({
        title: '确定提交此记录?',
        content: '以上信息经过本人签字确认无误，如有不实情况，由本人承担所有责任。确认，请提交',
        centered: true,
        onOk () {
          that.loading = true
          that.$delete('dcaBWorker/' + that.worker.id, {
            state: 1,
            zzZy: ''
          }).then(() => {
            //this.reset()
            that.$message.success('提交成功')
            that.search()
            // that.freshTabs()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
   handleBack () {
      let that = this
      if(that.zzZy==''){
        that.$message.success('请输入退回原因');
        return false
      }
      else {
      that.$confirm({
        title: '确定退回此记录?',
        content: '以上信息经过本人签字确认无误，如有不实情况，由本人承担所有责任。确认，请提交',
        centered: true,
        onOk () {
          that.loading = true
          that.$delete('dcaBWorker/' + that.worker.id, {
            state: 2,
            zzZy: that.zzZy
          }).then(() => {
            //this.reset()
            that.$message.success('退回成功')
            that.search()
            // that.freshTabs()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    }
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
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams
      })
    },
    reset () {
      
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams
      })
    },
    fetch (params = {}) {
      this.loading = true
      params.userAccount = this.$store.state.account.user.username
      this.$get('dcaBWorker', {
        ...params
      }).then((r) => {
        let data = r.data
        this.loading= false;
       if(data.rows.length>0){
          this.worker =data.rows[0]
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
<style lang="less" scoped>
  .tb{
      border: black solid 1px;
      width:100%;

      td{
         border: black solid 1px;
         text-align: center;
         font-size: 24px;
         height: 35px ;
      }
  }
</style>
