# ☁️ Cloud AI Setup — Google Gemini Integration

This guide walks through configuring Google Gemini Cloud for the ZAKA ACADEMY educational app.

---

## Prerequisites
- A Google account.
- Free access to Google AI Studio ([https://aistudio.google.com/](https://aistudio.google.com/)).

---

## Step 1: Generate an API Key
1. Go to [Google AI Studio API Keys](https://aistudio.google.com/app/apikey).
2. Click **Create API Key**.
3. Choose or create a Google Cloud project and copy your generated key string.

---

## Step 2: Injecting Key into the Application

### Option A: Via AI Studio Secrets Panel (Recommended)
1. Open the **Secrets** panel in Google AI Studio.
2. Set the key `GEMINI_API_KEY` to your generated string.
3. The project's Secrets Gradle Plugin will automatically inject it into `BuildConfig.GEMINI_API_KEY` at build time without exposing secrets in Git.

### Option B: Directly Inside the App UI
1. Launch ZAKA ACADEMY on your Android device or emulator.
2. Tap **Settings** in the bottom navigation bar.
3. Under **AI Teacher Engine Provider**, select **Google Gemini AI (Cloud)**.
4. Paste your key into the **Gemini API Key** field.
5. The key is securely persisted to Android encrypted private app preferences.

---

## Step 3: Model & Parameter Details
- **Endpoint:** `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent`
- **Model:** `gemini-2.5-flash`
- **Temperature:** `0.7` (Optimal balance between factual textbook fidelity and natural pedagogical tone)
- **Token Budget:** `1200 maxOutputTokens` per turn.
- **Failover Protection:** Active. If quota limits are reached (HTTP 429), the engine automatically falls back to the internal Offline Expert.
