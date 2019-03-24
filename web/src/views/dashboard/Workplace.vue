/**
 * 工作台
 * 1. 日历，天气预报
 * 2. project 本日工作，本周工作，进度，
 * 3. OA 邮件，邮件支持自定义SMTP POP3等协议，用于收发邮件
 * 4. 通知、消息公告
 * 5. Todo，自定义的
 */

<template>
  <page-layout>
   

    <div>工作台</div>

  </page-layout>
</template>

<script>
import { mapGetters } from 'vuex'
import PageLayout from '@/components/page/PageLayout'
import HeadInfo from '@/components/tools/HeadInfo'

export default {
  name: 'Workplace',
  components: {
    PageLayout,
    HeadInfo
  },
  data () {
    return {
      avatar: ''
    }
  },
  computed: {
    userInfo () {
      return this.$store.getters.userInfo
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    ...mapGetters(['nickname', 'welcome'])
  }
}
</script>

<style lang="less" scoped>

</style>
