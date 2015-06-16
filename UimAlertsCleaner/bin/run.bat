@echo off
REM ---------------------------------------------
REM 	This script is intended to clear 
REM 	UIM Alerts when associated alert
REM 	is cleared in SOI
REM 
REM 	Input parameters:
REM			1. Alert Source ID (ex. I:341)
REM ---------------------------------------------

pushd D:\CA\actions\uimAlertCleaner

set PATH=%PATH%;D:\CA\SOI\jre-64\bin

java.exe -jar lib\uac-1.0.0-RELEASE.jar  %1