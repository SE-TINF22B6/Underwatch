import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Scoreboard from './Scoreboard';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import NotFound from './NotFound';
import Wiki from './Wiki';
import WikiWeapons from './WikiWeapons';
import WikiMobs from './WikiMobs';
import WikiTipps from './WikiTipps';
import Champions from './Champions';
import ViedeoBackground from './VideoBackground';




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
    path: '/wiki/weapons/',
    element: <WikiWeapons/>
  },
  {
    path: '/wiki/mobs/',
    element: <WikiMobs/>
  },
  {
    path: '/wiki/tipps/',
    element: <WikiTipps/>
  },
  {
    path: '*',
    element: <NotFound/>
  }
]);

root.render(
  <React.StrictMode>
    <ViedeoBackground path="./Gameplay.mov"/>
    <RouterProvider router={router}/>
  </React.StrictMode>
);