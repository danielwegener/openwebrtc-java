# openwebrtc-java


> This repo aims to provide java bindings for openwebrtc on linux. openwebrtc does currently only support JNI bindings for the android plattform. It is not here to stay but rather early experimentations that may flow back to the openwebrtc release process. Additionally this repo contains some java tests that show how the integration works.

## How to build openwebrtc java bindings for linux

This guide refers to the [Building OpenWebRTC guide](https://github.com/EricssonResearch/openwebrtc/wiki/Building-OpenWebRTC). Follow this guide but use the following cerbero git repo/branch:

    git clone https://github.com/danielwegener/cerbero.git
    git checkout linux-java


Afterwards you should have `/opt/openwebrtc-0.3/lib` that contains the folowing files:

* `libopenwebrtc.so`
* `libopenwebrtc_jni.so`
* `libopenwebrtc_bridge.so`
* `libopenwebrtc_bridge_jni.so`

Additionally you should have the jar files `openwebrtc-0.3.0.jar` and `openwebrtc_bridge-0.3.0.jar` in the (TBD/retest) directory.

## Run the tests

Just run `mvn clean install` on the parent directory of this repo. It will install the bundled openwebrtc-java jar files into your local repository and run the tests.

If you want to run the tests from the IDE, add the following JVM argument to the test runner:

    -Djava.library.path=/opt/openwebrtc-0.3/lib

## Background

The normal openwebrtc build only creates jar bindings for the android platform release.

This build methods enables the openwebrtc build to produce jni bindings and jar files. The build tool _cerbero_ can somewhat be pushed to create these artifacts. However, I got there through try and error and maybe someone who knows what he is doing should take a look:

* https://github.com/danielwegener/cerbero/commit/45a02ae3c077234b5663092befcb32f36471ba8f
* https://github.com/danielwegener/openwebrtc/commit/7b7a38836909e165d9c5f127baf0a2f29f99f826

Eventually I'd prefer that openwebrtc build can create a java-binding jar and platform specific native jars with all required native library dependencies bundled (depending on the current build target).
