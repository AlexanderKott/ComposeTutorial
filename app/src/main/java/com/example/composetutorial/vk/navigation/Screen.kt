package com.example.composetutorial.vk.navigation

sealed class Screen(val route: String) {

    object HomeScreen : Screen(HOME_SCREEN_ROUTE)
    object FeedScreen : Screen(FEED_SCREEN_ROUTE)
    object CommentsScreen : Screen(COMMENTS_SCREEN_ROUTE) {
         const val KEY_COMMENTS_POST_ID = "post_id"
         const val COMMENTS_SCREEN = "comments"

        fun routeWithParam(param: String) = "$COMMENTS_SCREEN/$param"
    }
    object FavoriteScreen : Screen(FAVORITE_SCREEN_ROUTE)
    object ProfileScreen : Screen(PROFILE_SCREEN_ROUTE)

    companion object {

        private const val HOME_SCREEN_ROUTE = "home"
        private const val FEED_SCREEN_ROUTE = "feed"
        private const val COMMENTS_SCREEN_ROUTE = "${CommentsScreen.COMMENTS_SCREEN}/{${CommentsScreen.KEY_COMMENTS_POST_ID}}"
        private const val FAVORITE_SCREEN_ROUTE = "favorite"
        private const val PROFILE_SCREEN_ROUTE = "profile"


    }
}