#!/bin/bash

# Hybrid Automation Framework - Quick Start Script
# This script provides easy commands to run different types of tests

echo "🚀 Welcome to the World-Class Hybrid UI + REST API Automation Framework!"
echo "=================================================================="

# Function to display usage
show_usage() {
    echo ""
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  ui          Run UI tests only"
    echo "  api         Run API tests only"
    echo "  hybrid      Run hybrid tests only"
    echo "  all         Run all tests"
    echo "  smoke       Run smoke tests"
    echo "  regression  Run regression tests"
    echo "  parallel    Run tests in parallel mode"
    echo "  chrome      Run tests with Chrome browser"
    echo "  firefox     Run tests with Firefox browser"
    echo "  headless    Run tests in headless mode"
    echo "  debug       Run tests with debug logging"
    echo "  clean       Clean target directory"
    echo "  help        Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 ui                    # Run UI tests"
    echo "  $0 api                   # Run API tests"
    echo "  $0 hybrid                # Run hybrid tests"
    echo "  $0 all                   # Run all tests"
    echo "  $0 smoke                 # Run smoke tests"
    echo "  $0 chrome                # Run with Chrome browser"
    echo "  $0 firefox headless      # Run Firefox tests in headless mode"
    echo "  $0 parallel              # Run tests in parallel"
    echo ""
}

# Function to clean target directory
clean_target() {
    echo "🧹 Cleaning target directory..."
    mvn clean
    echo "✅ Target directory cleaned successfully!"
}

# Function to run tests with specific parameters
run_tests() {
    local browser=${1:-chrome}
    local headless=${2:-false}
    local groups=${3:-}
    local parallel=${4:-false}
    local debug=${5:-false}
    
    echo "🔧 Test Configuration:"
    echo "   Browser: $browser"
    echo "   Headless: $headless"
    echo "   Groups: ${groups:-all}"
    echo "   Parallel: $parallel"
    echo "   Debug: $debug"
    echo ""
    
    local mvn_cmd="mvn test"
    
    # Add browser parameter
    mvn_cmd="$mvn_cmd -Dbrowser=$browser"
    
    # Add headless parameter
    mvn_cmd="$mvn_cmd -Dheadless=$headless"
    
    # Add groups if specified
    if [ ! -z "$groups" ]; then
        mvn_cmd="$mvn_cmd -Dgroups=$groups"
    fi
    
    # Add parallel execution
    if [ "$parallel" = "true" ]; then
        mvn_cmd="$mvn_cmd -Dparallel.execution=true -Dthread.count=3"
    fi
    
    # Add debug logging
    if [ "$debug" = "true" ]; then
        mvn_cmd="$mvn_cmd -Dlog.level=DEBUG"
    fi
    
    echo "🚀 Executing: $mvn_cmd"
    echo ""
    
    # Execute the command
    eval $mvn_cmd
    
    if [ $? -eq 0 ]; then
        echo ""
        echo "✅ Tests completed successfully!"
        echo "📊 Reports generated in: target/reports/"
        echo "📸 Screenshots saved in: target/screenshots/"
        echo "📝 Logs available in: target/logs/"
    else
        echo ""
        echo "❌ Tests failed! Check the logs for details."
        exit 1
    fi
}

# Function to run specific test types
run_test_type() {
    local test_type=$1
    local browser=${2:-chrome}
    local headless=${3:-false}
    
    case $test_type in
        "ui")
            echo "🎨 Running UI Tests..."
            run_tests "$browser" "$headless" "ui"
            ;;
        "api")
            echo "🔌 Running API Tests..."
            run_tests "$browser" "$headless" "api"
            ;;
        "hybrid")
            echo "🔄 Running Hybrid Tests..."
            run_tests "$browser" "$headless" "hybrid"
            ;;
        "smoke")
            echo "💨 Running Smoke Tests..."
            run_tests "$browser" "$headless" "smoke"
            ;;
        "regression")
            echo "🔄 Running Regression Tests..."
            run_tests "$browser" "$headless" "regression"
            ;;
        *)
            echo "❌ Unknown test type: $test_type"
            show_usage
            exit 1
            ;;
    esac
}

# Function to run tests in parallel
run_parallel() {
    echo "⚡ Running tests in parallel mode..."
    run_tests "chrome" "false" "" "true" "false"
}

# Function to run with specific browser
run_browser() {
    local browser=$1
    local headless=${2:-false}
    
    echo "🌐 Running tests with $browser browser..."
    run_tests "$browser" "$headless"
}

# Function to run in headless mode
run_headless() {
    local browser=${1:-chrome}
    echo "👻 Running tests in headless mode with $browser..."
    run_tests "$browser" "true"
}

# Function to run with debug logging
run_debug() {
    local browser=${1:-chrome}
    echo "🐛 Running tests with debug logging..."
    run_tests "$browser" "false" "" "false" "true"
}

# Main script logic
case "$1" in
    "ui")
        run_test_type "ui"
        ;;
    "api")
        run_test_type "api"
        ;;
    "hybrid")
        run_test_type "hybrid"
        ;;
    "all")
        echo "🌟 Running All Tests..."
        run_tests
        ;;
    "smoke")
        run_test_type "smoke"
        ;;
    "regression")
        run_test_type "regression"
        ;;
    "parallel")
        run_parallel
        ;;
    "chrome")
        run_browser "chrome"
        ;;
    "firefox")
        run_browser "firefox"
        ;;
    "edge")
        run_browser "edge"
        ;;
    "safari")
        run_browser "safari"
        ;;
    "headless")
        run_headless
        ;;
    "debug")
        run_debug
        ;;
    "clean")
        clean_target
        ;;
    "help"|"-h"|"--help"|"")
        show_usage
        ;;
    *)
        echo "❌ Unknown option: $1"
        show_usage
        exit 1
        ;;
esac

# Handle additional parameters
if [ ! -z "$2" ]; then
    case "$2" in
        "headless")
            case "$1" in
                "chrome"|"firefox"|"edge"|"safari")
                    run_headless "$1"
                    ;;
                *)
                    run_headless
                    ;;
            esac
            ;;
        "debug")
            case "$1" in
                "chrome"|"firefox"|"edge"|"safari")
                    run_debug "$1"
                    ;;
                *)
                    run_debug
                    ;;
            esac
            ;;
        *)
            echo "⚠️  Additional parameter '$2' ignored"
            ;;
    esac
fi

echo ""
echo "🎉 Framework execution completed!"
echo "📚 For more information, check the README.md file"
echo "🔗 GitHub: https://github.com/yourusername/selenium-automation-framework"
