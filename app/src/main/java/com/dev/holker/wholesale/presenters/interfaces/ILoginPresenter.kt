package com.dev.holker.wholesale.presenters

interface ILoginPresenter {
    fun logIn(username: String, password: String)
    fun goToMain()
    fun toast(string: String)
    fun goToSignUp()
}