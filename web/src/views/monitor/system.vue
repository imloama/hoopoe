/**
 * 运行系统
 */

<template>
  
    <a-card :title="'更新时间：'+time">
      <a-button :loading="loading" slot="extra" @click="reload">刷新</a-button>
      <a-skeleton active :loading="loading" :paragraph="{rows: 17}">
      <detail-list :col="1">
        <a-divider orientation="left">服务器信息</a-divider>
        <detail-list-item term="CPU 数量">{{system.cpu.count}}</detail-list-item>
        <detail-list-item term="系统 CPU 使用率">{{system.cpu.usage}}</detail-list-item>
        <detail-list-item term="应用启动时间点">{{system.process.startTime}}</detail-list-item>
        <detail-list-item term="应用已运行时间">{{system.process.uptime}}</detail-list-item>
        <detail-list-item term="当前应用 CPU 使用率">{{system.process.cpuUsage}}</detail-list-item>
        <a-divider orientation="left">JVM信息</a-divider>
        <detail-list-item term="JVM 最大内存">{{bytesToMB(jvm.memory.max)}}MB</detail-list-item>
        <detail-list-item term="JVM 可用内存">{{bytesToMB(jvm.memory.committed)}}MB</detail-list-item>
        <detail-list-item term="JVM 已用内存">{{bytesToMB(jvm.memory.used)}}MB</detail-list-item>
        <detail-list-item term="JVM 缓冲区已用内存">{{bytesToMB(jvm.buffer.memory.used)}} MB</detail-list-item>
        <detail-list-item term="当前缓冲区数量">{{jvm.buffer.count}} 个</detail-list-item>
        <detail-list-item term="JVM 守护线程数量">{{jvm.threads.daemon}} 个</detail-list-item>
        <detail-list-item term="JVM 当前活跃线程数量">{{jvm.threads.live}} 个</detail-list-item>
        <detail-list-item term="JVM 峰值线程数量">{{jvm.threads.peak}} 个</detail-list-item>
        <detail-list-item term="JVM 已加载 Class 数量">{{jvm.classes.loaded}} 个</detail-list-item>
        <detail-list-item term="JVM 未加载 Class 数量">{{jvm.classes.unloaded}} 个</detail-list-item>
        <detail-list-item term="GC 时, 年轻代分配的内存空间">{{bytesToMB(jvm.gc.memory.allocated)}} MB</detail-list-item>
        <detail-list-item term="GC 时, 老年代分配的内存空间">{{bytesToMB(jvm.gc.memory.promoted)}} MB</detail-list-item>
        <detail-list-item term="GC 时, 老年代的最大内存空间">{{bytesToMB(jvm.gc.maxDataSize)}} MB</detail-list-item>
        <detail-list-item term="FullGC 时, 老年代的内存空间">{{bytesToMB(jvm.gc.liveDataSize)}} MB</detail-list-item>
        <detail-list-item term="系统启动以来GC 次数">{{jvm.gc.pause.count}} 次</detail-list-item>
        <detail-list-item term="系统启动以来GC 总耗时">{{jvm.gc.pause.totalTime}} 秒</detail-list-item>
        <detail-list-item term="当前应用 CPU 使用率">{{system.process.cpuUsage}}</detail-list-item>
        <detail-list-item term="当前应用 CPU 使用率">{{system.process.cpuUsage}}</detail-list-item>
        <detail-list-item term="当前应用 CPU 使用率">{{system.process.cpuUsage}}</detail-list-item>
        <detail-list-item term="当前应用 CPU 使用率">{{system.process.cpuUsage}}</detail-list-item>

        <a-divider orientation="left">Tomcat信息</a-divider>
        <detail-list-item term="已创建 session 数">{{tomcat.sessions.created}} 个</detail-list-item>
        <detail-list-item term="已过期 session 数">{{tomcat.sessions.expired}} 个</detail-list-item>
        <detail-list-item term="当前活跃 session 数">{{tomcat.sessions.active.current}} 个</detail-list-item>
        <detail-list-item term="活跃 session 数峰值">{{tomcat.sessions.active.max}} 个</detail-list-item>
        <detail-list-item term="超过session 最大配置后，拒绝的 session 个数">{{tomcat.sessions.rejected}} 个</detail-list-item>
        <detail-list-item term="发送的字节数">{{tomcat.global.sent}} bytes</detail-list-item>
        <detail-list-item term="request 请求最长耗时">{{tomcat.global.request.max}} 秒</detail-list-item>
        <detail-list-item term="全局 request 请求次数">{{tomcat.global.request.count}} 次</detail-list-item>
        <detail-list-item term="全局 request 请求总耗时">{{tomcat.global.request.totalTime}} 秒</detail-list-item>
        <detail-list-item term="当前线程数（包括守护线程）">{{tomcat.threads.current}} 个</detail-list-item>
        <detail-list-item term="配置的线程最大数">{{tomcat.threads.configMax}} 个</detail-list-item>

      </detail-list>
      </a-skeleton>
    </a-card>
  
</template>

