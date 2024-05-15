import WikiCard from "./WikiCard";
import './WikiCardBox.css';

function WikiCardBox(props:{getData:Function}) {
    const data:object[] = props.getData();
    let cardAttributes: string[] = [];
    let cardValues: string[][] = [];
    let picturePaths: string[] = [];
    cardAttributes.push(Object.keys(data[0])[2], Object.keys(data[0])[3]);
    data.forEach((object) => {
        const values = Object.values(object);
        cardValues.push([values[2], values[3]]);
        picturePaths.push(values[values.length-1]);
    });

    return (
        <div className="OuterCardBox" style={{
            width: "100%",
            display: "flex",
            justifyContent: "center"
        }}>
            <div className="InnerCardBox" style={{
                height: 'calc(100vh - 200px)',
                overflowX: "hidden",
                overflowY: "auto",
                padding: "30px 30px 0 30px",
                display: "flex",
                flexWrap: "wrap",
                gap: "10px",
                maxWidth: "100%",
                width: "auto",
                margin: "auto",
                justifyContent: "center"
            }}>
                {data.map((object, index) => 
                    <WikiCard title={Object.values(object)[1]} attributes={cardAttributes} values={cardValues[index]} pictureLink={picturePaths[index]}/>
                )}
            </div>
        </div>
    );
}

export default WikiCardBox;