package com.example.teraroon.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teraroon.DbHelper
import com.example.teraroon.GameActivity
import com.example.teraroon.R
import com.example.teraroon.databinding.FragmentGalleryBinding
import com.google.android.material.snackbar.Snackbar

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var count: Int = 0
    private var progress: Int = 0
    private var currentGoal: Int = 0
    private lateinit var login: String
    private lateinit var pass: String
    private lateinit var db: DbHelper


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val gameInst = GameActivity()
        db = DbHelper(gameInst.getContext(), null)
        login = arguments?.getString("login")!!
        pass = arguments?.getString("pass")!!

        count = db.getCount(login, pass)
        progress = db.getProgress(login, pass)
        changeGoal()

        if (currentGoal == count)
            binding.splitMessage.text = "Пора делить!!!"
        else
            binding.splitMessage.text = "Еще нажимай!"


        binding.clickButton.setOnClickListener {
            if (count < currentGoal - 1) {

                count += 1
                binding.textView.text = "Кол-во нажатий: $count"
            }
            else if (count < currentGoal) {
                binding.splitMessage.text = "Пора делить!!!"

                count += 1
                binding.textView.text = "Кол-во нажатий: $count"
            }
            else {
                if (progress == 12) {
                    Toast.makeText(gameInst.getContext(), "Поздравляю! Ты прошел игру! \n Беги скорей, показывай разрабу!", Toast.LENGTH_LONG).show()
                    binding.winMessage.text = "Победа!"
                }
                else {
                    Snackbar.make(requireView(), "Ты совершаешь глупыую ошибку нажимая сюда", Snackbar.LENGTH_LONG).show()
                    if(count%10 == 0){
                        db.setCount(login, pass, count)
                        db.setProgress(login, pass, progress)
                        Snackbar.make(requireView(), "Ваш прогресс и нажатия успешно сохранены", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.splitButton.setOnClickListener {
            if (count%2 == 0) {
                if (progress > 4) {
                    if (count == currentGoal) {
                        progress += 1
                        binding.splitMessage.text = "Еще нажимай!"
                        db.setProgress(login, pass, progress)
                        changeGoal()
                    }
                    if (progress == 12)
                        Toast.makeText(gameInst.getContext(), "Поздравляю! Ты прошел игру! \n Беги скорей, показывай разрабу!", Toast.LENGTH_LONG).show()
                    count /= 2
                    binding.textView.text = "Кол-во нажатий: $count"
                }
                if ((progress == 0)&&(count == 100)) {
                    count /= 2
                    binding.splitMessage.text = "Еще нажимай!"
                    binding.textView.text = "Кол-во нажатий: $count"
                    progress += 1
                    db.setProgress(login, pass, progress)
                    changeGoal()
                    Toast.makeText(gameInst.getContext(), "Поздравляю! \n У вас открылась способность делить! \n Отныне нажимай на эту кнопку, \n когда захочешь!", Toast.LENGTH_LONG).show()
                }
                if ((progress == 1)&&(count == 50)) {
                    count /= 2
                    binding.splitMessage.text = "Еще нажимай!"
                    binding.textView.text = "Кол-во нажатий: $count"
                    progress += 1
                    db.setProgress(login, pass, progress)
                    changeGoal()
                }
                if ((progress == 2)&&(count == 36)) {
                    count /= 2
                    binding.splitMessage.text = "Еще нажимай!"
                    binding.textView.text = "Кол-во нажатий: $count"
                    progress += 1
                    db.setProgress(login, pass, progress)
                    changeGoal()
                    Toast.makeText(gameInst.getContext(), "Ой-ой-ой!", Toast.LENGTH_SHORT).show()
                }
                if ((progress == 3)&&(count == 18)) {
                    count /= 2
                    binding.splitMessage.text = "Еще нажимай!"
                    binding.textView.text = "Кол-во нажатий: $count"
                    progress += 2
                    changeGoal()
                    Toast.makeText(gameInst.getContext(), "Ты разгадал тайну! Теперь ты как сыр в масле!", Toast.LENGTH_LONG).show()
                }
                if ((progress == 0)&&(count < 100)) {
                    Toast.makeText(gameInst.getContext(), "У тебя еще не открылась такая способность", Toast.LENGTH_LONG).show()
                }
            }
            else {
                Toast.makeText(gameInst.getContext(), "НЕЛЬЗЯ делить непарное число!", Toast.LENGTH_SHORT).show()
            }
        }

        val textView: TextView = binding.textView
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = "Кол-во нажатий: $count"
        }

        return root

    }

    private fun changeGoal() {
        
        if (progress == 0)
            currentGoal = 100
        if (progress == 1)
            currentGoal = 50
        if (progress == 2)
            currentGoal = 100
        if (progress == 3)
            currentGoal = 100
        if (progress == 4)
            currentGoal = 100
        if (progress == 5)
            currentGoal = 10
        if (progress == 6)
            currentGoal = 100
        if (progress == 7)
            currentGoal = 150
        if (progress == 8)
            currentGoal = 200
        if (progress == 9)
            currentGoal = 250
        if (progress == 10)
            currentGoal = 500
        if (progress == 11)
            currentGoal = 1000
        
    }

    override fun onDestroyView() {
        db.setProgress(login, pass, progress)
        db.setCount(login, pass, count)
        super.onDestroyView()
        _binding = null
    }
}