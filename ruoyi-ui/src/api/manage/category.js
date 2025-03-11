import request from '@/utils/request'

// 查询类别管理列表
export function listCategory(query) {
  return request({
    url: '/manage/category/list',
    method: 'get',
    params: query
  })
}

// 用户检索书籍类别
export function searchCategory(query) {
  return request({
    url: '/manage/category/search',
    method: 'get',
    params: query
  })
}

// 查询类别管理详细
export function getCategory(id) {
  return request({
    url: '/manage/category/' + id,
    method: 'get'
  })
}

// 新增类别管理
export function addCategory(data) {
  return request({
    url: '/manage/category',
    method: 'post',
    data: data
  })
}

// 修改类别管理
export function updateCategory(data) {
  return request({
    url: '/manage/category',
    method: 'put',
    data: data
  })
}

// 删除类别管理
export function delCategory(id) {
  return request({
    url: '/manage/category/' + id,
    method: 'delete'
  })
}
