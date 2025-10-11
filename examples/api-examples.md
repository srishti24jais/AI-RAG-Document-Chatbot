# API Examples

This document provides practical examples of how to use the AI Document Search API.

## Prerequisites

- Backend server running on `http://localhost:8080`
- Valid OpenAI API key configured
- Vector database (Qdrant or Pinecone) running

## 1. Upload a PDF Document

### Using curl

```bash
curl -X POST http://localhost:8080/api/documents/upload \
  -F "file=@sample-document.pdf" \
  -H "Accept: application/json"
```

### Expected Response

```json
{
  "message": "Document uploaded and processed successfully",
  "filename": "sample-document.pdf",
  "chunksCreated": 15,
  "success": true
}
```

### Using JavaScript/Fetch

```javascript
const formData = new FormData();
formData.append('file', fileInput.files[0]);

const response = await fetch('http://localhost:8080/api/documents/upload', {
  method: 'POST',
  body: formData
});

const result = await response.json();
console.log(result);
```

## 2. Chat with Documents

### Using curl

```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What is the main topic discussed in the document?"
  }'
```

### Expected Response

```json
{
  "answer": "The main topic discussed in the document is artificial intelligence and machine learning applications in healthcare. The document covers various AI technologies, their implementation challenges, and potential benefits for medical diagnosis and treatment.",
  "sources": [
    "The document begins by introducing artificial intelligence as a transformative technology in healthcare...",
    "Machine learning algorithms are being increasingly used for medical image analysis...",
    "The implementation of AI systems in healthcare faces several challenges including data privacy..."
  ],
  "citations": [
    "The document begins by introducing artificial intelligence as a transformative technology in healthcare...",
    "Machine learning algorithms are being increasingly used for medical image analysis...",
    "The implementation of AI systems in healthcare faces several challenges including data privacy..."
  ]
}
```

### Using JavaScript/Fetch

```javascript
const response = await fetch('http://localhost:8080/api/chat', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    question: 'What are the key benefits mentioned?'
  })
});

const result = await response.json();
console.log('Answer:', result.answer);
console.log('Sources:', result.sources);
```

## 3. Health Check

### Using curl

```bash
curl http://localhost:8080/api/documents/health
```

### Expected Response

```
Vector database is available
```

### Using JavaScript/Fetch

```javascript
const response = await fetch('http://localhost:8080/api/documents/health');
const status = await response.text();
console.log('Health status:', status);
```

## 4. Error Handling Examples

### Invalid PDF File

```bash
curl -X POST http://localhost:8080/api/documents/upload \
  -F "file=@not-a-pdf.txt"
```

**Response (400 Bad Request):**
```json
{
  "message": "Invalid PDF file",
  "filename": null,
  "chunksCreated": 0,
  "success": false
}
```

### Empty Question

```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"question": ""}'
```

**Response (400 Bad Request):**
```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Question cannot be blank",
  "path": "/api/chat"
}
```

### No Documents Uploaded

```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"question": "What is this about?"}'
```

**Response:**
```json
{
  "answer": "I couldn't find any relevant information in the uploaded documents to answer your question.",
  "sources": [],
  "citations": []
}
```

## 5. Advanced Usage Examples

### Batch Document Upload

```bash
# Upload multiple documents
for file in documents/*.pdf; do
  echo "Uploading $file..."
  curl -X POST http://localhost:8080/api/documents/upload \
    -F "file=@$file"
done
```

### Conversation Flow

```javascript
// Example conversation flow
const questions = [
  "What is the document about?",
  "What are the main points?",
  "Can you summarize the key findings?",
  "What recommendations are made?"
];

for (const question of questions) {
  const response = await fetch('http://localhost:8080/api/chat', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ question })
  });
  
  const result = await response.json();
  console.log(`Q: ${question}`);
  console.log(`A: ${result.answer}\n`);
}
```

### File Upload with Progress

