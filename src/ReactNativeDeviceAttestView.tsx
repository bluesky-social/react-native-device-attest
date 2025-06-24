import { requireNativeView } from 'expo'
import * as React from 'react'

import { ReactNativeDeviceAttestViewProps } from './ReactNativeDeviceAttest.types'

const NativeView: React.ComponentType<ReactNativeDeviceAttestViewProps> =
  requireNativeView('ReactNativeDeviceAttest')

export default function ReactNativeDeviceAttestView(
  props: ReactNativeDeviceAttestViewProps
) {
  return <NativeView {...props} />
}
