<template>
  <div class="login">
    <a-form @submit.prevent="doLogin" :form="form">
      <a-tabs
        size="large"
        :tabBarStyle="{ textAlign: 'center' }"
        style="padding: 0 2px"
        :activeKey="activeKey"
        @change="handleTabsChange"
      >
        <a-tab-pane tab="HRI登录" key="1">
          <a-alert
            type="error"
            :closable="true"
            v-show="error"
            :message="error"
            showIcon
            style="margin-bottom: 24px"
          ></a-alert>
          <a-form-item>
            <a-input
              size="large"
              v-decorator="[
                'name',
                {
                  rules: [
                    {
                      required: true,
                      message: '请输入账户名',
                      whitespace: true,
                    },
                  ],
                },
              ]"
            >
              <a-icon slot="prefix" type="user"></a-icon>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-input
              size="large"
              type="password"
              v-decorator="[
                'password',
                {
                  rules: [
                    { required: true, message: '请输入密码', whitespace: true },
                  ],
                },
              ]"
            >
              <a-icon slot="prefix" type="lock"></a-icon>
            </a-input>
          </a-form-item>
          <!-- <a-form-item>
            <a-row>
              <a-col :span="19">
                <a-input
                  size="large"
                  v-decorator="['verifyCode',{rules: [{ required: true, message: '请输入验证码', whitespace: true}]}]"
                  placeholder="请输入验证码"
                >
                </a-input>
              </a-col>
              <a-col :span="4" :offset="1">
                <img :src="imgUrl" style="width:80px;height:40px" @click="createCode" >
                 <a-tag color="#87d068" @click="createCode">{{ showCode}}</a-tag> 
              </a-col>
            </a-row>
          </a-form-item> -->
        </a-tab-pane>
      </a-tabs>
      <a-form-item>
        <a-button
          :loading="loading"
          style="width: 100%; margin-top: 4px"
          size="large"
          htmlType="submit"
          type="primary"
        >
          登录
        </a-button>
      </a-form-item>
      <a-form-item>
        <div style="width: 100%">
          <a href="#" @click="show">忘记密码</a>
        </div>
      </a-form-item>
      <a-form-item>
        <a-input
          style="width: 60%; float: left"
          v-if="isS == 1"
          v-model="tel"
          placeholder="请输入手机号"
        >
        </a-input>
         <a-button
          v-if="isS == 1"
          :loading="loading"
          style="width: 30%; margin-left: 5px; float: right"
          type="primary"
          @click="sendTelCode"
          :disabled="validShow"
        >
          {{validTitle}}
        </a-button> 
      </a-form-item>
      <a-form-item>
        <a-input
          style="width: 60%; float: left"
          v-if="isS == 1"
          v-model="checkCode"
          placeholder="请输入验证码"
        >
        </a-input>
        <a-button
          :loading="loading"
          v-if="isS == 1"
          style="width: 30%; margin-left: 5px; float: right"
          type="primary"
          @click="resetPas"
        >
          重置密码
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
import SelecteType from "./SelectType";

