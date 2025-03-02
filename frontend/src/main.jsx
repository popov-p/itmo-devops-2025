import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { createRoot } from 'react-dom/client'
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { CssBaseline } from '@mui/material';

import Login from './Login';
import Home from './Home';

const duneTheme = createTheme({
  palette: {
    mode: 'dark', // Тёмная тема
    primary: {
      main: '#00e676', // Яркий зеленый цвет
    },
    secondary: {
      main: '#00bfae', // Светлый зеленый/бирюзовый для вторичной схемы
    },
    background: {
      default: '#121212', // Темный фон
      paper: '#1C1C1C', // Тёмный фон для карточек и диалогов
    },
    text: {
      primary: '#ffffff', // Белый текст
      secondary: '#D3D3D3', // Более светлый текст для вторичных элементов
    },
  },
  typography: {
    fontFamily: '"Roboto", sans-serif', // Шрифт по умолчанию
  },
});




export default function Main() {
  return (
    <ThemeProvider theme={duneTheme}>
      <CssBaseline />
      <Router future={{ v7_startTransition: true, v7_relativeSplatPath: true }}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

createRoot(document.getElementById('root')).render(
  <Main />
)