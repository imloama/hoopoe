<template>
  <a-tree-select
    :allowClear="true"
    :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
    :treeData="deptTree"
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
    })
  },
  methods: {
    ...mapActions(['getDeptTree']),
    reset () {
      this.value = ''
    }
  },
  created () {
    this.getDeptTree().then(()=>{}).catch(err=>console.log(err))
  },
  watch: {
    value (value) {
      this.$emit('change', value)
    }
  }
}
</script>