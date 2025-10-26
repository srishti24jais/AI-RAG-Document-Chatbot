@echo off
echo 🚀 Setting up AI Document Search (RAG Chatbot) - Java Version

echo 📋 Checking prerequisites...

REM Check Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed. Please install Java 17 or higher.
    pause
    exit /b 1
)
echo ✅ Java found

REM Check Maven
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven is not installed. Please install Maven 3.6 or higher.
    pause
    exit /b 1
)
echo ✅ Maven found

REM Check Node.js
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Node.js is not installed. Please install Node.js 18 or higher.
    pause
    exit /b 1
)
echo ✅ Node.js found

REM Check Docker (optional)
docker --version >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Docker found
) else (
    echo ⚠️  Docker not found (optional for containerized deployment)
)

echo.
echo 🔧 Setting up environment...

REM Create .env file if it doesn't exist
if not exist .env (
    echo 📝 Creating .env file from template...
    copy env.example .env
    echo ⚠️  Please edit .env file with your API keys before running the application
) else (
    echo ✅ .env file already exists
)

REM Install frontend dependencies
echo 📦 Installing frontend dependencies...
cd frontend
if not exist node_modules (
    call npm install
    echo ✅ Frontend dependencies installed
) else (
    echo ✅ Frontend dependencies already installed
)
cd ..

REM Build backend
echo 🔨 Building backend...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo ❌ Backend build failed
    pause
    exit /b 1
)
echo ✅ Backend built successfully

echo.
echo 🎉 Setup complete!
echo.
echo Next steps:
echo 1. Edit .env file with your OpenAI API key
echo 2. Choose your vector database (Qdrant or Pinecone)
echo 3. Run the application:
echo    - With Docker: docker-compose up -d
echo    - Manually: java -jar target\ai-document-search-1.0.0.jar
echo.
echo 📚 Documentation: README.md
echo 🔗 API Examples: examples\api-examples.md

pause


