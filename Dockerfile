FROM ubuntu:16.04

RUN apt-get update && apt-get install -y \
  build-essential \
  openjdk-8-jdk \
  maven \
  curl \
  wget

	
#====================================
# Install latest nodejs, npm, appium
#====================================
RUN curl -sL https://deb.nodesource.com/setup_7.x | bash -
RUN apt-get install nodejs -y
ENV APPIUM_VERSION 1.6.3

#create user
RUN useradd -m -s /bin/bash automator
USER automator


# Main Android SDK in user dir
RUN wget -qO- "http://dl.google.com/android/android-sdk_r23.0.2-linux.tgz" | tar -zxv -C /home/automator
RUN echo y | /home/automator/android-sdk-linux/tools/android update sdk --no-ui --all --filter platform-tools,build-tools-19.1.0,system-image,android-19,extra-android-support --force
ENV ANDROID_HOME /home/automator/android-sdk-linux
ENV ANDROID_SDK_HOME /home/automator/.android
RUN echo no | /home/automator/android-sdk-linux/tools/android create avd --force -n ANDROID -t android-19 --abi default/x86 --skin "HVGA"

RUN mkdir /home/automator/appium
ENV HOME /home/automator/appium
RUN cd /home/automator/appium && npm install appium@$APPIUM_VERSION

CMD (/home/automator/android-sdk-linux/tools/emulator -verbose -avd ANDROID -no-skin -no-audio -no-window&) && sleep 30 && /home/automator/appium/node_modules/appium/bin/appium.js --device-ready-timeout 180


USER root

RUN mkdir app
WORKDIR app

COPY src force-automation/src

#CMD /bin/bash  

COPY pom.xml force-automation/

COPY testng.xml force-automation/



#mvn compile

#mvn test 





	

