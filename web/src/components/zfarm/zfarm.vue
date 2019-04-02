/**
 * 实现内容参考List/TableList.vue实现
 */

<template>
  <div class="zfarm-wrapper">

    <!--1. 查询条件-->
    <div class="table-search-wrapper">

    </div>

    <!--2. 操作按钮区-->

    <!--3. 表格区 及每行数据操作-->

    <!--4. 新增-->

    <!--4. 修改-->
    

  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'ZFarm',
  props: {
    api:{
      type:String,
      required: true
    }
  },

}
</script>

<style>

</style>
