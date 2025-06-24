package xyz.blueskyweb.reactnativedeviceattest

import android.adservices.appsetid.AppSetId
import android.content.Context
import android.util.Base64
import com.google.android.gms.appset.AppSet
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.StandardIntegrityManager.PrepareIntegrityTokenRequest
import com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityTokenProvider
import com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityTokenRequest
import org.json.JSONObject
import java.security.MessageDigest

class Integrity(
    val context: Context,
    val gcpKey: Long,
) {
    private var tokenProvider: StandardIntegrityTokenProvider? = null
    private var appSetId: String? = null

    fun warmup(cb: (e: Exception?) -> Unit) {
        if (tokenProvider != null) {
            cb(null)
            return
        }

        // Only available on Android 21+, but RN requires 24+ so no need to check
        val manager = IntegrityManagerFactory.createStandard(context)
        val request =
            PrepareIntegrityTokenRequest
                .builder()
                .apply {
                    setCloudProjectNumber(gcpKey)
                }.build()

        manager
            .prepareIntegrityToken(request)
            .apply {
                addOnSuccessListener { tokenProvider ->
                    this@Integrity.tokenProvider = tokenProvider
                    warmupAppSetId(cb)
                }
                addOnFailureListener { e ->
                    cb(e)
                }
                addOnCanceledListener {
                    cb(Exception("Integrity token request was cancelled"))
                }
            }
    }

    private fun warmupAppSetId(cb: (e: Exception?) -> Unit) {
        val client = AppSet.getClient(context)
        client.appSetIdInfo.apply {
            addOnSuccessListener {
                if (appSetId == null && it.scope == AppSetId.SCOPE_DEVELOPER) {
                    appSetId = it.id
                    cb(null)
                }
            }
            addOnFailureListener {
                cb(it)
            }
            addOnCanceledListener {
                cb(Exception("AppSetId request was cancelled"))
            }
        }
    }

    fun getToken(
        action: String,
        cb: (token: String, e: Exception?) -> Unit,
    ) {
        val tokenProvider =
            tokenProvider ?: run {
                cb("", IllegalStateException("Play Integrity API not warmed up"))
                return
            }

        val hash = createRequestHash(action)
        val request =
            StandardIntegrityTokenRequest
                .builder()
                .apply {
                    setRequestHash(hash)
                }.build()

        tokenProvider
            .request(request)
            .apply {
                addOnSuccessListener { response ->
                    cb(response.token(), null)
                }
                addOnFailureListener { e ->
                    cb("", e)
                }
                addOnCanceledListener {
                    cb("", Exception("Integrity token request was cancelled"))
                }
            }
    }

    private fun createRequestHash(action: String): String {
        val data =
            JSONObject().apply {
                put("action", action)
                put("timestamp", System.currentTimeMillis())
                put("appSetId", appSetId)
            }

        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(data.toString().toByteArray())
        return Base64.encodeToString(hashBytes, Base64.DEFAULT)
    }
}
