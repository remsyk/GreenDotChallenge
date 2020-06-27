package com.example.greendotchallenge.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greendotchallenge.R
import com.example.greendotchallenge.ui.fragments.FragmentFib

//this is the main and only activity, it doesn't have much functionality, it just handles the fragments at the moment
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //moves right into the main fragment
        this.supportFragmentManager.beginTransaction().add(R.id.framelayout_main, FragmentFib()).addToBackStack(null).commit()

    }

    //since its transitions right into a fragment, if this wasn't here, on back pressed would eventually lead the user to a blank screen
    override fun onBackPressed() {
        super.onBackPressed()
        if( this.supportFragmentManager.backStackEntryCount==0){
            finish()
        }
    }
}