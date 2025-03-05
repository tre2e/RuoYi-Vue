<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="书籍名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入书籍名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版社" prop="publisher">
        <el-input
          v-model="queryParams.publisher"
          placeholder="请输入出版社"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="ISBN" prop="isbn">
        <el-input
          v-model="queryParams.isbn"
          placeholder="请输入ISBN编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版日期" prop="publishDate">
        <el-date-picker clearable
          v-model="queryParams.publishDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择出版日期">
        </el-date-picker>
      </el-form-item>

<!--      <el-form-item label="类别ID" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入类别ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->

      <el-form-item label="类别" prop="categoryId">
        <el-select v-model="queryParams.categoryId" placeholder="请选择类别" clearable>
          <el-option v-for="category in categories" :key="category.id" :label="category.name" :value="category.id" />
        </el-select>
      </el-form-item>

<!--      <el-form-item label="区域ID" prop="regionId">
        <el-input
          v-model="queryParams.regionId"
          placeholder="请输入区域ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->

      <el-form-item label="区域" prop="regionId">
        <el-select v-model="queryParams.regionId" placeholder="请选择区域" clearable>
          <el-option v-for="region in regions" :key="region.id" :label="region.name" :value="region.id" />
        </el-select>
      </el-form-item>

<!--      <el-form-item label="书籍数量" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          placeholder="请输入书籍数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
<!--      <el-form-item label="入馆时间" prop="entryDate">
        <el-date-picker clearable
          v-model="queryParams.entryDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择入馆时间">
        </el-date-picker>
      </el-form-item>-->
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
          v-hasPermi="['manage:book:add']"
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
          v-hasPermi="['manage:book:edit']"
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
          v-hasPermi="['manage:book:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:book:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="书籍ID" align="center" prop="id" />
      <el-table-column label="书籍名称" align="center" prop="name" />
      <el-table-column label="封面" align="center" prop="cover" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.cover" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="作者" align="center" prop="author" />
      <el-table-column label="出版社" align="center" prop="publisher" />
      <el-table-column label="ISBN" align="center" prop="isbn" />
      <el-table-column label="出版日期" align="center" prop="publishDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="类别ID" align="center" prop="categoryId" />
      <el-table-column label="区域ID" align="center" prop="regionId" />-->
<!--   改为显示类别、区域的内容   -->
      <el-table-column label="类别" align="center" prop="categoryName" />
      <el-table-column label="区域" align="center" prop="regionName" />

      <el-table-column label="书籍数量" align="center" prop="quantity" />
<!--      <el-table-column label="状态" align="center" prop="status" />-->
      <el-table-column label="状态" align="center" prop="statusName" />

      <el-table-column label="入馆时间" align="center" prop="entryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:book:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:book:remove']"
          >删除</el-button>
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

    <!-- 添加或修改书籍对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="书籍名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入书籍名称" />
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <image-upload v-model="form.cover"/>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="出版社" prop="publisher">
          <el-input v-model="form.publisher" placeholder="请输入出版社" />
        </el-form-item>
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="form.isbn" placeholder="请输入ISBN编号" />
        </el-form-item>
        <el-form-item label="出版日期" prop="publishDate">
          <el-date-picker clearable
            v-model="form.publishDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出版日期">
          </el-date-picker>
        </el-form-item>

<!--        <el-form-item label="类别ID" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入类别ID" />
        </el-form-item>-->
<!--    在对话框改为下拉框显示   -->
        <el-form-item label="类别" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择类别">
            <el-option v-for="category in categories" :key="category.id" :label="category.name" :value="category.id" />
          </el-select>
        </el-form-item>

<!--        <el-form-item label="区域ID" prop="regionId">
          <el-input v-model="form.regionId" placeholder="请输入区域ID" />
        </el-form-item>-->
<!--    改为下拉框显示   -->
        <el-form-item label="区域" prop="regionId">
          <el-select v-model="form.regionId" placeholder="请选择区域">
            <el-option v-for="region in regions" :key="region.id" :label="region.name" :value="region.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="书籍数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入书籍数量" />
        </el-form-item>
        <el-form-item label="入馆时间" prop="entryDate">
          <el-date-picker clearable
            v-model="form.entryDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择入馆时间">
          </el-date-picker>
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
//导入 request 模块
import request from '@/utils/request'
import { listBook, getBook, delBook, addBook, updateBook } from "@/api/manage/book";

export default {
  name: "Book",
  data() {
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
      // 书籍表格数据
      bookList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数

      //类别选项
      categories: [],
      //区域选项
      regions: [],

      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        cover: null,
        author: null,
        publisher: null,
        isbn: null,
        publishDate: null,
        categoryId: null,
        regionId: null,
        quantity: null,
        status: null,
        entryDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "书籍名称不能为空", trigger: "blur" }
        ],
        author: [
          { required: true, message: "作者不能为空", trigger: "blur" }
        ],
        publisher: [
          { required: true, message: "出版社不能为空", trigger: "blur" }
        ],
        isbn: [
          { required: true, message: "ISBN编号不能为空", trigger: "blur" }
        ],
        publishDate: [
          { required: true, message: "出版日期不能为空", trigger: "blur" }
        ],
        categoryId: [
          { required: true, message: "类别ID不能为空", trigger: "blur" }
        ],
        regionId: [
          { required: true, message: "区域ID不能为空", trigger: "blur" }
        ],
        quantity: [
          { required: true, message: "书籍数量不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        entryDate: [
          { required: true, message: "入馆时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.fetchCategories();
    this.fetchRegions();
  },
  methods: {
    /** 查询类别列表 */
    fetchCategories() {
      request({
        url: '/manage/category/list',
        method: 'get'
      }).then(response => {
        console.log('Categories response:', response); // 调试输出
        if (response.code === 200) {
          this.categories = response.rows;
        } else {
          this.$message.error('获取类别列表失败');
        }
      }).catch(error => {
        console.error('Fetch categories error:', error);
        this.$message.error('请求类别列表出错');
      });
    },
    /** 查询区域列表 */
    fetchRegions() {
      request({
        url: '/manage/region/list',
        method: 'get'
      }).then(response => {
        console.log('Regions response:', response); //调试输出
        if (response.code === 200) {
          this.regions = response.rows;
        } else {
          this.$message.error('获取区域列表失败');
        }
      }).catch(error => {
        console.error('Fetch regions error:', error);
        this.$message.error('请求区域列表出错');
      })
    },
    /** 查询书籍列表 */
    getList() {
      this.loading = true;
      listBook(this.queryParams).then(response => {
        this.bookList = response.rows;
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
        name: null,
        cover: null,
        author: null,
        publisher: null,
        isbn: null,
        publishDate: null,
        categoryId: null,
        regionId: null,
        quantity: null,
        status: null,
        entryDate: null,
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
      this.title = "添加书籍";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBook(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改书籍";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBook(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBook(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除书籍编号为"' + ids + '"的数据项？').then(function() {
        return delBook(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/book/export', {
        ...this.queryParams
      }, `book_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