```javascript
const uploadWithProgress = async (file, onProgress) => {
  const formData = new FormData();
  formData.append('file', file);
  
  const xhr = new XMLHttpRequest();
  
  xhr.upload.addEventListener('progress', (e) => {
    if (e.lengthComputable) {
      const percentComplete = (e.loaded / e.total) * 100;
      onProgress(percentComplete);
    }
  });
  
  return new Promise((resolve, reject) => {
    xhr.onload = () => {
      if (xhr.status === 200) {
        resolve(JSON.parse(xhr.responseText));
      } else {
        reject(new Error('Upload failed'));
      }
    };
    
    xhr.onerror = () => reject(new Error('Upload failed'));
    xhr.open('POST', 'http://localhost:8080/api/documents/upload');
    xhr.send(formData);
  });
};

// Usage
uploadWithProgress(file, (progress) => {
  console.log(`Upload progress: ${progress.toFixed(1)}%`);
});
```

## 6. Testing with Postman

### Collection Setup

1. Create a new Postman collection called "AI Document Search"
2. Set base URL variable: `{{baseUrl}} = http://localhost:8080/api`

### Upload Document Request

- **Method**: POST
- **URL**: `{{baseUrl}}/documents/upload`
- **Body**: form-data
  - Key: `file`, Type: File, Value: [select PDF file]
- **Headers**: Accept: application/json

### Chat Request

- **Method**: POST
- **URL**: `{{baseUrl}}/chat`
- **Headers**: 
  - Content-Type: application/json
  - Accept: application/json
- **Body**: raw JSON
```json
{
  "question": "What is the main topic of the document?"
}
```

### Health Check Request

- **Method**: GET
- **URL**: `{{baseUrl}}/documents/health`

## 7. Integration Examples

### React Component Example

```jsx
import React, { useState } from 'react';
import axios from 'axios';

const DocumentChat = () => {
  const [question, setQuestion] = useState('');
  const [answer, setAnswer] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    
    try {
      const response = await axios.post('/api/chat', {
        question: question
      });
      setAnswer(response.data.answer);
    } catch (error) {
      console.error('Error:', error);
      setAnswer('Sorry, there was an error processing your question.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="Ask a question about your documents..."
        />
        <button type="submit" disabled={loading}>
          {loading ? 'Thinking...' : 'Ask'}
        </button>
      </form>
      
      {answer && (
        <div>
          <h3>Answer:</h3>
          <p>{answer}</p>
        </div>
      )}
    </div>
  );
};
```

### Python Client Example

```python
import requests
import json

class AIDocumentSearchClient:
    def __init__(self, base_url="http://localhost:8080/api"):
        self.base_url = base_url
    
    def upload_document(self, file_path):
        with open(file_path, 'rb') as file:
            files = {'file': file}
            response = requests.post(f"{self.base_url}/documents/upload", files=files)
        return response.json()
    
    def chat(self, question):
        data = {"question": question}
        response = requests.post(f"{self.base_url}/chat", json=data)
        return response.json()
    
    def health_check(self):
        response = requests.get(f"{self.base_url}/documents/health")
        return response.text

# Usage
client = AIDocumentSearchClient()

# Upload a document
result = client.upload_document("sample.pdf")
print(f"Uploaded: {result['filename']}, Chunks: {result['chunksCreated']}")

# Ask a question
response = client.chat("What is this document about?")
print(f"Answer: {response['answer']}")
```

## 8. Performance Tips

1. **Chunk Size**: Adjust chunk size based on your document types
   - Technical documents: 1000-1500 characters
   - General text: 800-1200 characters
   - Dense content: 600-1000 characters

2. **Batch Operations**: Upload multiple documents in sequence rather than parallel to avoid rate limits

3. **Question Quality**: Ask specific, focused questions for better results

4. **Document Preprocessing**: Ensure PDFs are text-based (not scanned images) for best results

## 9. Rate Limits and Best Practices

- OpenAI API has rate limits based on your plan
- Process documents during off-peak hours
- Cache embeddings to avoid regenerating them
- Use appropriate chunk sizes to balance context and performance
- Monitor API usage and costs

