<template>
  <div class="search-system">
    <!-- Header navigation -->
    <header class="header">
      <div class="header-left">
        <h1 class="system-title">统一检索系统</h1>
      </div>
      <div class="header-right">
        <div class="nav-links">
          <a href="#" class="nav-item">资源检索</a>
          <a href="#" class="nav-item">资源推荐</a>
          <a href="#" class="nav-item">资源导航</a>
          <a href="#" class="nav-item">资源浏览</a>
        </div>
        <div class="user-info">
          <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link">
                <el-avatar size="small" :src="userAvatar || require('@/assets/images/profile.jpg')"></el-avatar>
                <span class="username">{{ username }}</span>
                <i class="el-icon-arrow-down"></i>
              </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="userInfo">个人信息</el-dropdown-item>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <a href="#" class="setting-icon">
            <i class="el-icon-setting"></i>
          </a>
          <a href="#" class="help-icon">
            <i class="el-icon-question"></i>
          </a>
        </div>
      </div>
    </header>

    <!-- Main search area with background image -->
    <div class="search-container">
      <div class="search-area">
        <div class="search-bar">
          <div class="search-type">
            <el-dropdown trigger="click">
              <span class="el-dropdown-link">
                全部检索 <i class="el-icon-arrow-down"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>全部检索</el-dropdown-item>
                <el-dropdown-item>标题检索</el-dropdown-item>
                <el-dropdown-item>作者检索</el-dropdown-item>
                <el-dropdown-item>主题检索</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <div class="search-match">
            <el-dropdown trigger="click">
              <span class="el-dropdown-link">
                任意匹配 <i class="el-icon-arrow-down"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>任意匹配</el-dropdown-item>
                <el-dropdown-item>精确匹配</el-dropdown-item>
                <el-dropdown-item>模糊匹配</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <div class="search-input">
            <el-input placeholder="请输入想要的内容" v-model="searchText">
              <el-button slot="append" icon="el-icon-search" @click="search">检索</el-button>
            </el-input>
          </div>
<!--          <div class="advanced-search">
            <a href="#">高级检索</a>
          </div>-->
        </div>
      </div>
    </div>

    <!-- Resources section -->
    <div class="resources-section">
      <div class="resources-tabs">
        <ul class="tabs-list">
          <li class="tab-item active" @click="setActiveTab('popular')">热门借阅</li>
          <li class="tab-item" @click="setActiveTab('collection')">热门收藏</li>
          <li class="tab-item" @click="setActiveTab('highScore')">高分图书</li>
          <li class="view-all"><a href="#">查看全部</a></li>
        </ul>
      </div>

      <div class="book-list" v-if="activeTab === 'popular'">
        <el-row :gutter="20">
          <el-col :span="6" v-for="(book, index) in popularBooks" :key="index">
            <div class="book-item">
              <div class="book-cover">
                <img :src="book.cover" :alt="book.title">
              </div>
              <div class="book-info">
                <div class="book-title">{{ book.title }}</div>
                <div class="book-author">{{ book.author }}</div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>

      <div class="book-list" v-if="activeTab === 'collection'">
        <!-- Collection tab content -->
        <el-row :gutter="20">
          <el-col :span="6" v-for="(book, index) in collectionBooks" :key="index">
            <div class="book-item">
              <div class="book-cover">
                <img :src="book.cover" :alt="book.title">
              </div>
              <div class="book-info">
                <div class="book-title">{{ book.title }}</div>
                <div class="book-author">{{ book.author }}</div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>

      <div class="book-list" v-if="activeTab === 'highScore'">
        <!-- High score tab content -->
        <el-row :gutter="20">
          <el-col :span="6" v-for="(book, index) in highScoreBooks" :key="index">
            <div class="book-item">
              <div class="book-cover">
                <img :src="book.cover" :alt="book.title">
              </div>
              <div class="book-info">
                <div class="book-title">{{ book.title }}</div>
                <div class="book-author">{{ book.author }}</div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import { getUser } from "@/api/system/user";


