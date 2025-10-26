# AI Document Search

A full-stack AI-powered document search application that allows users to upload PDF documents and ask questions about their content using OpenAI's GPT models.

## 🚀 Features

- **PDF Upload**: Upload PDF documents to create a searchable knowledge base
- **AI Chat**: Ask questions about your documents using natural language
- **Vector Search**: Powered by Qdrant vector database for semantic search
- **Real-time Processing**: Instant document processing and indexing
- **Modern UI**: Clean, responsive React frontend with Tailwind CSS

## 🏗️ Architecture

- **Frontend**: React.js with Tailwind CSS
- **Backend**: Spring Boot (Java) with REST API
- **Vector Database**: Qdrant for semantic search
- **AI Integration**: OpenAI embeddings and GPT models for question answering
- **Deployment**: Docker Compose for local development

## 🛠️ Tech Stack

### Frontend
- React 18
- Tailwind CSS
- Axios for API calls
- React Dropzone for file upload

### Backend
- Spring Boot 2.7.18
- Java 11
- LangChain4j for AI integration
- PDFBox for PDF processing
- In-memory vector storage (Qdrant-ready)

### AI Integration
- **Chat**: Gemini Pro (subscription) or OpenAI GPT-3.5-turbo
- **Embeddings**: OpenAI text-embedding-3-small
- Vector search with cosine similarity

## 🚀 Quick Start

### Prerequisites
- Docker and Docker Compose
- Gemini Pro API Key (recommended) or OpenAI API Key

### Local Development

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd ai-document-search
   ```

2. **Set up environment variables**
   ```bash
   cp env.example .env
   # Edit .env and add your API key
   # Get Gemini key: https://aistudio.google.com/apikey
   # Or get OpenAI key: https://platform.openai.com/api-keys
   ```

3. **Start the application**
   ```bash
   docker-compose up -d
   ```

4. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080
   - Qdrant: http://localhost:6333

### Quick Start Guide

1. Get API key from [Google AI Studio](https://aistudio.google.com/apikey) (Gemini) or [OpenAI Platform](https://platform.openai.com/api-keys)
2. Add to `.env` file: `GOOGLEAI_API_KEY=your_key_here` or `OPENAI_API_KEY=your_key_here`
3. Run `docker-compose up -d`
4. Open http://localhost:3000
5. Upload a PDF and start chatting!

## 📁 Project Structure

```
ai-document-search/
├── frontend/                 # React frontend
│   ├── src/
│   │   ├── components/      # React components
│   │   ├── services/        # API services
│   │   └── App.js          # Main app component
│   ├── public/             # Static files
│   └── package.json        # Frontend dependencies
├── src/                    # Spring Boot backend
│   └── main/java/com/example/aidocumentsearch/
│       ├── controller/     # REST controllers
│       ├── service/        # Business logic
│       ├── config/        # Configuration
│       └── dto/           # Data transfer objects
├── docker-compose.yml      # Multi-container setup
├── Dockerfile             # Backend container
├── pom.xml               # Maven dependencies
└── .env                  # Environment variables
```

## 🔧 Configuration

### Environment Variables

Create a `.env` file with the following variables:

```env
# OpenAI Configuration (Required)
OPENAI_API_KEY=your_openai_api_key_here

# Vector Database Configuration
VECTOR_DB=QDRANT
QDRANT_HOST=localhost
QDRANT_PORT=6333
QDRANT_COLLECTION_NAME=documents

# Application Configuration
CHUNK_SIZE=1000
CHUNK_OVERLAP=200
TOP_K_RESULTS=5
```

## 📚 API Endpoints

### Document Management
- `POST /api/documents/upload` - Upload PDF documents
- `GET /api/documents/actuator/health` - Health check

### Chat Interface
- `POST /api/chat` - Send chat message to query documents

## 🚀 Deployment

### Docker Compose (Recommended)

```bash
# Start all services
docker-compose up -d

# Stop services
docker-compose down
```

### Backend Deployment

The application can be deployed to any platform that supports Docker:
- AWS ECS, Google Cloud Run, Azure Container Instances
- DigitalOcean App Platform, Railway, Render
- Or deploy frontend separately to Vercel/Netlify

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- LangChain4j team
- Qdrant for vector database
- Spring Boot team
- React team
- Tailwind CSS team
- Apache PDFBox