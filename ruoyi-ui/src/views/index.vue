<template>
  <div class="app-container">
    <header class="header">
      <div class="logo-container">
        <h1 class="logo">上理信息安全图书角</h1>
        <span class="version">v{{ version }}</span>
      </div>
      <nav class="navigation">
        <ul>
          <li><a href="#" @click="navigateTo('home')">首页</a></li>
          <li><a href="#" @click="navigateTo('books')">图书列表</a></li>
          <li><a href="#" @click="navigateTo('new')">最新上架</a></li>
          <li><a href="#" @click="navigateTo('about')">关于我们</a></li>
        </ul>
      </nav>
    </header>

    <main class="main-content">
      <section class="hero">
        <h2>欢迎访问上理信息安全图书角</h2>
        <p>探索信息安全领域的前沿知识，提升专业技能与理论素养</p>
        <button class="cta-button" @click="$router.push('/search/common/')">浏览图书</button>
      </section>

      <section class="features">
        <div class="feature-card">
          <div class="feature-icon">📚</div>
          <h3>专业精选</h3>
          <p>精心挑选信息安全领域的优质书籍，覆盖密码学、网络安全、系统安全等多个方向</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">🔍</div>
          <h3>便捷检索</h3>
          <p>多维度的图书分类与搜索功能，帮助您快速找到所需资源</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">🔄</div>
          <h3>定期更新</h3>
          <p>持续引入行业最新出版物，保持知识库的时效性与前瞻性</p>
        </div>
      </section>

      <section class="recent-books">
        <h2>最新上架</h2>
        <div class="book-list">
          <!-- 这里可以使用v-for循环渲染最新上架的书籍 -->
          <div class="book-card" v-for="(book, index) in recentBooks" :key="index">
            <div class="book-cover" :style="{ backgroundColor: book.color }">
              <span>{{ book.title.charAt(0) }}</span>
            </div>
            <h4>{{ book.title }}</h4>
            <p>{{ book.author }}</p>
          </div>
        </div>
      </section>
    </main>

    <footer class="footer">
      <p>&copy; {{ new Date().getFullYear() }} 上理信息安全图书角 | 版本 {{ version }}</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.9",
      // 示例数据：最新上架书籍
      recentBooks: [
        {title: "网络安全态势感知", author: "张三", color: "#4e79a7"},
        {title: "密码学原理与实践", author: "李四", color: "#f28e2c"},
        {title: "Web渗透测试指南", author: "王五", color: "#e15759"},
        {title: "安全开发生命周期", author: "赵六", color: "#76b7b2"}
      ]
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    navigateTo(section) {
      // 页面导航逻辑
      console.log(`导航至: ${section}`);
      // 这里可以添加实际的路由导航
      if (section === 'books') {
        this.$router.push('/search/common/');
      } else {
        // 其他导航逻辑
      }
    }
  }
};
</script>

<style scoped lang="scss">
// 颜色变量
$primary-color: #3c88c2;
$secondary-color: #f5f5f5;
$text-color: #333;
$text-light: #666;
$accent-color: #e74c3c;
$border-color: #eee;

// 主容器样式
.app-container {
  font-family: "Helvetica Neue", "PingFang SC", "Microsoft YaHei", sans-serif;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  color: $text-color;
}

// 头部样式
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0;
  border-bottom: 1px solid $border-color;
  margin-bottom: 30px;

  .logo-container {
    display: flex;
    align-items: baseline;

    .logo {
      font-size: 24px;
      font-weight: 700;
      margin: 0;
      color: $primary-color;
    }

    .version {
      margin-left: 10px;
      font-size: 12px;
      color: $text-light;
    }
  }

  .navigation {
    ul {
      display: flex;
      list-style: none;
      margin: 0;
      padding: 0;

      li {
        margin-left: 30px;

        a {
          text-decoration: none;
          color: $text-color;
          font-size: 16px;
          transition: color 0.3s;

          &:hover {
            color: $primary-color;
          }
        }
      }
    }
  }
}

// 主要内容区域
.main-content {
  min-height: 70vh;
}

// 英雄区域
.hero {
  text-align: center;
  padding: 60px 0;
  background-color: $secondary-color;
  border-radius: 8px;
  margin-bottom: 40px;

  h2 {
    font-size: 32px;
    font-weight: 700;
    margin-bottom: 16px;
    color: $primary-color;
  }

  p {
    font-size: 18px;
    color: $text-light;
    max-width: 600px;
    margin: 0 auto 30px;
  }

  .cta-button {
    background-color: $primary-color;
    color: white;
    border: none;
    padding: 12px 30px;
    font-size: 16px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;

    &:hover {
      background-color: darken($primary-color, 10%);
    }
  }
}

// 特性区域
.features {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 30px;
  margin-bottom: 60px;

  .feature-card {
    padding: 30px;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
    transition: transform 0.3s, box-shadow 0.3s;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    }

    .feature-icon {
      font-size: 36px;
      margin-bottom: 20px;
    }

    h3 {
      font-size: 20px;
      margin-bottom: 15px;
    }

    p {
      color: $text-light;
      line-height: 1.6;
    }
  }
}

// 最新上架书籍
.recent-books {
  margin-bottom: 60px;

  h2 {
    font-size: 24px;
    margin-bottom: 20px;
    position: relative;
    padding-bottom: 10px;

    &:after {
      content: "";
      position: absolute;
      bottom: 0;
      left: 0;
      width: 50px;
      height: 3px;
      background-color: $primary-color;
    }
  }

  .book-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
    gap: 25px;

    .book-card {
      border-radius: 8px;
      overflow: hidden;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
      transition: transform 0.3s;

      &:hover {
        transform: translateY(-5px);
      }

      .book-cover {
        height: 160px;
        display: flex;
        align-items: center;
        justify-content: center;

        span {
          font-size: 48px;
          color: white;
          font-weight: bold;
        }
      }

      h4 {
        padding: 15px 15px 5px;
        margin: 0;
        font-size: 16px;
      }

      p {
        padding: 0 15px 15px;
        margin: 0;
        color: $text-light;
        font-size: 14px;
      }
    }
  }
}

// 页脚
.footer {
  text-align: center;
  padding: 30px 0;
  border-top: 1px solid $border-color;
  color: $text-light;
  font-size: 14px;
}

// 响应式布局
@media (max-width: 768px) {
  .header {
    flex-direction: column;

    .navigation {
      margin-top: 20px;
      width: 100%;

      ul {
        justify-content: space-between;

        li {
          margin: 0;
        }
      }
    }
  }

  .hero {
    padding: 40px 20px;

    h2 {
      font-size: 28px;
    }
  }
}
</style>
