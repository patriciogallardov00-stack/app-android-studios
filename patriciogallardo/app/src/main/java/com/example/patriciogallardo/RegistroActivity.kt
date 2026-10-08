package com.example.patriciogallardo


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registroactiviti)

        val etNuevoUsuario = findViewById<EditText>(R.id.etNuevoUsuario)
        val etNuevaPassword = findViewById<EditText>(R.id.etNuevaPassword)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val usuario = etNuevoUsuario.text.toString()
            val password = etNuevaPassword.text.toString()

            // IP de tu Raspbian apuntando a ingreso.php
            val url = "http://192.168.137.203/ingreso.php"

            val queue = Volley.newRequestQueue(this)
            val request = object : StringRequest(
                Method.POST, url,
                { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val status = jsonObject.getString("status")
                        val mensaje = jsonObject.getString("mensaje")

                        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

                        if (status == "exito") {
                            finish() // Cierra la pantalla y vuelve al login automáticamente
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error de datos", Toast.LENGTH_SHORT).show()
                    }
                },
                { error ->
                    Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
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