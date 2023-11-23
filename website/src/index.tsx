import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Scoreboard from './Scoreboard';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import NavigationMenu from './NavigationMenu';
import NotFound from './NotFound';
import Wiki from './wiki';
import Champions from './Champions';


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
  },
  {
    path: '/champions/',
    element: <Champions/>
  },
  {
    path: '/wiki/',
    element: <Wiki/>
  },
  {
    path: '*',
    element: <NotFound/>
  }
]);

root.render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>
);