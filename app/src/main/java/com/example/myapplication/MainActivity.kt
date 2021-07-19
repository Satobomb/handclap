package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var yourHand = 0
    private var droidHand = 0
    private var winCount = 0
    private var loseCount = 0
    private var gameStart = false

    /**
     * アクティビティ生成時の処理test
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guBtn.setOnClickListener{
            if(gameStart) {
                yourHand = 0
                compareHand()
            }
        }

        chokiBtn.setOnClickListener{
            if(gameStart) {
                yourHand = 1
                compareHand()
            }
        }

        paBtn.setOnClickListener{
            if(gameStart) {
                yourHand = 2
                compareHand()
            }
        }

        resetBtn.setOnClickListener{
            onResume()
        }
    }

    /**
     * ユーザーの操作受付処理
     */
    override fun onResume() {
        super.onResume()
        winCount = 0
        loseCount = 0
        winText.text = getString(R.string.win_text)
        loseText.text = getString(R.string.lose_text)
        resultText.text = "じゃんけん"

        // ゲーム開始状態にする
        gameStart = true
        // リセットボタンを非表示
        resetBtn.visibility = View.INVISIBLE

    }

    /**
     * 自分とCPUの手の比較をする
     */
    private fun compareHand(){
        resultText.text = "ぽん！"
        // CPUの手をランダムで決定
        droidHand = (0..2).random()
        // 画面にイメージを表示する
        showHand()

        when(yourHand){
            // グー
            0 -> {
                if(droidHand == 0){
                    // do nothing
                }else if(droidHand == 1){
                    winCount++
                    winText.text = getString(R.string.win_text) + winCount
                }else if(droidHand == 2){
                    loseCount++
                    loseText.text = getString(R.string.lose_text) + loseCount
                }
            }
            // チョキ
            1 -> {
                if(droidHand == 0){
                    loseCount++
                    loseText.text = getString(R.string.lose_text) + loseCount
                }else if(droidHand == 1){
                    // do nothing
                }else if(droidHand == 2){
                    winCount++
                    winText.text = getString(R.string.win_text) + winCount
                }
            }
            // パー
            2 -> {
                if(droidHand == 0){
                    winCount++
                    winText.text = getString(R.string.win_text) + winCount
                }else if(droidHand == 1){
                    loseCount++
                    loseText.text = getString(R.string.lose_text) + loseCount
                }else if(droidHand == 2){
                    // do nothing
                }
            }
        }

        if(winCount == 5){
            resultText.text = "あなたの勝ちです！"
            gameStart = false
            resetBtn.visibility = View.VISIBLE
        }else if(loseCount == 5){
            resultText.text = "あなたの負けです・・・"
            gameStart = false
            resetBtn.visibility = View.VISIBLE
        }else{
            // do nothing
        }
    }

    private fun showHand(){
        when(yourHand){
            0 -> yourHandImage.setImageResource(R.drawable.janken_gu)
            1 -> yourHandImage.setImageResource(R.drawable.janken_choki)
            2 -> yourHandImage.setImageResource(R.drawable.janken_pa)
        }

        when(droidHand){
            0 -> droidHandImage.setImageResource(R.drawable.janken_gu)
            1 -> droidHandImage.setImageResource(R.drawable.janken_choki)
            2 -> droidHandImage.setImageResource(R.drawable.janken_pa)
        }
    }
}