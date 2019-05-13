/**
 * redis监控
 */

<template>
<a-card>
  <detail-list title="redis服务器信息" :col="1">
    <detail-list-item term="redis版本">{{info.redis_version}}</detail-list-item>
    <detail-list-item term="运行模式，单机（standalone）或者集群（cluster）">{{info.redis_mode}}</detail-list-item>
    <detail-list-item term="操作系统">{{info.os}}</detail-list-item>
    <detail-list-item term="架构（32 或 64 位）">{{info.arch_bits}}</detail-list-item>
    <detail-list-item term="PID">{{info.process_id}}</detail-list-item>
    <detail-list-item term="端口">{{info.tcp_port}}</detail-list-item>
    <detail-list-item term="运行天数">{{info.uptime_in_days}}</detail-list-item>
    <detail-list-item term="客户端的数量">{{info.connected_clients}}</detail-list-item>
    <detail-list-item term="内存总量">{{info.used_memory_human}}</detail-list-item>
    <detail-list-item term="系统总内存">{{info.total_system_memory_human}}</detail-list-item>
    <detail-list-item term="命中次数">{{info.keyspace_hits}}</detail-list-item>
    <detail-list-item term="没命中次数">{{info.keyspace_misses}}</detail-list-item>
    <detail-list-item term="key数量">{{info.keys_size}}</detail-list-item>
  </detail-list>
  </a-card>
</template>

<script>
import * as api from '@/api/base'
import DetailList from '@/components/tools/DetailList'
const DetailListItem = DetailList.Item
export default {
  components: {
    DetailList,
    DetailListItem,
  },
  data () {
    return {
      info: {}
    }
  },
  created () {
    this.reload()
  },
  methods: {
    reload(){
      api.http.get(`/redis/info`).then(data => {
        this.info = data
      }).catch(err => {
        console.error(err)
      })
    }
  }

}
</script>

<style>

</style>
