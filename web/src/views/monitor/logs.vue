/**
 * 系统日志功能
 */
<template>
  <a-card title="系统日志">
    <div slot="extra">
      <a-button :loading="connecting" v-if="connected" @click="closeSocket">停止</a-button>
      <a-button :loading="connecting" v-else @click="openSocket">开启</a-button>
      <a-button @click="clear">清除</a-button>
    </div>
    
    <div id="log-container" class="logcontainer">
      <div ref="logcontent"></div>
  </div>
  </a-card>
</template>

<script>
// import stomp from 'stompjs';
// import SockJS from 'sockjs-client';
import Vue from 'vue'
import { mapState } from 'vuex'
import { ACCESS_TOKEN } from '@/store/mutation-types'

let stompClient = null
let ws = null

export default {
  data () {
    return {
      connected: false,
      connecting: false,

    }
  },
  computed: {
    ...mapState({
      info: state => state.user.info
    })
  },
  mounted () {
    this.openSocket()
  },
  beforeDestroy () {
    this.closeSocket()
  },
  methods: {
    openSocket(){
      if(this.connecting)return
      this.connecting = true
      let dom = this.$refs.logcontent
      dom.innerHTML = "正在连接...<br/>"
      const token = Vue.ls.get(ACCESS_TOKEN)
      const url = `ws://127.0.0.1:8090/api/v1/logs/sys/${this.info.name}?HTOKEN=${token}`
      ws = new WebSocket(url)
      ws.onopen = (event) => {
          this.connecting = false
          dom.innerHTML = dom.innerHTML + "已经连接<br/>"
      }
       //接收到消息的回调方法
      ws.onmessage = (event) => {
          console.log('接收到内容：' + event.data)
          var content=JSON.parse(event.data);
          let txt = content.timestamp +" "+ content.level+" --- ["+ content.threadName+"] "+ content.className+"   :" +content.body + "<br/>";
          dom.innerHTML = dom.innerHTML + txt
      }

      //连接发生错误的回调方法
      ws.onerror =  (event) => {
          console.log('发生错误')
          dom.innerHTML = dom.innerHTML + `发生错误：${event}<br/>`
      }

      //连接关闭的回调方法
      ws.onclose =  (event) => {
          console.log('关闭连接')
          dom.innerHTML = dom.innerHTML + `关闭连接 ${event}<br/>`
      }

      // var socket = new SockJS('ws://127.0.0.1:8090/api/v1/logs?HTOKEN=' + token);
      // stompClient = stomp.over(socket);
      // let dom = this.$refs.logcontent
      // dom.innerHTML = "正在连接...<br/>",
      // stompClient.connect({}, (frame) => {
      //   console.log(`-------connected----------`)
      //   this.connecting = false
      //     dom.innerHTML = dom.innerHTML + "已经连接<br/>",
      //     stompClient.subscribe('/sys', (event) => {
      //         var content=JSON.parse(event.body);
      //         // $("#log-container div").append(content.timestamp +" "+ content.level+" --- ["+ content.threadName+"] "+ content.className+"   :"+content.body).append("<br/>");
      //         // $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
      //         let txt = content.timestamp +" "+ content.level+" --- ["+ content.threadName+"] "+ content.className+"   :" +content.body + "<br/>";
      //         dom.innerHTML = dom.innerHTML + txt

      //     },{});
      // });

    },
    closeSocket(){
      // if(stompClient){
      //   stompClient.disconnect();
      //   stompClient=null;
      // }
       ws.close();
    },
    clear(){
      this.$refs.logcontent.innerHTML = ''
    }
  }
}
</script>

<style>
.logcontainer{
  height: calc(100vh - 250px); 
  overflow-y: scroll; 
  background: #333; 
  color: #aaa; 
  padding: 10px;
}
</style>
