#!/bin/bash
apt-get update && apt-get install -y wget unzip
mkdir -p /opt/android-sdk/cmdline-tools
wget https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -o cmdline-tools.zip
unzip cmdline-tools.zip -d /opt/android-sdk/cmdline-tools/
mv /opt/android-sdk/cmdline-tools/cmdline-tools /opt/android-sdk/cmdline-tools/latest

export android_home=/opt/android-sdk
export path=$path:$android_home/cmdline-tools/latest/bin:$android_home/platform-tools

yes | sdkmanager --licenses
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
