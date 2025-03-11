<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="书籍名" prop="bookName">
        <el-input
          v-model="queryParams.bookName"
          placeholder="请输入书籍名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="借阅日期" prop="issueDate">
        <el-date-picker
          clearable
          v-model="queryParams.issueDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择借阅日期"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="未归还" :value="0" />
          <el-option label="已归还" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="issueList">
      <el-table-column label="封面" align="center" prop="cover" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.cover" :width="50" :height="70" />
        </template>
      </el-table-column>
      <el-table-column label="书籍名" align="center" prop="bookName" />
      <el-table-column label="借阅日期" align="center" prop="issueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.issueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应还日期" align="center" prop="dueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="归还日期" align="center" prop="returnDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.returnDate, '{y}-{m}-{d}') || '-' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="statusName" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" width="100">
        <template slot-scope="scope">
          <el-button
            v-if="Number(scope.row.status) === 0"
            size="mini"
            type="success"
            icon="el-icon-check"
            @click="handleReturn(scope.row.id)"
            plain
          >归还</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { getUserProfile } from "@/api/system/user";
import { listMyIssues } from "@/api/manage/issue";
import request from '@/utils/request';

export default {
  name: "MyIssues",
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      issueList: [],
      defaultCover: 'https://via.placeholder.com/50x70?text=No+Cover',
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        bookName: null,
        issueDate: null,
        status: null,
      },
    };
  },
  created() {
    this.getUserIdAndList();
  },
  methods: {
    async getUserIdAndList() {
      try {
        const response = await getUserProfile();
        /*console.log('User Profile:', response);*/  // 调试用
        this.queryParams.userId = response.data.userId;
        this.getList();
      } catch (error) {
        this.$modal.msgError("获取用户信息失败: " + (error.message || "网络异常"));
      }
    },
    getList() {
      this.loading = true;
      listMyIssues(this.queryParams).then(response => {
        /*console.log('Issue List Response:', JSON.stringify(response, null, 2));*/  // 调试用
        this.issueList = (response.rows || []).map(item => ({
          ...item,
          coverUrl: this.formatCoverUrl(item.cover), // 在数据处理时转换 URL
        }));
        this.total = response.total || 0;
        this.loading = false;
      }).catch(error => {
        console.error('List Error:', error);
        this.loading = false;
        this.$modal.msgError("加载借阅记录失败: " + (error.message || "网络异常"));
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.queryParams.bookName = null;
      this.queryParams.issueDate = null;
      this.queryParams.status = null;
      this.handleQuery();
    },
    async handleReturn(issueId) {
      this.$confirm('确认归还此书籍吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const response = await request({
            url: `/manage/issue/return/${issueId}`,
            method: 'put'
          });
          console.log('Return Response:', response);
          if (response.code === 200) {
            this.$message.success("归还成功");
            this.getList();
          } else {
            this.$modal.msgError(response.msg || "归还失败");
          }
        } catch (error) {
          console.error('Return Error:', error);
          this.$modal.msgError("归还失败: " + (error.message || "网络异常"));
        }
      }).catch(() => {
        this.$message.info("已取消归还");
      });
    },
    parseTime(time, format) {
      if (!time) return null;
      const date = new Date(time);
      const map = {
        '{y}': date.getFullYear(),
        '{m}': String(date.getMonth() + 1).padStart(2, '0'),
        '{d}': String(date.getDate()).padStart(2, '0')
      };
      return format.replace(/{y}|{m}|{d}/g, match => map[match]);
    },
    formatCoverUrl(cover) {
      // 从 request.js 获取基础 URL
      const baseUrl = request.defaults.baseURL || 'http://localhost:8080';
      if (!cover) return this.defaultCover;
      if (cover.startsWith('http://') || cover.startsWith('https://')) {
        return cover;
      }
      return `${baseUrl}${cover.startsWith('/') ? '' : '/'}${cover}`;
    },
    handleImageError(row) {
      // 图片加载失败时使用默认图片
      row.coverUrl = this.defaultCover;
    }
  }
};
</script>

<style scoped>
.el-table .cell {
  padding: 0 10px;
}
</style>
