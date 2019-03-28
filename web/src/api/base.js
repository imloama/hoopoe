import { axios } from '@/utils/request'

// 查询对象
export function getModel (name, id, params) {
  return axios({
    method: 'get',
    url: `/${name}/${id}`
  })
}

// 新增对象
export function createModel (name, params) {
  return axios({
    method: 'post',
    url: `/${name}/create`,
    data: params
  })
}

// 修改对象
export function updateModel (name, id, params) {
  return axios({
    method: 'post',
    url: `/${name}/update/${id}`,
    data: params
  })
}

// 删除对象
export function delModel (name, id) {
  return axios({
    method: 'get',
    url: `/${name}/del/${id}`
  })
}

// 批量删除对象
export function delAllModel (name, ids) {
  return axios({
    url: `/${name}/delall`,
    method: 'post',
    data: { ids }
  })
}

// 查询分页对象
export function getModelPage (name, params) {
  if (params == null) {
    params = { pageNum: 1, pageSize: 100 }
  }
  return axios({
    url: `/${name}/page`,
    method: 'post',
    data: params
  })
}

// 下载excel
export function downExcel (name, params) {
  if (params == null) {
    params = { pageNum: 1, pageSize: 1000 }
  }
  return axios({
    url: `/${name}/excel`,
    method: 'post',
    data: params
  })
}
