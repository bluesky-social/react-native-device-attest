import { NativeModule, requireNativeModule } from 'expo';

import { ReactNativeDeviceAttestModuleEvents } from './ReactNativeDeviceAttest.types';

declare class ReactNativeDeviceAttestModule extends NativeModule<ReactNativeDeviceAttestModuleEvents> {
  PI: number;
  hello(): string;
  setValueAsync(value: string): Promise<void>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ReactNativeDeviceAttestModule>('ReactNativeDeviceAttest');
