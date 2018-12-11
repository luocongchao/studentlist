<template>
    <div class="login-container">
        <el-form :model="loginForm" :rules="loginRules" auto-complete="on" class="login-form" label-position="left" ref="loginForm">
            <h3 class="title">Student Info Manage</h3>
            <el-form-item prop="userName">
                <span class="svg-container">
                    <svg-icon icon-class="user"/>
                </span>
                <el-input auto-complete="on" name="userName" placeholder="username" type="text" v-model="loginForm.userName"/>
            </el-form-item>
            <el-form-item prop="userPwd">
                <span class="svg-container">
                    <svg-icon icon-class="password"/>
                </span>
                <el-input :type="pwdType" @keyup.enter.native="handleLogin" auto-complete="on" name="userPwd" placeholder="password" v-model="loginForm.userPwd"/>
                <span @click="showPwd" class="show-pwd">
                    <svg-icon icon-class="eye"/>
                </span>
            </el-form-item>
            <el-form-item>
                <el-button :loading="loading" @click.native.prevent="handleLogin" style="width:100%;" type="primary">Sign in</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
//import { validateUpperCase } from "@/utils/validate";
//import axios from "axios";

export default {
    name: "Login",
    data() {
        // const validateUsername = (rule, value, callback) => {
        //     if (validateUpperCase(value)) {
        //         callback(new Error("请输入正确的用户名"));
        //     } else {
        //         callback();
        //     }
        // };
        const validatePass = (rule, value, callback) => {
            if (value.length < 5) {
                callback(new Error("密码不能小于5位"));
            } else {
                callback();
            }
        };
        return {
            loginForm: {
                userName: "Admin",
                userPwd: "123456"
            },
            loginRules: {
                userName: [
                    {
                        required: true,
                        trigger: "blur"
                        // validator: validateUsername
                    }
                ],
                userPwd: [
                    { required: true, trigger: "blur", validator: validatePass }
                ]
            },
            loading: false,
            pwdType: "password",
            redirect: undefined
        };
    },
    watch: {
        $route: {
            handler: function(route) {
                this.redirect = route.query && route.query.redirect;
            },
            immediate: true
        }
    },
    methods: {
        showPwd() {
            if (this.pwdType === "password") {
                this.pwdType = "";
            } else {
                this.pwdType = "password";
            }
        },
        handleLogin() {
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.loading = true;
                    this.$store
                        .dispatch("Login", this.loginForm)
                        .then(() => {
                            this.loading = false;
                            this.$router.push({ path: this.redirect || "/" });
                        })
                        .catch(() => {
                            this.loading = false;
                        });

                    // var _this = this;
                    // _this.axios
                    //     .postEx("/login", this.loginForm)
                    //     .then(res => {
                    //         if (res.code && res.code == 20000 && res.data) {
                    //             this.loading = false;
                    //             this.$router.push({
                    //                 path: this.redirect || "/"
                    //             });
                    //             this.$store.dispatch("Login", res.sessiontoken);

                    //         } else {
                    //             _this.loading = false;
                    //             this.$message.error("用户名或者密码错误");
                    //         }
                    //     })
                    //     .catch(function(err) {
                    //         console.log(err);
                    //     });
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        }
    }
};
</script>

<style rel="stylesheet/scss" lang="scss">
$bg: #2d3a4b;
$light_gray: #eee;

/* reset element-ui css */
.login-container {
    .el-input {
        display: inline-block;
        height: 47px;
        width: 85%;
        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 15px;
            color: $light_gray;
            height: 47px;
            &:-webkit-autofill {
                -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
                -webkit-text-fill-color: #fff !important;
            }
        }
    }
    .el-form-item {
        border: 1px solid rgba(255, 255, 255, 0.1);
        background: rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        color: #454545;
    }
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
.login-container {
    position: fixed;
    height: 100%;
    width: 100%;
    background-color: $bg;
    .login-form {
        position: absolute;
        left: 0;
        right: 0;
        width: 520px;
        max-width: 100%;
        padding: 35px 35px 15px 35px;
        margin: 120px auto;
    }
    .tips {
        font-size: 14px;
        color: #fff;
        margin-bottom: 10px;
        span {
            &:first-of-type {
                margin-right: 16px;
            }
        }
    }
    .svg-container {
        padding: 6px 5px 6px 15px;
        color: $dark_gray;
        vertical-align: middle;
        width: 30px;
        display: inline-block;
    }
    .title {
        font-size: 26px;
        font-weight: 400;
        color: $light_gray;
        margin: 0px auto 40px auto;
        text-align: center;
        font-weight: bold;
    }
    .show-pwd {
        position: absolute;
        right: 10px;
        top: 7px;
        font-size: 16px;
        color: $dark_gray;
        cursor: pointer;
        user-select: none;
    }
}
</style>
