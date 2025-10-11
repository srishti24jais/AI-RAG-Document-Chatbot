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
- **AI Integration**: OpenAI GPT models for question answering
- **Deployment**: Docker Compose for local development

## 🛠️ Tech Stack

### Frontend
- React 18
- Tailwind CSS
- Axios for API calls
- React Router

### Backend
- Spring Boot 2.7.18
- Java 11
- LangChain4j for AI integration
- PDFBox for PDF processing
- Qdrant client for vector operations

### Database
- Qdrant Vector Database
- OpenAI Embeddings (text-embedding-ada-002)

## 🚀 Quick Start

### Prerequisites
- Docker and Docker Compose
- OpenAI API Key

### Local Development

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd ai-document-search
   ```

2. **Set up environment variables**
   ```bash
   cp env.example .env
   # Edit .env and add your OpenAI API key
   ```

3. **Start the application**
   ```bash
   docker compose up -d
   ```

4. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080
   - Qdrant: http://localhost:6333

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
# OpenAI Configuration
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
- `GET /api/documents` - List uploaded documents
- `DELETE /api/documents/{id}` - Delete document

### Chat Interface
- `POST /api/chat` - Send chat message
- `GET /api/chat/history` - Get chat history

## 🚀 Deployment

### Vercel Deployment (Frontend)

1. **Connect to Vercel**
   - Push your code to GitHub
   - Connect your GitHub repo to Vercel
   - Set environment variables in Vercel dashboard

2. **Environment Variables for Vercel**
   ```
   REACT_APP_API_URL=https://your-backend-url.com
   ```

### Backend Deployment Options

1. **Railway** (Recommended)
   - Connect GitHub repo
   - Set environment variables
   - Deploy automatically

2. **Heroku**
   - Add Procfile for Java
   - Set environment variables
   - Deploy via Git

3. **AWS/GCP/Azure**
   - Use container services
   - Set up load balancers
   - Configure databases

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- OpenAI for GPT models
- Qdrant for vector database
- Spring Boot team
- React team
- Tailwind CSS team