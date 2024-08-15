package com.example.customwaterdrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.customwaterdrop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var waterDropProgressBar: WaterDropProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        waterDropProgressBar = binding.customWater



        binding.btTarget.setOnClickListener {
            val target = binding.etTarget.text.toString().toFloatOrNull()
            if (target != null){
                waterDropProgressBar.setDailyTarget(target)
                Toast.makeText(this,"$target Target added",Toast.LENGTH_SHORT).show()
            }
        }

        binding.btAdd.setOnClickListener {
            val addValue = binding.etValue.text.toString().toFloatOrNull()
            if (addValue != null) {
                waterDropProgressBar.updateProgress(addValue)
                Toast.makeText(this,"$addValue value added",Toast.LENGTH_SHORT).show()
            }
        }

        binding.btMinus.setOnClickListener {
            val minusValue = binding.etValue.text.toString().toFloatOrNull()
            if (minusValue != null) {
                waterDropProgressBar.updateProgress(-minusValue)
                Toast.makeText(this,"$minusValue value minus",Toast.LENGTH_SHORT).show()
            }
        }
    }
}