import { NotImplementedError } from './NotImplemented'

export class DeviceAttestBase {
  static async getDeviceCheckToken(): Promise<string> {
    throw new NotImplementedError()
  }

  static async warmupIntegrity(_gcpProjectId: number): Promise<string> {
    throw new NotImplementedError()
  }

  static async getIntegrityToken(
    _action: string
  ): Promise<{ token: string; payload: string }> {
    throw new NotImplementedError()
  }
}