export default {
  beforeCreate() {
    this.form = this.$form.createForm(this);
  },
  components: { SelecteType },
  name: "Login",
  data() {
    return {
      loading: false,
      error: "",
      activeKey: "1",
      selectVisiable: false,
      checkCode: "",
      showCode: "",
      validTitle: '获取验证码',
      validShow: false,
      tel: "",
      isS: 0,
      imgUrl: "http://localhost:1088/captcha.jpg",
    };
  },
  mounted() {
    //this.createCode()
  },
  computed: {
    systemName() {
      return "华中科技大学同济医学院附属协和医院人力资源信息综合管理平台";
    },
    copyright() {
      return this.$store.state.setting.copyright;
    },
  },
  created() {
    this.$db.clear();
    this.$router.options.routes = [];
  },
  methods: {
    doLogin() {
      if (this.activeKey === "1") {
        // 用户名密码登录
        this.form.validateFields(["name", "password"], (errors, values) => {
          if (!errors) {
            let name = this.form.getFieldValue("name");
            let password = this.form.getFieldValue("password");
            // let verifyCodeActual = this.form.getFieldValue('verifyCode')
            this.loading = true;
            this.$post("login", {
              username: name,
              password: password,
            })
              .then((r) => {
                console.info(r);
                let data = r.data.data;
                this.saveLoginData(data);
                setTimeout(() => {
                  this.loading = false;
                }, 500);
                this.$router.push("/");
              })
              .catch((e) => {
                console.error(e);
                setTimeout(() => {
                  this.loading = false;
                }, 500);
              });
          }
        });
      }
      if (this.activeKey === "2") {
        // 手机验证码登录
        this.$message.warning("暂未开发");
      }
    },
    createCode() {
      // let code = "";
      // let code2="";
      // const codeLength = 4; //验证码的长度
      // const random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
      //   'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); //随机数
      // for (let i = 0; i < codeLength; i++) { //循环操作
      //   let index = Math.floor(Math.random() * 36); //取得随机数的索引（0~35）
      //   code += random[index]; //根据索引取得随机数加到code上
      //   code2 += "        "+random[index];
      // }
      // this.checkCode = code; //把code值赋给验证码
      // this.showCode=code2;
      this.imgUrl =
        "http://localhost:1088/captcha.jpg?index=" +
        Math.floor(Math.random() * 36);
    },
    show() {
      this.isS = 1;
    },
    reduceSec(){
      let mil= 120;
      this.validShow= true;
      var interv= setInterval(() => {
         mil=mil-1;
         this.validTitle = '请查收验证码';
         if(mil==0){
            clearInterval(interv);
           this.validShow= false;
         }
      }, 1000);
      
    },
    sendTelCode() {
      if (this.tel == "") {
        this.$message.warning("请输入手机号!");
      } else {
        this.$post("user/passMess", {
          tel: this.tel,
        }).then((r) => {
           if(r.data.message=='发送成功，请查收'){
              this.reduceSec();
           }
           else {
              this.$message.warning(r.data.message);
              this.$message.warning(r.data.message);
              this.$message.warning(r.data.message);
           }
        });
      }
    },
    resetPas() {
if (this.tel == "") {
        this.$message.warning("请输入手机号!");
      } 
      else if(this.checkCode==''){
        this.$message.warning("请输入验证码!");
      }
      else {
        this.$post("user/resetPass", {
          tel: this.tel,
          code: this.checkCode
        }).then((r) => {
          
          if(r.data.message=='XH+身份证后6位'){
             this.$message.success("密码重置为：XH+身份证后6位")
             this.$message.success("密码重置为：XH+身份证后6位")
             this.$message.success("密码重置为：XH+身份证后6位")
             this.$message.success("密码重置为：XH+身份证后6位")
             this.$message.success("密码重置为：XH+身份证后6位")
          }
          else if(r.data.message=='验证码已过期'){
            this.$message.warning(r.data.message);
          this.$message.warning(r.data.message);
          this.$message.warning(r.data.message);
          //  this.$message.warning(r.data.message);
            this.$message.warning("请重新发送验证码");
          //  this.$message.warning(r.data.message);
            this.$message.warning("请重新发送验证码");
          }
          else {
             this.$message.warning(r.data.message);
          this.$message.warning(r.data.message);
          this.$message.warning(r.data.message);
          }
          this.isS =0 ;
          this.checkCode = '';

        });
      }
    },
    open() {
      this.selectVisiable = true;
    },
    close() {
      this.selectVisiable = false;
    },
    observe(type, vendorId) {
      this.selectVisiable = false;
      if (vendorId == "" || vendorId.length < 32) {
        this.$message.warning("注册码不能为空或注册码位数不对!");
        return false;
      } else {
        this.$emit("regist", "Modify", vendorId);
      }
    },
    regist() {
      this.$emit("regist", "Regist");
    },
    getCaptcha() {
      this.$message.warning("暂未开发");
    },
    handleTabsChange(val) {
      this.activeKey = val;
    },
    ...mapMutations({
      setToken: "account/setToken",
      setExpireTime: "account/setExpireTime",
      setPermissions: "account/setPermissions",
      setRoles: "account/setRoles",
      setUser: "account/setUser",
      setTheme: "setting/setTheme",
      setLayout: "setting/setLayout",
      setMultipage: "setting/setMultipage",
      fixSiderbar: "setting/fixSiderbar",
      fixHeader: "setting/fixHeader",
      setColor: "setting/setColor",
    }),
    saveLoginData(data) {
      this.setToken(data.token);
      this.setExpireTime(data.exipreTime);
      this.setUser(data.user);
      this.setPermissions(data.permissions);
      this.setRoles(data.roles);
      this.setTheme(data.config.theme);
      this.setLayout(data.config.layout);
      this.setMultipage(data.config.multiPage === "1");
      this.fixSiderbar(data.config.fixSiderbar === "1");
      this.fixHeader(data.config.fixHeader === "1");
      this.setColor(data.config.color);
    },
  },
};
</script>

<style lang="less" scoped>
.login {
  .icon {
    font-size: 24px;
    color: rgba(0, 0, 0, 0.2);
    margin-left: 16px;
    vertical-align: middle;
    cursor: pointer;
    transition: color 0.3s;

    &:hover {
      color: #1890ff;
    }
  }
}
</style>
