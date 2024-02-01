package com.eunji.lookatthis.data.datasource.local

import javax.inject.Inject

class UserDataSourceLocal @Inject constructor() {

    fun saveBase64UserAccount(userName: String, password: String){}
    fun getBase64UserAccount(): String{
        return "sdf"
    }

}