<template>
    <div class="card">
      <div class="card-body">
        <!-- 标题 -->
        <h5 class="card-title">{{ title }}</h5>
        <p class="card-text text-secondary">{{ descript }}</p>
  
        <hr class="border border-lightgrey opacity-75" />
  
        <!-- 图标区 -->
        <div class="icons">
          <div class="calendar">
            <img src="../assets/icons/calendar.svg" alt="日期" />
            {{ create_time }}
          </div>
  
          <div class="chat-dots">
            <button class="chat-btn" @click="showComments = !showComments">
              <img src="../assets/icons/chat-dots.svg" alt="评论" />
            </button>
            {{ chatDotsCount }}
          </div>
  
          <div class="likes">
            <button
              class="like-btn"
              :class="{ 'text-danger': liked }"
              @click="handleLike"
            >
            <svg 
                xmlns="http://www.w3.org/2000/svg" 
                width="16" height="16" 
                fill="currentColor" viewBox="0 0 16 16" class="heart"
            >
            <path 
                d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143q.09.083.176.171a3 3 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15"
            />
            </svg>
            </button>
            {{ likesCount }}
          </div>
        </div>
  
        <!-- 评论区 -->
        <div class="container text-center mt-4" v-if="showComments">
            <hr class="border border-lightgrey opacity-75" />
            <div class="row">
                <div class="col-1">
                <img
                    id="user-photo"
                    class="img-fluid rounded-circle"
                    src="../assets/images/photo.jpg"
                    alt="用户头像"
                />
            </div>
  
            <div class="col-11">
              <div class="form-floating">
                <textarea
                  id="floatingTextarea2"
                  v-model="commentText"
                  class="form-control"
                  placeholder="Leave a comment here"
                  style="height: 100px"
                ></textarea>
                <label for="floatingTextarea2">
                  开始发表宝贵的评论吧！
                </label>
              </div>
            </div>
          </div>
  
          <div class="row mt-2">
            <div class="col-12 text-end">
              <button
                type="button"
                class="btn btn-success"
                @click="sendComment"
              >
                发送评论
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>
  
<script setup>
import { ref } from 'vue'

const title = ref('springboot入门');
const descript = ref('从零开始的springboot入门文章');
const create_time = ref('2004-01-01');

const chatDotsCount = ref(15);
const likesCount = ref(15);
const liked = ref(false);

const showComments = ref(false);

const commentText = ref('');

const handleLike = () => {
    liked.value = !liked.value ;
    liked.value ? likesCount.value ++ : likesCount.value -- ;
}
</script>

<style scoped>
.icons {
    display: flex;
    gap: 30px;
    margin-top: 10px;
}

.chat-btn, .like-btn {
    border: none;
    background: none;
    padding: 0;
}

#user-photo {
    width: 50px;
    height: 50px;
    object-fit: cover;
}
</style>