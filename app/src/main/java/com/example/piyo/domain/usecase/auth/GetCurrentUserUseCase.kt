package com.example.piyo.domain.usecase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class GetCurrentUserUseCase(
    private val auth: FirebaseAuth
) {
    operator fun invoke(): FirebaseUser? {
        return auth.currentUser
    }
}

