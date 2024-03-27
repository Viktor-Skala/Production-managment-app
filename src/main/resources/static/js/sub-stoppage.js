$(document).ready(function () {

    // Функция для получения списка машин
    function getSubTypes() {
        $.ajax({
            url: "/sub_type_stoppage/all",
            type: "GET",
            dataType: "json",
            beforeSend: function(xhr) {
                var jwtToken = localStorage.getItem("jwtToken");
                if (jwtToken) {
                    xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                }
            },
            success: function (data) {
                // Очищаем список машин перед обновлением
                $("#sub-type-list-container").empty();

                // Добавляем каждую машину в список
                data.forEach(function (stoppageDto) {
                    var listItem = $("<li>").addClass("sub-stoppage-item");

                    var subTypeName = $("<div>").text(stoppageDto.name);
                    var baseTypeName = $("<div>").text(stoppageDto.baseTypeStoppageName);

                    var editButton = $("<button class='edit-button'>").text("Редагувати").click(function () {
                        window.location.href = "/sub-stoppage-update.html?id=" + stoppageDto.id;
                    });

                    var deleteButton = $("<button class='delete-button'>").text("Видалити").click(function () {
                        $.ajax({
                            url: '/sub_type_stoppage/' + stoppageDto.id,
                            type: 'DELETE',
                            beforeSend: function(xhr) {
                                var jwtToken = localStorage.getItem("jwtToken");
                                if (jwtToken) {
                                    xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                                }
                            },
                            success: function () {
                                location.reload();
                            },
                            error: function (xhr) {
                                if (xhr.status == 401) {
                                    window.location.href = '/login.html';
                                } else if (xhr.status == 403) {
                                    window.location.href = '/unauthorized.html';
                                } else {
                                    alert(xhr.responseText);
                                }
                            }
                        });
                    });

                    listItem.append(subTypeName, baseTypeName, editButton, deleteButton);
                    $("#sub-type-list-container").append(listItem);
                });
            },
            error: function (xhr) {
                if (xhr.status == 401) {
                    window.location.href = '/login.html';
                } else if (xhr.status == 403) {
                    window.location.href = '/unauthorized.html';
                } else {
                    alert(xhr.responseText);
                }
            }
        });
    }

    // Инициализация страницы
    getSubTypes();

    function fillBaseTypesDropdown() {
        $.ajax({
            url: "/base_type_stoppage/all",
            type: "GET",
            dataType: "json",
            beforeSend: function(xhr) {
                var jwtToken = localStorage.getItem("jwtToken");
                if (jwtToken) {
                    xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                }
            },
            success: function (data) {
                // Очищаем список машин перед обновлением
                $("#base-type-name").empty();

                $("#base-type-name").append("<option value='' disabled selected>Оберіть базовий тип</option>");
                // Добавляем каждую машину в выпадающий список
                data.forEach(function (baseStoppageDto) {
                    $("#base-type-name").append("<option value='" + baseStoppageDto.id + "'>" + baseStoppageDto.name + "</option>");
                });
            },
            error: function (xhr) {
                if (xhr.status == 401) {
                    window.location.href = '/login.html';
                } else if (xhr.status == 403) {
                    window.location.href = '/unauthorized.html';
                } else {
                    alert(xhr.responseText);
                }
            }
        });
    }

    fillBaseTypesDropdown();

    // Обработчик клика по кнопке "Добавить машину"
    $("#add-sub-type").on("click", function () {
        var baseTypeId = $("#base-type-name").val();
        var subTypeName = $("#sub-type-name").val();

        // Создаем объект с данными машины
        var typeStoppageData = {
            name: subTypeName,
            baseTypeStoppageId: baseTypeId
        };

        // Отправляем POST-запрос на сервер
        $.ajax({
            url: "/sub_type_stoppage",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(typeStoppageData),
            dataType: "json",
            beforeSend: function(xhr) {
                var jwtToken = localStorage.getItem("jwtToken");
                if (jwtToken) {
                    xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                }
            },
            success: function (data) {
                alert("Добавлен новий базовий тип простою: " + data.name);

                $("#base-type-name").val('');
                $("#sub-type-name").val('');

                getSubTypes();
            },
            error: function (xhr) {
                if (xhr.status == 401) {
                    window.location.href = '/login.html';
                } else if (xhr.status == 403) {
                    window.location.href = '/unauthorized.html';
                } else {
                    alert(xhr.responseText);
                }
            }
        });
    })
});
