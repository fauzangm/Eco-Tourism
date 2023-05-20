package com.id.etourism.data.network.repository.wisata

import com.id.etourism.data.network.model.Wisata
import com.id.etourism.utils.ExceptionState

interface WisataRepository {

    fun getWisata(result: (ExceptionState<List<Wisata>>) -> Unit)
}