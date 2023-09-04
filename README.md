---
title: Mixed Content Mode
description: Configures Android WebView mixed content mode behavior.
---
<!---
# license: MIT License
#
# Copyright 2023 Carlos Bello A.K.A: CarlosBet
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE.
-->
# cordova-plugin-Mixed-Content-Mode

> At this moment, this plugin is intended only for Android platforms. This plugin permits configuring Android WebView behavior when a secure origin attempts to load a resource from an insecure origin.

## Description

In Android and Apache Cordova apps targeting Build.VERSION_CODES._LOLLIPOP (SDK level 21)_ default not allowed use requests to non-https destinations if you use https schema for your app. The preferred and most secure mode of operation for WebView is never to allow mixed content and always allow mixed content is strongly discouraged. However, there are plenty of legit use cases out there where being more permissive may be necessary.

There are three possible configuration modes:
Never allow, Always allow, or Compatibility mode (default mode selected by this plugin).

### Never Allow

In this mode, the WebView will not allow a secure origin to load content from an insecure origin. This is the preferred and most secure mode of operation for the WebView and apps are strongly advised to use this mode.

### Always Allow

In this mode, the WebView will allow a secure origin to load content from any other origin, even if that origin is insecure. This is the least secure mode of operation for the WebView, and where possible apps should not set this mode.

### Compatibility Mode

In this mode, the WebView will attempt to be compatible with the approach of a modern web browser with regard to mixed content. Some insecure content may be allowed to be loaded by a secure origin and other types of content will be blocked. The types of content are allowed or blocked may change from release to release and are not explicitly defined. This mode is intended to be used by apps that are not in control of the content that they render but desire to operate in a reasonably secure environment. This is the default behavior when installing this pluging and not setting another mode.

## Installation

Can install via repo URL directly

    cordova plugin add https://github.com/carlosbet/cordova-plugin-mixed-content-mode.git

## Usage

### Through config.xml
-----------

__MixedContentMode__ (String, defaults "CompatibilityMode"). The mixed content mode to use. One of "NeverAllow", "AlwaysAllow" or "CompatibilityMode"

        <preference name="MixedContentMode" value="CompatibilityMode" />

### Through JavaScript
-----------

This plugin defines a global `MixedContentMode` object.

Although the object is in the global scope, it is not available to applications until after the `deviceready` event fires.

```
document.addEventListener("deviceready", onDeviceReady, false);
function onDeviceReady() {
    MixedContentMode.AlwaysAllow();
    //MixedContentMode.NeverAllow();
    //MixedContentMode.CompatibilityMode();
}
```

## Important notes

From Android 9 PIE (SDK Level 27) devices, clear text communication is [disabled by default](https://developer.android.com/training/articles/security-config#CleartextTrafficPermitted). To allow clear text communication, yet should set the `android:usesCleartextTraffic` attribute on your application _config.xml_
file:

```
<platform name="android">
  <edit-config file="app/src/main/AndroidManifest.xml" mode="merge" target="/manifest/application">
      <application android:usesCleartextTraffic="true" />
  </edit-config>
</platform>
```

Also, it´s necessary to add the XML namespace for Android in the widget tag, if not defined previously:

```
<widget id="you-app-id" version="1.2.3"
xmlns="http://www.w3.org/ns/widgets" 
xmlns:cdv="http://cordova.apache.org/ns/1.0"
xmlns:android="http://schemas.android.com/apk/res/android">
```

Finally, remember to adjust your [_*Content Security Policy*_](https://cordova.apache.org/docs/en/11.x/guide/appdev/allowlist/#content-security-policy-csp), in case you use that.
