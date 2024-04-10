import React from 'react';
import './App.css';
import {
  Box,
  ThemeProvider, 
  Typography
} from '@mui/material';
import VideoBackground from './VideoBackground';
import { theme1 } from './theme';
import NavigationMenu from './NavigationMenu';


function App() {
  return (
    <ThemeProvider theme={theme1}>
      <div className="App">
        <Box
          display="flex"
          justifyContent="right"
          paddingRight="24px"
          paddingTop="12px"
        >
          <NavigationMenu/>
        </Box>
        <div style={{display:'block'}}>
          <Typography variant='h1' align="center" style={{color: theme1.palette.primary.main, fontSize: '15vw', marginTop: '20vh', marginBottom: '0'}}>Underwatch</Typography>
          <Typography variant='subtitle2' align="center" style={{color: theme1.palette.primary.main, fontSize: '5vw', marginTop: '0', marginBottom: '30px'}}>The best game from TINF22B6</Typography>
        </div>
        
        {/*<div style={{display: 'flex', justifyContent: 'center'}}>
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
        </div>/*/}
      </div>
    </ThemeProvider>
  );
}

export default App;
