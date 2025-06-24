import ExpoModulesCore
import DeviceCheck

public class ReactNativeDeviceAttestModule: Module {
  public func definition() -> ModuleDefinition {
    AsyncFunction("getDeviceCheckToken") { (token: String, promise: Promise) in
      if !DCDevice.current.isSupported {
        promise.reject("DEVICECHECK_NOT_SUPPORTED", "DeviceCheck is not supported on this device")
        return
      }
      
      DCDevice.current.generateToken { token, err in
        if let err = err {
          promise.reject("DEVICECHECK_ERROR", "DeviceCheck error: \(String(describing: err))")
          return
        }
        
        guard let token = token else {
          promise.reject("DEVICECHECK_NO_TOKEN", "No token was provided by DeviceCheck")
          return
        }
        
        promise.resolve(token)
      }
    }
  }
}
