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
import Champions from './Champions';
import Download from './Download';
import ViedeoBackground from './VideoBackground';
import {Toaster} from 'react-hot-toast';
import WikiLore from './WikiLore';


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
    path: '/wiki/lore/',
    element: <WikiLore/>
  },
  {
    path: '*',
    element: <NotFound/>
  },
  {
    path: '/download/',
    element: <Download/>
  }
]);

root.render(
  <React.StrictMode>
    <div><Toaster/></div>
    <div><ViedeoBackground path="/Gameplay.mov"/></div>
    <RouterProvider router={router}/>
  </React.StrictMode>
);