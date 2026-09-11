# 🖥️ Local AI Setup — Ollama LAN Integration

This guide walks through configuring a self-hosted, local LLM with Ollama to power the ZAKA ACADEMY AI Teacher without sending data outside your local network.

---

## What You Need
- A desktop PC or laptop (Windows, macOS, or Linux) with at least 8GB to 16GB RAM.
- Android phone/tablet connected to the same Wi-Fi network as your PC.
- [Ollama](https://ollama.ai) installed on your computer.

---

## Step 1: Install and Test Ollama

Download and install Ollama from [ollama.ai](https://ollama.ai).

Open a terminal or command prompt and test running a coding or instruction-tuned model:
```bash
# Recommended lightweight models:
ollama run qwen2.5-coder:7b
# or
ollama run llama3:8b
# or for ultra-lightweight hardware:
ollama run phi3:mini
```

---

## Step 2: Configure Ollama to Listen Across Your Wi-Fi Network

By default, Ollama only listens on `127.0.0.1` (localhost), which prevents your Android phone from reaching it. You must configure Ollama to bind to `0.0.0.0`.

### On macOS
In Terminal:
```bash
launchctl setenv OLLAMA_HOST "0.0.0.0:11434"
# Then restart Ollama from your Applications menu or menu bar
```

### On Linux (systemd)
```bash
sudo systemctl edit ollama.service
```
Add the following lines:
```ini
[Service]
Environment="OLLAMA_HOST=0.0.0.0:11434"
```
Save and exit, then run:
```bash
sudo systemctl daemon-reload
sudo systemctl restart ollama
```

### On Windows
1. Open **Start Menu** and search for **Environment Variables**.
2. Click **Edit the system environment variables**.
3. Click **Environment Variables...**
4. Under User or System variables, click **New...**:
   - Variable name: `OLLAMA_HOST`
   - Variable value: `0.0.0.0`
5. Click **OK**, then exit Ollama from the system tray and relaunch it.

---

## Step 3: Find Your Host PC's Local IP Address

On Windows:
```cmd
ipconfig
```
Look for `IPv4 Address` under your Wi-Fi or Ethernet adapter (e.g., `192.168.1.105`).

On macOS or Linux:
```bash
ip route get 1.1.1.1 | awk '{print $7}'
# or
ifconfig
```

---

## Step 4: Configure in the ZAKA ACADEMY Android App

1. Launch **ZAKA ACADEMY** on your Android device.
2. Tap **Settings** in the bottom navigation bar.
3. Under **AI Teacher Engine Provider**, select **Local Ollama / LAN Model**.
4. In **Ollama Endpoint URL**, enter your PC's IP and port:
   ```
   http://192.168.1.105:11434
   ```
5. In **Model Name**, enter the model you downloaded:
   ```
   qwen2.5-coder:7b
   ```
6. Open **AI Teacher** in the app and ask any question!

---

## Step 5: Troubleshooting & Fail-Safe Protection

- **Firewall Check:** If the app cannot connect, ensure your PC's firewall allows inbound connections on TCP port `11434`.
- **Automatic Fallback:** If your PC goes to sleep or is disconnected from Wi-Fi, ZAKA ACADEMY's 10-second watchdog timer automatically falls back to the internal Offline Expert engine. You will never encounter a frozen or crashed screen.
