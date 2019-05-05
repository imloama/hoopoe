<template>
  <a-tree-select
    :allowClear="true"
    :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
    :treeData="menuTree.children"
    placeholder="请选择菜单"
    v-model="value">
  </a-tree-select>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'MenuInputTree',
  data () {
    return {
      value: undefined
    }
  },
  computed:{
    ...mapState({
      menuTree: state => state.sys.menuTree
    })
  },
  methods: {
    ...mapActions(['getMenuTree']),
    reset (val) {
      this.value = val || ''
    }
  },
  created () {
    this.getMenuTree({query: []}).then(()=>{}).catch(err=>console.log(err))
  },
  watch: {
    value (value) {
      this.$emit('change', value)
    }
  }
}
</script>