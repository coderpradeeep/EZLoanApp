package com.example.ezloan.Data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ezloan.Retrofit.RetrofitInstance
import com.example.ezloan.RoomApplication
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val todoDao = RoomApplication.todoDatabase.getTodoDao()

    var enteredText by mutableStateOf("")
    var name by mutableStateOf("")
    var page by mutableIntStateOf(0)
    var isPredictScreen by mutableStateOf(false)
    var isResultScreen by mutableStateOf(false)

    private var _snackBarState = MutableLiveData<Int>(0)
    var snackBarState : MutableLiveData<Int> = _snackBarState

    private var _predicting = MutableLiveData<Int>(0)
    var predicting : MutableLiveData<Int> = _predicting

    private var _dialogBoxState = MutableLiveData<Boolean>(false)
    var dialogBoxState : MutableLiveData<Boolean> = _dialogBoxState

    var dummy by mutableStateOf("")
    var apiRequestData by mutableStateOf(APIRequestDataType(
        2,
        0,
        0,
        800000,
        5000000,
        16,
        420,
        400000,
        1000000,
        500000,
        100000
    ))

    private var apiResponse = RetrofitInstance.api

    private val _prediction = MutableLiveData<Double?>(null)
    val prediction: LiveData<Double?> get() = _prediction

    private val _error = MutableLiveData<String>("")
    val error: LiveData<String> get() = _error



    suspend fun getPrediction(data: APIRequestDataType) {
        predicting.postValue(1)
        viewModelScope.launch() {
            try {
                Log.i("Request Data", data.toString())
                val result : String = apiResponse.predict(data)
                _prediction.value = result.toDouble()
                Log.i("Success", "${result}")

                predicting.postValue(2)
                delay(3000)
                dialogBoxState.postValue(true )
            }
            catch (e: Exception) {
                // Log other exceptions
                Log.e("Error", "API call failed: ${e.message}", e)
                _error.value = "Error: ${e.message}"
                _snackBarState.value = 1
            }
            finally {
                if(predicting.value != 2) {
                    predicting.postValue(2)
                }
            }
        }
    }

    var isChatScreen by mutableStateOf(false)
    var isUser by mutableStateOf(false)

    val chatList = todoDao.getAllChat()

    suspend fun addChat(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insertChat(ChatDataType(isUser = isUser, text = text))
            isUser = !isUser
        }
    }

    suspend fun deleteChat() {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteMyChatList()
        }
    }

    var firstChat by mutableStateOf(true)
    var prompt by mutableStateOf("")
    val proMode = todoDao.getProModeStatus()

    suspend fun addMode(mode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insertProModeStatus(ProMode(isProMode = mode))
        }
    }

    suspend fun chatAI(model: GenerativeModel) {
        viewModelScope.launch {
            try {
                val response = model.generateContent(
                    content {
                        if(firstChat) {
                            text(
                                "Context : \n" +
                                "name : $name" +
                                "no of dependents : ${apiRequestData.no_of_dependents}" +
                                "education : ${if (apiRequestData.education == 1) "graduate" else "not graduate"}" +
                                "self employed : ${if (apiRequestData.self_employed == 1) "yes" else "no"}" +
                                "income annum : ${apiRequestData.income_annum}" +
                                "loan amount : ${apiRequestData.loan_amount} rupees" +
                                "loan term : ${apiRequestData.loan_term} months" +
                                "cibil score : ${apiRequestData.cibil_score}" +
                                "residential assets value : ${apiRequestData.residential_assets_value} rupees" +
                                "commercial assets value : ${apiRequestData.commercial_assets_value} rupees" +
                                "luxury assets value : ${apiRequestData.luxury_assets_value} rupees" +
                                "bank asset value : ${apiRequestData.bank_asset_value} rupees"
                            )
                            text(".\nThis is my profile. Give me some suggestions to improve my profile to get desired amount of loan. Give brief in 50 words\n")
                        }
                        else {
                            text("$prompt ? Give brief within 50 words")
                        }
                    }
                )

                addChat(response.text.toString())
                Log.i("Success", response.text.toString())
            }
            catch (e : Error) {
                Log.i("Error", "Error")
            }
        }

        firstChat = false
    }

}
