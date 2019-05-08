<template>
  <a-modal
    v-model="visiable"
    :centered="true"
    :keyboard="false"
    :footer="null"
    :width="750"
    @ok="handleCancleClick"
    title="字典信息">
    <a-layout class="role-info p-a-1">
      <a-layout-content class="role-content-one">
        <p>编码：{{record.code}}</p>
        <p>名称：{{record.name}}</p>
        <p>值：{{record.v}}</p>
        <p>备注：{{record.memo}}</p>
        
      </a-layout-content>
    </a-layout>
  </a-modal>
</template>
<script>
export default {
  data(){
    return {
      visiable: true,
      record: {}
    }
  },
  created(){
    this.visiable = true
  },
  methods: {
    setData(result){
      this.visiable = true
      this.record = result
    },
    handleCancleClick () {
      this.$emit('close')
    },
  }
}
</script>