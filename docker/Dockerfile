FROM java:8-jdk

RUN apt-get update
RUN apt-get -y install sudo
RUN apt-get -y install git
RUN apt-get -y install autotools-dev automake autoconf libtool g++ autopoint make cmake bison flex yasm pkg-config gtk-doc-tools libxv-dev libx11-dev libpulse-dev python-dev texinfo gettext build-essential pkg-config doxygen curl libxext-dev libxi-dev x11proto-record-dev libxrender-dev libgl1-mesa-dev libxfixes-dev libxdamage-dev libxcomposite-dev libasound2-dev libxml-simple-perl dpkg-dev debhelper build-essential devscripts fakeroot transfig gperf libdbus-glib-1-dev wget glib-networking libxtst-dev libxrandr-dev libglu1-mesa-dev ruby libegl1-mesa-dev git subversion vim-common xutils-dev chrpath libfuse-dev libgdk-pixbuf2.0-dev

RUN useradd -m docker && echo "docker:docker" | chpasswd && adduser docker sudo
RUN passwd docker -d
RUN  mkdir -p /opt/openwebrtc-0.3 && chmod a+rw /opt/openwebrtc-0.3

USER docker

RUN cd ~ && git clone https://github.com/danielwegener/cerbero.git && cd cerbero && git checkout linux-java



RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc fetch-package --full-reset --reset-rdeps openwebrtc
RUN git config --global user.email "noone@noone.com" && git config --global user.name "An innocent docker container"
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc bootstrap
RUN cd ~/cerbero && ./cerbero-uninstalled -c config/linux.cbc package -f openwebrtc
CMD /bin/bash

