import { Typography } from "@mui/material";
import { theme2 } from "./theme";

function WikiCard(props:{title:string, attributes:string[], values:string[], pictureLink:string}) {
    return (
        <div className="wiki-card" style={{
            backgroundColor: theme2.palette.primary.main,
            color: theme2.palette.primary.contrastText,
            borderRadius: "10px 10px 10px 10px",
            height: "250px",
            width: "180px",
        }}>
            <Typography variant="h6" align="center">{props.title}</Typography>
            <img src={props.pictureLink} alt="placeholder" style={{
                display: "block",
                margin: "auto",
                maxHeight: "100px",
                maxWidth: "120px",
                height: "100px",
                width: "auto",
                objectFit: "contain",
            }}/>
            <div className="wiki-card-stats" style={{
                width: "calc(100% - 40px)",
                paddingLeft: "20px",
                paddingRight: "20px",
            }}>
                <table style={{width: "100%", marginTop: "30px"}}>
                    {props.attributes.map((text, index) => 
                        <tr>
                            <td>
                                <Typography variant="body1">{text.charAt(0).toUpperCase() + text.slice(1)}:</Typography>
                            </td>
                            <td>
                                <Typography variant="body1" style={{paddingLeft: "20px", textAlign: "right"}}>{props.values[index]}</Typography>
                            </td>
                        </tr>
                    )}
                </table>
            </div>
        </div>
    );
}

export default WikiCard;