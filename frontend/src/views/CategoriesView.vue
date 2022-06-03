<template>
    <main class="container">
        <div class="d-flex justify-content-between py-5">
            <h1>CATEGORIES</h1>
            <button class="btn btn-success" @click="animateModal(true)">ADD CATEGORY</button>
        </div>

        <!-- //MODAL// -->
        <div class="back_overlay" @click="animateModal(false)" v-show="showModal"></div> <!-- //OVERLAY -->

        <!-- //TODO CLOSE BUTTON -->
        <div v-show="showModal" class="pop_up_form">

            <Loading v-if="isLoadingForm" />

            <form v-else @submit.prevent="addCategory()">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control" id="name" name="name" v-model="data.name" placeholder="Name">
                </div>
                <div class="py-3 d-flex justify-content-center">
                    <button type="submit" class="btn btn-primary d-block">Submit</button>
                </div>
            </form>
        </div>

        <!-- //TABLE -->
        <div class="d-flex justify-content-center">

            <Loading v-if="isLoading" />

            <table v-else class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="category in categories" :key="category.id">
                        <th scope="row">{{ category.id }}</th>
                        <td>{{ category.name }}</td>
                        <td>
                            <button @click="animateModal(true), fillFormFields(category)" class="btn btn-primary mx-2">
                                <i class="fa-solid fa-pen"></i>
                            </button>
                            <button @click="deleteCategory(category.id)" class="btn btn-danger mx-2">
                                <i class="fa-solid fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>

        </div>
    </main>
</template>

<script>
import Loading from "@/components/partials/Loading.vue";
import gsap from "gsap";

export default {

    name: "CategoriesView",
    components: { Loading },

    data() {
        return {
            isLoading: false,
            isLoadingForm: false,
            showModal: false,
            categories: null,
            //form field ->
            data: {
                id: null,
                name: null
            },
        }
    },

    methods: {
        getCategories() {
            this.isLoading = true;
            this.axios.get("api/category")
                .then(response => {
                    this.categories = response.data;
                    this.isLoading = false;
                });
        },

        addCategory() {
            this.isLoadingForm = true;

            this.axios.post("api/category", this.data)
                .then(response => {
                    this.getCategories()
                    this.isLoadingForm = false
                    this.animateModal(false);
                })
                .catch(error => {
                    console.log(error);
                })
        },

        deleteCategory(categoryId) {
            this.axios.delete("api/category/" + categoryId)
                .then(response => {
                    this.getCategories()
                })
                .catch(error => {
                    console.log(error);
                })
        },

        //UTILS

        fillFormFields(category) {
            this.data.id = category.id;
            this.data.name = category.name;
        },

        resetData() {
            this.data.id = null;
            this.data.name = null
        },

        animateModal(action) {

            ///check if form is sending new expense
            if (!this.isLoadingForm) {

                /////////// true -> open modal
                /////////// false -> close modal
                if (action) {
                    this.resetData();
                    this.showModal = action;
                    gsap.fromTo(".pop_up_form", {
                        opacity: 0,
                        top: "-100%",
                    },
                        {
                            opacity: 1,
                            top: "50%",
                            duration: 0.4
                        }
                    );
                    gsap.fromTo(".back_overlay", {
                        filter: "blur(0px)",
                        backgroundColor: "rgba(0,0,0,0.0)",
                    },
                        {
                            filter: "blur(1px)",
                            backgroundColor: "rgba(0,0,0,0.4)",
                            duration: 0.4
                        }
                    );
                } else {
                    this.resetData();
                    gsap.fromTo(".pop_up_form", {
                        opacity: 1,
                        top: "50%",
                    },
                        {
                            opacity: 0,
                            top: "-100%",
                            duration: 0.4
                        }
                    );
                    gsap.fromTo(".back_overlay", {
                        filter: "blur(1px)",
                        backgroundColor: "rgba(0,0,0,0.4)",
                    },
                        {
                            filter: "blur(0px)",
                            backgroundColor: "rgba(0,0,0,0.0)",
                            delay: 0.2,
                            duration: 0.2
                        }
                    );
                    setTimeout(() => this.showModal = action, 400)
                }
            }
        }
    },

    mounted() {
        this.getCategories();
    }
}
</script>
