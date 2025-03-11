<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:issue:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar @queryTable="getOverdueList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="overdueList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="封面" align="center" prop="cover" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.cover" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="用户名" align="center" prop="userName" />
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
      <el-table-column label="状态" align="center" prop="statusName" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            icon="el-icon-check"
            @click="handleReturn(scope.row.id)"
            plain
            class="custom-button"
          >归还</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getOverdueList"
    />
  </div>
</template>

<script>
import request from '@/utils/request';
import { listOverdue } from "@/api/manage/issue";

export default {
  name: "OverdueIssue",
  data() {
    return {
      loading: true,
      ids: [],
      total: 0,
      overdueList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    };
  },
  created() {
    this.getOverdueList();
  },
  methods: {
    /** 查询逾期未归还列表 */
    getOverdueList() {
      this.loading = true;
      listOverdue(this.queryParams).then(response => {
        this.overdueList = response.rows;
        this.total = response.total;
        this.loading = false;
      }).catch(error => {
        console.error('Overdue List Error:', error);
        this.loading = false;
        this.$modal.msgError("加载逾期记录失败: " + (error.message || "网络异常"));
      });
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
    },
    handleReturn(id) {
      this.$confirm('是否确认归还该逾期记录？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          return request({
            url: `/manage/issue/return/${id}`,
            method: 'put'
          })
            .then(response => {
              if (!response || response.code !== 200) {
                this.$message.error('归还失败: ' + (response.msg || '操作失败'));
                return;
              }
              this.$message.success('归还成功');
              this.getOverdueList();
            })
            .catch(error => {
              console.error('Return Error:', error);
              this.$message.error('归还失败: ' + (error.message || '请求异常'));
            });
        })
        .catch(() => {
          this.$message.info('已取消归还');
        });
    },
    handleExport() {
      this.download('/manage/issue/export', {
        ...this.queryParams,
        status: 0,
        dueDate: '<' + new Date().toISOString().split('T')[0] // 模拟逾期条件
      }, `overdue_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>

<style scoped>
.el-table .small-padding .cell .custom-button {
  margin-right: 8px;
  padding: 6px 6px;
}
.el-table .small-padding .cell .custom-button:last-child {
  margin-right: 0;
}
.custom-button .el-icon {
  margin-right: 4px;
}
</style>
