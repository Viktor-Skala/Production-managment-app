$(document).ready(function () {
    // Получение параметра id из URL
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

    // Запрос на получение данных о продукте
    $.ajax({
        url: '/product/' + productId,  // Замените на ваш эндпоинт для получения данных о продукте
        type: 'GET',
        beforeSend: function(xhr) {
            var jwtToken = localStorage.getItem("jwtToken");
            if (jwtToken) {
                xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
            }
        },
        success: function (productResponceDto) {
            // Заполнение формы данными о продукте
            $('#product-name').val(productResponceDto.productName);
            $("#machine-name").val(productResponceDto.machineName).attr('readonly', true).data("machine-id", productResponceDto.machineId);
            $("#product-numbersInPack").val(productResponceDto.numbersInPack);
            $("#product-expectedProductivity").val(productResponceDto.expectedProductivity);

            // Обработчик события для кнопки "Обновить продукт"
            $('#update-product').on('click', function () {
                // Сбор данных из формы
                const updatedProductData = {
                    name: $('#product-name').val(),
                    machineId: $('#machine-name').data("machine-id"),
                    numbersInPack: $('#product-numbersInPack').val(),
                    expectedProductivity: $('#product-expectedProductivity').val()
                };

                // Отправка PUT запроса с обновленными данными
                $.ajax({
                    url: '/product/' + productId,
                    type: 'PUT',
                    contentType: 'application/json',
                    data: JSON.stringify(updatedProductData),
                    beforeSend: function(xhr) {
                        var jwtToken = localStorage.getItem("jwtToken");
                        if (jwtToken) {
                            xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
                        }
                    },
                    success: function () {
                        // Перенаправление на страницу product.html после успешного обновления
                        window.location.href = '/product.html';
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
