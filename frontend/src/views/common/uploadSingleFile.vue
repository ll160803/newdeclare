<template>
    <a-upload
      accept=".pdf"
      :fileList="fileList"
      :remove="handleRemove"
      :beforeUpload="beforeUpload"
      @change="handleChange"
    >
      <a-button v-if="fileList.length === 0">
        <a-icon type="upload"  /> 选择文件
      </a-button>
    </a-upload>
</template>
<script>
export default {
  name: "file",
  data () {
    return {
      tstyle: { "color": "#0785fd", "font-weight": "bold", "background-color": "#ececec" },
      isShow: 1,
      fileList: [],
      uploading: false,
      fileId: ''
    }
  },
  props: {
    title: '',

    fileName: '',
    showFileOnly: false
  },
  methods: {
    reset() {
       this.fileId =''
       this.fileList = []
    },
    handleRemove (file) {
      const index = this.fileList.indexOf(file)
      const newFileList = this.fileList.slice()
      newFileList.splice(index, 1)
      this.fileList = newFileList
      this.isShow = 1
      this.$emit("uploadRemove")
    },
    beforeUpload (file) {
      const isJPG = (file.type === 'application/pdf')
      console.info(file.type)
      if (!isJPG) {
        this.$message.error('请只上传pdf文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 20
      if (!isLt2M) {
        this.$message.error('附件必须小于 20MB!')
      }
      if (isJPG && isLt2M) {
        this.fileList = [...this.fileList, file]
      }
      return isJPG && isLt2M;
    },
    handleChange (info) {
      console.info(info.file)
      if (info.file.status === 'uploading') {
        this.handleUpload()
      }
    },
    setForm (fileID) {
      if(fileID!=null&&fileID!=''){
     this.fetch(fileID)
     }
    },
    fetch (fileId) {
      this.fileId = fileId
      this.fileList=[]
      this.$get('comFile/' + fileId).then((r) => {
        let data = r.data
        this.fileUrl = data.url
        data.url = data.url
        this.fileList.push(data)
      })
    },
    handleUpload () {
      const { fileList } = this
      const formData = new FormData()
      formData.append('file', fileList[0])
      this.uploading = true

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        let comfile = r.data.data
        this.fileId = comfile.uid
        var fileServer = comfile.url
        this.fileUrl = comfile.url
        comfile.url= this.fileUrl
        this.fileList=[]
        this.fileList.push(comfile)
        //this.fileList = []
        this.isShow = 0
        this.uploading = false
        this.$message.success('上传成功.')
        this.$emit("uploadSuc",this.fileId,fileServer)
      }).catch((r) => {
        this.fileList= []
        this.fileId = ''
        this.isShow = 1
        this.uploading = false
        this.$message.error('上传失败或加密文件不能上传')
      })
    }
  },
  watch: {

  },
  mounted () {
  }
}
</script>
