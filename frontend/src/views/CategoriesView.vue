<template>
    <main class="container">

        <!-- //MODAL// -->
        <div class="back_overlay" style="z-index: 2;" @click="animateModal(false)" v-show="showModal"></div> <!-- //OVERLAY -->

        <div v-show="showModal" class="pop_up_form" style="z-index: 3;">
            <!-- //TODO CLOSE BUTTON -->

            <Loading v-if="isLoadingForm" />

            <form v-else @submit.prevent="
                subcategoryOperation ? (modify ? modifySubcategory() : addSubcategory()) : (modify ? modifyCategory() : addCategory())
            ">
                <div v-if="subcategoryOperation" class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input required type="text" class="form-control" id="name" name="name" v-model="dataSubcategory.name" placeholder="Name">
                </div>
                <div v-else class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input required type="text" class="form-control" id="name" name="name" v-model="dataCategory.name" placeholder="Name">
                </div>
                <div v-if="subcategoryOperation" class="mb-3 me-3 d-inline-block">
                    <label for="category" class="form-label d-block">Category</label>
                    <select required name="category" id="category" v-model="dataSubcategory.category" class="fullSizeSelectOption">
                    <option v-for="item in categories" :key="item.id" :value="item.name">{{ item.name }}</option>
                    </select>
                </div>
                <div class="py-3 d-flex justify-content-center">
                    <button type="submit" class="btn btn-primary d-block">Submit</button>
                </div>
            </form>
            
            <!-- <div v-else class="form_category">
                <FormCategory v-if="subcategoryOperation"
                    :category="dataSubcategory"
                    :categories="categories"
                    :modify="modify"
                    :subcategoryOperation="subcategoryOperation"
                    @sendCategory="addSubcategory"
                    @modifyCategory="modifySubcategory"
                />
                <FormCategory v-else
                    :category="dataCategory"
                    :modify="modify"
                    :subcategoryOperation="subcategoryOperation"
                    @sendCategory="addCategory"
                    @modifyCategory="modifyCategory"
                />
            </div> -->
            
        </div>

        <!-- //TABLE -->
        <div class="d-flex">
            <!-- // CATEGORIES -->
            <div class="categories tableContainer tableShadow">
                <div class="d-flex justify-content-between py-3">
                    <h1>CATEGORIES</h1>
                    <button class="btn btn-success" 
                        @click="animateModal(true), subcategoryOperation = false"
                    >ADD CATEGORY</button>
                </div>

                <div class="d-flex justify-content-center">
                    <Loading v-if="isLoadingCategory" />

                    <h2 v-else-if="categories == null || categories.length == 0" class="text-center">No categories present. Add one!</h2>
        
                    <Table v-else
                        :categories="categories"
                        @setCategoryType="setCategoryType"
                        @edit="animateModal"
                        @fill="fillFormFields"
                        @delete="deleteCategory"
                    />
                </div>
            </div>

            <!-- // SUBCATEGORIES -->
            <div class="categories tableContainer tableShadow">
                <div class="d-flex justify-content-between py-3">
                    <h1>SUBCATEGORIES</h1>
                    <button class="btn btn-success" @click="animateModal(true), subcategoryOperation = true">ADD SUBCATEGORY</button>
                </div>

                <div class="d-flex justify-content-center">
                    <Loading v-if="isLoadingSubcategory" />

                    <h2 v-else-if="subcategories == null || subcategories.length == 0" class="text-center">No subcategories present. Add one!</h2>

                    <Table v-else
                        :categories="subcategories"
                        @setCategoryType="setCategoryType"
                        @edit="animateModal"
                        @fill="fillFormFields"
                        @delete="deleteSubcategory"
                    />
                </div>
            </div>
        </div>
    </main>
</template>

<script>
import Loading from "../components/partials/Loading.vue";
import Table from "../components/partials/Table.vue"
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
            subcategoryOperation: false,
            categories: null,
            subcategories: null,

            //form field ->
            dataCategory: {
                id: null,
                name: null
            },
            dataSubcategory: {
                id: null,
                name: null,
                category: null,
            },
        }
    },

    methods: {
        getCategories() {
            this.isLoadingCategory = true;
            this.axios.get("api/category")
                .then(response => {
                    this.categories = response.data;
                    if (this.categories.length > 0) {
                        this.dataSubcategory.category = this.categories[0].name;
                    }
                    this.isLoadingCategory = false;
                })
                .catch(error => {
                    console.log(error);
                })
        },

        getSubcategories() {
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

        addSubcategory() {
            this.isLoadingForm = true;

            this.axios.post("api/subcategory", this.dataSubcategory)
                .then(response => {
                    this.getSubcategories()
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
                    this.getSubcategories()
                    this.isLoadingForm = false
                    this.animateModal(false);
                })
                .catch(error => {
                    console.log(error);
                })
        },

        modifySubcategory() {
            this.isLoadingForm = true;

            this.axios.put("api/subcategory", this.dataSubcategory)
                .then(response => {
                    this.getCategories()
                    this.getSubcategories()
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
                    this.getSubcategories()
                })
                .catch(error => {
                    console.log(error);
                })
        },

        deleteSubcategory(subcategoryId) {
            this.axios.delete("api/subcategory/" + subcategoryId)
                .then(response => {
                    this.getSubcategories()
                })
                .catch(error => {
                    console.log(error);
                })
        },

        //UTILS

        setCategory(categoryId) {
            this.categories.forEach(category => {
                if (category.id === categoryId) {
                this.dataSubcategory = category.name;
                }
            });
        },

        setCategoryType(bool) {
            this.subcategoryOperation = bool;
        },

        fillFormFields(category) {
            //modify mode ON
            this.modify = true

            if (this.subcategoryOperation) {
                this.dataSubcategory.id = category.id;
                this.dataSubcategory.name = category.name;
                this.dataSubcategory.category = category.category.name;
            } else {
                this.dataCategory.id = category.id;
                this.dataCategory.name = category.name;
            }
        },

        resetData() {
            //modify mode OFF
            this.modify = false;

            this.dataCategory.id = null;
            this.dataCategory.name = null;

            this.dataSubcategory.id = null;
            this.dataSubcategory.name = null;
            if (this.categories != null && this.categories.length > 0) {
                this.dataSubcategory.category = this.categories[0].name;
            }
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
        this.getSubcategories();
    }
}
</script>
