import React from 'react';
import './NotFound.css';

function NotFound() {
  return (
    <div className="NotFound">
      <h1 className="nf-header">404</h1>
      <p>
        Die von Ihnen angeforderte Seite konnte leider nicht gefunden werden.
        Bitte überprüfen Sie die URL auf Tippfehler oder kehren sie
        <a href="/"> hier </a>
        zur Startseite zurück.
      </p>

    </div>
  );
}

export default NotFound;
