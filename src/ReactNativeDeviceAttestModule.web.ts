import { registerWebModule, NativeModule } from 'expo';

import { ReactNativeDeviceAttestModuleEvents } from './ReactNativeDeviceAttest.types';

class ReactNativeDeviceAttestModule extends NativeModule<ReactNativeDeviceAttestModuleEvents> {
  PI = Math.PI;
  async setValueAsync(value: string): Promise<void> {
    this.emit('onChange', { value });
  }
  hello() {
    return 'Hello world! 👋';
  }
}

export default registerWebModule(ReactNativeDeviceAttestModule, 'ReactNativeDeviceAttestModule');
