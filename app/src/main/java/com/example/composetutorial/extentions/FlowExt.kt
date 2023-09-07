package com.example.composetutorial.extentions

import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.merge(flow : Flow<T>) : Flow<T> = kotlinx.coroutines.flow.merge(this, flow)