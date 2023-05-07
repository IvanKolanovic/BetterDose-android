package com.ik3130.betterdose.ui.components.onBoarding

import com.ik3130.betterdose.R

class OnBoardingItems(
    val image: Int,
    val title: String,
    val desc: String
) {
    companion object {
        fun getData(): List<OnBoardingItems> {
            return listOf(
                OnBoardingItems(
                    R.drawable.intro1,
                    "Know your medicine",
                    "Search through our database of medicine and see what your medication is made out of"
                ),
                OnBoardingItems(
                    R.drawable.intro2,
                    "Never miss a dose",
                    "Keep a diary of all of your medication that you need to take and BetterDose will handle the rest"
                )
            )
        }
    }
}