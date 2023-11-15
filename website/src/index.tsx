import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Scoreboard from './Scoreboard';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { theme1 } from './theme';
import { ThemeProvider } from '@mui/material';


const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />
  },
  {
    path: '/scoreboard/',
    element: <Scoreboard />
  }
]);

root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme1}>
      <RouterProvider router={router}/>
    </ThemeProvider>
  </React.StrictMode>
);