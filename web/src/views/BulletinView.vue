<template>
    <div class="container">
        <p>My page</p>
        <div>
            <b-form inline class="form-container">
                <!-- 題名 -->
                <div class="d-flex align-items-center">
                    <p class="mb-0 mr-2">題名</p>
                    <input id="inline-form-input-name" v-model="searchTitle" type="text" placeholder="題名を入力して下さい"
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
        <!-- sticky-header : スクロールさせたい時は、trueに変更する -->
        <!-- busy : ローディングをすぐに使用する -->
        <div class="mt-3">
            <b-table :sticky-header="false" :items="items" :fields="fields" :busy="isBusy" :per-page="perPage"
                :current-page="currentPage">
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
            <b-form-group label="題名" label-for="inline-form-input-name">
                <b-form-input id="inline-form-input-name" v-model="title" :state="validation2"
                    class="mb-2 mr-sm-2 mb-sm-0"></b-form-input>
                <b-form-invalid-feedback :state="validation2">
                    題名は1-50文字以内で入力して下さい
                </b-form-invalid-feedback>
            </b-form-group>
            <b-form-group label="記事" label-for="inline-form-input-article">
                <b-form-input id="inline-form-input-article" v-model="contents" placeholder="記事を入力して下さい" rows="3"
                    :state="validation3"></b-form-input>
                <b-form-invalid-feedback :state="validation3">
                    題名は1-50文字以内で入力して下さい
                </b-form-invalid-feedback>
            </b-form-group>
            <p>写真アップロード</p>
            <b-form-file accept="image/jpeg, image/png, image/gif" id="file-default" @change="preview"></b-form-file>
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
            <img v-if="image" :src="image" alt="Article Image" style="max-width: 100%;">

            <div class="d-flex justify-content-center">
                <b-button class="mt-3 mr-2" variant="primary" @click="edit">修正</b-button>
                <b-button class="mt-3" variant="secondary" @click="hideEditModal">閉じる</b-button>
            </div>
        </b-modal>

        <!-- 新規登録モーダル -->
        <b-modal ref="my-modal" size="xl" hide-footer title="新規記事登録">
            <b-form-group label="題名" label-for="inline-form-input-name">
                <b-form-input id="inline-form-input-name" v-model="newTitle" :state="validation"
                    class="mb-2 mr-sm-2 mb-sm-0" placeholder="題名を入力して下さい"></b-form-input>
                <b-form-invalid-feedback :state="validation">
                    題名は1-50文字以内で入力して下さい
                </b-form-invalid-feedback>
            </b-form-group>
            <b-form-group label="記事" label-for="inline-form-input-article" :state="!isInvalid"
                invalid-feedback="記事は1-1000文字で入力して下さい">
                <b-form-textarea id="inline-form-input-article" v-model="newContents" placeholder="記事を入力して下さい" rows="3"
                    :state="!isInvalid" @input="validate"></b-form-textarea>
            </b-form-group>
            <!-- ファイルインプット -->
            <p>写真アップロード</p>
            <b-form-file accept="image/jpeg, image/png, image/gif" id="file-default" @change="preview"></b-form-file>

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
            <b-pagination v-model="currentPage" :total-rows="totalCount" :per-page="perPage" align="center"
                @change="pageChanged"></b-pagination>

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
            //一ページの最大表示数
            perPage: 10,
            //現在のページ
            currentPage: 1,
            text: '',
            day1: today.toISOString().slice(0, 10),
            day2: yesterday.toISOString().slice(0, 10),
            context: null,
            fields: ['title', 'contents', 'create_date', 'delete', 'edit'],
            items: [],
            isBusy: false, // isBusyプロパティを追加してリアクティブなデータとして定義
            totalCount: 0,
            errorMessage: '',
            url: null,
            editUrl: null,
            title: '',
            searchTitle: '',
            contents: '',
            newTitle: '',
            newContents: '',
            articleForm: {
                newTitle: '',
                newContents: '',
                image: null,
            },
            editArticleForm: {
                title: '',
                contents: '',
                image: null,
            },
            result: [],
            image: '',
            isInvalid: true,
            isInvalid2: true,
            offset: '0',
            seq: null,
            imgData: {
                image: null,
            },
            image: '',
            currentItem: null,
            uploadedFile: '',
        }
    },
    //インスタンスが生成された後で実行される
    created() {
        console.log("....")
    },
    computed: {
        validation() {
            return this.newTitle != "" && this.newTitle.length < 51
        },
        validation2() {
            return this.title != "" && this.title.length < 51
        },
        validation3() {
            return this.contents != "" && this.contents.length < 1000
        },
        rows() {
            return this.items.length
        }
    },
    methods: {
        validate() {
            this.isInvalid = this.newContents.length < 0 || this.newContents.length > 1000;
        },
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

        registerArticle() {
            this.$refs['my-modal'].show()
        },
        deleteItem(item) {
            if (confirm('本当に削除しますか')) {
                console.log(item);
                this.form = {
                    seq: item.seq
                };
                axios.put('http://localhost:8080/delete/' + item.seq, this.form)
                    .then((res) => {
                        console.log(res);
                        alert('記事を削除しました');
                        this.search();
                    })
                    .catch((err) => {
                        console.log(err);
                        // エラー処理
                    });
                console.log("Delete item:", item);
            }

        },
        //修正モーダルを開く
        editItem(item) {
            console.log("Edit item:", item);
            this.getArticle(item);
            this.$refs['edit-modal'].show();

        },
        // 画像のプレビューを表示するメソッド
        preview(event) {
            const files = event.target.files || event.dataTransfer.files
            this.imgData.image = files[0]
            console.log(this.imgData.image);
            this.url = URL.createObjectURL(this.imgData.image)
            //ファイrデータを追加
            this.articleForm.image = this.imgData.image;
        },
        deleteImage() {
            this.articleForm.image = null
            this.url = null
        },
        hideModal() {
            this.$refs['my-modal'].hide()
            this.newTitle = '';
            this.newContents = '';
            this.imgData.image = '';
            this.articleForm = '';
        },
        search() {
            // 入力した値を格納する
            this.form = {
                title: this.searchTitle,
                startDate: this.day2,
                endDate: this.day1
                // limit: this.perPage,
                // //現在のページ番号　*　一ページあたりに表示する件数
                // offset: (this.currentPage - 1) * this.perPage
            };
            console.log(this.form);

            // バリデーションチェックのメソッドを呼び出す
            this.formCheck();

            this.isBusy = true; // データ取得中にロード中の状態を設定
            axios.post('http://localhost:8080/bulletin', this.form)
                .then(response => {

                    // 検索結果の件数をtotalCountに格納
                    this.totalCount = response.data.totalCount;

                    // レスポンスデータをitemsに格納
                    this.items = response.data.response.map(item => {
                        return {
                            seq: item.seq,
                            create_date: item.create_date, // レスポンスのdateフィールドに合わせて変更
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
        //現在のページを決める
        pageChanged(offset) {
            this.currentPage = offset;
            this.search(); // ページ変更時に再検索
        },
        submit(event) {
            if (confirm("登録しますか？")) {
                this.articleForm.newTitle = this.newTitle;
                this.articleForm.newContents = this.newContents;
                event.preventDefault();

                const formData = new FormData();
                formData.append('image', this.articleForm.image);
                formData.append('newTitle', this.articleForm.newTitle); // 추가
                formData.append('newContents', this.articleForm.newContents); // 추가

                axios.post('http://localhost:8080/upload', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                })
                    .then((res) => {
                        console.log(res);
                        alert("登録しました")
                        this.$refs['my-modal'].hide();
                        this.newTitle = '';
                        this.newContents = '';
                        this.imgData.image = '';
                        this.articleForm = '';
                        this.search();
                    })
                    .catch((err) => {
                        console.log(err);
                        // エラー処理
                    });
            }
        },
        getArticle(item) {
            axios.get(`http://localhost:8080/detail/${item.seq}`)
                .then((res) => {
                    console.log(res);
                    this.title = res.data.title;
                    this.contents = res.data.contents;
                    this.filePath = res.data.filePath;
                    this.currentItem = item;
                    // 이미지 데이터가 있는 경우 처리
                    if (res.data.image) {
                        this.image = `data:image/jpeg;base64,${res.data.image}`;
                    } else {
                        this.image = ''; // 이미지가 없는 경우 처리
                    }
                })
                .catch((err) => {
                    console.log(err);
                    // エラー処理
                });
        },
        edit(event) {
            if (confirm('修正しますか？')) {
                this.editArticleForm.title = this.title;
                this.editArticleForm.contents = this.contents;
                event.preventDefault();

                const formData = new FormData();
                formData.append('image', this.editArticleForm.image);
                formData.append('title', this.editArticleForm.title);
                formData.append('contents', this.editArticleForm.contents);

                console.log(formData);
                axios.put("http://localhost:8080/edit/" + this.currentItem.seq, formData)
                    .then((res) => {
                        console.log(res);
                        alert('記事を修正しました');
                        this.$refs['edit-modal'].hide();
                        this.search(); // search()をthis.search()に修正
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            }
        },
        hideEditModal() {
            this.imgData.image = null
            this.url = null
            this.form = "";
            this.$refs['edit-modal'].hide()
        }
    }
}
</script>

<style
    src="/Users/arisaohta/Documents/workspace-spring-tool-suite-4-4.15.1.RELEASE/Bulletin/web/src/views/BulletinView.css"></style>