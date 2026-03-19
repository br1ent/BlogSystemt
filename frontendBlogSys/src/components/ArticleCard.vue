<template>
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">{{ title }}</h5>
        <p class="card-text text-secondary">{{ descript }}</p>
        <hr class="border border-dark opacity-75">
        <div class="icons">
          <div class="calendar">
            <img src="../assets/icons/calendar.svg" alt="日期">
            {{ create_time }}
          </div>
          <div class="chat-dots">
            <button>
              <img src="../assets/icons/chat-dots.svg" alt="评论">
            </button>
            {{ chatDotsCount }}
          </div>
          <div class="likes">
            <button @click="handleLike">
              <img src="../assets/icons/suit-heart.svg" alt="点赞量" v-bind="liked">
            </button>
            {{ likesCount }}
          </div>
        </div>
      </div>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue'

const title = ref("springboot入门");
const descript = ref("从零开始的springboot入门文章");
const create_time = ref("2004-01-01");
let chatDotsCount = ref(15);
let likesCount = ref(15)
let liked = false;


const handleLike = () => {
    if (likesCount.value > 0) {
        if (liked) {
            likesCount.value -- ;
            liked = false;
        } else {
            likesCount.value ++ ;
            liked = true;
        }
    } else if (likesCount.value === 0) {
        likesCount.value ++ ;
        liked = true;
    }
}

</script>

<style scoped>
div.icons {
  display: flex;
  gap: 30px;
  margin-top: 10px;
}
  
p.text-secondary {
  font-size: 13px;
}

button {
  border: none;
  background-color: white;
  width: 23px;
}
</style>