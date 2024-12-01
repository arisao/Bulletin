<template>
    <div>
        <div id="google-signin-button"></div>
        <p v-if="user">{{ user.name }} としてログインしています。</p>
        <b-button v-if="user" @click="signOut">ログアウト</b-button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            user: null, // ユーザー情報を格納
        };
    },
    mounted() {
        this.initializeGoogleSignIn();
    },
    methods: {
        initializeGoogleSignIn() {
            const clientId = "404430356219-3unlhvnav810crsd8bpcqv7h59afjh4q.apps.googleusercontent.com";
            const script = document.createElement("script");
            script.src = "https://accounts.google.com/gsi/client";
            script.onload = () => {
                window.google.accounts.id.initialize({
                    client_id: clientId,
                    callback: this.handleCredentialResponse,
                });
                window.google.accounts.id.renderButton(
                    document.getElementById("google-signin-button"),
                    { theme: "outline", size: "large" }
                );
            };
            document.head.appendChild(script);
        },
        handleCredentialResponse(response) {
            const token = response.credential;
            const payload = JSON.parse(atob(token.split(".")[1])); // デコードしてユーザー情報を取得
            this.user = {
                name: payload.name,
                email: payload.email,
                picture: payload.picture,
            };
        },
        signOut() {
            this.user = null;
            alert("ログアウトしました。");
        },
    },
};
</script>

<style>
#google-signin-button {
    margin: 50spx 0;
}
</style>