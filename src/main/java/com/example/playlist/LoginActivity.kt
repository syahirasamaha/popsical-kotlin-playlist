package com.example.playlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.playlist.model.LoginRequest
import com.example.playlist.model.LoginResponse
import com.example.playlist.network.ApiClient
import com.example.playlist.network.PrefService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class LoginStatus { LOADING, ERROR, DONE}

class LoginActivity : AppCompatActivity() {
    private lateinit var prefService: PrefService
    private lateinit var apiClient: ApiClient

    private val _status = MutableLiveData<LoginStatus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        submitButton.setOnClickListener {

            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if(email.isEmpty()) {
                editTextEmail.error = "Email required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()) {
                editTextPassword.error = "Password required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            apiClient = ApiClient()
            prefService = PrefService(this)

            apiClient.getApiService(this).login(
                LoginRequest(
                    email, password,
                    deviceId = "key here",
                    clientId = "key here",
                    scope = "key here"
                )
            )
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        val loginResponse = response.body()

                        _status.value = LoginStatus.LOADING
                        if (response.code() == 200) {
                            try {
                                openPlaylistActivity()
                                if (loginResponse != null) {
                                    prefService.saveAuthToken(loginResponse.accessToken)
                                }
                                _status.value = LoginStatus.DONE
                            } catch (e: Exception) {
                                Toast.makeText(
                                    applicationContext,
                                    "Failure: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                _status.value = LoginStatus.ERROR
                            }
                        }else{
                            Toast.makeText(applicationContext, "Error: ${response.body().toString()}", Toast.LENGTH_LONG)
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        _status.value = LoginStatus.ERROR

                    }

                })
        }

    }

    private fun openPlaylistActivity() {
        val intent = Intent(this, PlaylistActivity::class.java)
        startActivity(intent)
        finish()
    }

}