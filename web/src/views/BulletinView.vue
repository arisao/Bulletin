<template>
    <div class="container">
        <p>My page</p>
        <div>
            <b-form inline class="form-container">
                <!-- 題名 -->
                <div class="d-flex align-items-center">
                    <p class="mb-0 mr-2">題名</p>
                    <input id="inline-form-input-name" v-model="title" type="text" placeholder="題名を入力して下さい"
                        class="mr-sm-2 form-control">
                </div>
                <!-- 日付 -->
                <div class="d-flex align-items-center">
                    <b-form-datepicker v-model="day2" class="mb-2 mr-2"></b-form-datepicker>
                    <p class="mb-0">~</p>
                    <b-form-datepicker v-model="day1" class="mb-2 mr-2"></b-form-datepicker>
                </div>
                <b-button variant="primary" v-on:click="search">検索</b-button>
                <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

            </b-form>

            <h6 class="totalCount">全{{ totalCount }}件</h6>
        </div>



        <!-- 新規登録 -->
        <div class="mt-3 d-flex justify-content-end"><b-button class="register" @click="registerArticle">新規登録</b-button>
        </div>
        <!-- 新規登録モーダル -->
        <b-modal id="modal-register" size="xl" title="記事タイトル">記事中身</b-modal>

        <!-- sticky-header : 스크롤로 테이블의 크기를 한정짓고 싶을 때 사용 ( 필자는 docs에서 해당옵션을 못보고 지나가 css로 삽질을 했음 ) -->
        <!-- busy : 로딩바로 사용 -->
        <div class="mt-3">
            <b-table :sticky-header="true" :items="items" :fields="fields" :busy="isBusy">
                <template #cell(edit)="row">
                    <b-button v-b-modal.modal-xl variant="primary" size="sm" @click="editItem(row.item)">修正</b-button>
                </template>
                <template #cell(delete)="row">
                    <b-button variant="danger" size="sm" @click="deleteItem(row.item)">削除</b-button>
                </template>
            </b-table>
        </div>
        <!-- 修正モーダル -->
        <b-modal ref="edit-modal" size="xl" hide-footer title="記事修正">
            <p>題名</p>
            <b-form-input id="inline-form-input-name" class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="題名を入力して下さい"></b-form-input>
            <p>内容</p>
            <b-form-input id="inline-form-input-name" class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="記事を入力して下さい"></b-form-input>
            <!-- ファイルインプット -->
            <!-- <b-form-group label="写真アップロード" label-cols-sm="2"> -->
            <p>写真アップロード</p>
            <b-form-file accept="image/jpeg, image/png, image/gif" id="file-default" @change="preview"></b-form-file>
            <!-- </b-form-group> -->

            <!-- ファイルプレビュー -->
            <div v-if="url" class="position-relative my-2">
                <img :src="url" class="border p-2" style="max-width: 100%;">
                <!-- Cancel -->
                <b-button type="button" variant="secondary border-light" class="position-absolute"
                    style="left: 0;top: 0;" @click="deleteImage">
                    削除
                </b-button>
                UploadedFile: {{ uploadedFile }}
            </div>

            <div class="d-flex justify-content-center">
                <b-button class="mt-3 mr-2" variant="primary" @click="edit">修正</b-button>
                <b-button class="mt-3" variant="secondary" @click="hideEditModal">閉じる</b-button>
            </div>
        </b-modal>

        <!-- 新規登録モーダル -->
        <b-modal ref="my-modal" size="xl" hide-footer title="新規記事登録">
            <p>題名</p>
            <b-form-input id="inline-form-input-name" class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="題名を入力して下さい"></b-form-input>
            <p>内容</p>
            <b-form-input id="inline-form-input-name" class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="記事を入力して下さい"></b-form-input>
            <!-- ファイルインプット -->
            <!-- <b-form-group label="写真アップロード" label-cols-sm="2"> -->
            <p>写真アップロード</p>
            <b-form-file accept="image/jpeg, image/png, image/gif" id="file-default" @change="preview"></b-form-file>
            <!-- </b-form-group> -->

            <!-- ファイルプレビュー -->
            <div v-if="url" class="position-relative my-2">
                <img :src="url" class="border p-2" style="max-width: 100%;">
                <!-- Cancel -->
                <b-button type="button" variant="secondary border-light" class="position-absolute"
                    style="left: 0;top: 0;" @click="deleteImage">
                    削除
                </b-button>
                UploadedFile: {{ uploadedFile }}
            </div>

            <div class="d-flex justify-content-center">
                <b-button class="mt-3 mr-2" variant="primary" @click="submit">登録</b-button>
                <b-button class="mt-3" variant="secondary" @click="hideModal">閉じる</b-button>
            </div>

        </b-modal>


        <!-- ページネーション -->
        <div class="mt-3">
            <h6 class="text-center"></h6>
            <b-pagination v-model="currentPage" :total-rows="rows" align="center"></b-pagination>
        </div>
    </div>
