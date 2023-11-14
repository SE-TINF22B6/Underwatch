import React from 'react';
import './App.css';
import { Link } from 'react-router-dom';
import {
  Button
} from '@mui/material';


function App() {
  return (
    <div className="App">
      <h1 className='lp-heading'>Underwatch</h1>
      <p className='lp-subheading'>The best game from TINF22B6</p>
      
      <div style={{display: 'flex', justifyContent: 'center'}}>
        <Link to="/scoreboard">
          <Button variant="contained">
                  Scoreboard
          </Button>
        </Link>
      </div>
    </div>
  );
}

export default App;
