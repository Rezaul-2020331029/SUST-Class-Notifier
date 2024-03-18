package com.example.sustclassnotifier.navigation

import com.example.sustclassnotifier.R
import com.example.sustclassnotifier.strings.PROFILE_BUTTON
import com.example.sustclassnotifier.strings.ROUTINE_BUTTON
import com.example.sustclassnotifier.strings.SIGN_OUT_BUTTON

enum class MenuItem(val iconId: Int, val title: String) {

    Profile(R.drawable.profile, PROFILE_BUTTON),
    Routine(R.drawable.routine, ROUTINE_BUTTON),
    SignOut(R.drawable.log_out, SIGN_OUT_BUTTON),
}