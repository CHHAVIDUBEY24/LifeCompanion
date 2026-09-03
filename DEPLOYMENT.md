# 🚀 LifeCompanion Deployment Guide

This guide covers the quickest ways to deploy **LifeCompanion** to the cloud or via Docker.

---

## 🌟 Option 1: Free Cloud Deployment on Render (Recommended, ~2 minutes)

**Render** offers free cloud hosting for Docker/Web services with free HTTPS SSL.

### Steps:
1. Push this repository to your **GitHub** account:
   ```bash
   git remote add origin https://github.com/<YOUR_USERNAME>/<YOUR_REPO_NAME>.git
   git branch -M main
   git push -u origin main
   ```
2. Go to **[render.com](https://render.com)** and sign in with GitHub.
3. Click **New +** $\rightarrow$ **Web Service**.
4. Select your **life-companion** repository.
5. Render will automatically detect the `Dockerfile` or `render.yaml`.
   - **Environment**: `Docker`
   - **Plan**: `Free`
6. (Optional) Under **Environment Variables**, add:
   - `GEMINI_API_KEY`: *(Your Gemini API key from Google AI Studio)*
   - `PORT`: `10000`
7. Click **Create Web Service**. Render will build and deploy the container, providing a live URL like `https://life-companion-xxxx.onrender.com`.

---

## 🚂 Option 2: 1-Click Deployment on Railway / Fly.io

### Railway
1. Go to **[railway.app](https://railway.app)**.
2. Click **New Project** $\rightarrow$ **Deploy from GitHub repo**.
3. Select your `life-companion` repository.
4. Railway automatically detects `Dockerfile` and deploys it immediately with an auto-generated public domain.

### Fly.io
1. Install Fly CLI: `powershell -Command "iwr https://fly.io/install.ps1 -useb | iex"`
2. Run `fly launch` in the project directory.
3. Run `fly deploy`.

---

## 🐳 Option 3: Local or VPS Docker Deployment

You can run the entire containerized application locally or on any Linux/Windows VPS with Docker.

### Run with Docker Compose:
```bash
docker compose up --build -d
```
The application will be live at `http://localhost:8080`.

### View Logs:
```bash
docker compose logs -f
```

### Stop:
```bash
docker compose down
```

---

## ☕ Option 4: Direct JAR Execution on any VPS / Server

1. Build the production JAR:
   ```bash
   mvn clean package -DskipTests
   ```
2. Run the JAR:
   ```bash
   java -jar target/life-companion-0.0.1-SNAPSHOT.jar --server.port=8080
   ```
