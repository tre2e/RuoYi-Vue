<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="书籍ID" prop="bookId">
        <el-input
          v-model="queryParams.bookId"
          placeholder="请输入书籍ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="书籍名" prop="bookName">
        <el-input
          v-model="queryParams.bookName"
          placeholder="请输入书籍名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="借阅日期" prop="issueDate">
        <el-date-picker clearable
          v-model="queryParams.issueDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择借阅日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="应还日期" prop="dueDate">
        <el-date-picker clearable
          v-model="queryParams.dueDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择应还日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="归还日期" prop="returnDate">
        <el-date-picker clearable
          v-model="queryParams.returnDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择归还日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:issue:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:issue:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:issue:remove']"
        >删除</el-button>
      </el-col>
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="issueList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />

<!--      改为显示用户名和书籍名
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="书籍ID" align="center" prop="bookId" />-->
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
      <el-table-column label="归还日期" align="center" prop="returnDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.returnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="状态" align="center" prop="status" />-->
      <el-table-column label="状态" align="center" prop="statusName" />

      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="240" >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:issue:edit']"
            plain
            class="custom-button"
          >修改</el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:issue:remove']"
            plain
            class="custom-button"
          >删除</el-button>
          <el-button
            v-if="!scope.row.returnDate"
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
      @pagination="getList"
    />

    <!-- 添加或修改用户借阅对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" disabled/>
        </el-form-item>
        <el-form-item label="书籍ID" prop="bookId">
          <el-input v-model="form.bookId" placeholder="请输入书籍ID" disabled/>
        </el-form-item>
        <el-form-item label="借阅日期" prop="issueDate">
          <el-date-picker
            v-model="form.issueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择借阅日期"
            :picker-options="issueDatePickerOptions"
            disabled
          />
        </el-form-item>
        <el-form-item label="应还日期" prop="dueDate">
          <el-date-picker
            v-model="form.dueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择应还日期"
            :picker-options="dueDatePickerOptions"
            :disabled="form.status === 1"
          />
        </el-form-item>
        <el-form-item label="归还日期" prop="returnDate">
          <el-date-picker
            v-model="form.returnDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择归还日期"
            :disabled="form.status === 0 || form.status === 1"
          />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIssue, getIssue, delIssue, addIssue, updateIssue, addIssueWithQuantity } from "@/api/manage/issue";
import request from '@/utils/request'; // 确保导入 request

