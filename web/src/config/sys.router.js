// sys菜单配置
import { RouteView } from '@/components/layouts'

export default {
  path: '/sys',
  component: RouteView,
  redirect: '/sys/depts',
  name: 'sys',
  meta: { title: '系统设置', icon: 'setting', keepAlive: true, permission: [ 'depts' ] },
  children: [
    {
      path: '/sys/depts',
      name: 'depts',
      component: () => import('@/views/sys/depts/depts'),
      meta: { title: '部门管理', keepAlive: true, permission: [ 'depts' ] }
    },
    {
      path: '/sys/users',
      name: 'users',
      component: () => import('@/views/sys/users/users'),
      meta: { title: '用户管理', keepAlive: true, permission: [ 'users' ] }
    },
    {
      path: '/sys/roles',
      name: 'roles',
      component: () => import('@/views/sys/roles/roles'),
      meta: { title: '角色管理', keepAlive: true, permission: [ 'roles' ] }
    },
    {
      path: '/sys/menus',
      name: 'menus',
      component: () => import('@/views/sys/menus'),
      meta: { title: '菜单管理', keepAlive: true, permission: [ 'menus' ] }
    },
    {
      path: '/sys/dicts',
      name: 'dicts',
      component: () => import('@/views/sys/dicts'),
      meta: { title: '字典管理', keepAlive: true, permission: [ 'dicts' ] }
    }
  ]
}
