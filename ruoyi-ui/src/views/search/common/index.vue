<template>
  <div class="app-container">
    <!-- 馆藏检索表单 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="80px" @submit.native.prevent>
      <el-form-item label="书籍名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入书籍名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="queryParams.author" placeholder="请输入作者" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="出版社" prop="publisher">
        <el-input v-model="queryParams.publisher" placeholder="请输入出版社" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="ISBN" prop="isbn">
        <el-input v-model="queryParams.isbn" placeholder="请输入ISBN编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="出版日期" prop="publishDateRange">
        <el-date-picker
          v-model="queryParams.publishDateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          clearable
        />
      </el-form-item>
      <el-form-item label="类别" prop="categoryId">
        <el-select v-model="queryParams.categoryId" placeholder="请选择类别" clearable>
          <el-option v-for="category in categories" :key="category.id" :label="category.name" :value="category.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="区域" prop="regionId">
        <el-select v-model="queryParams.regionId" placeholder="请选择区域" clearable>
          <el-option v-for="region in regions" :key="region.id" :label="region.name" :value="region.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="入馆时间" prop="entryDateRange">
        <el-date-picker
          v-model="queryParams.entryDateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          clearable
        />
      </el-form-item>
<!--      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="正常" value="0" />
          <el-option label="禁用" value="1" />
        </el-select>
      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 检索结果表格 -->
    <el-table v-loading="loading" :data="bookList" border>
      <el-table-column label="书籍ID" align="center" prop="id" width="80" />
      <el-table-column label="书籍名称" align="center" prop="name" />
      <el-table-column label="封面" align="center" prop="cover" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.cover" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="作者" align="center" prop="author" />
      <el-table-column label="出版社" align="center" prop="publisher" />
      <el-table-column label="ISBN" align="center" prop="isbn" />
      <el-table-column label="出版日期" align="center" prop="publishDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="类别" align="center" prop="categoryName" />
      <el-table-column label="区域" align="center" prop="regionName" />
      <el-table-column label="书籍数量" align="center" prop="quantity" width="80" />
      <el-table-column label="状态" align="center" prop="statusName" width="80" />
      <el-table-column label="入馆时间" align="center" prop="entryDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <!-- 添加操作列    可选（添加借阅按钮权限显示）：v-hasPermi="['manage:issue:add']"-->
      <el-table-column label="操作" align="center" width="100">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-plus"
            @click="handleBorrow(scope.row)"

          >借阅</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

    <!-- 借阅对话框 -->
    <el-dialog :title="borrowTitle" :visible.sync="borrowOpen" width="500px" append-to-body>
      <el-form ref="borrowForm" :model="borrowForm" :rules="borrowRules" label-width="80px">

<!--        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="borrowForm.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="书籍ID" prop="bookId">
          <el-input v-model="borrowForm.bookId" placeholder="书籍ID" disabled />
        </el-form-item>-->
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="borrowForm.userName" disabled />
        </el-form-item>
        <el-form-item label="书籍名称" prop="bookName">
          <el-input v-model="borrowForm.bookName" disabled />
        </el-form-item>

        <el-form-item label="借阅日期" prop="issueDate">
          <el-date-picker
            v-model="borrowForm.issueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择借阅日期"
          />
        </el-form-item>
        <el-form-item label="应还日期" prop="dueDate">
          <el-date-picker
            v-model="borrowForm.dueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择应还日期"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBorrowForm">确 定</el-button>
        <el-button @click="cancelBorrow">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request' // 导入 request
import { searchBook } from '@/api/manage/book' // 使用新接口
/*import { addIssue } from '@/api/manage/issue'; // 借阅 API*/
import { addIssueWithQuantity } from '@/api/manage/issue'; // 新增接口
import { getUserProfile } from '@/api/system/user';  // 获取用户信息