export default {
  name: "Issue",
  data() {
    const today = new Date();
    today.setHours(0, 0, 0, 0); // 设置为当天0点
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户借阅表格数据
      issueList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        bookId: null,
        userName: null, // 新增用户名查询参数
        bookName: null, // 新增书籍名查询参数
        issueDate: null,
        dueDate: null,
        returnDate: null,
        status: null,
      },
      // 表单参数
      form: {
        issueDate: this.formatDate(today) // 默认当前日期
      },
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
        ],
        bookId: [
          { required: true, message: "书籍ID不能为空", trigger: "blur" }
        ],
        issueDate: [
          { required: true, message: "借阅日期不能为空", trigger: "blur" }
        ],
        dueDate: [
          { required: true, message: "应还日期不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
      },
      // 借阅日期限制：从当天开始
      issueDatePickerOptions: {
        /*disabledDate(time) {
          return time.getTime() < today.getTime();
        }*/
        disabledDate(time) {
          return true; // 完全禁用选择
        }
      },
      // 应还日期限制：基于issueDate，最长2个月
      dueDatePickerOptions: {
        disabledDate: (time) => {
          const issueDate = this.form.issueDate ? new Date(this.form.issueDate) : today;
          const minDueDate = new Date(issueDate);
          minDueDate.setDate(minDueDate.getDate() + 1);
          const maxDueDate = new Date(issueDate);
          maxDueDate.setMonth(maxDueDate.getMonth() + 2);
          return time.getTime() < minDueDate.getTime() || time.getTime() > maxDueDate.getTime();
        }
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户借阅列表 */
    getList() {
      this.loading = true;
      listIssue(this.queryParams).then(response => {
        this.issueList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        bookId: null,
        issueDate: this.formatDate(new Date()), // 重置时填入当前日期
        dueDate: null,
        returnDate: null,
        status: null,
        createTime: null,
        updateTime: null,
        createBy: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.userName = null; // 重置新增字段
      this.queryParams.bookName = null; // 重置新增字段
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户借阅";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIssue(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户借阅";
      });
    },
    /** 归还按钮操作 */
    handleReturn(id) {
      this.$confirm('是否确认归还该借阅记录？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 发送归还请求
          /*this.$axios.put(`/manage/issue/return/${id}`)       // 相对路径，依赖前端代理*/
          return request({
            url: `/manage/issue/return/${id}`,
            method: 'put'
          })
            .then((response) => {
              /*console.log('Response:', response); // 调试用*/
              if (!response) {
                this.$message.error('归还失败: 响应数据为空');
                return;
              }
              const code = response.code || 200;
              const msg = response.msg || '操作成功';
              if (code === 200) {
                this.$message.success('归还成功');
                this.getList();
              } else {
                this.$message.error('归还失败: ' + msg);
              }
            })
            .catch(error => {
              console.error('Request error:', error); // 调试错误
              this.$message.error('归还失败: ' + (error.message || '请求异常'));
            });
        })
        .catch(() => {
          this.$message.info('已取消归还');
        });
    },
    /** 提交按钮 - 修改为绑定数量 */
    async submitForm() {
      try {
        const valid = await this.$refs["form"].validate();
        if (!valid) return;

        let response;
        if (this.form.id != null) {
          response = await updateIssue(this.form);
        } else {
          response = await addIssueWithQuantity(this.form).catch(error => {
            // 捕获后端抛出的业务错误
            const msg = error.message || "新增失败";
            this.$modal.msgError(msg); // 直接显示后端 msg
            return null; // 返回 null 表示已处理
          });
        }

        // 如果 response 为 null，说明已在 catch 中处理
        if (!response) return;

        console.log('Response:', response);

        if (response.code === 200) {
          this.$modal.msgSuccess(this.form.id ? "修改成功" : "新增成功");
          this.open = false;
          this.getList();
        } else {
          this.$modal.msgError(response.msg || (this.form.id ? "修改失败" : "新增失败"));
        }
      } catch (error) {
        // 只处理表单验证或其他意外错误
        if (error.message !== "您已借阅此书，无法重复借阅" && // 避免重复显示已处理的消息
          error.message !== "您已借阅10本书，无法再借更多" &&
          error.message !== "库存不足") {
          this.$modal.msgError("请求失败: " + (error.message || "网络异常"));
        }
      }
    },
    /** 删除按钮操作 - 修改为检查归还状态 */
    handleDelete(row) {
      const ids = row.id || this.ids; // 支持单条或批量删除
      // 如果是单条删除，直接检查 row 的状态
      if (row.id) {
        if (row.status === 0) {
          this.$modal.msgError("请先归还书籍后再删除该借阅记录");
          return;
        }
      } else {
        // 批量删除，检查所有选中记录的状态
        const selectedRows = this.issueList.filter(item => this.ids.includes(item.id));
        const unreturned = selectedRows.some(item => item.status === 0);
        if (unreturned) {
          this.$modal.msgError("请先归还所有选中的书籍后再删除");
          return;
        }
      }
      // 如果已归还，执行删除
      this.$modal.confirm('是否确认删除用户借阅编号为"' + ids + '"的数据项？').then(() => {
        return delIssue(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/issue/export', {
        ...this.queryParams
      }, `issue_${new Date().getTime()}.xlsx`)
    },
    parseTime(time, format) {
      if (!time) return '';
      const date = new Date(time);
      const map = {
        '{y}': date.getFullYear(),
        '{m}': String(date.getMonth() + 1).padStart(2, '0'),
        '{d}': String(date.getDate()).padStart(2, '0')
      };
      return format.replace(/{y}|{m}|{d}/g, match => map[match]);
    },
    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    }
  }
};
</script>

<style scoped>
/* 美化操作栏按钮 */
.el-table .small-padding .cell .custom-button {
  margin-right: 8px; /* 增大按钮间距 */
  padding: 6px 6px; /* 增加按钮内边距，左右12px，上下6px */
}

/* 移除最后一个按钮的右边距 */
.el-table .small-padding .cell .custom-button:last-child {
  margin-right: 0;
}

/* 可选：调整按钮文字与图标的间距 */
.custom-button .el-icon {
  margin-right: 4px; /* 图标与文字之间的间距 */
}
</style>
