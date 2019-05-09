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
            <a-button type="primary">提交</a-button>
            <a-button style="margin-left: 8px">保存</a-button>
          </a-form-item>
        </a-form>

      </a-col>
      

    </a-row>
  </div>
</template>

<script>
export default {
  data () {
    return {
      originpassword: null,
      newpassword: null,
      newpassword2: null,
    }
  }
}
</script>

<style scoped>

</style>
