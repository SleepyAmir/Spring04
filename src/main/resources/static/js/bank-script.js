/*<![CDATA[*/
const showModalOnError = /*[[${showModal}]]*/ false;
/*]]>*/

document.addEventListener('DOMContentLoaded', function () {

    const accountModalEl = document.getElementById('accountModal');
    const accountModal = new bootstrap.Modal(accountModalEl);
    const accountForm = document.getElementById('accountForm');
    const methodField = document.getElementById('methodField');
    const modalTitle = document.getElementById('modalTitle');
    const nameInput = document.getElementById('name');
    const familyInput = document.getElementById('family');
    const typeSelect = document.getElementById('type');

    // Show modal if there are validation errors
    if (showModalOnError) {
        accountModal.show();
    }

    // Show toast notifications
    const toasts = document.querySelectorAll('.toast');
    toasts.forEach(t => new bootstrap.Toast(t).show());

    // Open Add Modal Function
    window.openAddModal = function () {
        modalTitle.innerText = "Add New Bank Account";
        accountForm.action = "/bank";
        accountForm.reset();

        // Remove validation error classes
        nameInput.classList.remove('is-invalid');
        familyInput.classList.remove('is-invalid');
        typeSelect.classList.remove('is-invalid');

        // Show info alert
        const infoAlert = document.querySelector('.alert-info');
        if (infoAlert) {
            infoAlert.style.display = 'block';
        }

        methodField.disabled = true;
        methodField.value = "";
        accountModal.show();
    }

    // Open Edit Modal Function
    window.openEditModal = function (id, name, family, type) {
        modalTitle.innerText = "Edit Bank Account";
        accountForm.action = "/bank/" + id;

        nameInput.value = name;
        familyInput.value = family;
        typeSelect.value = type;

        // Remove validation error classes
        nameInput.classList.remove('is-invalid');
        familyInput.classList.remove('is-invalid');
        typeSelect.classList.remove('is-invalid');

        // Hide info alert for edit mode
        const infoAlert = document.querySelector('.alert-info');
        if (infoAlert) {
            infoAlert.style.display = 'none';
        }

        methodField.disabled = false;
        methodField.value = "put";
        accountModal.show();
    }

    // Delete Modal Handler
    const deleteModal = document.getElementById('deleteModal');
    const deleteForm = document.getElementById('deleteForm');
    deleteModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const id = button.getAttribute('data-id');
        deleteForm.action = `/bank/${id}`;
    });
});
