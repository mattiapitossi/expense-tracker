<template>
    <main class="container">
        <div class="d-flex justify-content-between py-5">
            <h1>WALLETS</h1>
            <button class="btn btn-success" @click="animateModal(true)">ADD WALLET</button>
        </div>

        <!-- //MODAL// -->
        <div class="back_overlay" @click="animateModal(false)" v-show="showModal"></div> <!-- //OVERLAY -->

        <!-- //TODO CLOSE BUTTON -->
        <div v-show="showModal" class="pop_up_form">

            <Loading v-if="isLoadingForm" />
            <form v-else @submit.prevent="modify ? modifyWallet() : addWallet()">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control" id="name" name="name" v-model="data.name"
                        placeholder="Name">
                </div>
                <div class="mb-3">
                    <label for="value" class="form-label">Value</label>
                    <input type="number" class="form-control" id="value" name="value" v-model="data.value" placeholder="0.0" step="0.01">
                </div>
                <div class="py-3 d-flex justify-content-center">
                    <button :disabled="data.name == null || data.name.length == 0"
                    type="submit" class="btn btn-primary d-block">Submit</button>
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
                        <th scope="col">Value</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="wallet in wallets" :key="wallet.id">
                        <th scope="row">{{ wallet.id }}</th>
                        <td>{{ wallet.name }}</td>
                        <td>{{ wallet.value }} €</td>
                        <td>
                            <button @click="animateModal(true), fillFormFields(wallet)" class="btn btn-primary mx-2">
                                <i class="fa-solid fa-pen"></i>
                            </button>
                            <button @click="deleteWallet(wallet.id)" class="btn btn-danger mx-2">
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

    name: "WalletsView",
    components: { Loading },

    data() {
        return {
            modify: false,
            isLoading: false,
            isLoadingForm: false,
            showModal: false,
            wallets: null,
            //form field ->
            data: {
                id: null,
                name: null,
                value: 0.0
            },
        }
    },

    methods: {
        getWallets() {
            this.isLoading = true;
            this.axios.get("api/wallets")
                .then(response => {
                    this.wallets = response.data;
                    this.isLoading = false;
                });
        },

        addWallet() {
            this.isLoadingForm = true;

            this.axios.post("api/wallets", this.data)
                .then(response => {
                    this.getWallets()
                    this.isLoadingForm = false
                    this.animateModal(false);
                })
                .catch(error => {
                    console.log(error);
                })
        },

        modifyWallet() {
            this.isLoadingForm = true;

            this.axios.put("api/wallets", this.data)
                .then(response => {
                    this.getWallets()
                    this.isLoadingForm = false
                    this.animateModal(false);
                })
                .catch(error => {
                    console.log(error);
                })
        },

        deleteWallet(walletId) {
            this.axios.delete("api/wallets/" + walletId)
                .then(response => {
                    this.getWallets()
                })
                .catch(error => {
                    console.log(error);
                })
        },

        //UTILS

        fillFormFields(wallet) {
            //modify mode ON
            this.modify = true

            this.data.id = wallet.id;
            this.data.name = wallet.name;
        },

        resetData() {
            //modify mode OFF
            this.modify = false

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
        this.getWallets();
    }
}
</script>
