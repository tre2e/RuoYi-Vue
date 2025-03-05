<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="书籍名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入书籍名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版社" prop="publisher">
        <el-input
          v-model="queryParams.publisher"
          placeholder="请输入出版社"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="ISBN编号" prop="isbn">
        <el-input
          v-model="queryParams.isbn"
          placeholder="请输入ISBN编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版日期" prop="publishDate">
        <el-date-picker clearable
          v-model="queryParams.publishDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择出版日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="类别ID" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入类别ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="区域ID" prop="regionId">
        <el-input
          v-model="queryParams.regionId"
          placeholder="请输入区域ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="书籍数量" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          placeholder="请输入书籍数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入馆时间" prop="entryDate">
        <el-date-picker clearable
          v-model="queryParams.entryDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择入馆时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['manage:book:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:book:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:book:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:book:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="书籍ID" align="center" prop="id" />
      <el-table-column label="书籍名称" align="center" prop="name" />
      <el-table-column label="封面" align="center" prop="cover" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.cover" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="作者" align="center" prop="author" />
      <el-table-column label="出版社" align="center" prop="publisher" />
      <el-table-column label="ISBN编号" align="center" prop="isbn" />
      <el-table-column label="出版日期" align="center" prop="publishDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.publishDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="类别ID" align="center" prop="categoryId" />
      <el-table-column label="区域ID" align="center" prop="regionId" />
      <el-table-column label="书籍数量" align="center" prop="quantity" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="入馆时间" align="center" prop="entryDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.entryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:book:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:book:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改书籍对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="bookRef" :model="form" :rules="rules" label-width="80px">
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
        <el-form-item label="ISBN编号" prop="isbn">
          <el-input v-model="form.isbn" placeholder="请输入ISBN编号" />
        </el-form-item>
        <el-form-item label="出版日期" prop="publishDate">
          <el-date-picker clearable
            v-model="form.publishDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择出版日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="类别ID" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入类别ID" />
        </el-form-item>
        <el-form-item label="区域ID" prop="regionId">
          <el-input v-model="form.regionId" placeholder="请输入区域ID" />
        </el-form-item>
        <el-form-item label="书籍数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入书籍数量" />
        </el-form-item>
        <el-form-item label="入馆时间" prop="entryDate">
          <el-date-picker clearable
            v-model="form.entryDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择入馆时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Book">
import { listBook, getBook, delBook, addBook, updateBook } from "@/api/manage/book";

const { proxy } = getCurrentInstance();

const bookList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
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
});

const { queryParams, form, rules } = toRefs(data);

/** 查询书籍列表 */
function getList() {
  loading.value = true;
  listBook(queryParams.value).then(response => {
    bookList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
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
  proxy.resetForm("bookRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加书籍";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getBook(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改书籍";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["bookRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateBook(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addBook(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除书籍编号为"' + _ids + '"的数据项？').then(function() {
    return delBook(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/book/export', {
    ...queryParams.value
  }, `book_${new Date().getTime()}.xlsx`)
}

getList();
</script>
