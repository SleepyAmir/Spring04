/*<![CDATA[*/
const showModalOnError = /*[[${showModal}]]*/ false;
/*]]>*/

document.addEventListener('DOMContentLoaded', function () {

    const personModalEl = document.getElementById('personModal');
    const personModal = new bootstrap.Modal(personModalEl);
    const personForm = document.getElementById('personForm');
    const methodField = document.getElementById('methodField');
    const modalTitle = document.getElementById('modalTitle');
    const nameInput = document.getElementById('name');
    const familyInput = document.getElementById('family');

    if (showModalOnError) {
        personModal.show();
    }

    const toasts = document.querySelectorAll('.toast');
    toasts.forEach(t => new bootstrap.Toast(t).show());

    window.openAddModal = function () {
        modalTitle.innerText = "Add New Person";
        personForm.action = "/persons";
        personForm.reset();
        nameInput.classList.remove('is-invalid');
        familyInput.classList.remove('is-invalid');
        methodField.disabled = true;
        methodField.value = "";
        personModal.show();
    }

    window.openEditModal = function (id, name, family) {
        modalTitle.innerText = "Edit Person";
        personForm.action = "/persons/" + id;
        nameInput.value = name;
        familyInput.value = family;
        nameInput.classList.remove('is-invalid');
        familyInput.classList.remove('is-invalid');
        methodField.disabled = false;
        methodField.value = "put";
        personModal.show();
    }

    const deleteModal = document.getElementById('deleteModal');
    const deleteForm = document.getElementById('deleteForm');
    deleteModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const id = button.getAttribute('data-id');
        deleteForm.action = `/persons/${id}`;
    });
});