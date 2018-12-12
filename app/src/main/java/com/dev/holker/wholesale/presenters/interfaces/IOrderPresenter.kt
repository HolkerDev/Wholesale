package com.dev.holker.wholesale.presenters.interfaces

interface IOrderPresenter {
    fun submit(name: String, amount: Int, descr: String)
    fun toast(string: String)
}