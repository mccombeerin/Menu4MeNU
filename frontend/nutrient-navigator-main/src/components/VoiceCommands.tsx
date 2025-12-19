import { useEffect, useState, useRef } from "react";

type VoiceCommandsProps = {
  toggleDarkMode: () => void;
};

const VoiceCommands = ({ toggleDarkMode }: VoiceCommandsProps) => {
  const [listening, setListening] = useState(false);
  const recognitionRef = useRef<any>(null);

  useEffect(() => {
    const SpeechRecognition =
      (window as any).SpeechRecognition || (window as any).webkitSpeechRecognition;

    if (!SpeechRecognition) {
      console.warn("SpeechRecognition API not supported in this browser.");
      return;
    }

    const recognition = new SpeechRecognition();
    recognition.continuous = true;
    recognition.interimResults = false;
    recognition.lang = "en-US";

    recognition.onresult = (event: any) => {
      const transcript = event.results[event.results.length - 1][0].transcript
        .trim()
        .toLowerCase();

      if (transcript.includes("dark mode") || transcript.includes("light mode")) {
        toggleDarkMode();
      }

      if (transcript.includes("read page")) {
        const ttsContent = document.getElementById("tts-content")?.innerText || "";
        if (ttsContent) {
          const utterance = new SpeechSynthesisUtterance(ttsContent);
          window.speechSynthesis.speak(utterance);
        }
      }

      if (transcript.includes("stop reading") || transcript.includes("stop")) {
        window.speechSynthesis.cancel();
      }

      if (transcript.includes("scroll down")) {
        window.scrollBy({ top: 500, behavior: "smooth" });
      }
      if (transcript.includes("scroll up")) {
        window.scrollBy({ top: -500, behavior: "smooth" });
      }
    };

    recognition.onerror = (event: any) => {
      console.error("Speech recognition error:", event);
    };

    recognitionRef.current = recognition;

    if (listening) recognition.start();
    else recognition.stop();

    return () => {
      recognition.stop();
      window.speechSynthesis.cancel();
    };
  }, [listening, toggleDarkMode]);

  return (
    <button
    onClick={() => setListening(!listening)}
    className={`
      fixed bottom-6 right-6 z-50 px-4 py-2 rounded-full
      shadow-lg transform transition-transform duration-200 hover:scale-105
      ${listening
        ? "bg-[hsl(var(--destructive))] text-[hsl(var(--destructive-foreground))]"
        : "bg-[hsl(var(--primary))] text-[hsl(var(--primary-foreground))]"
      }
    `}
  >
    {listening ? "Stop Listening" : "Start Listening"}
  </button>
  );
};

export default VoiceCommands;
