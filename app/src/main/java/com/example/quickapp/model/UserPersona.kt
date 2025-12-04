package com.example.quickapp.model

import androidx.annotation.DrawableRes
import com.example.quickapp.R

enum class UserPersona(val id: Int, val displayName: String, val handle: String, @DrawableRes val avatarResource: Int) {
    USER_7(7,"Sarah Steady", "@rock_steady_2003", R.drawable.avatar2),
    USER_5(5, "Perrick Dortis", "@PerrickD", R.drawable.avatar3),
    USER_3(3, "Devonte Fox", "@DFox_Zone6", R.drawable.avatar4),
    USER_8(8,"Shane White", "@ShaShaWhite", R.drawable.avatar5),
    USER_2(2, "Diana Watkins", "@ThePrincess1738", R.drawable.avatar6),
    USER_6(6,"Janet Planet", "@JaneyPlaney", R.drawable.avatar7),
    USER_1(1, "Chance Franks", "@FrankyC2Times", R.drawable.avatar8),
    USER_4(4, "Cindy Blache", "@CindyBlache2", R.drawable.avatar9),
    USER_9(9,"Grover Childress", "@GroveStreet", R.drawable.avatar10),
    USER_10(10, "Amy Poof", "@poof_be_gone", R.drawable.avi),
    UNKNOWN(0, "DISPLAY USER", "@User", R.drawable.avi);

    companion object {
        fun fromId(id: Int): UserPersona{
            return entries.find { it.id == id } ?: UNKNOWN
        }
    }
}