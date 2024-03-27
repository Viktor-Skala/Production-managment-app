$(document).ready(function () {
    // Получение параметра id из URL
    const urlParams = new URLSearchParams(window.location.search);
    const stoppageId = urlParams.get('stoppageId');

    // Запрос на получение данных о простое
    $.ajax({
        url: '/stoppage/' + stoppageId,
        type: 'GET',
        beforeSend: function(xhr) {
            var jwtToken = localStorage.getItem("jwtToken");
            if (jwtToken) {
                xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
            }
        },
        success: function (stoppageResponceDto) {
            //Заповнюємо данні машини
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
                    // Очистка выпадающего списка
                    $("#machine-name").empty();

                    data.forEach(function (machineDto) {
                        const option = $("<option></option>")
                            .attr("value", machineDto.id)
                            .text(machineDto.name);

                        if (machineDto.id === stoppageResponceDto.machineId) {
                            option.attr("selected", "selected");
                        }

                        $("#machine-name").append(option);
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

            //Заповнюємо данні продукта
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
                    // Очистка выпадающего списка
                    $("#product-name").empty();

                    data.forEach(function (productDto) {
                        const option = $("<option></option>")
                            .attr("value", productDto.id)
                            .text(productDto.productName);

                        if (productDto.id === stoppageResponceDto.productId) {
                            option.attr("selected", "selected");
                        }

                        $("#product-name").append(option);
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

            //Заповнюємо данні базового типу простоїв
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
                    // Очистка выпадающего списка
                    $("#base-type-name").empty();

                    data.forEach(function (baseStoppageDto) {
                        const option = $("<option></option>")
                            .attr("value", baseStoppageDto.id)
                            .text(baseStoppageDto.name);

                        if (baseStoppageDto.id === stoppageResponceDto.baseTypeStoppageId) {
                            option.attr("selected", "selected");
                        }

                        $("#base-type-name").append(option);
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

            //Заповнюємо данні підтипу простоїв
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
                    // Очистка выпадающего списка
                    $("#sub-type-name").empty();

                    data.forEach(function (subStoppageDto) {
                        const option = $("<option></option>")
                            .attr("value", subStoppageDto.id)
                            .text(subStoppageDto.name);

                        if (subStoppageDto.id === stoppageResponceDto.subTypeStoppageId) {
                            option.attr("selected", "selected");
                        }

                        $("#sub-type-name").append(option);
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

            // // Заполнение полей формы значениями
            // const startDate = new Date(stoppageResponceDto.startDate);
            // const endDate = new Date(stoppageResponceDto.endDate);
            //
            // // Форматирование даты в "YYYY-MM-DD"
            // const formattedStartDate = startDate.toISOString().split('T')[0];
            // const formattedEndDate = endDate.toISOString().split('T')[0];
            //
            // // Заполнение полей формы
            // $("#datepicker-start").val(formattedStartDate);
            // $("#timepicker-start").val(startDate.toTimeString().substring(0, 5)); // Время в формате "HH:mm"
            // $("#datepicker-end").val(formattedEndDate);
            // $("#timepicker-end").val(endDate.toTimeString().substring(0, 5)); // Время в формате "HH:mm"

            // Заполнение полей формы значениями
            const startDateParts = stoppageResponceDto.startDate.split(' ');
            const startDate = startDateParts[0].split('.').reverse().join('-');

            console.log(startDateParts);
            console.log(startDate);

            const endDateParts = stoppageResponceDto.endDate.split(' ');
            const endDate = endDateParts[0].split('.').reverse().join('-');

            $("#datepicker-start").val(startDate);
            $("#timepicker-start").val(startDateParts[1]);
            $("#datepicker-end").val(endDate);
            $("#timepicker-end").val(endDateParts[1]);

            // Обработчик события для кнопки "Обновить продукт"
            $('#update-product').on('click', function () {
                // Сбор данных из формы
                const updatedStoppageData = {
                    machineId: $("#machine-name").val(),
                    productId: $("#product-name").val(),
                    baseTypeStoppageId: $("#base-type-name").val(),
                    subTypeStoppageId: $("#sub-type-name").val(),
                    startDate: new Date($("#datepicker-start").val() + 'T' + $("#timepicker-start").val() + 'Z'),
                    endDate: new Date($("#datepicker-end").val() + 'T' + $("#timepicker-end").val() + 'Z'),
                };

                // Отправка PUT запроса с обновленными данными
                $.ajax({
                    url: '/stoppage/' + stoppageId,
                    type: 'PUT',
                    contentType: 'application/json',
                    data: JSON.stringify(updatedStoppageData),
                    beforeSend: function(xhr) {
                        var jwtToken = localStorage.getItem("jwtToken");
                        if (jwtToken) {
                            xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                        }
                    },
                    success: function () {
                        // Перенаправление на страницу product.html после успешного обновления
                        window.location.href = '/stoppage.html';
                    },
                    error: function (error) {
                        console.error('Помилка при оновленні простою:', error);
                    }
                });
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
