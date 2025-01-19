<template>
    <div class="box1">
        <b-form @submit="onLogin" @reset="onReset" v-if="show">
            <h1>LOGIN</h1>
            <b-form-group id="input-group-1" label="id:" label-for="input-1" description="">
                <b-form-input id="input-1" v-model="form.id" type="id" placeholder="Enter id" required></b-form-input>
            </b-form-group>

            <b-form @submit.stop.prevent>
                <label for="text-password">Password</label>
                <b-form-input type="password" id="text-password" aria-describedby="password-help-block"
                    v-model="form.password"></b-form-input>
                <b-form-text id="password-help-block">
                </b-form-text>
            </b-form>
            <div class="buttonLocation">
                <b-button type="login" variant="primary" @click="login">Login</b-button>
                <b-button type="reset" variant="danger">Cencel</b-button>
            </div>
        </b-form>
    </div>
</template>

<script>
import Login from '@/views/Login.vue';
import axios from 'axios'; // axiosをインポート
import bcrypt from 'bcryptjs';
export default {
    data() {
        return {
            form: {
                id: '',
                password: ''
            },
            show: true
        }
    },
    computed: {
    },
    methods: {
        //awaitを使っている
        async onLogin(event) {
            event.preventDefault();

            // ロード中状態を設定
            this.isBusy = true;
            // パスワードをハッシュ化
            const salt = bcrypt.genSaltSync(10); // 同期的にソルトを生成
            const hashedPassword = bcrypt.hashSync(this.form.password); // パスワードをハッシュ化

            // ハッシュ化されたパスワードをフォームに設定
            const data = {
                id: this.form.id,
                password: hashedPassword
            };

            // postは非同期、ポストが戻ってくるまで待つのがawait
            //非同期だが同期にする。
            //非同期の他のメソッド fetch
            try {
                // 環境変数でエンドポイントを管理
                const response = await axios.post(`http://localhost:8080/login`, data);
                console.log(response);
                this.$router.push({
                    path: '/bulletin'
                })
            } catch (error) {
                console.error(error);
                // ユーザーにエラーメッセージを表示
                this.errorMessage = "ログインに失敗しました。IDまたはパスワードを確認してください。";
            } finally {
                // ロード中状態を解除
                this.isBusy = false;
                //login visibleがtrue
            }
        }
    },
    //postは非同期
    onReset(event) {
        event.preventDefault()
        // Reset our form values
        this.form.id = ''
        this.form.password = ''
        // Trick to reset/clear native browser form validation state
        this.show = false
        this.$nextTick(() => {
            this.show = true
        })
    },
}
</script>

<style src="/Users/arisaohta/Documents/Project/Bulletin/web/src/views/Login.css"></style>