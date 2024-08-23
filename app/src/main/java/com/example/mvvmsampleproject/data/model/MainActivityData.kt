package com.example.mvvmsampleproject.data.model


/**
 * mainBottomPosition = -1 나미지 URL , 0 메뉴 , 1 혜택 , 2 홈 , 3 샵 , 4 챗봇
 *
 * url = mainWebView 현재 url
 *
 * mBottomNavigation = 현재 하단탭 위치 문자값 : MENU_TAB(메뉴) , BENEFIT_TAB(혜택) , MY_TAB(홈) ,SHOP_TAB(샵) , CHATBOT_TAB(챗봇)
 *
 * mainBottomIsClick = true : 하단탭바 클릭으로 url 변경시 , false : 하단탭바 클릭 제외 url 변경시
 * **/
data class MainActivityData(var mainBottomPosition : Int ,var url : String  , var mBottomNavigation : String , var mainBottomIsClick : Boolean){
}