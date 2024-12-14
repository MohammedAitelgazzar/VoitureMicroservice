import React from 'react';
import './Voiture.css';

const Voiture = ({ voiture }) => {
    return (
        <div className="voiture-card">
            <h2>{voiture.marque} {voiture.model}</h2>
            <p>Matricule: {voiture.matricule}</p>
            <p>Propriétaire: {voiture.client.nom}</p>
            <p>Âge du Propriétaire: {voiture.client.age} ans</p>
        </div>
    );
};

export default Voiture; 