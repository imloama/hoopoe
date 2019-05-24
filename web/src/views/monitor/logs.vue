/**
 * 系统日志功能
 */
<template>
  <a-card title="系统日志">
    <div slot="extra">
      <a-button :loading="connecting" v-if="connected" @click="closeSocket">停止</a-button>
      <a-button :loading="connecting" v-else @click="openSocket">开启</a-button>
    </div>
    
    <div id="log-container" style="height: 450px; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;">
      <div ref="logcontent"></div>
  </div>
  </a-card>
</template>

<script>
import stomp from 'stompjs';
import socket from 'sockjs-client';


let stompClient = null

export default {
  data () {
    return {
      connected: false,
      connecting: false,

    }
  },
  created () {
    this.openSocket()
  },
  beforeDestroy () {
    this.closeSocket()
  },
  methods: {
    openSocket(){
      if(this.connecting)return
      this.connecting = true
      var socket = new SockJS('http://localhost:8080/api/v1/logs');
      stompClient = stomp.over(socket);
      stompClient.connect({}, (frame) => {
        this.connecting = true
          stompClient.subscribe('/sys', (event) => {
              var content=JSON.parse(event.body);
              // $("#log-container div").append(content.timestamp +" "+ content.level+" --- ["+ content.threadName+"] "+ content.className+"   :"+content.body).append("<br/>");
              // $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
              let txt = content.timestamp +" "+ content.level+" --- ["+ content.threadName+"] "+ content.className+"   :" +content.body + "<br/>";
              let dom = this.$refs.logcontent
              dom.innerHTML = dom.innerHTML + txt

          },{});
      });

    },
    closeSocket(){
      if(stompClient){
        stompClient.disconnect();
        stompClient=null;
      }
    }
  }
}
</script>

<style>

</style>
