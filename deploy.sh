#!/bin/bash

# AI Document Search - Deployment Helper Script

echo "🚀 AI Document Search Deployment Helper"
echo "========================================"

# Check if git is initialized
if [ ! -d ".git" ]; then
    echo "📁 Initializing Git repository..."
    git init
    git add .
    git commit -m "Initial commit: AI Document Search application"
    echo "✅ Git repository initialized"
else
    echo "✅ Git repository already exists"
fi

# Check if .env file exists
if [ ! -f ".env" ]; then
    echo "⚠️  .env file not found!"
    echo "Please create .env file with your OpenAI API key"
    echo "Copy env.example to .env and update the values"
    exit 1
else
    echo "✅ .env file found"
fi

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "⚠️  Docker is not running!"
    echo "Please start Docker Desktop and try again"
    exit 1
else
    echo "✅ Docker is running"
fi

# Test local build
echo "🔨 Testing local build..."
docker compose up -d --build

# Wait for services to start
echo "⏳ Waiting for services to start..."
sleep 30

# Check service status
echo "📊 Checking service status..."
docker compose ps

# Test API endpoints
echo "🧪 Testing API endpoints..."
if curl -f http://localhost:8080/api/documents/health > /dev/null 2>&1; then
    echo "✅ Backend API is responding"
else
    echo "❌ Backend API is not responding"
fi

if curl -f http://localhost:3000 > /dev/null 2>&1; then
    echo "✅ Frontend is responding"
else
    echo "❌ Frontend is not responding"
fi

echo ""
echo "🎉 Local deployment test completed!"
echo ""
echo "Next steps for production deployment:"
echo "1. Push to GitHub: git push origin main"
echo "2. Deploy backend to Railway"
echo "3. Deploy frontend to Vercel"
echo "4. Set up Qdrant Cloud"
echo ""
echo "See DEPLOYMENT.md for detailed instructions"
