import { requireNativeModule } from 'expo'
import { DeviceAttestBase } from './DeviceAttestBase'

const nativeModule = requireNativeModule('ReactNativeDeviceAttest')

export default class ReactNativeDeviceAttest extends DeviceAttestBase {
  static async getAppSetId(): Promise<string> {
    return nativeModule.getAppSetId()
  }
}
