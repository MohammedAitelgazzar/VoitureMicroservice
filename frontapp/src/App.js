import React, { useState, useEffect } from 'react';
import Client from './components/Client';
import Voiture from './components/Voiture';
import VoitureService from './service/voiture';
import './App.css';

const App = () => {
    const [clients, setClients] = useState([]);
    const [voitures, setVoitures] = useState([]);
    const [newVoiture, setNewVoiture] = useState({ marque: '', matricule: '', model: '' });
    const [selectedClientId, setSelectedClientId] = useState('');

    useEffect(() => {
        const fetchClients = async () => {
            try {
                const response = await fetch('http://localhost:8888/SERVICE-CLIENT/clients');
                const data = await response.json();
                setClients(data);
            } catch (error) {
                console.error('Erreur lors de la récupération des clients:', error);
            }
        };

        const fetchVoitures = async () => {
            try {
                const response = await fetch('http://localhost:8888/SERVICE-VOITURE/voitures');
                const data = await response.json();
                setVoitures(data);
            } catch (error) {
                console.error('Erreur lors de la récupération des voitures:', error);
            }
        };

        fetchClients();
        fetchVoitures();
    }, []);

    const handleAddVoiture = async (e) => {
        e.preventDefault();
        try {
            const voitureData = {
                marque: newVoiture.marque,
                matricule: newVoiture.matricule,
                model: newVoiture.model
            };

            await VoitureService.addVoiture(selectedClientId, voitureData);
            const updatedVoitures = await VoitureService.getAllVoitures();
            setVoitures(updatedVoitures.data);
            setNewVoiture({ marque: '', matricule: '', model: '' });
        } catch (error) {
            console.error('Erreur lors de l\'ajout de la voiture:', error);
        }
    };

    return (
        <div className="App">
            <h1>Informations sur les Clients</h1>
            <div className="container">
                {clients.map((client) => (
                    <Client key={client.id} client={client} />
                ))}
            </div>
            <h1>Informations sur les Voitures</h1>
            <div className="container">
                {voitures.map((voiture) => (
                    <Voiture key={voiture.id} voiture={voiture} />
                ))}
            </div>
            <h2>Ajouter une Voiture</h2>
            <form onSubmit={handleAddVoiture} className="form-container">
                <select onChange={(e) => setSelectedClientId(e.target.value)} value={selectedClientId}>
                    <option value="">Sélectionnez un client</option>
                    {clients.map(client => (
                        <option key={client.id} value={client.id}>{client.nom}</option>
                    ))}
                </select>
                <input
                    type="text"
                    placeholder="Marque"
                    value={newVoiture.marque}
                    onChange={(e) => setNewVoiture({ ...newVoiture, marque: e.target.value })}
                    required
                />
                <input
                    type="text"
                    placeholder="Matricule"
                    value={newVoiture.matricule}
                    onChange={(e) => setNewVoiture({ ...newVoiture, matricule: e.target.value })}
                    required
                />
                <input
                    type="text"
                    placeholder="Modèle"
                    value={newVoiture.model}
                    onChange={(e) => setNewVoiture({ ...newVoiture, model: e.target.value })}
                    required
                />
                <button type="submit">Ajouter Voiture</button>
            </form>
        </div>
    );
};

export default App;
