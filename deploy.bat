@echo off
echo 🚀 AI Document Search - Deployment Helper
echo ========================================

REM Check if git is initialized
if not exist ".git" (
    echo 📁 Initializing Git repository...
    git init
    git add .
    git commit -m "Initial commit: AI Document Search application"
    echo ✅ Git repository initialized
) else (
    echo ✅ Git repository already exists
)

REM Check if .env file exists
if not exist ".env" (
    echo ⚠️  .env file not found!
    echo Please create .env file with your OpenAI API key
    echo Copy env.example to .env and update the values
    pause
    exit /b 1
) else (
    echo ✅ .env file found
)

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  Docker is not running!
    echo Please start Docker Desktop and try again
    pause
    exit /b 1
) else (
    echo ✅ Docker is running
)

REM Test local build
echo 🔨 Testing local build...
docker compose up -d --build

REM Wait for services to start
echo ⏳ Waiting for services to start...
timeout /t 30 /nobreak >nul

REM Check service status
echo 📊 Checking service status...
docker compose ps

REM Test API endpoints
echo 🧪 Testing API endpoints...
curl -f http://localhost:8080/api/documents/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Backend API is responding
) else (
    echo ❌ Backend API is not responding
)

curl -f http://localhost:3000 >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Frontend is responding
) else (
    echo ❌ Frontend is not responding
)

echo.
echo 🎉 Local deployment test completed!
echo.
echo Next steps for production deployment:
echo 1. Push to GitHub: git push origin main
echo 2. Deploy backend to Railway
echo 3. Deploy frontend to Vercel
echo 4. Set up Qdrant Cloud
echo.
echo See DEPLOYMENT.md for detailed instructions
pause
