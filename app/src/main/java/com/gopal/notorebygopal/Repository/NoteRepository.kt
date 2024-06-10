package com.gopal.notorebygopal.Repository

import androidx.lifecycle.MutableLiveData
import com.gopal.notorebygopal.API.NoteAPI
import com.gopal.notorebygopal.models.NoteRequest
import com.gopal.notorebygopal.models.NoteResponse
import com.gopal.notorebygopal.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteAPI: NoteAPI) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData

    suspend fun createNote(noteRequest: NoteRequest) {
        val response = noteAPI.createNote(noteRequest)
        handleResponse(response, "Note Created")
    }

    suspend fun getNotes() {
        val response = noteAPI.getNotes()
        if (response.isSuccessful && response.body() != null) {
            _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _notesLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun updateNote(id: String, noteRequest: NoteRequest) {
        val response = noteAPI.updateNote(id, noteRequest)
        handleResponse(response, "Note Updated")
    }

    suspend fun deleteNote(noteId: String) {
        val response = noteAPI.deleteNote(noteId)
        handleResponse(response, "Note Deleted")
    }

    private fun handleResponse(response: Response<NoteResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(Pair(true, message)))
        } else {
            _statusLiveData.postValue(NetworkResult.Success(Pair(false, "Something went wrong")))
        }
    }
}