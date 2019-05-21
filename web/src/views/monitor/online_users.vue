/**
 * 在线用户
 */
/**
 * 运行系统
 */

<template>
  
    <a-card title="在线用户">
      <a-skeleton active :loading="loading" :paragraph="{rows: 8}">
        <a-table size="middle" rowKey="id" :columns="columns" :dataSource="onlineusers">
          <template slot="operation" slot-scope="text, record, index">
            <a-spin v-if="deling">
              <a-icon slot="indicator" type="loading" style="font-size: 24px" spin />
            </a-spin>
            <a-popconfirm title="是否强制该用户退出？" @confirm="remove(record.username)" v-else>
              <a>强制退出</a>
            </a-popconfirm>
          </template>
        </a-table>
        
      </a-skeleton>
    </a-card>
  
</template>

<script>
import * as api from '@/api/base'
import moment from 'moment'
moment.locale('zh-cn')
export default {
  components: {
  },
  data () {
    return {
      loading: false,
      deling: false,
      onlineusers:[],
      columns:[
         {
          title: '用户名',
          dataIndex: 'username',
        },
         {
          title: '登陆时间',
          dataIndex: 'created',
          customRender: (text) => moment(text).format('YYYY-MM-DD HH-mm-ss')
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: { customRender: 'operation' }
        }
      ]
    }
  },
  created () {
    this.reload()
   
  },
  methods: {
    reload(){
      this.loading = true
      api.htpp.get(`/system/onlineusers`).then(data => {
        this.onlineusers = data
      }).catch(err => {
        console.error(err)
        this.$message.error(`获取数据失败，${err.message}。`)
      })
    },
    remove(name){
      if(name === null || typeof name === 'undefined'){
        this.$message.
        return
      }
      api.http.post(`/system/dropuser`, { usernames: [name]})
        .then(data => {
          if(data === null || typeof data === 'undefined'){
            this.$message.error(`强制该用户退出失败`)
          }else{
            this.$message.success(`操作成功！`)
            this.reload()
          }
        }).catch(err => {
          console.error(err)
          this.$message.error(`强制该用户退出失败，${err.message}`)
        })
    }
  }
}
</script>

<style>

</style>
