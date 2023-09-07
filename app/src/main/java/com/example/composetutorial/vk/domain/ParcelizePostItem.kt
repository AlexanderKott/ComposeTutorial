package com.example.composetutorial.vk.domain

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson


class AssetParamType : NavType<PostItem>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PostItem? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): PostItem {
        return Gson().fromJson(value, PostItem::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: PostItem) {
        bundle.putParcelable(key, value)
    }
}
