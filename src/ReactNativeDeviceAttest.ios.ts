import { requireNativeModule } from 'expo'
import { DeviceAttestBase } from './DeviceAttestBase'

const nativeModule = requireNativeModule('ReactNativeDeviceAttest')

export default class ReactNativeDeviceAtteest extends DeviceAttestBase {
  static async generateToken(): Promise<string> {
    return nativeModule.generateToken()
  }
}
