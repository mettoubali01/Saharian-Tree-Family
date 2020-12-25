/*************************************vars****************************************/
let results = "", api_call_node_detail = "", options = "", currentId = 0, parentName = "";
const endpoints = {
    nodes: "http://localhost:8088/nodes/",
    nodeInfo: "http://localhost:8088/" // add the id of the node
}
const hrefs = {
    updateNode: "/updateMember/",
    deleteNode: "/delete/"
}

/***************************************functions**********************************/

function printChart(nodos, nodosSinTratar) {
    let element = "";
    if (document.body.contains(document.getElementById("tree")))
        element = document.getElementById("tree");
    else if (document.body.contains(document.getElementById("treee")))
        element = document.getElementById("treee");

    let chart = new OrgChart(element, {
        template: "polina",
        mouseScrool: OrgChart.action.none,
        nodeMouseClick: OrgChart.action.none,
        nodeBinding: {
            field_0: "name",
            img_0: ""
        },
        nodes: nodos,
    });

    chart.on('click', function (sender, args) {
        let data = sender.get(args.node.id);
        api_call_node_detail = endpoints.nodeInfo.slice(0, (endpoints.nodeInfo.lastIndexOf('/') + 1))
        showNodeDetails();
        api_call_node_detail += data.id;
        getParentNameById(nodosSinTratar, data.pid)

        getSpecificNodeDetails(nodosSinTratar);
    });
}

//
async function getSpecificNodeDetails(nodosSinTratar) {

    const response_specific_node = await fetch(api_call_node_detail)
    const data_one_node = await response_specific_node.json();
    printNodeDetails(data_one_node);

    $("#update_data_node").click(function () {
        printUpdateDataForm(data_one_node);
    });

    $("#remove-node").click(function () {
        deleteNode(data_one_node);
    })
}

function treatData(data) {
    if (data.parent === null)
        results += "[{id : " + data.id + ", name : \"" + data.nodeDetails.name + "\"},";
    else
        results += "{id : " + data.id + ", pid : " + data.parent + ", name : \"" + data.nodeDetails.name + "\"},";

    for (let i = 0; i < data.childs.length; i++) {
        if (data.childs[i].childs.length === 0)
            results += "{id : " + data.childs[i].id + ", pid : " + data.childs[i].parent + ", name : \"" + data.childs[i].nodeDetails.name + "\"},";

        if (data.childs[i].childs.length > 0) {

            treatData(data.childs[i]);
        }
    }
}

function checkAliveOrDead(){
    $('input[type=radio][name=isDead]').on('change', function() {
        if ($(this).val() === "true")
            $(".deadDate_section").css("display","block")
        else
            $(".deadDate_section").css("display","none")
    })
}

function showNodeDetails() {
    if (document.body.contains(document.getElementById("container-tree")))
        element = document.getElementById("container-tree");
    else if (document.body.contains(document.getElementById("container-treee")))
        element = document.getElementById("container-treee");

    if (element.id === "container-treee")
        element.className = "col-sm-7 d-flex justify-content-center";
    else
        element.className = "col-sm-9";
    document.getElementById("sidenav").style.display = "block";
}

function hideNodeDetails() {
    document.getElementById("btn_close").onclick = function () {
        if (document.body.contains(document.getElementById("container-tree")))
            element = document.getElementById("container-tree");
        else if (document.body.contains(document.getElementById("container-treee")))
            element = document.getElementById("container-treee");

        if (element.id === "container-treee")
            element.className = "col-sm-7 d-flex justify-content-center";
        else
            element.className = "col-sm-12 d-flex justify-content-center";

        document.getElementById("sidenav").style.display = "none";
    }
}