export default {
  name: 'BookCollectionSearch',
  data() {
    return {
      loading: false,
      total: 0,
      bookList: [],
      categories: [],
      regions: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        author: null,
        publisher: null,
        isbn: null,
        publishDateBegin: null,
        publishDateEnd: null,
        categoryId: null,
        regionId: null,
        entryDateBegin: null,
        entryDateEnd: null,
        status: null
      },
      // 借阅相关数据
      borrowOpen: false,
      borrowTitle: "借阅书籍",
      borrowForm: {
        userId: null,
        userName: null, // 显示用
        bookId: null,
        bookName: null,     // 显示用
        issueDate: null,
        dueDate: null,
        status: 0 // 默认未归还
      },
      borrowRules: {
        /*userId: [{ required: true, message: "用户ID不能为空", trigger: "blur" }],
        bookId: [{ required: true, message: "书籍ID不能为空", trigger: "blur" }],*/
        issueDate: [{ required: true, message: "借阅日期不能为空", trigger: "change" }],
        dueDate: [{ required: true, message: "应还日期不能为空", trigger: "change" }]
      }
    };   //添加分号
  },
  computed: {
    publishDateRange: {
      get() {
        return [this.queryParams.publishDateBegin, this.queryParams.publishDateEnd]
      },
      set(val) {
        this.queryParams.publishDateBegin = val ? val[0] : null
        this.queryParams.publishDateEnd = val ? val[1] : null
      }
    },
    entryDateRange: {
      get() {
        return [this.queryParams.entryDateBegin, this.queryParams.entryDateEnd]
      },
      set(val) {
        this.queryParams.entryDateBegin = val ? val[0] : null
        this.queryParams.entryDateEnd = val ? val[1] : null
      }
    }
  },
  created() {
    this.fetchCategories()
    this.fetchRegions()
    this.getList()
    this.fetchUserProfile()
  },
  methods: {
    fetchCategories() {
      request({
        url: '/manage/category/search',
        method: 'get'
      }).then(response => {
        if (response.code === 200) {
          this.categories = response.rows
        } else {
          this.$message.error('获取类别列表失败')
        }
      }).catch(error => {
        console.error('Fetch categories error:', error)
      })
    },
    fetchRegions() {
      request({
        url: '/manage/region/search',
        method: 'get'
      }).then(response => {
        if (response.code === 200) {
          this.regions = response.rows
        } else {
          this.$message.error('获取区域列表失败')
        }
      }).catch(error => {
        console.error('Fetch regions error:', error)
      })
    },
    getList() {
      this.loading = true
      searchBook(this.queryParams).then(response => {
        this.bookList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(error => {
        this.loading = false
        console.error('Error fetching book list:', error)
        this.$message.error('查询失败')
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.$refs['queryForm'].resetFields() // 重置表单字段
      this.publishDateRange = null // 手动重置出版日期
      this.entryDateRange = null   // 手动重置入馆时间
      this.queryParams.status = null // 手动重置状态（不影响表格）
      this.handleQuery()
    },
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    // 获取用户信息
    fetchUserProfile() {
      getUserProfile()
        .then(response => {
          console.log('User Profile Response:', response);
          if (response && response.data) {
            // 根据实际结构调整
            this.borrowForm.userId = response.data.user?.userId || response.data.userId;
            this.borrowForm.userName = response.data.user?.userName || response.data.userName;
          } else {
            console.error('Unexpected response format:', response);
            this.$message.error('用户信息格式错误');
          }
        })
        .catch(error => {
          console.error('Error fetching user profile:', error);
          this.$message.error('获取用户信息失败');
        });
      },
    // 借阅相关方法
    handleBorrow(row) {
      this.resetBorrow();
      this.fetchUserProfile(); // 每次打开借阅时刷新用户信息（可选）
      this.borrowForm.bookId = row.id; // 从表格行获取书籍ID
      this.borrowForm.bookName = row.name; // 从表格行获取书籍名
      this.borrowOpen = true;
      this.borrowTitle = `借阅书籍: ${row.name}`;
    },
    resetBorrow() {
      this.borrowForm = {
        userId: this.borrowForm.userId, // 保留当前用户ID
        userName: this.borrowForm.userName, // 保留当前用户名
        bookId: null,
        bookName: null,
        issueDate: null,
        dueDate: null,
        status: 0
      };
      this.resetForm("borrowForm");
    },
    cancelBorrow() {
      this.borrowOpen = false;
      this.resetBorrow();
    },
    // 新增借阅
    submitBorrowForm() {
      this.$refs["borrowForm"].validate(valid => {
        if (valid) {
          const submitData = {
            userId: this.borrowForm.userId,
            bookId: this.borrowForm.bookId,
            issueDate: this.borrowForm.issueDate,
            dueDate: this.borrowForm.dueDate,
            status: 0
          };
          addIssueWithQuantity(submitData)
            .then(response => {
              if (response.code === 200) {
                this.$modal.msgSuccess("借阅成功");
                this.borrowOpen = false;
                this.resetBorrow();
                this.getList(); // 刷新列表，显示更新后的 quantity
              } else if (response.code === 400 && response.msg === "库存不足") {
                this.$modal.msgError("书籍库存不足，无法借阅");
              } else {
                this.$modal.msgError(response.msg || "借阅失败");
              }
            })
            .catch(error => {
              console.error('Borrow error:', error);
              this.$message.error('借阅失败');
            });
        }
      });
    }
  }
};   //添加分号


</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
