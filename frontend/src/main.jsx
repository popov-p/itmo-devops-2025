import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { createRoot } from 'react-dom/client'
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { CssBaseline } from '@mui/material';

import Home from './Home';

const duneTheme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#00e676',
    },
    secondary: {
      main: '#00bfae',
    },
    background: {
      default: '#121212',
      paper: '#1C1C1C',
    },
    text: {
      primary: '#ffffff',
      secondary: '#D3D3D3',
    },
  },
  typography: {
    fontFamily: '"Roboto", sans-serif',
  },
});




export default function Main() {
  return (
    <ThemeProvider theme={duneTheme}>
      <CssBaseline />
      <Router future={{ v7_startTransition: true, v7_relativeSplatPath: true }}>
        <Routes>
          <Route path="/" element={<Home />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

createRoot(document.getElementById('root')).render(
  <Main />
)