package Screens.Class

import Screens.Class.Components.MainBody.ContentState
import androidx.compose.runtime.*
import data.api.ApiServicePractice
import data.remote.Class
import data.remote.Practice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ViewModelClass {
    companion object{
        private var errorMessage: String by mutableStateOf ("")

        var selectedClass : Class = data.remote.Class("","","", arrayListOf(), arrayListOf(),"")
        var currentPractices : MutableList<Practice> = arrayListOf()

        fun getCurrentPractices(
            composableScope: CoroutineScope,
            onFinished: () -> Unit
        ) {
            currentPractices.clear()
            var countTmp = 0


            if(selectedClass.idPractices.size == 0) onFinished()
            selectedClass.idPractices.forEach{
                composableScope.launch {
                    val apiService = ApiServicePractice.getInstance()

                    try {
                        val result = apiService.getPracticeById(it)
                        if (result.isSuccessful) {
                            currentPractices.add(result.body()!!)

                        }
                        countTmp++
                        if(countTmp == selectedClass.idPractices.size) {
                            onFinished()
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }
        }

        //Content State
        private val _contentState: MutableState<ContentState> = mutableStateOf(value = ContentState.PRACTICES)
        val contentState: State<ContentState> = _contentState


        fun updateContentState(newValue: ContentState) {
            _contentState.value = newValue
        }

    }
}