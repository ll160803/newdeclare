<template>
  <div>
    <a-spin :spinning="loading">
      <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="record => record.id"
        :pagination="pagination"
        @change="handleTableChange"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        bordered
        :scroll="scroll"
      >
        <template
          slot="splitHang"
          slot-scope="text, record"
        >
          <p
            style="width:100%;"
            v-for="item in splitStr(text)"
          >{{item}}</p>
        </template>
        <template
          slot="userAccount"
          slot-scope="text, record"
        >
          <a @click="showUserInfo(text)">{{text}}</a>
        </template>
        <template
          slot="action"
          slot-scope="text, record"
        >
          <div v-if="record.state==2">
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="ExportDeclareReport(record)"
            >
              导出职称申报表
            </a-button>
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="ExportAttachReport(record)"
            >
              导出附件材料
            </a-button>
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="ExportDocReport(record)"
            >
              导出高级职称确认表
            </a-button>
             <a-button
              v-if="record.showState == 0"
              v-hasNoPermission="['dca:audit']"
              style="width: 100%; padding-left: 2px; padding-right: 2px"
              type="dashed"
              block
              @click="handleBack(record)"
            >
              退回待处理
            </a-button>
          </div>
          <div v-else-if="record.state==1">
            <a-button
              v-hasNoPermission="['dca:audit']"
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="handleSave(record)"
            >
              退回
            </a-button>
          </div>
        </template>
        <template
          slot="auditMan"
          slot-scope="text, record"
        >
          <div v-if="text=='正常'">
            <a-tag
              color="green"
              @click="showUserInfoRight(record)"
            >正常</a-tag>
          </div>
          <div v-else>
            <a-tag
              color="red"
              @click="showUserInfoRight(record)"
            >异常</a-tag>
          </div>
        </template>
      </a-table>
      <audit-userInfo
        ref="userinfo"
        @close="onCloseUserInfo"
        :visibleUserInfo="visibleUserInfo"
        :userAccount="userAccount"
      ></audit-userInfo>
      <audit-resultInfo
        ref="userinfo"
        @close="onCloseUserInfoRight"
        :visibleUserInfo="visibleUserInfo_right"
        :userAccount="userAccount_right"
        :dcaYear="deaYear"
        :gwdj="gwDj"
      ></audit-resultInfo>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment';
