/*
function drawChart() {
    var data = google.visualization.arrayToDataTable([
        ['Year', 'Sales', 'Expenses'],
        ['2013', 1000, 400],
        ['2014', 1170, 460],
        ['2015', 660, 1120],
        ['2016', 1030, 540]
    ]);

    var options = {
        title: 'Company Performance',
        curveType: 'function',
        legend: { position: 'bottom' }
    };

    var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

    chart.draw(data, options);
}
*/

document.addEventListener("DOMContentLoaded", function () {
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        // Используем данные, которые переданы через Thymeleaf
        const perfData = transportationData;

        // Создаем объект для хранения количества грузов по каждой дате отправки
        const cargoCountByDate = {};

        // Пробегаем по данным и группируем их по дате отправки
        perfData.forEach(function(perf) {
            const dispatchDate = new Date(perf.dispatch_date).toLocaleDateString();  // Преобразуем дату в формат строки

            // Если дата уже существует в объекте, увеличиваем количество грузов, иначе создаем новый ключ
            if (cargoCountByDate[dispatchDate]) {
                cargoCountByDate[dispatchDate]++;
            } else {
                cargoCountByDate[dispatchDate] = 1;
            }
        });

        // Преобразуем объект в массив для диаграммы
        const dataArray = [['Дата отправки', 'Количество грузов']];
        for (const [date, count] of Object.entries(cargoCountByDate)) {
            dataArray.push([date, count]);
        }

        // Преобразуем массив данных в формат DataTable для Google Charts
        const data = google.visualization.arrayToDataTable(dataArray);

        // Настройки диаграммы
        const options = {
            title: 'Количество грузов по датам отправки',
            hAxis: {title: 'Дата отправки'},
            vAxis: {title: 'Количество грузов'},
            legend: {position: 'none'},
            bar: {groupWidth: "95%"},
            colors: ['#76A7FA'],
        };

        // Создаем и рисуем диаграмму
        const chart = new google.visualization.ColumnChart(document.getElementById('curve_chart'));
        chart.draw(data, options);
    }
});