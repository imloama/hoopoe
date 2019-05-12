// sys菜单配置
import { RouteView } from '@/components/layouts'

export default {
  path: '/monitor',
  component: RouteView,
  redirect: '/monitor/online',
  name: 'monitor',
  meta: { title: '系统监控', icon: 'line-chart', keepAlive: true, permission: [ 'monitor' ] },
  children: [
    {
      path: '/monitor/online',
      name: 'online',
      component: () => import('@/views/monitor/online_users.vue'),
      meta: { title: '在线用户', keepAlive: true, permission: [ 'user:online' ] }
    },
    {
      path: '/monitor/logs',
      name: 'logs',
      component: () => import('@/views/monitor/logs.vue'),
      meta: { title: '系统日志', keepAlive: true, permission: [ 'logs' ] }
    },
    {
      path: '/monitor/db',
      name: 'dbmonitor',
      component: () => import('@/views/monitor/db.vue'),
      meta: { title: '数据库监控', keepAlive: true, permission: [ 'dbmonitor' ] }
    },
    {
      path: '/monitor/redis',
      name: 'redis',
      component: () => import('@/views/monitor/redis'),
      meta: { title: 'Redis监控', keepAlive: true, permission: [ 'redis' ] }
    },
    {
      path: '/monitor/httptrace',
      name: 'httptrace',
      component: () => import('@/views//monitor/httptrace.vue'),
      meta: { title: '请求追踪', keepAlive: true, permission: [ 'httptrace' ] }
    },
    {
      path: '/monitor/system',
      name: 'system',
      component: () => import('@/views//monitor/system.vue'),
      meta: { title: '系统信息', keepAlive: true, permission: [ 'system' ] }
    }
  ]
}
