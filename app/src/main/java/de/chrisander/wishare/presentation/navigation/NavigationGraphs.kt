package de.chrisander.wishare.presentation.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@NavGraph
annotation class HomeNavGraph(
    val start: Boolean = false
)

@RootNavGraph(start = true)
@NavGraph
annotation class SignInNavGraph(
    val start: Boolean = false
)