import './App.css';
import {
  Box,
  Button,
  ThemeProvider, 
  Typography
} from '@mui/material';
import { theme1 } from './theme';
import NavigationMenu from './NavigationMenu';
import { Link } from 'react-router-dom';


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
          <Typography variant='h1' align="center" style={{color: theme1.palette.primary.main, fontSize: '15vw', marginTop: '18vh', marginBottom: '0'}}>Underwatch</Typography>
          <Typography variant='subtitle2' align="center" style={{color: theme1.palette.primary.main, fontSize: '5vw', marginTop: '0', marginBottom: '30px'}}>The best game from TINF22B6</Typography>
        </div>
        <div style={{display: 'flex', justifyContent: 'center'}}>
            <Button variant="contained" component={Link} to="/download/" style={{
                fontWeight: 'bold', 
                border: '1px solid #F36437', 
                marginTop: "20px", 
                width: "40%"
            }}>
                Play Now
            </Button>
        </div>
      </div>
    </ThemeProvider>
  );
}

export default App;
