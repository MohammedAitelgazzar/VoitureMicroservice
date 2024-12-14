import axios from 'axios';

const API_URL = 'http://localhost:8888/SERVICE-CLIENT/clients';

class ClientService {
    getAllClients() {
        return axios.get(`${API_URL}`);
    }

    getClientById(id) {
        return axios.get(`${API_URL}/${id}`);
    }

    addClient(client) {
        return axios.post(`${API_URL}`, client);
    }
}

export default new ClientService(); 