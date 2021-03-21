let err = true;

//validate the field
function hasError(field) {
    //Get the error
    let validity = field.validity;

    if (field.type === "file") return;

    if (validity.valid) {
        err = true;
        return null;
    }

    if (validity.valueMissing) {
        err = false;
        return "Please fill out the field.";
    }
}

function showError(field, error) {
    let fieldId = $(field).attr('id');
    let msgClassName = "field_err_msg_style " + fieldId;

    $(field).addClass("err_field_border");

    if ($('#span__' + fieldId).length === 0)
        $(field).after(`<p class="${msgClassName}" id="span__${fieldId}">${error}</p>`);
}

function removeMsgError(field) {

    let fieldId = $(field).attr('id');
    let fieldClassName = "err_field_border";
    let idErrSpan = "span__" + fieldId;

    //remove the border color
    $(field).removeClass(fieldClassName);

    //remove the error msg
    $("#" + idErrSpan).remove();

}

//in this method we controlling the msg by showing then and hide them depend on of the rules
function errorController(field) {

    //Validate the fields
    let error = hasError(field);

    //if there is an error show it.
    if (error)
        showError(field, error);
    else
        removeMsgError(field);
}

$(function () {
    $("#updateModalForm").on('submit', function (event) {
        const nameModalField = event.target;

        if (event.type === "submit") return;

        errorController(nameModalField);

    });

    $("#updateModalForm input").on('blur', function (event) {
        let field = event.target;
        errorController(field);
    });
})