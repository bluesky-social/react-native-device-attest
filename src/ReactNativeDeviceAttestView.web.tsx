import * as React from 'react';

import { ReactNativeDeviceAttestViewProps } from './ReactNativeDeviceAttest.types';

export default function ReactNativeDeviceAttestView(props: ReactNativeDeviceAttestViewProps) {
  return (
    <div>
      <iframe
        style={{ flex: 1 }}
        src={props.url}
        onLoad={() => props.onLoad({ nativeEvent: { url: props.url } })}
      />
    </div>
  );
}
