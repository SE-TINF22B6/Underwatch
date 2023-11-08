import React from 'react';
import './App.css';
import { Link } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <h1 className='lp-heading'>Underwatch</h1>
      <p className='lp-subheading'>The best game from TINF22B6</p>
      <Link to="/scoreboard">Scoreboard</Link>
    </div>
  );
}

export default App;
