<template>
    <div>
        <component :is="loginType" />
    </div>
</template>

<script>
import GoogleLogin from "@/components/GoogleLogin.vue";
import BasicLogin from "@/components/BasicLogin.vue";

export default {
    components: {
        GoogleLogin,
        BasicLogin,
    },
    computed: {
        loginType() {
            return this.$route.query.type === 'google' ? 'GoogleLogin' : 'BasicLogin';
        },
    },
};
</script>