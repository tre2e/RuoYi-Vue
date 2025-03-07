import request from '@/utils/request'

// 查询用户借阅列表
export function listIssue(query) {
  return request({
    url: '/manage/issue/list',
    method: 'get',
    params: query
  })
}

// 查询用户借阅详细
export function getIssue(id) {
  return request({
    url: '/manage/issue/' + id,
    method: 'get'
  })
}

// 新增用户借阅
export function addIssue(data) {
  return request({
    url: '/manage/issue',
    method: 'post',
    data: data
  })
}

// 新增带库存检查的借阅接口
export function addIssueWithQuantity(data) {
  return request({
    url: '/manage/issue/addWithQuantity',
    method: 'post',
    data: data
  });
}

// 修改用户借阅
export function updateIssue(data) {
  return request({
    url: '/manage/issue',
    method: 'put',
    data: data
  })
}

// 删除用户借阅
export function delIssue(id) {
  return request({
    url: '/manage/issue/' + id,
    method: 'delete'
  })
}
