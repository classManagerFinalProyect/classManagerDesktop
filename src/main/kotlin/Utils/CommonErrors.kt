package Utils

class CommonErrors {
    companion object{
        const val notFoundData = "No se ha podido encontrar el elemento solicitado"
        const val notAlphanumericText = "El texto excrito debe ser alfanumérico"
        const val notValidPassword = "La contraseña no puede ser inferior a 8 caracteres ni contener caracteres especiales"
        const val notValidEmail = "El email no tiene un formato válido: email@gmail.com"
        const val notValidName = "El nombre debe ser alfanumérico y estar entre 2 y 20"
        const val notValidDescription = "La descripción no puede tener más de 40 caracteres"
        const val incompleteFields = "Debes rellenar todos los campos correctamente"
        const val notValidDate = "La fecha debe seguir el siguiente formato: dd/mm/yyyy"
        const val notValidTime = "Las horas deben seguir el siguente formato: hh:mm"
    }
}