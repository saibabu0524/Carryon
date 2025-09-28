package com.saibabui.auth.google

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleAuthHelper(private val context: Context) {
    
    private lateinit var googleSignInClient: GoogleSignInClient

    init {
        initializeGoogleSignIn()
    }

    private fun initializeGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    fun signOut(): Task<Void> {
        return googleSignInClient.signOut()
    }

    fun revokeAccess(): Task<Void> {
        return googleSignInClient.revokeAccess()
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>): GoogleSignInResult {
        return try {
            val account = completedTask.getResult(ApiException::class.java)
            GoogleSignInResult.Success(account)
        } catch (e: ApiException) {
            GoogleSignInResult.Error(e.statusCode, e.message ?: "Sign in failed")
        }
    }
}

sealed class GoogleSignInResult {
    data class Success(val account: GoogleSignInAccount) : GoogleSignInResult()
    data class Error(val code: Int, val message: String) : GoogleSignInResult()
}