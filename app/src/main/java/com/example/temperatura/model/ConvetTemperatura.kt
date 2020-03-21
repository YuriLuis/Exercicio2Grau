package com.example.temperatura.model

class ConvetTemperatura(){

    fun calculaTemperaturaEmGrauFahrenheit(grauInformado: Double) : Double = (grauInformado * 1.8) + 32

    fun calculaTemperaturaEmGrauCelsios(grauInformado: Double) : Double = (grauInformado - 32) / 1.8

}