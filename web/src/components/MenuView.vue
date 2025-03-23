<template lang="">
<div>
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-navbar-brand href="#">bulletin</b-navbar-brand>
  
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
  
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#">Link</b-nav-item>
          <b-nav-item href="#" disabled>Disabled</b-nav-item>
        </b-navbar-nav>
  
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-form>
            <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
            <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
          </b-nav-form>
  
          <b-nav-item-dropdown text="Lang" right>
            <b-dropdown-item href="#">English</b-dropdown-item>
            <b-dropdown-item href="#">日本語</b-dropdown-item>
            <b-dropdown-item href="#">한국어</b-dropdown-item>
          </b-nav-item-dropdown>
  
          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em>{{ userId }}</em>
            </template>
<b-dropdown-item href="#">Profile</b-dropdown-item>
<b-dropdown-item href="#" @click="logout">Sign Out</b-dropdown-item>
</b-nav-item-dropdown>
</b-navbar-nav>
</b-collapse>
</b-navbar>
</div>
</template>
<script>
import { onMounted } from 'vue';
import axios from 'axios';
export default {
  methods: {
    logout() {
      console.log("ログアウト");
      console.log(document.cookie);
      axios.get(`http://localhost:8080/logout`, { withCredentials: true })
        .then((res) => {
          console.log(res);
          //クッキーを消しただけではセッション情報は消えない。
          document.cookie = "JSESSIONID=; Path=/; HttpOnly; Max-Age=0;";
          //sessionStorageからuserIdを消す
          sessionStorage.removeItem("userId");
          this.$router.push("/login");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // getUserId() {
    //   const userId = sessionStorage.getItem('userId');
    //   console.log("ユーザー");
    //   console.log(userId);
    // },
    // window: onload = function () {
    //   console.log("onload");
    // }
  }
}
</script>
<script setup>
import { ref, onMounted } from 'vue';

const userId = ref(null);

//サーバー
//１ページ１ページ生成していない。１ページ目の段階で必要な資材をダウンロード。
//画面を作り替えている domはhtmlのタグ。
onMounted(() => {
  console.log('ああああ');
  userId.value = sessionStorage.getItem('userId');
  console.log(userId.value);
});
</script>
<style lang="">

</style>
