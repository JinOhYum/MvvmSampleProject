package com.example.mvvmsampleproject.data.model


/**
 * mainBottomPosition = -1 나미지 URL , 0 메뉴 , 1 혜택 , 2 홈 , 3 샵 , 4 챗봇
 * **/
data class MainActivityData(var mainBottomPosition : Int ,var url : String  , var mBottomNavigation : String , ){
}