$(document).ready(function () {
    $("#base-stoppage").on("click", function () {
        window.location.href = "base-stoppage.html";
    });

    $("#sub-stoppage").on("click", function () {
        window.location.href = "sub-stoppage.html";
    });

    function fillMachineDropdown() {
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
                // Очищаем выпадающий список перед обновлением
                $("#machine-name").empty();

                $("#machine-name").append("<option value='' selected>Оберіть машину</option>");
                // Добавляем каждую машину в выпадающий список
                data.forEach(function (machine) {
                    $("#machine-name").append("<option value='" + machine.id + "'>" + machine.name + "</option>");
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
    fillMachineDropdown();

    function fillProductDropdown() {
        $.ajax({
            url: "/product/allDto",
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
                $("#product-name").empty();

                $("#product-name").append("<option value='' selected>Оберіть продукт</option>");
                // Добавляем каждый продукт в выпадающий список
                data.forEach(function (productDto) {
                    $("#product-name").append("<option value='" + productDto.id + "'>" + productDto.productName + " " + productDto.machineName + "</option>");
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
    fillProductDropdown();

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

                $("#base-type-name").append("<option value='' selected>Оберіть базовий тип</option>");
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

    function fillSubTypesDropdown() {
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
                $("#sub-type-name").empty();

                $("#sub-type-name").append("<option value='' selected>Оберіть підтип</option>");
                // Добавляем каждую машину в выпадающий список
                data.forEach(function (subStoppageDto) {
                    $("#sub-type-name").append("<option value='" + subStoppageDto.id + "'>" + subStoppageDto.name + "</option>");
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

    fillSubTypesDropdown();

    $("#machine-name").on("change", function () {
        // Получение выбранного id машины
        var machineId = $(this).val();

        // Проверка, что машина выбрана
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
                    // Очищаем выпадающий список продуктов перед обновлением
                    $("#product-name").empty();

                    $("#product-name").append("<option value='' selected>Оберіть продукт</option>");
                    // Добавляем каждый продукт в выпадающий список
                    data.forEach(function (productDto) {
                        $("#product-name").append("<option value='" + productDto.id + "'>" + productDto.productName + "</option>");
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
        } else {
            fillProductDropdown();
        }
    });

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

                    $("#sub-type-name").append("<option value='' selected>Оберіть підтип</option>");
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
        } else {
            fillSubTypesDropdown();
        }
    });

    var currentPage = 0; // Текущая страница (изначально 0, так как в Pageable страницы нумеруются с 0)
    var totalPages = 1;

    // Обработчики для кнопок пагинации
    $("#firstPage").on("click", function () {
        currentPage = 0; // Переход на первую страницу
        fetchData();
    });

    $("#prevPage").on("click", function () {
        if (currentPage > 0) {
            currentPage--; // Переход на предыдущую страницу
            fetchData();
        }
    });

    $("#nextPage").on("click", function () {
        // Переход на следующую страницу
        if (currentPage < totalPages - 1) {
            currentPage++;
            fetchData();
        }
    });

    $("#lastPage").on("click", function () {
        currentPage = totalPages - 1; // Переход на последнюю страницу
        fetchData();
    });

    // Функция для получения данных с сервера с учетом текущей страницы
    function fetchData() {
        // Получение значений из полей ввода
        const machineId = $("#machine-name").val();
        const productId = $("#product-name").val();
        const baseTypeStoppageId = $("#base-type-name").val();
        const subTypeStoppageId = $("#sub-type-name").val();
        const startDate = new Date(($("#datepicker-start").val() || "1900-01-01") + 'T' + ($("#timepicker-start").val() || "00:00:00") + 'Z');
        const endDate = new Date(($("#datepicker-end").val() || "2100-01-01") + 'T' + ($("#timepicker-end").val() || "23:59:59") + 'Z');
        const durationStart = (parseInt($("#days-start").val()) || 0) * 86400 + (parseInt($("#hours-start").val()) || 0) * 3600 + (parseInt($("#minutes-start").val()) || 0) * 60 + (parseInt($("#seconds-start").val()) || 0);
        const durationEnd = (parseInt($("#days-end").val()) || 0) * 86400 + (parseInt($("#hours-end").val()) || 0) * 3600 + (parseInt($("#minutes-end").val()) || 0) * 60 + (parseInt($("#seconds-end").val()) || 0);
        const finalDurationEnd = durationEnd === 0 ? 9999999999 : durationEnd;
        const pageSize = parseInt($("#stoppagesOnPage").val()) || 20;

        // Создание объекта StoppageFilterDto без указания текущей страницы
        const stoppageFilterDto = {
            machineId: machineId,
            productId: productId,
            baseTypeStoppageId: baseTypeStoppageId,
            subTypeStoppageId: subTypeStoppageId,
            startDate: startDate,
            endDate: endDate,
            durationStart: durationStart,
            durationEnd: finalDurationEnd
        };

        // Отправка GET-запроса на /stoppage/all_by_criteria с параметрами пагинации
        $.ajax({
            url: `/stoppage/all_by_criteria_paged?page=${currentPage}&size=${pageSize}`, // Пример, где size - количество элементов на странице
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
                totalPages = data.totalPages; // Обновление общего количества страниц
                $("#currentPage").text(currentPage + 1); // Обновление номера текущей страницы

                // Очищаем список перед обновлением
                $("#stoppage-list-container").empty();

                // Добавляем каждое производство в список
                data.content.forEach(function (stoppageResponseDto) {
                    var listItem = $("<li>").addClass("stoppage-item");
                    var startDate = $("<span>").text(stoppageResponseDto.startDate);
                    var duration = $("<span>").text(stoppageResponseDto.duration);
                    var product = $("<span>").text(stoppageResponseDto.productName);
                    var machine = $("<span>").text(stoppageResponseDto.machineName);
                    var baseType = $("<span>").text(stoppageResponseDto.baseTypeStoppageName);
                    var subType = $("<span>").text(stoppageResponseDto.subTypeStoppageName);
                    var manageButton = $("<button>").text("Керувати").click(function () {
                        window.location.href = "/stoppage-update.html?stoppageId=" + stoppageResponseDto.id;
                    });

                    listItem.append(startDate, duration, product, machine, baseType, subType, manageButton);
                    $("#stoppage-list-container").append(listItem);
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

    $("#find-stoppage").on("click", function () {
        fetchData();
    });

    $("#showDiagram").on("click", function () {

        // Получение значений из полей ввода
        const machineId = $("#machine-name").val();
        const productId = $("#product-name").val();
        const baseTypeStoppageId = $("#base-type-name").val();
        const subTypeStoppageId = $("#sub-type-name").val();
        const startDate = new Date(($("#datepicker-start").val() || "1900-01-01") + 'T' + ($("#timepicker-start").val() || "00:00:00") + 'Z');
        const endDate = new Date(($("#datepicker-end").val() || "2100-01-01") + 'T' + ($("#timepicker-end").val() || "23:59:59") + 'Z');
        const durationStart = (parseInt($("#days-start").val()) || 0) * 86400 + (parseInt($("#hours-start").val()) || 0) * 3600 + (parseInt($("#minutes-start").val()) || 0) * 60 + (parseInt($("#seconds-start").val()) || 0);
        const durationEnd = (parseInt($("#days-end").val()) || 0) * 86400 + (parseInt($("#hours-end").val()) || 0) * 3600 + (parseInt($("#minutes-end").val()) || 0) * 60 + (parseInt($("#seconds-end").val()) || 0);
        const finalDurationEnd = durationEnd === 0 ? 9999999999 : durationEnd;

        // Создание объекта StoppageCreateDto
        const stoppageFilterDto = {
            machineId: machineId,
            productId: productId,
            baseTypeStoppageId: baseTypeStoppageId,
            subTypeStoppageId: subTypeStoppageId,
            startDate: startDate,
            endDate: endDate,
            durationStart: durationStart,
            durationEnd: finalDurationEnd
        };

        var selectedParameter = $("#group-by-parameter").val();

        // Получите элемент canvas
        var canvas = document.getElementById('barChart');

        // Установите желаемые размеры
        canvas.width = 1200;
        canvas.height = 400;

        // Отправка POST-запроса на /stoppage/{id}
        $.ajax({
            url: "/stoppage/all_by_criteria",
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
                var durationSumByParameter = {};

                data.forEach(function (stoppageResponseDto) {

                    var groupByValue = stoppageResponseDto[selectedParameter];
                    if (!durationSumByParameter[groupByValue]) {
                        durationSumByParameter[groupByValue] = 0;
                    }
                    durationSumByParameter[groupByValue] += stoppageResponseDto.durationInSec;
                });

                var chartLabels = Object.keys(durationSumByParameter);
                var chartData = Object.values(durationSumByParameter);

                // Отображение графика
                displayBarChart(chartLabels, chartData);
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

    var myChart;

    // Функция для отображения графика
    function displayBarChart(labels, data) {
        // Уничтожаем предыдущий график, если он существует
        if (myChart) {
            myChart.destroy();
        }
        var ctx = document.getElementById('barChart').getContext('2d');
        myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Сумма продолжительности простоев',
                    data: data,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                },
                maintainAspectRatio: true, // отключает сохранение пропорций
                responsive: false, // позволяет графику адаптироваться к размерам контейнера
            }
        });
    }
});
