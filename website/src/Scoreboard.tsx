import React from 'react';
import './Scoreboard.css';
import { Link } from 'react-router-dom';
import { AppBar, Toolbar} from '@mui/material';



function Scoreboard() {
  return (
    <div className="Scoreboard">
        <AppBar position="static" className='appBar'>
            <Toolbar variant="dense" className='toolBar'>
                <Link to="/" className='link'>
                    <img src="../Logo.png" alt="Underwatch-logo" className='logo'/>
                </Link>
                <p className='sc-heading'>
                    Scoreboard
                </p>
            </Toolbar>
        </AppBar>
        <table>
            <tr>
                <td>Test</td>
                <td>Test2</td>
            </tr>
            <tr>
                <td>Inhalt</td>
                <td>Inhalt2</td>
            </tr>
        </table>
    </div>
  );
}

export default Scoreboard;