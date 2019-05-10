<template>
  <div class="account-settings-info-view">
    <a-row :gutter="16">
      <a-col :md="24" :lg="16">

        <a-form layout="vertical">
          <a-form-item
            label="昵称"
          >
            <a-input v-model="nickname" placeholder="给自己起个名字" />
          </a-form-item>
         <a-form-item
            label="真实姓名"
          >
            <a-input v-model="realName" placeholder="真实姓名" />
          </a-form-item>
          <a-form-item
            label="手机号"
          >
            <a-input v-model="mobile" placeholder="手机号" />
          </a-form-item>
          <a-form-item
            label="性别"
          >
            <a-radio-group v-model="sex">
              <a-radio value="1">男</a-radio>
              <a-radio value="0">女</a-radio>
              <a-radio value="2">保密</a-radio>
            </a-radio-group>
          </a-form-item>

          <a-form-item
            label="电子邮件"
            :required="false"
          >
            <a-input v-model="email" placeholder="exp@admin.com"/>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="submit" :loading="loading">提交</a-button>
          </a-form-item>
        </a-form>

      </a-col>
      

    </a-row>

    <avatar-modal ref="modal">

    </avatar-modal>
  </div>
</template>

<script>
import AvatarModal from './AvatarModal'
import { mapState, mapActions } from 'vuex'
import * as api from '@/api/base'

export default {
  components: {
    AvatarModal
  },
  data () {
    return {
      nickname: null,
      email: null,
      sex: null,
      mobile: null,
      loading: false,
      // cropper
      preview: {},
      option: {
        img: '/avatar2.jpg',
        info: true,
        size: 1,
        outputType: 'jpeg',
        canScale: false,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 180,
        autoCropHeight: 180,
        fixedBox: true,
        // 开启宽度和高度比例
        fixed: true,
        fixedNumber: [1, 1]
      }
    }
  },
  computed: {
    ...mapState({
      info: state => state.user.info
    })
  },
  created(){
    this.nickname = this.info.nickname
    this.email = this.info.email
    this.sex = this.info.sex
    this.mobile = this.info.mobile
    this.realName = this.info.realName
  },
  methods: {
    submit(){
      if(this.loading)return;
      if(this.nickname === null || this.email === null || this.sex === null || this.mobile === null){
        this.$message.error('请填写参数！')
        return
      }
      this.loading = true
      api.http.post(`/users/updateinfo`, {nickname: this.nickname, email: this.email, sex: this.sex, mobile: this.mobile, realName: this.realName}).then(data => {
        this.loading = false
        if(data){
          this.$message.success('更新成功！')
        }
      }).catch(err => {
        this.loading = false
        this.$message.error('更新失败：'+ err.message)
      })
    }
  }
}
</script>

<style lang="less" scoped>

  .avatar-upload-wrapper {
    height: 200px;
    width: 100%;
  }

  .ant-upload-preview {
    position: relative;
    margin: 0 auto;
    width: 100%;
    max-width: 180px;
    border-radius: 50%;
    box-shadow: 0 0 4px #ccc;

    .upload-icon {
      position: absolute;
      top: 0;
      right: 10px;
      font-size: 1.4rem;
      padding: 0.5rem;
      background: rgba(222, 221, 221, 0.7);
      border-radius: 50%;
      border: 1px solid rgba(0, 0, 0, 0.2);
    }
    .mask {
      opacity: 0;
      position: absolute;
      background: rgba(0,0,0,0.4);
      cursor: pointer;
      transition: opacity 0.4s;

      &:hover {
        opacity: 1;
      }

      i {
        font-size: 2rem;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-left: -1rem;
        margin-top: -1rem;
        color: #d6d6d6;
      }
    }

    img, .mask {
      width: 100%;
      max-width: 180px;
      height: 100%;
      border-radius: 50%;
      overflow: hidden;
    }
  }
</style>
