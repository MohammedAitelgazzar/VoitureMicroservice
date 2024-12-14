import axios from 'axios';

const API_URL = 'http://localhost:8888/SERVICE-VOITURE/voitures';

class VoitureService {
    getAllVoitures() {
        return axios.get(`${API_URL}`);
    }

    getVoituresByClient(clientId) {
        return axios.get(`${API_URL}/client/${clientId}`);
    }

    addVoiture(clientId, voiture) {
        return axios.post(`${API_URL}/${clientId}`, voiture);
    }
}

export default new VoitureService();