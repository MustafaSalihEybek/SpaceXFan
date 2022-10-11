package com.nexis.spacexfan.view

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.nexis.spacexfan.R
import com.nexis.spacexfan.databinding.FragmentSignInAndSignUpBinding
import com.nexis.spacexfan.util.show
import com.nexis.spacexfan.viewmodel.SignViewModel

class SignInAndSignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var v: View
    private lateinit var signBinding: FragmentSignInAndSignUpBinding
    private lateinit var signViewModel: SignViewModel
    private lateinit var navDirections: NavDirections

    private lateinit var txtEmail: String
    private lateinit var txtPassword: String

    private var emailIsFilled: Boolean = false
    private var passwordIsFilled: Boolean = false

    private fun init(){
        signViewModel = ViewModelProvider(this).get(SignViewModel::class.java)
        observeLiveData()

        signBinding.signFragmentTxtSignUp.setOnClickListener(this)
        signBinding.signFragmentBtnSignIn.setOnClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        signBinding = FragmentSignInAndSignUpBinding.inflate(inflater, container, false)
        return signBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()

        signBinding.signFragmentEditEmail.addTextChangedListener {
            emailIsFilled = !it.toString().trim().isEmpty()
            checkFills()
        }

        signBinding.signFragmentEditPassword.addTextChangedListener {
            passwordIsFilled = !it.toString().trim().isEmpty()
            checkFills()
        }
    }

    private fun observeLiveData(){
        signViewModel.successMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.show(v, it)
            }
        })

        signViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.show(v, it)
            }
        })

        signViewModel.loginedUserId.observe(viewLifecycleOwner, Observer {
            it?.let {
                goToMainPage(it)
            }
        })
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when (it.id){
                R.id.sign_fragment_txtSignUp -> checkInputsAndSign(false)
                R.id.sign_fragment_btnSignIn -> checkInputsAndSign(true)
            }
        }
    }

    private fun checkInputsAndSign(isSign: Boolean){
        txtEmail = signBinding.signFragmentEditEmail.text.toString().trim()
        txtPassword = signBinding.signFragmentEditPassword.text.toString().trim()

        if (!txtEmail.isEmpty()){
            if (!txtPassword.isEmpty()){
                if (isSign)
                    signViewModel.signInUser(txtEmail, txtPassword)
                else
                    signViewModel.signUpUser(txtEmail, txtPassword)
            } else
                "message".show(v, "Please set valid password")
        } else
            "message".show(v, "Please enter a valid email address")
    }

    private fun checkFills(){
        if (emailIsFilled && passwordIsFilled){
            signBinding.signFragmentImgEmail.setColorFilter(ContextCompat.getColor(v.context, R.color.filledColor), android.graphics.PorterDuff.Mode.MULTIPLY)
            signBinding.signFragmentImgPassword.setColorFilter(ContextCompat.getColor(v.context, R.color.filledColor), android.graphics.PorterDuff.Mode.MULTIPLY)
            signBinding.signFragmentBtnSignIn.setBackgroundResource(R.drawable.btn_light_bg)
            signBinding.signFragmentBtnSignIn.setTextColor(ContextCompat.getColor(v.context, R.color.btnLightTextColor))
        } else {
            signBinding.signFragmentImgEmail.setColorFilter(ContextCompat.getColor(v.context, R.color.emptyColor), android.graphics.PorterDuff.Mode.MULTIPLY)
            signBinding.signFragmentImgPassword.setColorFilter(ContextCompat.getColor(v.context, R.color.emptyColor), android.graphics.PorterDuff.Mode.MULTIPLY)
            signBinding.signFragmentBtnSignIn.setBackgroundResource(R.drawable.btn_dark_bg)
            signBinding.signFragmentBtnSignIn.setTextColor(ContextCompat.getColor(v.context, R.color.btnDarkTextColor))
        }
    }

    private fun goToMainPage(userId: String){
        navDirections = SignInAndSignUpFragmentDirections.actionSignInAndSignUpFragmentToMainFragment(userId)
        Navigation.findNavController(v).navigate(navDirections)
    }
}