/*
function printParentNamesAsOptions(data){
    console.log("Currnet id " + currentId)

    if (data.id !== currentId)
        options +="<option value=\'" +  data.nodeDetails.name + "\'>"+  data.nodeDetails.name + "<option>";
        console.log("Id " + data.id)
    for (let i = 0; i < data.childs.length; i++) {
        if ( data.childs[i].id !== currentId)
            continue;
        if (data.childs[i].childs.length === 0)
            console.log("Id " + data.childs[i].id)
            options +="<option value=\'" +  data.childs[i].nodeDetails.name + "\'>"+  data.childs[i].nodeDetails.name + "<option>";
        if (data.childs[i].childs.length > 0){

            printParentNamesAsOptions(data.childs[i]);
        }
    }
    return options
}*/
function deleteNode(data) {
    let hrefDeleteNodeBtn = hrefs.deleteNode + data.id;
    $("#delete_form > form").attr('action', hrefDeleteNodeBtn);
}

function getParentNameById(data, id) {
    if (data.id === id)
        parentName = data.nodeDetails.name;

    for (let i = 0; i < data.childs.length; i++) {
        if (data.childs[i].id === id)
            parentName = data.childs[i].nodeDetails.name;

        if (data.childs[i].childs.length > 0)
            getParentNameById(data.childs[i], id)
    }
}

function printUpdateDataForm(data) {
    let actionUpdateForm = hrefs.updateNode + data.id
    $("#update_form > form").attr('action', actionUpdateForm);
    let var_ = "th:if = \"${node.nodeDetails.id} != " + data.id + "\"";
    $("#update_form form #parentNamee option").attr(var_);

    options = "<option selected>Choose your parent</option>";
    currentId = data.id
    if (data.nodeDetails.name != null)
        $("#name__for__update").val(data.nodeDetails.name);
    else
        $("#name__for__update").val("We don't have this information yet.");
    if (data.nodeDetails.surname)
        $("#surname__for__update").val(data.nodeDetails.surname);
    else
        $("#surname__for__update").val("We don't have this information yet.");
    if (data.nodeDetails.birthDate != null)
        $("#birthDate__for__update").val(data.nodeDetails.birthDate);
    else
        $("#birthDate__for__update").val("We don't have this information yet.");
    if (data.nodeDetails.birthPlace != null)
        $("#birthPlace__for__update").val(data.nodeDetails.birthPlace);
    else
        $("#birthPlace__for__update").val("We don't have this information yet.");

    if (data.parent !== null)
        $("#parent__for_update").val(data.parent);
    else
        $("#parent__for_update").val(data.nodeDetails.name + " is the root of the Tree.");
    if (data.nodeDetails.description != null)
        $("#description__for__update").val(data.nodeDetails.description);
    else
        $("#description__for__update").val("We don't have this information yet.");
}

function printNodeDetails(data) {
    if (data.nodeDetails.name != null)
        $(".node-name").text(data.nodeDetails.name);
    else
        $(".node-name").text("We don't have this information yet.");
    if (data.nodeDetails.surname)
        $(".node-surname").text(data.nodeDetails.surname);
    else
        $(".node-surname").text("We don't have this information yet.");
    if (data.nodeDetails.birthDate != null)
        $(".node-birth-date").text(data.nodeDetails.birthDate);
    else
        $(".node-birth-date").text("We don't have this information yet.");
    if (data.nodeDetails.birthPlace != null)
        $(".node-birth-place").text(data.nodeDetails.birthPlace);
    else
        $(".node-birth-place").text("We don't have this information yet.");

    if (data.nodeDetails.deadDate != null){
        $(".node-alive-dead").text("No");
        $(".node-death-date").text(data.nodeDetails.deadDate);
        //$(".node-death-date").text(data.nodeDetails.deadDate);
    }else{
        $(".node-alive-dead").text("Yes");
        $(".node-death-date").text("---");
    }
    if (data.parent !== null) {
        $(".node-parent-name").text(parentName);
    } else
        $(".node-parent-name").text(data.nodeDetails.name + " is the root of the Tree.");
    if (data.nodeDetails.description != null)
        $(".node-description").text(data.nodeDetails.description);
    else
        $(".node-description").text("We don't have this information yet.");
}

//main
async function main() {

    const response_nodes = await fetch(endpoints.nodes)
    const all_nodes = await response_nodes.json();
    treatData(all_nodes);
    results = results.slice(0, -1);
    results += "]";
    hideNodeDetails();
    printChart(eval(results), all_nodes);
    checkAliveOrDead()
}

main().catch(err =>
    console.error(err));