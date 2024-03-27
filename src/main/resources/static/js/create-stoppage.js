$(document).ready(function () {
    // Обработчик клика по кнопке "Logout"
    $("#logout").on("click", function () {
        // Ваш код для выхода из системы
        alert("Выход из системы");
    });

    const urlParams = new URLSearchParams(window.location.search);
    const stoppageId = urlParams.get('stoppageId');

    $.ajax({
        url: "/stoppage/not_full/" + encodeURIComponent(stoppageId),
        type: "GET",
        dataType: "json",
        beforeSend: function(xhr) {
            var jwtToken = localStorage.getItem("jwtToken");
            if (jwtToken) {
                xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
            }
        },
        success: function (stoppageCreateResponseDto) {
            $("#machine-name").val(stoppageCreateResponseDto.machineName).attr('readonly', true).data("machine-id", stoppageCreateResponseDto.machineId);
            $("#product-name").val(stoppageCreateResponseDto.productName).attr('readonly', true).data("product-id", stoppageCreateResponseDto.productId);
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

    $("#base-type-name").on("change", function () {
        // Получение выбранного id машины
        var baseStoppageId = $(this).val();

        // Проверка, что машина выбрана
        if (baseStoppageId) {
            // Загрузка списка продуктов для выбранной машины
            $.ajax({
                url: "/sub_type_stoppage/all/by_base_stoppage/" + baseStoppageId,
                type: "GET",
                dataType: "json",
                beforeSend: function(xhr) {
                    var jwtToken = localStorage.getItem("jwtToken");
                    if (jwtToken) {
                        xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                    }
                },
                success: function (data) {
                    // Очищаем выпадающий список продуктов перед обновлением
                    $("#sub-type-name").empty();

                    $("#sub-type-name").append("<option value='' disabled selected>Оберіть підтип</option>");
                    // Добавляем каждый продукт в выпадающий список
                    data.forEach(function (subStoppage) {
                        $("#sub-type-name").append("<option value='" + subStoppage.id + "'>" + subStoppage.name + "</option>");
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
    });

    // Обработчик кнопки "Зберегти простой"
    $("#create-stoppage").on("click", function () {
        // Получение значений из полей ввода
        const productId = $("#product-name").data("product-id");
        const machineId = $("#machine-name").data("machine-id");
        const baseTypeStoppageId = $("#base-type-name").val();
        const subTypeStoppageId = $("#sub-type-name").val();

        // Создание объекта StoppageCreateDto
        const stoppageCreateDto = {
            productId: productId,
            machineId: machineId,
            baseTypeStoppageId: baseTypeStoppageId,
            subTypeStoppageId: subTypeStoppageId
        };

        // Отправка POST-запроса на /stoppage/{id}
        $.ajax({
            url: "/stoppage/not_full/" + encodeURIComponent(stoppageId),
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(stoppageCreateDto),
            beforeSend: function(xhr) {
                var jwtToken = localStorage.getItem("jwtToken");
                if (jwtToken) {
                    xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                }
            },
            success: function (stoppageResponseDto) {
                var threadName = stoppageResponseDto.productName + " " + stoppageResponseDto.machineName;

                // Выполнение гет-запроса на /production/resume/{threadName}
                $.ajax({
                    url: "/production/resume/" + encodeURIComponent(threadName),
                    type: "GET",
                    beforeSend: function(xhr) {
                        var jwtToken = localStorage.getItem("jwtToken");
                        if (jwtToken) {
                            xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                        }
                    },
                    success: function () {
                        // Перенаправление на страницу после успешного выполнения гет-запроса
                        window.location.href = "/manage-production.html?threadName=" + threadName;
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
});
