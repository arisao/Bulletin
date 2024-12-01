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
                <b-button type="login" variant="primary">Login</b-button>
                <b-button type="reset" variant="danger">Cencel</b-button>
            </div>
        </b-form>
    </div>
</template>

<script>
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
    methods: {
        onLogin(event) {
            event.preventDefault()
            alert(JSON.stringify(this.form))
        },
        onReset(event) {
            event.preventDefault()
            // Reset our form values
            this.form.id = ''
            // Trick to reset/clear native browser form validation state
            this.show = false
            this.$nextTick(() => {
                this.show = true
            })
        }
    }
}
</script>

<style src="/Users/arisaohta/Documents/Project/Bulletin/web/src/views/Login.css"></style>