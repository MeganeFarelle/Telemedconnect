package com.telemedconnect.patient.ui.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.telemedconnect.patient.api.Api
import com.telemedconnect.patient.data.entities.Subscription
import com.telemedconnect.patient.databinding.ActivityAccountPlanBinding
import com.telemedconnect.patient.ui.adapters.SubscriptionsAdapter
import com.telemedconnect.patient.ui.utils.SpaceItemDecoration
import com.telemedconnect.patient.ui.view_models.AccountPlanViewModel


open class AccountPlanActivity : BaseActivity() {

    private lateinit var binding: ActivityAccountPlanBinding
    lateinit var viewModel: AccountPlanViewModel

    private lateinit var subscriptionAdapter: SubscriptionsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AccountPlanViewModel::class.java)

        binding.activity = this
        binding.viewModel = viewModel

        linearLayoutManager =  LinearLayoutManager(applicationContext)
        subscriptionAdapter = SubscriptionsAdapter(this, Api.getSubscriptions(this))
        binding.subscriptionsRecyclerView.apply {
            adapter = subscriptionAdapter
            layoutManager =linearLayoutManager
            addItemDecoration(SpaceItemDecoration(30, 0))
        }

        subscriptionAdapter.setOnClickListener(object : SubscriptionsAdapter.OnClickListener{
            override fun onClick(item: Subscription, position: Int) {
                linearLayoutManager.scrollToPositionWithOffset(position, 0)
            }
        })
    }

    fun backPressed(){
        finish()
    }

}