<template>
  <a-tree-select
    :allowClear="true"
    :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
    :treeData="deptTreeData"
    v-model="value">
  </a-tree-select>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'DetpInputTree',
  data () {
    return {
      value: undefined
    }
  },
  computed:{
    ...mapState({
      deptTree: state => state.sys.deptTree
    }),
    deptTreeData(){
      if(this.deptTree){
        return this.deptTree.children
      }
      return []
    }
  },
  methods: {
    reset () {
      this.value = ''
    }
  },
  mounted () {
  },
  watch: {
    value (value) {
      this.$emit('change', value)
    }
  }
}
</script>