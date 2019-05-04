import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import { VueAxios } from './axios'
import notification from 'ant-design-vue/es/notification'
import { ACCESS_TOKEN } from '@/store/mutation-types'

// 创建 axios 实例
const service = axios.create({
  baseURL: '/api/v1', // api base_url
  timeout: 6000 // 请求超时时间
})

const err = (error) => {
  if (error.response) {
    const data = error.response.data
    const token = Vue.ls.get(ACCESS_TOKEN)
    if (error.response.status === 403) {
      notification.error({ message: 'Forbidden', description: data.message })
    }
    if (error.response.status === 401) {
      notification.error({ message: 'Unauthorized', description: 'Authorization verification failed' })
      if (token) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      }
    }
  }
  return Promise.reject(error)
}

// request interceptor
service.interceptors.request.use(config => {
  const token = Vue.ls.get(ACCESS_TOKEN)
  if (token) {
    config.headers[ 'HTOKEN' ] = token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  if (config.url.endsWith('/excel')) {
    // config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
    config.headers['Accept'] = 'application/json, text/plain, */*'
  } else {
    config.headers['Accept'] = 'application/json'
  }
  return config
}, err)

// response interceptor
service.interceptors.response.use((response) => {
  const req = response.request
  const url = req.responseURL
  const result = response.data
  if (response.status === 200) {
    if (url.endsWith('/excel')) {
      const blob = new Blob([result], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
      const fileName = `${new Date().getTime()}_导出结果.xlsx`
      if ('download' in document.createElement('a')) {
        const elink = document.createElement('a')
        elink.download = fileName
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        console.log(blob)
        console.log(elink)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href)
        document.body.removeChild(elink)
      } else {
        console.log('----------x save0000--------')
        navigator.msSaveBlob(blob, fileName)
      }
      return response
    } else if (result.code !== 200) {
      notification.error({ message: '错误', description: result.message })
    }
  }
  return result.data
}, err)

const installer = {
  vm: {},
  install (Vue, router = {}) {
    Vue.use(VueAxios, router, service)
  }
}

export {
  installer as VueAxios,
  service as axios
}
