$(document).ready(function () {

    const stoppageFilterDto = {
        duration: "null"
    };

    //Загрузка незавершенных остановок
    $.ajax({
        url: "/stoppage/not_finished/all_filtered",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(stoppageFilterDto),
        beforeSend: function(xhr) {
            var jwtToken = localStorage.getItem("jwtToken");
            if (jwtToken) {
                xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
            }
        },
        success: function (data) {
            $("stoppage-creation-list-container").empty();

            // Проверяем, есть ли простои для вывода
            if (data.length > 0) {
                $("#stoppage-creation-list").prepend("<h3>Діючи простої:</h3>");
            }

            data.forEach(function (stoppageCreationDto) {
                var listItem = $("<li>").addClass("stoppage-creation-item");
                var startDate = $("<span>").text(stoppageCreationDto.startDate);
                var product = $("<span>").text(stoppageCreationDto.productName);
                var machine = $("<span>").text(stoppageCreationDto.machineName);
                var manageButton = $("<button>").text("Керувати").click(function () {
                    window.location.href = "/create-stoppage.html?stoppageId=" + stoppageCreationDto.id;
                });

                listItem.append(startDate, product, machine, manageButton);
                $("#stoppage-creation-list-container").append(listItem);
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

    // Загрузка списка действующих производств
    $.ajax({
        url: "/production/all",
        type: "GET",
        dataType: "json",
        beforeSend: function(xhr) {
            var jwtToken = localStorage.getItem("jwtToken");
            if (jwtToken) {
                xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
            }
        },
        success: function (data) {
            $("#production-list-container").empty();

            data.forEach(function (productionThreadDto) {
                var listItem = $("<li>").addClass("production-item");
                var threadName = $("<span>").text(productionThreadDto.threadName);
                var threadState = $("<span>").text(productionThreadDto.state);
                var manageButton = $("<button>").text("Керувати").click(function () {
                    window.location.href = "/manage-production.html?threadName=" + productionThreadDto.threadName;
                });

                listItem.append(threadName, threadState, manageButton);
                $("#production-list-container").append(listItem);
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

    // Загрузка списка машин
    $.ajax({
        url: "/machine/all",
        type: "GET",
        dataType: "json",
        beforeSend: function(xhr) {
            var jwtToken = localStorage.getItem("jwtToken");
            if (jwtToken) {
                xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
            }
        },
        success: function (data) {
            $("#machine-select").empty();

            $("#machine-select").append("<option value='' disabled selected>Оберіть машину</option>");
            data.forEach(function (machine) {
                $("#machine-select").append("<option value='" + machine.id + "'>" + machine.name + "</option>");
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

    $("#machine-select").on("change", function () {
        var machineId = $(this).val();

        if (machineId) {
            // Загрузка списка продуктов для выбранной машины
            $.ajax({
                url: "/product/all/byMachineId/" + machineId,
                type: "GET",
                dataType: "json",
                beforeSend: function(xhr) {
                    var jwtToken = localStorage.getItem("jwtToken");
                    if (jwtToken) {
                        xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                    }
                },
                success: function (data) {
                    $("#product-select").empty();

                    $("#product-select").append("<option value='' disabled selected>Оберіть продукт</option>");
                    data.forEach(function (productDto) {
                        $("#product-select").append("<option value='" + productDto.id + "'>" + productDto.productName + " " + productDto.machineName + "</option>");
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

    // Обработчик клика по кнопке "Запустить"
    $("#start-production").on("click", function () {
        var machineId = $("#machine-select").val();
        var productId = $("#product-select").val();

        var productionData = {
            productId: productId,
            machineId: machineId
        };

        // Отправляем GET-запрос на сервер
        $.ajax({
            url: "/production/start",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(productionData),
            beforeSend: function(xhr) {
                var jwtToken = localStorage.getItem("jwtToken");
                if (jwtToken) {
                    xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                }
            },
            success: function () {
                // Перезагрузка страницы после успешного запуска
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
});
