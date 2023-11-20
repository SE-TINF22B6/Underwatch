import React from "react";

const ViedeoBackground = () => {
    return(
        <video
            autoPlay
            loop
            muted
            style={{
                position: 'fixed',
                top: 0,
                left: 0,
                width: '100%',
                height: '100%',
                objectFit: 'cover',
                zIndex: -1,
                filter: 'blur(5px)'
            }}
        >
            <source src="../Gameplay.mov"/>
            Dein Browser unterstützt das Viedeo-Tag nicht
        </video>
    );
};

export default ViedeoBackground;