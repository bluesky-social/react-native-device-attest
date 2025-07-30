package xyz.blueskyweb.reactnativedeviceattest

import expo.modules.kotlin.Promise
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.kotlin.records.Field
import expo.modules.kotlin.records.Record

class ReactNativeDeviceAttestModule : Module() {
  private var integrity: Integrity? = null

  override fun definition() =
    ModuleDefinition {
      Name("ReactNativeDeviceAttest")

      AsyncFunction("warmupIntegrity") { gcpKey: Long, promise: Promise ->
        val reactContext =
          appContext.reactContext ?: run {
            promise.reject("INTEGRITY_NO_CONTEXT", "No React context", null)
            return@AsyncFunction
          }

        val integrity = Integrity(reactContext, gcpKey)
        this@ReactNativeDeviceAttestModule.integrity = integrity
        integrity.warmup { e ->
          if (e != null) {
            promise.reject("INTEGRITY_WARMUP_FAILED", "Failed to warmup Play Integrity API", e)
          } else {
            promise.resolve(null)
          }
        }
      }

      AsyncFunction("getIntegrityToken") { action: String, promise: Promise ->
        val integrity =
          integrity ?: run {
            promise.reject("INTEGRITY_NOT_WARMED_UP", "Play Integrity API not warmed up", null)
            return@AsyncFunction
          }

        integrity.getToken(action) { token, payload, e ->
          if (e != null) {
            promise.reject("INTEGRITY_GET_TOKEN_FAILED", "Failed to get Play Integrity token", e)
          } else {
            val result = IntegrityTokenResult()
            result.token = token
            result.payload = payload
            promise.resolve(result)
          }
        }
      }
    }
}

class IntegrityTokenResult : Record {
  @Field
  var token: String = ""

  @Field
  var payload: String = ""
}
