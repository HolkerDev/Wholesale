package com.dev.holker.wholesale

interface ILoginView {
    fun logIn(username: String, password: String)
    fun goToMain()
    fun toast()
    fun goToSignUp()
}