$(document).ready(function () {
    // Получение параметра id из URL
    const urlParams = new URLSearchParams(window.location.search);
    const machineId = urlParams.get('id');

    // Запрос на получение данных о машине
    $.ajax({
        url: '/machine/' + machineId,
        type: 'GET',
        beforeSend: function(xhr) {
            var jwtToken = localStorage.getItem("jwtToken");
            if (jwtToken) {
                xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
            }
        },
        success: function (machineDto) {
            // Заполнение формы данными о машине
            $('#machine-name').val(machineDto.name);

            // Обработчик события для кнопки "Обновить машину"
            $('#update-machine').on('click', function () {
                const updatedMachineData = {
                    name: $('#machine-name').val(),
                };

                // Отправка PUT запроса с обновленными данными
                $.ajax({
                    url: '/machine/' + machineId,
                    type: 'PUT',
                    contentType: 'application/json',
                    data: JSON.stringify(updatedMachineData),
                    beforeSend: function(xhr) {
                        var jwtToken = localStorage.getItem("jwtToken");
                        if (jwtToken) {
                            xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                        }
                    },
                    success: function () {
                        alert("Машина успішно відредагована")
                        window.location.href = '/machine.html';
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
