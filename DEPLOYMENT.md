# Deployment Guide

This guide will help you deploy your AI Document Search application to production.

## 🚀 Deployment Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Vercel        │    │   Railway       │    │   Qdrant Cloud  │
│   (Frontend)    │◄──►│   (Backend)     │◄──►│   (Vector DB)   │
│   React App     │    │   Spring Boot   │    │   Vector Store  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 📋 Prerequisites

- GitHub account
- Vercel account (free tier available)
- Railway account (free tier available)
- Qdrant Cloud account (free tier available)
- OpenAI API key

## 🎯 Step-by-Step Deployment

### 1. Prepare Your Repository

1. **Initialize Git** (if not already done):
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   ```

2. **Create GitHub Repository**:
   - Go to GitHub.com
   - Create a new repository
   - Push your code:
   ```bash
   git remote add origin https://github.com/yourusername/ai-document-search.git
   git push -u origin main
   ```

### 2. Deploy Backend to Railway

1. **Sign up for Railway**:
   - Go to https://railway.app
   - Sign up with GitHub

2. **Deploy Backend**:
   - Click "New Project"
   - Select "Deploy from GitHub repo"
   - Choose your repository
   - Railway will auto-detect it's a Java project

3. **Set Environment Variables**:
   ```
   OPENAI_API_KEY=your_openai_api_key_here
   VECTOR_DB=QDRANT
   QDRANT_HOST=your_qdrant_host
   QDRANT_PORT=6333
   QDRANT_API_KEY=your_qdrant_api_key
   QDRANT_COLLECTION_NAME=documents
   CHUNK_SIZE=1000
   CHUNK_OVERLAP=200
   TOP_K_RESULTS=5
   ```

4. **Get Backend URL**:
   - Railway will provide a URL like: `https://your-app.railway.app`

### 3. Set Up Qdrant Cloud

1. **Sign up for Qdrant Cloud**:
   - Go to https://cloud.qdrant.io
   - Create a free cluster

2. **Get Connection Details**:
   - Host URL
   - API Key
   - Collection name

3. **Update Railway Environment Variables**:
   - Use the Qdrant Cloud details

### 4. Deploy Frontend to Vercel

1. **Sign up for Vercel**:
   - Go to https://vercel.com
   - Sign up with GitHub

2. **Import Project**:
   - Click "New Project"
   - Import your GitHub repository
   - Set root directory to `frontend`

3. **Set Environment Variables**:
   ```
   REACT_APP_API_URL=https://your-backend.railway.app
   ```

4. **Deploy**:
   - Vercel will automatically build and deploy
   - You'll get a URL like: `https://your-app.vercel.app`

### 5. Configure CORS (Important!)

Update your Spring Boot backend to allow Vercel domain:

```java
@CrossOrigin(origins = {"https://your-app.vercel.app", "http://localhost:3000"})
@RestController
public class ChatController {
    // Your existing code
}
```

## 🔧 Environment Variables Summary

### Railway (Backend)
```env
OPENAI_API_KEY=sk-your-openai-key
VECTOR_DB=QDRANT
QDRANT_HOST=your-qdrant-host.qdrant.tech
QDRANT_PORT=6333
QDRANT_API_KEY=your-qdrant-api-key
QDRANT_COLLECTION_NAME=documents
CHUNK_SIZE=1000
CHUNK_OVERLAP=200
TOP_K_RESULTS=5
```

### Vercel (Frontend)
```env
REACT_APP_API_URL=https://your-backend.railway.app
```

## 🧪 Testing Your Deployment

1. **Test Backend**:
   ```bash
   curl https://your-backend.railway.app/api/health
   ```

2. **Test Frontend**:
   - Visit your Vercel URL
   - Try uploading a PDF
   - Ask questions about the document

## 💰 Cost Estimation

### Free Tier Limits:
- **Vercel**: 100GB bandwidth/month
- **Railway**: $5 credit/month
- **Qdrant Cloud**: 1GB storage, 1M vectors
- **OpenAI**: Pay per API call

### Estimated Monthly Cost:
- **Small usage**: $0-10
- **Medium usage**: $10-50
- **Heavy usage**: $50-200

## 🔍 Monitoring & Maintenance

1. **Monitor Logs**:
   - Railway: Built-in logging
   - Vercel: Function logs

2. **Set up Alerts**:
   - Monitor API usage
   - Set up error notifications

3. **Regular Updates**:
   - Keep dependencies updated
   - Monitor security patches

## 🚨 Troubleshooting

### Common Issues:

1. **CORS Errors**:
   - Check CORS configuration in backend
   - Verify frontend API URL

2. **API Key Issues**:
   - Verify environment variables are set
   - Check API key validity

3. **Database Connection**:
   - Verify Qdrant connection details
   - Check network connectivity

4. **Build Failures**:
   - Check build logs
   - Verify all dependencies

## 📞 Support

If you encounter issues:
1. Check the logs in Railway/Vercel
2. Verify environment variables
3. Test locally first
4. Check GitHub Issues for similar problems

## 🎉 Success!

Once deployed, you'll have:
- ✅ Production-ready AI Document Search
- ✅ Scalable architecture
- ✅ Professional deployment
- ✅ GitHub repository with CI/CD
- ✅ Live demo URL for portfolio

Your application will be accessible at:
- **Frontend**: https://your-app.vercel.app
- **Backend**: https://your-backend.railway.app
- **API Docs**: https://your-backend.railway.app/swagger-ui.html
