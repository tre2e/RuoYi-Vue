<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="邀请码" prop="inviteCode">
        <el-input
          v-model="queryParams.inviteCode"
        placeholder="请输入邀请码"
        clearable
        @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="使用者ID" prop="usedBy">
        <el-input
          v-model="queryParams.usedBy"
          placeholder="请输入使用者的ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="过期时间" prop="expireTime">
        <el-date-picker
          v-model="queryParams.expireTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择过期时间"
          clearable
        />
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
          v-hasPermi="['manage:invitecode:add']"
        >生成邀请码</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:invitecode:edit']"
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
          v-hasPermi="['manage:invitecode:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:invitecode:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="inviteCodeList" @selection-change="handleSelectionChange" border>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="80" />
      <el-table-column label="邀请码" align="center" prop="inviteCode" width="150" />
      <el-table-column label="是否已使用" align="center" prop="isUsed" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isUsed === 1 ? 'info' : 'success'">
            {{ scope.row.isUsed === 1 ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="使用者ID" align="center" prop="usedBy" width="80" />
      <el-table-column label="用户名" align="center" prop="userName" width="100" />
      <el-table-column label="使用时间" align="center" prop="usedTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.usedTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="过期时间" align="center" prop="expireTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expireTime, '{y}-{m}-{d}') || '永不过期' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:invitecode:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:invitecode:remove']"
          >删除</el-button>
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

    <!-- 修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="邀请码" prop="inviteCode"> <!-- 修改为 inviteCode -->
          <el-input v-model="form.inviteCode" disabled />
        </el-form-item>
        <el-form-item label="使用者ID" prop="usedBy">
          <el-input v-model="form.usedBy" placeholder="请输入使用者ID" />
        </el-form-item>
        <el-form-item label="使用时间" prop="usedTime">
          <el-date-picker
            v-model="form.usedTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择使用时间"
            clearable
          />
        </el-form-item>
        <el-form-item label="过期时间" prop="expireTime">
          <el-date-picker
            v-model="form.expireTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择过期时间"
            clearable
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
import { listInvitecode, getInvitecode, delInvitecode, addInvitecode, updateInvitecode } from "@/api/manage/invitecode";

export default {
  name: "InviteCode",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      inviteCodeList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inviteCode: null,
        isUsed: null,
        usedBy: null,
        usedTime: null,
        expireTime: null,
      },
      form: {},
      rules: {
        usedBy: [{ required: false, message: "使用者ID可以为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询邀请码列表 */
    getList() {
      this.loading = true;
      listInvitecode(this.queryParams).then((response) => {
        this.inviteCodeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: null,
        inviteCode: null,
        isUsed: null,
        usedBy: null,
        usedTime: null,
        expireTime: null,
        createBy: null,
        createTime: null,
        updateTime: null,
        remark: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 生成邀请码 */
    handleAdd() {
      addInvitecode().then((response) => {
        this.$modal.msgSuccess(`生成邀请码成功: ${response.data}`);
        this.getList();
      }).catch((error) => {
        this.$modal.msgError("生成邀请码失败: " + (error.msg || "未知错误"));
      });
    },
    /** 修改按钮 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getInvitecode(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改邀请码";
      });
    },
    /** 提交修改 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          updateInvitecode(this.form).then((response) => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm(`是否确认删除邀请码 "${ids}"？`)
        .then(() => delInvitecode(ids))
    .then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
        .catch(() => {});
    },
    /** 导出按钮 */
    handleExport() {
      this.download("manage/code/export", { ...this.queryParams }, `invitecode_${new Date().getTime()}.xlsx`);
    },
  },
};
</script>

<style scoped>
.mb8 {
  margin-bottom: 8px;
}
</style>
