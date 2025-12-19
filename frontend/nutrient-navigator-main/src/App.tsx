import { useState, useCallback, useEffect } from "react";
import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import Index from "./pages/Index";
import Recipes from "./pages/Recipes";
import NotFound from "./pages/NotFound";
import VoiceCommands from "@/components/VoiceCommands";

const queryClient = new QueryClient();

const AppContent = ({ isDark, toggleDarkMode }: { isDark: boolean; toggleDarkMode: () => void }) => {
  const location = useLocation();

  // Stop speech when route changes
  useEffect(() => {
    window.speechSynthesis.cancel();
  }, [location]);

  return (
    <Routes>
      <Route path="/" element={<Index isDark={isDark} toggleDarkMode={toggleDarkMode} />} />
      <Route path="/recipes" element={<Recipes isDark={isDark} toggleDarkMode={toggleDarkMode} />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
};

const App = () => {
  // Dark mode state
  const [isDark, setIsDark] = useState(document.documentElement.classList.contains("dark"));

  // Memoized toggle function
  const toggleDarkMode = useCallback(() => {
    document.documentElement.classList.toggle("dark");
    setIsDark(document.documentElement.classList.contains("dark"));
  }, []);

  return (
    <QueryClientProvider client={queryClient}>
      <TooltipProvider>
        <Toaster />
        <Sonner />
        <BrowserRouter>
          {/* VoiceCommands outside re-rendering routes */}
          <VoiceCommands toggleDarkMode={toggleDarkMode} />
          <AppContent isDark={isDark} toggleDarkMode={toggleDarkMode} />
        </BrowserRouter>
      </TooltipProvider>
    </QueryClientProvider>
  );
};

export default App;
