FROM java:8-jdk

RUN apt-get update
RUN apt-get -y install sudo
RUN apt-get -y install git
RUN apt-get -y install autotools-dev automake autoconf libtool g++ autopoint make cmake bison flex yasm pkg-config gtk-doc-tools libxv-dev libx11-dev libpulse-dev python-dev texinfo gettext build-essential pkg-config doxygen curl libxext-dev libxi-dev x11proto-record-dev libxrender-dev libgl1-mesa-dev libxfixes-dev libxdamage-dev libxcomposite-dev libasound2-dev libxml-simple-perl dpkg-dev debhelper build-essential devscripts fakeroot transfig gperf libdbus-glib-1-dev wget glib-networking libxtst-dev libxrandr-dev libglu1-mesa-dev ruby libegl1-mesa-dev git subversion vim-common xutils-dev chrpath libfuse-dev libgdk-pixbuf2.0-dev

RUN useradd -m docker && echo "docker:docker" | chpasswd && adduser docker sudo
RUN passwd docker -d
RUN  mkdir -p /opt/openwebrtc-0.3 && chmod a+rw /opt/openwebrtc-0.3

USER docker

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN cd ~ && git clone https://github.com/danielwegener/cerbero.git && cd cerbero && git checkout linux-java
RUN git config --global user.email "noone@noone.com" && git config --global user.name "An innocent docker container"


RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc fetch-package --full-reset --reset-rdeps openwebrtc
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc bootstrap

# Building individual dependencies. Could be build implicitly, but stacking images allow us to continue a failed build
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build openssl
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build libffi
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build glib
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build libsrtp
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gtk-doc-lite
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gobject-introspection
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gstreamer-1.0
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build libnice
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gstreamer-1.0-static
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gst-plugins-base-1.0-static
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build orc
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build libpng
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gst-plugins-base-1.0
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build bzip2
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build libjpeg-turbo
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build libvpx
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gst-plugins-good-1.0
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gst-plugins-bad-1.0
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build openh264
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build opus
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gst-plugins-base-1.0-static
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gst-plugins-good-1.0-static
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gst-plugins-bad-1.0-static
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build libnice-static
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gettext
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build libusrsctp
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build openwebrtc-gst-plugins
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build intltool
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gnome-js-common
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build icu
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build json-glib
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build javascriptcoregtk
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build seed
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build libxml2
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build libsoup
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build pygobject
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build gst-python-1.0
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build openwebrtc
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc build openwebrtc-gst-plugins-static

# Building the debian packages
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc package -f openwebrtc
CMD /bin/bash

