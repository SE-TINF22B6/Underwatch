import React from 'react';
import './App.css';
import { Link } from 'react-router-dom';
import {
  Button, ThemeProvider, Typography
} from '@mui/material';
import VideoBackground from './VideoBackground';
import { theme1 } from './theme';


function App() {
  return (
    <ThemeProvider theme={theme1}>
      <div className="App">
        <VideoBackground/>
        <Typography variant='h1' style={{color: theme1.palette.primary.main, fontSize: '15vw', marginTop: '20vh', marginBottom: '0'}}>Underwatch</Typography>
        <Typography variant='subtitle2' style={{color: theme1.palette.primary.main, fontSize: '5vw', marginTop: '0', marginBottom: '30px'}}>The best game from TINF22B6</Typography>

        <div style={{display: 'flex', justifyContent: 'center'}}>
          <Link to="/scoreboard">
            <Button variant="contained">
                    Scoreboard
            </Button>
          </Link>
          <Link to="/wiki">
              <Button variant="contained">
                  Wiki
              </Button>
          </Link>
        </div>
      </div>
    </ThemeProvider>
  );
}

export default App;
