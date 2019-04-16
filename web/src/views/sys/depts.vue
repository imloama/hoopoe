<template>
  <a-card :bordered="false"  class="depts-wrapper">

    <!--查询条件-->

    <a-table rowKey="key" :columns="columns" 
    :dataSource="deptTree"
    :loading="loading"></a-table>
  </a-card>
</template>
<script>
import { mapState, mapActions } from 'vuex'
export default {
  data () {
    return {
      columns: [
        {
          title: '#',
          dataIndex: 'key'
        },
        {
          title: '名称',
          dataIndex: 'label',
        }
      ],
      loading: false
    }
  },
  computed:{
    ...mapState({
      deptTree: state => state.sys.deptTree
    })
  },
  created () {
    this.loading = true
    this.getDeptTree().then(()=>{
      this.loading = false
    }).catch(err=>{
      console.log(err)
      this.loading = false
    })
  },
  methods: {
    ...mapActions(['getDeptTree']),
  },
  
}
</script>