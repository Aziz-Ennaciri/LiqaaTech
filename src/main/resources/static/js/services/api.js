import axios from 'axios';

const API_BASE_URL = '/api';

// Event API calls
export const fetchEvents = async (params) => {
    const response = await axios.get(`${API_BASE_URL}/events`, { params });
    return response;
};

export const fetchEventById = async (id) => {
    const response = await axios.get(`${API_BASE_URL}/events/${id}`);
    return response;
};

export const createEvent = async (eventData) => {
    const response = await axios.post(`${API_BASE_URL}/events`, eventData);
    return response;
};

export const updateEvent = async (id, eventData) => {
    const response = await axios.put(`${API_BASE_URL}/events/${id}`, eventData);
    return response;
};

export const deleteEvent = async (id) => {
    const response = await axios.delete(`${API_BASE_URL}/events/${id}`);
    return response;
};

export const publishEvent = async (id) => {
    const response = await axios.post(`${API_BASE_URL}/events/${id}/publish`);
    return response;
};

export const cancelEvent = async (id, reason) => {
    const response = await axios.post(`${API_BASE_URL}/events/${id}/cancel`, null, {
        params: { reason }
    });
    return response;
};

// Category API calls
export const fetchCategories = async () => {
    const response = await axios.get(`${API_BASE_URL}/categories`);
    return response;
};

// Registration API calls
export const registerForEvent = async (eventId) => {
    const response = await axios.post(`${API_BASE_URL}/registrations`, { eventId });
    return response;
};

// Add axios interceptors for error handling
axios.interceptors.response.use(
    response => response,
    error => {
        if (error.response) {
            // Handle specific error cases
            switch (error.response.status) {
                case 401:
                    // Handle unauthorized
                    break;
                case 403:
                    // Handle forbidden
                    break;
                case 404:
                    // Handle not found
                    break;
                case 500:
                    // Handle server error
                    break;
                default:
                    // Handle other errors
                    break;
            }
        }
        return Promise.reject(error);
    }
); 