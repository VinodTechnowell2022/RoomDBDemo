package com.tw.roomdbdemo.ui.screens

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import com.tw.roomdbdemo.R
import com.tw.roomdbdemo.databinding.ActivityMainBinding
import com.tw.roomdbdemo.models.UserTable
import com.tw.roomdbdemo.ui.adapters.UsersAdapter
import com.tw.roomdbdemo.viewmodels.UserDBViewModel

class MainActivity : AppCompatActivity(), UsersAdapter.OnItemClickListener {

    val TAG : String = this.javaClass.simpleName
    private val userDBViewModel: UserDBViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    val optionList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabPlus.setOnClickListener {
            openInputDialog()
        }

        getUsersData()

        textChangeListener()

        binding.ivGroupBy.setOnClickListener {
            optionList.clear()
            optionList.add("8790654321")
            optionList.add("9876543210")
            optionList.add("9988776655")

            val name = "Binny"

            userDBViewModel.searchOnNameAndEmailAndPhoneList( name, optionList.toList() ).observe(this@MainActivity) { it->

                searchUsersData(it)
            }
        }
    }

    private fun textChangeListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                userDBViewModel.searchDatabase(s.toString()).observe(this@MainActivity) { it->

                    searchUsersData(it)
                }
            }
        })
    }

    private fun searchUsersData(list: List<UserTable>) {

        binding.homeItemsGrid.removeAllViews()
        val adapter = UsersAdapter()
        adapter.setAdapterData(list as MutableList<UserTable>, this, this@MainActivity)
        val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false )
        binding.homeItemsGrid.layoutManager = layoutManager
        binding.homeItemsGrid.adapter = adapter
    }

    private fun getUsersData() {
        userDBViewModel.getAllData.observe(this) { it->

            binding.homeItemsGrid.removeAllViews()
            val adapter = UsersAdapter()
            adapter.setAdapterData(it as MutableList<UserTable>, this, this@MainActivity)
            val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false )
            binding.homeItemsGrid.layoutManager = layoutManager
            binding.homeItemsGrid.adapter = adapter
        }
    }

    private fun openInputDialog() {

        val dialog = Dialog( ContextThemeWrapper(this@MainActivity, R.style.AlertDialogCustom ))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.input_dialog_screen)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowlp = dialog.window?.attributes
        windowlp?.gravity = Gravity.BOTTOM
        dialog.window?.attributes = windowlp

        val tvSave: TextView = dialog.findViewById(R.id.tvSave)
        val tvCancel: TextView = dialog.findViewById(R.id.tvCancel)
        val etName: EditText = dialog.findViewById(R.id.etName)
        val etEmail: EditText = dialog.findViewById(R.id.etEmail)
        val etPhone: EditText = dialog.findViewById(R.id.etPhone)

        tvSave.setOnClickListener {

            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()
            if (name.isNullOrEmpty()){
                Toast.makeText(this@MainActivity, "Please enter name", Toast.LENGTH_SHORT).show()
            }else if (email.isNullOrEmpty()){
                Toast.makeText(this@MainActivity, "Please enter email", Toast.LENGTH_SHORT).show()
            }else if (phone.isNullOrEmpty()){
                Toast.makeText(this@MainActivity, "Please enter phone", Toast.LENGTH_SHORT).show()
            }else{
                dialog.dismiss()
                val data = UserTable(0, name, email, phone)
                userDBViewModel.insertData(data)
                refreshScreen()
            }
        }

        tvCancel.setOnClickListener { dialog.dismiss() }

        if (!dialog.isShowing) { dialog.show() }
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


    override fun onDataClick(data: UserTable, pos: Int, type: Int) {
        Log.e(TAG, "onItemClicked: ${data.name}" )
        openUpdteDialog(data)
        getUsersData()
    }


    private fun openUpdteDialog(data: UserTable) {

        val dialog = Dialog( ContextThemeWrapper(this@MainActivity, R.style.AlertDialogCustom ))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.input_dialog_screen)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowlp = dialog.window?.attributes
        windowlp?.gravity = Gravity.BOTTOM
        dialog.window?.attributes = windowlp

        val tvSave: TextView = dialog.findViewById(R.id.tvSave)
        val tvCancel: TextView = dialog.findViewById(R.id.tvCancel)
        val etName: EditText = dialog.findViewById(R.id.etName)
        val etEmail: EditText = dialog.findViewById(R.id.etEmail)
        val etPhone: EditText = dialog.findViewById(R.id.etPhone)

        etName.setText(data.name)
        etEmail.setText(data.email)
        etPhone.setText(data.phone)

        tvSave.setOnClickListener {

            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()
            if (name.isNullOrEmpty()){
                Toast.makeText(this@MainActivity, "Please enter name", Toast.LENGTH_SHORT).show()
            }else if (email.isNullOrEmpty()){
                Toast.makeText(this@MainActivity, "Please enter email", Toast.LENGTH_SHORT).show()
            }else if (phone.isNullOrEmpty()){
                Toast.makeText(this@MainActivity, "Please enter phone", Toast.LENGTH_SHORT).show()
            }else{
                dialog.dismiss()
                val data = UserTable(data.id, name, email, phone)
                userDBViewModel.updateData(data)
                refreshScreen()
            }
        }

        tvCancel.setOnClickListener { dialog.dismiss() }

        if (!dialog.isShowing) { dialog.show() }
    }

}