import AuditUserInfo from '../../common/AuditUserInfo'
import AuditResultInfo from '../../common/AuditResultInfo'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
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
        ks: '正高,副高'
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 2700,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: '',
      userAccount_right: '',
      visibleUserInfo_right: false,
      deaYear: '',
      gwDj:'',
      listAuditInfo: [
        { fieldName: 'ydyf' },
        { fieldName: 'ydyffj' },
        { fieldName: 'zzsc' },
        { fieldName: 'zzscypfj' },
        { fieldName: 'jlsc' },
        { fieldName: 'xsddsc' },
        { fieldName: 'xsddscypfj' },
        { fieldName: 'yyxtsc' },
        { fieldName: 'sfssds' },
        { fieldName: 'sfbsds' },

{ fieldName: 'sfdlwcyjspy' },
{ fieldName: 'pyzlsfyl' },

        { fieldName: 'sftgsdsf' },
        { fieldName: 'sdsfypfj' },
        { fieldName: 'sdsfypfj2' },
       { fieldName: 'ylpfbfz2022' },
        { fieldName: 'ylpfdj2022' },

        { fieldName: 'mzylsgypfj' }
        , { fieldName: 'sfypfjyl' },
        { fieldName: 'hlylpf' },
        { fieldName: 'hlylpfdj' },
        { fieldName: 'hljxpfbfz' },
        { fieldName: 'hljxpfdl' },
        { fieldName: 'hlhlzrypfj' },
        { fieldName: 'sshbdts' },
        { fieldName: 'sshkyxts' },
        { fieldName: 'blxwjf' }, {
          fieldName: 'wfzgszcf'
        },
        { fieldName: 'zypfdjyjxl' },

        { fieldName: 'zypfyjxl' },
        { fieldName: 'zypfbfz58' },
        { fieldName: 'zypfdj59' },
        { fieldName: 'sfyszgzs' },
        { fieldName: 'sfjyhlzgzs' },
        { fieldName: 'xingfscsftg' },
        { fieldName: 'sfczxfypfj61' },
        { fieldName: 'zypf52' },
        { fieldName: 'zypfdj52' },
        //beizhu
        { fieldName: 'beizhumenban' },
        { fieldName: 'beizhuyiwuchu' },
        { fieldName: 'beizhuhuli' },
        // { fieldName: 'ifxjjl' },
        // { fieldName: 'yearmore' }
      ], // 当前用户包含的审核数据
    }
  },
  components: { AuditUserInfo, AuditResultInfo },
  props: {
    state: {
      default: 1
    },
    activeKey: {
      default: 1
    }
  },
  mounted () {
    this.fetch2(this.queryParams)
  },
  methods: {
    moment,
    splitStr (text) {
      if (text == null) return ''
      return text.split('#')
    },
    search () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch2({
        sortField: "paixu1,pingshenfenzu,paixu2,paixu3,paixu4,convert(kspaixu using gbk) collate gbk_chinese_ci,cast(ksrank as unsigned integer) ",
        sortOrder: "ascend",
        ...this.queryParams
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
    },
    fetch2 (params = {}) {
      this.loading = true
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
      params.sortField = "paixu1,pingshenfenzu,paixu2,paixu3,paixu4,convert(kspaixu using gbk) collate gbk_chinese_ci,cast(ksrank as unsigned integer)   "
      params.sortOrder = "ascend"
      let that = this
      that.$get('dcaBReport', {
        state: this.state,
        ...params
      }).then((r) => {
        that.loading = false
        let data = r.data
        const pagination = { ...that.pagination }
        pagination.total = data.total
        data.rows.forEach(element => {
          let auditList = element.dcaBAuditdynamicList
          // console.info(auditList)
          if (auditList != null && auditList.length > 0) {
            // console.info(this.listAuditInfo)
            that.listAuditInfo.forEach(element2 => {
              //  console.info('element2' + element2)
              let lire = auditList.filter(p => p.auditTitletype == element2.fieldName);
              // console.info(lire);
              if (lire.length > 0) {
                element[element2.fieldName] = lire[0].auditResult
              }
              else {
                element[element2.fieldName] = ''
              }
            });
          }
          else {
            that.listAuditInfo.forEach(element2 => {
              element[element2.fieldName] = ''
            });
          }

        });
        that.dataSource = data.rows
        that.pagination = pagination
      }
      )
    },
    ExportDeclareReport (record) {
      this.$download('dcaBCopyUser/excel', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        gwdj: record.gwdj,//岗位等级
        npPositionName: record.npPositionName,
        sexName: record.gwdj //岗位等级
      }, record.userAccount + ".pdf")
    },
    ExportDocReport (record) {
      this.$download('dcaBCopyUser/doc', {
        userAccount: record.userAccount,
        year: record.year,
        npPositionName: record.npPositionName,
        gwdj: record.gwdj//岗位等级
      }, record.year + '_' + record.userAccount + '_高级职称表' + ".docx")
    },
    ExportAttachReport (record) {
      this.$download('dcaBCopyUser/attach', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        npPositionName: record.npPositionName
      }, record.year + record.userAccount + ".pdf")
    },
      handleBack(record) {
      // let jsonStr = JSON.stringify(record)
      let vRecord = {};
      vRecord.id = record.id;
      vRecord.userAccount = record.userAccount;
      vRecord.state = 0;
      var that = this;
      that.$confirm({
        title: "确定退回所选中的记录?",
        content: "当您点击确定按钮后，此记录将退回待处理状态！",
        centered: true,
        onOk() {
          // vRecord.dcaBAuditdynamicList=''
          that.loading = true;
          that
            .$put("dcaBReport", {
              ...vRecord,
            })
            .then(() => {
              // this.reset()
              that.$message.success("退回成功");
              that.search();
              that.loading = false;
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
      });
    },
    handleSave (record) {

      // let jsonStr = JSON.stringify(record)
      let vRecord = {}
      vRecord.id = record.id
      vRecord.userAccount = record.userAccount
      vRecord.state = 0
      // vRecord.dcaBAuditdynamicList=''
      this.loading = true
      this.$put('dcaBReport', {
        ...vRecord
      }).then(() => {
        // this.reset()
        this.$message.success('保存成功')
        this.search()
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    onCloseUserInfo () {
      this.visibleUserInfo = false
    },
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },
    showUserInfoRight (record) {
      //debugger
      this.visibleUserInfo_right = true
      this.userAccount_right = record.userAccount
      this.deaYear = record.state == 2 ? record.year : ''
       this.gwDj = record.gwdj
    },
    onCloseUserInfoRight () {
      this.visibleUserInfo_right = false
    },
    fetch (userAccount) {
      this.loading = true
      this.queryParams.userAccount = userAccount
      let params = {}
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.paginationInfo.defaultCurrent
      }
      params.sortField = "paixu1,pingshenfenzu,paixu2,paixu3,paixu4,convert(kspaixu using gbk) collate gbk_chinese_ci,cast(ksrank as unsigned integer)   "
      params.sortOrder = "ascend"
      params.userAccount = userAccount
      let that = this
      that.$get('dcaBReport', {
        state: this.state,
        ...params
      }).then((r) => {
        that.loading = false
        let data = r.data
        const pagination = { ...that.pagination }
        pagination.total = data.total
        data.rows.forEach(element => {
          let auditList = element.dcaBAuditdynamicList

          // console.info(auditList)
          if (auditList != null && auditList.length > 0) {
            // console.info(this.listAuditInfo)
            that.listAuditInfo.forEach(element2 => {
              //  console.info('element2' + element2)
              let lire = auditList.filter(p => p.auditTitletype == element2.fieldName);
              // console.info(lire);
              if (lire.length > 0) {
                element[element2.fieldName] = lire[0].auditResult
              }
              else {
                element[element2.fieldName] = ''
              }
            });
          }
          else {
            that.listAuditInfo.forEach(element2 => {
              element[element2.fieldName] = ''
            });
          }


        });
        that.dataSource = data.rows
        that.pagination = pagination
      }
      )
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch2({
        sortField: "paixu1,pingshenfenzu,paixu2,paixu3,paixu4,convert(kspaixu using gbk) collate gbk_chinese_ci,cast(ksrank as unsigned INTEGER)  ",
        sortOrder: "ascend",
        ...this.queryParams
      })
    },
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      let json = [
        {
          title: '序号',
          dataIndex: 'indexHao'
        },
        {
          title: '顺序号1',
          dataIndex: 'confirmIndex'
        },

        {
          title: '报名档案顺序号2',
          dataIndex: 'baomingIndex'
        },
        {
          title: '系列3',
          dataIndex: 'xl',
        },
        {
          title: '评审分组4',
          dataIndex: 'pingshenfenzu'
        },
        {
          title: '双报标志5',
          dataIndex: 'ifshuangbao'
        },
          {
          title: '审核后是否双报',
          dataIndex: 'wchshuangbao',
          width: 40
        },
        {
          title: '人事编号6',
          dataIndex: 'userAccount',
          width: 80
        },
        {
          title: '申报等级7',
          dataIndex: 'gwdj'
        },
        {
          title: '科室8',
          dataIndex: 'ks',
        },
        {
          title: '科室分类9',
          dataIndex: 'kslb',
          width: 130
        },
        {
          title: '员工组',
          dataIndex: 'yuangongzu',
          width: 80
        },
        {
          title: '姓名10',
          dataIndex: 'userAccountName'
        },
        {
          title: '出生年月',
          dataIndex: 'birthdaystr'
        },
        {
          title: '年龄12',
          dataIndex: 'age'
        },
        {
          title: '性别13',
          dataIndex: 'sexName'
        },
        {
          title: '学历(位)14',
          dataIndex: 'edu'
        },
        {
          title: '毕业时间15',
          dataIndex: 'eduDate'
        },

        {
          title: '现职务名称16',
          dataIndex: 'positionName',
          width: 100
        },
        {
          title: '聘任时间17',
          dataIndex: 'zygwDate',
          width: 100
        },

        {
          title: '申报职称18',
          dataIndex: 'npPositionName',
          width: 100
        },
        {
          title: '来院时间19',
          dataIndex: 'schoolDate',
          width: 100
        },

        {
          title: '是否起带头或骨干作用20',
          dataIndex: 'ifdaitou',
          width: 80,
          scopedSlots: { customRender: 'ifdaitou' }
        },
        {
          title: '等级4900',
          dataIndex: 'ylpfdj',
          width: 100
        },
        {
          title: '分数5000',
          dataIndex: 'ylpfbfz',
          width: 80,

        },
        {
          title: '门办等级4900',
          dataIndex: 'mzylpfdj',
          width: 100
        },
        {
          title: '门办分数5000',
          dataIndex: 'mzylpf',
          width: 80,

        },
        {
          title: '等级5100',
          dataIndex: 'jxpfdj',
          width: 100
        }, {
          title: '分数5200',
          dataIndex: 'jxpf',
        },

        {
          title: '教学科研项目或获奖情况是否符合',
          dataIndex: 'iffuhekeyan',

        },
        {
          title: '第一作者论文情况是否符合',
          dataIndex: 'iffuhediyi',
          width: 100,
          scopedSlots: { customRender: 'iffuhediyi' }
        },
        {
          title: '是否符合必备条件',
          dataIndex: 'iffuhebibei',
          width: 80,
          scopedSlots: { customRender: 'iffuhebibei' }
        },
        {
          title: '抗疫一线人员情况（是）',
          dataIndex: 'yxryqk',
          width: 80,
          scopedSlots: { customRender: 'yxryqk' }
        },
        {
          title: '名称23',
          dataIndex: 'sciName'
        },
        {
          title: '等级24',
          dataIndex: 'sciDengji'
        },
        {
          title: '排名25',
          dataIndex: 'sciRanknum',
        },

        {
          title: '名称26',
          dataIndex: 'teachName',

        },
        {
          title: '等级27',
          dataIndex: 'teachDengji',
        },
        {
          title: '排名28',
          dataIndex: 'teachRanknum',
        },

        {
          title: '项数29',
          dataIndex: 'patentNum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '实施转让费30',
          dataIndex: 'patentFund',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },


        {
          title: 'A 类',
          dataIndex: 'publishA',
          width: 100
        },
        {
          title: 'B 类',
          dataIndex: 'publishB',
          width: 100
        },
        {
          title: 'C 类',
          dataIndex: 'publishC',
          width: 100
        },
        {
          title: 'D 类',
          dataIndex: 'publishD',
          width: 100
        },
        {
          title: 'E 类',
          dataIndex: 'publishE',
          width: 100
        },
        {
          title: 'F 类',
          dataIndex: 'publishF',
          width: 100
        },
        {
          title: 'D类以上',
          dataIndex: 'publishDup',
          width: 100,
        },
        {
          title: 'E类以上',
          dataIndex: 'publishEup',
          width: 100,
        },
        {
          title: 'F类以上',
          dataIndex: 'publishFup',
          width: 100,
        },



        {
          title: '出版书类别',
          dataIndex: 'publicarticle1',
          width: 100
        },
        {
          title: '承担字数(万)',
          dataIndex: 'publicarticle2',
          width: 100
        },
         {
                      title: '是否构成选择条件',
                      dataIndex: 'ifgoucheng',
                      width: 100
                
                    },


        {
          title: '名称32',
          dataIndex: 'schoolprizeName',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '等级33',
          dataIndex: 'schoolprizeDengji',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '排名34',
          dataIndex: 'schoolprizeRanknum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '时间35',
          dataIndex: 'schoolprizeDate',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },


        {
          title: '等级36',
          dataIndex: 'courseDengji',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '排名37',
          dataIndex: 'courseRanknum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '时间38',
          dataIndex: 'courseDate',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },



        {
          title: '奖项级别39',
          dataIndex: 'youngName',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '等级40',
          dataIndex: 'youngDengji',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '排名41',
          dataIndex: 'youngRanknum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '时间42',
          dataIndex: 'youngDate',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },



        {
          title: '类别43',
          dataIndex: 'sciDjlb',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '经费44',
          dataIndex: 'sciDjfund',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '排名45',
          dataIndex: 'sciDjranknum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },



        {
          title: '类别46',
          dataIndex: 'sciJflb',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '经费47',
          dataIndex: 'sciJffund',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '排名48',
          dataIndex: 'sciJfranknum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },



        {
          title: '等级49',
          dataIndex: 'ylpfdj',
          width: 100
        },
        {
          title: '分数50',
          dataIndex: 'ylpfbfz',
          width: 80,

        },
        {
          title: '门办等级49',
          dataIndex: 'mzylpfdj',
          width: 100
        },
        {
          title: '门办分数50',
          dataIndex: 'mzylpf',
          width: 80,

        },
        {
          title: '等级51',
          dataIndex: 'jxpfdj',
          width: 100
        }, {
          title: '分数52',
          dataIndex: 'jxpf',
        },

        {
          title: '评分合计53',
          dataIndex: 'pfHeji',
          width: 100
        },

        {
          title: '是否担任一年辅导员或班主任54',
          dataIndex: 'tutor',
          width: 100
        },
        {
          title: '近五年教学',
          dataIndex: 'j5njxgz',
          width: 200,
        },
        {
          title: '是否有校级及以上教学竞赛奖励',
          dataIndex: 'ifxjjl',
          width: 120,
          scopedSlots: { customRender: 'ifxjjl' }
        },
        {
          title: '是否有连续一年以上出国学习经历',
          dataIndex: 'yearmore',
          width: 200,
        },
        {
          title: '申报类型55',
          dataIndex: 'sblx',
          width: 120,
          scopedSlots: { customRender: 'sblx' }
        },
        {
          title: '达到选择条件一之第几条56',
          dataIndex: 'choosepos',
          width: 100,
          scopedSlots: { customRender: 'choosepos' }
        },
        {
          title: '部门审核结果57',
          dataIndex: 'auditMan',
          width: 100,
          scopedSlots: { customRender: 'auditMan' }

        },

        {
          title: '是否通过医德医风审核58',
          dataIndex: 'ydyf',
          width: 100,

        },
        {
          title: '是否存在医德医风一票否决的情况59',
          dataIndex: 'ydyffj',
          width: 120
        }
        ,

        {
          title: '是否通过政治综合评价60',
          dataIndex: 'zzsc',
          width: 100,

        },
        {
          title: '是否存在政治综合评价一票否决的情况61',
          dataIndex: 'zzscypfj',
          width: 120

        },

        {
          title: '是否通过纪律审查62',
          dataIndex: 'jlsc',
          width: 100,

        },
        {
          title: '是否存在纪律审查一票否决的情况63',
          dataIndex: 'jlscypfj',
          width: 120
        }
        ,

        {
          title: '是否通过学术道德审查64',
          dataIndex: 'xsddsc',
          width: 100,

        },
        {
          title: '是否存在学术道德一票否决的情况65',
          dataIndex: 'xsddscypfj',
          width: 120
        },
        {
          title: '专业评分（仅研究系列需要填写）(百分制)66',
          dataIndex: 'zypfyjxl',
          width: 150,
        },
        {
          title: '专业评分等级（仅研究系列需要填写）67',
          dataIndex: 'zypfdjyjxl',
          width: 150
        }
        ,

        {
          title: '是否通过意识形态审查68',
          dataIndex: 'yyxtsc',
          width: 100,

        },
        {
          title: '是否存在意识形态一票否决的情况69',
          dataIndex: 'yyxtypfj',
          width: 120
        },
        {
          title: '专业评分（仅宣传部职工需要填写）(百分制）70',
          dataIndex: 'zypfbfz58',
          width: 150,
        },
        {
          title: '专业评分等级（仅宣传部职工需要填写）71',
          dataIndex: 'zypfdj59',
          width: 150
        }
        ,

        {
          title: '是否硕士导师72',
          dataIndex: 'sfssds',
          width: 100,
        },
        {
          title: '是否博士导师73',
          dataIndex: 'sfbsds',
          width: 120
        },
        {
          title: '是否通过师德师风审查74',
          dataIndex: 'sftgsdsf',
          width: 120,

        },
        {
          title: '是否存在师德师风一票否决的情况75',
          dataIndex: 'sdsfypfj',
          width: 120
        }
        ,
 {
              title: "是否独立完成一届以上研究生培养工作（截止申报年度10月31日）",
              dataIndex: "sfdlwcyjspy",
              width: 120,
            },
             {
              title: "培养质量是否优良",
              dataIndex: "pyzlsfyl",
              width: 120,
            },
        {
          title: '教学评分(百分制)76',
          dataIndex: 'jxpf',
          width: 100,
        },
        {
          title: '教学评分等级77',
          dataIndex: 'jxpfdj',
          width: 120
        },
        {
          title: '是否存在师德师风一票否决的情况78',
          dataIndex: 'sdsfypfj2',
          width: 120
        },
        {
          title: '是否担任一年辅导员或班主任并考核合格79',
          dataIndex: 'tutor',
          width: 120
        },
        {
          title: '近五年教学工作在本单位总体评价情况80',
          dataIndex: 'j5njxgz',
          width: 150
        },


        {
          title: '门诊医疗评分81',
          dataIndex: 'mzylpf',
          width: 100,
        },
        {
          title: '门诊医疗评分等级82',
          dataIndex: 'mzylpfdj',
          width: 120
        },
        {
          title: '是否门诊医疗事故一票否决的情况83',
          dataIndex: 'mzylsgypfj',
          width: 150
        },
        {
          title: 'beizhu100',
          dataIndex: 'beizhumenban',
          width: 150
        },

        {
          title: '医疗评分84',
          dataIndex: 'ylpfbfz2022',
          width: 100,
        },
        {
          title: '医疗评分等级85',
          dataIndex: 'ylpfdj2022',
          width: 120
        },
        {
          title: '是否有一票否决的情况86',
          dataIndex: 'sfypfjyl',
          width: 120
        },
        {
          title: '是否具有医师资格证书87',
          dataIndex: 'sfyszgzs',
          width: 120
        },
        {
          title: '备注200',
          dataIndex: 'beizhuyiwuchu',
          width: 120
        },
        {
          title: '医疗评分88',
          dataIndex: 'hlylpf',
          width: 100,
        },
        {
          title: '医疗评分等级89',
          dataIndex: 'hlylpfdj',
          width: 120
        },
        {
          title: '教学评分90',
          dataIndex: 'hljxpfbfz',
          width: 120
        },
        {
          title: '教学评分等级91',
          dataIndex: 'hljxpfdl',
          width: 120
        },
        {
          title: '是否有护理责任事故一票否决的情况92',
          dataIndex: 'hlhlzrypfj',
          width: 120
        },
        {
          title: '是否具有护理资格证书93',
          dataIndex: 'sfjyhlzgzs',
          width: 120
        },
        {
          title: 'hulinote',
          dataIndex: 'beizhuhuli',
          width: 120
        },
        {
          title: '是否存在收受红包的有效投诉94',
          dataIndex: 'sshbdts',
          width: 100,
        },
        {
          title: '是否存在收受回扣的有效投诉95',
          dataIndex: 'sshkyxts',
          width: 120
        },
        {
          title: '是否违反湖北96',
          dataIndex: 'blxwjf',
          width: 200
        },
        {
          title: '是否违反97',
          dataIndex: 'wfzgszcf',
          width: 150
        },
        {
          title: '行风审查是否通过98',
          dataIndex: 'xingfscsftg',
          width: 120,

        },
        {
          title: '是否存在行风建设一99',
          dataIndex: 'sfczxfypfj61',
          width: 150
        },

        {
          title: '专业评分100',
          dataIndex: 'zypf52',
          width: 80,
        },
        {
          title: '专业评分等级101',
          dataIndex: 'zypfdj52',
          width: 100

        },
        {
          title: '材料审核结果',
          dataIndex: 'clshjg',
          width: 100,
          scopedSlots: { customRender: 'clshjg' }
        },

        {
          title: '退审原因',
          dataIndex: 'ntyy',
          width: 150,
          scopedSlots: { customRender: 'ntyy' }
        },
        {
          title: '科室排名55',
          dataIndex: 'ksrank',
          width: 100,
          scopedSlots: { customRender: 'ksrank' }
        },
        {
          title: '教师资格证',
          dataIndex: 'teacherQualify',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '内聘情况',
          dataIndex: 'npqk',
          width: 100,
        },
        {
          title: '出国情况',
          dataIndex: 'borad',
          width: 150,
        },
         {
          title: '任现职以来出国时长（月）',
          dataIndex: 'chuguonianyue',
          width: 60
        },
        {
          title: '支援情况',
          dataIndex: 'help',
          width: 150,
        },
        {
          title: '其他指令性支援情况',
          dataIndex: 'qtzlxzy',
          width: 150,
        },
         {
          title: '支援类型',
          dataIndex: 'zhiyuanchuguo',
          width: 60
        },
        {
          title: '时长（月）',
          dataIndex: 'helpmonth',
          width: 60
        },
        {
          title: '备注',
          dataIndex: 'note',
          width: 100,
        },
         {
          title: '个人亮点自述',
          dataIndex: 'dbxcgbs',
          width: 100,
        },
        {
          title: '联系方式',
          dataIndex: 'telephone',
          width: 100
        }
      ];
      let listj = ['ylpfbfz2022','ylpfdj2022','ydyf', 'ydyffj', 'zzsc', 'zzscypfj', 'jlsc', 'jlscypfj', 'xsddsc', 'xsddscypfj', 'zypfyjxl', 'zypfdjyjxl', 'yyxtsc', 'yyxtypfj', 'zypfbfz58', 'zypfdj59', 'sfssds', 'sfbsds', 'sftgsdsf',
        'sdsfypfj', 'sdsfypfj2', 'mzylsgypfj', 'sfypfjyl', 'sfyszgzs','sfdlwcyjspy','pyzlsfyl',
        'hlylpf', 'hlylpfdj', 'hljxpfbfz', 'hljxpfdl', 'hlhlzrypfj', 'sfjyhlzgzs', 'sshbdts', 'sshkyxts', 'blxwjf', 'wfzgszcf', 'xingfscsftg', 'sfczxfypfj61',
        'zypf52', 'zypfdj52', 'beizhuyiwuchu', 'beizhumenban', 'beizhuhuli'];

      json.forEach(element => {
        if (listj.includes(element.dataIndex)) {
          element["isDynamic"] = 1
        }
      });
      let dataJson = JSON.stringify(json)

      this.$export('dcaUserAudit/excelBigTable', {
        sortField: "paixu1,pingshenfenzu,paixu2,paixu3,paixu4,convert(kspaixu using gbk) collate gbk_chinese_ci,cast(ksrank as unsigned integer) ",
        sortOrder: 'ascend',
        state: this.state,
        excelIndex: 0,
        dataJson: dataJson,
        ...this.queryParams
      })
    },
  },
  computed: {
    columns () {

      let clm = [
        {
          title: '申报年度',
          dataIndex: 'year',
          width: 100
        },
        {
          title: '顺序号',
          dataIndex: 'confirmIndex',
          width: 130,
          scopedSlots: { customRender: 'confirmIndex' }
        },

        {
          title: '报名档案顺序号',
          dataIndex: 'baomingIndex',
          width: 130,
          scopedSlots: { customRender: 'baomingIndex' }
        },
        {
          title: '系列',
          dataIndex: 'xl',
          width: 100
        },
        {
          title: '评审分组',
          dataIndex: 'pingshenfenzu',
          width: 150,
          scopedSlots: { customRender: 'pingshenfenzu' }
        },
        {
          title: '双报标志',
          dataIndex: 'ifshuangbao',
          width: 130
        },
          {
          title: '审核后是否双报',
          dataIndex: 'wchshuangbao',
          width: 100
        },
        {
          title: '人事编号',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' },
        },

        {
          title: '申报等级',
          dataIndex: 'gwdj',
          width: 100
        },
        {
          title: '科室',
          dataIndex: 'ks',
          width: 100
        },
        {
          title: '科室分类',
          dataIndex: 'kslb',
          width: 130,
          scopedSlots: { customRender: 'kslb' }
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '出生年月',
          dataIndex: 'birthdaystr',
          width: 100
        },
        {
          title: '年龄',
          dataIndex: 'age',
          width: 100
        },
        {
          title: '性别',
          dataIndex: 'sexName',
          width: 100
        },
        {
          title: '学历(位)',
          dataIndex: 'edu',
          width: 100
        },
        {
          title: '毕业时间',
          dataIndex: 'eduDate',
          width: 100
        },
        {
          title: '现职务',
          children: [
            {
              title: '现职务名称',
              dataIndex: 'positionName',
              width: 100
            },
            {
              title: '聘任时间',
              dataIndex: 'zygwDate',
              width: 100
            },
          ]
        },

        {
          title: '申报职称',
          dataIndex: 'npPositionName',
          width: 100
        },
        {
          title: '来院时间',
          dataIndex: 'schoolDate',
          width: 100
        },
        {
          title: '必备条件',
          children: [
            {
              title: '是否起带头或骨干作用',
              dataIndex: 'ifdaitou',
              width: 100,
              scopedSlots: { customRender: 'ifdaitou' }
            },
              {
              title: "医疗评价",
              children: [
                {
                  title: "医办",
                  children: [
                    {
                      title: "等级",
                      dataIndex: "ylpfdj2",
                      width: 100,
                      customRender: (text, row, index) => {
                        return row.ylpfdj;
                      },
                    },
                    {
                      title: "评分",
                      dataIndex: "ylpfbfz",
                      width: 80,
                    },
                  ],
                },
                {
                  title: "门办",
                  children: [
                    {
                      title: "等级",
                      dataIndex: "mzylpfdj2",
                      width: 100,
                      customRender: (text, row, index) => {
                        return row.mzylpfdj;
                      },
                    },
                    {
                      title: "评分",
                      dataIndex: "mzylpf",
                      width: 80,
                    },
                  ],
                },
              ],
            },
            {
              title: "临床学院评价",
              children: [
                {
                  title: "等级",
                  dataIndex: "jxpfdj2",
                  width: 100,
                  customRender: (text, row, index) => {
                    return row.jxpfdj;
                  },
                },
                {
                  title: "评分",
                  dataIndex: "jxpf",
                  width: 80,
                },
              ],
            },

            {
              title: '教学科研项目或获奖情况是否符合',
              dataIndex: 'iffuhekeyan',
              width: 100,
              scopedSlots: { customRender: 'iffuhekeyan' }
            },
            {
              title: '第一作者论文情况是否符合',
              dataIndex: 'iffuhediyi',
              width: 100,
              scopedSlots: { customRender: 'iffuhediyi' }
            }
          ]
        },

        {
          title: '是否符合必备条件',
          dataIndex: 'iffuhebibei',
          width: 100,
          scopedSlots: { customRender: 'iffuhebibei' }
        },
        {
          title: '抗疫一线人员情况（是）',
          dataIndex: 'yxryqk',
          width: 80,
          scopedSlots: { customRender: 'yxryqk' }
        },
        {
          title: '选择条件',
          children: [
            {
              title: '1',
              children: [
                {
                  title: '国家、省部级科研奖',
                  children: [
                    {
                      title: '名称',
                      dataIndex: 'sciName',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '等级',
                      dataIndex: 'sciDengji',
                      width: 60,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'sciRanknum',
                      width: 60,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },
              ]
            },
            {
              title: '2',
              children: [
                {
                  title: '国家、省部级教学获奖',
                  children: [
                    {
                      title: '名称',
                      dataIndex: 'teachName',
                      width: 150,
                      scopedSlots: { customRender: 'splitHang' }

                    },
                    {
                      title: '等级',
                      dataIndex: 'teachDengji',
                      width: 80,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'teachRanknum',
                      width: 60,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },
              ]
            },
            {
              title: '3',
              children: [
                {
                  title: '发明专利',
                  children: [{
                    title: '项数',
                    dataIndex: 'patentNum',
                    width: 100,
                    scopedSlots: { customRender: 'splitHang' }
                  },
                  {
                    title: '实施转让费',
                    dataIndex: 'patentFund',
                    width: 100,
                    scopedSlots: { customRender: 'splitHang' }
                  }]
                },
              ]
            },
            {
              title: '4',
              children: [
                {
                  title: '第一作者或通迅作者论文情况',
                  children: [{
                    title: '其中',
                    children: [
                      {
                        title: 'A 类',
                        dataIndex: 'publishA',
                        width: 100
                      },
                      {
                        title: 'B 类',
                        dataIndex: 'publishB',
                        width: 100
                      },
                      {
                        title: 'C 类',
                        dataIndex: 'publishC',
                        width: 100
                      },
                      {
                        title: 'D 类',
                        dataIndex: 'publishD',
                        width: 100
                      },
                      {
                        title: 'E 类',
                        dataIndex: 'publishE',
                        width: 100
                      },
                      {
                        title: 'F 类',
                        dataIndex: 'publishF',
                        width: 100
                      },
                      {
                        title: 'D类以上',
                        dataIndex: 'publishDup',
                        width: 100,
                        scopedSlots: { customRender: 'publishDup' }
                      },
                      {
                        title: 'E类以上',
                        dataIndex: 'publishEup',
                        width: 100,
                        scopedSlots: { customRender: 'publishEup' }
                      },
                      {
                        title: 'F类以上',
                        dataIndex: 'publishFup',
                        width: 100,
                        scopedSlots: { customRender: 'publishFup' }
                      },
                    ]
                  }
                  ]
                },
              ]
            },
            {
              title: '5',
              children: [
                {
                  title: '出版书类别及字数',
                  children: [
                    {
                      title: '出版书类别',
                      dataIndex: 'publicarticle1',
                      width: 100
                    },
                    {
                      title: '承担字数(万)',
                      dataIndex: 'publicarticle2',
                      width: 100
                    },
                     {
                      title: '是否构成选择条件',
                      dataIndex: 'ifgoucheng',
                      width: 100,
                    }
                  ]
                },
              ]
            },
            {
              title: '6',
              children: [
                {
                  title: '教学质量奖与成果奖',
                  children: [
                    {
                      title: '名称',
                      dataIndex: 'schoolprizeName',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '等级',
                      dataIndex: 'schoolprizeDengji',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'schoolprizeRanknum',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '时间',
                      dataIndex: 'schoolprizeDate',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },
                {
                  title: '精品课程',
                  children: [
                    {
                      title: '等级',
                      dataIndex: 'courseDengji',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'courseRanknum',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '时间',
                      dataIndex: 'courseDate',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },

                {
                  title: '教学竞赛获奖',
                  children: [
                    {
                      title: '奖项级别',
                      dataIndex: 'youngName',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '等级',
                      dataIndex: 'youngDengji',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'youngRanknum',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '时间',
                      dataIndex: 'youngDate',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },
              ]
            },
            {
              title: '7',
              children: [
                {
                  title: '科研项目教改项目',
                  children: [
                    {
                      title: '类别',
                      dataIndex: 'sciDjlb',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '经费',
                      dataIndex: 'sciDjfund',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'sciDjranknum',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },]
            },
            {
              title: '8',
              children: [
                {
                  title: '实到校单项科研经费',
                  children: [
                    {
                      title: '类别',
                      dataIndex: 'sciJflb',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '经费',
                      dataIndex: 'sciJffund',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'sciJfranknum',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },]
            },
            {
              title: '9',
              children: [
               {
                  title: "医疗评价",
                  children: [
                    {
                      title: "医办",
                      children: [
                        {
                          title: "等级",
                          dataIndex: "ylpfdj",
                          width: 100,
                        },
                        {
                          title: "评分",
                          dataIndex: "ylpfbfz2",
                          width: 80,
                          customRender: (text, row, index) => {
                            return row.ylpfbfz;
                          },
                        },
                      ],
                    },
                    {
                      title: "门办",
                      children: [
                        {
                          title: "等级",
                          dataIndex: "mzylpfdj",
                          width: 100,
                        },
                        {
                          title: "评分",
                          dataIndex: "mzylpf2",
                          width: 80,
                          customRender: (text, row, index) => {
                            return row.mzylpf;
                          },
                        },
                      ],
                    },
                  ],
                },
                {
                  title: "临床学院评价",
                  children: [
                    {
                      title: "等级",
                      dataIndex: "jxpfdj",
                      width: 100,
                    },
                    {
                      title: "评分",
                      dataIndex: "jxpf2",
                      width: 80,
                      customRender: (text, row, index) => {
                        return row.jxpf;
                      },
                    },
                  ],
                },
                {
                  title: '评分合计',
                  dataIndex: 'pfHeji',
                  width: 100
                },
              ]
            },]
        },
        {
          title: '是否担任一年辅导员或班主任',
          dataIndex: 'tutor',
          width: 100
        },
        {
          title: '近五年教学工作在本单位总体评价情况（前%）',
          dataIndex: 'j5njxgz34',
          width: 200,
          customRender: (text, row, index) => {
            return row.j5njxgz
          }
        },
        {
          title: '是否有校级及以上教学竞赛奖励',
          dataIndex: 'ifxjjl',
          width: 120,
          scopedSlots: { customRender: 'ifxjjl' }
        },
        {
          title: '是否有连续一年以上出国学习经历',
          dataIndex: 'yearmore',
          width: 200,
        },
        {
          title: '申报类型',
          dataIndex: 'sblx',
          width: 100,
          scopedSlots: { customRender: 'sblx' }
        },
        {
          title: '达到选择条件一之第几条',
          dataIndex: 'choosepos',
          width: 100,
          scopedSlots: { customRender: 'choosepos' }
        },
        {
          title: '部门审核结果',
          dataIndex: 'auditMan',
          width: 100,
          scopedSlots: { customRender: 'auditMan' }
        },
        {
          title: '党办',
          children: [
            {
              title: '是否通过医德医风审核',
              dataIndex: 'ydyf',
              width: 100,
              customRender: (text, row, index) => {
                switch (text) {
                  case '是':
                    return '通过'
                  case '否':
                    return '不通过'
                  default:
                    return ''
                }
              }
            },
            {
              title: '是否存在医德医风一票否决的情况',
              dataIndex: 'ydyffj',
              width: 120
            }
          ]
        },
        {
          title: '党委组织部',
          children: [
            {
              title: '是否通过政治综合评价',
              dataIndex: 'zzsc',
              width: 100,
              customRender: (text, row, index) => {
                switch (text) {
                  case '是':
                    return '通过'
                  case '否':
                    return '不通过'
                  default:
                    return ''
                }
              }
            },
            {
              title: '是否存在政治综合评价一票否决的情况',
              dataIndex: 'zzscypfj',
              width: 120
            }
          ]
        },
        {
          title: '纪委办公室',
          children: [
            {
              title: '是否通过纪律审查',
              dataIndex: 'jlsc',
              width: 100,
              customRender: (text, row, index) => {
                switch (text) {
                  case '是':
                    return '通过'
                  case '否':
                    return '不通过'
                  default:
                    return ''
                }
              }
            },
            {
              title: '是否存在纪律审查一票否决的情况',
              dataIndex: 'jlscypfj',
              width: 120
            }
          ]
        },
        {
          title: '科研处',
          children: [
            {
              title: '是否通过学术道德审查',
              dataIndex: 'xsddsc',
              width: 100,
              customRender: (text, row, index) => {
                switch (text) {
                  case '是':
                    return '通过'
                  case '否':
                    return '不通过'
                  default:
                    return ''
                }
              }
            },
            {
              title: '是否存在学术道德一票否决的情况',
              dataIndex: 'xsddscypfj',
              width: 120
            },
            {
              title: '专业评分（仅研究系列需要填写）(百分制)',
              dataIndex: 'zypfyjxl',
              width: 150,
            },
            {
              title: '专业评分等级（仅研究系列需要填写）',
              dataIndex: 'zypfdjyjxl',
              width: 150
            }
          ]
        },
        {
          title: '宣传部',
          children: [
            {
              title: '是否通过意识形态审查',
              dataIndex: 'yyxtsc',
              width: 100,
              customRender: (text, row, index) => {
                switch (text) {
                  case '是':
                    return '通过'
                  case '否':
                    return '不通过'
                  default:
                    return ''
                }
              }
            },
            {
              title: '是否存在意识形态一票否决的情况',
              dataIndex: 'yyxtypfj',
              width: 120
            },
            {
              title: '专业评分（仅宣传部职工需要填写）(百分制）',
              dataIndex: 'zypfbfz58',
              width: 150,
            },
            {
              title: '专业评分等级（仅宣传部职工需要填写）',
              dataIndex: 'zypfdj59',
              width: 150
            }
          ]
        },
        {
          title: '研究生管理办',
          children: [
            {
              title: '是否硕士导师',
              dataIndex: 'sfssds',
              width: 100,
            },
            {
              title: '是否博士导师',
              dataIndex: 'sfbsds',
              width: 120
            },
            {
              title: '是否通过师德师风审查',
              dataIndex: 'sftgsdsf',
              width: 120,
              customRender: (text, row, index) => {
                switch (text) {
                  case '是':
                    return '通过'
                  case '否':
                    return '不通过'
                  default:
                    return ''
                }
              }
            },
            {
              title: '是否存在师德师风一票否决的情况',
              dataIndex: 'sdsfypfj',
              width: 120
            },
             {
              title: "是否独立完成一届以上研究生培养工作（截止申报年度10月31日）",
              dataIndex: "sfdlwcyjspy",
              width: 120,
            },
             {
              title: "培养质量是否优良",
              dataIndex: "pyzlsfyl",
              width: 120,
            }
          ]
        },
        {
          title: '教学办公室',
          children: [
            {
              title: '教学评分(百分制)',
              dataIndex: 'jxpf',
              width: 100,
            },
            {
              title: '教学评分等级',
              dataIndex: 'jxpfdj',
              width: 120
            },
            {
              title: '是否存在师德师风一票否决的情况',
              dataIndex: 'sdsfypfj2',
              width: 120
            },
            {
              title: '是否担任一年辅导员或班主任并考核合格',
              dataIndex: 'tutor',
              width: 120
            },
            {
              title: '近五年教学工作在本单位总体评价情况（前%）',
              dataIndex: 'j5njxgz',
              width: 150
            },
          ]
        },
        {
          title: '门诊办公室',
          children: [
            {
              title: '门诊医疗评分(百分制)',
              dataIndex: 'mzylpf',
              width: 100,
            },
            {
              title: '门诊医疗评分等级',
              dataIndex: 'mzylpfdj',
              width: 120
            },
            {
              title: '是否门诊医疗事故一票否决的情况',
              dataIndex: 'mzylsgypfj',
              width: 150
            },
            {
              title: '备注',
              dataIndex: 'beizhumenban',
              width: 150
            },
          ]
        },
        {
          title: '医务处',
          children: [
            {
              title: '医疗评分(百分制)',
              dataIndex: 'ylpfbfz2022',
              width: 100,
            },
            {
              title: '医疗评分等级',
              dataIndex: 'ylpfdj2022',
              width: 120
            },
            {
              title: '是否有一票否决的情况',
              dataIndex: 'sfypfjyl',
              width: 120
            },
            {
              title: '是否具有医师资格证书',
              dataIndex: 'sfyszgzs',
              width: 120
            },
            {
              title: '备注',
              dataIndex: 'beizhuyiwuchu',
              width: 150
            },
          ]
        },
        {
          title: '护理部',
          children: [
            {
              title: '医疗评分(百分制)',
              dataIndex: 'hlylpf',
              width: 100,
            },
            {
              title: '医疗评分等级',
              dataIndex: 'hlylpfdj',
              width: 120
            },
            {
              title: '教学评分(百分制)',
              dataIndex: 'hljxpfbfz',
              width: 120
            },
            {
              title: '教学评分等级',
              dataIndex: 'hljxpfdl',
              width: 120
            },
            {
              title: '是否有护理责任事故一票否决的情况',
              dataIndex: 'hlhlzrypfj',
              width: 120
            },
            {
              title: '是否具有护理资格证书',
              dataIndex: 'sfjyhlzgzs',
              width: 120
            },
            {
              title: '备注',
              dataIndex: 'beizhuhuli',
              width: 150
            },
          ]
        },
        {
          title: '行风建设办公室',
          children: [
            {
              title: '是否存在收受红包的有效投诉',
              dataIndex: 'sshbdts',
              width: 100,
            },
            {
              title: '是否存在收受回扣的有效投诉',
              dataIndex: 'sshkyxts',
              width: 120
            },
            {
              title: '是否违反湖北省医务人员不良执业行为记分管理办法，并被卫生健康部门一次记12分、6分',
              dataIndex: 'blxwjf',
              width: 200
            },
            {
              title: '是否违反《职工守则》有关行风建设规定并被处罚',
              dataIndex: 'wfzgszcf',
              width: 150
            },
            {
              title: '行风审查是否通过',
              dataIndex: 'xingfscsftg',
              width: 120,
              customRender: (text, row, index) => {
                switch (text) {
                  case '是':
                    return '通过'
                  case '否':
                    return '不通过'
                  default:
                    return ''
                }
              }
            },
            {
              title: '是否存在行风建设一票否决的情况',
              dataIndex: 'sfczxfypfj61',
              width: 150
            },
          ]
        },
        {
          title: '科室负责人',
          children: [
            {
              title: '专业评分',
              dataIndex: 'zypf52',
              width: 80,
            },
            {
              title: '专业评分等级',
              dataIndex: 'zypfdj52',
              width: 100
            }
          ]
        },
        {
          title: '材料审核结果',
          dataIndex: 'clshjg',
          width: 100,
          scopedSlots: { customRender: 'clshjg' }
        },
        {
          title: '退审原因',
          dataIndex: 'ntyy',
          width: 250,
          scopedSlots: { customRender: 'ntyy' }
        },
        {
          title: '科室排名',
          dataIndex: 'ksrank',
          width: 100,
          scopedSlots: { customRender: 'ksrank' }
        },
        {
          title: '教师资格证',
          dataIndex: 'teacherQualify',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '内聘情况',
          dataIndex: 'npqk',
          width: 100,
          scopedSlots: { customRender: 'npqk' }
        },
        {
          title: '出国情况',
          dataIndex: 'borad',
          width: 150,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '任现职以来出国时长（月）',
          dataIndex: 'chuguonianyue',
          width: 120
        },
        {
          title: '任现职以来参加指令性支援情况',
          children: [
            {
              title: '援疆援藏援非援滇',
              dataIndex: 'help',
              width: 170,
              scopedSlots: { customRender: 'splitHang' }
            },
            {
              title: '其他指令性支援情况',
              dataIndex: 'qtzlxzy',
              width: 170,
              scopedSlots: { customRender: 'splitHang' }
            },
            {
              title: '支援类型',
              dataIndex: 'zhiyuanchuguo',
              scopedSlots: { customRender: 'splitHang' },
              width: 100
            },
            {
              title: '时长（月）',
              dataIndex: 'helpmonth',
              scopedSlots: { customRender: 'splitHang' },
              width: 60
            }
          ]
        },
        {
          title: '备注',
          dataIndex: 'note',
          width: 100,
          scopedSlots: { customRender: 'note' }
        },
         {
          title: '个人亮点自述',
          dataIndex: 'dbxcgbs',
          width: 100,
        },
        {
          title: '联系方式',
          dataIndex: 'telephone',
          width: 100
        },
        {
          title: '操作',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' },

          width: 150
        }


      ]
      // if(this.state==1){
      //   clm.push({
      //     title: '操作',
      //     dataIndex: 'action',
      //     scopedSlots: { customRender: 'action' },

      //     width: 100
      //   })
      // }
      let filtersCls = ['confirmIndex', 'pingshenfenzu', 'kslb', 'iffuhebibei', 'sblx', 'choosepos', 'auditMan', 'clshjg', 'ntyy', 'ksrank', 'note']
      let permissions = this.$store.state.account.permissions
      //console.info(permissions)
      if (permissions.includes('dca:audit')) {
        clm = clm.filter(p => !filtersCls.includes(p.dataIndex));
      }
      return clm
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>