package com.tw.roomdbdemo.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tw.roomdbdemo.databinding.ActivityMainBinding
import com.tw.roomdbdemo.viewmodels.UserDBViewModel
import com.tw.roomdbdemo.models.UserTable

class MainActivity : AppCompatActivity() {

    val TAG : String = this.javaClass.simpleName
    private val userDBViewModel: UserDBViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()
            if (name.isNullOrEmpty()){
                Toast.makeText(this@MainActivity, "Please enter name", Toast.LENGTH_SHORT).show()
            }else if (email.isNullOrEmpty()){
                Toast.makeText(this@MainActivity, "Please enter email", Toast.LENGTH_SHORT).show()
            }else if (phone.isNullOrEmpty()){
                Toast.makeText(this@MainActivity, "Please enter phone", Toast.LENGTH_SHORT).show()
            }else{
                val data = UserTable(0, name, email, phone)
                userDBViewModel.insertData(data)
                refreshScreen()
            }
        }

    }

    private fun refreshScreen() {
        val i = Intent(this@MainActivity, MainActivity::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN,0, 0)
            startActivity(i)
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN,0, 0)
        }else{
            overridePendingTransition(0, 0)
            startActivity(i)
            overridePendingTransition(0, 0)
        }
    }


}