package com.dev.holker.wholesale.presenters.interfaces

interface ILoginPresenter {
    fun logIn(username: String, password: String)
    fun goToMain()
    fun toast(string: String)
    fun goToSignUp()
}