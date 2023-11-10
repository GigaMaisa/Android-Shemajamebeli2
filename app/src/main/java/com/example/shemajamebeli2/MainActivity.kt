package com.example.shemajamebeli2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.shemajamebeli2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var inputEditText: AppCompatEditText
    private lateinit var countTextView: TextView
    private lateinit var saveButton: AppCompatButton
    private lateinit var outputButton: AppCompatButton
    private lateinit var screenContainer: ConstraintLayout
    private lateinit var buttonsContainer: LinearLayoutCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            inputEditText = etAnagramInput
            countTextView = tvCount
            saveButton = btnSave
            outputButton = btnOutput
            screenContainer = screenBackground
            buttonsContainer = btnContainer
        }

        val words = mutableListOf<String>()
        saveButton.setOnClickListener {
            val userInput: String? = getUserInput()

            userInput?.let {
                words.add(it)
            }
        }

        outputButton.setOnClickListener {
            val anagramGroups = mutableMapOf<String, MutableList<String>>()

            for (word in words) {
                val sortedWord = word.toCharArray().sorted().joinToString("").trim()
                if (!anagramGroups.containsKey(sortedWord)) {
                    anagramGroups[sortedWord] = mutableListOf()
                }
                anagramGroups[sortedWord]?.add(word)
            }

            Log.d("LISTOFINPUTS", "$anagramGroups")
            var lastElementId: Int = buttonsContainer.id
            countTextView.text = anagramGroups.size.toString()

           anagramGroups.values.forEach {
               lastElementId = addField(it.toString(), lastElementId)
           }
        }
    }

    private fun getUserInput(): String? {
        val input = inputEditText.text.toString().trim()

        return if (input == "") {
            showToast("Edit Text Is Empty")
            null
        } else {
            inputEditText.text?.clear()
            input
        }
    }

    private fun addField(text: String, id: Int): Int {
        val textView = TextView(this)
        textView.id = View.generateViewId()
        textView.text = text
        screenContainer.addView(textView)

        val textViewParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        textViewParams.startToStart = screenContainer.id
        textViewParams.endToEnd = screenContainer.id
        textViewParams.topToBottom = id
        textView.layoutParams = textViewParams
        return textView.id
    }

    private fun showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}