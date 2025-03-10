$(async function () {
    await getTableWithUsers();
    getNewUserForm();
    getDefaultModal();
    addNewUser();
})

const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    // bodyAdd : async function(user) {return {'method': 'POST', 'headers': this.head, 'body': user}},
    findAllUsers: async () => await fetch('admin/api/users'),
    findOneUser: async (id) => await fetch(`admin/api/users/${id}`),
    addNewUser: async (user) => await fetch('admin/api/users', {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    updateUser: async (data, id) => await fetch(`admin/api/users/${id}`, {
        method: 'PUT',
        headers: userFetchService.head,
        body: JSON.stringify(data)
    }),
    deleteUser: async (id) => await fetch(`admin/api/users/${id}`, {method: 'DELETE', headers: userFetchService.head})
}

async function getTableWithUsers() {
    let table = $('#mainTableWithUsers tbody');
    table.empty();

    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {

            users.forEach(user => {
                let uid = user.id;
                let idEditButton = uid.toString();
                let tableFilling = `$(        
                        <tr
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.password.slice(0, 15)}...</td>
                            <td>${user.enabled}</td>
                            <td>
                                <button type="button"  id=idEditButton data-userid="${user.id}" data-action="edit" class="btn btn-outline-secondary"
                                data-toggle="modal" data-target="#exampleModalEdit"></button>
                            </td>
                            <td>
                                <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-outline-danger"
                                data-toggle="modal" data-target="#exampleModalDelete"></button>
                            </td>
                        </tr>    
                )`;
                console.log("uid= ", uid);
                console.log("data-userid= ", $(idEditButton).attr('data-userid'));
                console.log("##########")
                table.append(tableFilling);
            })
        })

    // обрабатываем нажатие на любую из кнопок edit или delete
    // достаем из нее данные и отдаем модалке, которую к тому же открываем
    $("#mainTableWithUsers").find('button').on('click', (event) => {
        let defaultModal = $('#someDefaultModal');
        let targetButton = $(event.target)
        let buttonUserId = targetButton.attr('data-userid')
        let buttonAction = targetButton.attr('data-action');
        console.log("buttonUserId= ", buttonUserId);
        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })
}

async function getNewUserForm() {
    let button = $(`#SliderNewUserForm`);
    let form = $(`#defaultSomeForm`)
    button.on('click', () => {
        if (form.attr("data-hidden") === "true") {
            form.attr('data-hidden', 'false');
            form.show();
            button.text('Hide panel');
        } else {
            form.attr('data-hidden', 'true');
            form.hide();
            button.text('Show panel');
        }
    })
}

// что то деалем при открытии модалки и при закрытии
// основываясь на ее дата атрибутах
async function getDefaultModal() {
    $('#someDefaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userId = thisModal.attr('data-userid');
        console.log("@@@");
        console.log(userId);
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userId);
                console.log('findOneUser - id = ', userId);
                break;
            case 'delete':
                deleteUser(thisModal, userId);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}

// редактируем юзера из модалки редактирования, забираем данные, отправляем
async function editUser(modal, id) {
    console.log("@@@");
    console.log(id);
    let preuser = await userFetchService.findOneUser(id);
    let user = preuser.json();
    console.log("user= ", user);
    modal.find('.modal-title').html('Edit user');
    let editButton = `<button  class="btn btn-outline-success" id="editButton" value="${user.id}">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);
    user.then(user => {
        let bodyForm = `
            <form class="form-group" id="editUser">
                <label  for="id">id</label>
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled><br>
                <label  for="login">Login</label>
                <input class="form-control" type="text" id="login" value="${user.username}"><br>
                <label  for="password">Password</label>
                <input class="form-control" type="password" id="password" value="${user.password}" autocomplete="off"><br>
                <label  for="enabled">Enabled</label>
                <input class="form-control" id="enabled" type="text" value="${user.enabled}">
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })
    $("#editButton").on('click', async () => {
        let id = modal.find("#id").val().trim();
        let username = modal.find("#login").val().trim();
        let password = modal.find("#password").val().trim();
        let enabled = modal.find("#enabled").val().trim();
        let data = {
            id: parseInt(id),
            username: username,
            password: password,
            enabled: enabled
        }
        console.log("for userFechService: data= ", data, "id= ", id);
        const response = await userFetchService.updateUser(data, id);
        if (response.ok) {
            getTableWithUsers();
            modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}

// удаляем юзера из модалки удаления
async function deleteUser(modal, id) {
    await userFetchService.deleteUser(id);
    getTableWithUsers();
    modal.find('.modal-title').html('');
    modal.find('.modal-body').html('User was deleted');
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(closeButton);
}

async function addNewUser() {
    $('#addNewUserButton').click(async () => {
        let addUserForm = $('#defaultSomeForm')
        let id = addUserForm.find('#AddNewUserId').val();
        let username = addUserForm.find('#AddNewUserLogin').val().trim();
        let password = addUserForm.find('#AddNewUserPassword').val().trim();
        let enabled = addUserForm.find('#AddNewUserEnabled').val().trim();
        let data = {
            id: id,
            username: username,
            password: password,
            enabled: enabled
        }
        const response = await userFetchService.addNewUser(data);
        if (response.ok) {
            getTableWithUsers();
            addUserForm.find('#AddNewUserId').val('');
            addUserForm.find('#AddNewUserLogin').val('');
            addUserForm.find('#AddNewUserPassword').val('');
            addUserForm.find('#AddNewUserEnabled').val('');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addUserForm.prepend(alert)
        }
    })
}