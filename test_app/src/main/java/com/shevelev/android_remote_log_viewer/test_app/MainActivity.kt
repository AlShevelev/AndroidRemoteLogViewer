package com.shevelev.android_remote_log_viewer.test_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shevelev.android_remote_log_viewer.test_app.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        showLog()
    }

    @SuppressLint("SetTextI18n")
    private fun showLog() {
        try {
            val process = Runtime.getRuntime().exec("logcat -d")

            val log = BufferedReader(InputStreamReader(process.inputStream)).use { bufferedReader ->
                val logBuilder = StringBuilder()

                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    logBuilder.appendLine(line)
                    logBuilder.appendLine()
                }

                logBuilder
            }

            binding.logOutput.text = log.toString()
        } catch (ex: IOException) {
            binding.logOutput.text = "Something went wrong: ${ex.message}"
        }
    }
}