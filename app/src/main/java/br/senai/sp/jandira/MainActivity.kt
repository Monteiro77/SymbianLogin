package br.senai.sp.jandira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.screens.login.LoginScreen
import br.senai.sp.jandira.screens.signup.SignUpScreen
import br.senai.sp.jandira.ui.theme.LoginSymbianTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginSymbianTheme {

                val navController = rememberNavController()
                val lifecycleScope = lifecycleScope
                
                NavHost(
                    navController = navController,
                    startDestination = "signup_screen"
                ){



                    composable(route = "signup_screen"){
                        SignUpScreen(lifecycleScope)
                    }

                }
            }
        }
    }
}
