var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    var timeParam = {
        datepicker:false,
        format: "H:i",
        lang:'ru'
    };

    var dateParam = {
        timepicker:false,
        format: 'Y-m-d',
        lang:'ru'
    };

    $("#startDate").datetimepicker(dateParam);
    $("#endDate").datetimepicker(dateParam);
    $("#startTime").datetimepicker(timeParam);
    $("#endTime").datetimepicker(timeParam);

    $("#dateTime").datetimepicker({
        format:'Y-m-d H:i',
        lang:'ru'
    });

    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 16).replace('T', ' ');
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "Delete",
                "orderable": false,
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (!data.exceed) {
                $(row).addClass("exceeded");
            } else {
                $(row).addClass("normal");
            }
        },
        "initComplete": makeEditable
    });
});