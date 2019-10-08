cd C:\AlessandroBAM\TimeSheet Development\timesheet\timesheet\

REM mvn package

xcopy /s "C:\AlessandroBAM\TimeSheet Development\timesheet\timesheet\target\TimeSheetFX-jar-with-dependencies.jar"   "C:\AlessandroBAM\TimeSheet 2017\TimeSheetFX.jar" /Y
xcopy /s "C:\AlessandroBAM\TimeSheet Development\timesheet\timesheet\target\TimeSheetFX-jar-with-dependencies.jar"   "C:\AlessandroBAM\TimeSheet 2014\TimeSheetFX.jar" /Y