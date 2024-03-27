$(document).ready(function () {
    // Получение параметра id из URL
    const urlParams = new URLSearchParams(window.location.search);
    const subStoppageId = urlParams.get('id');

    // Запрос на получение данных о машине
    $.ajax({
        url: '/sub_type_stoppage/' + subStoppageId,
        type: 'GET',
        beforeSend: function(xhr) {
            var jwtToken = localStorage.getItem("jwtToken");
            if (jwtToken) {
                xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
            }
        },
        success: function (subStoppageDto) {
            // Заполнение формы данными о машине
            $('#sub-stoppage-name').val(subStoppageDto.name);

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
                    $("#base-stoppage-name").empty();

                    // Заполнение выпадающего списка и выбор текущего базового типа
                    data.forEach(function (baseStoppageDto) {
                        const option = $("<option></option>")
                            .attr("value", baseStoppageDto.id)
                            .text(baseStoppageDto.name);

                        if (baseStoppageDto.id === subStoppageDto.baseTypeStoppageId) {
                            option.attr("selected", "selected");
                        }

                        $("#base-stoppage-name").append(option);
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

            // Обработчик события для кнопки "Обновить машину"
            $('#update-sub-stoppage').on('click', function () {
                const updatedStoppageData = {
                    name: $('#sub-stoppage-name').val(),
                    baseTypeStoppageId: $('#base-stoppage-name').val()
                };

                // Отправка PUT запроса с обновленными данными
                $.ajax({
                    url: '/sub_type_stoppage/' + subStoppageId,
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
                        alert("Підтип простою успішно відредагована")
                        window.location.href = '/sub-stoppage.html';
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
