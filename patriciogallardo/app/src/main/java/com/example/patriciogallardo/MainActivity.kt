package com.example.patriciogallardo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enlazamos las variables con los elementos del diseño (XML)
        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnIrRegistro = findViewById<Button>(R.id.btnIrRegistro)

        // Botón para ir a la pantalla de crear usuario
        btnIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        // Botón para iniciar sesión
        btnLogin.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val password = etPassword.text.toString()

            // IP de tu Raspbian apuntando a tu archivo login.php
            val url = "http://192.168.137.203/login.php"

            val queue = Volley.newRequestQueue(this)
            val request = object : StringRequest(Request.Method.POST, url,
                { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val status = jsonObject.getString("status")
                        val mensaje = jsonObject.getString("mensaje")

                        // Muestra el mensaje que responde el servidor (Ej: "Bienvenido" o "Error")
                        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

                        if (status == "exito") {
                            // Aquí más adelante puedes poner el código para ir a la pantalla principal de tu app
                        }

                    } catch (e: Exception) {
                        Toast.makeText(this, "Error procesando datos", Toast.LENGTH_SHORT).show()
                    }
                },
                { error ->
                    Toast.makeText(this, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    // Estos son los datos que se envían por POST a tu PHP
                    val parametros = HashMap<String, String>()
                    parametros["usuario"] = usuario
                    parametros["password"] = password
                    return parametros
                }
            }
            queue.add(request)
        }
    }
}