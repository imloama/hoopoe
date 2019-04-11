<template>
  <div class="logo">
    <router-link :to="{name:'workplace'}">
      <img :src="logo" alt="logo" />
      <h1 v-if="showTitle">{{ title }}</h1>
    </router-link>
  </div>
</template>

<script>
import Logo from '@/assets/logo.png'


export default {
  name: 'Logo',
  data () {
    return {
      logo: Logo
    }
  },
  components: {
    
  },
  props: {
    title: {
      type: String,
      default: 'Hoopoe',
      required: false
    },
    showTitle: {
      type: Boolean,
      default: true,
      required: false
    }
  }
}
</script>
