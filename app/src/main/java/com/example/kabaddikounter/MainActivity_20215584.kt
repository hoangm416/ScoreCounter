package com.example.kabaddikounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.kabaddikounter.databinding.ActivityMainBinding

//Ho ten: Vuong Kim Hoang
//MSSV: 20215584
class MainActivity : AppCompatActivity() {
    // Delegate provided by androidx.activity.viewModels
    val viewModel: ScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.plusOneButtonA.setOnClickListener {
            viewModel.incrementOneScore(true)
        }
        binding.plusOneButtonB.setOnClickListener {
            viewModel.incrementOneScore(false)
        }

        binding.plusTwoButtonA.setOnClickListener {
            viewModel.incrementTwoScore(true)
        }
        binding.plusTwoButtonB.setOnClickListener {
            viewModel.incrementTwoScore(false)
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetScore()
        }

        val scoreA_Observer = Observer<Int> {
            newValueA -> binding.scoreViewA.text = newValueA.toString()
        }
        val scoreB_Observer = Observer<Int> {
            newValueB -> binding.scoreViewB.text = newValueB.toString()
        }

        viewModel.scoreA.observe(this, scoreA_Observer)
        viewModel.scoreB.observe(this, scoreB_Observer)
    }
}

class ScoreViewModel : ViewModel() {
    private val _scoreA = MutableLiveData<Int>(0)
    private val _scoreB = MutableLiveData<Int>(0)
    val scoreA: LiveData<Int>
        get() = _scoreA
    val scoreB: LiveData<Int>
        get() = _scoreB

    fun incrementOneScore(isTeamA: Boolean) {
        if (isTeamA) {
            _scoreA.value = _scoreA.value!! + 1
        }
        else {
            _scoreB.value = _scoreB.value!! + 1
        }
    }

    fun incrementTwoScore(isTeamA: Boolean) {
        if (isTeamA) {
            _scoreA.value = _scoreA.value!! + 2
        }
        else {
            _scoreB.value = _scoreB.value!! + 2
        }
    }

    fun resetScore() {
        _scoreA.value = 0
        _scoreB.value = 0
    }
}