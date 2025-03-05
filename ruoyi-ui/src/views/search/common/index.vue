<template>
  <div class="book-search">
    <!-- 搜索表单 -->
    <el-form :inline="true" :model="queryParams" ref="queryForm" @submit.native.prevent>
      <el-form-item label="书名">
        <el-input v-model="queryParams.bookName" placeholder="请输入书名" clearable></el-input>
      </el-form-item>
      <el-form-item label="作者">
        <el-input v-model="queryParams.author" placeholder="请输入作者" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 结果表格 -->
    <el-table
      v-loading="loading"
      :data="bookList"
      border
      style="width: 100%"
    >
      <el-table-column prop="name" label="书名" width="200"></el-table-column>
      <el-table-column label="封面" align="center" prop="cover" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.cover" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column prop="author" label="作者" width="150"></el-table-column>
      <el-table-column prop="publisher" label="出版社"></el-table-column>
      <el-table-column prop="publishDate" label="出版日期" width="150"></el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryParams.pageNum"
      :page-sizes="[10, 20, 30, 50]"
      :page-size="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
  </div>
</template>

<script>
import { listBook } from '@/api/manage/book' // 复用你已有的接口

export default {
  name: 'BookSearch',
  data() {
    return {
      // 查询参数，与后端 Book 实体字段保持一致
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bookName: '',
        author: ''
      },
      // 书籍列表
      bookList: [],
      // 总数
      total: 0,
      // 加载状态
      loading: false
    }
  },
  created() {
    this.getList() // 页面加载时默认查询
  },
  methods: {
    // 获取书籍列表
    getList() {
      this.loading = true
      listBook(this.queryParams).then(response => {
        // 假设返回数据格式为 RuoYi 标准格式
        this.bookList = response.rows // 或 response.data.rows，视你的接口返回而定
        this.total = response.total // 或 response.data.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    // 搜索
    handleQuery() {
      this.queryParams.pageNum = 1 // 重置到第一页
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm') // RuoYi 提供的表单重置工具方法
      this.handleQuery()
    },
    // 分页大小改变
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    // 当前页改变
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.getList()
    }
  }
}
</script>

<style scoped>
.book-search {
  padding: 20px;
}
</style>
