$(document).ready(function () {
    // Получение параметра id из URL
    const urlParams = new URLSearchParams(window.location.search);
    const baseStoppageId = urlParams.get('id');

    // Запрос на получение данных о машине
    $.ajax({
        url: '/base_type_stoppage/' + baseStoppageId,
        type: 'GET',
        beforeSend: function(xhr) {
            var jwtToken = localStorage.getItem("jwtToken");
            if (jwtToken) {
                xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
            }
        },
        success: function (baseStoppageDto) {
            // Заполнение формы данными о машине
            $('#base-stoppage-name').val(baseStoppageDto.name);

            // Обработчик события для кнопки "Обновить машину"
            $('#update-base-stoppage').on('click', function () {
                const updatedStoppageData = {
                    id: baseStoppageId,
                    name: $('#base-stoppage-name').val()
                };

                // Отправка PUT запроса с обновленными данными
                $.ajax({
                    url: '/base_type_stoppage/' + baseStoppageId,
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
                        alert("Базовий тип простою успішно відредагована")
                        window.location.href = '/base-stoppage.html';
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
