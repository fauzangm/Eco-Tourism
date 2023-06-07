package com.id.etourism.ui.onBoarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.id.etourism.R
import com.id.etourism.databinding.ActivityOnboardingBinding
import com.id.etourism.ui.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import me.relex.circleindicator.CircleIndicator
import splitties.activities.start
import timber.log.Timber

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        try {
            initUi()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initUi() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(OnBoarding1Fragment(), "")
        viewPagerAdapter.addFragment(OnBoarding2Fragment(), "")
        viewPagerAdapter.addFragment(OnBoarding3Fragment(), "")
        binding.onboardingGuideContainer.adapter = viewPagerAdapter

        setupIndicator()
        initAction()
    }

    private fun initAction() {
        binding.tvSkipGuidance.setOnClickListener {
            start<LoginActivity>()
            finishAffinity()
        }
    }

    private fun setupIndicator(){
        Timber.e("indicator muncul")
        val indicator: CircleIndicator = findViewById(R.id.indicator)
        indicator.setViewPager(binding.onboardingGuideContainer)
    }
}