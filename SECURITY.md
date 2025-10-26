# Security Notice

## 🔒 API Key Protection

This repository is configured to **never** expose API keys publicly.

### What's Protected

✅ **`.env`** - Contains your real API keys (NOT in repository)  
✅ **`*.env`** - Any environment files  
✅ **`*.key`** - Any key files  
✅ **`*.secret`** - Any secret files  

### What's Safe

✅ **`env.example`** - Template file with NO real keys  
✅ All config files use placeholders  

### How to Use

1. **Clone the repository**
2. **Copy the example file**
   ```bash
   cp env.example .env
   ```
3. **Add your API keys to `.env`** (never commit this!)
4. **Your keys stay local**

## ⚠️ Important

- **NEVER** commit `.env` file
- **NEVER** share your API keys
- The repository is public-safe

## If You Find Exposed Keys

If you accidentally commit an API key:

1. **Rotate the key immediately** (create a new one)
2. **Remove from Git history**
3. **Check your usage** for unauthorized access

## Setup Instructions

See `README.md` for complete setup guide.

