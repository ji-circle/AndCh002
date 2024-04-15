package com.example.chtask002

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider

class SignUpActivity : AppCompatActivity() {

    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this)[SignUpViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val nameIn = findViewById<EditText>(R.id.et_name)
        val emailAdd = findViewById<EditText>(R.id.et_add)
        val emailDom = findViewById<EditText>(R.id.et_dom)
        val pwIn = findViewById<EditText>(R.id.et_password)
        val checkPw = findViewById<EditText>(R.id.et_password2)

        val nameError = findViewById<TextView>(R.id.tv_nameError)
        val addError = findViewById<TextView>(R.id.tv_addError)
        val domError = findViewById<TextView>(R.id.tv_domError)
        val pwError = findViewById<TextView>(R.id.tv_passwordError)
        val checkPwError = findViewById<TextView>(R.id.tv_passwordError2)

        val joinButton = findViewById<Button>(R.id.btn_join)

        // EditText의 포커스 이벤트 및 텍스트 변경 이벤트 처리하는 부분...
        //화면에 있는 EditText들을 담아둠
        // 각 EditText에 대해 포커스가 벗어났을 때와 텍스트가 변경될 때의 리스너를 등록함
        //포커스 벗어났을 때 -> checkAll() 메서드 호출해 모든 입력에 대해 유효성 검사
        //텍스트 변경되었을 때 -> viewModel.onTextChanged() 메서드 호출,
        // 해당 EditText의 id랑 변경된 텍스트를 뷰모델에 전달
        val editTextList = listOf(nameIn, emailAdd, emailDom, pwIn, checkPw)
        editTextList.forEach { editText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    checkAll()
                }
            }
            editText.addTextChangedListener {
                viewModel.onTextChanged(editText.id, it.toString())
            }
        }

        // 뷰모델에서 유효성 검사한 것의 결과를 관찰해서 화면에 에러메세지 반영하는 부분
        viewModel.usernameError.observe(this) { errorMessage ->
            if (errorMessage != null) {
                nameError.visibility = View.VISIBLE
                nameError.text = errorMessage
            } else {
                nameError.visibility = View.INVISIBLE
            }
        }
        viewModel.emailAddError.observe(this) { errorMessage ->
            if (errorMessage != null) {
                addError.visibility = View.VISIBLE
                addError.text = errorMessage
            } else {
                addError.visibility = View.INVISIBLE
            }
        }
        viewModel.emailDomError.observe(this) { errorMessage ->
            if (errorMessage != null) {
                domError.visibility = View.VISIBLE
                domError.text = errorMessage
            } else {
                domError.visibility = View.INVISIBLE
            }
        }
        viewModel.pwError.observe(this) { errorMessage ->
            if (errorMessage != null) {
                pwError.visibility = View.VISIBLE
                pwError.text = errorMessage
                pwError.isEnabled = true
            } else {
                //pwError.visibility = View.INVISIBLE
                pwError.isEnabled = false
                pwError.visibility = View.VISIBLE
            }
        }
        viewModel.checkPwError.observe(this) { errorMessage ->
            if (errorMessage != null) {
                checkPwError.visibility = View.VISIBLE
                checkPwError.text = errorMessage
            } else {
                checkPwError.visibility = View.INVISIBLE
            }
        }
        //다 제대로 입력되었는지 확인 후 회원가입 버튼 활성화
        viewModel.isJoinEnabled.observe(this) { isEnabled ->
            joinButton.isEnabled = isEnabled
        }

        // 회원가입 버튼 클릭 이벤트 처리... 코드 추가해야 함
        joinButton.setOnClickListener {
            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
            //추가하기...
        }
    }

    private fun checkAll() {
        viewModel.checkAll()
    }
}
