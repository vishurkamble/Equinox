#!/bin/bash

# script to write server connection settings file. Example usage:
# sh createConnectionSettingsFile.sh connection.set 1235 1234 1236 2222

# set inputs
fileName=$1
dataServerPort=$2
exchangeServerPort=$3
analysisServerPort=$4
fileServerPort=$5

# write file header
echo "# Server connection settings file generated by bootstrap script" >> $fileName
echo "# This file contains connection settings to Equinox Digital Twin servers." >> $fileName
echo "# Equinox client application downloads and reads this file in order to establish connection to servers." >> $fileName
echo "# Following formatting convention is used: propertyName=propertyValue" >> $fileName
echo >> $fileName

# write connection settings
echo "hostname=$(curl http://checkip.amazonaws.com -s)" >> $fileName
echo "dataServerPort=$2" >> $fileName
echo "exchangeServerPort=$3" >> $fileName
echo "analysisServerPort=$4" >> $fileName
echo "fileServerPort=$5" >> $fileName