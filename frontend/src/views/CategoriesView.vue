<template>
    <main class="container">

        <!-- //MODAL// -->
        <div class="back_overlay" @click="animateModal(false)" v-show="showModal"></div> <!-- //OVERLAY -->

        <!-- //TODO CLOSE BUTTON -->
        <div v-show="showModal" class="pop_up_form">

            <Loading v-if="isLoadingForm" />

            <form v-else @submit.prevent="modify ? modifyCategory() : addCategory()">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control" id="name" name="name" v-model="dataCategory.name" placeholder="Name">
                </div>
                <div class="py-3 d-flex justify-content-center">
                    <button type="submit" class="btn btn-primary d-block">Submit</button>
                </div>
            </form>
        </div>

        <!-- //TABLE -->
        <div class="d-flex">

            <div class="categories">
                <div class="d-flex justify-content-between py-5">
                    <h1>CATEGORIES</h1>
                    <button class="btn btn-success" @click="animateModal(true)">ADD CATEGORY</button>
                </div>

                <div class="d-flex justify-content-center">
                    <Loading v-if="isLoadingCategory" />
        
                    <Table v-else
                        :categories="categories"
                        @edit="animateModal"
                        @fill="fillFormFields"
                        @delete="deleteCategory"
                    />
                </div>
            </div>

            <div class="categories">
                <div class="d-flex justify-content-between py-5">
                    <h1>SUBCATEGORIES</h1>
                    <button class="btn btn-success" @click="animateModal(true)">ADD SUBCATEGORY</button>
                </div>

                <div class="d-flex justify-content-center">
                    <Loading v-if="isLoadingSubcategory" />
        
                    <Table v-else
                        :categories="subcategories"
                        @edit="animateModal"
                        @fill="fillFormFields"
                        @delete="deleteCategory"
                    />
                </div>
            </div>

        </div>
    </main>
</template>

<script>
import Loading from "@/components/partials/Loading.vue";
import Table from "@/components/partials/Table.vue"
import gsap from "gsap";

export default {

    name: "CategoriesView",
    components: { Loading, Table },

    data() {
        return {
            modify: false,
            isLoadingCategory: false,
            isLoadingSubcategory: false,
            isLoadingForm: false,
            showModal: false,
            categories: null,
            subcategories: null,
            //form field ->
            dataCategory: {
                id: null,
                name: null
            },
            dataSubcategory: {
                id: null,
                name: null
            },
        }
    },

    methods: {
        getCategories() {
            this.isLoadingCategory = true;
            this.axios.get("api/category")
                .then(response => {
                    this.categories = response.data;
                    this.isLoadingCategory = false;
                });
        },

        getSubcategies() {
            this.isLoadingSubcategory = true;
            this.axios.get("api/subcategory")
                .then(response => {
                    this.subcategories = response.data;
                    this.isLoadingSubcategory = false;
                });
        },

        addCategory() {
            this.isLoadingForm = true;

            this.axios.post("api/category", this.dataCategory)
                .then(response => {
                    this.getCategories()
                    this.isLoadingForm = false
                    this.animateModal(false);
                })
                .catch(error => {
                    console.log(error);
                })
        },

        modifyCategory() {
            this.isLoadingForm = true;

            this.axios.put("api/category", this.dataCategory)
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
            //modify mode ON
            this.modify = true

            this.dataCategory.id = category.id;
            this.dataCategory.name = category.name;
        },

        resetData() {
            //modify mode OFF
            this.modify = false

            this.dataCategory.id = null;
            this.dataCategory.name = null
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
        this.getSubcategies();
    }
}
</script>