<script>
import * as api from '@/api/base'
import moment from 'moment'
moment.locale('zh-cn')
import DetailList from '@/components/tools/DetailList'
const DetailListItem = DetailList.Item
export default {
  components: {
    DetailList,
    DetailListItem
  },
  data () {
    return {
      time: '',
      loading: true,
      system: {
        cpu: {
          count: 0,
          usage: 0
        },
        process: {
          cpuUsage: 0,
          uptime: 0,
          startTime: 0
        }
      },// end of system
      jvm: {
        memory: {
          max: 0,
          committed: 0,
          used: 0
        },
        buffer: {
          memory: {
            used: 0
          },
          count: 0
        },
        threads: {
          daemon: 0,
          live: 0,
          peak: 0
        },
        classes: {
          loaded: 0,
          unloaded: 0
        },
        gc: {
          memory: {
            allocated: 0,
            promoted: 0
          },
          maxDataSize: 0,
          liveDataSize: 0,
          pause: {
            totalTime: 0,
            count: 0
          }
        }
      },// end of jvm
      tomcat: {
        sessions: {
          created: 0,
          expired: 0,
          active: {
            current: 0,
            max: 0
          },
          rejected: 0
        },
        global: {
          sent: 0,
          request: {
            count: 0,
            max: 0,
            totalTime: 0
          }
        },
        servlet: {
          request: {
            count: 0,
            totalTime: 0,
            max: 0
          }
        },
        threads: {
          current: 0,
          configMax: 0
        }
      },//end of tomcat
    }
  },
  created () {
    this.reload()
   
  },
  methods: {
    reload(){
      this.loading = true
      this.time = moment().format('YYYY年MM月DD日 HH时mm分ss秒')
      const fns = [
        '/actuator/metrics/system.cpu.count',
        '/actuator/metrics/system.cpu.usage',
        '/actuator/metrics/process.uptime',
        '/actuator/metrics/process.start.time',
        '/actuator/metrics/process.cpu.usage',
        //jvm
        '/actuator/metrics/jvm.memory.max',
        '/actuator/metrics/jvm.memory.committed',
        '/actuator/metrics/jvm.memory.used',
        '/actuator/metrics/jvm.buffer.memory.used',
        '/actuator/metrics/jvm.buffer.count',
        '/actuator/metrics/jvm.threads.daemon',
        '/actuator/metrics/jvm.threads.live',
        '/actuator/metrics/jvm.threads.peak',
        '/actuator/metrics/jvm.classes.loaded',
        '/actuator/metrics/jvm.classes.unloaded',
        '/actuator/metrics/jvm.gc.memory.allocated',
        '/actuator/metrics/jvm.gc.memory.promoted',
        '/actuator/metrics/jvm.gc.max.data.size',
        '/actuator/metrics/jvm.gc.live.data.size',
        '/actuator/metrics/jvm.gc.pause',
        //tomecat
        '/actuator/metrics/tomcat.sessions.created',
        '/actuator/metrics/tomcat.sessions.expired',
        '/actuator/metrics/tomcat.sessions.active.current',
        '/actuator/metrics/tomcat.sessions.active.max',
        '/actuator/metrics/tomcat.sessions.rejected',
        '/actuator/metrics/tomcat.global.sent',
        '/actuator/metrics/tomcat.global.request.max',
        '/actuator/metrics/tomcat.global.request',
        '/actuator/metrics/tomcat.threads.current',
        '/actuator/metrics/tomcat.threads.config.max'
        ].map(url => api.http.get(url))
      Promise.all(fns).then(results => {
        this.system.cpu.count = results[0].measurements[0].value
        this.system.cpu.usage = this.convert(results[1].measurements[0].value)
        this.system.process.uptime = results[2].measurements[0].value
        this.system.process.startTime = moment(results[3].measurements[0].value * 1000).format('YYYY-MM-DD HH:mm:ss')
        this.system.process.cpuUsage = this.convert(results[4].measurements[0].value)

        //jvm
        this.jvm.memory.max = this.convert(results[5].measurements[0].value)
        this.jvm.memory.committed = this.convert(results[6].measurements[0].value)
        this.jvm.memory.used = this.convert(results[7].measurements[0].value)
        this.jvm.buffer.memory.used = this.convert(results[8].measurements[0].value)
        this.jvm.buffer.count = results[9].measurements[0].value
        this.jvm.threads.daemon = results[10].measurements[0].value
        this.jvm.threads.live = results[11].measurements[0].value
        this.jvm.threads.peak = results[12].measurements[0].value
        this.jvm.classes.loaded = results[13].measurements[0].value
        this.jvm.classes.unloaded = results[14].measurements[0].value
        this.jvm.gc.memory.allocated = this.convert(results[15].measurements[0].value)
        this.jvm.gc.memory.promoted = this.convert(results[16].measurements[0].value)
        this.jvm.gc.maxDataSize = this.convert(results[17].measurements[0].value)
        this.jvm.gc.liveDataSize = this.convert(results[18].measurements[0].value)
        this.jvm.gc.pause.count = results[19].measurements[0].value
        this.jvm.gc.pause.totalTime = results[19].measurements[1].value

        this.tomcat.sessions.created = results[20].measurements[0].value
        this.tomcat.sessions.expired = results[21].measurements[0].value
        this.tomcat.sessions.active.current = results[22].measurements[0].value
        this.tomcat.sessions.active.max = results[23].measurements[0].value
        this.tomcat.sessions.rejected = results[24].measurements[0].value
        this.tomcat.global.sent = results[25].measurements[0].value
        this.tomcat.global.request.max = results[26].measurements[0].value
        this.tomcat.global.request.count = results[27].measurements[0].value
        this.tomcat.global.request.totalTime = results[27].measurements[1].value
        this.tomcat.threads.current = results[28].measurements[0].value
        this.tomcat.threads.configMax = results[29].measurements[0].value

        this.loading = false
      }).catch(error => {
        console.error(error)
        this.$message.error('获取服务器系统信息失败:'+error.message)
      })
    },
    convert (value) {
      return Number(value * 100).toFixed(2)
    },
    bytesToMB(value){
      return ((Number(value)/1024)/1024).toFixed(2)
    }
  }
}
</script>

<style>

</style>
