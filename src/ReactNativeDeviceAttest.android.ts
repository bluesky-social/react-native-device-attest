import { requireNativeModule } from 'expo'
import { DeviceAttestBase } from './DeviceAttestBase'

const nativeModule = requireNativeModule('ReactNativeDeviceAttest')

export default class ReactNativeDeviceAttest implements DeviceAttestBase {
  static async warmupIntegrity(gcpProjectId: number): Promise<string> {
    return nativeModule.warmupIntegrity(gcpProjectId)
  }

  static async getIntegrityToken(
    action: string
  ): Promise<{ token: string; payload: string }> {
    return nativeModule.getIntegrityToken(action)
  }
}
