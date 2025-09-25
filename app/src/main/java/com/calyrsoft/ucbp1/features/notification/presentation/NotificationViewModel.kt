import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import com.calyrsoft.ucbp1.features.notification.domain.usecase.GetTokenUseCase

class NotificationViewModel(
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    fun loadToken(onTokenReady: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val result = getTokenUseCase()
                Log.d("FIREBASE", "Token obtenido: $result")
                onTokenReady(result)
            } catch (e: Exception) {
                Log.e("FIREBASE", "Error al obtener token", e)
            }
        }
    }
}
