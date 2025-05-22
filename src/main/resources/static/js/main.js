// Utility Functions
const formatDate = (date) => {
    return new Intl.DateTimeFormat('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    }).format(new Date(date));
};

const formatTime = (time) => {
    return new Intl.DateTimeFormat('en-US', {
        hour: '2-digit',
        minute: '2-digit'
    }).format(new Date(time));
};

const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    }).format(amount);
};

// DOM Elements
const mobileMenuBtn = document.querySelector('.mobile-menu-btn');
const mobileMenu = document.getElementById('mobile-menu');
const searchInput = document.getElementById('search');
const categorySelect = document.getElementById('category');
const dateSelect = document.getElementById('date');
const priceSelect = document.getElementById('price');
};

const closeModal = (modalId) => {
    document.getElementById(modalId).classList.add('hidden');
    document.body.style.overflow = 'auto';
};

// Form Validation
const validateForm = (formId) => {
    const form = document.getElementById(formId);
    let isValid = true;

    // Reset previous validation states
    form.querySelectorAll('.error-message').forEach(el => el.remove());
    form.querySelectorAll('.border-red-500').forEach(el => {
        el.classList.remove('border-red-500');
        el.classList.add('border-gray-300');
    });

    // Required fields validation
    form.querySelectorAll('[required]').forEach(field => {
        if (!field.value.trim()) {
            isValid = false;
            field.classList.add('border-red-500');
            field.classList.remove('border-gray-300');

            const error = document.createElement('p');
            error.className = 'error-message text-red-500 text-sm mt-1';
            error.textContent = 'This field is required';
            field.parentNode.appendChild(error);
        }
    });

    // Email validation
    form.querySelectorAll('[type="email"]').forEach(field => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (field.value && !emailRegex.test(field.value)) {
            isValid = false;
            field.classList.add('border-red-500');
            field.classList.remove('border-gray-300');

            const error = document.createElement('p');
            error.className = 'error-message text-red-500 text-sm mt-1';
            error.textContent = 'Please enter a valid email address';
            field.parentNode.appendChild(error);
        }
    });

    return isValid;
};

// Toast Notifications
const showToast = (message, type = 'success') => {
    const container = document.getElementById('toast-container');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = `toast toast-${type} fade-in`;

    const icon = document.createElement('i');
    icon.className = type === 'success' ? 'fas fa-check-circle mr-2' :
        type === 'error' ? 'fas fa-exclamation-circle mr-2' :
            'fas fa-exclamation-triangle mr-2';

    toast.appendChild(icon);
    toast.appendChild(document.createTextNode(message));

    container.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 3000);
};

// View Toggle Functionality
document.addEventListener('DOMContentLoaded', function() {
    const gridView = document.getElementById('gridView');
    const listView = document.getElementById('listView');
    const eventsGrid = document.getElementById('eventsGrid');
    const eventsList = document.getElementById('eventsList');

    if (gridView && listView) {
        // View Toggle
        gridView.addEventListener('click', () => {
            gridView.classList.add('text-indigo-600');
            listView.classList.remove('text-indigo-600');
            eventsGrid.classList.remove('hidden');
            eventsList.classList.add('hidden');
        });

        listView.addEventListener('click', () => {
            listView.classList.add('text-indigo-600');
            gridView.classList.remove('text-indigo-600');
            eventsList.classList.remove('hidden');
            eventsGrid.classList.add('hidden');
        });
    }

    // Filter Handling
    const categorySelect = document.getElementById('category');
    const dateFilter = document.getElementById('dateFilter');
    const searchInput = document.getElementById('search');

    if (categorySelect && dateFilter && searchInput) {
        const updateEvents = debounce(() => {
            const params = new URLSearchParams({
                category: categorySelect.value,
                date: dateFilter.value,
                search: searchInput.value
            });

            window.location.href = `/events?${params.toString()}`;
        }, 500);

        categorySelect.addEventListener('change', updateEvents);
        dateFilter.addEventListener('change', updateEvents);
        searchInput.addEventListener('input', updateEvents);
    }
});

// Utility Functions
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    // Initialize tooltips
    const tooltips = document.querySelectorAll('[data-tooltip]');
    tooltips.forEach(element => {
        const tooltip = document.createElement('div');
        tooltip.className = 'tooltip opacity-0 transition-opacity duration-200';
        tooltip.textContent = element.getAttribute('data-tooltip');

        element.addEventListener('mouseenter', () => {
            document.body.appendChild(tooltip);
            const rect = element.getBoundingClientRect();
            tooltip.style.top = `${rect.top - tooltip.offsetHeight - 5}px`;
            tooltip.style.left = `${rect.left + (element.offsetWidth - tooltip.offsetWidth) / 2}px`;
            tooltip.classList.remove('opacity-0');
        });

        element.addEventListener('mouseleave', () => {
            tooltip.classList.add('opacity-0');
            setTimeout(() => tooltip.remove(), 200);
        });
    });
});