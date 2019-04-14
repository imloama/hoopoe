import Vue from 'vue'
import router from './router'
import store from './store'

import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import notification from 'ant-design-vue/es/notification'
import { ACCESS_TOKEN } from '@/store/mutation-types'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['login', 'register', 'registerResult'] // no redirect whitelist

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar

  if (Vue.ls.get(ACCESS_TOKEN)) {
    /* has token */
    if (to.path === '/user/login') {
      next({ path: '/workplace' })
      NProgress.done()
    } else {
      // 如果 userinfo存在，则表示已经登陆了，否则需要获取用户信息
      const user = store.getters.userInfo
      if (user.token === null || typeof user.token === 'undefined') {
        store.dispatch('GetInfo')
          .then(({ roles, menus }) => {
            resetRouter({ roles, menus }, { to, from, next })
          })
          .catch(err => {
            console.error(err)
            notification.error({
              message: '错误',
              description: '请求用户信息失败，请重试'
            })
            store.dispatch('Logout').then(() => {
              next({ path: '/user/login', query: { redirect: to.fullPath } })
            })
            // next({ path: '/user/login', query: { redirect: to.fullPath } })
          })
      } else {
        next()
      }
    }
  } else {
    if (whiteList.includes(to.name)) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next({ path: '/user/login', query: { redirect: to.fullPath } })
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

function resetRouter ({ roles, menus }, { to, from, next }) {
  store.dispatch('GenerateRoutes', { roles, menus }).then(() => {
    // 根据roles权限生成可访问的路由表
    // 动态添加可访问路由表
    router.addRoutes(store.getters.addRouters)
    const redirect = decodeURIComponent(from.query.redirect || to.path)
    console.log(redirect)
    if (to.path === redirect) {
      // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
      next({ ...to, replace: true })
    } else {
      // 跳转到目的路由
      next({ path: redirect })
    }
  }).catch(err => {
    console.error(err)
  })
}

router.afterEach(() => {
  NProgress.done() // finish progress bar
})

/**
 * Action 权限指令
 * 指令用法：
 *  - 在需要控制 action 级别权限的组件上使用 v-action="'method'" , 如下：
 *    <i-button v-action="'add'" >添加用户</a-button>
 *    <a-button v-action="'delete'">删除用户</a-button>
 *    <a v-action="'edit'" @click="edit(record)">修改</a>
 */
const action = Vue.directive('action', {
  bind: function (el, binding, vnode) {
    const action = binding.value
    const menus = store.getters.userInfo.menus
    const codes = menus.map(p => p.code)
    if (codes.indexOf(action) < 0) {
      el.parentNode && el.parentNode.removeChild(el) || (el.style.display = 'none')
    }
  }
})

// 只要包含列出的任意一个权限，元素就会显示
const hasAnyPermission = Vue.directive('hasAnyPermission', {
  bind (el, binding, vnode) {
    const menus = (store.getters.userInfo.menus || []).map(m => m.code)
    const values = binding.value.split(',')
    let flag = false
    for (const v of values) {
      if (menus.includes(v)) {
        flag = true
      }
    }
    if (!flag) {
      if (!el.parentNode) {
        el.style.display = 'none'
      } else {
        el.parentNode.removeChild(el)
      }
    }
  }
})

export {
  action,
  hasAnyPermission
}
