@echo off
setlocal enabledelayedexpansion

REM Hybrid Automation Framework - Quick Start Script for Windows
REM This script provides easy commands to run different types of tests

echo.
echo 🚀 Welcome to the World-Class Hybrid UI + REST API Automation Framework!
echo ==================================================================

REM Function to display usage
:show_usage
echo.
echo Usage: %0 [OPTION]
echo.
echo Options:
echo   ui          Run UI tests only
echo   api         Run API tests only
echo   hybrid      Run hybrid tests only
echo   all         Run all tests
echo   smoke       Run smoke tests
echo   regression  Run regression tests
echo   parallel    Run tests in parallel mode
echo   chrome      Run tests with Chrome browser
echo   firefox     Run tests with Firefox browser
echo   headless    Run tests in headless mode
echo   debug       Run tests with debug logging
echo   clean       Clean target directory
echo   help        Show this help message
echo.
echo Examples:
echo   %0 ui                    # Run UI tests
echo   %0 api                   # Run API tests
echo   %0 hybrid                # Run hybrid tests
echo   %0 all                   # Run all tests
echo   %0 smoke                 # Run smoke tests
echo   %0 chrome                # Run with Chrome browser
echo   %0 firefox headless      # Run Firefox tests in headless mode
echo   %0 parallel              # Run tests in parallel
echo.
goto :eof

REM Function to clean target directory
:clean_target
echo 🧹 Cleaning target directory...
call mvn clean
if %errorlevel% equ 0 (
    echo ✅ Target directory cleaned successfully!
) else (
    echo ❌ Failed to clean target directory
)
goto :eof

REM Function to run tests with specific parameters
:run_tests
set browser=%1
set headless=%2
set groups=%3
set parallel=%4
set debug=%5

if "%browser%"=="" set browser=chrome
if "%headless%"=="" set headless=false
if "%groups%"=="" set groups=
if "%parallel%"=="" set parallel=false
if "%debug%"=="" set debug=false

echo 🔧 Test Configuration:
echo    Browser: %browser%
echo    Headless: %headless%
echo    Groups: %groups%
echo    Parallel: %parallel%
echo    Debug: %debug%
echo.

set mvn_cmd=mvn test

REM Add browser parameter
set mvn_cmd=%mvn_cmd% -Dbrowser=%browser%

REM Add headless parameter
set mvn_cmd=%mvn_cmd% -Dheadless=%headless%

REM Add groups if specified
if not "%groups%"=="" (
    set mvn_cmd=%mvn_cmd% -Dgroups=%groups%
)

REM Add parallel execution
if "%parallel%"=="true" (
    set mvn_cmd=%mvn_cmd% -Dparallel.execution=true -Dthread.count=3
)

REM Add debug logging
if "%debug%"=="true" (
    set mvn_cmd=%mvn_cmd% -Dlog.level=DEBUG
)

echo 🚀 Executing: %mvn_cmd%
echo.

REM Execute the command
%mvn_cmd%

if %errorlevel% equ 0 (
    echo.
    echo ✅ Tests completed successfully!
    echo 📊 Reports generated in: target\reports\
    echo 📸 Screenshots saved in: target\screenshots\
    echo 📝 Logs available in: target\logs\
) else (
    echo.
    echo ❌ Tests failed! Check the logs for details.
    exit /b 1
)
goto :eof

REM Function to run specific test types
:run_test_type
set test_type=%1
set browser=%2
set headless=%3

if "%browser%"=="" set browser=chrome
if "%headless%"=="" set headless=false

if "%test_type%"=="ui" (
    echo 🎨 Running UI Tests...
    call :run_tests "%browser%" "%headless%" "ui"
) else if "%test_type%"=="api" (
    echo 🔌 Running API Tests...
    call :run_tests "%browser%" "%headless%" "api"
) else if "%test_type%"=="hybrid" (
    echo 🔄 Running Hybrid Tests...
    call :run_tests "%browser%" "%headless%" "hybrid"
) else if "%test_type%"=="smoke" (
    echo 💨 Running Smoke Tests...
    call :run_tests "%browser%" "%headless%" "smoke"
) else if "%test_type%"=="regression" (
    echo 🔄 Running Regression Tests...
    call :run_tests "%browser%" "%headless%" "regression"
) else (
    echo ❌ Unknown test type: %test_type%
    call :show_usage
    exit /b 1
)
goto :eof

REM Function to run tests in parallel
:run_parallel
echo ⚡ Running tests in parallel mode...
call :run_tests "chrome" "false" "" "true" "false"
goto :eof

REM Function to run with specific browser
:run_browser
set browser=%1
set headless=%2

if "%headless%"=="" set headless=false

echo 🌐 Running tests with %browser% browser...
call :run_tests "%browser%" "%headless%"
goto :eof

REM Function to run in headless mode
:run_headless
set browser=%1
if "%browser%"=="" set browser=chrome

echo 👻 Running tests in headless mode with %browser%...
call :run_tests "%browser%" "true"
goto :eof

REM Function to run with debug logging
:run_debug
set browser=%1
if "%browser%"=="" set browser=chrome

echo 🐛 Running tests with debug logging...
call :run_tests "%browser%" "false" "" "false" "true"
goto :eof

REM Main script logic
if "%1"=="ui" (
    call :run_test_type "ui" "%2" "%3"
) else if "%1"=="api" (
    call :run_test_type "api" "%2" "%3"
) else if "%1"=="hybrid" (
    call :run_test_type "hybrid" "%2" "%3"
) else if "%1"=="all" (
    echo 🌟 Running All Tests...
    call :run_tests
) else if "%1"=="smoke" (
    call :run_test_type "smoke" "%2" "%3"
) else if "%1"=="regression" (
    call :run_test_type "regression" "%2" "%3"
) else if "%1"=="parallel" (
    call :run_parallel
) else if "%1"=="chrome" (
    call :run_browser "chrome" "%2"
) else if "%1"=="firefox" (
    call :run_browser "firefox" "%2"
) else if "%1"=="edge" (
    call :run_browser "edge" "%2"
) else if "%1"=="safari" (
    call :run_browser "safari" "%2"
) else if "%1"=="headless" (
    call :run_headless
) else if "%1"=="debug" (
    call :run_debug
) else if "%1"=="clean" (
    call :clean_target
) else if "%1"=="help" (
    call :show_usage
) else if "%1"=="" (
    call :show_usage
) else (
    echo ❌ Unknown option: %1
    call :show_usage
    exit /b 1
)

REM Handle additional parameters
if not "%2"=="" (
    if "%2"=="headless" (
        if "%1"=="chrome" (
            call :run_headless "chrome"
        ) else if "%1"=="firefox" (
            call :run_headless "firefox"
        ) else if "%1"=="edge" (
            call :run_headless "edge"
        ) else if "%1"=="safari" (
            call :run_headless "safari"
        ) else (
            call :run_headless
        )
    ) else if "%2"=="debug" (
        if "%1"=="chrome" (
            call :run_debug "chrome"
        ) else if "%1"=="firefox" (
            call :run_debug "firefox"
        ) else if "%1"=="edge" (
            call :run_debug "edge"
        ) else if "%1"=="safari" (
            call :run_debug "safari"
        ) else (
            call :run_debug
        )
    ) else (
        echo ⚠️  Additional parameter '%2' ignored
    )
)

echo.
echo 🎉 Framework execution completed!
echo 📚 For more information, check the README.md file
echo 🔗 GitHub: https://github.com/yourusername/selenium-automation-framework

pause
