import React from 'react';
import './Client.css';

const Client = ({ client }) => {
    return (
        <div className="client-card">
            <h2>{client.nom}</h2>
            <p>Âge: {client.age}</p>
        </div>
    );
};

export default Client;
