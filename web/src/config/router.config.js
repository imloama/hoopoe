// eslint-disable-next-line
import { UserLayout, BasicLayout, RouteView, BlankLayout, PageView } from '@/components/layouts'
import sys from './sys.router'
import monitor from './monitor.router'

export const asyncRouterMap = [

  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: '首页' },
    redirect: '/workplace',
    children: [
      {
        path: '/workplace',
        name: 'workplace',
        component: () => import('@/views/dashboard/Workplace'),
        meta: { title: '工作台', keepAlive: true, icon: 'home', permission: [ 'workplace' ] }
      },
      sys,
      monitor,

      // account
      {
        path: '/account',
        component: RouteView,
        redirect: '/account/center',
        name: 'account',
        meta: { title: '个人中心', icon: 'user', keepAlive: true, permission: [ 'user' ] },
        children: [
          {
            path: '/account/settings/base',
            name: 'BaseSettings',
            component: () => import('@/views/account/settings/BaseSetting'),
            meta: { title: '基本设置', hidden: true, keepAlive: true, permission: [ 'user' ] }
          },
          {
            path: '/account/settings/security',
            name: 'SecuritySettings',
            component: () => import('@/views/account/settings/Security'),
            meta: { title: '修改密码', hidden: true, keepAlive: true, permission: [ 'user' ] }
          }
        ]
      }
    ]
  },
  {
    path: '*', redirect: '/404', hidden: true
  }
]

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      },
      {
        path: 'register',
        name: 'register',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Register')
      },
      {
        path: 'register-result',
        name: 'registerResult',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/RegisterResult')
      }
    ]
  },

  {
    path: '/test',
    component: BlankLayout,
    redirect: '/test/home',
    children: [
      {
        path: 'home',
        name: 'TestHome',
        component: () => import('@/views/Home')
      }
    ]
  },

  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  }

]
