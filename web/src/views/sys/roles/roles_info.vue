<template>
  <a-modal
    v-model="visiable"
    :centered="true"
    :keyboard="false"
    :footer="null"
    :width="750"
    @ok="handleCancleClick"
    title="角色信息">
    <a-layout class="role-info p-a-1">
      <a-layout-content class="role-content-one">
        <p>编码：{{role.code}}</p>
        <p>名称：{{role.name}}</p>
        <p>备注：{{role.remark}}</p>
        
      </a-layout-content>
      <a-layout-content class="role-content-two">
        <div>权限：</div>
        <div class="p-l-1">
          <menu-tree @check="onMenuTreeCheck" ref="menuTree" checkable :userMenuIds="userMenuIds"/>
        </div>
      </a-layout-content>
    </a-layout>
  </a-modal>
</template>
<script>
import MenuTree from '@/components/sys/menu_tree'
export default {
  name: 'RoleInfo',
  components: {
    MenuTree
  },
  data(){
    return {
      visiable: true,
      role: {},
      menus: [],
      userMenuIds: []
    }
  },
  props: {
    userInfoData: {
      require: true
    }
  },
  created(){
    this.visiable = true
  },
  methods: {
    setData(result){
      this.visiable = true
      this.role = result.role;
      this.menus = result.menus.children;//tree
      const checkedKeys = this.menus.map(m =>this.getMenuIds(m)).reduce((a,b)=> a.concat(b))
      this.userMenuIds = checkedKeys
    },
    handleCancleClick () {
      this.$emit('close')
    },
    getMenuIds(item){
      let child = item.children
      if(child === null || typeof child === 'undefined' || child.length === 0)return [item.key]
      let keys = child.map(c => this.getMenuIds(c)).reduce((a,b)=> a.concat(b))
      return [item.key, ...keys]
    },
    onMenuTreeCheck(){

    }
  }
}
</script>
<style lang="less" scoped>
</style>