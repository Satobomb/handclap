package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var yourHand = 0
    private var droidHand = 0
    private var YourChargeCount = 0
    private var DroidChargeCount = 0
    private var gameStart = false
    private var firstFlag = 1

    /**
     * アクティビティ生成時の処理
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chBtn.setOnClickListener{
            if(gameStart) {
                yourHand = 1
                compareHand()
            }
        }

        atBtn.setOnClickListener{
            if(gameStart) {
                yourHand = 0
                compareHand()
            }
        }

        diBtn.setOnClickListener{
            if(gameStart) {
                yourHand = 2
                compareHand()
            }
        }

        resetBtn.setOnClickListener{
            //onResume()
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * ユーザーの操作受付処理
     */
    override fun onResume() {
        super.onResume()
        YourChargeCount = 0
        DroidChargeCount = 0
        YourChargeText.text = getString(R.string.your_charge_text)
        AndroidChargeText.text = getString(R.string.android_charge_text)
        resultText.text = "せーの！"

        // ゲーム開始状態にする
        gameStart = true
        // リセットボタンを非表示
        resetBtn.visibility = View.INVISIBLE
        // 攻撃ボタンを非表示
        atBtn.visibility = View.INVISIBLE

    }

    /**
     * 自分とCPUの手の比較をする
     */
    private fun compareHand(){
        resultText.text = "ぽん！"
        // CPUの手をランダムで決定
        if(DroidChargeCount >= 1){
            if(YourChargeCount <= 0){
                // 攻撃　or 溜め
                droidHand = (0..1).random()
            }else{
                // 攻撃 or 溜め or 防御
                droidHand = (0..2).random()
            }
        }else{
            if(firstFlag == 1){
                // 溜め
                droidHand = 1
                firstFlag = 0
            }else if(YourChargeCount <= 0) {
                // 溜め
                droidHand = 1
            }else{
                // 溜め or 防御
                droidHand = (1..2).random()
            }
        }

        // 画面にイメージを表示する
        showHand()

        when(yourHand){
            // 溜める
            1 -> {
                YourChargeCount++
                YourChargeText.text = getString(R.string.charge_text) + YourChargeCount
                if(droidHand == 0){
                    resultText.text = "あなたの負けです・・・"
                    gameStart = false
                    resetBtn.visibility = View.VISIBLE
                }else if(droidHand == 1){
                    DroidChargeCount++
                    AndroidChargeText.text = getString(R.string.android_charge_text) + DroidChargeCount
                }else if(droidHand == 2){
                    // do nothing
                }
            }
            // 攻撃
            0 -> {
                YourChargeCount--
                YourChargeText.text = getString(R.string.charge_text) + YourChargeCount
                if(droidHand == 0){
                    DroidChargeCount--
                    AndroidChargeText.text = getString(R.string.android_charge_text) + DroidChargeCount
                }else if(droidHand == 1){
                    resultText.text = "あなたの勝ちです！"
                    gameStart = false
                    resetBtn.visibility = View.VISIBLE
                }else if(droidHand == 2){
                    // do nothing
                }
            }
            // 防御
            2 -> {
                if(droidHand == 0){
                    DroidChargeCount--
                    AndroidChargeText.text = getString(R.string.android_charge_text) + DroidChargeCount
                }else if(droidHand == 1){
                    DroidChargeCount++
                    AndroidChargeText.text = getString(R.string.android_charge_text) + DroidChargeCount
                }else if(droidHand == 2){
                    // do nothing
                }
            }
        }

        if(YourChargeCount >= 1){
            atBtn.visibility = View.VISIBLE
        }else{
            atBtn.visibility = View.INVISIBLE
        }

        /**
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
         */
    }

    private fun showHand(){
        when(yourHand){
            0 -> yourHandImage.setImageResource(R.drawable.attack)
            1 -> yourHandImage.setImageResource(R.drawable.charge)
            2 -> yourHandImage.setImageResource(R.drawable.diffense)
        }

        when(droidHand){
            0 -> droidHandImage.setImageResource(R.drawable.attack)
            1 -> droidHandImage.setImageResource(R.drawable.charge)
            2 -> droidHandImage.setImageResource(R.drawable.diffense)
        }
    }
}
