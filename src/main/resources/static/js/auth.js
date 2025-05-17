const auth = {
    isAuthenticated: function() {
        return localStorage.getItem('token') !== null;
    },

    getToken: function() {
        return localStorage.getItem('token');
    },

    setToken: function(token) {
        localStorage.setItem('token', token);
    },

    removeToken: function() {
        localStorage.removeItem('token');
    },

    logout: function() {
        this.removeToken();
        window.location.href = '/login';
    },

    fetchWithAuth: function(url, options = {}) {
        const token = this.getToken();
        if (!token) {
            return Promise.reject('No authentication token found');
        }

        const headers = {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
            ...options.headers
        };

        return fetch(url, {
            ...options,
            headers
        });
    }
};

document.addEventListener('DOMContentLoaded', function() {
    const protectedRoutes = ['/dashboard', '/events/create', '/profile'];
    const currentPath = window.location.pathname;

    if (protectedRoutes.includes(currentPath) && !auth.isAuthenticated()) {
        window.location.href = '/login';
    }
});

const logoutButton = document.getElementById('logoutButton');
if (logoutButton) {
    logoutButton.addEventListener('click', function(e) {
        e.preventDefault();
        auth.logout();
    });
}