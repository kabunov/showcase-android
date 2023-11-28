package com.kabunov.showcase.model

data class IrregularVerb(
    val id: String,
    val presentSimple: String,
    val pastSimple: IrregularVerbForm,
    val pastParticiple: IrregularVerbForm,
)

data class IrregularVerbDetails(
    val id: String,
    val presentSimple: String,
    val pastSimple: IrregularVerbForm,
    val pastParticiple: IrregularVerbForm,
    val description: String,
)

data class IrregularVerbForm(
    val version1: String?, // Nullable because of "can-could-_" and "may-might-_"
    val version2: String? = null,
)