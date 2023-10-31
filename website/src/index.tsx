import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Scoreboard from './Scoreboard';
import NotFound from './NotFound';
import { BrowserRouter, Route, Routes} from 'react-router-dom';


const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);



root.render(
<BrowserRouter>
  <Routes>
    <Route path="*" element={<NotFound/>}/>
    <Route path="/" element={<App/>}/>
    <Route path="/scoreboard/" element={<Scoreboard/>}/>
  </Routes>
</BrowserRouter>
);