<template>
    <div class="box1">
        <b-form @submit="onLogin" v-if="show">
            <h1>LOGIN</h1>
            <b-form-group id="input-group-1" label="ID:" label-for="input-1">
                <b-form-input id="input-1" v-model="form.id" type="text" placeholder="Enter ID" required></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-2" label="Password:" label-for="text-password">
                <b-form-input type="password" id="text-password" v-model="form.password" required></b-form-input>
            </b-form-group>

            <div class="buttonLocation">
                <b-button type="submit" variant="primary">Login</b-button>
                <b-button type="button" variant="danger" @click="onReset">Cancel</b-button>
            </div>
        </b-form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            form: {
                id: '',
                password: ''
            },
            show: true,
            isBusy: false,
            errorMessage: ""
        };
    },
    methods: {
        async onLogin(event) {
            event.preventDefault();
            this.isBusy = true;

            console.log("Before hashing:", this.form.password); // ← ここで確認

            const uint8 = new TextEncoder().encode(this.form.password);
            const digest = await crypto.subtle.digest('SHA-256', uint8);
            const hashedPassword = Array.from(new Uint8Array(digest))
                .map(v => v.toString(16).padStart(2, '0'))
                .join('');

            console.log("After hashing:", hashedPassword); // ← ここで確認

            const data = {
                id: this.form.id,
                password: hashedPassword
            };

            try {
                const response = await axios.post(`http://localhost:8080/login`, data);
                console.log(response);
                this.$router.push({ name: 'BulletinView' });
            } catch (error) {
                console.error(error);
                this.errorMessage = "ログインに失敗しました。IDまたはパスワードを確認してください。";
            } finally {
                this.isBusy = false;
            }
        },
        onReset() {
            console.log("Resetting form:", this.form);
            this.form.id = '';
            this.form.password = '';
            this.show = false;
            this.$nextTick(() => {
                this.show = true;
            });
        }
    }
};
</script>

<style src="@/views/Login.css"></style>