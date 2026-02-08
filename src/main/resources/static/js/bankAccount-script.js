/*<![CDATA[*/
const showModalOnError = /*[[${showModal}]]*/ false;
const currentSortBy = /*[[${sortBy}]]*/ 'balance';
const currentSortDirection = /*[[${sortDirection}]]*/ 'desc';
const isTrash = /*[[${isTrash}]]*/ false;
/*]]>*/

document.addEventListener('DOMContentLoaded', function () {
    
    // ==============================
    // Toast Notifications
    // ==============================
    const toasts = document.querySelectorAll('.toast');
    toasts.forEach(toast => {
        const bsToast = new bootstrap.Toast(toast, {
            autohide: true,
            delay: 5000
        });
        bsToast.show();
    });

    // ==============================
    // Modal Initialization
    // ==============================
    const bankAccountModalEl = document.getElementById('bankAccountModal');
    const bankAccountModal = new bootstrap.Modal(bankAccountModalEl);
    const bankAccountForm = document.getElementById('bankAccountForm');
    const methodField = document.getElementById('methodField');
    const modalTitle = document.getElementById('modalTitle');

    // Input fields
    const nameInput = document.getElementById('name');
    const familyInput = document.getElementById('family');
    const accountNumberInput = document.getElementById('accountNumber');
    const balanceInput = document.getElementById('balance');
    const typeInput = document.getElementById('type');

    // Show modal if validation errors exist
    if (showModalOnError) {
        bankAccountModal.show();
    }

    // ==============================
    // Delete Modal Handler
    // ==============================
    const deleteModal = document.getElementById('deleteModal');
    const deleteForm = document.getElementById('deleteForm');
    
    if (deleteModal && deleteForm) {
        deleteModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const id = button.getAttribute('data-id');
            deleteForm.action = `/bank/${id}`;
        });
    }

    // ==============================
    // Restore Modal Handler
    // ==============================
    const restoreModal = document.getElementById('restoreModal');
    const restoreForm = document.getElementById('restoreForm');
    
    if (restoreModal && restoreForm) {
        restoreModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const id = button.getAttribute('data-id');
            restoreForm.action = `/bank/restore/${id}`;
        });
    }

    // ==============================
    // Account Number Formatting
    // ==============================
    if (accountNumberInput) {
        accountNumberInput.addEventListener('input', function(e) {
            // Remove non-numeric characters
            let value = e.target.value.replace(/\D/g, '');
            
            // Limit to 16 digits
            if (value.length > 16) {
                value = value.substring(0, 16);
            }
            
            e.target.value = value;
        });
    }

    // ==============================
    // Balance Validation
    // ==============================
    if (balanceInput) {
        balanceInput.addEventListener('input', function(e) {
            // Ensure only positive numbers
            if (parseFloat(e.target.value) < 0) {
                e.target.value = 0;
            }
        });
    }

    // ==============================
    // Form Validation Enhancement
    // ==============================
    if (bankAccountForm) {
        bankAccountForm.addEventListener('submit', function(e) {
            // Clear previous validation states
            clearValidationErrors();
            
            let isValid = true;
            const errors = [];

            // Validate Name
            if (!nameInput.value.trim() || !validatePattern(nameInput.value, /^[a-zA-Z\s]{3,20}$/)) {
                showError(nameInput, 'Name must be 3-20 alphabetic characters');
                isValid = false;
                errors.push('Invalid Name');
            }

            // Validate Family
            if (!familyInput.value.trim() || !validatePattern(familyInput.value, /^[a-zA-Z\s]{3,20}$/)) {
                showError(familyInput, 'Family must be 3-20 alphabetic characters');
                isValid = false;
                errors.push('Invalid Family');
            }

            // Validate Account Number
            if (!accountNumberInput.value.trim() || accountNumberInput.value.length !== 16) {
                showError(accountNumberInput, 'Account number must be exactly 16 digits');
                isValid = false;
                errors.push('Invalid Account Number');
            }

            // Validate Balance
            if (!balanceInput.value || parseFloat(balanceInput.value) <= 0) {
                showError(balanceInput, 'Balance must be greater than 0');
                isValid = false;
                errors.push('Invalid Balance');
            }

            // Validate Type
            if (!typeInput.value) {
                showError(typeInput, 'Please select an account type');
                isValid = false;
                errors.push('Account Type Required');
            }

            if (!isValid) {
                e.preventDefault();
                console.log('Form validation failed:', errors);
            }
        });
    }

    // ==============================
    // Helper Functions
    // ==============================
    function validatePattern(value, pattern) {
        return pattern.test(value);
    }

    function showError(element, message) {
        element.classList.add('is-invalid');
        const feedback = element.nextElementSibling;
        if (feedback && feedback.classList.contains('invalid-feedback')) {
            feedback.textContent = message;
        } else {
            const div = document.createElement('div');
            div.className = 'invalid-feedback';
            div.textContent = message;
            element.parentNode.insertBefore(div, element.nextSibling);
        }
    }

    function clearValidationErrors() {
        document.querySelectorAll('.is-invalid').forEach(el => {
            el.classList.remove('is-invalid');
        });
    }
});

