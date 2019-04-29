<template>
  <a-modal
    v-model="visiable"
    :centered="true"
    :keyboard="false"
    :footer="null"
    :width="750"
    @ok="handleCancleClick"
    title="角色信息">
    <a-layout class="user-info">
      <a-layout-sider class="user-info-side">
        <a-avatar shape="square" :size="115" icon="user" :src="`static/avatar/${userInfoData.avatar}`"/>
      </a-layout-sider>
      <a-layout-content class="user-content-one">
        <p><a-icon type="user"/>账户：{{userInfoData.name}}</p>
        <p><a-icon type="star"/>角色：
          <span v-if="userInfoData.roles && userInfoData.roles.length > 0">
            <span v-for="item in userInfoData.roles" :key="item.id">{{item.name}}</span>
          </span>
          <span v-else>没有角色</span>
        </p>
        <p><a-icon type="skin"/>性别：{{sex}}</p>
        <p><a-icon type="phone"/>电话：{{userInfoData.mobile ? userInfoData.mobile : '暂未绑定电话'}}</p>
        <p><a-icon type="mail"/>邮箱：{{userInfoData.email ? userInfoData.email : '暂未绑定邮箱'}}</p>
      </a-layout-content>
      <a-layout-content class="user-content-two">
        <p><a-icon type="home"/>部门：{{userInfoData.deptName ? userInfoData.deptName : '暂无部门信息'}}</p>
        <p>
          <a-icon type="smile" v-if="userInfoData.status === 0"/>
          <a-icon type="frown" v-else/>状态：
          <template v-if="userInfoData.status === 1">
            <a-tag color="red">锁定</a-tag>
          </template>
          <template v-else-if="userInfoData.status === 0">
            <a-tag color="cyan">有效</a-tag>
          </template>
          <template v-else>
            {{userInfoData.status}}
          </template>
        </p>
        <p :title="userInfoData.memo"><a-icon type="message"/>备注：{{userInfoData.memo}}</p>
      </a-layout-content>
    </a-layout>
  </a-modal>
</template>
<script>
export default {
  name: 'UserInfo',
  data(){
    return {
      visiable: false
    }
  },
  props: {
    userInfoData: {
      require: true
    }
  },
  created(){
  },
  methods: {
    setData(result){
      const role = result.role;
      const menus = result.menus.children;//tree
    },
    handleCancleClick () {
      this.$emit('close')
    }
  }
}
</script>
<style lang="less" scoped>
</style>