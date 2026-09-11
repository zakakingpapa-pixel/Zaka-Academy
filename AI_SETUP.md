# 🧠 ZAKA ACADEMY — AI Provider Architecture & Setup Guide

ZAKA ACADEMY provides a versatile **Multi-Provider AI Architecture** designed for classroom environments, remote self-study, and offline computing.

---

## 1. Supported AI Providers

| Provider | Internet Required? | Setup Complexity | Latency | Recommended For |
|---|---|---|---|---|
| **ZAKA Offline Expert** | ❌ No (100% Offline) | None (Out of the box) | < 10ms | Classrooms, low-connectivity zones, board exam prep |
| **Google Gemini Cloud** | ✅ Yes | Free API Key | 500ms - 1.5s | Deep exploratory questions, dynamic variations |
| **Local Ollama (LAN)** | ❌ No (Local Wi-Fi) | Run Ollama on PC/Mac | 1s - 3s | Privacy-first self-hosted coding models |

---

## 2. Mode 1: ZAKA Offline Expert (Default & Recommended)

### Why it's the gold standard for students:
- **Zero latency:** Instant replies powered by indexed `FastConcept` and Class 9 RAG course tables.
- **Accurate & Safe:** Eliminates AI hallucinations by grounding explanations in standard board examination definitions.
- **Bilingual:** Fluently understands both English and Roman Urdu (e.g., *"kya hota ha"*, *"asan lafzon mein samjhao"*).
- **Zero Configuration:** Works immediately with no keys, no logins, and no cellular/Wi-Fi connection.

---

## 3. Mode 2: Google Gemini Cloud Setup

To enable Google's Gemini models for cloud-augmented explanations:
1. Obtain a Gemini API Key from [Google AI Studio](https://aistudio.google.com/app/apikey).
2. Configure your key via either method:
   - **Method A (In-App):** Navigate to **Settings** in the bottom navigation bar -> Select **Google Gemini AI (Cloud)** -> Enter your API key into the field.
   - **Method B (AI Studio Secrets):** Add `GEMINI_API_KEY=your_key_here` to the `.env` file in the project root.
3. The app connects to the `gemini-2.5-flash` model with temperature 0.7 and a strict 10-second timeout.

---

## 4. Mode 3: Local Ollama LAN Setup (Zero Cloud Dependency)

Students and teachers with a desktop computer or laptop on the local Wi-Fi can run open-source models completely locally.

### Step 1: Install & Launch Ollama
On your host machine (Windows, macOS, or Linux):
```bash
# Pull recommended model
ollama run qwen2.5-coder:7b
# or
ollama run llama3:8b
```

### Step 2: Allow LAN Connections
By default, Ollama only listens on `localhost:11434`. To allow your Android phone to connect:
- **Linux/macOS:**
  ```bash
  OLLAMA_HOST=0.0.0.0 ollama serve
  ```
- **Windows:** Set the environment variable `OLLAMA_HOST=0.0.0.0` in System Environment Settings and restart Ollama.

### Step 3: Find your Host PC's Local IP
- Run `ipconfig` (Windows) or `ifconfig` / `ip a` (macOS/Linux).
- Look for your IPv4 address (e.g., `192.168.1.105`).

### Step 4: Configure in ZAKA Academy App
1. Open the app -> **Settings**.
2. Select **Local Ollama / LAN Model**.
3. In **Ollama Endpoint URL**, enter: `http://192.168.1.105:11434`
4. In **Model Name**, enter: `qwen2.5-coder:7b` (or `llama3:8b`).
5. Save settings. Queries from the AI Teacher will now execute on your local hardware!

---

## 5. Anti-Hang Failover & Safety Guarantees

1. **10-Second Watchdog Timer:** Every network call to Gemini or Ollama is monitored by a 10s timeout wrapper.
2. **Automatic Offline Fallback:** If the network disconnects or the host PC is asleep, the engine immediately yields:
   > *"⚠️ Notice: Cloud/Local AI service dastiyab nahi hai, is liye ZAKA AI ka verified Offline Expert mode use ho raha hai."*
3. **No Dead-Ends:** The student is guaranteed to receive an answer every single time.
