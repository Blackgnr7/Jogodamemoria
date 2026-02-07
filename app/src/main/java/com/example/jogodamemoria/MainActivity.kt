package com.example.jogodamemoria

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.jogodamemoria.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    var duracao: Long = 0
    val lista_certa = mutableListOf<String>()
    var pontuação: Int = 0
    var quantidade: Int = 0
    var position_atual: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.botaodejogar.setOnClickListener {
            binding.botaodejogar.visibility = View.INVISIBLE
            binding.modos.visibility = View.VISIBLE
        }
        binding.mododificil.setOnClickListener {
            binding.modos.visibility = View.INVISIBLE
            binding.jogo.visibility = View.VISIBLE
            binding.pontuaO.visibility = View.VISIBLE
            binding.contador.visibility = View.VISIBLE
            contador()
            duracao = 200
        }
        binding.modomedio.setOnClickListener {
            binding.modos.visibility = View.INVISIBLE
            binding.jogo.visibility = View.VISIBLE
            binding.pontuaO.visibility = View.VISIBLE
            binding.contador.visibility = View.VISIBLE
            contador()
            duracao = 300
        }
        binding.modofacil.setOnClickListener {
            binding.modos.visibility = View.INVISIBLE
            binding.jogo.visibility = View.VISIBLE
            binding.pontuaO.visibility = View.VISIBLE
            binding.contador.visibility = View.VISIBLE
            contador()
            duracao = 500
        }
        binding.red.setOnClickListener {
            if (lista_certa[position_atual] == "red") {
                position_atual += 1
                if (lista_certa.size == position_atual) {
                    position_atual = 0
                    pontuação += 1
                    change_text()
                    desligar()
                    add()
                    ligar()
                }
            } else {
                lifecycleScope.launch { game_over() }
            }
        }
        binding.green.setOnClickListener {
            if (lista_certa[position_atual] == "green") {
                position_atual += 1
                if (lista_certa.size == position_atual) {
                    position_atual = 0
                    pontuação += 1
                    change_text()
                    desligar()
                    add()
                    ligar()
                }
            } else {
                lifecycleScope.launch { game_over() }
            }
        }
        binding.blue.setOnClickListener {
            if (lista_certa[position_atual] == "blue") {
                position_atual += 1
                if (lista_certa.size == position_atual) {
                    position_atual = 0
                    pontuação += 1
                    change_text()
                    desligar()
                    add()
                    ligar()
                }
            } else {
                lifecycleScope.launch { game_over()}
            }
        }
        binding.yellow.setOnClickListener {
            if (lista_certa[position_atual] == "yellow") {
                position_atual += 1
                if (lista_certa.size == position_atual) {
                    position_atual = 0
                    pontuação += 1
                    change_text()
                    desligar()
                    add()
                    ligar()
                }
            } else {
                lifecycleScope.launch { game_over() }
            }
        }
    }

    fun contador() {
        val textView = binding.textView
        val animator = ValueAnimator.ofInt(3, 0)
        animator.setDuration(2000)
        animator.addUpdateListener(object : AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                textView.setText(animation.getAnimatedValue().toString())
                if (textView.text.toString().toInt() == 0) {
                    binding.contador.visibility = View.INVISIBLE
                    add()
                    ligar()
                }
            }
        })
        animator.start()
    }

    suspend fun game_over() {
        lista_certa.clear()
        duracao = 0
        desligar()
        binding.gameover.visibility = View.VISIBLE
        delay(1000)
        binding.gameover.visibility = View.INVISIBLE
        position_atual = 0
        pontuação = 0
        change_text()
        binding.modos.visibility = View.INVISIBLE
        binding.jogo.visibility = View.INVISIBLE
        binding.pontuaO.visibility = View.INVISIBLE
        binding.contador.visibility = View.INVISIBLE
        binding.botaodejogar.visibility = View.VISIBLE
    }

    fun desligar() {
        binding.red.isEnabled = false
        binding.green.isEnabled = false
        binding.blue.isEnabled = false
        binding.yellow.isEnabled = false
    }

    fun ligar(){
        binding.red.isEnabled = true
        binding.green.isEnabled = true
        binding.blue.isEnabled = true
        binding.yellow.isEnabled = true
    }

    fun add(){
        val random: Int = random()
        if(random == 1){
            lista_certa.add("red")
        }
        if(random == 2){
            lista_certa.add("green")
        }
        if(random == 3){
            lista_certa.add("blue")
        }
        if(random == 4){
            lista_certa.add("yellow")
        }
        quantidade += 1
        animation()
    }

    fun animation() {
        lifecycleScope.launch {
            for (elemento in lista_certa) {
                when (elemento) {
                    "yellow" -> animarBotao(
                        binding.yellow,
                    )

                    "blue" -> animarBotao(
                        binding.blue,
                    )

                    "green" -> animarBotao(
                        binding.green,
                    )

                    "red" -> animarBotao(
                        binding.red,
                    )
                }
            }
        }
    }

    suspend fun animarBotao( button: View, ) {
        button.isPressed = false

        delay(duracao)

        button.isPressed = true

        delay(duracao)

        button.isPressed = false

        delay(300)
    }

    fun change_text(){
        binding.pontuaO.setText("Pontuação: " + pontuação)
    }

    fun random(): Int{
        val randomInt: Int = (1..4).random()
        return randomInt
    }
}