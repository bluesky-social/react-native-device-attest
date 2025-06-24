import { NotImplementedError } from './NotImplemented'

export class DeviceAttestBase {
  static async generateToken(): Promise<string> {
    throw new NotImplementedError()
  }

  static async getAppSetId(): Promise<string> {
    throw new NotImplementedError()
  }
}
