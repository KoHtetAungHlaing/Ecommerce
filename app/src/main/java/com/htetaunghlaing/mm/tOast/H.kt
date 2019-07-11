package com.htetaunghlaing.mm.tOast

import android.util.Log

class H {
    companion object {

        var cartMap: HashMap<Int, Int> = hashMapOf()
        var USER_TOKEN = ""

        fun l(msg: String) {
            Log.d("message", msg)
        }

        fun CheckUserAuth(): Boolean {
            return USER_TOKEN == ""
        }

        fun addToCart(key: Int) {
            if (cartMap.containsKey(key)) {
                addCountCart(key)
            } else {
                cartMap[key] = 1
            }
        }

        private fun addCountCart(key: Int) {
            val oldcount = cartMap[key]
            val newcount = oldcount!! + 1
            cartMap[key] = newcount
        }

        fun removeFromCart(key: Int) {
            if (cartMap.containsKey(key)) {
                cartMap.remove(key)
            }
        }

        fun clearCart() {
            cartMap.clear()
        }

        fun getCartCount(): Int {
            return cartMap.size
        }

        fun getSingleItemCount(key: Int): Int {
            if (cartMap.containsKey(key)) {
                return cartMap[key]!!
            }
            return 0
        }

        fun getAllKey(): String {
            var keys = ""
            for ((k, v) in cartMap) {
                keys += "$k#$v,"
            }
            return keys
        }
    }
}