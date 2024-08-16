package com.example.mvvmsampleproject.util

import javax.inject.Singleton

class DefineConfig {

    companion object{
        const val DOMAIN = "https://m.my.kt.com"
        const val CHATBOT_HOST = "https://ibot.kt.com"
        const val URL_MENU = "$DOMAIN/menu/v5/a_AllMenu.do"
        const val URL_BENEFIT = "$DOMAIN/cardmain/v5/a_BenefitCardMain.do"
        const val URL_MAIN = "$DOMAIN/cardmain/v5/a_CardMain.do"
        const val URL_SHOP = "https://m.shop.kt.com:444/m/main.do"
        const val URL_CHATBOT = "$CHATBOT_HOST/client/mobile-app/chat.html"
    }
}