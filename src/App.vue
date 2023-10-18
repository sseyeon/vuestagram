<template>
  <div class="header">
    <ul class="header-button-left">
      <li v-if="step != 0" @click="step = 0">Cancel</li>
    </ul>
    <ul class="header-button-right">
      <li v-if="step == 1" @click="step++">Next</li>
      <li v-if="step == 2" @click="publish">발행</li>
    </ul>
    <img src="./assets/logo.png" class="logo" />
  </div>
  <Container @write="작성한글 = $event" /> 
  <Container :게시물="게시물" :step="step" :imgURL="imgURL" />
  <button v-if="step == 0" @click="more">더보기</button>
  <div v-if="step == 0" class="footer">
    <ul class="footer-button-plus">
      <input @change="upload" type="file" id="file" class="inputfile" />
      <label for="file" class="input-plus">+</label>
    </ul>
  </div>
</template>

<script>
import Container from "./components/Container.vue";
import postdata from "./assets/data.js";
import axios from "axios";
export default {
  name: "App",
  data() {
    return {
      게시물: postdata,
      count: 0,
      step: 0,
      imgURL: "",
      작성한글: "",
    };
  },
  components: {
    Container: Container,
  },
  methods: {
    publish() {
      var myPost = {
        name: "SeYeon Jeong",
        userImage: "https://picsum.photos/100?random=22",
        postImage: this.imgURL,
        likes: Math.ceil(Math.random() * 100),
        date: "Oct 2",
        liked: false,
        content: this.작성한글,
        filter: "perpetua",
      };
      this.게시물.unshift(myPost);
      this.step = 0;
    },
    more() {
      axios
        .get(`https://codingapple1.github.io/vue/more${this.count}.json`)
        .then((res) => {
          this.게시물.push(res.data);
          this.count++;
        });
    },
    upload(e) {
      let 파일 = e.target.files;
      let url = URL.createObjectURL(파일[0]);
      this.step = 1;
      this.imgURL = url;
    },
  },
};
</script>

<style>
body {
  margin: 0;
}
ul {
  padding: 5px;
  list-style-type: none;
}
.logo {
  width: 22px;
  margin: auto;
  display: block;
  position: absolute;
  left: 0;
  right: 0;
  top: 13px;
}
.header {
  width: 100%;
  height: 40px;
  background-color: white;
  padding-bottom: 8px;
  position: sticky;
  top: 0;
}
.header-button-left {
  color: skyblue;
  float: left;
  width: 50px;
  padding-left: 20px;
  cursor: pointer;
  margin-top: 10px;
}
.header-button-right {
  color: skyblue;
  float: right;
  width: 50px;
  padding-left: 20px;
  cursor: pointer;
  margin-top: 10px;
}
.footer {
  width: 100%;
  position: sticky;
  bottom: 0;
  padding-bottom: 10px;
  background-color: white;
}
.footer-button-plus {
  width: 80px;
  margin: auto;
  text-align: center;
  cursor: pointer;
  font-size: 24px;
  padding-top: 12px;
}
.sample-box {
  width: 100%;
  height: 600px;
  background-color: white;
}
.inputfile {
  display: none;
}
.input-plus {
  cursor: pointer;
}
#app {
  box-sizing: border-box;
  font-family: "consolas";
  margin-top: 60px;
  width: 100%;
  max-width: 460px;
  margin: auto;
  position: relative;
  border-right: 1px solid #eee;
  border-left: 1px solid #eee;
}
</style>
