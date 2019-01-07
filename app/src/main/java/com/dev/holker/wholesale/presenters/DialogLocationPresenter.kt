package com.dev.holker.wholesale.presenters

import android.R
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter

class DialogLocationPresenter(val view: View) {
    fun getCountryAdapter(): SpinnerAdapter {
        val listOfCountries = listOf("Poland", "UK", "Ukraine", "Russia", "USA")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(view.context, R.layout.simple_spinner_item, listOfCountries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    fun getCityAdapter(country: Int): SpinnerAdapter {
        when (country) {
            0 -> {
                val listOfCities = listOf("Rzeszow", "Warsaw", "Cracow", "Poznan", "Wroclaw")
                val adapter: ArrayAdapter<String> =
                    ArrayAdapter(view.context, R.layout.simple_spinner_item, listOfCities)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                return adapter
            }
            1 -> {
                val listOfCities = listOf("London", "Leeds", "Liverpool", "Edinburh", "Belfast")
                val adapter: ArrayAdapter<String> =
                    ArrayAdapter(view.context, R.layout.simple_spinner_item, listOfCities)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                return adapter
            }
            2 -> {

                val listOfCities = listOf("Dnipro", "Kiev", "Ternopil", "Lviv", "Odessa")
                val adapter: ArrayAdapter<String> =
                    ArrayAdapter(view.context, R.layout.simple_spinner_item, listOfCities)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                return adapter
            }
            3 -> {
                val listOfCities = listOf("Saint Petersburg", "Moscow", "Novosibirsk", "Yekaterinburg", "Rostov-on-Don")
                val adapter: ArrayAdapter<String> =
                    ArrayAdapter(view.context, R.layout.simple_spinner_item, listOfCities)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                return adapter
            }
            4 -> {
                val listOfCities = listOf("New York", "San Francisco", "Washington, D.C.", "Boston", "Chicago")
                val adapter: ArrayAdapter<String> =
                    ArrayAdapter(view.context, R.layout.simple_spinner_item, listOfCities)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                return adapter
            }
            else -> {
                val listOfCities = listOf("Rzeszow", "Warsaw", "Cracow", "Poznan", "Wroclaw")
                val adapter: ArrayAdapter<String> =
                    ArrayAdapter(view.context, R.layout.simple_spinner_item, listOfCities)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                return adapter
            }
        }
    }
}