</template>

<script>
import axios from 'axios'; // axiosをインポート

export default {
    data() {
        const today = new Date();
        const yesterday = new Date(today);
        yesterday.setDate(today.getDate() - 1);

        return {
            text: '',
            day1: today.toISOString().slice(0, 10),
            day2: yesterday.toISOString().slice(0, 10),
            context: null,
            fields: ['title', 'contents', 'date', 'delete', 'edit'],
            items: [
                // { date: '2024/01/02', title: 'Dickerson', contents: 'Macdonald' },
                // { date: 21, title: 'Larsen', contents: 'Shaw' },
                // { date: 89, title: 'Geneva', contents: 'Wilson' }
            ],
            isBusy: false, // isBusyプロパティを追加してリアクティブなデータとして定義
            totalCount: 0,
            rows: 100,
            currentPage: 1,
            errorMessage: '',
            form: {
                image: null,
            },
            url: null,
            uploadFile: '',
            title: '',
            result: []
        }
    },
    //インスタンスが生成された後で実行される
    created() {
        console.log("....")
    },

    methods: {
        onContext({ detail }) {
            this.context = JSON.stringify(detail, null, 2);
        },
        //バリデーションチェック用メソッド
        formCheck() {
            const date1 = new Date(this.day1);
            const date2 = new Date(this.day2);

            if (date1 < date2) {
                this.errorMessage = "最初に選択した日付よりも後の日付を選択することはできません";
            } else {
                this.errorMessage = ''; // エラーメッセージをクリア
            }
        },
        search() {
            // 入力した値を格納する
            this.form = {
                title: this.title,
                startDate: this.day2,
                endDate: this.day1
            };
            console.log(this.form);

            // バリデーションチェックのメソッドを呼び出す
            this.formCheck();

            this.isBusy = true; // データ取得中にロード中の状態を設定
            axios.post('http://localhost:8080/bulletin', this.form)
                .then(response => {
                    // レスポンスデータをitemsに格納
                    this.items = response.data.map(item => {
                        return {
                            date: item.date, // レスポンスのdateフィールドに合わせて変更
                            title: item.title,
                            contents: item.contents
                        };
                    });
                    console.log(response);
                })
                .catch(error => {
                    // エラーを処理する（必要に応じて）
                    console.error(error);
                })
                .finally(() => {
                    this.isBusy = false; // データ取得完了後にロード中の状態を解除
                });
        },
        registerArticle() {
            this.$refs['my-modal'].show()
        },
        deleteItem(item) {
            alert('本当に削除しますか')
            confirm('記事を削除しました')
            // 削除のロジックをここに追加
            console.log("Delete item:", item);
        },
        editItem(item) {
            // 修正のロジックをここに追加
            console.log("Edit item:", item);
            this.$refs['edit-modal'].show();
        },
        // 画像のプレビューを表示するメソッド
        preview(event) {
            const files = event.target.files || event.dataTransfer.files
            this.form.image = files[0]
            this.url = URL.createObjectURL(this.form.image)
        },
        deleteImage() {
            this.form.image = null
            this.url = null
        },
        hideModal() {
            this.form.image = null
            this.url = null
            this.$refs['my-modal'].hide()
        },
        submit(event) {
            event.preventDefault()
            const formData = new FormData()
            formData.append('image', this.form.image)
            axios.post('/api/form/upload', formData)
                .then((res) => {
                    console.log(res)
                    // アップロード処理
                    this.uploadedFile = res.data.filename
                })
                .catch((err) => {
                    console.log(err)
                    // エラー処理
                })

        },
        edit(event) {
            confirm('修正しますか？');
            event.preventDefault()
            const formData = new FormData()
            formData.append('image', this.form.image)
            axios.post('/api/form/upload', formData)
                .then((res) => {
                    console.log(res)
                    // アップロード処理
                    this.uploadedFile = res.data.filename
                })
                .catch((err) => {
                    console.log(err)
                    // エラー処理
                })

        },
        hideEditModal() {
            this.form.image = null
            this.url = null
            this.$refs['edit-modal'].hide()
        },
    }
}
</script>

<style
    src="/Users/arisaohta/Documents/workspace-spring-tool-suite-4-4.15.1.RELEASE/Bulletin/web/src/views/BulletinView.css"></style>
