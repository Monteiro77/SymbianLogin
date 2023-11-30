package br.senai.sp.jandira.screens.signup

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleCoroutineScope
import br.senai.sp.jandira.R
import br.senai.sp.jandira.components.InputOutlineTextField
import br.senai.sp.jandira.model.SignUpRepository
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    lifecycleScope: LifecycleCoroutineScope
) {
    lateinit var storageRef: StorageReference
    lateinit var fibaseFirestore: FirebaseFirestore
    storageRef = FirebaseStorage.getInstance().reference.child("images")
    fibaseFirestore = FirebaseFirestore.getInstance()


    var emailState by remember {
        mutableStateOf("")
    }

    var senhaState by remember {
        mutableStateOf("")
    }

    var imageUri by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        imageUri.ifEmpty {
            R.drawable.baseline_photo_camera_24
        }
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri = uri.toString() }
    }

    fun signup(
        email: String,
        senha: String,
        foto: String
    ) {
        val signUp = SignUpRepository()

        lifecycleScope.launch {

            val response = signUp.signupClient(
                email,
                senha,
                foto
            )

            if (response.isSuccessful) {
                Toast.makeText(context, "CADASTRADO COM SUCESSO", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
            }

        }

    }





    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(201, 226, 250)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = "SYMBIAN",
                )

                Text(
                    text = "CADASTRO"
                )
            }

            Surface(
                color = Color(201, 226, 250)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(200.dp)
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = CircleShape
                        )
                )
                Box(modifier = Modifier.padding(top = 150.dp, start = 150.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White)
                            .size(50.dp)
                            .padding(10.dp)
                            .clickable { launcher.launch("image/*") }
                    )
                }
            }


            InputOutlineTextField(
                value = emailState,
                onValueChange = { emailState = it },
                leadingIconImageVector = Icons.Default.Email,
                showError = false,
                borderColor = Color(255, 255, 255, 255),
                border = RoundedCornerShape(15.dp),
                label = "Email"
            )

            InputOutlineTextField(
                value = senhaState,
                onValueChange = { senhaState = it },
                leadingIconImageVector = Icons.Default.Lock,
                showError = false,
                borderColor = Color(255, 255, 255, 255),
                border = RoundedCornerShape(15.dp),
                label = "Senha",
            )

            Button(
                onClick = {

                    storageRef = storageRef.child(System.currentTimeMillis().toString())
                    imageUri.let {
                        it.let {
                            storageRef.putFile(it.toUri()).addOnCompleteListener{task ->
                                if(task.isSuccessful){

                                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                                        val map = HashMap<String, Any>()
                                        map["pic"] = uri.toString()
                                        imageUri = map.toString()
                                        fibaseFirestore.collection("images").add(map)
                                        Log.e("TESTE", "SignUpScreen: $imageUri")
                                        signup(emailState, senhaState, imageUri)

                                    }

                                }
                            }
                        }
                    }

                },
                modifier = Modifier
                    .height(50.dp)
                    .width(130.dp),

                ) {
                Text(text = "REGISTER")

            }
        }

    }
}
