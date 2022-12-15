@echo off
setlocal enabledelayedexpansion
IF EXIST "%1" (
cd /d  %1
) ELSE (
mkdir %1
cd /d %1
)
IF EXIST "tetr" (
    echo directory exists
del dev.txt
rmdir /s /q tetr
mkdir tetr
) ELSE (
    echo Not EXIST
mkdir tetr
)
echo > dev.txt
ipconfig > dev.txt
copy dev.txt tetr
cd tetr
rename dev.txt dev1.txt
set/p =count of lines: <nul
find /V /C""<dev1.txt
cd ..
endlocal