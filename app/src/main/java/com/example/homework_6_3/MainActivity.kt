package com.example.homework_6_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.core.widget.addTextChangedListener
import com.example.homework_6_3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = HashtagAdapter(this::replaceSelected)
    private val savedTags = hashSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        saveHashtag()
        showSavedHashtags()
    }

    private fun saveHashtag() {
        binding.editText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val input = binding.editText.text.toString()
                if (input.startsWith(getString(R.string.hashtag))) {
                    savedTags.addAll(getAll(input))
                }
                binding.editText.text.clear()
                true
            } else {
                false
            }
        }
    }

    private fun replaceSelected(selectedTag: String) {
        val message = binding.editText.text.toString()
        val words = message.split(getString(R.string.space)).toMutableList()
        val tagIndex = getTagIndex(words, message)
        words[tagIndex] = selectedTag
        binding.editText.setText(words.joinToString(getString(R.string.space)))
        binding.editText.setSelection(binding.editText.text.length)
    }

    private fun getTagIndex(words: MutableList<String>, message: String): Int {
        for (tag in getAll(message)) {
            if (!savedTags.contains(tag))
                return words.indexOf(tag)
        }
        return 0
    }

    private fun showSavedHashtags() {
        binding.editText.addTextChangedListener {
            adapter.addHashtag(getMatching(binding.editText.text.toString()))
        }
    }

    private fun getAll(text: String): ArrayList<String> {
        val tags = arrayListOf<String>()
        val message = text.split(getString(R.string.space))
        for (word in message) {
            if (word.startsWith(getString(R.string.hashtag))) {
                tags.add(word)
            }
        }
        return tags
    }

    private fun getMatching(text: String): ArrayList<String> {
        val matchingTags = arrayListOf<String>()
        val inputTags = getAll(text)
        for (tag in inputTags) {
            for (savedTag in savedTags) {
                if (savedTag.contains(tag) && savedTag != tag)
                    matchingTags.add(savedTag)
            }
        }
        return matchingTags
    }

}
