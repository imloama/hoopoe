/**
 * 数据库监控
 */

<template>
  <a-card>
    <a-tabs defaultActiveKey="1">
      <a-tab-pane :tab="db.Name" v-for="db in dbs" :key="db.Identity">
      
        <detail-list :col="1">
          <detail-list-item term="数据库">{{db.DbType}}</detail-list-item>
          <detail-list-item term="JDBC连接字符串">{{db.URL}}</detail-list-item>
          <detail-list-item term="用户名">{{db.UserName}}</detail-list-item>
          <detail-list-item term="驱动类名">{{db.DriverClassName}}</detail-list-item>
          <detail-list-item term="filter类名">{{db.FilterClassNames}}</detail-list-item>
          <detail-list-item term="获取连接时检测">{{db.TestOnBorrow}}</detail-list-item>
          <detail-list-item term="空闲时检测">{{db.TestWhileIdle}}</detail-list-item>
          <detail-list-item term="连接放回连接池时检测">{{db.TestOnReturn}}</detail-list-item>
          <detail-list-item term="初始化连接大小">{{db.InitialSize}}</detail-list-item>
          <detail-list-item term="最小空闲连接数">{{db.MinIdle}}</detail-list-item>
          <detail-list-item term="最大连接数">{{db.MaxActive}}</detail-list-item>
          <detail-list-item term="查询超时时间">{{db.QueryTimeout}}</detail-list-item>
          <detail-list-item term="事务查询超时时间">{{db.TransactionQueryTimeout}}</detail-list-item>
          <detail-list-item term="默认autocommit设置">{{db.DefaultAutoCommit}}</detail-list-item>
          <detail-list-item term="默认只读设置">{{db.DefaultReadOnly}}</detail-list-item>
          <detail-list-item term="默认事务隔离">{{db.DefaultTransactionIsolation}}</detail-list-item>
          <detail-list-item term="等待次数">{{db.NotEmptyWaitCount}}</detail-list-item>
          <detail-list-item term="等待最大时长">{{db.NotEmptyWaitMillis}}</detail-list-item>
          <detail-list-item term="等待线程数量">{{db.WaitThreadCount}}</detail-list-item>
          <detail-list-item term="事务启动数">{{db.StartTransactionCount}}</detail-list-item>
          <detail-list-item term="事务时间分布">{{db.TransactionHistogram}}</detail-list-item>
          <detail-list-item term="池中连接数">{{db.PoolingCount}}</detail-list-item>
          <detail-list-item term="池中连接数峰值">{{db.PoolingPeak}}</detail-list-item>
          <detail-list-item term="池中连接数峰值时间">{{db.PoolingPeakTime}}</detail-list-item>
          <detail-list-item term="活跃连接数">{{db.ActiveCount}}</detail-list-item>
          <detail-list-item term="活跃连接数峰值">{{db.ActivePeak}}</detail-list-item>
          <detail-list-item term="活跃连接数峰值时间">{{db.ActivePeakTime}}</detail-list-item>
          <detail-list-item term="逻辑连接打开次数">{{db.LogicConnectCount}}</detail-list-item>
          <detail-list-item term="逻辑连接关闭次数">{{db.LogicCloseCount}}</detail-list-item>
          <detail-list-item term="逻辑连接错误次数">{{db.LogicConnectErrorCount}}</detail-list-item>
          <detail-list-item term="物理连接打开次数">{{db.PhysicalConnectCount}}</detail-list-item>
          <detail-list-item term="物理关闭数量">{{db.PhysicalCloseCount}}</detail-list-item>
          <detail-list-item term="物理连接错误次数">{{db.PhysicalConnectErrorCount}}</detail-list-item>
          <detail-list-item term="执行数(总共)">{{db.ExecuteCount}}</detail-list-item>
          <detail-list-item term="错误数">{{db.ErrorCount}}</detail-list-item>
          <detail-list-item term="提交数">{{db.CommitCount}}</detail-list-item>
          <detail-list-item term="回滚数">{{db.RollbackCount}}</detail-list-item>
          <detail-list-item term="PSCache访问次数">{{db.PSCacheAccessCount}}</detail-list-item>
          <detail-list-item term="PSCache命中次数">{{db.PSCacheHitCount}}</detail-list-item>
          <detail-list-item term="PSCache不命中次数">{{db.PSCacheMissCount}}</detail-list-item>
          <detail-list-item term="连接持有时间分布">{{db.ConnectionHoldTimeHistogram}}</detail-list-item>
          <detail-list-item term="Clob打开次数">{{db.ClobOpenCount}}</detail-list-item>
          <detail-list-item term="Blob打开次数">{{db.BlobOpenCount}}</detail-list-item>
          <detail-list-item term="KeepAlive检测次数">{{db.KeepAliveCheckCount}}</detail-list-item>
        </detail-list>
        <a-table v-if="db.sqls.length>0" 
            size="middle" rowKey="ID" :columns="columns" :dataSource="db.sqls"></a-table>
        
      </a-tab-pane>
    </a-tabs>
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
  data(){
    return {
      dbs: [],
      columns:[
         {
          title: 'SQL',
          dataIndex: 'SQL',
          width: 200
        },
         {
          title: '读取行数',
          dataIndex: 'FetchRowCount',
        },
         {
          title: '最慢发生时间',
          dataIndex: 'MaxTimespanOccurTime',
        },
         {
          title: '最慢',
          dataIndex: 'MaxTimespan',
        },
        {
          title: '执行数(总共)',
          dataIndex: 'ExecuteCount',
        }
      ]
    }
  },
  created () {
    this.reload()
  },
  methods: {
    reload(){
      api.http.get(`/druid/info`).then(data => {
        this.dbs = data
      }).catch(err => {
        console.error(err)
        this.$message.error(`发生错误：${err.message}`)
      })
    }
  }

}
</script>

<style>

</style>
