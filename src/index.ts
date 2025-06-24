// Reexport the native module. On web, it will be resolved to ReactNativeDeviceAttestModule.web.ts
// and on native platforms to ReactNativeDeviceAttestModule.ts
export { default } from './ReactNativeDeviceAttestModule'
export { default as ReactNativeDeviceAttestView } from './ReactNativeDeviceAttestView'
export * from './ReactNativeDeviceAttest.types'
