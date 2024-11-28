package com.zhbcompany.shop.di

import com.zhbcompany.shop.domain.repo.ShopRepository
import com.zhbcompany.shop.domain.use_case.ShopUseCases
import org.koin.core.module.Module
import org.koin.dsl.module

private fun provideShopUseCases(repo: ShopRepository): ShopUseCases {
    return ShopUseCases(repo)
}

val domainModule: Module = module {
    single { provideShopUseCases(get()) }
}