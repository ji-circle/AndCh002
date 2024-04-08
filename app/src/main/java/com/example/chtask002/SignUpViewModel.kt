package com.example.chtask002

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    private val _usernameError = MutableLiveData<String>()
    val usernameError: LiveData<String>
        get() = _usernameError

    private val _emailAddError = MutableLiveData<String>()
    val emailAddError: LiveData<String>
        get() = _emailAddError

    private val _emailDomError = MutableLiveData<String>()
    val emailDomError: LiveData<String>
        get() = _emailDomError

    private val _pwError = MutableLiveData<String>()
    val pwError: LiveData<String>
        get() = _pwError

    private val _checkPwError = MutableLiveData<String>()
    val checkPwError: LiveData<String>
        get() = _checkPwError

    private val _isJoinEnabled = MutableLiveData<Boolean>()
    val isJoinEnabled: LiveData<Boolean>
        get() = _isJoinEnabled


    private val _username = MutableLiveData<String>()
    private val _emailAdd = MutableLiveData<String>()
    private val _emailDom = MutableLiveData<String>()
    private val _pw = MutableLiveData<String>()
    private val _pwCheck = MutableLiveData<String>()

    init {
        _usernameError.value = null
        _emailAddError.value = null
        _emailDomError.value = null
        _pwError.value = null
        _checkPwError.value = null
        _isJoinEnabled.value = false
    }

    //EditText의 텍스트가 변경될 때 호출되는 부분임
    //변경된 해당 입력 필드의 id랑 변경된 텍스트를 받아옴
    fun onTextChanged(viewId: Int, text: String) {
        when (viewId) {
            R.id.et_name -> _username.value = text
            R.id.et_add -> _emailAdd.value = text
            R.id.et_dom -> _emailDom.value = text
            R.id.et_password -> _pw.value = text
            R.id.et_password2 -> _pwCheck.value = text
        }
    }

    fun checkAll() {
        //null이면 빈 문자열로 대체하게
        val username = _username.value.orEmpty()
        val userAdd = _emailAdd.value.orEmpty()
        val userDom = _emailDom.value.orEmpty()
        val password = _pw.value.orEmpty()
        val passwordCheck = _pwCheck.value.orEmpty()

        _usernameError.value = if (username.isEmpty()) "이름을 입력하세요." else null
        _emailAddError.value = if (userAdd.isEmpty()) "이메일을 입력하세요." else null
        _emailDomError.value = if (userDom.isEmpty()) "도메인을 입력하세요." else null

        _pwError.value = when {
            password.isEmpty() -> "10자리 이상, 특수문자, 대문자 포함"
            password.length < 10 -> "비밀번호는 10자 이상 입력해주세요."
            !password.any { it.isUpperCase() } -> "비밀번호에 대문자를 포함해주세요."
            !password.any { !it.isLetterOrDigit() } -> "비밀번호에 특수문자를 포함해주세요."
            else -> null
        }
        _checkPwError.value = when {
            passwordCheck.isEmpty() -> "비밀번호를 다시 입력하세요."
            passwordCheck != password -> "비밀번호가 일치하지 않습니다."
            else -> null
        }

        checkStatus()
    }


    private fun checkStatus() {
        // = 해당 LiveData가 오류 메시지를 포함하고 있는지 여부(값이 null이거나 빈 문자열인지 등) 확인
        // 에러 메시지가 없거나 비어있음 -> true를 반환
        val isName = _usernameError.value.isNullOrEmpty()
        val isAdd = _emailAddError.value.isNullOrEmpty()
        val isDom = _emailDomError.value.isNullOrEmpty()
        val isPw = _pwError.value.isNullOrEmpty()
        val isCheckPw = _checkPwError.value.isNullOrEmpty()
        _isJoinEnabled.value = isName && isAdd && isDom && isPw && isCheckPw
    }
}