// ==============================
// Global Functions (callable from HTML)
// ==============================

/**
 * Open modal for adding new bank account
 */
window.openAddModal = function() {
    const modal = new bootstrap.Modal(document.getElementById('bankAccountModal'));
    const form = document.getElementById('bankAccountForm');
    const methodField = document.getElementById('methodField');
    const modalTitle = document.getElementById('modalTitle');
    
    modalTitle.innerText = "Add New Bank Account";
    form.action = "/bank";
    form.reset();
    
    // Clear all validation errors
    document.querySelectorAll('.is-invalid').forEach(el => {
        el.classList.remove('is-invalid');
    });
    document.querySelectorAll('.invalid-feedback').forEach(el => {
        el.textContent = '';
    });
    
    methodField.disabled = true;
    methodField.value = "";
    
    modal.show();
}

/**
 * Open modal for editing existing bank account
 */
window.openEditModal = function(id, name, family, accountNumber, balance, type) {
    const modal = new bootstrap.Modal(document.getElementById('bankAccountModal'));
    const form = document.getElementById('bankAccountForm');
    const methodField = document.getElementById('methodField');
    const modalTitle = document.getElementById('modalTitle');
    
    modalTitle.innerText = "Edit Bank Account";
    form.action = `/bank/${id}`;
    
    // Set form values
    document.getElementById('name').value = name;
    document.getElementById('family').value = family;
    document.getElementById('accountNumber').value = accountNumber;
    document.getElementById('balance').value = balance;
    document.getElementById('type').value = type;
    
    // Clear validation errors
    document.querySelectorAll('.is-invalid').forEach(el => {
        el.classList.remove('is-invalid');
    });
    document.querySelectorAll('.invalid-feedback').forEach(el => {
        el.textContent = '';
    });
    
    methodField.disabled = false;
    methodField.value = "put";
    
    modal.show();
}

/**
 * Sort table by column
 */
window.sortTable = function(column) {
    const url = new URL(window.location);
    const currentSort = url.searchParams.get('sortBy') || 'balance';
    const currentDirection = url.searchParams.get('sortDirection') || 'desc';
    
    let newDirection = 'asc';
    
    // Toggle direction if clicking same column
    if (currentSort === column && currentDirection === 'asc') {
        newDirection = 'desc';
    }
    
    url.searchParams.set('sortBy', column);
    url.searchParams.set('sortDirection', newDirection);
    url.searchParams.set('page', '0'); // Reset to first page when sorting
    
    // Preserve filter parameters
    const filters = ['name', 'family', 'accountNumber'];
    filters.forEach(filter => {
        const value = url.searchParams.get(filter);
        if (value) {
            url.searchParams.set(filter, value);
        }
    });
    
    window.location.href = url.toString();
}

/**
 * Clear all filters and reset to default view
 */
window.clearFilters = function() {
    window.location.href = isTrash ? '/bank/trash' : '/bank';
}

/**
 * Format currency for display
 */
window.formatCurrency = function(amount) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(amount);
}

/**
 * Format account number with spacing (XXXX XXXX XXXX XXXX)
 */
window.formatAccountNumber = function(accountNumber) {
    return accountNumber.replace(/(\d{4})(?=\d)/g, '$1 ');
}

// ==============================
// Keyboard Shortcuts
// ==============================
document.addEventListener('keydown', function(e) {
    // Ctrl/Cmd + K = Open Add Modal
    if ((e.ctrlKey || e.metaKey) && e.key === 'k' && !isTrash) {
        e.preventDefault();
        window.openAddModal();
    }
    
    // Escape = Close all modals
    if (e.key === 'Escape') {
        const modals = document.querySelectorAll('.modal.show');
        modals.forEach(modal => {
            const bsModal = bootstrap.Modal.getInstance(modal);
            if (bsModal) {
                bsModal.hide();
            }
        });
    }
});

// ==============================
// Auto-hide alerts after 5 seconds
// ==============================
setTimeout(() => {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        const bsAlert = new bootstrap.Alert(alert);
        bsAlert.close();
    });
}, 5000);

// ==============================
// Loading State Management
// ==============================
window.addEventListener('beforeunload', function() {
    document.body.style.opacity = '0.6';
    document.body.style.pointerEvents = 'none';
});

console.log('✅ Bank Account Management System Loaded Successfully');
console.log('📊 Current Sort:', currentSortBy, currentSortDirection);
console.log('🗑️ Trash Mode:', isTrash);
