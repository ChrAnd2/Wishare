package de.chrisander.wishare.data.datasource

import android.content.Intent
import android.content.IntentSender
import de.chrisander.wishare.presentation.sign_in.SignInResult
import de.chrisander.wishare.presentation.sign_in.UserData

interface IAuthenticationDataSource {
    suspend fun signIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent): SignInResult
    suspend fun signOut()
    fun getSignedInUser(): UserData?
}