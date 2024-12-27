package com.edison.librolink

import android.app.Application
import com.edison.librolink.data.AppContainer
import com.edison.librolink.data.AppDataContainer

class LibroLinkApplication: Application() {

    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = AppDataContainer(this)
    }
}