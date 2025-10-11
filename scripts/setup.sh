#!/bin/bash

# AI Document Search Setup Script
echo "🚀 Setting up AI Document Search (RAG Chatbot) - Java Version"

# Check prerequisites
echo "📋 Checking prerequisites..."

# Check Java
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java version $JAVA_VERSION is too old. Please install Java 17 or higher."
    exit 1
fi
echo "✅ Java $JAVA_VERSION found"

# Check Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi
echo "✅ Maven found"

# Check Node.js
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js 18 or higher."
    exit 1
fi
echo "✅ Node.js found"

# Check Docker (optional)
if command -v docker &> /dev/null; then
    echo "✅ Docker found"
else
    echo "⚠️  Docker not found (optional for containerized deployment)"
fi

echo ""
echo "🔧 Setting up environment..."

# Create .env file if it doesn't exist
if [ ! -f .env ]; then
    echo "📝 Creating .env file from template..."
    cp env.example .env
    echo "⚠️  Please edit .env file with your API keys before running the application"
else
    echo "✅ .env file already exists"
fi

# Install frontend dependencies
echo "📦 Installing frontend dependencies..."
cd frontend
if [ ! -d "node_modules" ]; then
    npm install
    echo "✅ Frontend dependencies installed"
else
    echo "✅ Frontend dependencies already installed"
fi
cd ..

# Build backend
echo "🔨 Building backend..."
mvn clean package -DskipTests
echo "✅ Backend built successfully"

echo ""
echo "🎉 Setup complete!"
echo ""
echo "Next steps:"
echo "1. Edit .env file with your OpenAI API key"
echo "2. Choose your vector database (Qdrant or Pinecone)"
echo "3. Run the application:"
echo "   - With Docker: docker-compose up -d"
echo "   - Manually: java -jar target/ai-document-search-1.0.0.jar"
echo ""
echo "📚 Documentation: README.md"
echo "🔗 API Examples: examples/api-examples.md"

