import { NotImplementedError } from './NotImplemented'

export class DeviceAttestBase {
  static async getDeviceCheckToken(): Promise<string> {
    throw new NotImplementedError()
  }

  static async warumpIntegrity(): Promise<string> {
    throw new NotImplementedError()
  }

  static async getIntegrityToken(): Promise<string> {
    throw new NotImplementedError()
  }
}
