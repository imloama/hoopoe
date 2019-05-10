<template>
  <div class="account-security">
    <a-row :gutter="16">
      <a-col :md="24" :lg="16">
        <a-form layout="vertical">
          <a-form-item
            label="原密码"
          >
            <a-input v-model="originpassword" type="password" placeholder="请输入原密码" />
          </a-form-item>
         <a-form-item
            label="新密码"
          >
            <a-input v-model="newpassword" type="password" placeholder="请输入新密码" />
          </a-form-item>
           <a-form-item
            label="再输一次新密码"
          >
            <a-input v-model="newpassword2" type="password" placeholder="请再输一次新密码" />
          </a-form-item>

          <a-form-item>
            <a-button type="primary" :loading="loading" @click="sumbit">提交</a-button>
          </a-form-item>
        </a-form>

      </a-col>
      

    </a-row>
  </div>
</template>

<script>
import * as api from '@/api/base'

export default {
  data () {
    return {
      originpassword: null,
      newpassword: null,
      newpassword2: null,
      loading: false
    }
  },
  created () {
    this.originpassword = null
    this.newpassword = null
    this.newpassword2 = null
  },
  methods: {
    submit(){
      if(this.loading )return
      if(this.originpassword === null || this.newpassword === null || this.newpassword2 === null || this.newpassword!==this.newpassword2){
        this.$message.error('请填写完整参数！')
        return
      }
      this.loading = true
      api.post(`/users/resetpwd`, {password: this.originpassword, newpassword: this.newpassword, newpassword2: this.newpassword2}).then(data => {
        if(data){
          this.$message.success('密码更新成功！')
        }else{
          this.$message.error('密码更新失败！')
        }
        this.loading = false
      }).catch(err => {
        this.loading = false
        this.$message.error('密码更新失败！')
      })
    }
  }
}
</script>