export default {
  name: "MenuSearch",
  data() {
    return {
      searchText: "",
      activeTab: "popular",
      username: "",
      userAvatar: "",
      popularBooks: [
        {
          title: "明朝那些事儿",
          author: "当年明月",
          cover: "https://via.placeholder.com/150x200"
        },
        {
          title: "新版剑桥BEC考试真题集",
          author: "剑桥大学外语考试编著",
          cover: "https://via.placeholder.com/150x200"
        },
        {
          title: "平凡的世界",
          author: "路遥",
          cover: "https://via.placeholder.com/150x200"
        },
        {
          title: "那不勒斯四部曲",
          author: "(意大利) 埃莱娜·费兰特",
          cover: "https://via.placeholder.com/150x200"
        }
      ],
      collectionBooks: [
        {
          title: "战争与和平",
          author: "(俄) 列夫·托尔斯泰",
          cover: "https://via.placeholder.com/150x200"
        },
        {
          title: "设计心理学",
          author: "(美)唐纳德·诺曼",
          cover: "https://via.placeholder.com/150x200"
        },
        {
          title: "数学物理方程",
          author: "王明新",
          cover: "https://via.placeholder.com/150x200"
        },
        {
          title: "自适应控制",
          author: "韩晋宣",
          cover: "https://via.placeholder.com/150x200"
        }
      ],
      highScoreBooks: [
        {
          title: "百年孤独",
          author: "加西亚·马尔克斯",
          cover: "https://via.placeholder.com/150x200"
        },
        {
          title: "人类简史",
          author: "尤瓦尔·赫拉利",
          cover: "https://via.placeholder.com/150x200"
        },
        {
          title: "三体",
          author: "刘慈欣",
          cover: "https://via.placeholder.com/150x200"
        },
        {
          title: "概率论与数理统计",
          author: "陈希孺",
          cover: "https://via.placeholder.com/150x200"
        }
      ]
    };
  },
  created() {
    this.getUserInfo();
  },
  methods: {
    getUserInfo() {
      // 从若依框架获取当前登录用户信息
      this.username = this.$store.getters.name || "用户";
      this.userAvatar = this.$store.getters.avatar || "";
    },
    search() {
      // 处理搜索逻辑
      this.$message({
        message: "正在搜索: " + this.searchText,
        type: "info"
      });
      console.log("Searching for:", this.searchText);
    },
    setActiveTab(tab) {
      this.activeTab = tab;
    },
    handleCommand(command) {
      if (command === 'userInfo') {
        this.$router.push('/user/profile');
      } else if (command === 'logout') {
        this.$confirm('确定注销并退出系统吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$store.dispatch('LogOut').then(() => {
            location.href = '/index';
          });
        });
      }
    }
  }
};
</script>

<style scoped>
.search-system {
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", Arial, sans-serif;
  min-height: 100vh;
  background-color: #f5f5f5;
}

/* Header styles */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #263445;
  padding: 0 20px;
  height: 60px;
  color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-left {
  display: flex;
  align-items: center;
}

.system-title {
  font-size: 20px;
  font-weight: 500;
  margin: 0;
}

.header-right {
  display: flex;
  align-items: center;
}

.nav-links {
  display: flex;
  margin-right: 20px;
}

.nav-item {
  color: #fff;
  text-decoration: none;
  padding: 0 15px;
  font-size: 14px;
  transition: color 0.3s;
}

.nav-item:hover {
  color: #66b1ff;
}

.user-info {
  display: flex;
  align-items: center;
}

.username {
  margin: 0 8px;
  font-size: 14px;
}

.setting-icon, .help-icon {
  color: white;
  margin-left: 15px;
  font-size: 18px;
}

/* Search container styles */
.search-container {
  background-image: url("~@/assets/images/search-background.png");
  background-size: cover;
  background-position: center;
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 20px;
}

.search-area {
  width: 100%;
  max-width: 1000px;
}

.search-bar {
  display: flex;
  background-color: white;
  border-radius: 4px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.search-type, .search-match {
  padding: 0 15px;
  display: flex;
  align-items: center;
  border-right: 1px solid #e4e7ed;
  background-color: #f5f7fa;
  color: #606266;
  font-size: 14px;
  cursor: pointer;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  height: 40px;
}

.search-input {
  flex: 1;
}

.advanced-search {
  display: flex;
  align-items: center;
  padding: 0 15px;
  font-size: 14px;
}

.advanced-search a {
  color: #409EFF;
  text-decoration: none;
}

/* Resources section styles */
.resources-section {
  max-width: 1200px;
  margin: 30px auto;
  padding: 0 20px;
}

.resources-tabs {
  margin-bottom: 20px;
}

.tabs-list {
  display: flex;
  list-style: none;
  padding: 0;
  margin: 0;
  border-bottom: 1px solid #e4e7ed;
}

.tab-item {
  padding: 10px 20px;
  cursor: pointer;
  position: relative;
  font-size: 16px;
  color: #606266;
}

.tab-item.active {
  color: #409EFF;
  font-weight: 500;
}

.tab-item.active::after {
  content: '';
  position: absolute;
  bottom: -1px;
  left: 0;
  width: 100%;
  height: 2px;
  background-color: #409EFF;
}

.view-all {
  margin-left: auto;
  padding: 10px 0;
}

.view-all a {
  color: #909399;
  text-decoration: none;
  font-size: 14px;
}

/* Book list styles */
.book-list {
  margin-top: 20px;
}

.book-item {
  background-color: white;
  border-radius: 4px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
  margin-bottom: 20px;
}

.book-item:hover {
  transform: translateY(-5px);
}

.book-cover {
  height: 200px;
  overflow: hidden;
}

.book-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.book-info {
  padding: 15px;
}

.book-title {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 8px;
  color: #303133;
}

.book-author {
  font-size: 14px;
  color: #909399;
}
</style>
