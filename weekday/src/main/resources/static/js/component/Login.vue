<template>
  <div @click="change($event)" class="input-group-append">
    <span class="input-group-text" :class="{hide, show}"></span>
  </div>
</template>

<script>
module.exports = {
  data () {
    return {
      hide: true,
      show: false
    }
  },
  methods: {
    change: function (e) {
      let type = e.currentTarget.previousElementSibling.type
      if (type === 'text') {
        e.currentTarget.previousElementSibling.type = 'password'
        this.hide = true
        this.show = false
      } else {
        e.currentTarget.previousElementSibling.type = 'text'
        this.hide = false
        this.show = true
      }
    }
  }
}
</script>