package com.example.carevault.authentication

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.carevault.MainFragment
import com.example.carevault.R
import com.example.carevault.WelcomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val backButton = findViewById<ImageButton>(R.id.imageButton2)
        val loginButton = findViewById<TextView>(R.id.textView5)
        val emailEditText = findViewById<EditText>(R.id.textView6)
        val passwordEditText = findViewById<EditText>(R.id.textView3)
        val signuptext = findViewById<TextView>(R.id.textView7)
        val signinnumber = findViewById<TextView>(R.id.textView12)

        passwordEditText.setOnTouchListener { _, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= passwordEditText.right - passwordEditText.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    togglePasswordVisibility(passwordEditText)
                    return@setOnTouchListener true
                }
            }
            false
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success
                            Toast.makeText(
                                applicationContext,
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(Intent(this, MainFragment::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please enter both email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        signuptext.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }

        signinnumber.setOnClickListener {
            startActivity(Intent(this, LoginWithNumberActivity::class.java))
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webclientid))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val googleSignInButton = findViewById<TextView>(R.id.textView9)

        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleGoogleSignInResult(result.resultCode, result.data)
        }

        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }
    }
    private fun signInWithGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
    private fun handleGoogleSignInResult(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                firebaseAuthWithGoogle(account.idToken)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Toast.makeText(applicationContext, "Google Sign-In successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainFragment::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Authentication failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun togglePasswordVisibility(editText: EditText) {
        val selection = editText.selectionEnd
        if (editText.transformationMethod == PasswordTransformationMethod.getInstance()) {
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        editText.setSelection(selection)
    }
}