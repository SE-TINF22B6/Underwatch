import { Button, MenuItem, Menu } from "@mui/material";
import MenuIcon from '@mui/icons-material/Menu';
import React from "react";
import {useNavigate} from 'react-router-dom';
import { theme2 } from "./theme";


export default function NavigationMenu() {
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);
    
    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };
    const navigate = useNavigate()


    return(
        <div>
            <Button
                id="basic-button"
                aria-controls={open ? 'basic-menu' : undefined}
                aria-haspopup="true"
                aria-expanded={open ? 'true' : undefined}
                onClick={handleClick}
            >
                <MenuIcon style={{color: theme2.palette.primary.contrastText}}/>
            </Button>
            <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                MenuListProps={{
                    'aria-labelledby': 'basic-button',
                  }}
                PaperProps={{style: {backgroundColor:theme2.palette.primary.main, color: theme2.palette.primary.contrastText}}}        
            >
                <MenuItem onClick={()=>{navigate('/')}}>Landing Page</MenuItem>
                <MenuItem onClick={()=>{navigate('/scoreboard/')}}>Scoreboard</MenuItem>
                <MenuItem onClick={()=>{navigate('/champions/')}}>Champions</MenuItem>
                <MenuItem onClick={()=>{navigate('/wiki/')}}>Wiki</MenuItem>
            </Menu>
        </div>
    )
}