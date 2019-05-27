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
import stomp from 'stompjs';
import SockJS from 'sockjs-client';
import Vue from 'vue'
import { ACCESS_TOKEN } from '@/store/mutation-types'

let stompClient = null

export default {
  data () {
    return {
      connected: false,
      connecting: false,

    }
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
      const token = Vue.ls.get(ACCESS_TOKEN)
      var socket = new SockJS('/api/v1/logs?HTOKEN=' + token);
      stompClient = stomp.over(socket);
      let dom = this.$refs.logcontent
      dom.innerHTML = "正在连接...<br/>",
      stompClient.connect({}, (frame) => {
        console.log(`-------connected----------`)
        this.connecting = false
          dom.innerHTML = dom.innerHTML + "已经连接<br/>",
          stompClient.subscribe('/sys', (event) => {
              var content=JSON.parse(event.body);
              // $("#log-container div").append(content.timestamp +" "+ content.level+" --- ["+ content.threadName+"] "+ content.className+"   :"+content.body).append("<br/>");
              // $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
              let txt = content.timestamp +" "+ content.level+" --- ["+ content.threadName+"] "+ content.className+"   :" +content.body + "<br/>";
              dom.innerHTML = dom.innerHTML + txt

          },{});
      });

    },
    closeSocket(){
      if(stompClient){
        stompClient.disconnect();
        stompClient=null;
      }
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
