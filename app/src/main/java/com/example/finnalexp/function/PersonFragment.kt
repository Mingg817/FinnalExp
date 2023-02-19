package com.example.finnalexp.function

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.finnalexp.databinding.FragmentHomeBinding
import com.example.finnalexp.databinding.FragmentPersonBinding
import com.example.finnalexp.loginDatabase.User
import com.example.finnalexp.loginDatabase.UserDao
import com.example.finnalexp.loginDatabase.UserDataBaseAdapter

class PersonFragment : Fragment() {
    lateinit var binding: FragmentPersonBinding
    lateinit var dao: UserDao
    lateinit var id:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Ming", "PersonFragment")
        binding = FragmentPersonBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(
            //注意这里不是this！！！
            owner = activity as FunctionActivity,
            factory = ViewModelProvider.AndroidViewModelFactory(Application())
        ).get(FunctionViewModel::class.java)

        id = viewModel.userId
        Log.d("ming","GetID:$id")

        dao = UserDataBaseAdapter.getInstance(requireContext().applicationContext).userDao()
        getAndSetUser(viewModel)

        binding.updateButton.setOnClickListener {
            val profile = binding.profileTextEdit.text.toString()
            val size = binding.textSizeTextEdit.text.toString()
            val pic = binding.picTextEdit.text.toString()
            dao.updateUser(User(id, dao.getUserPassword(id)!!, profile, size, pic))
            getAndSetUser(viewModel)
        }

        return binding.root
    }

    private fun getAndSetUser(viewModel: FunctionViewModel) {
        val user = dao.getUser(viewModel.userId)
        Log.d("ming", "GetUser:$user")
        binding.user = user
        Glide.with(this).load(user.img).into(binding.personImageView);
    }

}