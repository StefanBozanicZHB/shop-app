package com.zhbcompany.shop.di

import com.zhbcompany.shop.domain.use_case.ShopUseCases
import com.zhbcompany.shop.presentation.shop_list.ShopListViewModel
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

private fun provideShopListViewModel(
    uceCase: ShopUseCases,
    dispatcher: CoroutineDispatcher
): ShopListViewModel {
    return ShopListViewModel(uceCase, dispatcher)
}

val presentationModule: Module = module {
    viewModel { provideShopListViewModel(get(), get(named("IoDispatcher"))) }
}