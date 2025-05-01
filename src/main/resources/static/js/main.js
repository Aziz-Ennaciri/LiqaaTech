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
    }).format(new Date(`2000-01-01T${time}`));
};

const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    }).format(amount);
};

// Modal Handling
const openModal = (modalId) => {
    document.getElementById(modalId).classList.remove('hidden');
    document.body.style.overflow = 'hidden';
};

const closeModal = (modalId) => {
    document.getElementById(modalId).classList.add('hidden');
    document.body.style.overflow = 'auto';
};

// Form Validation
const validateForm = (formId) => {
    const form = document.getElementById(formId);
    const requiredFields = form.querySelectorAll('[required]');
    let isValid = true;

    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            isValid = false;
            field.classList.add('border-red-500');

            // Add error message if it doesn't exist
            if (!field.nextElementSibling?.classList.contains('error-message')) {
                const errorMessage = document.createElement('p');
                errorMessage.className = 'mt-1 text-sm text-red-600 error-message';
                errorMessage.textContent = 'This field is required';
                field.parentNode.insertBefore(errorMessage, field.nextSibling);
            }
        } else {
            field.classList.remove('border-red-500');
            const errorMessage = field.nextElementSibling;
            if (errorMessage?.classList.contains('error-message')) {
                errorMessage.remove();
            }
        }
    });

    return isValid;
};

// Alert Messages
const showAlert = (message, type = 'success') => {
    const alertContainer = document.getElementById('alert-container');
    if (!alertContainer) return;

    const alert = document.createElement('div');
    alert.className = `alert alert-${type} fade-enter`;
    alert.innerHTML = `
        <div class="flex items-center">
            <div class="flex-shrink-0">
                ${type === 'success' ? '<i class="fas fa-check-circle text-green-400"></i>' :
        type === 'error' ? '<i class="fas fa-exclamation-circle text-red-400"></i>' :
            type === 'warning' ? '<i class="fas fa-exclamation-triangle text-yellow-400"></i>' :
                '<i class="fas fa-info-circle text-blue-400"></i>'}
            </div>
            <div class="ml-3">
                <p class="text-sm font-medium ${type === 'success' ? 'text-green-800' :
        type === 'error' ? 'text-red-800' :
            type === 'warning' ? 'text-yellow-800' :
                'text-blue-800'}">${message}</p>
            </div>
            <div class="ml-auto pl-3">
                <button type="button" class="alert-close">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        </div>
    `;

    alertContainer.appendChild(alert);

    // Add fade in effect
    setTimeout(() => {
        alert.classList.add('fade-enter-active');
    }, 10);

    // Auto remove after 5 seconds
    setTimeout(() => {
        alert.classList.remove('fade-enter-active');
        alert.classList.add('fade-exit-active');
        setTimeout(() => {
            alert.remove();
        }, 200);
    }, 5000);

    // Close button handler
    alert.querySelector('.alert-close').addEventListener('click', () => {
        alert.classList.remove('fade-enter-active');
        alert.classList.add('fade-exit-active');
        setTimeout(() => {
            alert.remove();
        }, 200);
    });
};

// Dropdown Handling
document.addEventListener('click', (e) => {
    const dropdowns = document.querySelectorAll('.dropdown');
    dropdowns.forEach(dropdown => {
        if (!dropdown.contains(e.target)) {
            dropdown.querySelector('.dropdown-menu')?.classList.add('hidden');
        }
    });
});

const toggleDropdown = (dropdownId) => {
    const dropdown = document.getElementById(dropdownId);
    const menu = dropdown.querySelector('.dropdown-menu');
    menu.classList.toggle('hidden');
};

// File Input Preview
const handleFileInput = (input, previewId) => {
    const preview = document.getElementById(previewId);
    const file = input.files[0];

    if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
            preview.src = e.target.result;
            preview.classList.remove('hidden');
        };
        reader.readAsDataURL(file);
    }
};

// Search Functionality
const debounce = (func, wait) => {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
};

const handleSearch = debounce((searchTerm) => {
    // Implement your search logic here
    console.log('Searching for:', searchTerm);
}, 300);

// Form Submission with AJAX
const submitFormAjax = async (formId, url, method = 'POST') => {
    const form = document.getElementById(formId);
    if (!validateForm(formId)) return;

    const formData = new FormData(form);

    try {
        const response = await fetch(url, {
            method: method,
            body: formData
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        showAlert(data.message, 'success');
        return data;
    } catch (error) {
        showAlert(error.message, 'error');
        throw error;
    }
};

// Initialize Tooltips
const initTooltips = () => {
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
            setTimeout(() => {
                tooltip.remove();
            }, 200);
        });
    });
};

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    initTooltips();

    // Add form validation listeners
    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', (e) => {
            if (!validateForm(form.id)) {
                e.preventDefault();
            }
        });
    });

    // Add search input listeners
    const searchInputs = document.querySelectorAll('[data-search]');
    searchInputs.forEach(input => {
        input.addEventListener('input', (e) => {
            handleSearch(e.target.value);
        });
    });
});

// Export functions for use in other files
window.utils = {
    formatDate,
    formatTime,
    formatCurrency,
    openModal,
    closeModal,
    validateForm,
    showAlert,
    toggleDropdown,
    handleFileInput,
    submitFormAjax
};
// Common JavaScript functions
document.addEventListener('DOMContentLoaded', function() {
    // Initialize any common functionality
});