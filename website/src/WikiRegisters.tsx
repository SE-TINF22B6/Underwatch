import { Typography } from "@mui/material";
import { theme2 } from "./theme";
import React, { useState } from "react";
import { Link } from "react-router-dom";

function mapTabIdents(array:number[]):number[]{
    const returnArray: number[] = [];
    array.forEach((number: number) => {
        if(number === 1){
            returnArray.push(50);
        } else if (number === 2){
            returnArray.push(100);
        } else if (number === 3){
            returnArray.push(200);
        }
    });
    return  returnArray;
}

function WikiRegisters(props:{tabIdents:number[]}) {
    const registerNames = ["Weapons", "Mobs", "Game Law"];
    const wikiLinks = ["/wiki/weapons/", "/wiki/mobs/", "/wiki/law/"]
    const registerText = ["gives an overview over all the playable weapons", "gives an overview over all the mobs you can meet", "just explore the story behind the game"]
    const tabIdentValues : number[] = mapTabIdents(props.tabIdents);
    const [hoveredCards, setHoveredCards] = useState(Array(tabIdentValues.length).fill(false));

    return (
        <div className="wiki-registers" style={{
            position: "fixed",
            display: "flex",
            bottom: 0,
            width: "80%",
            height: "20%",
            marginLeft: "10%",
            alignItems: "flex-end",
        }}>
            {registerNames.map((text, index) => 
                <div className="register-card" 
                    key={"registerNo." + index}
                    style={{
                        color: theme2.palette.primary.contrastText,
                        backgroundColor: theme2.palette.primary.main,
                        border: "1px solid",
                        borderRadius: "10px 10px 0 0",
                        height: hoveredCards[index]? "200px" : tabIdentValues[index],
                        flex:1,
                        transition: "height 0.3s",
                    }}
                    onMouseEnter={() => {
                        const updatedHoveredCards = Array(tabIdentValues.length).fill(false);
                        updatedHoveredCards[index] = true;
                        setHoveredCards(updatedHoveredCards);
                    }}
                    onMouseLeave={() => {setHoveredCards(Array(tabIdentValues.length).fill(false));
                }}>
                    <Link to={wikiLinks[index]} className="link" style={{
                        color: theme2.palette.primary.contrastText,
                        textDecoration: "none",
                    }}>
                        <div style={{
                            height:"100%"
                        }}>
                            <Typography variant='h5' align='center'>{text}</Typography>
                            <Typography variant='body1' align='center' style={{
                                marginTop:"20px",
                            }}>
                                {registerText[index]}
                            </Typography>
                        </div>
                    </Link>
                </div>
            )}
        </div>
    );
}

export default WikiRegisters;