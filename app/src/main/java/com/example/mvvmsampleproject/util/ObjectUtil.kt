package com.example.mvvmsampleproject.util

object ObjectUtil {
    /**
     * Null 체크
     * @param obj    해당 Object
     * @return
     */
    fun isEmpty(obj: Any?): Boolean {
        return if (obj is String) {
            obj == null || "" == obj.toString().trim { it <= ' ' }
        } else if (obj is List<*>) {
            if (obj == null || (obj as List<*>).isEmpty()) {
                true
            } else {
                (obj as List<*>).size == 0
            }
        } else if (obj is Map<*, *>) {
            obj == null || (obj as Map<*, *>).isEmpty()
        } else if (obj is Array<*> && obj.isArrayOf<Any>()) {
            obj == null || java.lang.reflect.Array.getLength(obj) == 0
        } else {
            obj == null
        }
    }

    /**
     * Null 값이면 공백으로 변경
     * @return
     */
    fun nvl(obj: String?): String? {
        return if (!isEmpty(obj)) obj else ""
    }

    /**
     * Null 값이면 치환 문자열로 변경
     * @param obj    체크 Object
     * @param def    치환 Object
     * @return
     */
    fun nvl(obj: Any?, def: Any?): Any? {
        return if (!isEmpty(obj)) obj else def
    }
}